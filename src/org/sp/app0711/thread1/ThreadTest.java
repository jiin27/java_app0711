package org.sp.app0711.thread1;

import java.awt.Color;
import java.awt.Dimension;
import java.awt.FlowLayout;
import java.awt.Font;
import java.awt.event.ActionEvent;
import java.awt.event.ActionListener;

import javax.swing.JButton;
import javax.swing.JFrame;
import javax.swing.JLabel;

/*
 * 1) 쓰레드(Thread) 란?
 * - 하나의 프로세스(process_실행중인 프로그램)내에서 독립적으로 실행될 수 있는 세부 실행 단위.
 * 
 * 							OS				JVM
 * 멀티태스킹 대상	프로세스			쓰레드(thread)
 * 병행 처리 원리		시분할				시분할
 * 
 * 2) 쓰레드의 생명주기(LifeCycle)
 * - 작성: 로직은 run() 메서드를 override 해야 한다
 * - 생성: 개발자가 new
 * - Runnable 상태로 진입시킨다: start() 호출 (jvm에게 전적으로 맡김)
 * 	  jvm의 실행 대상이 된다. 즉 실행 후보가 된다.
 * - jvm의 내부 알고리즘에 의해 랜덤하게 특정 thread가 선택되고, 이 선택된 쓰레드의 run() 메서드를 실행하는 단계를 Running 이라 한다. (1회 실행이 아니라면 무조건 while문으로 실행)
 * - 소멸: run() 메서드의 닫는 브레이스 }를 만나면 쓰레드는 소멸된다. 이때의 생명주기 상태를 가리켜 Dead라 한다.
 * 
 * 3) 대부분의 프로그래밍 언어에서는 Thread를 지원한다.
 * 
 * */
public class ThreadTest extends JFrame{
	JButton bt; // 시작버튼
	JLabel la1; // 1씩 증가하는 숫자를 보여줄 라벨
	JLabel la2; // 5씩 감소하는 숫자를 보여줄 라벨
	int n;
	
	public ThreadTest() {
		bt=new JButton("Start");
		la1=new JLabel("0");
		la2=new JLabel("0");
		
		la1.setBackground(Color.YELLOW);
		la2.setBackground(Color.YELLOW);
		
		la1.setFont(new Font("Verdana", Font.BOLD, 100));
		la2.setFont(new Font("Verdana", Font.BOLD, 100));
		
		la1.setPreferredSize(new Dimension(270, 170));
		la2.setPreferredSize(new Dimension(270, 170));
		
		setLayout(new FlowLayout());
		
		add(bt);
		add(la1);
		add(la2);
		
		setVisible(true);
		setSize(300, 400);
		setDefaultCloseOperation(EXIT_ON_CLOSE);
	
		//이벤트 연결 코드는 재사용성이 없기 때문에, 되도록이면 코드가 간소화될 수록 좋다.
		//따라서 이벤트 구현시 내부익명클래스를 지원함
		//* java8 람다(Lambda) : 굳이 이벤트 연결 코드를 객체로까지 갈 필요 있는가? --> 그냥 함수로 표현하는 방법을 지원함.
		//								 ex) js에서의 화살표 함수랑 흡사.
		bt.addActionListener(new ActionListener() {
			
			//내부 익명 클래스 내의 메서드에서는 바깥쪽 외부 클래스(ThreadTest)의 멤버 변수를 내 것처럼 쓸 수 있다. (지역변수는 final로 선언해  함)
			public void actionPerformed(ActionEvent e) {
				System.out.println("버튼 눌렀어?");
				//내부익명클래스에서는 지역변수에 접근하려면 해당 지역변수는 final로 선언되어 있어야 한다.
				createThread();
			}
		});
	}
	
	//버튼을 누르면 2개의 쓰레드를 생성하여 jvm에 맡기자
	//왜? 시분할로 멀티 쓰레드 작동을 위해
	//메인 실행부는 절대로, 대기상태나 무한 루프에 빠뜨리면 안됨.
	//왜? 
	//1) GUI 에서 사용자의 이벤트 처리, 그래픽 처리를 할 수 없기 때문에
	//2) 
	public void createThread() {
		
//		while (true) {
//			System.out.println("호출함");
//		}
		AddThread t1=new AddThread(this);
		t1.start(); //Runnable로 진입
		
		//MinusThread t2=new MinusThread();
		Thread t2=new Thread() { // 내부 익명 클래스로 ThreadTest 클래스를 넘겨 받는 하나의 방법. 클래스 재사용성이 없을 때는 클래스를 만들어 .java를 만들지 말고 내부 익명 클래스로 넣자.
			public void run() {
				while(true) {
					try {
						Thread.sleep(1000);
					} catch (InterruptedException e) {
						e.printStackTrace();
					}
					n-=5;
					la2.setText(Integer.toString(n));
				}
			}
		};
		t2.start(); //Runnable로 진입
		
		//쓰레드를 이용하는 코드는 비동기 방식으로 동작되므로 메인쓰레드는 개발자가 정의한 쓰레드의 실행완료를 기다리지 않는다.
		//비동기 방식이란, 특정 코드가 끝날 때가지 기다리지 않고 다른 코드를 실행할 수 있는 방식을 말한다.
		System.out.println("main thread에 의해 실행");
	}
	
	public static void main(String[] args) {
		//아래의 코드를 실행하면, 에러 메시지에 main thread에서 에러가 발생했다는 메시지가 출력된다.
		//결론) java의 실행부는 main Thread.
		//		main Thread는 프로그램을 운영하는 실행부이므로, 반드시 대기상태나, 루프에 빠뜨리지 말자!
//		int[] arr=new int[2];
//		arr[0]=5;
//		arr[1]=7;
//		arr[2]=9; //ArrayIndexOutOfBoundsException. 실행 시 error 남.
		
		new ThreadTest();
	}
	
}

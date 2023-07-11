package org.sp.app0711.thread1;

//숫자 증가용 thread
public class AddThread extends Thread{
	int n;
	ThreadTest tt; //외부 클래스. 즉 외부 객체이므로 객체 내 컴포넌트에 접근하려면 클래스(객체)를 선언해줘야 한다.
	
	public AddThread(ThreadTest tt) { //넘겨 받을 땐 꼭 매개변수 활용
		this.tt=tt;
	}
	
	//개발자는 독립수행하고 싶은 코드가 있을때, 반드시 run메서드에 코드를 작성해야 한다. 
	//jvm이 호출하는 메서드이기 때문에
	public void run() {
		while (true) {
			try {
				Thread.sleep(1000);
			} catch (InterruptedException e) {
				e.printStackTrace();
			}
			System.out.println("증가 쓰레드 run() 호출");
			n++;
			
			//첫번째 라벨에 출력
			tt.la1.setText(Integer.toString(n));
		}
	}
}

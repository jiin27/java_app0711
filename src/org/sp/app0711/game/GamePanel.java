package org.sp.app0711.game;

import java.awt.Color;
import java.awt.Dimension;
import java.awt.Graphics;
import java.awt.Image;
import java.awt.event.KeyEvent;
import java.awt.event.KeyListener;
import java.awt.image.BufferedImage;
import java.io.IOException;
import java.net.URL;
import java.util.ArrayList;
import java.util.List;

import javax.imageio.ImageIO;
import javax.swing.JPanel;

//실제로 모든 게임의 그래픽이 표현될 컨테이너
public class GamePanel extends JPanel implements KeyListener{
	public static final int WIDTH=1200;
	public static final int HEIGHT=700;
	
	//메인 쓰레드를 루프에 빠뜨리지 않고, 별도의 개발자 정의 쓰레드를 만들어 루프를 실행하기 위해.
	//메인 쓰레드는 프로그램의 실행과 운영을 담당하는 메인 실행부이므로 대기나 루프에 빠지는 순간 프로그램 운영이 멈춘다.
	//또한 이벤트 감지도 불가하다.
	Thread loopThread;
	Image bgImg; //배경 이미지
	Hero hero;
	List<Bullet> bulletList=new ArrayList<Bullet>();
	
	public GamePanel() {
		setPreferredSize(new Dimension(WIDTH, HEIGHT));
				
		createBg(); //배경 이미지 준비
		createHero(); //주인공 등장
		
		//게임 루프 생성하기
		loopThread=new Thread() {
			public void run() {
				while(true) {
					try {
						Thread.sleep(1);
					} catch (InterruptedException e) {
						e.printStackTrace();
					} //1000분의 1초 동안 non-runnable로 진입하도록, 하지만 지정한 시간이 지나면 다시 runnable 영역으로 복귀한다.
					loop();
				}
			}
		};
		
		loopThread.start(); //Runnable 로 진입
		
		//패널에 키보드와 관련된 리스너 연결
		this.addKeyListener(this);
		
	}
	
	//개발자가 주도하여 그림을 뺏어 그려야 게임을 구현할 수 있다.
	//결론) 컴포넌트가 보유한 paint() 메서드를 재정의하자
	public void paint(Graphics g) {
		g.setColor(Color.ORANGE);
		g.fillRect(0, 0, WIDTH, HEIGHT);
		
		g.drawImage(bgImg, 0, 0, WIDTH, HEIGHT, this);
		hero.render(g); //g 객체를 넘겨 받아야 하기 때문에 g 객체를 보유한 paint() 메서드 내에서 호출해야 한다. 
		for(int i=0; i<bulletList.size(); i++) {
			bulletList.get(i).render(g);
		}
		
	}
	
	//배경 이미지 만들기
	public void createBg() {
		URL url=ClassLoader.getSystemResource("res/hero/game_bg.png");
		
		BufferedImage buffImg=null; //기존 이미지 객체보다 크기, 편집이 용이한 이미지
		try{
			buffImg=ImageIO.read(url);
			bgImg=buffImg;
		}catch(IOException e) {
			e.printStackTrace();
		}
		
		//배경 이미지 그리기
		Image image=null;
	}
	
	//주인공 생성
	public void createHero() {
		hero=new Hero(0, 200, 100, 100, 0, 0);
	}
	
	//주인공의 좌우 움직임
	public void moveX(int velX) {
		hero.velX=velX;
	}
	
	//주인공의 위아래 움직임
	public void moveY(int velY) {
		hero.velY=velY;
	}
	
	//주인공 총알 발사
	public void fire() {
		Bullet bullet=new Bullet(hero.x+hero.width, hero.y+(hero.height/2), 25, 25, 5, 0);
		
		bulletList.add(bullet);
	}
	
	public void keyTyped(KeyEvent e) {		
	}

	public void keyPressed(KeyEvent e) {
		//System.out.println("키보드 눌렀어");
		int key=e.getKeyCode();
		
		switch (key) {
			case KeyEvent.VK_LEFT:moveX(-2);break;
			case KeyEvent.VK_RIGHT:moveX(2);break;
			case KeyEvent.VK_UP:moveY(-2);break;
			case KeyEvent.VK_DOWN:moveY(2);break;
			case KeyEvent.VK_SPACE:fire();break;
		}
	}

	public void keyReleased(KeyEvent e) {
		int key=e.getKeyCode();
		
		switch (key) {
			case KeyEvent.VK_LEFT:moveX(0);break;
			case KeyEvent.VK_RIGHT:moveX(0);break;
			case KeyEvent.VK_UP:moveY(0);break;
			case KeyEvent.VK_DOWN:moveY(0);break;
		}

	}
	
	//게임의 심장인 루프를 수행
	public void loop() {
		//주인공의 움직임
		hero.tick();
		
		//총알의 움직임
		for(int i=0; i<bulletList.size(); i++) {
			bulletList.get(i).tick();			
		}
		
		repaint(); //g객체를 보유한 paint() 메서드를 간접 호출하기 위해(paint() 메서드가 호출돼야 hero.render() 메서드를 호출할 수 있으므로) repaint() 호출
	}
	
}

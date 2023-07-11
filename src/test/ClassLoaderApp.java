package test;

import java.awt.image.BufferedImage;
import java.io.IOException;
import java.net.URL;

import javax.imageio.ImageIO;

public class ClassLoaderApp {
	
	public ClassLoaderApp() {
		//자바의 여러 클래스 중 class에 대한 정보를 가진 클래스를 Class 라고 한다. 
		//.java 나 .class는 .으로 경로 표현하는 게 원칙이지만, 그외 파일이라면 디렉토리 경로를 표현할 때 쓰는 / 로 표현하는 게 원칙.
		URL url=ClassLoader.getSystemResource("res/hero/game_bg.png");
		
		//기존 Image 객체보다 크기나 사이즈 조절이 가능한 이미지를 말함
		//따라서 Image보다 더 활용도가 높다
		try {
			BufferedImage buffimg=ImageIO.read(url);
			System.out.println(buffimg);
		} catch (IOException e) {
			e.printStackTrace();
		}
	}
	
	public static void main(String[] args) {
		new ClassLoaderApp();
	}
}

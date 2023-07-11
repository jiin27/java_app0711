package org.sp.app0711.game;

import java.awt.event.KeyEvent;
import java.awt.event.KeyListener;

import javax.swing.JFrame;

public class GameMain extends JFrame {
	GamePanel gamePanel;
	
	public GameMain() {
		gamePanel=new GamePanel();
		add(gamePanel);
		
		pack();
		setVisible(true);
		setLocationRelativeTo(null);
		setDefaultCloseOperation(EXIT_ON_CLOSE);
		
		gamePanel.requestFocus(); //윈도우가 가지고 있던 focus()를 gamePanel에 전달하기
	}
		
	public static void main(String[] args) {
		new GameMain();
	}
}

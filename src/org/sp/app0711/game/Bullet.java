package org.sp.app0711.game;

import java.awt.Graphics;
import java.awt.Image;
import java.io.IOException;

import javax.imageio.ImageIO;

public class Bullet extends GameObject{
	Image img;

	public Bullet(int x, int y, int width, int height, int velX, int velY) {
		super(x, y, width, height, velX, velY);
		
		try {
			img=ImageIO.read(ClassLoader.getSystemResource("res/hero/ball.png"));
		} catch (IOException e) {
			e.printStackTrace();
		}
	}

	public void tick() {
		x+=velX;
	}

	public void render(Graphics g) {
		g.drawImage(img, x, y, width, height, null);
	}

}

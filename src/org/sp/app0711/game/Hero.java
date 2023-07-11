package org.sp.app0711.game;

import java.awt.Graphics;
import java.awt.Image;
import java.awt.image.BufferedImage;
import java.io.IOException;
import java.net.URL;

import javax.imageio.ImageIO;

public class Hero extends GameObject{
	Image img; //주인공 이미지

	public Hero(int x, int y, int width, int height, int velX, int velY) {
		super(x, y, width, height, velX, velY);
		URL url=ClassLoader.getSystemResource("res/hero/rocket.png");
		BufferedImage buffImg=null;
		try {
			buffImg=ImageIO.read(url);
			img=buffImg;
		} catch (IOException e) {
			e.printStackTrace();
		}
	}

	public void tick() {
		x+=velX;
		y+=velY;
	}

	public void render(Graphics g) {
		g.drawImage(img, x, y, width, height, null);
	}

}

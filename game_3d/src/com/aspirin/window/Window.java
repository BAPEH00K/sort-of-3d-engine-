package com.aspirin.window;

import java.awt.Canvas;
import java.awt.Dimension;
import java.awt.Graphics;
import java.awt.Graphics2D;
import java.awt.image.BufferedImage;
import java.awt.image.DataBufferInt;
import java.util.Arrays;

import javax.swing.JFrame;

public class Window extends JFrame{
	
	private JFrame window;
	
	public static final int DEFAULT_WIDTH=640, DEFAULT_HEIGHT=640; 
	
	public static final String DEFAULT_TITLE="КУБ ГАВНА";
	
	private static Canvas content;
	
	//Буферезация
	private static BufferedImage buffer;
	private static int [] bufferData;
	private static Graphics bufferGraphics;
	private static final int clearColor=0xff000000;

	
	public Window() {
		this.window=new JFrame(DEFAULT_TITLE);
		
		content=new Canvas();
		this.window.setSize(DEFAULT_WIDTH, DEFAULT_HEIGHT);
		content.setPreferredSize(new Dimension(getWidth(),getHeight()));
		
		
		this.window.setDefaultCloseOperation(JFrame.EXIT_ON_CLOSE);
		
		this.window.getContentPane().add(content);
		
		this.window.pack();
		this.window.setLocationRelativeTo(null);
		
		this.window.setVisible(true);
		
		buffer=new BufferedImage(this.getWidth(),this.getHeight(),BufferedImage.TYPE_INT_ARGB);
		bufferData=((DataBufferInt) buffer.getRaster().getDataBuffer()).getData();
		bufferGraphics=buffer.getGraphics();
	}
	
	public static void clear() {
		Arrays.fill(bufferData, clearColor);
	}
	
	public static void render() {}
	
	public static void swapBuffers() {
		Graphics g=content.getGraphics();
		g.drawImage(buffer, 0, 0, null);
	}

	public int getWidth() {
		return this.window.getWidth();
	}
	
	public int getHeight() {
		return this.window.getHeight();
	}
	
	public Graphics2D getGraphics() {
		return (Graphics2D) bufferGraphics;
	}

	public BufferedImage getImage() {
		return this.buffer;
	}
	
	public void setTitle(String title) {
		this.window.setTitle(title);
	}
}

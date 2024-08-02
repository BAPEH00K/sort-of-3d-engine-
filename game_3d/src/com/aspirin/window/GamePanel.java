package com.aspirin.window;

import java.awt.Color;
import java.awt.Dimension;

import javax.swing.JPanel;

public class GamePanel extends JPanel{
	
	public GamePanel(int width, int height) {
		this.setPreferredSize(new Dimension(width, height));
		this.setBackground(Color.BLACK);
		this.setDoubleBuffered(true);
	}
	
}

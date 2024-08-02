package com.aspirin.main;

import java.awt.Color;
import java.util.ArrayList;

import com.aspirin.graphics.Cube;
import com.aspirin.graphics.Triangle;
import com.aspirin.graphics.Vertex3;
import com.aspirin.window.Window;

public class Main {

	
	private static Window window;
	
	private static GameManager gm;
	
	public static void main(String[] args) {
		// TODO Auto-generated method stub
		window=new Window();
		
		gm=new GameManager(window);
		gm.Start();
		
	}

}

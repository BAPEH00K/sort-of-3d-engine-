package com.aspirin.graphics;

public class Vertex2 {
	public double x,y;
	
	
	public Vertex2 (double x, double y) {
		this.x=x;
		this.y=y;
	}
	
	public Vertex2 (int x, int y) {
		this.x=(double)x;
		this.y=(double)y;
	}
	
	public Vertex2(double [] data) {
		this.x=data[0];
		this.y=data[1];
	}
}

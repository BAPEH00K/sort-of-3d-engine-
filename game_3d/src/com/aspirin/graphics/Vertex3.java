package com.aspirin.graphics;

public class Vertex3 {
	public double x,y,z;
	
	
	public Vertex3 (double x, double y, double z) {
		this.x=x;
		this.y=y;
		this.z=z;
	}
	
	public Vertex3 (int x, int y, int z) {
		this.x=(double)x;
		this.y=(double)y;
		this.z=(double)z;
	}
	
	public Vertex3(double [] data) {
		this.x=data[0];
		this.y=data[1];
		this.z=data[2];
	}
	
	public Vertex3(Vertex3 v) {
		this.x=v.x;
		this.y=v.y;
		this.z=v.z;
	}
	
	public void log() {
		System.out.println("V3=("+this.x+"; "+this.y+"; "+this.z+")");
	}
	
}

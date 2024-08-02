package com.aspirin.utils;

public class Vector2 {
	public double x,y, x1,y1,x2,y2, length;
	
	public Vector2(double x, double y) {
		this.x=x;
		this.y=y;
		this.x1=0;
		this.x1=0;
		this.x2=x;
		this.y2=y;
		this.length=Vector2.calcLength(this);
	}
	
	public Vector2(Vector3 v) {
		this.x=v.x;
		this.y=v.y;
		this.x1=0;
		this.x1=0;
		this.x2=x;
		this.y2=y;
		this.length=Vector2.calcLength(this);
	}
	
	public static double calcLength(Vector2 v) {
		return Vector2.calcLength(v.x,v.y);
	}
	
	private static double calcLength(double x, double y) {
		return Math.sqrt(Math.pow(x, 2)+Math.pow(y, 2));
	}


	

}

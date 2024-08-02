package com.aspirin.utils;

import com.aspirin.graphics.Vertex3;

public class Vector3 {
	public double x,y,z, x1,y1,z1,x2,y2,z2, length;
	
	public Vector3(double x, double y, double z) {
		this.x=x;
		this.y=y;
		this.z=z;
		this.x1=0;
		this.z1=0;
		this.x1=0;
		this.x2=x;
		this.y2=y;
		this.z2=z;
		
		this.length=Vector3.calcLength(this);
	}
	
	public Vector3(Vertex3 v1, Vertex3 v2) {
		this.x1=v1.x;
		this.y1=v1.x;
		this.z1=v1.z;
		this.x2=v2.x;
		this.y2=v2.y;
		this.z2=v2.z;
		
		this.x=x2-x1;
		this.y=y2-y1;
		this.z=z2-z1;
		this.length=Vector3.calcLength(this);
	}
	

	
	public static double calcLength(double x, double y, double z) {
		return Math.sqrt(Math.pow(x, 2)+Math.pow(y, 2)+Math.pow(z, 2));
	}
	
	public static int calcLength(int x, int y, int z) {
		return (int) Math.sqrt(Math.pow(x, 2)+Math.pow(y, 2)+Math.pow(z, 2));
	}
	
	public static double calcLength(Vector3 v) {
		return Vector3.calcLength(v.x,v.y,v.z);
	}
	
	public static double calcLength(Vertex3 v1, Vertex3 v2) {
		return Vector3.calcLength(v2.x-v1.x,v2.y-v1.y,v2.z-v1.z);
	}
	
	public static Vector3 crossProduct(Vector3 v1, Vector3 v2) {
		return new Vector3(v1.y*v2.z-v1.z*v2.y,
				-(v1.x*v2.z-v1.z*v2.x),
				v1.x*v2.y-v1.y*v2.x);
	}
	
	public static double dotProduct(Vector3 v1, Vector3 v2) {
		return v1.x*v2.x+v1.y*v2.y+v1.z*v2.z;
	}
	
	public void log() {
		System.out.println("---------");
		System.out.println("X="+this.x+" Y="+this.y+" Z="+this.z);
		
	}
}

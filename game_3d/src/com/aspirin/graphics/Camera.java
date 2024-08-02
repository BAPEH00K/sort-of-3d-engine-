package com.aspirin.graphics;

public class Camera {
	public double x,y,z, azimut, zenit;
	public Vertex3 viewPoint;
	
	public  Camera(double x, double y, double z, double azimut, double zenit) {
		this.x=x;
		this.y=y;
		this.z=z;
		this.azimut=azimut;
		this.zenit=zenit;
		
		viewPoint=new Vertex3(x,y,z);
	}
}

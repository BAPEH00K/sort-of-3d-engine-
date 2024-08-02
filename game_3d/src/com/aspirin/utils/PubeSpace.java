package com.aspirin.utils;

import com.aspirin.graphics.Vertex3;

public class PubeSpace {
	
	public static final double PUBE_SPACE_WIDTH=2.0, PUBE_SPACE_HEIGHT=2.0, PUBE_SPACE_DEPTH=2.0;
	public double X_FACTOR,Y_FACTOR;
	
	public PubeSpace(int ScreenWidth, int ScreenHeight) {
		X_FACTOR=(double)(ScreenWidth)/PubeSpace.PUBE_SPACE_WIDTH;
		Y_FACTOR=(double)(ScreenHeight)/PubeSpace.PUBE_SPACE_HEIGHT;
		
	}
	
	public Vertex3 Transform(Vertex3 v,double z) {
		Vertex3 v2=new Vertex3(v);
		v2.z=v.z+z;
		double Z_FACTOR=1.0f/(v.z+z);
		v2.x=(v.x*Z_FACTOR+1.0f)*this.X_FACTOR;
		v2.y=(-v.y*Z_FACTOR+1.0f)*this.Y_FACTOR;
		//System.out.println("X_FACTOR="+this.X_FACTOR+" | Z_FACTOR="+Z_FACTOR+" | X="+v.x+1*this.X_FACTOR+" | dX="+v2.x);
		return v2;
	}
}

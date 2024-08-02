package com.aspirin.utils;

import com.aspirin.graphics.Vertex3;

public class Matrix3 {

	private double [] values;
	
	public Matrix3 (double [] values) {
		this.values=values;
	}
	
	public double[] getValues() {
		return this.values;
	}
	
	public static Matrix3 multiply(Matrix3 a, Matrix3 b) {
		 double[] result = new double[9];
	        for (int row = 0; row < 3; row++) {
	            for (int col = 0; col < 3; col++) {
	                for (int i = 0; i < 3; i++) {
	                    result[row * 3 + col] +=
	                        a.values[row * 3 + i] * b.values[i * 3 + col];
	                }
	            }
	        }
	        return new Matrix3(result);
	}	
	
	public static Vertex3 transform(Vertex3 in, Matrix3 matrix) {
		double x=in.x;
		double y=in.y;
		double z=in.z;
		
		double newX, newY, newZ;
		double[] trans_values=matrix.getValues();
		
		newX=x*trans_values[0]+y*trans_values[3]+z*trans_values[6];
		newY=x*trans_values[1]+y*trans_values[4]+z*trans_values[7];
		newZ=x*trans_values[2]+y*trans_values[5]+z*trans_values[8];
		
		return new Vertex3(newX, newY, newZ);
	}
	
	public static Matrix3 createXRotMat(double radians) {
		double cos=Math.cos(radians);
		double sin=Math.sin(radians);
		return new Matrix3(new double[] {1,0,0,
				0,cos,sin,
				0, -sin, cos});
	}
	
	public static Matrix3 createYRotMat(double radians) {
		double cos=Math.cos(radians);
		double sin=Math.sin(radians);
		return new Matrix3(new double[] {cos,0,-sin,
				0,1,0,
				sin, 0, cos});
	}
	
	public static Matrix3 createZRotMat(double radians) {
		double cos=Math.cos(radians);
		double sin=Math.sin(radians);
		return new Matrix3(new double[] {cos,sin,0,
				-sin,cos,0,
				0, 0, 1});
	}
}

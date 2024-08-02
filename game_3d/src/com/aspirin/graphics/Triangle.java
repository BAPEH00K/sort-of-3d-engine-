package com.aspirin.graphics;

import java.awt.Color;
import java.awt.Graphics2D;
import java.awt.image.BufferedImage;
import java.util.Arrays;
import java.util.Collections;

import com.aspirin.utils.Matrix3;
import com.aspirin.utils.PubeSpace;
import com.aspirin.utils.Vector3;

public class Triangle {
	public double size, z_offset;
	private Vertex3[] verticies;
	public Vertex3[] transformed_verticies;
	private Color color;
	
	public static final int FLAT_TOP=0, FLAT_BOTTOM=1, UNDEFINED=-1, GENERAL=2;
	
	public int triangleClass;
	
	public Vector3 normal;
	public boolean cullFlag=false;
	
	
	public Triangle(Vertex3 v1, Vertex3 v2, Vertex3 v3, Color color, PubeSpace pst, double offset) {
		this.verticies=new Vertex3[3];
		this.transformed_verticies=new Vertex3[3];
		this.color=color;
		this.verticies[0]=v1;
		this.verticies[1]=v2;
		this.verticies[2]=v3;
		
		this.triangleClass=Triangle.UNDEFINED;
		sortShit();	

		this.z_offset=offset;
		transform(pst);

		
	}
	
	//Ошибка в закрутке треугольников
	public void checkClass() {
		if(this.triangleClass!=Triangle.UNDEFINED)
			return;
		
		if(this.verticies[0].y==this.verticies[1].y) {
			this.triangleClass=Triangle.FLAT_TOP;
		}else if(verticies[1].y==verticies[2].y){
			this.triangleClass=Triangle.FLAT_BOTTOM;
		}
		else
		{
			this.triangleClass=Triangle.GENERAL;
		}
	}
	
	
	public void sortShit() {
		sortVerticiesVertical();
		checkClass();
		sortVerticiesHorizontal();
	}
	
	public void setOffset(double offset) {
		this.z_offset=offset;
	}
	
	public void transform(PubeSpace pst) {
		this.transformed_verticies[0]=pst.Transform(this.verticies[0], this.z_offset);
		this.transformed_verticies[1]=pst.Transform(this.verticies[1], this.z_offset);
		this.transformed_verticies[2]=pst.Transform(this.verticies[2], this.z_offset);
	}
	
	private void sortVerticiesVertical() {

		if(verticies[1].y<verticies[0].y)
			Collections.swap(Arrays.asList(this.verticies), 0, 1);
		if(verticies[2].y<verticies[1].y)
			Collections.swap(Arrays.asList(this.verticies), 1, 2);
		if(verticies[1].y<verticies[0].y)
			Collections.swap(Arrays.asList(this.verticies), 0, 1);
		
	}
	
	private void sortVerticiesHorizontal() {
		if(this.triangleClass!=Triangle.FLAT_BOTTOM||this.triangleClass!=Triangle.FLAT_TOP)
			return;
		if(this.triangleClass==Triangle.FLAT_TOP && this.verticies[1].x<this.verticies[0].x) 
				Collections.swap(Arrays.asList(this.verticies), 0, 1);
		if(this.triangleClass==Triangle.FLAT_BOTTOM && this.verticies[2].x<this.verticies[1].x) 
			Collections.swap(Arrays.asList(this.verticies), 1, 2);
	}
	
	public void update(Matrix3 rot, PubeSpace pst) {
		
		for(int i=0;i<verticies.length;++i) {
			verticies[i]=Matrix3.transform(verticies[i], rot);
		}
		this.normal=Vector3.crossProduct(new Vector3(this.verticies[1],this.verticies[0]), 
				new Vector3(this.verticies[2],this.verticies[0]));
		
		transform(pst);
		
		
	}
	
	public boolean isContained(Vertex3 p0) {
		double x0=p0.x;
		double y0=p0.y;

		double x1=transformed_verticies[0].x;
		double y1=transformed_verticies[0].y;
		double x2=transformed_verticies[1].x;
		double y2=transformed_verticies[1].y;
		double x3=transformed_verticies[2].x;
		double y3=transformed_verticies[2].y;
		//System.out.println(x1);
		double s1=((x1 - x0) * (y2 - y1) - (x2 - x1) * (y1 - y0));
		double s2=((x2 - x0) * (y3 - y2) - (x3 - x2) * (y2 - y0));
		double s3=((x3 - x0) * (y1 - y3) - (x1 - x3) * (y3 - y0));
		
		return (s1>=0 && s2>=0 && s3>=0) || (s1<=0 && s2<=0 && s3<=0);
		
	}
	
	public void render(Graphics2D g2, BufferedImage img) {
		cull();
		if(this.cullFlag)
		return;		
		
		int IMG_WIDTH=img.getWidth();
		int IMG_HEIGHT=img.getHeight();
		
		int TRIANGLE_MIN_Y=(int)Math.min(this.transformed_verticies[0].y, Math.min(this.transformed_verticies[1].y, this.transformed_verticies[2].y));
		int TRIANGLE_MAX_Y=(int) Math.max(this.transformed_verticies[0].y, Math.max(this.transformed_verticies[1].y, this.transformed_verticies[2].y));
		int TRIANGLE_MIN_X,  TRIANGLE_MAX_X;
		
	
		TRIANGLE_MIN_X=(int) Math.min(this.transformed_verticies[0].x, Math.min(this.transformed_verticies[1].x, this.transformed_verticies[2].x));
		TRIANGLE_MAX_X=(int) Math.max(this.transformed_verticies[0].x, Math.max(this.transformed_verticies[1].x, this.transformed_verticies[2].x));
		
		int MIN_X=Math.max(0,TRIANGLE_MIN_X);
		int MAX_X=Math.min(IMG_WIDTH,TRIANGLE_MAX_X);
		int MIN_Y=Math.max(0,TRIANGLE_MIN_Y);
		int MAX_Y=Math.min(IMG_WIDTH,TRIANGLE_MAX_Y);
		//System.out.println("TRIANGLE_MIN_X="+TRIANGLE_MIN_X+" TRIANGLE_MAX_X="+TRIANGLE_MAX_X);
		//System.out.println("TRIANGLE_MIN_Y="+MIN_Y+" TRIANGLE_MAX_Y="+MAX_Y);
		for(int y=MIN_Y; y<MAX_Y;++y) {
			for(int x=MIN_X; x<MAX_X;++x) {
				if(isContained(new Vertex3(x,y,0)))
				img.setRGB(x, y, this.color.getRGB());
			}
		}
	}
	
	public void logTriangle() {
		Vertex3 v1, v2, v3;
		v1=this.verticies[0];
		v2=this.verticies[1];
		v3=this.verticies[2];
		System.out.println("Класс: "+this.triangleClass+" v0=("+v1.x+";"+v1.y+");"
				+ "v1=("+v2.x+";"+v2.y+");"
						+"v2=("+v3.x+";"+v3.y+");");
	}
	

	public void cull() {
		Vector3 base1, base2;
		//base1=new Vector3(verticies[1],verticies[0]);
		//base2=new Vector3(verticies[2],verticies[0]);
		
		base1=new Vector3(verticies[0],verticies[1]);
		base2=new Vector3(verticies[0],verticies[2]);
		this.normal=Vector3.crossProduct(base2, base1);
		Vertex3 viewpoint=new Vertex3 (0,0,-1);
		Vector3 viewVector=new Vector3(viewpoint, this.verticies[0]);
		double flag=Vector3.dotProduct(viewVector,this.normal); 
		//System.out.println(flag);
		this.cullFlag=(flag>=0);
		
		if(this.cullFlag) {
			System.out.println("НЕ ПРОШЕЛ");
			logTriangle();
			}
		
	}
	
}

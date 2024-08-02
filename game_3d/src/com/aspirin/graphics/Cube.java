package com.aspirin.graphics;

import java.awt.Color;
import java.awt.Graphics2D;
import java.awt.image.BufferedImage;
import java.util.ArrayList;

import com.aspirin.utils.Matrix3;
import com.aspirin.utils.PubeSpace;



public class Cube {
	public double size, z_offset;
	public ArrayList<Vertex3> verticies, transformed_verticies;
	public Triangle[] mesh;


	//ПРОБЛЕМА В ТОМ, ЧТО У КУБА НЕТ РАЗДЕЛЕНИЯ НА ПРОСТРАНСТВА
	
	public Cube(double size, PubeSpace pst) {
		this.size=size;
		this.verticies=new ArrayList<Vertex3>();
		this.verticies.add(new Vertex3(-size,-size,-size));
		this.verticies.add(new Vertex3(size,-size,-size));
		this.verticies.add(new Vertex3(-size,size,-size));
		this.verticies.add(new Vertex3(size,size,-size));
		this.verticies.add(new Vertex3(-size,-size,size));
		this.verticies.add(new Vertex3(size,-size,size));
		this.verticies.add(new Vertex3(-size,size,size));
		this.verticies.add(new Vertex3(size,size,size));
		this.z_offset=0.4;
		this.transformed_verticies=new ArrayList<Vertex3>();
		//transform(pst);
		mesh=getTriangles(pst);
	
	}
	
	/*public void zOffset() {
		for(int i=0;i<transformed_verticies.size();++i) {
			transformed_verticies.get(i).z=this.z_offset;
		}
		
	}*/
	
	public Vertex2[] getIndicies() {
		return new Vertex2[]{new Vertex2(0,1), new Vertex2(1,3), new Vertex2(3,2), new Vertex2(2,0),
				new Vertex2(0,4), new Vertex2(1,5), new Vertex2(3,7), new Vertex2(2,6),
				new Vertex2(4,5), new Vertex2(5,7), new Vertex2(7,6), new Vertex2(6,4)};
	}
	//ОШИБКА ТУТ ИЛИ РАНЬШЕ ПО ЛАЙНУ
	public Triangle[] getTriangles(PubeSpace pst) {
		 Vertex3[] indicies={new Vertex3(0,2,1), new Vertex3(2,3,1), 
							new Vertex3(1,3,5), new Vertex3(3,7,4),
							new Vertex3(2,6,3), new Vertex3(3,6,7),
							new Vertex3(4,5,7), new Vertex3(4,7,6),
							new Vertex3(0,4,2), new Vertex3(2,4,6),
							new Vertex3(0,1,4), new Vertex3(1,5,4)};
		Vertex3[] orderedVerticies=new Vertex3[indicies.length*3];
					for (int i=0;i<indicies.length;i++) {
								int a,b,c;
								a=i*3;
								b=a+1;
								c=a+2;
								//System.out.println("i="+i+" a="+a+" b="+b+" c="+c);
								//ТУТ ВЫДАЕТСЯ НЕПРАВИЛЬНОЕ ПРОСТРАНСТВО
								orderedVerticies[a]=verticies.get((int)indicies[i].x);
								orderedVerticies[b]=verticies.get((int)indicies[i].y);
								orderedVerticies[c]=verticies.get((int)indicies[i].z);
		}
		Triangle[] res=new Triangle[orderedVerticies.length/3];
		//System.out.println("Количество вершин: "+orderedVerticies.length+" Количество треугольников "+res.length);
		for (int i=0;i<res.length;++i) {
			int a=i*3;
			int b=i*3+1;
			int c=i*3+2;
			//System.out.println("i="+i+" a="+a+" b="+b+" c="+c);
			//orderedVerticies[a].log();
			//orderedVerticies[b].log();
			//orderedVerticies[c].log();
			//System.out.println("========================================");
			res[i]=new Triangle(orderedVerticies[a],orderedVerticies[b],orderedVerticies[c], Color.WHITE, pst, this.z_offset);
			//res[i].logTriangle();
		}
		return res;
	}
	
	
	
	public void transform(PubeSpace pst) {
		transformed_verticies.clear();
		for(Vertex3 v:this.verticies) {
			transformed_verticies.add(pst.Transform(v, this.z_offset));
		}
		
	}
	
	public void update(Matrix3 rot, PubeSpace pst) {
		
		for(int i=0;i<verticies.size();++i) {
			verticies.set(i, Matrix3.transform(verticies.get(i), rot));
		}
		//transform(pst);
		mesh=getTriangles(pst);

	
	}
	
	public void render(Graphics2D g2, BufferedImage img, PubeSpace pst) {
		for(Triangle t:mesh) {
			t.cull();
			t.transform(pst);
			t.render(g2, img);
		}
	}
	
	
}

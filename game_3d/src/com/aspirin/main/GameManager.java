package com.aspirin.main;

import java.awt.Color;
import java.awt.Graphics2D;
import java.awt.image.BufferedImage;
import java.util.ArrayList;

import com.aspirin.graphics.Cube;
import com.aspirin.graphics.Triangle;
import com.aspirin.graphics.Vertex2;
import com.aspirin.graphics.Vertex3;
import com.aspirin.utils.Matrix3;
import com.aspirin.utils.PubeSpace;
import com.aspirin.utils.Timer;
import com.aspirin.window.Window;

public class GameManager implements Runnable{

	
	
	//КАДРЫ
	public static final float PREFFERD_FPS=60.0f;
	public static final float FRAME_TIME=Timer.SECOND/PREFFERD_FPS;
	
	//ТАКТЫ
	public static final float PREFERED_TICK=20.0f;
	public static final float TICK_TIME=Timer.SECOND/PREFERED_TICK;
	
	private long START_TIME, CURRENT_TIME, LAST_TIME,ELAPSED_TIME, TO_SEC;
	
	private boolean isAlive;
	
	private int FPS, TICKS;
	
	private Window window;
	private Thread gameThread;
	private PubeSpace pst;
		
	//private Cube cube;
	
	private Triangle tri;
	
	public GameManager(Window window) {
		
		this.LAST_TIME=START_TIME;
		this.CURRENT_TIME=START_TIME;
		this.ELAPSED_TIME=0;
				
		FPS=0;
		TICKS=0;
		TO_SEC=0;
		
		this.window=window;
		this.pst=new PubeSpace(Window.DEFAULT_WIDTH, Window.DEFAULT_HEIGHT); 
		
		//cube=new Cube(0.1, pst);
		//cube.mesh=cube.getTriangles(pst);
		//mesh=cube.getTriangleMesh(pst);
		/*for(Triangle t:mesh) {
			t.logTriangle();
		}*/
		
		tri=new Triangle(new Vertex3(0.1,0.1,0), new Vertex3(0.3,0.1,0), new Vertex3(0.3,0,0), Color.RED, pst, 1);
		
		isAlive=false;
		}
	
	
	public synchronized void Start() {
		if(isAlive)
			return;
		isAlive=true;
		gameThread=new Thread(this);
		gameThread.start();
		
		this.START_TIME=Timer.getNow();
		
		
		
	}
	
	public synchronized void Stop() {
		if(!isAlive)
			return;
		
		isAlive=false;
		try {
			gameThread.join();
		} catch (InterruptedException e) {
			// TODO Auto-generated catch block
			e.printStackTrace();
		}
		
	}
	
	@Override
	public void run() {
		
		
		float UPDATES_TO_MAKE=0, RENDERS_TO_MAKE=0;
		
		this.LAST_TIME=Timer.getNow();
		while (isAlive) {
			this.CURRENT_TIME=Timer.getNow();
			this.ELAPSED_TIME=this.CURRENT_TIME-this.LAST_TIME;
			
			this.LAST_TIME=this.CURRENT_TIME;
			
			
			UPDATES_TO_MAKE+=(this.ELAPSED_TIME/GameManager.TICK_TIME);
			RENDERS_TO_MAKE+=(this.ELAPSED_TIME/GameManager.FRAME_TIME);
			
			
			while(UPDATES_TO_MAKE>1) {
				update();
				UPDATES_TO_MAKE--;
				
			}
			
			while(RENDERS_TO_MAKE>1) {
				render();
				RENDERS_TO_MAKE--;
				
			}
				if(RENDERS_TO_MAKE<=1 && UPDATES_TO_MAKE<=1)
			{
				try {
					Thread.sleep(1);
					
				} catch (InterruptedException e) {
					// TODO Auto-generated catch block
					e.printStackTrace();
				}
			}
			
			this.TO_SEC+=ELAPSED_TIME;
			
			if(this.TO_SEC>=Timer.SECOND) {
				window.setTitle(Window.DEFAULT_TITLE+" | FPS: "+FPS+" TICK: "+TICKS);
				this.TO_SEC=0;
				this.FPS=0;
				this.TICKS=0;
			}
	}	
		
	}
	Matrix3 zrot=Matrix3.createYRotMat(Math.toRadians(1));

	public void update() {
		//ПРОПИСАТЬ ВСЕ АПДЕЙТЫ ТУТ
		//cube.update(zrot,pst);
		tri.update(zrot, pst);
		TICKS+=1;
	}
	

	
	public void render() {
		Graphics2D g2=window.getGraphics();
		BufferedImage b=window.getImage();
		Window.clear();
		//RENDER-START
		g2.setColor(Color.WHITE);
		
		tri.render(g2, b);
		//cube.render(g2, b, pst);
		
		
		//RENDER-STOP
		Window.swapBuffers();
		
		FPS+=1;
	}
	
	public void clear(Graphics2D g2) {
		g2.clearRect(0, 0, window.getWidth(), window.getHeight());
	}
	
	public int getFPS() {
		return this.FPS;
	}
	
	public int getTicks() {
		return this.TICKS;
	}
}

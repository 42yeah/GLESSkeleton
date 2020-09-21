package me.ftyeah.skelet;
import android.content.*;
import android.opengl.*;

public class Game
{
	public Game(Context ctx, GLSurfaceView surfaceView) {
		this.ctx = ctx;
		this.renderer = new GameRenderer(ctx);
		this.surfaceView = surfaceView;
	}
	
	public void start() {
		this.surfaceView.setEGLContextClientVersion(2);
		this.surfaceView.setRenderer(renderer);
	}
	
	public Context ctx;
	public GameRenderer renderer;
	public GLSurfaceView surfaceView;

	public static String identifier = "SKELET_APP";
}


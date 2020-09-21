package me.ftyeah.skelet;
import android.content.*;
import android.opengl.*;
import java.nio.*;
import javax.microedition.khronos.opengles.*;
import java.io.*;
import android.util.*;

class gl extends GLES20 {} // Alias

public class GameRenderer implements GLSurfaceView.Renderer
{
	public GameRenderer(Context ctx) {
		this.ctx = ctx;
		this.callback = null;
	}

	public void setCallback(RenderCallback callback)
	{
		this.callback = callback;
	}

	@Override
	public void onSurfaceCreated(GL10 p1, javax.microedition.khronos.egl.EGLConfig p2)
	{
		gl.glClearColor(1.0f, 0.0f, 0.0f, 1.0f);
		setupVertices();
		setupProgram();
	}

	@Override
	public void onSurfaceChanged(GL10 p1, int p2, int p3)
	{
	}

	@Override
	public void onDrawFrame(GL10 p1)
	{
		gl.glClear(gl.GL_COLOR_BUFFER_BIT);
		
		gl.glUseProgram(program);
		int aPosLoc = attrib("aPos");
		gl.glEnableVertexAttribArray(aPosLoc);
		// gl.glVertexAttribP
		gl.glVertexAttribPointer(aPosLoc, 2, gl.GL_FLOAT, false, 4 * 2, vbo);
		gl.glDrawArrays(gl.GL_TRIANGLES, 0, 3);
		
		if (callback != null) {
			callback.callback();
		}
	}
	
	private void setupVertices() {
		float[] array = {
			-1.0f, -1.0f,
			1.0f, -1.0f,
			1.0f, 1.0f
		};
		ByteBuffer bb = ByteBuffer.allocateDirect(4 * array.length);
		bb.order(ByteOrder.nativeOrder());
		FloatBuffer fb = bb.asFloatBuffer();
		fb.put(array);
		fb.position(0);
		vbo = fb;
	}
	
	private void setupProgram() {
		int program = gl.glCreateProgram();
		int vs = loadShader(gl.GL_VERTEX_SHADER, "v.glsl");
		int fs = loadShader(gl.GL_FRAGMENT_SHADER, "f.glsl");
		gl.glAttachShader(program, vs);
		gl.glAttachShader(program, fs);
		gl.glLinkProgram(program);
		String log = gl.glGetProgramInfoLog(program);
		if (log.trim().length() > 0) {
			Log.e(Game.identifier, log);
		} else {
			Log.i(Game.identifier, "Program successfully compiled.");
		}
		this.program = program;
	}
	
	private int loadShader(int type, String name) {
		int shader = gl.glCreateShader(type);
		String content = loadRes(name);
		gl.glShaderSource(shader, content);
		gl.glCompileShader(shader);
		String log = gl.glGetShaderInfoLog(shader);
		if (log.trim().length() > 0) {
			Log.e(Game.identifier, log);
		} else {
			Log.i(Game.identifier, "Shader successfully compiled: " + name);
		}
		return shader;
	}
	
	private String loadRes(String name) {
		try
		{
			InputStream is = ctx.getAssets().open(name);
			ByteArrayOutputStream baos = new ByteArrayOutputStream();
			while (is.available() > 0) {
				byte[] b = new byte[1024];
				int len = is.read(b, 0, b.length);
				if (len < 0) {
					break;
				}
				baos.write(b, 0, len);
			}
			return baos.toString();
		}
		catch (IOException e)
		{
			Log.e(Game.identifier, "Failed to load resources: " + name);
			return "";
		}
	}
	
	private int locate(String uniform) {
		return gl.glGetUniformLocation(program, uniform);
	}
	
	private int attrib(String attribute) {
		return gl.glGetAttribLocation(program, attribute);
	}
	
	public abstract class RenderCallback {
		public void draw(float x, float y, int id) {
			// TODO
		}
		
		public abstract void callback();
	}

	private int program;
	private FloatBuffer vbo;
	private Context ctx;
	private RenderCallback callback;
}

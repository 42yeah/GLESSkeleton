package me.ftyeah.skelet;

import android.app.*;
import android.os.*;
import android.opengl.*;
import android.widget.*;

public class MainActivity extends Activity 
{
    @Override
    protected void onCreate(Bundle savedInstanceState)
    {
        super.onCreate(savedInstanceState);
        setContentView(R.layout.main);
		
		GLSurfaceView surf = new GLSurfaceView(this);
		LinearLayout ll = findViewById(R.id.mainLinearLayout1);
		ll.addView(surf);
		
		Game gameObject = new Game(this, surf);
		gameObject.start();
    }
}

package com.example.usgviewer;

import java.io.File;
import java.io.IOException;

import android.app.Activity;
import android.content.Intent;
import android.graphics.drawable.BitmapDrawable;
import android.graphics.drawable.Drawable;
import android.os.AsyncTask;
import android.os.Bundle;
import android.view.Menu;
import android.view.MenuItem;
import android.view.View;
import android.view.View.OnClickListener;
import android.widget.Button;
import android.widget.ImageView;
import android.widget.TextView;
import android.widget.Toast;

/* Obtain image path from intent bundle, load image and create 
 * graphical interface elements - time-slider, etc.
 */

public class ImageViewActivity extends Activity {

	private ImageView usgImgView;
	private RFImage rfImg;
	private TextView frameNoText;
	private Button prevFrameButton;
	private Button nextFrameButton;

    @Override
    public void onCreate(Bundle savedInstanceState) {
        super.onCreate(savedInstanceState);
        setContentView(R.layout.activity_image_view);
        
        Intent intent = getIntent();
        Bundle bundle = intent.getExtras();
        
        rfImg = new RFImage(new File(bundle.getString("IMG_FILENAME")));

        try {
        	rfImg.createHeaders();
	        //Drawable frameDrawable = rfImg.getImg();
	        usgImgView = (ImageView) findViewById(R.id.imageView1);
	        //usgImgView.setBackgroundDrawable(frameDrawable);
	        usgImgView.setScaleType(ImageView.ScaleType.FIT_CENTER);
	        new LoadImageTask().execute(1);
        } catch (IOException e) {
        	Toast.makeText(this, e.getMessage(), Toast.LENGTH_LONG).show();
        }
        
        frameNoText = (TextView) findViewById(R.id.frameNoText);
        updateFrameNumTextView();
        
        prevFrameButton = (Button) findViewById(R.id.prevFrameButton);
        nextFrameButton = (Button) findViewById(R.id.nextFrameButton);
        
        prevFrameButton.setOnClickListener(new OnClickListener() {
        	public void onClick(View view) {
        		int prevFrame = rfImg.getCurrentFrame() - 1;
        		new LoadImageTask().execute(prevFrame);
        		updateFrameNumTextView(prevFrame);
        	}
        });
        
        nextFrameButton.setOnClickListener(new OnClickListener() {
        	public void onClick(View view) {
        		int nextFrame = rfImg.getCurrentFrame() + 1;
        		new LoadImageTask().execute(nextFrame);
        		updateFrameNumTextView(nextFrame);
        	}
        });
    }

    public void setDrawableToView(Drawable frameDrawable, ImageView imgView) {
    	imgView.setBackgroundDrawable(frameDrawable);
    }
    
    public void updateImgView(int frame) {
    	try {
	    	Drawable frameDrawable = rfImg.getAtFrame(frame);
	    	setDrawableToView(frameDrawable, usgImgView);
    	} catch (IOException e) {
    		Toast.makeText(this, "Error while loading frame", Toast.LENGTH_LONG).show();
    	}
    }
    
    public void updateFrameNumTextView() {
    	frameNoText.setText("Frame " + rfImg.getCurrentFrame() + " of " + rfImg.getFrames());
    }
    
    public void updateFrameNumTextView(int frame) {
    	frameNoText.setText("Frame " + frame + " of " + rfImg.getFrames());
    }
    
    @Override
    public boolean onCreateOptionsMenu(Menu menu) {
        getMenuInflater().inflate(R.menu.activity_image_view, menu);
        return true;
    }
    
    @Override
    public boolean onOptionsItemSelected(MenuItem item) {
    	if (item.isChecked()) { 
    		return super.onOptionsItemSelected(item);
    	} else {
	        switch (item.getItemId()) {
	            case R.id.optionBlueMap:
	            	item.setChecked(true);
	            	rfImg.setCm(ColorMap.Blue);
	                break;
	            case R.id.optionGrayMap:
	            	item.setChecked(true);
	            	rfImg.setCm(ColorMap.Grayscale);
	            	break;
	        }
	        new LoadImageTask().execute(rfImg.getCurrentFrame());
    	}
        return super.onOptionsItemSelected(item);
    }
    
	class LoadImageTask extends AsyncTask<Integer, Void, Boolean> {
		private Drawable loadedDrawable;
		public Boolean doInBackground(Integer... frames) {
			try {
				loadedDrawable = rfImg.getAtFrame(frames[0]);
			} catch (IOException e) {
				return false;
			}
			return true;
		}
		
		public void onPostExecute(Boolean result) {
			usgImgView.setBackgroundDrawable(loadedDrawable);
			usgImgView.invalidate();
		}
	}
}

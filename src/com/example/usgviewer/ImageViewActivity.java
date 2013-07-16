package com.example.usgviewer;

import java.io.File;
import java.io.IOException;

import android.app.Activity;
import android.content.Intent;
import android.graphics.Bitmap;
import android.graphics.drawable.BitmapDrawable;
import android.os.Bundle;
import android.view.Menu;
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
	private int currentFrame;
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
	        Bitmap initialFrame = rfImg.getImg();
	        BitmapDrawable frameDrawable = new BitmapDrawable(initialFrame);
	        usgImgView = (ImageView) findViewById(R.id.imageView1);
	        //usgImgView.setImageBitmap(initialFrame);
	        usgImgView.setBackgroundDrawable(frameDrawable);
	        //usgImgView.setMaxHeight(50);
	        usgImgView.setScaleType(ImageView.ScaleType.FIT_CENTER);
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
        		updateImgView(prevFrame);
        		updateFrameNumTextView();
        	}
        });
        
        nextFrameButton.setOnClickListener(new OnClickListener() {
        	public void onClick(View view) {
        		int nextFrame = rfImg.getCurrentFrame() + 1;
        		updateImgView(nextFrame);
        		updateFrameNumTextView();
        	}
        });
    }

    public void setBitmapToView(Bitmap bmp, ImageView imgView) {
    	BitmapDrawable frameDrawable = new BitmapDrawable(bmp);
    	imgView.setBackgroundDrawable(frameDrawable);
    }
    
    public void updateImgView(int frame) {
    	try {
	    	Bitmap bmp = rfImg.getAtFrame(frame);
	    	setBitmapToView(bmp, usgImgView);
    	} catch (IOException e) {
    		Toast.makeText(this, "Error while loading frame", Toast.LENGTH_LONG).show();
    	}
    }
    
    public void updateFrameNumTextView() {
    	frameNoText.setText("Frame " + rfImg.getCurrentFrame() + " of " + rfImg.getFrames());
    }
    
    @Override
    public boolean onCreateOptionsMenu(Menu menu) {
        getMenuInflater().inflate(R.menu.activity_image_view, menu);
        return true;
    }
}

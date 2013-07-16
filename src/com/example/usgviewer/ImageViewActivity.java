package com.example.usgviewer;

import java.io.File;
import java.io.IOException;

import android.app.Activity;
import android.content.Intent;
import android.graphics.Bitmap;
import android.graphics.drawable.BitmapDrawable;
import android.os.Bundle;
import android.view.Menu;
import android.widget.ImageView;
import android.widget.TextView;
import android.widget.Toast;

/* Obtain image path from intent bundle, load image and create 
 * graphical interface elements - time-slider, etc.
 */

public class ImageViewActivity extends Activity {

	private ImageView usgImgView;
	private TextView frameNoText;

    @Override
    public void onCreate(Bundle savedInstanceState) {
        super.onCreate(savedInstanceState);
        setContentView(R.layout.activity_image_view);
        
        Intent intent = getIntent();
        Bundle bundle = intent.getExtras();
        
        RFImage rfImg = new RFImage(new File(bundle.getString("IMG_FILENAME")));

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
        frameNoText.setText("Frame " + rfImg.getCurrentFrame() + " of " + rfImg.getFrames());
        
    }

    @Override
    public boolean onCreateOptionsMenu(Menu menu) {
        getMenuInflater().inflate(R.menu.activity_image_view, menu);
        return true;
    }
}

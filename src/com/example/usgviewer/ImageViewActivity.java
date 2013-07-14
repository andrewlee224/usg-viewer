package com.example.usgviewer;

import java.io.File;
import java.io.IOException;

import android.app.Activity;
import android.content.Intent;
import android.graphics.Bitmap;
import android.os.Bundle;
import android.view.Menu;
import android.widget.ImageView;
import android.widget.Toast;

/* Obtain image path from intent bundle, load image and create 
 * graphical interface elements - time-slider, etc.
 */

public class ImageViewActivity extends Activity {

	private ImageView usgImgView;

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
	        usgImgView = (ImageView) findViewById(R.id.imageView1);
	        usgImgView.setImageBitmap(initialFrame);
        } catch (IOException e) {
        	Toast.makeText(this, e.getMessage(), Toast.LENGTH_LONG).show();
        }
        
    }

    @Override
    public boolean onCreateOptionsMenu(Menu menu) {
        getMenuInflater().inflate(R.menu.activity_image_view, menu);
        return true;
    }
}

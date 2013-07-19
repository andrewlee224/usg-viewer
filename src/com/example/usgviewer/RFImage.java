package com.example.usgviewer;

import java.io.DataInputStream;
import java.io.File;
import java.io.IOException;
import java.util.ArrayList;
import java.util.List;

import android.graphics.Bitmap;
import android.graphics.ColorMatrix;
import android.graphics.ColorMatrixColorFilter;
import android.graphics.drawable.BitmapDrawable;
import android.graphics.drawable.Drawable;
import android.os.AsyncTask;
import android.widget.ImageView;

public class RFImage implements TimeViewable {

	private DataInputStream ds;
	private List<Integer> headers = new ArrayList<Integer>();
	private int[] intFrame1d;
	private ColorMap cm = ColorMap.Blue;
	
	private int width;
	private int height;
	private int frames;
	private int currentFrame;

	public RFImage(File file) {
		DataStreamLoader dsl = new DataStreamLoader(file, 4);
		ds = dsl.getStream(); 
	}

	public void createHeaders() throws IOException {
		for(int i = 0; i < 19; i++) {
			headers.add(Integer.reverseBytes(ds.readInt()));
		}
		
		width = headers.get(2);
		height = headers.get(3);
		frames = headers.get(1);
		ds.skipBytes(4);
		currentFrame = 1;
		//frame1d = new short[width*height];
		intFrame1d = new int[width*height];
	}
	
	public Drawable getImg() throws IOException {
		return getAtFrame(1);
	}
	
	public Drawable getAtFrame(int prefFrame) throws IOException {
		if (prefFrame > frames) return null;
		
		int frameDiff = prefFrame - currentFrame;
		ds.skipBytes(frameDiff*(4 + width*height*2));
		currentFrame = prefFrame;
		
		for(int i = 0; i < getFrameSize(); i++) {
			//frame1d[i] = Short.reverseBytes(ds.readShort());
			int val = Short.reverseBytes(ds.readShort());
			switch(cm) {
			case Blue:
				intFrame1d[i] = Math.abs((val/256));
				break;
			case Grayscale:
				intFrame1d[i] = Math.abs(val);
			}
		}
		
		//convert short[] to int[]
		//byte[] byteFrame1d = new byte[frame1d.length];

		Bitmap bmp = Bitmap.createBitmap(intFrame1d, height, width, Bitmap.Config.RGB_565);
		Drawable frameDrawable = new BitmapDrawable(bmp);
		
		switch(cm) {
		case Grayscale:
			frameDrawable = convertToGrayscale(frameDrawable);
		}
		return frameDrawable;
	}
	
	private Drawable convertToGrayscale(Drawable drawable)
	{
	    ColorMatrix matrix = new ColorMatrix();
	    matrix.setSaturation(0);

	    ColorMatrixColorFilter filter = new ColorMatrixColorFilter(matrix);

	    drawable.setColorFilter(filter);

	    return drawable;
	}
	
	public int getWidth() {
		return width;
	}

	public int getHeight() {
		return height;
	}

	public int getFrames() {
		return frames;
	}
	
	public int getFrameSize() {
		return height*width;
	}
	
	public int getCurrentFrame() {
		return currentFrame;
	}
	
	public void setCurrentFrame(int frame) {
		currentFrame = frame;
	}

	public ColorMap getCm() {
		return cm;
	}

	public void setCm(ColorMap cm) {
		this.cm = cm;
	}
}

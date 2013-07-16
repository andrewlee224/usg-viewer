package com.example.usgviewer;

import java.io.DataInputStream;
import java.io.File;
import java.io.IOException;
import java.util.ArrayList;
import java.util.List;

import android.graphics.Bitmap;

public class RFImage implements TimeViewable {

	private DataInputStream ds;
	private List<Integer> headers = new ArrayList<Integer>();
	private short[] frame1d;
	
	private int width;
	private int height;
	private int frames;
	private int transmitFreq;
	private int lineDensity;
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
		transmitFreq = headers.get(14);
		lineDensity = headers.get(17);
		ds.skipBytes(4);
		currentFrame = 1;
		//frame = new short[width][height];
		frame1d = new short[width*height];
	}
	
	public Bitmap getImg() throws IOException {
		return getAtFrame(9);
	}
	
	public Bitmap getAtFrame(int prefFrame) throws IOException {
		if (prefFrame > frames) return null;
		
		int frameDiff = prefFrame - currentFrame;
		ds.skipBytes(frameDiff*(4 + width*height*2));
		currentFrame = prefFrame;
		
		for(int i = 0; i < getFrameSize(); i++) {
			//int col = (int) (i/4);
			//int row = i%4;
			
			//frame[col][row] = Short.reverseBytes(ds.readShort());
			frame1d[i] = Short.reverseBytes(ds.readShort());
		}
		
		//convert short[] to int[]
		int[] intFrame1d = new int[frame1d.length];
		//byte[] byteFrame1d = new byte[frame1d.length];

		for (int i = 0; i < frame1d.length; i++) {
			intFrame1d[i] = Math.abs(((frame1d[i]))/256);
		}

		Bitmap bmp = Bitmap.createBitmap(intFrame1d, height, width, Bitmap.Config.RGB_565);
		
		return bmp;
	}
	
	public double calcTGC(int frameDepth, int originalValue) {
		double depthCm = frameDepth/lineDensity;
		double TGCalpha = 0.25; 	// [dB / MHz*cm]
		
		double correctedValue = originalValue * Math.exp(-TGCalpha * transmitFreq * depthCm);
		
		return correctedValue;
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


}

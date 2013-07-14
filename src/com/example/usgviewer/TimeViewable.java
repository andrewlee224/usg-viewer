package com.example.usgviewer;

import java.io.IOException;

import android.graphics.Bitmap;

public interface TimeViewable extends Viewable {
	Bitmap getAtFrame(int frame) throws IOException;
}

package com.example.usgviewer;

import java.io.IOException;

import android.graphics.drawable.Drawable;

public interface TimeViewable extends Viewable {
	Drawable getAtFrame(int frame) throws IOException;
}

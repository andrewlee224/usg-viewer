package com.example.usgviewer;

import java.io.IOException;

import android.graphics.Bitmap;

public interface Viewable {
	Bitmap getImg() throws IOException;
}

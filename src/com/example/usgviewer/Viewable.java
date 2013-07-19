package com.example.usgviewer;

import java.io.IOException;

import android.graphics.drawable.Drawable;

public interface Viewable {
	Drawable getImg() throws IOException;
}

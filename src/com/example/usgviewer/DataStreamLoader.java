package com.example.usgviewer;

import java.io.BufferedInputStream;
import java.io.DataInputStream;
import java.io.File;
import java.io.FileInputStream;
import java.io.IOException;
import java.io.InputStream;

import android.os.Environment;

public class DataStreamLoader {
	
	private DataInputStream ds;
	
	public DataStreamLoader(File path, int chunkSize) {
			
		if (!path.isAbsolute()) {
			path = new File(Environment.getExternalStorageDirectory() +
				"//signals//" + path);
		}
		
		try {
			InputStream is = new FileInputStream(path);
			ds = new DataInputStream(new BufferedInputStream(is, chunkSize));
		} catch (IOException e) {
			
		}

	}
	
	public DataStreamLoader(File path) {
		this(path, 1);
	}
	
	
	public DataInputStream getStream() {

		return ds;
	}
}

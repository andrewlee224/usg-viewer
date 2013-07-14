package com.example.usgviewer;

import java.io.File;
import java.io.FilenameFilter;

import android.content.Context;
import android.os.Environment;
import android.util.Log;
import android.view.View;
import android.widget.AdapterView;
import android.widget.AdapterView.OnItemClickListener;
import android.widget.ArrayAdapter;
import android.widget.ListView;
import android.widget.TextView;
import android.widget.Toast;

public class FileChooser {
	private String[] fileList;
	private File path = new File(Environment.getExternalStorageDirectory() + "//signals//");
	private String chosenFile;
	private static final String FTYPE = ".rf";
	private TextView chosenFileLabel;
	
	private OnItemClickListener fileClickedHandler = new OnItemClickListener() {
	    public void onItemClick(AdapterView<?> parent, View v, int position, long id) {
	        chosenFile = (String) parent.getItemAtPosition(position);
	        chosenFileLabel.setText(chosenFile);
	    }
	};
	
	
	public FileChooser(Context context, ListView listView) {
		loadFileList();
		if (fileList.length == 0) {
			Toast.makeText(context, "No files found in directory!", Toast.LENGTH_SHORT);
		} else {
			ArrayAdapter<String> adapter = new ArrayAdapter<String>(context, 
			        android.R.layout.simple_list_item_1, fileList);
			listView.setAdapter(adapter);
			listView.setOnItemClickListener(fileClickedHandler);
		}
	}
	
	private void loadFileList() {
	    try {
	        path.mkdirs();
	    }
	    catch(SecurityException e) {
	        Log.e(this.toString(), "unable to write on the sd card " + e.toString());
	    }
	    if(path.exists()) {
	        FilenameFilter filter = new FilenameFilter() {
	            public boolean accept(File dir, String filename) {
	                return filename.contains(FTYPE);
	            }
	        };
	        fileList = path.list(filter);
	    }
	    else {
	        fileList= new String[0];
	    }
	}
	
	public String getChosenFileString() {
		return chosenFile;
	}
	
	public File getFile() {
		return new File(path.toString() + chosenFile); 
	}

	public File getPath() {
		return path;
	}
	
	public void setChosenFileTextView(TextView label) {
		chosenFileLabel = label;
	}
	
}

package com.example.usgviewer;

import android.app.Activity;
import android.content.Intent;
import android.os.Bundle;
import android.view.Menu;
import android.view.View;
import android.view.View.OnClickListener;
import android.widget.Button;
import android.widget.ListView;
import android.widget.TextView;

//TODO: file list spinner

public class MainActivity extends Activity {

	private ListView fileListView;
	private TextView fileChosenTextView;
	private Button chooseFileButton;
	private FileChooser fc;
	private Intent intent;
	
    @Override
    public void onCreate(Bundle savedInstanceState) {
        super.onCreate(savedInstanceState);
        setContentView(R.layout.activity_main);
        fileListView = (ListView) findViewById(R.id.fileListView);
        fileChosenTextView = (TextView) findViewById(R.id.fileChosenTextView);
        chooseFileButton = (Button) findViewById(R.id.chooseFileButton);
        chooseFileButton.setOnClickListener(new OnClickListener() {
        	public void onClick(View view) {
        		onChooseFileClick();
        	}
        });
        setFileListContents();
        
        intent = new Intent(this, ImageViewActivity.class);
    }

    @Override
    public boolean onCreateOptionsMenu(Menu menu) {
        getMenuInflater().inflate(R.menu.activity_main, menu);
        return true;
    }
    
    private void setFileListContents() {
    	fc = new FileChooser(this, fileListView);
    	fc.setChosenFileTextView(fileChosenTextView);
    }
    
    private void onChooseFileClick() {
    	Bundle bundle = new Bundle();
    	bundle.putString("IMG_FILENAME", fc.getChosenFileString());
    	intent.putExtras(bundle);
    	startActivity(intent);
    }
}

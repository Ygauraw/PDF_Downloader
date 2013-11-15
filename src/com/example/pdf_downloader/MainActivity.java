package com.example.pdf_downloader;

import java.util.List;

import org.xml.sax.Parser;

import android.os.Bundle;
import android.app.Activity;
import android.app.AlertDialog;
import android.util.Log;
import android.view.Menu;
import android.view.View;
import android.widget.Button;
import android.widget.EditText;
import android.widget.ProgressBar;

public class MainActivity extends Activity {

	public static String logTag = "PDF_Downloader";
	
	private Button   goToUrlBtn  = null;
	private EditText urlTextEdit = null;
	private ProgressBar progressBar = null;

	
	@Override
	protected void onCreate(Bundle savedInstanceState) {
		super.onCreate(savedInstanceState);
		
		setContentView(R.layout.activity_main);
		
		goToUrlBtn  = (Button) findViewById(R.id.goToUrlBtnId);
		urlTextEdit = (EditText) findViewById(R.id.urlTextEditId);
		progressBar = (ProgressBar) findViewById(R.id.webPageDownloadProgressBarId);
		
		goToUrlBtn.setOnClickListener(new View.OnClickListener() {
			
			@Override
			public void onClick(View v) {
				String urlString = urlTextEdit.getText().toString();
				Log.d(logTag, "The URL is: " + urlString);
				
				disableUIComponents();
				
				AsyncDownloader downloader = new AsyncDownloader(progressBar, true);
				downloader.execute(urlString);
				
				UrlParser parser = new UrlParser();
				List<String> matches = parser.parse();
				
				if ( matches.size() > 0) {
					Log.d(logTag, "Number of matches is " + matches.size());
					// Deploy new Activity
				} else {
					// 1. Instantiate an AlertDialog.Builder with its constructor
					AlertDialog.Builder builder = new AlertDialog.Builder(MainActivity.this);

					// 2. Chain together various setter methods to set the dialog characteristics
					builder.setMessage("LOL")
					       .setTitle("DUPA");

					// 3. Get the AlertDialog from create()
					AlertDialog dialog = builder.create();
					Log.d(logTag, "No matches, do nothing");
				}
				
				enableUIComponents();
				
			}
		});
		
	}
	
	@Override
	public boolean onCreateOptionsMenu(Menu menu) {
		// Inflate the menu; this adds items to the action bar if it is present.
		getMenuInflater().inflate(R.menu.main, menu);
		return true;
	}

	private void disableUIComponents(){
		goToUrlBtn.setEnabled(false);
		urlTextEdit.setEnabled(false);	
	}
	
	private void enableUIComponents(){
		goToUrlBtn.setEnabled(true);
		urlTextEdit.setEnabled(true);	
	}
}

package com.example.pdf_downloader;

import java.util.List;

import org.xml.sax.Parser;

import android.os.Bundle;
import android.app.Activity;
import android.app.AlertDialog;
import android.app.Dialog;
import android.app.ProgressDialog;
import android.content.Context;
import android.util.Log;
import android.view.Menu;
import android.view.View;
import android.widget.Button;
import android.widget.EditText;
import android.widget.ProgressBar;

public class MainActivity extends Activity {

	public static String logTag = "PDF_Downloader";
	public static final int progress_bar_type = 0;
	
	private Button   goToUrlBtn  = null;
	private EditText urlTextEdit = null;
	private ProgressDialog pDialog = null;
	private Context ctx;
	
	@Override
	protected void onCreate(Bundle savedInstanceState) {
		super.onCreate(savedInstanceState);
		
		ctx = this;
		
		setContentView(R.layout.activity_main);
		
		goToUrlBtn  = (Button) findViewById(R.id.goToUrlBtnId);
		urlTextEdit = (EditText) findViewById(R.id.urlTextEditId);
		
		goToUrlBtn.setOnClickListener(new View.OnClickListener() {
			
			@Override
			public void onClick(View v) {
				String urlString = urlTextEdit.getText().toString();
				Log.d(logTag, "The URL is: " + urlString);
				
				disableUIComponents();
				
				AsyncDownloader downloader = new AsyncDownloader(pDialog, true, MainActivity.this, ctx);
				downloader.execute(urlString);
				
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

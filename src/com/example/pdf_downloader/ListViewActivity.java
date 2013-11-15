package com.example.pdf_downloader;

import java.util.ArrayList;
import java.util.Arrays;

import android.app.Activity;
import android.app.ProgressDialog;
import android.content.Context;
import android.content.Intent;
import android.os.Bundle;
import android.util.Log;
import android.view.View;
import android.widget.AdapterView;
import android.widget.ArrayAdapter;
import android.widget.ListView;
import android.widget.TextView;

public class ListViewActivity extends Activity {
  
  public static final int progress_bar_type = 0;
	
  private String[] links;
  private ListView mainListView ;
  private ArrayAdapter<String> listAdapter ;
  private ProgressDialog pDialog = null;  
  private Context ctx;
  
  @Override
  public void onCreate(Bundle savedInstanceState) {
    super.onCreate(savedInstanceState);
    
    ctx = this;
    
    setContentView(R.layout.activity_list_view);
    setTitle("PDF links");
    
    Intent intent = getIntent();
    String[] links = intent.getStringArrayExtra("links");
    
    // Find the ListView resource. 
    mainListView = (ListView) findViewById( R.id.mainListView );

    ArrayList<String> planetList = new ArrayList<String>();
    planetList.addAll( Arrays.asList(links) );
    
    // Create ArrayAdapter using the planet list.
    listAdapter = new ArrayAdapter<String>(this, R.layout.simplerow, planetList);
       
    // Set the ArrayAdapter as the ListView's adapter.
    mainListView.setAdapter( listAdapter );  
    
    mainListView.setOnItemClickListener(new AdapterView.OnItemClickListener() {
		@Override
		public void onItemClick(AdapterView<?> parent, final View view, int position, long id) {
			AsyncDownloader downloader = new AsyncDownloader(pDialog, false, ListViewActivity.this, ctx);
			String url = (String) ((TextView)mainListView.getChildAt(position)).getText();
			Log.d(MainActivity.logTag, url);
			downloader.execute(url);
		}    
    });
  }
}
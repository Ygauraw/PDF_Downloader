package com.example.pdf_downloader;

import java.io.BufferedInputStream;
import java.io.File;
import java.io.FileOutputStream;
import java.io.InputStream;
import java.io.OutputStream;
import java.net.URL;
import java.net.URLConnection;
import java.util.List;

import android.app.Activity;
import android.app.AlertDialog;
import android.app.ProgressDialog;
import android.content.Context;
import android.content.Intent;
import android.net.Uri;
import android.os.AsyncTask;
import android.os.Environment;
import android.util.Log;
import android.widget.ProgressBar;


public class AsyncDownloader extends AsyncTask<String, String, String> {

	public static final int progress_bar_type = 0; 
	
	private ProgressDialog pDialog;
	private Activity mainActivity;
	private Boolean saveAsTmpFile;
	private String downloadedFile;
	private Context ctx;
	
	public AsyncDownloader(ProgressDialog id, Boolean isTemp, Activity mainActivity, Context ctx) {
		super();
		this.saveAsTmpFile = isTemp;
		this.pDialog = id;
		this.mainActivity = mainActivity;
		this.ctx = ctx;
	}
	
	@Override
	protected void onPostExecute(String result) {
		pDialog.dismiss();
		if (saveAsTmpFile == true ) {
			// We downloaded regular html file, therefore we parse and run listview activity
			UrlParser parser = new UrlParser();
			List<String> matches = parser.parse();
			
			if ( matches.size() > 0) {
				Log.d(MainActivity.logTag, "Number of matches is " + matches.size());
				// Deploy new Activity
			} else {
				// 1. Instantiate an AlertDialog.Builder with its constructor
				AlertDialog.Builder builder = new AlertDialog.Builder(mainActivity);
	
				// 2. Chain together various setter methods to set the dialog characteristics
				builder.setMessage("No PDF links were found.")
				       .setTitle("Sorry...");
	
				// 3. Get the AlertDialog from create()
				AlertDialog dialog = builder.create();
				dialog.show();
				Log.d(MainActivity.logTag, "No matches, do nothing");
			}
			
			Intent intent = new Intent(mainActivity, ListViewActivity.class);
			intent.putExtra("links", new String[] {
				"http://ec.europa.eu/education/policies/educ/bologna/bologna.pdf",
				"http://www.mcesr.public.lu/enssup/dossiers/bologne/processus_bologne.pdf"
			});
			mainActivity.startActivity(intent);
			
		} else {
			//We downloaded pdf therefore we run new intent with pdf file
			File file = new File(downloadedFile);
			Intent intent = new Intent(Intent.ACTION_VIEW);
	        intent.setDataAndType(Uri.fromFile(file),"application/pdf");
	        intent.setFlags(Intent.FLAG_ACTIVITY_NO_HISTORY);
	        //mainActivity.startActivity(intent);
		}
		super.onPostExecute(result);
	}

	@Override
	protected void onPreExecute() {
		super.onPreExecute();
		pDialog = ProgressDialog.show(ctx, "Please wait....", "Downloading...");
//		pDialog.setTitle("Downloading...");
//		pDialog.setMessage("Please wait.");
//		pDialog.setCancelable(false);
//		pDialog.show();
	}
	
	@Override
	protected void onProgressUpdate(String... values) {
		//pDialog.setProgress(Integer.parseInt(values[0]));
		super.onProgressUpdate(values);
	}

	@Override
	protected String doInBackground(String... args) {
		int count;
        try {
        	String storagePath = Environment.getExternalStorageDirectory().getPath();
        	        	
            URL url = new URL(args[0]);
            URLConnection conection = url.openConnection();
            conection.connect();
            
            // getting file length
            int lenghtOfFile = conection.getContentLength();
 
            // input stream to read file - with 8k buffer
            InputStream input = new BufferedInputStream(url.openStream(), 8192);
 
            OutputStream output = null;
            
            // Output stream to write file
            if ( saveAsTmpFile == true) {
            	downloadedFile = storagePath + "/temp_web.tmp";
            } else {
                String urlString = url.toString();
                String fileName = urlString.substring( urlString.lastIndexOf('/')+1, urlString.length() );
                downloadedFile = storagePath + "/" + fileName;
            }
            
            output = new FileOutputStream(downloadedFile, false);
            
            
            byte data[] = new byte[1024];
 
            long total = 0;
 
            while ((count = input.read(data)) != -1) {
                total += count;
				if (lenghtOfFile > 0 ) {
				    publishProgress(""+(int)((total*100)/lenghtOfFile));
				} else {
					Log.d(MainActivity.logTag, "File size is below 0");
				}
                // writing data to file
                output.write(data, 0, count);
            }
 
            // flushing output
            output.flush();
 
            // closing streams
            output.close();
            input.close();
 
        } catch (Exception e) {
            Log.d(MainActivity.logTag, e.getMessage());
        }
        return null;
	}
}

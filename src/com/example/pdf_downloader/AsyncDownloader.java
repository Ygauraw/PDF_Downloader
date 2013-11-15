package com.example.pdf_downloader;

import java.io.BufferedInputStream;
import java.io.FileOutputStream;
import java.io.InputStream;
import java.io.OutputStream;
import java.net.URL;
import java.net.URLConnection;
import android.os.AsyncTask;
import android.os.Environment;
import android.util.Log;
import android.widget.ProgressBar;


public class AsyncDownloader extends AsyncTask<String, String, String> {

	private ProgressBar progressBarHandler;
	private Boolean saveAsTmpFile;
	
	public AsyncDownloader(ProgressBar id, Boolean isTemp) {
		super();
		this.saveAsTmpFile = isTemp;
		this.progressBarHandler = id;
	}
	
	@Override
	protected void onPostExecute(String result) {
		progressBarHandler.setVisibility(ProgressBar.VISIBLE);
		progressBarHandler.setProgress((int)100);
		super.onPostExecute(result);
	}

	@Override
	protected void onPreExecute() {
		super.onPreExecute();
		progressBarHandler.setVisibility(ProgressBar.VISIBLE);
		progressBarHandler.setProgress((int)0);
	}
	
	@Override
	protected void onProgressUpdate(String... values) {
		progressBarHandler.setProgress(Integer.parseInt(values[0]));
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
            	output = new FileOutputStream( storagePath + "/temp_web.tmp", false);
            } else {
                String urlString = url.toString();
                String fileName = urlString.substring( urlString.lastIndexOf('/')+1, urlString.length() );
            	output = new FileOutputStream( storagePath + fileName , false);
           	}
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

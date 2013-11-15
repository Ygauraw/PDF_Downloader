package com.example.pdf_downloader;
import android.os.Environment;
import android.util.Log;

import java.io.BufferedReader;
import java.io.File;
import java.io.FileNotFoundException;
import java.io.FileReader;
import java.io.IOException;
import java.util.ArrayList;
import java.util.List;
import java.util.Scanner;
import java.util.regex.Matcher;
import java.util.regex.Pattern;

public class UrlParser {
	
	String content = null;
	
	public UrlParser() {
    	String storagePath = Environment.getExternalStorageDirectory().getPath();
    	String fileName = storagePath + "/temp_web.tmp";
		try {
			BufferedReader br = new BufferedReader(new FileReader(fileName));
		    try {
		        StringBuilder sb = new StringBuilder();
		        String line = br.readLine();

		        while (line != null) {
		            sb.append(line);
		            sb.append('\n');
		            line = br.readLine();
		        }
		        content = sb.toString();
		    } catch (IOException e) {
				// TODO Auto-generated catch block
				e.printStackTrace();
			} finally {
		        try {
					br.close();
				} catch (IOException e) {
					// TODO Auto-generated catch block
					e.printStackTrace();
				}
		    }
			
			Log.d(MainActivity.logTag, content);
		} catch (FileNotFoundException e) {
			// TODO Auto-generated catch block
			e.printStackTrace();
		}
	}
	
	public List<String> parse() {
		List<String> allMatches = new ArrayList<String>();
		Matcher m = Pattern.compile("href=\"http://.*(.pdf\"){1}").matcher(content);
		while (m.find()) {
			String temp = m.group().substring(6, m.group().length()-1);
			allMatches.add(temp);
			Log.d(MainActivity.logTag, temp);
		}
		return allMatches;
	}
}

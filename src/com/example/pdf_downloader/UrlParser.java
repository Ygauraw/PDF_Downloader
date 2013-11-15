package com.example.pdf_downloader;
import android.os.Environment;
import android.util.Log;

import java.io.File;
import java.io.FileNotFoundException;
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
			content = new Scanner(new File(fileName)).useDelimiter("\\Z").next();
			Log.d(MainActivity.logTag, content);
		} catch (FileNotFoundException e) {
			// TODO Auto-generated catch block
			e.printStackTrace();
		}
	}
	
	public List<String> parse() {
		List<String> allMatches = new ArrayList<String>();
		Matcher m = Pattern.compile("a").matcher(content);
		while (m.find()) {
			Log.d(MainActivity.logTag, "CHUJ CI W DUPE");
			allMatches.add(m.group());
			Log.d(MainActivity.logTag, m.group());
		}
		return allMatches;
	}
}

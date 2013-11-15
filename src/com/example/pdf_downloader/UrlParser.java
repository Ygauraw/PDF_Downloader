package com.example.pdf_downloader;
import android.os.Environment;

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
		} catch (FileNotFoundException e) {
			// TODO Auto-generated catch block
			e.printStackTrace();
		}
	}
	
	public List<String> parse(){
		List<String> allMatches = new ArrayList<String>();
		Matcher m = Pattern.compile(".*(https?://.*\\.pdf).*").matcher(content);
		while (m.find()) {
			allMatches.add(m.group());
		}
		return allMatches;
	}
}

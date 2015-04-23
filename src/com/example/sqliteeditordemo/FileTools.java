package com.example.sqliteeditordemo;

import java.io.File;
import java.io.FilenameFilter;

public class FileTools {
	public static final String END_OF_THE_FILE_NAME = ".db";
	
	public static File[] scanAllDBFile(String dir){
		File[] file =  null;
		File path = new File(dir);
		file = path.listFiles(new FilenameFilter() {
			
			@Override
			public boolean accept(File arg0, String arg1) {
				// TODO 自动生成的方法存根
				System.out.println("arg1="+arg1);
				if(arg0.getName().endsWith(END_OF_THE_FILE_NAME))
					return true;
				return false;
			}
		});
		return file;
	}

}

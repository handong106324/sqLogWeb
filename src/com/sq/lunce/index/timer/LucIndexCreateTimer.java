package com.sq.lunce.index.timer;

import java.io.File;
import java.io.IOException;

import org.apache.lucene.store.Directory;
import org.apache.lucene.store.FSDirectory;

import com.sq.lunce.index.Constant;

public class LucIndexCreateTimer implements Runnable{

	private String date ;
	private String hour ;
	public LucIndexCreateTimer(String date,String hour){
		this.date = date;
		this.hour = hour;
	}
//	@Override
	public void run() {
		if(hour == null){
			createIndexByDate(date);
		}else {
			createIndexByHour(date+hour);
		}
	}
	
	private void createIndexByDate(String date) {
		try {
			Directory dir = FSDirectory.open(new File(Constant.getIndexRoot()));
//			dir.
		} catch (IOException e) {
			// TODO Auto-generated catch block
			e.printStackTrace();
		}
//		List<File> 
	}
	
	private void createIndexByHour(String dhour) {
		
	}

}

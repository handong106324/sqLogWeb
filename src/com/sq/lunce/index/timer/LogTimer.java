package com.sq.lunce.index.timer;

import java.util.Date;

import com.sq.shell.currency.ThreadPool;
import com.sq.utils.MyTimeUtils;

public class LogTimer extends Thread{

	@Override
	public void run() {
		runLuc();
	}

	private void runLuc() {
		String date = MyTimeUtils.getYYYYMMDD(new Date());
		
		ThreadPool.getInstance().execute(new LucIndexCreateTimer(date,null));
	}
}

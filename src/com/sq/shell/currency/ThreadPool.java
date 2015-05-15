package com.sq.shell.currency;

import java.util.concurrent.ExecutorService;
import java.util.concurrent.SynchronousQueue;
import java.util.concurrent.ThreadPoolExecutor;
import java.util.concurrent.TimeUnit;


public class ThreadPool {
	private static ExecutorService pool ;
	static{
		createPool();
	}
	private static void createPool(){
		int countT = Runtime.getRuntime().availableProcessors();
		pool = new ThreadPoolExecutor(1,countT,30L,TimeUnit.SECONDS,new SynchronousQueue(), new ThreadPoolExecutor.CallerRunsPolicy());//countT * 10);
	}
	public static  ExecutorService getInstance(){
		if(pool == null){
			createPool();
		}
		return pool;
	}
	
	public static void shutDown(){
		pool.shutdown();
	}
	
}

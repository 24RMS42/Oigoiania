package com.oigoiania.network;

import java.util.concurrent.BlockingQueue;
import java.util.concurrent.LinkedBlockingQueue;
import java.util.concurrent.ThreadPoolExecutor;
import java.util.concurrent.TimeUnit;

public class MyThreadPoolExecutor extends ThreadPoolExecutor {
	private static final int corePoolSize = 3;
	private static final int maximumPoolSize = 10;
	private static final long keepAliveTime = 1000 * 5;// 3 seconds
	private static final TimeUnit timeUnit = TimeUnit.MILLISECONDS;
	private static MyThreadPoolExecutor mMyThreadPoolExecutor = null;

	public static MyThreadPoolExecutor getInstance() {
		if (mMyThreadPoolExecutor == null)
			mMyThreadPoolExecutor = new MyThreadPoolExecutor();
		return mMyThreadPoolExecutor;
	}

	public static MyThreadPoolExecutor getInstance(int corePoolSize,
			int maximumPoolSize, long keepAliveTime, TimeUnit unit,
			BlockingQueue<Runnable> workQueue) {
		if (mMyThreadPoolExecutor == null)
			mMyThreadPoolExecutor = new MyThreadPoolExecutor(corePoolSize,
					maximumPoolSize, keepAliveTime, unit, workQueue);
		return mMyThreadPoolExecutor;
	}

	private MyThreadPoolExecutor() {
		super(corePoolSize, maximumPoolSize, keepAliveTime, timeUnit,
				new LinkedBlockingQueue<Runnable>());
	}

	private MyThreadPoolExecutor(int corePoolSize, int maximumPoolSize,
			long keepAliveTime, TimeUnit unit, BlockingQueue<Runnable> workQueue) {
		super(corePoolSize, maximumPoolSize, keepAliveTime, unit, workQueue);
	}
	public static void shutDown(){
		mMyThreadPoolExecutor.shutDown();
		mMyThreadPoolExecutor=null;
	}

}

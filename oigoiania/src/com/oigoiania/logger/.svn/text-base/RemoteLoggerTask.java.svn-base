package com.oigoiania.logger;

import java.io.IOException;
import java.util.Queue;
import java.util.concurrent.ConcurrentLinkedQueue;
import java.util.logging.Handler;
import java.util.logging.Level;
import java.util.logging.LogManager;
import java.util.logging.Logger;
import java.util.logging.SocketHandler;

import com.crittercism.app.Crittercism;
import com.oigoiania.util.SingleLineFormatter;
import com.oigoiania.util.Util;

public class RemoteLoggerTask extends Thread {
	private Queue<String> messages;
	private Logger remoteLogger = null;

	public RemoteLoggerTask() {
		messages = new ConcurrentLinkedQueue<String>();
	}

	public void run() {

		Handler handler = null;
		try {
			handler = new SocketHandler(Util.REMOTE_LOGGING_SERVER, Util.portNo);

			handler.setFormatter(new SingleLineFormatter());
		} catch (IOException e) {
			Crittercism.logHandledException(e);
			e.printStackTrace();
			return;
		}
		handler.setLevel(Level.FINEST);

		LogManager.getLogManager().getLogger("").addHandler(handler);
		Logger.getLogger("").setLevel(Level.FINEST);
		LogManager.getLogManager().getLogger("").setLevel(Level.FINEST);
		remoteLogger = LogManager.getLogManager().getLogger("");
		try {
			while (true) {
				sleep(3000);
				if (!messages.isEmpty())
					while (messages.isEmpty() != true) {
						remoteLogger.log(Level.FINEST, messages.remove());
					}
			}

		} catch (InterruptedException e) {
			Crittercism.logHandledException(e);
			e.printStackTrace();
		}
	}

	public void log(String msg) {
		messages.add(msg);
	}
}

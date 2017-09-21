package com.exhibit.rpc.task;

import java.util.Timer;
import java.util.TimerTask;
/**
 * @author: ankang
 */
public class TimerSchedule {
	
	private final static Timer TIMER = new Timer();
	
	public static TimerTask schedule(TimerTask task, long delay, long period){
		
		TIMER.schedule(task, delay, period);
		return task;
	}
	
	public static TimerTask schedule(TimerTask task, long delay){
		
		TIMER.schedule(task, delay);
		return task;
	}
}

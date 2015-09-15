package cn.ryanwu.zookeeper.timer;

import java.util.Timer;
import java.util.TimerTask;

public class TimerTaskExample {
	public static void main(String [] args) {
		System.out.println("About to schedule task.");
		Timer timer = new Timer();
		timer.schedule(new TimerTask() {
			
			@Override
			public void run() {
				System.out.println("Time's up");
				
			}
		}, 1*1000, 1000L);
		System.out.println("exit");
	}

}

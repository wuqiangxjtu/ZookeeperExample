package cn.ryanwu.zookeeper.user.server;

import java.util.concurrent.CountDownLatch;

/**
 * Server的启动和清理
 * 使用kill pid关闭，打印start-await-shutdown-countDown-close
 * kill -9 pid不会执行
 * @author wuqiang
 *
 */
public class ServerStartup {
	
	public final CountDownLatch countDownLatch = new CountDownLatch(1);
	
	public void start() {
		System.out.println("start");
	}
	
	public void countDown() {
		System.out.println("countDown");
		countDownLatch.countDown();
	}
		
	public void close() throws InterruptedException {
		System.out.println("await");
		countDownLatch.await();
		System.out.println("close");
	}
	
	public static void main(String[] args) throws InterruptedException {
		final ServerStartup startUp = new ServerStartup();
		startUp.start();
		
		Runtime.getRuntime().addShutdownHook(new Thread(){
			@Override
			public void run() {
				System.out.println("shutdown");
				startUp.countDown();
			}
		});
		startUp.close();
		
	}

}

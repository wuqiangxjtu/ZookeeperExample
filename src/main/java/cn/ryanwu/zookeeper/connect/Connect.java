package cn.ryanwu.zookeeper.connect;

import java.io.IOException;
import java.util.concurrent.CountDownLatch;

import org.apache.zookeeper.WatchedEvent;
import org.apache.zookeeper.Watcher;
import org.apache.zookeeper.Watcher.Event.KeeperState;
import org.apache.zookeeper.ZooKeeper;

public class Connect implements Watcher{
	
	private static CountDownLatch countDownLatch = new CountDownLatch(1);
	
	public static void main(String[] args) throws IOException, InterruptedException {
		ZooKeeper zookeeper = new ZooKeeper("127.0.0.1:2181", 5000, new Connect());
		System.out.println(zookeeper.getState());
		countDownLatch.await();
		Thread.sleep(60000);
	}

	public void process(WatchedEvent event) {
		System.out.println("receive watched event:" + event);
		if(KeeperState.SyncConnected == event.getState()) {
			countDownLatch.countDown();
		}
		
	}

}

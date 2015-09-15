package cn.ryanwu.zookeeper.curator.base;

import org.apache.curator.RetryPolicy;
import org.apache.curator.framework.CuratorFramework;
import org.apache.curator.framework.CuratorFrameworkFactory;
import org.apache.curator.retry.ExponentialBackoffRetry;

public class CreateSessionSample {
	
	public static void main(String[] args) throws InterruptedException {
		RetryPolicy retry = new ExponentialBackoffRetry(1000, 3);
		CuratorFramework client = CuratorFrameworkFactory.newClient("127.0.0.1:2181", 5000, 3000,retry);
		
		client.start();
		Thread.sleep(Integer.MAX_VALUE);
	}

}

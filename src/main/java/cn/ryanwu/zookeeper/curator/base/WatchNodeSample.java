package cn.ryanwu.zookeeper.curator.base;

import org.apache.curator.RetryPolicy;
import org.apache.curator.framework.CuratorFramework;
import org.apache.curator.framework.CuratorFrameworkFactory;
import org.apache.curator.framework.recipes.cache.NodeCache;
import org.apache.curator.framework.recipes.cache.NodeCacheListener;
import org.apache.curator.retry.ExponentialBackoffRetry;

/**
 * 监听节点
 * @author wuqiang
 *
 */
public class WatchNodeSample {
	
	public static void main(String[] args) throws Exception {
		RetryPolicy retry = new ExponentialBackoffRetry(1000, 3);
		CuratorFramework client = CuratorFrameworkFactory.newClient("127.0.0.1:2181", 5000, 3000,retry);
		client.start();
		
		String path = "/watch-test1";
//		client.create().forPath(path);
		NodeCache cache = new NodeCache(client, path, false);
		
		cache.start();
		cache.getListenable().addListener(new NodeCacheListener() {
			@Override
			public void nodeChanged() throws Exception {
				System.out.println("Node data update, new data:" + new String(cache.getCurrentData().getData()));
			}
		});
		Thread.sleep(10000000);
	}

}

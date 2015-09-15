package cn.ryanwu.zookeeper.curator.base;

import org.apache.curator.framework.CuratorFramework;
import org.apache.curator.framework.CuratorFrameworkFactory;
import org.apache.curator.framework.recipes.cache.PathChildrenCache;
import org.apache.curator.framework.recipes.cache.PathChildrenCacheEvent;
import org.apache.curator.framework.recipes.cache.PathChildrenCacheListener;
import org.apache.curator.framework.recipes.cache.PathChildrenCache.StartMode;
import org.apache.curator.retry.ExponentialBackoffRetry;

/**
 * 监听子节点
 * @author wuqiang
 *
 */
public class WatchChildNodeSample {
	
	static String path = "/watch-child-test1";
	
	static CuratorFramework client = CuratorFrameworkFactory.builder()
			.connectString("127.0.0.1:2181")
			.retryPolicy(new ExponentialBackoffRetry(1000, 3))
			.sessionTimeoutMs(5000).build();
	
	public static void main(String[] args) throws Exception {
		client.start();
		
//		client.create().forPath(path);
		
		
		PathChildrenCache cache = new PathChildrenCache(client, path, true);
		cache.start(StartMode.POST_INITIALIZED_EVENT);
		
		cache.getListenable().addListener(new PathChildrenCacheListener() {
			
			@Override
			public void childEvent(CuratorFramework client, PathChildrenCacheEvent event)
					throws Exception {
				switch (event.getType()) {
				case CHILD_ADDED:
					System.out.println("CHILD_ADD," + event.getData().getPath());
					break;
				case CHILD_UPDATED:
					System.out.println("CHILD_UPDATEED" + event.getData().getPath());
					break;
				case CHILD_REMOVED:
					System.out.println("CHILD_REMOVED," + event.getData().getPath());
					break;
				default:
					break;
				}
			}
		});
		Thread.sleep(Integer.MAX_VALUE);
	}

}

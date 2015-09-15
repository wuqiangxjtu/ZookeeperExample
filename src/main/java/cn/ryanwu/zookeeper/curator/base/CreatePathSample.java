package cn.ryanwu.zookeeper.curator.base;


import org.apache.curator.RetryPolicy;
import org.apache.curator.framework.CuratorFramework;
import org.apache.curator.framework.CuratorFrameworkFactory;
import org.apache.curator.retry.ExponentialBackoffRetry;


/**
 * 新建path示例
 * @author wuqiang
 *
 */
public class CreatePathSample {

	public static void main(String[] args) throws Exception {
		RetryPolicy retry = new ExponentialBackoffRetry(1000, 3);
		CuratorFramework client = CuratorFrameworkFactory.newClient("127.0.0.1:2181", 5000, 3000,retry);
		client.start();
		
		//create path
//		client.create().forPath("/create-test1");
		
//		client.create().forPath("/create-test1/sub1");
//		client.create().forPath("/create-test1/sub2");
//		client.create().forPath("/create-test1/sub3");
		
		//create path with data
//		client.create().forPath("/create-test1", "test1".getBytes());
		
		//set data to path
//		client.setData().forPath("/create-test1", "test1".getBytes());
		
		//get data
//		Stat stat = new Stat();
//		byte[] nodeData = client.getData().storingStatIn(stat).forPath("/create-test1");
//		System.out.println(new String(nodeData));
		
		//update data
//		client.setData().forPath("/create-test1", "test2".getBytes());
		
		//delete path
//		client.delete().forPath("/create-test1");
		
		//get children
//		List<String> children = client.getChildren().forPath("/create-test1");
//		children.forEach(System.out::println);
		
		client.close();
		Thread.sleep(Integer.MAX_VALUE);
	}
}

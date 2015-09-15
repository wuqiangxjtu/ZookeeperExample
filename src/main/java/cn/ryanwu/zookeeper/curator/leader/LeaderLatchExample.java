package cn.ryanwu.zookeeper.curator.leader;

import java.io.BufferedReader;
import java.io.InputStreamReader;
import java.util.List;
import java.util.concurrent.TimeUnit;

import org.apache.curator.framework.CuratorFramework;
import org.apache.curator.framework.CuratorFrameworkFactory;
import org.apache.curator.framework.recipes.leader.LeaderLatch;
import org.apache.curator.retry.ExponentialBackoffRetry;
import org.apache.curator.utils.CloseableUtils;

import com.google.common.collect.Lists;

/**
 * leader latch示例
 * @author wuqiang
 *
 */
public class LeaderLatchExample {
	
	private static final String PATH = "/examples/leader";
	
	private static final int CLIENT_QTY = 10;
	
	public static void main(String[] args) {
		List<CuratorFramework> clients = Lists.newArrayList();
		List<LeaderLatch> examples = Lists.newArrayList();
		
		try {
			for(int i=0; i < CLIENT_QTY; i++) {
				CuratorFramework client = CuratorFrameworkFactory.builder()
						.connectString("127.0.0.1:2181")
						.retryPolicy(new ExponentialBackoffRetry(1000, 3))
						.sessionTimeoutMs(5000)
						.connectionTimeoutMs(3000).build();
				clients.add(client);
				
				LeaderLatch latch = new LeaderLatch(client, PATH, "client #" + i);
				examples.add(latch);
				
				client.start();
				latch.start();
				
				
			}
			
			
			Thread.sleep(10000);
			
			LeaderLatch currentLeader = null;
            for (int i = 0; i < CLIENT_QTY; ++i) {
                LeaderLatch example = examples.get(i);
                if (example.hasLeadership())
                    currentLeader = example;
            }
            System.out.println("current leader is " + currentLeader.getId());
            System.out.println("release the leader " + currentLeader.getId());
            currentLeader.close();
            examples.get(0).await(2, TimeUnit.SECONDS);
            System.out.println("Client #0 maybe is elected as the leader or not although it want to be");
            System.out.println("the new leader is " + examples.get(0).getLeader().getId());

            System.out.println("Press enter/return to quit\n");
            new BufferedReader(new InputStreamReader(System.in)).readLine();

			
		} catch (Exception e) {
			e.printStackTrace();
		} finally {
			System.out.println("Shutting down...");
            for (LeaderLatch exampleClient : examples) {
                CloseableUtils.closeQuietly(exampleClient);
            }
            for (CuratorFramework client : clients) {
                CloseableUtils.closeQuietly(client);
            }
            
		}
	}

}

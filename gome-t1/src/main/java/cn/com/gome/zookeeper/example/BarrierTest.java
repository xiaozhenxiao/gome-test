package cn.com.gome.zookeeper.example;

import java.util.Random;

import org.apache.zookeeper.CreateMode;
import org.apache.zookeeper.KeeperException.NoAuthException;
import org.apache.zookeeper.WatchedEvent;
import org.apache.zookeeper.Watcher;
import org.apache.zookeeper.ZooKeeper;
import org.apache.zookeeper.ZooDefs.Ids;
import org.apache.zookeeper.data.Stat;

public class BarrierTest {
	private static String root = "/root";

	public static void main(String args[]) throws Exception {
		/*for (int i = 0; i < 2; i++) {
			Process p = new Process(i*i + "-" + i, new Barrier("/test/barrier",	2));
			p.start();
		}*/
		
		ZooKeeper zk = new ZooKeeper("127.0.0.1:2181", 10 * 1000, new Watcher(){

			@Override
			public void process(WatchedEvent event) {
				System.out.println("触发了事件: " + event.getType());
			}
		});
        while (ZooKeeper.States.CONNECTED != zk.getState()) {
			Thread.sleep(3000);
		}
        zk.addAuthInfo("digest", "passwd".getBytes());
        
        if (zk.exists(root, false) == null) {
            zk.create(root, "rootTest".getBytes(), Ids.CREATOR_ALL_ACL, CreateMode.PERSISTENT);
        }
        try {
        	byte[] result = zk.getData(root, false, null);
        	System.out.println(new String(result));
		} catch (Exception e) {
			if (e instanceof NoAuthException) {
				System.out.println("没有权限");
			}
			e.printStackTrace();
		}
       
       
	}
}

class Process extends Thread {

	private String name;
	private Barrier barrier;

	public Process(String name, Barrier barrier) {
		this.name = name;
		this.barrier = barrier;
	}

	@Override
	public void run() {
		try {
			barrier.enter(name);
			System.out.println(name + " enter");
			Integer rand = new Random().nextInt(2000);
			System.out.println("+++++++++++++++++"+rand+"+++++++++++++++++++++");
			Thread.sleep(1000 + rand);
			barrier.leave(name);
			System.out.println(name + " leave");
		} catch (Exception e) {
			e.printStackTrace();
		}
	}
}

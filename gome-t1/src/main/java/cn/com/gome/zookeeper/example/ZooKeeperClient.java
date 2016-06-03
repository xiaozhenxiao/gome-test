package cn.com.gome.zookeeper.example;

import java.io.IOException;

import org.apache.zookeeper.KeeperException;
import org.apache.zookeeper.WatchedEvent;
import org.apache.zookeeper.Watcher;
import org.apache.zookeeper.ZooKeeper;

public class ZooKeeperClient {
	
	public static ZooKeeper getInstance() {
		ZooKeeper zk = null;
		try {
			zk = new ZooKeeper("127.0.0.1:2181", 10 * 1000, new Watcher(){

				@Override
				public void process(WatchedEvent event) {
					System.out.println(event.getPath() + " 触发了事件: " + event.getType());
				}
			});
		} catch (IOException e) {
			e.printStackTrace();
		}
		while (zk.getState() != ZooKeeper.States.CONNECTED) {
			try {
				Thread.sleep(1000);
			} catch (InterruptedException e) {
				e.printStackTrace();
			}
			
		}
		return zk;
	}
	
	public static void main(String[] args) throws KeeperException, InterruptedException {
		ZooKeeper zooKeeper = getInstance();
		
		if(zooKeeper.exists("/root", true) != null){
			
		}
	}

}

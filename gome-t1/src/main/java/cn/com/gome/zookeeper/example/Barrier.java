package cn.com.gome.zookeeper.example;

import java.util.List;

import org.apache.zookeeper.CreateMode;
import org.apache.zookeeper.KeeperException;
import org.apache.zookeeper.WatchedEvent;
import org.apache.zookeeper.Watcher;
import org.apache.zookeeper.ZooDefs.Ids;
import org.apache.zookeeper.ZooKeeper;
import org.apache.zookeeper.data.Stat;

public class Barrier implements Watcher {

    private static final String addr = "127.0.0.1:2181";
    private ZooKeeper           zk   = null;
    private Integer             mutex;
    private int                 size = 0;
    private String              root;

    public Barrier(String root, int size){
        this.root = root;
        this.size = size;
        System.out.println(Thread.currentThread().getName()+":::::::::::::::::::::barrier:::::::::::::::::::::::::::::");
        try {
            zk = new ZooKeeper(addr, 10 * 1000, this);
            while (ZooKeeper.States.CONNECTED != zk.getState()) {
				Thread.sleep(3000);
			}
            zk.addAuthInfo("digest", "passwd".getBytes());
            mutex = new Integer(-1);
            Stat s = zk.exists(root, false);
            if (s == null) {
                zk.create(root, new byte[0], Ids.OPEN_ACL_UNSAFE, CreateMode.PERSISTENT);
            }

        } catch (Exception e) {
            e.printStackTrace();
        }

    }

    @Override
    public synchronized void process(WatchedEvent event) {
        synchronized (mutex) {
            mutex.notify();
        }
    }

    public boolean enter(String name) throws Exception {
        zk.create(root + "/" + name, new byte[0], Ids.OPEN_ACL_UNSAFE, CreateMode.EPHEMERAL);
        while (true) {
        	System.out.println(Thread.currentThread().getName() + "======================enter========================");
            synchronized (mutex) {
            	System.out.println(Thread.currentThread().getName() + "==========================synchronized===========================");
                List<String> list = zk.getChildren(root, true);
                if (list.size() < size) {
                    mutex.wait();
                    System.out.println(Thread.currentThread().getName() + " wait over!");
                } else {
                	System.out.println(Thread.currentThread().getName() + "  true! " + list.size());
                    return true;
                }
            }
        }
    }

    public boolean leave(String name) throws KeeperException, InterruptedException {
        zk.delete(root + "/" + name, 0);
        while (true) {
        	System.out.println(Thread.currentThread().getName()+"-----------------leave-------------------");
            synchronized (mutex) {
            	System.out.println(Thread.currentThread().getName()+"---------------------synchronized-----------------------");
                List<String> list = zk.getChildren(root, true);
                if (list.size() > 0) {
                    mutex.wait();
                    System.out.println(Thread.currentThread().getName() + " wait over! " + list.size());
                } else {
                	System.out.println(Thread.currentThread().getName() + " true!");
                    return true;
                }
            }
        }
    }

}

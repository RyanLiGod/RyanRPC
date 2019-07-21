package Register;

import org.apache.zookeeper.*;

import java.io.IOException;
import java.util.List;

/**
 * @author Ryan Li
 * @date 2019/07/21
 */
public class ServiceRegister {
    private boolean inited = false;
    private ZooKeeper zkClient;

    private void initZookeeper() {
        try {
            zkClient = new ZooKeeper("127.0.0.1:2181", 2000, (WatchedEvent watchedEvent) -> {
                System.out.println("receive the event:" + watchedEvent);
            });
        } catch (IOException e) {
            e.printStackTrace();
        }
        this.inited = true;
    }

    public void register(String serviceName, String ipAddress, int port) throws Exception {
        if (!inited) {
            this.initZookeeper();
        }
        zkClient.create("/" + serviceName + "/" + ipAddress + "," + port, ipAddress.getBytes(),
                ZooDefs.Ids.OPEN_ACL_UNSAFE, CreateMode.EPHEMERAL);
    }

    public List<String> getService(String serviceName) {
        if (!inited) {
            this.initZookeeper();
        }
        try {
            return zkClient.getChildren("/" + serviceName, false);
        } catch (Exception e) {
            e.printStackTrace();
        }
        return null;
    }
}

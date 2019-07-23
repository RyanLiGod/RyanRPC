package Register;

import org.apache.zookeeper.*;
import org.apache.zookeeper.data.Stat;

import java.io.IOException;
import java.util.List;

/**
 * @author Ryan Li
 * @date 2019/07/21
 */
public class ZKUtil {
    private boolean inited = false;
    private ZooKeeper zkClient;

    private void initZookeeper() {
        try {
            zkClient = new ZooKeeper("127.0.0.1:2181", 2000, (WatchedEvent watchedEvent) -> {
            });
        } catch (IOException e) {
            e.printStackTrace();
        }
        this.inited = true;
    }

    public void zkRegister(String serviceName, String ipAddress, int port) throws Exception {
        if (!inited) {
            this.initZookeeper();
        }
        if (zkClient.exists("/" + serviceName, false) == null) {
            zkClient.create("/" + serviceName, serviceName.getBytes(),
                    ZooDefs.Ids.OPEN_ACL_UNSAFE, CreateMode.PERSISTENT);
        }
        zkClient.create("/" + serviceName + "/" + ipAddress + ":" + port, "0".getBytes(),
                ZooDefs.Ids.OPEN_ACL_UNSAFE, CreateMode.EPHEMERAL);
    }

    public String getService(String serviceName) {
        if (!inited) {
            this.initZookeeper();
        }
        Integer min = Integer.MIN_VALUE;
        Integer minBak = Integer.MIN_VALUE;
        String selectServer = null;
        Stat stat = new Stat();
        try {
            List<String> servers = zkClient.getChildren("/" + serviceName, false);
            for (String server : servers) {
                try {
                    byte[] data = zkClient.getData("/" + serviceName + "/" + server, true, stat);
                    int load = Integer.parseInt(new String(data));
                    min = (min == Integer.MIN_VALUE) ? load : (load < min ? load : min);
                    if (!minBak.equals(min)) {
                        selectServer = server;
                        minBak = min;
                    }
                } catch (Exception e) {
                    e.printStackTrace();
                }
            }
        } catch (Exception e) {
            e.printStackTrace();
        }

        try {
            zkClient.setData("/" + serviceName + "/" + selectServer, String.valueOf(min + 1).getBytes(), stat.getVersion());
            System.out.println("获取到服务供给者：" + selectServer);
            System.out.println("当前服务器负载：" + (min + 1));
        }catch (Exception e) {
            e.printStackTrace();
        }

        return selectServer;
    }

    public void releaseService(String serviceName, String server) {
        Stat stat = new Stat();
        try {
            byte[] data = zkClient.getData("/" + serviceName + "/" + server, true, stat);
            int load = Integer.parseInt(new String(data));
            zkClient.setData("/" + serviceName + "/" + server, String.valueOf(load - 1).getBytes(), stat.getVersion());
            System.out.println("释放服务供给者负载：" + server);
            System.out.println("当前服务器负载：" + (load - 1));
        } catch (Exception e) {
            e.printStackTrace();
        }
    }
}

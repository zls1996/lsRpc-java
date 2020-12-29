package org.tiny.ls.client.util.zookeeper;

import lombok.extern.slf4j.Slf4j;
import org.apache.curator.framework.CuratorFramework;
import org.apache.curator.framework.recipes.cache.PathChildrenCache;
import org.apache.curator.framework.recipes.cache.PathChildrenCacheListener;
import org.apache.zookeeper.CreateMode;
import org.apache.zookeeper.ZooDefs;
import org.apache.zookeeper.data.Stat;

import java.util.ArrayList;
import java.util.List;

/**
 * Created By 朱立松 on 2020/12/22
 */
@Slf4j
public class ZookeeperUtil {

    private static final String PREFIX_NAMESPACE = "/ls/rpc";

    /**
     * 创建节点
     * @param client
     * @param zkPath
     * @param data
     */
    public static boolean createNode(CuratorFramework client, String zkPath, byte[] data) {
        String path = PREFIX_NAMESPACE + zkPath;
        log.info("start to create zookeeper node for path : {}", path);
        try {
            if (exists(client, zkPath)) {
                log.info("current node exists, update this node : {}", path);
                return updateNode(client, zkPath, data, -1);
            }
            client.create().creatingParentsIfNeeded().withMode(CreateMode.PERSISTENT)
                    .withACL(ZooDefs.Ids.OPEN_ACL_UNSAFE).forPath(path, data);
            log.info("create zookeeper node for path : {} success", path);
            return true;
        } catch (Exception e) {
            log.error("create zookeeper node error : {}", e.getCause().toString());
        }
        return false;
    }

    public static boolean updateNode(CuratorFramework client, String zkPath, byte[] data, int version) {
        String path = PREFIX_NAMESPACE + zkPath;
        try {
            client.setData().withVersion(version).forPath(path, data);
            log.info("update zookeeper node for path : {} success", path);
            return true;
        } catch (Exception e) {
            log.error("update zookeeper node data failed , path : {}, cause : {}", path, e.getCause().toString());
        }
        return false;
    }

    public static boolean exists(CuratorFramework client, String zkPath) {
        String path = PREFIX_NAMESPACE + zkPath;
        try {
            Stat exists = client.getZookeeperClient().getZooKeeper().exists(path, false);
            return exists != null;
        } catch (Exception e) {
            log.error("get node stat error : {}", e.getCause().toString());
        }
        return false;
    }

    /**
     * 获取一个目录下所有节点的值
     * @param client
     * @param zkPath
     * @return
     */
    public static List<byte[]> getChildrenData(CuratorFramework client, String zkPath) {
        String path = PREFIX_NAMESPACE + zkPath;
        List<byte[]> result = new ArrayList<>();
        try {
            List<String> childrenList = client.getChildren().forPath(path);
            childrenList.forEach(child -> {
                try {
                    byte[] bytes = client.getData().forPath(path + "/" +child);
                    result.add(bytes);
                } catch (Exception e) {
                    log.error("get data from zookeeper error : {}", e.getCause().toString());
                }
            });
        } catch (Exception e) {
           log.error("get zookeeper children path : {} error : {}", path, e.getCause().toString());
        }
        return result;
    }

    public static boolean deleteNode(CuratorFramework client, String zkPath) {
        String path = PREFIX_NAMESPACE + zkPath;
        try {
            client.delete().deletingChildrenIfNeeded().forPath(path);
            return true;
        } catch (Exception e) {
            log.error("delete zookeeper node failed, path : {}, cause : {}", path, e.getCause().toString());
        }
        return false;
    }

    /**
     * 为某个节点下的所有子节点添加watch
     * @param client
     * @param zkPath
     * @param cacheListener
     */
    public static boolean addChildrenWatch(CuratorFramework client, String zkPath, PathChildrenCacheListener cacheListener) {
        String path = PREFIX_NAMESPACE + zkPath;
        PathChildrenCache pathChildrenCache = new PathChildrenCache(client, path, true);
        pathChildrenCache.getListenable().addListener(cacheListener);
        try {
            pathChildrenCache.start();
            return true;
        } catch (Exception e) {
            log.error("add zookeeper node children watch failed , path : {}, cause : {}", path, e.getCause().toString());
        }

        return false;
    }
}

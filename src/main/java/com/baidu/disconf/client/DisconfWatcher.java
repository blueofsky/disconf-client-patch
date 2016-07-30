package com.baidu.disconf.client;

import com.baidu.disconf.client.fetcher.FetcherMgr;
import com.baidu.disconf.core.common.zookeeper.ZookeeperMgr;
import org.apache.zookeeper.WatchedEvent;
import org.apache.zookeeper.Watcher;
import org.apache.zookeeper.data.Stat;
import org.slf4j.Logger;
import org.slf4j.LoggerFactory;

public class DisconfWatcher implements Watcher{
    protected static final Logger log = LoggerFactory.getLogger(DisconfWatcher.class);
    private FetcherMgr fetcherMgr;
    private DisconfFileBean configFile;
    private Stat stat = new Stat();
    public DisconfWatcher(FetcherMgr fetcherMgr,DisconfFileBean configFile) {
        this.fetcherMgr=fetcherMgr;
        this.configFile=configFile;
    }
    public void start()throws Exception{
        ZookeeperMgr.getInstance().read(configFile.getMonitorPath(),this,stat);
    }
    @Override
    public void process(WatchedEvent event) {
        try {
            ZookeeperMgr.getInstance().read(configFile.getMonitorPath(),this,stat);
            if (event.getType() == Event.EventType.NodeDataChanged) {//结点更新时
                fetcherMgr.downloadFileFromServer(configFile.getRestfulUrl(), configFile.getFileName());
            }
            if (event.getState() == Event.KeeperState.Expired) {// session expired，需要重新关注哦
                ZookeeperMgr.getInstance().reconnect();
                fetcherMgr.downloadFileFromServer(configFile.getRestfulUrl(), configFile.getFileName());
            }
        }catch (Exception e) {
            log.error("disconf watch process failure",e.getMessage(),e);
        }
    }
}

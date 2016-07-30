package com.baidu.disconf.client;

import com.baidu.disconf.client.config.ConfigMgr;
import com.baidu.disconf.client.fetcher.FetcherFactory;
import com.baidu.disconf.client.fetcher.FetcherMgr;
import com.baidu.disconf.client.watch.WatchFactory;
import com.baidu.disconf.client.watch.WatchMgr;
import org.slf4j.Logger;
import org.slf4j.LoggerFactory;

import java.util.ArrayList;
import java.util.List;

public class DisconfFetcherMgr{
    protected static final Logger log = LoggerFactory.getLogger(DisconfFetcherMgr.class);
    private FetcherMgr fetcherMgr=null;
    private WatchMgr watchMgr=null;
    private Boolean inited=false;

    private static class SingletonHolder {
        private static DisconfFetcherMgr instance = new DisconfFetcherMgr();
    }
    public static DisconfFetcherMgr getInstance() {
        return SingletonHolder.instance;
    }
    private void init(){
        try{
            if (!ConfigMgr.isInit()) ConfigMgr.init();
            fetcherMgr = FetcherFactory.getFetcherMgr();
            watchMgr = WatchFactory.getWatchMgr(fetcherMgr);
            inited=true;
         } catch (Exception e){
            log.error("init failure!",e.getMessage(),e);
        }
    }
    public void process(DisconfFileBean disconfFile){
        if(!inited) init();
        List<DisconfFileBean> disconfFiles = new ArrayList<>();
        disconfFiles.add(disconfFile);
        process(disconfFiles);
    }
    public void process(List<DisconfFileBean> disconfFiles){
       if(!inited) init();
       try{
            for (DisconfFileBean disconfFile : disconfFiles) {
                fetcherMgr.downloadFileFromServer(disconfFile.getRestfulUrl(), disconfFile.getFileName());
                DisconfWatcher disconfWatcher = new DisconfWatcher(fetcherMgr, disconfFile);
                disconfWatcher.start();
            }
        } catch (Exception e){
           log.error("process failure!",e.getMessage(),e);
        }
    }
    public void destroy() throws Exception {
        if(fetcherMgr!=null)fetcherMgr.release();
        if(watchMgr!=null)watchMgr.release();
    }
}

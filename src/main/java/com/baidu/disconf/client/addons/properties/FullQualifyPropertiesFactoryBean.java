package com.baidu.disconf.client.addons.properties;

import com.baidu.disconf.client.DisconfFetcherMgr;
import com.baidu.disconf.client.DisconfFileBean;
import org.apache.commons.io.FilenameUtils;
import org.slf4j.Logger;
import org.slf4j.LoggerFactory;
import org.springframework.beans.factory.DisposableBean;
import org.springframework.beans.factory.config.PropertiesFactoryBean;
import org.springframework.core.io.Resource;
import org.springframework.core.io.support.PathMatchingResourcePatternResolver;

import java.io.IOException;
import java.util.ArrayList;
import java.util.List;

public class FullQualifyPropertiesFactoryBean extends PropertiesFactoryBean implements DisposableBean {
    protected static final Logger log = LoggerFactory.getLogger(FullQualifyPropertiesFactoryBean.class);
    private String app;
    private String env;
    private String version;
    private Resource[] locations;
    public void setLocation(final String fileNames) {
        List<String> list = new ArrayList<String>();
        list.add(fileNames);
        setLocations(list);
    }
    public void setLocations(List<String> fileNames) {
        List<Resource> resources = new ArrayList<Resource>();
        for (String filename : fileNames) {
            filename = filename.trim();
            DisconfFileBean disconfFile=new DisconfFileBean();
            disconfFile.setApp(app);
            disconfFile.setEnv(env);
            disconfFile.setVersion(version);
            disconfFile.setFileName(filename);
            DisconfFetcherMgr.getInstance().process(disconfFile);
            String ext = FilenameUtils.getExtension(filename);
            if (ext.equals("properties")) {
                PathMatchingResourcePatternResolver pathMatchingResourcePatternResolver =new PathMatchingResourcePatternResolver();
                try {
                    Resource[] resourceList = pathMatchingResourcePatternResolver.getResources(filename);
                    for (Resource resource : resourceList) {
                        resources.add(resource);
                    }
                } catch (IOException e) {
                    log.error("setLocations failure!",e.getMessage(),e);
                }
            }
        }
        this.locations = resources.toArray(new Resource[resources.size()]);
        super.setLocations(locations);
    }

    @Override
    public String toString() {
        return this.getClass().getSimpleName();
    }
    @Override
    public void destroy() throws Exception {
        DisconfFetcherMgr.getInstance().destroy();
    }
    public void setApp(String app) {
        this.app = app;
    }
    public void setEnv(String env) {
        this.env = env;
    }

    public void setVersion(String version) {
        this.version = version;
    }

}

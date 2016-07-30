package com.baidu.disconf.client;

public class DisconfFileBean {
    private String app;
    private String env;
    private String version;
    private String fileName;

    public String getFileName() {
        return fileName;
    }

    public void setFileName(String fileName) {
        this.fileName = fileName;
    }

    public String getVersion() {
        return version;
    }

    public void setVersion(String version) {
        this.version = version;
    }

    public String getEnv() {
        return env;
    }

    public void setEnv(String env) {
        this.env = env;
    }

    public String getApp() {
        return app;
    }

    public void setApp(String app) {
        this.app = app;
    }

    public String getRestfulUrl(){
        return String.format("/api/config/file?app=%s&version=%s&env=%s&key=%s&type=0",app,version,env,fileName);
    }
    public String getMonitorPath(){
        return  String.format("/disconf/%s_%s_%s/file/%s",app,version,env,fileName);
    }
}

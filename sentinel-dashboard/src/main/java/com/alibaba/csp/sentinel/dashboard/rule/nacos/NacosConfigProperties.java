package com.alibaba.csp.sentinel.dashboard.rule.nacos;

import org.springframework.boot.context.properties.ConfigurationProperties;

import java.util.concurrent.TimeUnit;

@ConfigurationProperties(prefix = NacosConfigProperties.PREFIX)
public class NacosConfigProperties {

    public static final String PREFIX = "sentinel.nacos";

    /**
     * nacos config server address.
     */
    private String serverAddr = "127.0.0.1:8848";

    /**
     * the nacos authentication username.
     */
    private String username = "";

    /**
     * the nacos authentication password.
     */
    private String password = "";

    /**
     * nacos config group, group is config data meta info.
     */
    private String groupId = NacosConfigUtil.GROUP_ID;

    /**
     * namespace, separation configuration of different environments.
     */
    private String namespace = "public";

    /**
     * access key for namespace.
     */
    private String accessKey = "";

    /**
     * secret key for namespace.
     */
    private String secretKey = "";

    /**
     * 从nacos中拉取规则配置的超时时间，单位秒
     */
    private long obtainConfigTimeout = TimeUnit.SECONDS.toMillis(3);

    public String getServerAddr() {
        return serverAddr;
    }

    public void setServerAddr(String serverAddr) {
        this.serverAddr = serverAddr;
    }

    public String getUsername() {
        return username;
    }

    public void setUsername(String username) {
        this.username = username;
    }

    public String getPassword() {
        return password;
    }

    public void setPassword(String password) {
        this.password = password;
    }

    public String getGroupId() {
        return groupId;
    }

    public void setGroupId(String groupId) {
        this.groupId = groupId;
    }

    public String getNamespace() {
        return namespace;
    }

    public void setNamespace(String namespace) {
        this.namespace = namespace;
    }

    public String getAccessKey() {
        return accessKey;
    }

    public void setAccessKey(String accessKey) {
        this.accessKey = accessKey;
    }

    public String getSecretKey() {
        return secretKey;
    }

    public void setSecretKey(String secretKey) {
        this.secretKey = secretKey;
    }

    public long getObtainConfigTimeout() {
        return obtainConfigTimeout;
    }

    public void setObtainConfigTimeout(long obtainConfigTimeout) {
        this.obtainConfigTimeout = obtainConfigTimeout;
    }
}

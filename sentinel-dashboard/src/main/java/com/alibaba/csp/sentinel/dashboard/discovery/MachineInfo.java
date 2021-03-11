package com.alibaba.csp.sentinel.dashboard.discovery;

import com.alibaba.csp.sentinel.dashboard.config.DashboardConfig;
import com.alibaba.csp.sentinel.util.StringUtil;

import java.util.Objects;

/**
 * 每个客户端会对应一条记录信息
 */
public class MachineInfo implements Comparable<MachineInfo> {

    private String app = "";
    private Integer appType = 0;
    private String hostname = "";
    private String ip = "";
    private Integer port = -1;
    private long lastHeartbeat; // 客户端最后一次发送心跳的时间戳
    private long heartbeatVersion;

    /**
     * Indicates the version of Sentinel client (since 0.2.0).
     */
    private String version;

    public static MachineInfo of(String app, String ip, Integer port) {
        MachineInfo machineInfo = new MachineInfo();
        machineInfo.setApp(app);
        machineInfo.setIp(ip);
        machineInfo.setPort(port);
        return machineInfo;
    }

    public String toHostPort() {
        return ip + ":" + port;
    }

    public Integer getPort() {
        return port;
    }

    public void setPort(Integer port) {
        this.port = port;
    }

    public String getApp() {
        return app;
    }

    public void setApp(String app) {
        this.app = app;
    }

    public Integer getAppType() {
        return appType;
    }

    public void setAppType(Integer appType) {
        this.appType = appType;
    }

    public String getHostname() {
        return hostname;
    }

    public void setHostname(String hostname) {
        this.hostname = hostname;
    }

    public String getIp() {
        return ip;
    }

    public void setIp(String ip) {
        this.ip = ip;
    }

    public long getHeartbeatVersion() {
        return heartbeatVersion;
    }

    public void setHeartbeatVersion(long heartbeatVersion) {
        this.heartbeatVersion = heartbeatVersion;
    }

    public String getVersion() {
        return version;
    }

    public MachineInfo setVersion(String version) {
        this.version = version;
        return this;
    }

    /**
     * 判断客户端是否是健康的，即如果客户端最后一次心跳的时间和当前时间的差值在指定的时间以内，那么是健康的
     * <p>
     * 1. 设置了-Dsentinel.dashboard.unhealthyMachineMillis系统属性
     * *****1.1 设置的值大于30000，那么DashboardConfig.getUnhealthyMachineMillis()=-Dsentinel.dashboard.unhealthyMachineMillis
     * *****1.2 设置的值小于30000，那么DashboardConfig.getUnhealthyMachineMillis()=30000
     * 2. 没有设置-Dsentinel.dashboard.unhealthyMachineMillis系统属性，那么DashboardConfig.getUnhealthyMachineMillis()=60000，即60秒
     *
     * @return
     */
    public boolean isHealthy() {
        long delta = System.currentTimeMillis() - lastHeartbeat;
        return delta < DashboardConfig.getUnhealthyMachineMillis();
    }

    /**
     * 是否应该删除已经挂掉的客户端，当设置了-Dsentinel.dashboard.autoRemoveMachineMillis系统属性时，则会判断客户端最后一次
     * 心跳的时间和当前时间的差值，如果差值超过了指定的时间，则会从注册中心剔除该客户端
     * <p>
     * 1. 设置了-Dsentinel.dashboard.autoRemoveMachineMillis系统属性
     * *****1.1 设置的值小于300000，那么DashboardConfig.getAutoRemoveMachineMillis()=300000
     * *****1.2 设置的值大于300000，那么DashboardConfig.getAutoRemoveMachineMillis()=-Dsentinel.dashboard.autoRemoveMachineMillis
     * 2. 没有设置-Dsentinel.dashboard.autoRemoveMachineMillis系统属性，那么DashboardConfig.getAutoRemoveMachineMillis()=0
     *
     * @return
     */
    public boolean isDead() {
        if (DashboardConfig.getAutoRemoveMachineMillis() > 0) {
            long delta = System.currentTimeMillis() - lastHeartbeat;
            return delta > DashboardConfig.getAutoRemoveMachineMillis();
        }
        return false;
    }

    public long getLastHeartbeat() {
        return lastHeartbeat;
    }

    public void setLastHeartbeat(long lastHeartbeat) {
        this.lastHeartbeat = lastHeartbeat;
    }

    @Override
    public int compareTo(MachineInfo o) {
        if (this == o) {
            return 0;
        }

        if (!port.equals(o.getPort())) {
            return port.compareTo(o.getPort());
        }

        if (!StringUtil.equals(app, o.getApp())) {
            return app.compareToIgnoreCase(o.getApp());
        }
        return ip.compareToIgnoreCase(o.getIp());
    }

    @Override
    public String toString() {
        return new StringBuilder("MachineInfo {")
                .append("app='").append(app).append('\'')
                .append(",appType='").append(appType).append('\'')
                .append(", hostname='").append(hostname).append('\'')
                .append(", ip='").append(ip).append('\'')
                .append(", port=").append(port)
                .append(", heartbeatVersion=").append(heartbeatVersion)
                .append(", lastHeartbeat=").append(lastHeartbeat)
                .append(", version='").append(version).append('\'')
                .append(", healthy=").append(isHealthy())
                .append('}').toString();
    }

    @Override
    public boolean equals(Object o) {
        if (this == o) {
            return true;
        }
        if (!(o instanceof MachineInfo)) {
            return false;
        }
        MachineInfo that = (MachineInfo) o;
        return Objects.equals(app, that.app) &&
                Objects.equals(ip, that.ip) &&
                Objects.equals(port, that.port);
    }

    @Override
    public int hashCode() {
        return Objects.hash(app, ip, port);
    }

    /**
     * Information for log
     *
     * @return
     */
    public String toLogString() {
        return app + "|" + ip + "|" + port + "|" + version;
    }
}

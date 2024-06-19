package com.example.concurrent.network.snmp;

import org.snmp4j.mp.SnmpConstants;

public class SnmpCon {
    //snmp版本
    public static final int DEFAULT_VERSION_V3 = SnmpConstants.version3;
    //udp
    public static final String DEFAULT_PROTOCOL = "udp";
    //被访问设备的IP地址
    public static final String IP_ADDRESS = "172.16.3.10/";
    //端口号
    public static final String DEFAULT_PORT = "161";
    //团体名
    public static final String COMMUNITY = "public";
    //v3用户名 -配置SNMP v3时 手动设置 写自己定义的名字
    public static final String USM_USER = "public";
    //authMD5-加密，配置SNMP v3时 手动设置的 写自己定义的密码
    public static final String AUTH_MD5 = "wison@2015";
    //PIRV_DES-加密，配置SNMP v3时 手动设置的 写自己定义的密码
    public static final String PREV_DES = "wison@2015";
    //超时时间
    public static final long DEFAULT_TIMEOUT = 3 * 1000L;
    //重置
    public static final int DEFAULT_RETRY = 0;
}

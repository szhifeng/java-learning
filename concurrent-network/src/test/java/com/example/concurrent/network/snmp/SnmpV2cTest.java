package com.example.concurrent.network.snmp;

import org.junit.jupiter.api.Test;

import java.util.ArrayList;
import java.util.List;

public class SnmpV2cTest {
    /**
     * 测试GET方式取值
     */
    @Test
    public void testGet() {
        String ip = "172.16.3.10";
        String community = "public";
        String oidval = "1.3.6.1.4.1.2021.11.11.0";
        SnmpV2cUtil.snmpGet(ip, community, oidval);
    }

    /**
     * 测试批量GET方式取值
     */
    @Test
    public void testGetList() {
        String ip = "192.168.2.2";
        String community = "snmp_pwd";
        List<String> oidList = new ArrayList<String>();
        oidList.add("1.3.6.1.2.1.1.4.0");
        oidList.add("1.3.6.1.2.1.1.5.0");
        SnmpV2cUtil.snmpGetList(ip, community, oidList);
    }

    /**
     * 测试批量GET方式异步取值
     */
    @Test
    public void testGetAsyList() {
        String ip = "192.168.2.2";
        String community = "snmp_pwd";
        List<String> oidList = new ArrayList<String>();
        oidList.add("1.3.6.1.2.1.1.5.0");
        oidList.add("1.3.6.1.2.1.1.4.0");
        SnmpV2cUtil.snmpAsynGetList(ip, community, oidList);

        System.out.println("i am first!");
    }

    /**
     * 测试WALK方式取值
     */
    @Test
    public void testWalk() {
        String ip = "192.168.2.2";
        String community = "snmp_pwd";
        String targetOid = "1.3.6.1.2.1.25.2.3.1.5";
        SnmpV2cUtil.snmpWalk(ip, community, targetOid);
    }

    /**
     * 测试WALK异步方式取值
     */
    @Test
    public void testAsyWalk() {
        String ip = "192.168.2.2";
        String community = "snmp_pwd";
        // 异步采集数据
        SnmpV2cUtil.snmpAsynWalk(ip, community, "1.3.6.1.2.1.25.4.2.1.2");
    }

    /**
     * 测试设置pdu
     * @throws Exception
     */
    @Test
    public void testSetPDU() throws Exception {
        String ip = "192.168.2.2";
        String community = "snmp_pwd";
        SnmpV2cUtil.setPDU(ip, community, "1.3.6.1.2.1.1.6.0");
    }
}

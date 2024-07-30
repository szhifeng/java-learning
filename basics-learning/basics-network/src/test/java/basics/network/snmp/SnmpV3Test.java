package basics.network.snmp;

import org.junit.jupiter.api.Test;

import java.util.ArrayList;
import java.util.List;

public class SnmpV3Test {

    /**
     * 测试v3 GET 方式获取
     */
    @Test
    public void snmpV3Get() {
        String oid = "1.3.6.1.2.1.25.2.2.0";
        SnmpV3Util.snmpV3Get(oid);
    }

    /**
     * 测试v3 GET 方式批量获取
     */
    @Test
    public void snmpV3GetList() {
        List<String> oidList = new ArrayList<String>();
        oidList.add("1.3.6.1.2.1.1.1.0");
        oidList.add("1.3.6.1.2.1.1.3.0");
        oidList.add("1.3.6.1.2.1.1.4.0");
        oidList.add("1.3.6.1.4.1.2021.9.1");
        oidList.add("1.3.6.1.2.1.2.2.1");
        oidList.add("1.3.6.1.4.1.2021.10.1");
        SnmpV3Util.snmpV3GetList(oidList);
    }

    /**
     * 测试v3 GET 方式异步批量获取
     */
    @Test
    public void snmpV3AsynGetList() {
        List<String> oidList = new ArrayList<String>();
        oidList.add("1.3.6.1.2.1.1.1.0");
        oidList.add("1.3.6.1.2.1.1.3.0");
        oidList.add("1.3.6.1.2.1.1.4.0");
        SnmpV3Util.snmpV3AsynGetList(oidList);
    }

    /**
     * 测试v3 WALK 方式获取数据
     */
    @Test
    public void snmpV3Walk() {
        String oid = "1.3.6.1.2.1.25.2.3.1.5";
        SnmpV3Util.snmpV3Walk(oid);
    }


    /**
     * 测试v3 WALK 异步方式获取数据
     */
    @Test
    public void snmpV3AsynWalk() {
        String oid = "1.3.6.1.2.1.25.2.3.1.5";
        SnmpV3Util.snmpV3AsynWalk(oid);
    }
}

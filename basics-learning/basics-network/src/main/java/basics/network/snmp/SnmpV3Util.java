package basics.network.snmp;

import org.snmp4j.PDU;
import org.snmp4j.ScopedPDU;
import org.snmp4j.Snmp;
import org.snmp4j.UserTarget;
import org.snmp4j.event.ResponseEvent;
import org.snmp4j.event.ResponseListener;
import org.snmp4j.mp.MPv3;
import org.snmp4j.security.*;
import org.snmp4j.smi.*;
import org.snmp4j.transport.DefaultUdpTransportMapping;

import java.io.IOException;
import java.util.List;
import java.util.Vector;
import java.util.concurrent.CountDownLatch;
import java.util.concurrent.TimeUnit;

public class SnmpV3Util {


    /**
     * 创建对象UserTarget
     *
     * @return
     */
    public static UserTarget createDefault() {
        //如果有问题可能是address的问题 v3没有udp--现在是加上udp测试的
        Address address = GenericAddress
                .parse(SnmpCon.DEFAULT_PROTOCOL + ":" + SnmpCon.IP_ADDRESS
                        + "/" + SnmpCon.DEFAULT_PORT);
        UserTarget target = new UserTarget();
        target.setVersion(SnmpCon.DEFAULT_VERSION_V3);
        target.setAddress(address);
        target.setSecurityLevel(SecurityLevel.AUTH_PRIV);
        target.setSecurityName(new OctetString(SnmpCon.USM_USER));
        target.setTimeout(SnmpCon.DEFAULT_TIMEOUT);
        target.setRetries(SnmpCon.DEFAULT_RETRY);
        return target;
    }

    /**
     * snmpV3Get方式获取数据
     *
     * @param oid 设备OID
     */
    public static void snmpV3Get(String oid) {
        UserTarget target = createDefault();
        Snmp snmp = null;
        try {
            ScopedPDU pdu = new ScopedPDU();
            pdu.setType(PDU.GET);
            pdu.setContextEngineID(new OctetString(SnmpCon.COMMUNITY));
            pdu.add(new VariableBinding(new OID(oid)));
            DefaultUdpTransportMapping transport = new DefaultUdpTransportMapping();
            snmp = new Snmp(transport);
            USM usm = new USM(SecurityProtocols.getInstance(),
                    new OctetString(MPv3.createLocalEngineID()),
                    0);
            SecurityModels.getInstance().addSecurityModel(usm);
            snmp.listen();
            //验证密码
            UsmUser user = new UsmUser(new OctetString(SnmpCon.USM_USER), AuthMD5.ID,
                    new OctetString(SnmpCon.AUTH_MD5), PrivDES.ID, new OctetString(SnmpCon.PREV_DES));
            snmp.getUSM().addUser(new OctetString(SnmpCon.USM_USER), user);

            System.out.println("-------> 发送PDU <-------");
            ResponseEvent respEvent = snmp.send(pdu, target);
            System.out.println("被访问设备的IP地址及端口号:  " + respEvent.getPeerAddress());
            PDU response = respEvent.getResponse();
            if (response == null) {
                System.out.println("response is null, request time out");
            } else {
                if (response.getErrorStatus() == PDU.noError) {
                    Vector<? extends VariableBinding> vbs = response.getVariableBindings();
                    for (VariableBinding vb : vbs) {
                        System.out.println("****** 返回结果 ******");
                        System.out.println(vb + " ," + vb.getVariable().getSyntaxString());
                        System.out.println("************");
                    }
                } else {
                    System.out.println("Error:" + response.getErrorStatusText());
                }
            }
        } catch (Exception e) {
            e.printStackTrace();
        } finally {
            if (snmp != null) {
                try {
                    snmp.close();
                } catch (IOException ex1) {
                    snmp = null;
                }
            }
        }
    }

    /**
     * snmp v3 批量获取设备信息
     *
     * @param oidList
     */
    public static void snmpV3GetList(List<String> oidList) {
        UserTarget target = createDefault();
        Snmp snmp = null;
        try {
            ScopedPDU pdu = new ScopedPDU();
            pdu.setType(PDU.GET);
            pdu.setContextEngineID(new OctetString(SnmpCon.COMMUNITY));
            for (String oid : oidList) {
                pdu.add(new VariableBinding(new OID(oid)));
            }
            DefaultUdpTransportMapping transport = new DefaultUdpTransportMapping();
            snmp = new Snmp(transport);
            USM usm = new USM(SecurityProtocols.getInstance(),
                    new OctetString(MPv3.createLocalEngineID()),
                    0);
            SecurityModels.getInstance().addSecurityModel(usm);
            snmp.listen();

            UsmUser user = new UsmUser(new OctetString(SnmpCon.USM_USER), AuthMD5.ID,
                    new OctetString(SnmpCon.AUTH_MD5), PrivDES.ID, new OctetString(SnmpCon.PREV_DES));
            snmp.getUSM().addUser(new OctetString(SnmpCon.USM_USER), user);

            System.out.println("-------> 发送PDU <-------");
            ResponseEvent respEvent = snmp.send(pdu, target);
            System.out.println("被访问设备的IP地址及端口号:  " + respEvent.getPeerAddress());
            PDU response = respEvent.getResponse();
            if (response == null) {
                System.out.println("response is null, request time out");
            } else {
                if (response.getErrorStatus() == PDU.noError) {
                    Vector<? extends VariableBinding> vbs = response.getVariableBindings();
                    for (VariableBinding vb : vbs) {
                        System.out.println("****** 返回结果 ******");
                        System.out.println(vb + " ," + vb.getVariable().getSyntaxString());
                        System.out.println("************");
                    }
                } else {
                    System.out.println("Error:" + response.getErrorStatusText());
                }
            }
        } catch (Exception e) {
            e.printStackTrace();
        } finally {
            if (snmp != null) {
                try {
                    snmp.close();
                } catch (IOException ex1) {
                    snmp = null;
                }
            }
        }
    }


    /**
     * snmp v3 异步批量获取设备信息
     *
     * @param oidList
     */
    public static void snmpV3AsynGetList(List<String> oidList) {
        UserTarget target = createDefault();
        Snmp snmp = null;
        try {
            ScopedPDU pdu = new ScopedPDU();
            pdu.setType(PDU.GET);
            pdu.setContextEngineID(new OctetString(SnmpCon.COMMUNITY));
            for (String oid : oidList) {
                pdu.add(new VariableBinding(new OID(oid)));
            }
            DefaultUdpTransportMapping transport = new DefaultUdpTransportMapping();
            snmp = new Snmp(transport);
            USM usm = new USM(SecurityProtocols.getInstance(),
                    new OctetString(MPv3.createLocalEngineID()),
                    0);
            SecurityModels.getInstance().addSecurityModel(usm);
            snmp.listen();

            UsmUser user = new UsmUser(new OctetString(SnmpCon.USM_USER), AuthMD5.ID,
                    new OctetString(SnmpCon.AUTH_MD5), PrivDES.ID, new OctetString(SnmpCon.PREV_DES));
            snmp.getUSM().addUser(new OctetString(SnmpCon.USM_USER), user);

            System.out.println("-------> 发送PDU <-------");
            ResponseEvent respEvent = snmp.send(pdu, target);
            System.out.println("被访问设备的IP地址及端口号:  " + respEvent.getPeerAddress());

            /*异步获取*/
            final CountDownLatch latch = new CountDownLatch(oidList.size());
            ResponseListener listener = new ResponseListener() {
                public void onResponse(ResponseEvent event) {
                    ((Snmp) event.getSource()).cancel(event.getRequest(), this);
                    PDU response = event.getResponse();
                    PDU request = event.getRequest();
                    System.out.println("[request]:" + request);
                    if (response == null) {
                        System.out.println("response is null, request time out");
                    } else {
                        if (response.getErrorStatus() == PDU.noError) {
                            Vector<? extends VariableBinding> vbs = response.getVariableBindings();
                            for (VariableBinding vb : vbs) {
                                System.out.println("****** 返回结果 ******");
                                System.out.println(vb + " ," + vb.getVariable().getSyntaxString());
                                System.out.println("************");
                            }
                        } else {
                            System.out.println("Error:" + response.getErrorStatusText());
                        }
                        System.out.println("SNMP Asyn GetList OID finished. ");
                        latch.countDown();
                    }
                }
            };
            pdu.setType(PDU.GET);
            snmp.send(pdu, target, null, listener);
            System.out.println("asyn send pdu wait for response...");

            boolean wait = latch.await(30, TimeUnit.SECONDS);
            System.out.println("latch.await =:" + wait);

            snmp.close();

            System.out.println("SNMP GET one OID value finished !");
        } catch (Exception e) {
            e.printStackTrace();
            System.out.println("SNMP Get Exception:" + e);
        } finally {
            if (snmp != null) {
                try {
                    snmp.close();
                } catch (IOException ex1) {
                    snmp = null;
                }
            }
        }
    }

    /**
     * SNMP V3 WALK 方式获取数据
     *
     * @param oid
     */
    public static void snmpV3Walk(String oid) {
        UserTarget target = createDefault();
        Snmp snmp = null;
        try {
            DefaultUdpTransportMapping transport = new DefaultUdpTransportMapping();
            snmp = new Snmp(transport);
            USM usm = new USM(SecurityProtocols.getInstance(),
                    new OctetString(MPv3.createLocalEngineID()),
                    0);
            SecurityModels.getInstance().addSecurityModel(usm);
            snmp.listen();
            UsmUser user = new UsmUser(new OctetString(SnmpCon.USM_USER), AuthMD5.ID,
                    new OctetString(SnmpCon.AUTH_MD5), PrivDES.ID, new OctetString(SnmpCon.PREV_DES));
            snmp.getUSM().addUser(new OctetString(SnmpCon.USM_USER), user);
            ScopedPDU pdu = new ScopedPDU();
            OID targetOID = new OID(oid);
            pdu.add(new VariableBinding(targetOID));
            boolean finished = false;
            System.out.println("-------> 发送PDU <-------");
            while (!finished) {
                VariableBinding vb = null;
                ResponseEvent respEvent = snmp.getNext(pdu, target);
                PDU response = respEvent.getResponse();
                if (null == response) {
                    System.out.println("responsePDU == null");
                    finished = true;
                    break;
                } else {
                    vb = response.get(0);
                }
                // check finish
                finished = checkWalkFinished(targetOID, pdu, vb);
                if (!finished) {
                    System.out.println("==== walk each vlaue :");
                    System.out.println(vb.getOid() + " = " + vb.getVariable());
                    // Set up the variable binding for the next entry.
                    pdu.setRequestID(new Integer32(0));
                    pdu.set(0, vb);
                } else {
                    System.out.println("SNMP walk OID has finished.");
                    snmp.close();
                }
            }
            System.out.println("-------> 发送PDU结束 <-------");
        } catch (Exception e) {
            e.printStackTrace();
            System.out.println("SNMP walk Exception: " + e);
        } finally {
            if (snmp != null) {
                try {
                    snmp.close();
                } catch (IOException ex1) {
                    snmp = null;
                }
            }
        }
    }

    /**
     * WALK 方式 异步获取数据
     *
     * @param oid
     */
    public static void snmpV3AsynWalk(String oid) {
        UserTarget target = createDefault();
        Snmp snmp = null;
        try {
            DefaultUdpTransportMapping transport = new DefaultUdpTransportMapping();
            snmp = new Snmp(transport);
            USM usm = new USM(SecurityProtocols.getInstance(),
                    new OctetString(MPv3.createLocalEngineID()),
                    0);
            SecurityModels.getInstance().addSecurityModel(usm);
            snmp.listen();
            UsmUser user = new UsmUser(new OctetString(SnmpCon.USM_USER), AuthMD5.ID,
                    new OctetString(SnmpCon.AUTH_MD5), PrivDES.ID, new OctetString(SnmpCon.PREV_DES));
            snmp.getUSM().addUser(new OctetString(SnmpCon.USM_USER), user);
            ScopedPDU pdu = new ScopedPDU();
            OID targetOID = new OID(oid);
            pdu.add(new VariableBinding(targetOID));
            final CountDownLatch latch = new CountDownLatch(1);
            ResponseListener listener = new ResponseListener() {
                public void onResponse(ResponseEvent event) {
                    ((Snmp) event.getSource()).cancel(event.getRequest(), this);
                    try {
                        PDU response = event.getResponse();
                        // PDU request = event.getRequest();
                        // System.out.println("[request]:" + request);
                        if (response == null) {
                            System.out.println("[ERROR]: response is null");
                        } else if (response.getErrorStatus() != 0) {
                            System.out.println("[ERROR]: response status"
                                    + response.getErrorStatus() + " Text:"
                                    + response.getErrorStatusText());
                        } else {
                            System.out.println("Received Walk response value :");
                            VariableBinding vb = response.get(0);
                            boolean finished = checkWalkFinished(targetOID,
                                    pdu, vb);
                            if (!finished) {
                                System.out.println(vb.getOid() + " = " + vb.getVariable());
                                pdu.setRequestID(new Integer32(0));
                                pdu.set(0, vb);
                                ((Snmp) event.getSource()).getNext(pdu, target,
                                        null, this);
                            } else {
                                System.out
                                        .println("SNMP Asyn walk OID value success !");
                                latch.countDown();
                            }
                        }
                    } catch (Exception e) {
                        e.printStackTrace();
                        latch.countDown();
                    }
                }
            };
            snmp.getNext(pdu, target, null, listener);
            System.out.println("pdu 已发送,等到异步处理结果...");

            boolean wait = latch.await(30, TimeUnit.SECONDS);
            System.out.println("latch.await =:" + wait);
            snmp.close();
            System.out.println("-------> 发送PDU结束 <-------");

        } catch (Exception e) {
            e.printStackTrace();
            System.out.println("SNMP Asyn Walk Exception:" + e);
        }
    }

    /**
     * 检查WALK
     *
     * @param targetOID
     * @param pdu
     * @param vb
     * @return
     */
    private static boolean checkWalkFinished(OID targetOID, PDU pdu, VariableBinding vb) {
        boolean finished = false;
        if (pdu.getErrorStatus() != 0) {
            System.out.println("[true] responsePDU.getErrorStatus() != 0 ");
            System.out.println(pdu.getErrorStatusText());
            finished = true;
        } else if (vb.getOid() == null) {
            System.out.println("[true] vb.getOid() == null");
            finished = true;
        } else if (vb.getOid().size() < targetOID.size()) {
            System.out.println("[true] vb.getOid().size() < targetOID.size()");
            finished = true;
        } else if (targetOID.leftMostCompare(targetOID.size(), vb.getOid()) != 0) {
            System.out.println("[true] targetOID.leftMostCompare() != 0");
            finished = true;
        } else if (Null.isExceptionSyntax(vb.getVariable().getSyntax())) {
            System.out.println("[true] Null.isExceptionSyntax(vb.getVariable().getSyntax())");
            finished = true;
        } else if (vb.getOid().compareTo(targetOID) <= 0) {
            System.out.println(
                    "[true] Variable received is not lexicographic successor of requested " + "one:");
            System.out.println(vb.toString() + " <= " + targetOID);
            finished = true;
        }
        return finished;
    }
}
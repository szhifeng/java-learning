package com.example.concurrent.pattern;

import com.alibaba.fastjson.JSON;
import com.example.concurrent.pattern.behavioral.observer.Sheep;
import com.example.concurrent.pattern.behavioral.observer.Wolf;
import com.example.concurrent.pattern.behavioral.responsibilitychain.*;

import com.google.common.collect.Lists;
import org.junit.jupiter.api.Test;

import java.net.URI;
import java.net.URISyntaxException;
import java.nio.charset.Charset;
import java.nio.charset.CharsetEncoder;
import java.nio.charset.StandardCharsets;
import java.util.*;

/**
 * @author szf
 * @describe: 设计模式单元测试
 * @Date 2022/5/31 13:55
 */
public class PatternTest {

    /**
     * 观察者模式适合以下几种情形：<p>
     * 对象间存在一对多关系，一个对象的状态发生改变会影响其他对象；<p>
     * 当一个抽象模型有两个方面，其中一个方面依赖于另一方面时，可将这二者封装在独立的对象中以使它们可以各自独立地改变和复用；<p>
     * 实现类似广播机制的功能，不需要知道具体收听者，只需分发广播，系统中感兴趣的对象会自动接收该广播；<p>
     * 多层级嵌套使用，形成一种链式触发机制，使得事件具备跨域（跨越两种观察者类型）通知。
     */
    @Test
    public void behavioralObserverTest() {
        Wolf wolf = new Wolf("灰太狼");
        wolf.addObserver(new Sheep("喜羊羊"));
        wolf.addObserver(new Sheep("懒羊羊"));
        wolf.addObserver(new Sheep("美羊羊"));
        wolf.addObserver(new Sheep("沸羊羊"));
        wolf.coming("hungry");
    }

    @Test
    public void chainofResponsibilityTest01(){
        Handler validateHandler = new ValidateHandler();
        Handler loginHandler = new LoginHandler();
        Handler authHandler = new AuthHandler();

        validateHandler.next(loginHandler);
        loginHandler.next(authHandler);
        validateHandler.doHandler(new Member("loginName", "loginPass"));
    }

    @Test
    public void chainofResponsibilityTest02(){
        Handler.Builder builder = new Handler.Builder();
        builder.addHandler(new ValidateHandler())
                .addHandler(new LoginHandler())
                .addHandler(new AuthHandler());
        builder.build().doHandler(new Member("loginName", "loginPass"));
    }

    public static int byteArrayToInt(byte[] bytes) {
        int value = 0;
        // 由高位到低位
        for (int i = 0; i < 4; i++) {
            int shift = (4 - 1 - i) * 8;
            value += (bytes[i] & 0x000000FF) << shift;// 往高位游
        }
        return value;
    }
    @Test
    public void testStream() {
        final CharsetEncoder GBKEncoder = Charset.forName("GBK").newEncoder();
        byte[] bytes = new byte[] {66, 13, 17, 24, 32, 97, 98, 99, 101, 103, 105, 42, 110, 46, 111, 48, 49, 114, 50, 51, 116, 53, -118, 118, 119, 122};
        System.out.println(bytes.length);
        String s = new String(bytes);
        String a = "setEncodercharsetEncoder=charset.newEncoder(); ";
        System.out.println(GBKEncoder.canEncode(a));
        System.out.println(s);
        StringBuilder buf = new StringBuilder(bytes.length * 2);
//        for(byte b : bytes) { // 使用String的format方法进行转换
//            buf.append(String.format("%02x", new Integer(b & 0xff)));
//        }
        for(byte b : bytes) {
            int v = b & 0xFF;
            String hv = Integer.toHexString(v);
            if (hv.length() < 2) {
                buf.append(0);
            }
            buf.append(hv);
        }
        System.out.println(buf);
    }

    @Test
    public void testList() throws URISyntaxException {
        List<String> list = Lists.newArrayList("1", "2", "23", "4");
        String[] array = list.toArray(new String[0]);
        System.out.println("===================" + array.length + "++++" + array[1]);
        HashMap<String, Object> conf = new HashMap<>();
        conf.put("userId", "123");
        conf.put("userName", "jome");
        conf.put("userCode", "ABA12K");
        System.out.println(Arrays.toString(JSON.toJSONString(conf).getBytes(StandardCharsets.UTF_8)));

        URI uri = new URI("https://172.16.3.10:8800/admin/message/upload");
        System.out.println(uri.getAuthority() + "===="+ uri.getUserInfo());
        System.out.println(new URI(uri.getScheme(), uri.getUserInfo(), uri.getHost(), uri.getPort(), "/probe/warning/log/config/save", null, null));
    }

    @Test
    public void stringTest(){
        String aa = "";
        String bb = "";
        System.out.println(bb.contains(aa));
    }
}

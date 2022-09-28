package basics;

import org.junit.jupiter.api.Test;

import java.util.Arrays;
import java.util.List;

/**
 * @author szf
 * @describe: 循环测试
 * @Date 2022/8/15 10:56
 * @Copyright: 2022 http://www.gzwison.com/ Inc. All rights reserved.
 * 注意：本内容仅限于广州智臣科技有限公司内部传阅，禁止外泄以及用于其他的商业目
 */
public class CirculationTest {

    @Test
    void test4For(){
        int a = 10;
        for (int i = 1; i < a; i++) {
            System.out.printf("开始第%d次循环！%n", i);
           if (i < 3) {
               System.out.println("循环次数小于 3");
               continue;        // 跳出循环
           }
            if (i > 6) {
                System.out.println("循环次数大于 6");
                break;          //  结束整个 for 循环
            }
            if (i == 7){
                System.out.println("第 7 次循环！");
                return;     // 结束整个方法
            }
            System.out.printf("结束第%d次循环！%n", i);
        }
        System.out.println("不在循环中！");
    }

    @Test
    void test4ForEach(){
        //java8 forEach 使用 continue，break 无效退出循环，只能使用 return
        List<String> list = Arrays.asList("123", "12345", "1234", "4321", "1234567", "5678");
        list.forEach(s -> {
            if (s.length() >= 5) {
                return;
            }
            System.err.print(s+ " ");       // 123 1234 4321 5678
        });
    }

    @Test
    public void testFor(){
        for (;;) {
            System.out.println("测试和是否循环：");
        }
    }
}

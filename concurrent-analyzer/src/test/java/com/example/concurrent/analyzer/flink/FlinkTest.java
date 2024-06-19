package com.example.concurrent.analyzer.flink;

import com.google.common.collect.Lists;
import org.apache.flink.api.common.RuntimeExecutionMode;
import org.apache.flink.api.common.functions.FlatMapFunction;
import org.apache.flink.api.common.functions.MapFunction;
import org.apache.flink.api.java.functions.KeySelector;
import org.apache.flink.api.java.tuple.Tuple2;
import org.apache.flink.streaming.api.environment.StreamExecutionEnvironment;
import org.apache.flink.util.Collector;
import org.junit.jupiter.api.Test;

import java.util.Arrays;

public class FlinkTest {

    @Test
    void streamingTest() {
        // flink 流执行环境
        StreamExecutionEnvironment env = StreamExecutionEnvironment.getExecutionEnvironment();
        //设置模式 STREAMING
        env.setRuntimeMode(RuntimeExecutionMode.STREAMING);
        try {
            //数据源，fromCollection
            env.fromCollection(Lists.newArrayList("nacos,python,java", "nacos,scripts,php", "nacos,java,springmvc", "nacos,sentinel,gateway"))
                    //扁平化
                    .flatMap(new FlatMapFunction<String, String>() {
                        @Override
                        public void flatMap(String value, Collector<String> out) throws Exception {
                            Arrays.stream(value.split(",")).forEach(out::collect);
                        }
                    })
                    //映射
                    .map(new MapFunction<String, Tuple2<String, Integer>>() {
                        @Override
                        public Tuple2<String, Integer> map(String value) throws Exception {
                            return Tuple2.of(value, 1);
                        }
                    })
                    //分组
                    .keyBy((KeySelector<Tuple2<String, Integer>, String>) value -> value.f0)
                    //求和
                    .sum(1)
                    //打印结果
                    .print();
            //开始执行
            env.execute("flink streaming");
        }catch (Exception e){
            System.err.println(e.getLocalizedMessage());
        }
    }

    @Test
    void batchTest() {
        // flink 流执行环境
        StreamExecutionEnvironment env = StreamExecutionEnvironment.getExecutionEnvironment();
        //设置模式 STREAMING
        env.setRuntimeMode(RuntimeExecutionMode.BATCH);
        try {
            //数据源，fromCollection
            env.fromCollection(Lists.newArrayList("nacos,python,java", "nacos,scripts,php", "nacos,java,springmvc", "nacos,sentinel,gateway"))
                    //扁平化
                    .flatMap(new FlatMapFunction<String, String>() {
                        @Override
                        public void flatMap(String value, Collector<String> out) throws Exception {
                            Arrays.stream(value.split(",")).forEach(out::collect);
                        }
                    })
                    //映射
                    .map(new MapFunction<String, Tuple2<String, Integer>>() {
                        @Override
                        public Tuple2<String, Integer> map(String value) throws Exception {
                            return Tuple2.of(value, 1);
                        }
                    })
                    //分组
                    .keyBy((KeySelector<Tuple2<String, Integer>, String>) value -> value.f0)
                    //求和
                    .sum(1)
                    //打印结果
                    .print();
            //开始执行
            env.execute("flink batch");
        }catch (Exception e){
            System.err.println(e.getLocalizedMessage());
        }
    }
}

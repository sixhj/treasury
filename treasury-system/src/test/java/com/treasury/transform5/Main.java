package com.treasury.transform5;

import org.apache.flink.api.common.functions.FlatMapFunction;
import org.apache.flink.api.common.functions.MapFunction;
import org.apache.flink.streaming.api.datastream.DataStream;
import org.apache.flink.streaming.api.datastream.SingleOutputStreamOperator;
import org.apache.flink.streaming.api.environment.StreamExecutionEnvironment;
import org.apache.flink.util.Collector;

public class Main {
    public static void main(String[] args) throws Exception {
        // 算子  map  flatMap  filter

        StreamExecutionEnvironment env = StreamExecutionEnvironment.getExecutionEnvironment();
        DataStream<String> source = env.readTextFile("/Users/a/Desktop/sys_menu.csv");
//        map(source);
//        flatMap(source);
        filter(source);


        env.execute();
    }


    private static void map2(DataStream<String> source) {
        // lambda 表达式
        DataStream<String> out = source.map(line -> line);
    }

    private static void map(DataStream<String> source) {
        DataStream<String> out = source.map(new MapFunction<String, String>() {
            @Override
            public String map(String s) throws Exception {
                return s; // 返回所有的记录
            }
        });
        out.print("map");
    }

    private static void filter(DataStream<String> source) {
       /* SingleOutputStreamOperator<String> out3 = source.filter(new FilterFunction<String>() {
            @Override
            public boolean filter(String s) throws Exception {
                // 过滤出来数据中包含 menu 的记录
                if (s.contains("menu")) {
                    return true;
                }
                return false; // 数据过滤
            }
        });*/

        SingleOutputStreamOperator<String> out3 = source.filter(line -> {
            if (line.contains("menu")) {
                return true;
            }
            return false; // 数据过滤
        });
        out3.print("filter");
    }

    private static void flatMap(DataStream<String> source) {
        SingleOutputStreamOperator<String> out2 = source.flatMap(new FlatMapFunction<String, String>() {
            @Override
            public void flatMap(String s, Collector<String> collector) throws Exception {
                String[] strings = s.split(",");
                for (String word : strings) {
                    if (word.contains("/")) {
                        collector.collect(word);
                    }
                }
            }
        });

        out2.print("flatMap");
    }
}

package com.treasury.keyby6;

import org.apache.flink.api.common.functions.ReduceFunction;
import org.apache.flink.api.java.tuple.Tuple;
import org.apache.flink.streaming.api.datastream.DataStream;
import org.apache.flink.streaming.api.datastream.KeyedStream;
import org.apache.flink.streaming.api.datastream.SingleOutputStreamOperator;
import org.apache.flink.streaming.api.environment.StreamExecutionEnvironment;

public class Main {
    public static void main(String[] args) throws Exception {


        // keyby  聚合操作
//        sum
//        min
//        max


        StreamExecutionEnvironment env = StreamExecutionEnvironment.getExecutionEnvironment();
        env.setParallelism(1);
        DataStream<String> source = env.readTextFile("/Users/a/Desktop/sys_menu.csv");


        DataStream<Menu> out = source.map(line -> {
            String[] strings = line.split(",");
            Menu menu = new Menu(Integer.parseInt(strings[0], 10), strings[4], Integer.parseInt(strings[7], 10));
            return menu;
        });

//        out.print("map");


        KeyedStream<Menu, Tuple> sort = out.keyBy("id");
//        KeyedStream<Menu, Integer> sort2 = out.keyBy(Menu::getId);

//        SingleOutputStreamOperator<Menu> sort1 = sort.max("sort");
        // 滚动聚合
        SingleOutputStreamOperator<Menu> sort1 = sort.maxBy("sort");
        sort1.print("max");

        // 替换记录中的值
        SingleOutputStreamOperator<Menu> reduce = sort.reduce(new ReduceFunction<Menu>() {
            @Override
            public Menu reduce(Menu old, Menu m2) throws Exception {
                return new Menu(old.getId(), m2.getTitle(), Math.max(old.getSort(), m2.getSort())); // 返回一个最新的记录 属性
            }
        });

        reduce.print("reduce");


        env.execute();
    }
}

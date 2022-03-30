package com.treasury.qiegeliu7;

import com.treasury.keyby6.Menu;
import org.apache.flink.api.common.functions.MapFunction;
import org.apache.flink.api.common.functions.RichMapFunction;
import org.apache.flink.api.java.tuple.Tuple;
import org.apache.flink.configuration.Configuration;
import org.apache.flink.streaming.api.datastream.DataStream;
import org.apache.flink.streaming.api.datastream.DataStreamSource;
import org.apache.flink.streaming.api.datastream.KeyedStream;
import org.apache.flink.streaming.api.environment.StreamExecutionEnvironment;

public class Main {

    public static void main(String[] args) {
        /*
         * 切割流
         * split select
         * connect CoMap
         * Union
         * */


        StreamExecutionEnvironment env = StreamExecutionEnvironment.getExecutionEnvironment();
        env.setParallelism(1);
        DataStreamSource<String> source = env.readTextFile("/Users/a/Desktop/sys_menu.csv");


        DataStream<Menu> out = source.map(line -> {
            String[] strings = line.split(",");
            Menu menu = new Menu(Integer.parseInt(strings[0], 10), strings[4], Integer.parseInt(strings[7], 10));
            return menu;
        });


        // split 分流操作  没有这个方法了
        KeyedStream<Menu, Tuple> stream = out.keyBy("id");
//        out.connect()          ;   // 合并流












        source.map(new MapFunction<String, Integer>() {
            @Override
            public Integer map(String s) throws Exception {

                return null;
            }
        });



        source.map(new RichMapFunction<String, Integer>() {
            @Override
            public Integer map(String s) throws Exception {
                return null;
            }

            @Override
            public void open(Configuration parameters) throws Exception {
//                打开链接
             }

            @Override
            public void close() throws Exception {
                super.close();
            }
        });


    }


}

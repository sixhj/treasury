package com.treasury.f2;

import org.apache.flink.streaming.api.datastream.DataStreamSource;
import org.apache.flink.streaming.api.environment.StreamExecutionEnvironment;

public class Main {
    public static void main(String[] args) throws Exception {

        StreamExecutionEnvironment env = StreamExecutionEnvironment.getExecutionEnvironment();

        // 从文件读取
        DataStreamSource<String> source = env.readTextFile("");

        source.print("file");

        env.execute("1");
    }
}

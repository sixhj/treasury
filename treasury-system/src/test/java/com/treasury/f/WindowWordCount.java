package com.treasury.f;

import org.apache.flink.api.common.functions.FlatMapFunction;
import org.apache.flink.api.java.tuple.Tuple2;
import org.apache.flink.streaming.api.datastream.DataStream;
import org.apache.flink.streaming.api.datastream.SingleOutputStreamOperator;
import org.apache.flink.streaming.api.environment.StreamExecutionEnvironment;
import org.apache.flink.streaming.api.windowing.assigners.TumblingEventTimeWindows;
import org.apache.flink.streaming.api.windowing.time.Time;
import org.apache.flink.util.Collector;
public class WindowWordCount {

    public static void main(String[] args) throws Exception {
        StreamExecutionEnvironment env = StreamExecutionEnvironment.getExecutionEnvironment();
        SingleOutputStreamOperator<Tuple2<String, Integer>> dataStream = env.socketTextStream("127.0.0.1", 11123)
                .flatMap(new FlatMapFunction<String, Tuple2<String, Integer>>() {
                    // 处理函数，处理接受到的数据
                    @Override
                    public void flatMap(String s, Collector<Tuple2<String, Integer>> collector) throws Exception {
                        for (String word :s.split(" ")){
                            collector.collect(new Tuple2<String, Integer>(word,1));
                        }
                    }
                })
                // f0  是返回的结果的第 0 个位置，
                .keyBy(value -> value.f0)
                .window(TumblingEventTimeWindows.of(Time.seconds(5)))
                // sume 1 是统计 返回结果 第1个位置的和
                .sum(1);


        dataStream.print();
        env.execute("window word count");
        // nc -lk 9999
    }
}

package com.treasury.f.wordcount;


import org.apache.flink.api.common.functions.FlatMapFunction;
import org.apache.flink.api.java.ExecutionEnvironment;
import org.apache.flink.api.java.operators.AggregateOperator;
import org.apache.flink.api.java.operators.DataSource;
import org.apache.flink.api.java.tuple.Tuple2;
import org.apache.flink.util.Collector;

public class WordCount {
    public static void main(String[] args) throws Exception {
        System.out.println("hello");

        // 创建执行环境
//        ExecutionEnvironment env = ExecutionEnvironment.createRemoteEnvironment("localhost",6123);

        ExecutionEnvironment env = ExecutionEnvironment.getExecutionEnvironment();
        env.setParallelism(1);
//         env.getCheckpointConfig().enableExternalizedCheckpoints(CheckpointConfig.ExternalizedCheckpointCleanup.RETAIN_ON_CANCELLATION);
        // 从文件读取数据
        String filePath = "/Users/a/IdeaProjects/eladmin/eladmin-system/src/test/resources/tmp.txt";
        DataSource<String> dataSource = env.readTextFile(filePath);


        // 按照空格分词  转换为元组 (hello,1)
        // MapFunction 只适用于一对一的转换：对每个进入算子的流元素，map() 将仅输出一个转换后的元素。对于除此以外的场景，你将要使用 flatmap()。
        // flatmap() 可以输出你想要的任意数量的元素，也可以一个都不

        // groupBy 0  sum 1  ,都是表示的位置
        AggregateOperator<Tuple2<String, Integer>> result = dataSource.flatMap(new MyFlatMap()).groupBy(0).sum(1);
        result.print();
    }
}



class MyFlatMap implements FlatMapFunction<String, Tuple2<String, Integer>> {

    @Override
    public void flatMap(String value, Collector<Tuple2<String, Integer>> out) throws Exception {
        String[] words = value.split(" "); // 空格分词
        for (String word : words) { // 遍历每个单词， 组装成 元组类型
            out.collect(new Tuple2<>(word, 1));
        }
    }
}


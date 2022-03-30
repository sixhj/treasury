package com.treasury.source4;

import com.treasury.f1.SensorReading;
import org.apache.flink.streaming.api.datastream.DataStreamSource;
import org.apache.flink.streaming.api.environment.StreamExecutionEnvironment;
import org.apache.flink.streaming.api.functions.source.SourceFunction;

import java.util.HashMap;
import java.util.Random;

public class Main {
    public static void main(String[] args) throws Exception {
        System.out.println("hello");


        StreamExecutionEnvironment env = StreamExecutionEnvironment.getExecutionEnvironment();

        // 自定义数据源
        //  SensorReading  从队列里面获取的数据源 的记录对象
        DataStreamSource<SensorReading> source = env.addSource(new SourceFunction<SensorReading>() {
            // 定义标志 控制数据产生

            private boolean running = true;

            @Override
            public void run(SourceContext<SensorReading> sourceContext) throws Exception {

                Random random = new Random();
                HashMap<String, Double> map = new HashMap<>();
                for (int i = 0; i < 10; i++) {
                    // nextGaussian 高斯分布
                    map.put("sensor-" + (i + 1), 60 + random.nextGaussian() * 20);
                }
                while (running) {
                    // 生成数据
                    for (String id : map.keySet()) {
                        Double tmp = map.get(id) + random.nextGaussian();
                        map.put(id, tmp);
                        sourceContext.collect(new SensorReading(id,System.currentTimeMillis(),tmp));
                    }
                }
                Thread.sleep(1000L);
            }

            @Override
            public void cancel() {

            }
        });

        source.print();

        env.execute();
    }
}

package com.treasury.f1;


import org.apache.flink.streaming.api.datastream.DataStream;
import org.apache.flink.streaming.api.environment.StreamExecutionEnvironment;

import java.util.Arrays;

public class Main {
    public static void main(String[] args) throws Exception {

        StreamExecutionEnvironment env = StreamExecutionEnvironment.getExecutionEnvironment();

        DataStream<SensorReading> streamSource = env.fromCollection(Arrays.asList(
                new SensorReading("s1", 1641894982091L, 1.2),
                new SensorReading("s2", 1641894982092L, 1.21),
                new SensorReading("s3", 1641894982093L, 1.22),
                new SensorReading("s4", 1641894982094L, 1.23)
        ));

        streamSource.print("reading");
        env.execute("job name");
    }
}

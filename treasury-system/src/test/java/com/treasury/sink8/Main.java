package com.treasury.sink8;

import com.treasury.keyby6.Menu;
import org.apache.flink.api.common.serialization.SimpleStringSchema;
import org.apache.flink.connector.pulsar.source.PulsarSource;
import org.apache.flink.connector.pulsar.source.enumerator.cursor.StartCursor;
import org.apache.flink.connector.pulsar.source.reader.deserializer.PulsarDeserializationSchema;
import org.apache.flink.streaming.api.datastream.DataStream;
import org.apache.flink.streaming.api.environment.StreamExecutionEnvironment;
import org.apache.pulsar.client.api.SubscriptionType;


public class Main {
    public static void main(String[] args) throws Exception {
        StreamExecutionEnvironment env = StreamExecutionEnvironment.getExecutionEnvironment();
        DataStream<String> source = env.readTextFile("/Users/a/Desktop/sys_menu.csv");


        String serviceUrl = "";
        String adminUrl = "";
        PulsarSource<String> pulsarSource = PulsarSource.builder().setServiceUrl(serviceUrl).setAdminUrl(adminUrl).setStartCursor(StartCursor.earliest()).setTopics("my-topic").setDeserializationSchema(PulsarDeserializationSchema.flinkSchema(new SimpleStringSchema())).setSubscriptionName("my-subscription").setSubscriptionType(SubscriptionType.Exclusive).build();

//        env.fromSource(source, WatermarkStrategy.noWatermarks(), "Pulsar Source");


        DataStream<Menu> out = source.map(line -> {
            String[] strings = line.split(",");
            Menu menu = new Menu(Integer.parseInt(strings[0], 10), strings[4], Integer.parseInt(strings[7], 10));
            return menu;
        });


        out.print();
        // 过滤的结果存入mysql
        out.addSink(new SinkToMySQL());
        env.execute();
        //  SinkFunction  存储数据的地方  mysql
//         out.addSink(new SinkFunction<Menu>() {
//             @Override
//             public void invoke(Menu value, Context context) throws Exception {
//                 // 写入数据
//                 SinkFunction.super.invoke(value, context);
//             }
//
//             @Override
//             public void writeWatermark(Watermark watermark) throws Exception {
//                 SinkFunction.super.writeWatermark(watermark);
//             }
//
//             @Override
//             public void finish() throws Exception {
//                 SinkFunction.super.finish();
//             }
//         });


    }
}




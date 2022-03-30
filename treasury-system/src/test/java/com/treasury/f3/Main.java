package com.treasury.f3;

import org.apache.flink.api.common.serialization.SimpleStringSchema;
import org.apache.flink.connector.pulsar.source.PulsarSource;
import org.apache.flink.connector.pulsar.source.PulsarSourceBuilder;
import org.apache.flink.connector.pulsar.source.enumerator.cursor.StartCursor;
import org.apache.flink.connector.pulsar.source.reader.deserializer.PulsarDeserializationSchema;
import org.apache.flink.streaming.api.environment.StreamExecutionEnvironment;
import org.apache.pulsar.client.api.SubscriptionType;

public class Main {
    public static void main(String[] args) throws Exception {

        // 从kafka 读取数据

        StreamExecutionEnvironment env = StreamExecutionEnvironment.getExecutionEnvironment();

//        getPulsarSource();






        env.execute();
    }

    private static void getPulsarSource() {
        String adminUrl = "";
        String serviceUrl = "";
        PulsarSource<String> pulsarSource = PulsarSource.builder()
                .setServiceUrl(serviceUrl)
                .setAdminUrl(adminUrl)
                .setStartCursor(StartCursor.earliest())
                .setTopics("my-topic")
                .setDeserializationSchema(PulsarDeserializationSchema.flinkSchema(new SimpleStringSchema()))
                .setSubscriptionName("my-subscription")
                .setSubscriptionType(SubscriptionType.Exclusive)
                .build();
    }
}

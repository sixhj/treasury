package com.treasury.p;

import com.treasury.keyby6.Menu;
import org.apache.pulsar.client.api.*;
import org.junit.Test;

import java.text.SimpleDateFormat;
import java.util.Date;

public class PulsarTest {

    @Test
    public void Conn() throws PulsarClientException {
        PulsarClient client = PulsarClient.builder()
                .serviceUrl("pulsar://127.0.0.1:6650")
                .build();


        boolean closed = client.isClosed();
        System.out.println(closed);
        Producer<byte[]> producer = client.newProducer()
                .topic("my-topic")
                .create();

        producer.send("My message".getBytes());
        SimpleDateFormat sdf = new SimpleDateFormat();// 格式化时间
        sdf.applyPattern("yyyy-MM-dd HH:mm:ss");// a为am/pm的标记
        Date date = new Date();// 获取当前时间
        System.out.println("现在时间：" + sdf.format(date)); // 输出已经格式化的现在时间（24小时制）


        producer.send(sdf.format(date).getBytes());


        // 使用字符串
        Producer<String> p2 = client.newProducer(Schema.STRING).topic("my-string").create();
        p2.send("message");

        //   异步关闭
        producer.closeAsync()
                .thenRun(() -> System.out.println("Producer closed"))
                .exceptionally((ex) -> {
                    System.err.println("Failed to close producer: " + ex);
                    return null;
                });
    }

    @Test
    public void Consumer() throws PulsarClientException {
        PulsarClient client = PulsarClient.builder()
                .serviceUrl("pulsar://127.0.0.1:6650")
                .build();


        Consumer consumer = client.newConsumer()
                .topic("my-topic")
                .subscriptionName("my-subscription")
                .subscribe();

        while (true) {
            // Wait for a message
            Message msg = consumer.receive();

            try {
                // Do something with the message
                System.out.println("Message received: " + new String(msg.getData()));

                // Acknowledge the message so that it can be deleted by the message broker
                consumer.acknowledge(msg);
            } catch (Exception e) {
                // Message failed to process, redeliver later
                consumer.negativeAcknowledge(msg);
            }
        }

    }


    @Test
    public void  JsonTest(){
        Menu menu = new Menu(1, "title", 1);

    }

}

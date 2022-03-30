package com.treasury.rabbitmq;








//import com.rabbitmq.client.Channel;
//import com.rabbitmq.client.Connection;
//import com.rabbitmq.client.ConnectionFactory;

import java.io.IOException;
import java.util.concurrent.TimeoutException;

public class Main {
    public static void main(String[] args) throws IOException, TimeoutException {
        // http://localhost:15672/#/  admin admin
/*
        ConnectionFactory factory = new ConnectionFactory();

        factory.setUsername("admin");
        factory.setPassword("admin");
        factory.setVirtualHost("localhost");
        factory.setHost("127.0.0.1");
        factory.setPort(5672);
        //factory.setUri("amqp://userName:password@hostName:portNumber/virtualHost");


        Connection connection = factory.newConnection();
        Channel channel = connection.createChannel();

        byte[] messageBodyBytes = "Hello, world!".getBytes();
        channel.basicPublish("hello", "hello-key", null, messageBodyBytes);

        channel.close();
        connection.close();*/
    }
}

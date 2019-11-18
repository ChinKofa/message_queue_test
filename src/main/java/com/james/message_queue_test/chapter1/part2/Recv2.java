package com.james.message_queue_test.chapter1.part2;

import com.rabbitmq.client.Channel;
import com.rabbitmq.client.Connection;
import com.rabbitmq.client.ConnectionFactory;
import com.rabbitmq.client.DeliverCallback;

public class Recv2 {
    private final static String QUEUE_NAME = "Beautiful";

    public static void main(String[] args) throws Exception{
        ConnectionFactory factory = new ConnectionFactory();
        factory.setHost("192.168.8.10");
        Connection connection = factory.newConnection();
        Channel channel = connection.createChannel();
        channel.queueDeclare(QUEUE_NAME, false, false, false, null);

        DeliverCallback deliverCallback = (consumerTag, delivery) -> {
            String message = new String(delivery.getBody(), "utf-8");
            System.out.println("[x] Received " + message);

            try {
                new Worker().doWork(message);
            } catch (InterruptedException e) {
                e.printStackTrace();
            } finally {
                System.out.println("[x] done");
            }
        };

        boolean autoAck = true;
        channel.basicConsume(QUEUE_NAME, autoAck, deliverCallback, c -> {});

    }
}

package com.james.message_queue_test.chapter1.workqueues;

import com.rabbitmq.client.Channel;
import com.rabbitmq.client.Connection;
import com.rabbitmq.client.ConnectionFactory;
import com.rabbitmq.client.DeliverCallback;

public class Recv2 {
    private final static String QUEUE_NAME = "Beautiful1";

    public static void main(String[] args) throws Exception{
        ConnectionFactory factory = new ConnectionFactory();
        factory.setHost("192.168.0.22");
        Connection connection = factory.newConnection();
        Channel channel = connection.createChannel();

        // 消息持久化
        boolean durable = true;
        channel.queueDeclare(QUEUE_NAME, durable, false, false, null);

        DeliverCallback deliverCallback = (consumerTag, delivery) -> {
            String message = new String(delivery.getBody(), "utf-8");
            System.out.println("[x] Received " + message);

            try {
                new Worker().doWork(message);
            } catch (InterruptedException e) {
                e.printStackTrace();
            } finally {
                System.out.println("[x] done");
                // 消息应答
                channel.basicAck(delivery.getEnvelope().getDeliveryTag(), false);
            }
        };

        int prefetchCount = 1;
        channel.basicQos(prefetchCount);
        boolean autoAck = false;
        channel.basicConsume(QUEUE_NAME, autoAck, deliverCallback, c -> {});

    }
}

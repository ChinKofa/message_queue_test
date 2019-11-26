package com.james.message_queue_test.chapter1.pubsub;

import com.rabbitmq.client.*;

import java.io.IOException;
import java.util.concurrent.TimeoutException;

/**
 * @author: qinkefa
 * @Date: 2019/11/26 0026 16:56
 */
public class ReceiveLogs {
    private final static String EXCHANGE_NAME = "logs";

    public static void main(String[] args) throws IOException, TimeoutException {
        ConnectionFactory factory = new ConnectionFactory();
        factory.setHost("192.168.0.22");

        Connection connection = factory.newConnection();
        Channel channel = connection.createChannel();

        channel.exchangeDeclare(EXCHANGE_NAME, "fanout");
        String queueName = channel.queueDeclare().getQueue();
        channel.queueBind(queueName, EXCHANGE_NAME, "");

        System.out.println(" [*] Waiting for messages. To exit press CTRL+C");

        DeliverCallback deliverCallback = ((consumerTag, delivery) -> {
           String message = new String(delivery.getBody(), "utf-8");

            System.out.println(" [x] Received '" + message + "'");
        });

        channel.basicConsume(queueName, true, deliverCallback, consumerTag -> {});
    }
}

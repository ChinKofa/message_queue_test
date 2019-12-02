package com.james.message_queue_test.chapter1.routing;

import com.rabbitmq.client.Channel;
import com.rabbitmq.client.Connection;
import com.rabbitmq.client.ConnectionFactory;
import com.rabbitmq.client.DeliverCallback;

import java.io.IOException;
import java.util.concurrent.TimeoutException;

/**
 * @author: qinkefa
 * @Date: 2019/11/29 0029 16:48
 */
public class ReceiveLogsDirect {

    private static final String EXCHANGE_NAME = "direct_logs";
    private final static String [] severity = new String[]{"info", "warning", "error"};

    public static void main(String[] args) throws IOException, TimeoutException {
        ConnectionFactory factory = new ConnectionFactory();
        factory.setHost("192.168.0.22");
        Connection connection = factory.newConnection();
        Channel channel = connection.createChannel();
        channel.exchangeDeclare(EXCHANGE_NAME, "direct");

        String queueName = channel.queueDeclare().getQueue();

        for(String s : severity){
            channel.queueBind(queueName, EXCHANGE_NAME, s);
        }

        DeliverCallback deliverCallback = (consumerTag, delivery) -> {
            String message = new String(delivery.getBody(), "utf-8");
            System.out.println("Received '"+delivery.getEnvelope().getRoutingKey()+"':'"+message+"'");
        };

        channel.basicConsume(queueName, true, deliverCallback, consumerTag -> {});

    }
}

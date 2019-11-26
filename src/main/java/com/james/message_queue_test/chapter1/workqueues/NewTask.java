package com.james.message_queue_test.chapter1.workqueues;

import com.rabbitmq.client.Channel;
import com.rabbitmq.client.Connection;
import com.rabbitmq.client.ConnectionFactory;
import com.rabbitmq.client.MessageProperties;

import java.io.IOException;
import java.util.concurrent.TimeoutException;

public class NewTask {
    private final static String QUEUE_NAME = "Beautiful1";

    public static void main(String[] args) {
        String message = String.join(" ", args);
        ConnectionFactory factory = new ConnectionFactory();
        factory.setHost("192.168.0.22");

        try(Connection connection = factory.newConnection();
            Channel channel = connection.createChannel();) {
            boolean durable = true;
            channel.queueDeclare(QUEUE_NAME, durable, false, false, null);
            channel.basicPublish("", QUEUE_NAME, MessageProperties.PERSISTENT_TEXT_PLAIN, message.getBytes("utf-8"));
            System.out.println("[x] Sent '" + message +"'");
        } catch (TimeoutException e) {
            e.printStackTrace();
        } catch (IOException e) {
            e.printStackTrace();
        }
    }
}
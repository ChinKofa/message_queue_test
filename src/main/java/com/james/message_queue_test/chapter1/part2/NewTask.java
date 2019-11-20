package com.james.message_queue_test.chapter1.part2;

import com.rabbitmq.client.Channel;
import com.rabbitmq.client.Connection;
import com.rabbitmq.client.ConnectionFactory;

import java.io.IOException;
import java.util.concurrent.TimeoutException;

public class NewTask {
    private final static String QUEUE_NAME = "Beautiful";

    public static void main(String[] args) {
        String message = String.join(" ", args);
        ConnectionFactory factory = new ConnectionFactory();
        factory.setHost("192.168.0.22");

        try(Connection connection = factory.newConnection();
            Channel channel = connection.createChannel();) {
            channel.queueDeclare(QUEUE_NAME, false, false, false, null);
            channel.basicPublish("", QUEUE_NAME, null, message.getBytes());
            System.out.println("[x] Sent '" + message +"'");
        } catch (TimeoutException e) {
            e.printStackTrace();
        } catch (IOException e) {
            e.printStackTrace();
        }
    }
}
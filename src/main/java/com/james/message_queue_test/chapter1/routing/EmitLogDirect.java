package com.james.message_queue_test.chapter1.routing;

import com.rabbitmq.client.Channel;
import com.rabbitmq.client.Connection;
import com.rabbitmq.client.ConnectionFactory;

import java.io.IOException;
import java.util.concurrent.TimeoutException;

/**
 * @author: qinkefa
 * @Date: 2019/11/29 0029 16:40
 */
public class EmitLogDirect {

    private final static String EXCHANGE_NAME = "direct_logs";
    private final static String [] severity = new String[]{"info", "warning", "error"};
    private final static String [] message = new String[]{"beautiful", "girl", "slim"};

    public static void main(String[] args) throws IOException, TimeoutException {
        ConnectionFactory factory = new ConnectionFactory();
        factory.setHost("192.168.0.22");
        try(Connection connection = factory.newConnection();
            Channel channel = connection.createChannel()){
            channel.exchangeDeclare(EXCHANGE_NAME, "direct");
            channel.basicPublish(EXCHANGE_NAME, severity[1], null, message[1].getBytes());
            System.out.println(" [x] Sent '" + severity[1] + "':'" + message[1] + "'");
        }

    }
}

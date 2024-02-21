package com.ithy.work.fair;

import com.rabbitmq.client.*;

import java.io.IOException;
import java.util.concurrent.TimeUnit;

public class Recv2 {

    private final static String QUEUE_NAME = "work_mq_fair";

    public static void main(String[] argv) throws Exception {
        ConnectionFactory factory = new ConnectionFactory();
        factory.setHost("192.168.204.130"); //ip地址
        factory.setUsername("itcast"); //使用名
        factory.setPassword("123321"); //密码
        factory.setVirtualHost("/dev");  //虚拟主机
        factory.setPort(5672); //端口号
        Connection connection = factory.newConnection();
        Channel channel = connection.createChannel();

        channel.queueDeclare(QUEUE_NAME, false, false, false, null);
        System.out.println(" [*] Waiting for messages. To exit press CTRL+C");
        /**
         * 限制消费者每次消费1个,消费完成再消费下一个
         */
        channel.basicQos(1);
        Consumer consumer = new DefaultConsumer(channel){
            @Override
            public void handleDelivery(String consumerTag, Envelope envelope, AMQP.BasicProperties properties, byte[] body) throws IOException {
                try {
                    TimeUnit.SECONDS.sleep(1);
                } catch (InterruptedException e) {
                    e.printStackTrace();
                }

                System.out.println("body="+new String(body,"utf-8"));

                //手动确认消息，不是多条确认
                channel.basicAck(envelope.getDeliveryTag(), false);
            }
        };
        //消费,关闭消息自动确认
        channel.basicConsume(QUEUE_NAME,false,consumer);

/*        DeliverCallback deliverCallback = (consumerTag, delivery) -> {
            String message = new String(delivery.getBody(), StandardCharsets.UTF_8);
            System.out.println(" [x] Received '" + message + "'");
        };
        channel.basicConsume(QUEUE_NAME, true, deliverCallback, consumerTag -> { });*/
    }
}
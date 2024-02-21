package com.ithy.topic;

import com.rabbitmq.client.*;

import java.io.IOException;

public class Recv1 {

    private final static String EXCHANGE_NAME = "exchange_topic";

    public static void main(String[] argv) throws Exception {
        ConnectionFactory factory = new ConnectionFactory();
        factory.setHost("192.168.204.130"); //ip地址
        factory.setUsername("itcast"); //使用名
        factory.setPassword("123321"); //密码
        factory.setVirtualHost("/dev");  //虚拟主机
        factory.setPort(5672); //端口号
        Connection connection = factory.newConnection();
        Channel channel = connection.createChannel();

        //绑定交换机，fanout扇形
        channel.exchangeDeclare(EXCHANGE_NAME,BuiltinExchangeType.TOPIC);

        //获取队列
        String queueName = channel.queueDeclare().getQueue();

        //绑定交换机和队列，direct交换机需要指定routingKey
        channel.queueBind(queueName,EXCHANGE_NAME,"order.log.errorRoutingKey");

        Consumer consumer = new DefaultConsumer(channel){
            @Override
            public void handleDelivery(String consumerTag, Envelope envelope, AMQP.BasicProperties properties, byte[] body) throws IOException {
                System.out.println("body="+new String(body,"utf-8"));

                //手动确认消息，不是多条确认
                channel.basicAck(envelope.getDeliveryTag(), false);
            }
        };
        //消费,关闭消息自动确认
        channel.basicConsume(queueName,false,consumer);

    }
}
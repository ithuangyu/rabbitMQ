package com.ithy.publish;

import com.rabbitmq.client.BuiltinExchangeType;
import com.rabbitmq.client.Channel;
import com.rabbitmq.client.Connection;
import com.rabbitmq.client.ConnectionFactory;

import java.nio.charset.StandardCharsets;

/**
 * @ Description:
 * @ date 2022/9/13 14:23
 * @ author ithy
 * @ version 1.0
 */
public class Send {

    private final static String EXCHANGE_NAME = "exchange_fanout";

    public static void main(String[] argv) throws Exception {
        //建立连接工厂
        ConnectionFactory factory = new ConnectionFactory();
        factory.setHost("192.168.204.130"); //ip地址
        factory.setUsername("itcast"); //使用名
        factory.setPassword("123321"); //密码
        factory.setVirtualHost("/dev");  //虚拟主机
        factory.setPort(5672); //端口号

        //JDK7语法，自动关闭，创建连接
        try (Connection connection = factory.newConnection();
             //创建信道
             Channel channel = connection.createChannel()) {

            //绑定交换机，fanout扇形，广播
            channel.exchangeDeclare(EXCHANGE_NAME, BuiltinExchangeType.FANOUT);

            /**
             * exchange:交换机名称
             * routingKey:路由键
             * props:消息的其他属性
             * body:内容
             */
            String msg = "超喜欢你";
            channel.basicPublish(EXCHANGE_NAME,"",null,msg.getBytes(StandardCharsets.UTF_8));

            System.out.println("广播消息发送成功");
        }
    }
}
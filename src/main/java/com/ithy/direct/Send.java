package com.ithy.direct;

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

    private final static String EXCHANGE_NAME = "exchange_direct";

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

            //绑定交换机，直连交换机
            channel.exchangeDeclare(EXCHANGE_NAME, BuiltinExchangeType.DIRECT);

            String error ="这个是我们的error日志";
            String info ="这个是我们的info日志";
            String debug ="这个是我们的debug日志";


            /**
             * exchange:交换机名称
             * routingKey:路由键
             * props:消息的其他属性
             * body:内容
             */
            channel.basicPublish(EXCHANGE_NAME,"errorRoutingKey",null,error.getBytes(StandardCharsets.UTF_8));
            channel.basicPublish(EXCHANGE_NAME,"infoRoutingKey",null,info.getBytes(StandardCharsets.UTF_8));
            channel.basicPublish(EXCHANGE_NAME,"debugRoutingKey",null,debug.getBytes(StandardCharsets.UTF_8));

            System.out.println("direct消息发送成功");
        }
    }
}
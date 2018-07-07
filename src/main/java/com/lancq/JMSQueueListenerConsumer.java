package com.lancq;

import org.apache.activemq.ActiveMQConnectionFactory;

import javax.jms.*;

/**
 * @Author lancq
 * @Description 监听式消息接收
 * @Date 2018/7/6
 **/
public class JMSQueueListenerConsumer {

    public static void main(String[] args) {
        //连接工厂
        ConnectionFactory connectionFactory = new ActiveMQConnectionFactory("tcp://192.168.227.129:61616");
        Connection connection = null;
        try {
            //创建连接
            connection = connectionFactory.createConnection();
            //开启连接
            connection.start();
            //创建会话
            Session session = connection.createSession(Boolean.TRUE, Session.AUTO_ACKNOWLEDGE);
            //创建目的地
            Destination destination = session.createQueue("MyQueue");
            //创建消费者
            MessageConsumer consumer = session.createConsumer(destination);

            //接收消息
            MessageListener messageListener = new MessageListener() {
                @Override
                public void onMessage(Message message) {
                    try {
                        System.out.println("message = [" + ((TextMessage)message).getText() + "]");
                    } catch (JMSException e) {
                        e.printStackTrace();
                    }
                }
            };
            while(true){
                consumer.setMessageListener(messageListener);
                session.commit();
            }

        } catch (JMSException e) {
            e.printStackTrace();
        } finally {
            try {
                connection.close();
            } catch (JMSException e) {
                e.printStackTrace();
            }
        }

    }
}

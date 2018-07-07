package com.lancq;

import org.apache.activemq.ActiveMQConnectionFactory;

import javax.jms.*;

/**
 * @Author lancq
 * @Description 消息订阅
 * @Date 2018/7/6
 **/
public class JMSTopicConsumer1 {

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
            Topic topic = session.createTopic("MyTopic");

            //创建消费者
            MessageConsumer consumer = session.createConsumer(topic);

            //接收消息
            TextMessage message = (TextMessage) consumer.receive();

            System.out.println("message = [" + message.getText() + "]");

            session.commit();

            session.close();
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

package com.lancq.prefetchsize;

import org.apache.activemq.ActiveMQConnectionFactory;

import javax.jms.*;

/**
 * @Author lancq
 * @Description 阻塞式消息接收
 * @Date 2018/7/6
 **/
public class PrefetchSizeQueueConsumer2 {

    public static void main(String[] args) {
        //连接工厂
        ConnectionFactory connectionFactory = new ActiveMQConnectionFactory(
                "tcp://192.168.227.129:61616?jms.optimizeAcknowledge=true&jms.optimizeAcknowledgeTimeOut=1000");
        Connection connection = null;
        try {
            //创建连接
            connection = connectionFactory.createConnection();
            //开启连接
            connection.start();
            //创建会话
            Session session = connection.createSession(Boolean.FALSE, Session.AUTO_ACKNOWLEDGE);//Session.CLIENT_ACKNOWLEDGE只能在消费端
            //创建目的地
            Destination destination = session.createQueue("MyQueue?consumer.prefetchSize=1");
            //创建消费者
            MessageConsumer consumer = session.createConsumer(destination);
            for(int i=0; i<1000; i++){
                //接收消息
                TextMessage message = (TextMessage) consumer.receive();
                System.out.println("message = [" + message.getText() + "]");
                try {
                    Thread.sleep(1000);
                } catch (InterruptedException e) {
                    e.printStackTrace();
                }
            }
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

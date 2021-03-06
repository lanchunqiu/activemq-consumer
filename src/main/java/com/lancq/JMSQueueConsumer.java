package com.lancq;

import org.apache.activemq.ActiveMQConnectionFactory;

import javax.jms.*;

/**
 * @Author lancq
 * @Description 阻塞式消息接收
 * @Date 2018/7/6
 **/
public class JMSQueueConsumer {

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
            Session session = connection.createSession(Boolean.FALSE, Session.CLIENT_ACKNOWLEDGE);//Session.CLIENT_ACKNOWLEDGE只能在消费端
            //创建目的地
            Destination destination = session.createQueue("MyQueue");
            //创建消费者
            MessageConsumer consumer = session.createConsumer(destination);
            for(int i=0; i<10; i++){
                //接收消息
                TextMessage message = (TextMessage) consumer.receive();
                System.out.println("message = [" + message.getText() + "]");
                if(i==4){
                    message.acknowledge();//只确认前5个消息，后面5个消息不确认
                }

            }


            //session.commit();//确认消息

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

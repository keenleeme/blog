package com.monster.blog.config.activemq;

import org.springframework.jms.annotation.JmsListener;
import org.springframework.messaging.handler.annotation.SendTo;
import org.springframework.stereotype.Component;

import javax.jms.ObjectMessage;
import java.util.List;

/**
 * @author liz
 * @Description 发布/订阅模式 主题消费者1
 * @date 2020/7/30-16:46
 */
@Component
public class TopicConsumer2 {

    @JmsListener(destination = "stringTopic", containerFactory = "jmsListenerContainerTopic")
    public void receiveStringTopic(String msg) {
        System.out.println("BTopicConsumer接收到消息...." + msg);
    }


    @JmsListener(destination = "stringListTopic", containerFactory = "jmsListenerContainerTopic")
    public void receiveStringListTopic(List<String> list) {
        System.out.println("BTopicConsumer接收到集合主题消息...." + list);
    }


    @JmsListener(destination = "objTopic", containerFactory = "jmsListenerContainerTopic")
    public void receiveObjTopic(ObjectMessage objectMessage) throws Exception {
        System.out.println("BTopicConsumer接收到对象主题消息...." + objectMessage.getObject());
    }


    @JmsListener(destination = "objListTopic", containerFactory = "jmsListenerContainerTopic")
    @SendTo("out.queue")
    public String receiveObjListTopic(ObjectMessage objectMessage) throws Exception {
        System.out.println("BTopicConsumer接收到的对象集合主题消息..." + objectMessage.getObject());
        return "BTopicConsumer:"+objectMessage.getObject();
    }

}

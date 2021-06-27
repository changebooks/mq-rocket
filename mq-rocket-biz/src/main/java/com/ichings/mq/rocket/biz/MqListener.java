package com.ichings.mq.rocket.biz;

import com.ichings.mq.rocket.infrastructure.AbstractMqListener;
import com.ichings.mq.rocket.infrastructure.MqConsumer;
import org.apache.rocketmq.spring.annotation.RocketMQMessageListener;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.stereotype.Service;

import java.util.List;

/**
 * 监听消息队列
 *
 * @author changebooks
 */
@RocketMQMessageListener(topic = MqName.TOPIC_NAME, consumerGroup = MqName.GROUP_NAME)
@Service
public class MqListener extends AbstractMqListener {

    @Autowired
    private List<MqConsumer> consumers;

    @Override
    public List<MqConsumer> getConsumers() {
        return consumers;
    }

}

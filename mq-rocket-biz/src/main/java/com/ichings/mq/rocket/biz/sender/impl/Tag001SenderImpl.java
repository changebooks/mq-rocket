package com.ichings.mq.rocket.biz.sender.impl;

import com.ichings.mq.rocket.biz.MqName;
import com.ichings.mq.rocket.biz.sender.Tag001Sender;
import com.ichings.mq.rocket.infrastructure.MqSender;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.stereotype.Service;

/**
 * @author changebooks
 */
@Service
public class Tag001SenderImpl implements Tag001Sender {

    @Autowired
    private MqSender sender;

    @Override
    public void send(String message) {
        sender.send(MqName.TOPIC_NAME, MqName.TAG_NAME, message);
    }

}

package com.ichings.mq.rocket.biz.consumer;

import com.ichings.mq.rocket.biz.MqName;
import com.ichings.mq.rocket.infrastructure.MqConsumer;
import org.slf4j.Logger;
import org.slf4j.LoggerFactory;
import org.springframework.stereotype.Service;

/**
 * @author changebooks
 */
@Service
public class Tag001Consumer implements MqConsumer {

    private static final Logger LOGGER = LoggerFactory.getLogger(Tag001Consumer.class);

    @Override
    public void onConsume(String message) {
        LOGGER.info("onConsume trace, message: {}", message);
    }

    @Override
    public String getTag() {
        return MqName.TAG_NAME;
    }

}

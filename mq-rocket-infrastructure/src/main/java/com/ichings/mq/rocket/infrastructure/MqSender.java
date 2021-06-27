package com.ichings.mq.rocket.infrastructure;

import org.apache.rocketmq.client.producer.SendCallback;
import org.apache.rocketmq.client.producer.SendResult;
import org.apache.rocketmq.spring.core.RocketMQTemplate;
import org.slf4j.Logger;
import org.slf4j.LoggerFactory;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.messaging.Message;
import org.springframework.messaging.support.MessageBuilder;
import org.springframework.stereotype.Service;
import org.springframework.util.StringUtils;

import java.util.Objects;

/**
 * 发送 RocketMQ 消息
 *
 * <pre>
 * <dependency>
 *     <groupId>org.apache.rocketmq</groupId>
 *     <artifactId>rocketmq-spring-boot-starter</artifactId>
 *     <exclusions>
 *         <exclusion>
 *             <groupId>org.springframework.boot</groupId>
 *             <artifactId>spring-boot-starter</artifactId>
 *         </exclusion>
 *     </exclusions>
 * </dependency>
 * </pre>
 * <pre>
 * # RocketMQ
 * rocketmq.name-server=127.0.0.1:9876
 * rocketmq.producer.group=
 * rocketmq.producer.send-message-timeout=3000
 * </pre>
 *
 * @author changebooks
 */
@Service
public class MqSender {

    private static final Logger LOGGER = LoggerFactory.getLogger(MqSender.class);

    /**
     * 主题名和标签名分隔符
     */
    private static final String TOPIC_TAG_SEPARATOR = ":";

    @Autowired
    private RocketMQTemplate template;

    /**
     * 发送消息
     *
     * @param topic 主题
     * @param tag   标签
     * @param data  消息内容
     */
    public <T> void send(String topic, String tag, T data) {
        send(topic, tag, data, null);
    }

    /**
     * 发送消息
     *
     * @param topic    主题
     * @param tag      标签
     * @param data     消息内容
     * @param callback 回调
     */
    public <T> void send(String topic, String tag, T data, SendCallback callback) {
        if (Objects.isNull(data)) {
            throw new IllegalArgumentException("data can't be null");
        }

        String destination = joinTopicAndTag(topic, tag);
        Message<T> message = MessageBuilder.withPayload(data).build();

        template.asyncSend(destination, message, new SendCallback() {
            @Override
            public void onSuccess(SendResult sendResult) {
                LOGGER.debug("send onSuccess, destination: {}, data: {}, sendResult: {}", destination, data, sendResult);
                if (Objects.nonNull(callback)) {
                    callback.onSuccess(sendResult);
                }
            }

            @Override
            public void onException(Throwable tr) {
                LOGGER.error("send onException, destination: {}, data: {}, throwable: ", destination, data, tr);
                if (Objects.nonNull(callback)) {
                    callback.onException(tr);
                }
            }
        });
    }

    /**
     * 拼接主题名和标签名
     *
     * @param topic 主题名
     * @param tag   标签名
     * @return topic:tag
     */
    public String joinTopicAndTag(String topic, String tag) {
        if (StringUtils.isEmpty(topic)) {
            throw new IllegalArgumentException("topic can't be empty");
        }

        if (StringUtils.isEmpty(tag)) {
            throw new IllegalArgumentException("tag can't be empty");
        }

        return topic + TOPIC_TAG_SEPARATOR + tag;
    }

}

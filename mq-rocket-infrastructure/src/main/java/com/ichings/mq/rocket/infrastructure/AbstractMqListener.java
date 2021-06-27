package com.ichings.mq.rocket.infrastructure;

import org.apache.rocketmq.common.message.MessageExt;
import org.apache.rocketmq.spring.core.RocketMQListener;
import org.slf4j.Logger;
import org.slf4j.LoggerFactory;
import org.springframework.util.CollectionUtils;
import org.springframework.util.StringUtils;

import java.nio.charset.Charset;
import java.nio.charset.StandardCharsets;
import java.util.List;
import java.util.Objects;

/**
 * 监听 RocketMQ 队列
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
 *
 * <pre>
 * # RocketMQ
 * rocketmq.name-server=127.0.0.1:9876
 * rocketmq.producer.group=
 * rocketmq.producer.send-message-timeout=3000
 * </pre>
 *
 * <pre>
 * RocketMQMessageListener(topic = TOPIC_NAME, consumerGroup = GROUP_NAME)
 * Service
 * </pre>
 *
 * @author changebooks
 */
public abstract class AbstractMqListener implements RocketMQListener<MessageExt> {

    private static final Logger LOGGER = LoggerFactory.getLogger(AbstractMqListener.class);

    /**
     * 默认的字符编码
     */
    private static final Charset DEFAULT_CHARSET = StandardCharsets.UTF_8;

    @Override
    public void onMessage(MessageExt message) {
        if (Objects.isNull(message)) {
            throw new IllegalArgumentException("message can't be null");
        }

        String id = message.getMsgId();
        String topic = message.getTopic();
        String tags = message.getTags();
        String keys = message.getKeys();
        int queueId = message.getQueueId();

        MqSchema schema = new MqSchema();
        schema.setId(id);
        schema.setTopic(topic);
        schema.setTags(tags);
        schema.setKeys(keys);
        schema.setQueueId(queueId);

        byte[] body = message.getBody();
        String data = new String(body, DEFAULT_CHARSET);

        try {
            onDispatch(schema.getTags(), data);
            LOGGER.info("onMessage trace, schema: {}, data: {}", schema, data);
        } catch (Throwable tr) {
            LOGGER.error("onMessage failed, schema: {}, data: {}, throwable: ", schema, data, tr);
        }
    }

    /**
     * 匹配标签，执行消费
     *
     * @param sendTag 发送消息的标签
     * @param message 消息内容
     */
    public void onDispatch(String sendTag, String message) {
        LOGGER.debug("onDispatch trace, sendTag: {}, message: {}", sendTag, message);

        if (StringUtils.isEmpty(sendTag)) {
            LOGGER.error("onDispatch failed, sendTag can't be empty, message: {}", message);
            return;
        }

        List<MqConsumer> consumers = getConsumers();
        if (CollectionUtils.isEmpty(consumers)) {
            LOGGER.error("onDispatch failed, consumers can't be empty, sendTag: {}, message: {}", sendTag, message);
            return;
        }

        for (MqConsumer c : consumers) {
            if (Objects.nonNull(c) && c.allowTag(sendTag)) {
                c.onConsume(message);
            }
        }
    }

    /**
     * 全部的消费者
     *
     * <pre>
     * Autowired
     * private List<MqConsumer> consumers;
     * </pre>
     *
     * @return MqConsumer
     */
    public abstract List<MqConsumer> getConsumers();

}

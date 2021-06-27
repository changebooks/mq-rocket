package com.ichings.mq.rocket.infrastructure;

import org.springframework.util.StringUtils;

/**
 * 消费者
 * <pre>
 * Autowired
 * private List<MqConsumer> consumers;
 *
 * for (MqConsumer c : consumers) {
 *     if (Check.nonNull(c) && c.allowTag(sendTag)) {
 *         c.onConsume(message);
 *     }
 * }
 * </pre>
 *
 * @author changebooks
 */
public interface MqConsumer {
    /**
     * 监听标签？
     *
     * @param sendTag 发送消息的标签
     * @return sendTag = consumeTag ?
     */
    default boolean allowTag(String sendTag) {
        if (StringUtils.isEmpty(sendTag)) {
            throw new IllegalArgumentException("sendTag can't be empty");
        }

        String consumeTag = getTag();
        if (StringUtils.isEmpty(consumeTag)) {
            throw new IllegalArgumentException("consumeTag can't be empty");
        }

        return consumeTag.equals(sendTag);
    }

    /**
     * 执行消费
     *
     * @param message 消息内容
     */
    void onConsume(String message);

    /**
     * 消费的标签
     *
     * @return 标签名
     */
    String getTag();

}

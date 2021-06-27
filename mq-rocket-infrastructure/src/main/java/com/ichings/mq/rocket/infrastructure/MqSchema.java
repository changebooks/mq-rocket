package com.ichings.mq.rocket.infrastructure;

/**
 * 消息概要
 *
 * @author changebooks
 */
public class MqSchema {
    /**
     * 消息id
     */
    private String id;

    /**
     * 分组名
     */
    private String group;

    /**
     * 主题名
     */
    private String topic;

    /**
     * 标签列表
     */
    private String tags;

    /**
     * 过滤标签
     */
    private String expression;

    /**
     * 键列表
     */
    private String keys;

    /**
     * 队列id
     */
    private int queueId;

    public String getId() {
        return id;
    }

    public void setId(String id) {
        this.id = id;
    }

    public String getGroup() {
        return group;
    }

    public void setGroup(String group) {
        this.group = group;
    }

    public String getTopic() {
        return topic;
    }

    public void setTopic(String topic) {
        this.topic = topic;
    }

    public String getTags() {
        return tags;
    }

    public void setTags(String tags) {
        this.tags = tags;
    }

    public String getExpression() {
        return expression;
    }

    public void setExpression(String expression) {
        this.expression = expression;
    }

    public String getKeys() {
        return keys;
    }

    public void setKeys(String keys) {
        this.keys = keys;
    }

    public int getQueueId() {
        return queueId;
    }

    public void setQueueId(int queueId) {
        this.queueId = queueId;
    }

}

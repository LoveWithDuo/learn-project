package com.dw.demo.mail;

import java.util.ArrayList;
import java.util.List;

/**
 * mail实体，使用Builder生成   例: new MailMessage.Builder().to(to).build();
 * @author fufei
 *
 */
public class MailMessage {
    private String from;
    private String fromName;
    private List<String> to;
    private List<String> cc;
    private String subject;

    MailMessage(Builder builder) {
        this.from = builder.from;
        this.to = builder.to;
        this.cc = builder.cc;
        this.subject = builder.subject;
        this.fromName = builder.fromName;
    }

    public static class Builder {
        private String from;
        private String fromName;
        private List<String> to = new ArrayList<String>();
        private List<String> cc = new ArrayList<String>();
        private String subject;

        public Builder() {

        }

        /**
         * 添加发送人信息，为空则需要调用方主动设置
         * @param from 发送人邮件字符串
         * @return
         */
        public Builder from(String from) {
            this.from = from;
            return this;
        }

        /**
         * 添加发送人信息，为空则需要调用方主动设置
         * @param from 发送人邮件字符串
         * @return
         */
        public Builder fromName(String fromName) {
            this.fromName = fromName;
            return this;
        }

        /**
         * 添加收件人
         * @param toAddr String
         * @return
         */
        public Builder addTo(String toAddr) {
            to.add(toAddr);
            return this;
        }

        /**
         * 添加收件人列表
         * @param toAddr String
         * @return
         */
        public Builder addTo(List<String> toAddr) {
            to.addAll(toAddr);
            return this;
        }

        /**
         * 设置收件人列表
         * @param to 收件人数组
         * @return
         */
        public Builder to(List<String> to) {
            this.to = to;
            return this;
        }

        /**
         * 添加抄送人
         * @param ccAddr
         * @return
         */
        public Builder addCc(String ccAddr) {
            cc.add(ccAddr);
            return this;
        }

        /**
         * 添加抄送人列表
         * @param ccAddr
         * @return
         */
        public Builder addCc(List<String> ccAddr) {
            cc.addAll(ccAddr);
            return this;
        }

        /**
         * 设置抄送人列表
         * @param cc
         * @return
         */
        public Builder cc(List<String> cc) {
            this.cc = cc;
            return this;
        }

        /**
         * 设置主题
         * @param subject
         * @return
         */
        public Builder subject(String subject) {
            this.subject = subject;
            return this;
        }

        /**
         * 生成MailMessage
         * @return MailMessage
         */
        public MailMessage build() {
            if (to.size() < 1)
                throw new IllegalStateException("邮件接收人为空!");
            return new MailMessage(this);
        }
    }

    public String getFrom() {
        return from;
    }

    public void setFrom(String from) {
        this.from = from;
    }

    public String getFromName() {
        return fromName;
    }

    public void setFromName(String fromName) {
        this.fromName = fromName;
    }

    public String[] getTo() {
        String[] array = new String[to.size()];
        String[] s = to.toArray(array);
        return s;
    }

    public String[] getCc() {
        if (cc.size() < 1)
            return null;
        String[] array = new String[cc.size()];
        String[] s = cc.toArray(array);
        return s;
    }

    public String getSubject() {
        return subject;
    }
}
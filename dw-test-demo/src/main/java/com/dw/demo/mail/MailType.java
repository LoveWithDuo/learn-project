package com.dw.demo.mail;
/**
 *  邮件类型
 * @Author: zhanzhihong
 * @Date: 2020/9/14 13:38
 * @version v1.0
 */
public abstract class MailType {
    /**
     * 文件类型
     */
    public final static char TYPE_FILE = 'F';
    /**
     * 附件类型
     */
    public final static char TYPE_ATTACH = 'A';
    /**
     * 文本类型
     */
    public final static char TYPE_TEXT = 'T';
    /**
     * json类型
     */
    public final static char TYPE_JSON = 'J';

    /**
     * 获取邮件类型
     * @return
     */
    public abstract char getType();
}
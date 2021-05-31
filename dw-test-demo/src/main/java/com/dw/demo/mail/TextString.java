package com.dw.demo.mail;
/**
 *
 * @Author: zhanzhihong
 * @Date: 2020/9/14 13:40
 * @version v1.0
 */

/**
 * 普通字符串
 * @author fufei
 *
 */
public class TextString extends MailType {
    private String text;

    public TextString() {
        super();
    }

    public TextString(String text) {
        super();
        this.text = text;
    }

    @Override
    public char getType() {
        return MailType.TYPE_TEXT;
    }

    public String getText() {
        return text;
    }

    public void setText(String text) {
        this.text = text;
    }

}
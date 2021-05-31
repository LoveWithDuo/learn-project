package com.dw.test;

import com.dw.demo.Application;
import com.dw.demo.mail.MailMessage;
import com.dw.demo.mail.MailType;
import com.dw.demo.mail.TextString;
import com.dw.demo.service.MailService;
import org.junit.Test;
import org.junit.runner.RunWith;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.boot.test.context.SpringBootTest;
import org.springframework.test.context.junit4.SpringRunner;

import java.util.ArrayList;
import java.util.List;

/**
 *
 * @Author: zhanzhihong
 * @Date: 2020/9/14 13:50
 * @version v1.0
 */
@SpringBootTest(classes = Application.class)
@RunWith(SpringRunner.class)
public class sendMail {
    @Autowired
    MailService mailService;


    @Test
    public void sendSimpleMail() {
        String content = "直接登录网页  看发邮件吗，不能发的话 asdasd";
        mailService.sendSimpleMail("1074501328@qq.com", content, content, true);
    }
    @Test
    public void sendCompleteHtml() throws Exception {
        String content = "测试完整的邮件发送";
        TextString text = new TextString();
        text.setText("测试");
        List<MailType> mailTypes = new ArrayList<>();
        mailTypes.add(text);
        List<String> to = new ArrayList<String>();
        to.add("1074501328@qq.com");
        MailMessage mailMessage = new MailMessage.Builder().from("1074501328@qq.com").fromName("rfmrqwdbbadzhbbd").to(to).subject("爱你就像爱生命").build();
        mailService.sendCompleteHtml(content, mailTypes, mailMessage);
    }
}
package com.baerwang.sms.listener;

import com.aliyuncs.exceptions.ClientException;
import com.baerwang.sms.utils.SmsUtil;
import org.springframework.amqp.rabbit.annotation.RabbitHandler;
import org.springframework.amqp.rabbit.annotation.RabbitListener;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.beans.factory.annotation.Value;
import org.springframework.stereotype.Component;

import java.util.Map;

/**
 * 监听mq，消息监听类
 *
 * @author baerwang
 * @version 1.0
 * @date 2020/2/21 16:06
 */
@Component
@RabbitListener(queues = "sms")
public class ConsumerListener {

    @Autowired
    private SmsUtil smsUtil;

    /**
     * 模板编号
     */
    @Value("${aliyun.sms.template_code}")
    private String template_code;
    /**
     * 签名
     */
    @Value("${aliyun.sms.sign_name}")
    private String sign_name;

    /**
     * 发送短信
     *
     * @param map
     */
    @RabbitHandler
    public void executeConsumer(Map<String, String> map) {
        System.out.println(template_code);
        System.out.println(sign_name);
        String mobile = map.get("mobile");
        String code = map.get("code");
        System.out.println("手机号：" + mobile);
        System.out.println("验证码：" + code);
       /*
        阿里短信发送
       try {
            smsUtil.sendSms(mobile, template_code, sign_name, " {\"code\":\"" + code + "\"}");
        } catch (ClientException e) {
            e.printStackTrace();
        }*/
    }
}

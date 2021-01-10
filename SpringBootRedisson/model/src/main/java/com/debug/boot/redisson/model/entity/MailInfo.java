package com.debug.boot.redisson.model.entity;

import com.alibaba.fastjson.JSON;
import lombok.Data;
import org.hibernate.validator.constraints.NotBlank;

import javax.persistence.*;
import java.io.Serializable;
import java.util.Date;

@Table(name = "mail_info")
@Entity
@Data
public class MailInfo implements Serializable{

    @GeneratedValue(strategy = GenerationType.IDENTITY)
    @Id
    private Integer id;

    @Column
    @NotBlank(message = "邮件主题不能为空！")
    private String subject;
    @Column
    @NotBlank(message = "邮件接受者不能为空！")
    private String tos;
    @Column(name = "is_active")
    private Integer isActive=1;
    @Column(name = "is_delay")
    private Integer isDelay=0;
    @Column(name = "send_time")
    private Date sendTime;
    @Column(name = "create_time")
    private Date createTime;
    @Column(name = "update_time")
    private Date updateTime;
    @Column
    @NotBlank(message = "邮件内容不能为空！")
    private String content;


    @Transient
    private String sendDateTime;

    public static void main(String[] args) {
        MailInfo mailInfo = new MailInfo();
        mailInfo.setSubject("愉快过年");
        mailInfo.setContent("快要过年了！");
        mailInfo.setIsActive(1);
        mailInfo.setIsDelay(0);
        mailInfo.setTos("505440@163.com");
        mailInfo.setSendDateTime("2021-01-09 16:00:00");
        System.out.println(JSON.toJSONString(mailInfo));
    }
}
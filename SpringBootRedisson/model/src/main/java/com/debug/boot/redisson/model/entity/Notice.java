package com.debug.boot.redisson.model.entity;

import com.alibaba.fastjson.JSON;
import lombok.Data;
import org.hibernate.validator.constraints.NotBlank;

import javax.persistence.*;
import java.io.Serializable;

@Table(name="notice")
@Entity
@Data
public class Notice implements Serializable{

    @GeneratedValue(strategy = GenerationType.IDENTITY)
    @Id
    @Column
    private Integer id;
    @Column
    @NotBlank(message = "通告标题不能为空！")
    private String title;
    @Column
    @NotBlank(message = "通告内容不能为空！")
    private String content;
    @Column(name = "is_active")
    private Integer isActive=1;

    public static void main(String[] args) {
        Notice notice = new Notice();
        notice.setTitle("过年了");
        notice.setContent("happy new year!");
        notice.setIsActive(1);
        System.out.println(JSON.toJSONString(notice));
    }
}
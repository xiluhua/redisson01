package com.debug.boot.redisson.model.entity;

import com.alibaba.fastjson.JSON;
import lombok.Data;
import lombok.Value;

import javax.persistence.*;
import java.io.Serializable;

@Table(name="user")
@Entity
@Data
public class User implements Serializable{

    @GeneratedValue(strategy = GenerationType.IDENTITY)
    @Id
    @Column
    private Integer id;
    @Column
    private String name;
    @Column
    private String email;

    public static void main(String[] args) {
        User user = new User();
        user.setName("zhangsan");
        user.setEmail("Test@163.com");
        System.out.println(JSON.toJSONString(user));

    }
}
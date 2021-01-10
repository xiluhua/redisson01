package com.debug.boot.redisson.model.entity;

import com.alibaba.fastjson.JSON;
import lombok.Data;
import lombok.NoArgsConstructor;
import org.hibernate.validator.constraints.NotBlank;

import javax.persistence.*;
import java.io.Serializable;
import java.util.Date;

@Table(name="item")
@Entity
@Data
@NoArgsConstructor
public class Item implements Serializable{

    @GeneratedValue(strategy = GenerationType.IDENTITY)
    @Id
    @Column
    private Integer id;

    @Column
    @NotBlank(message = "商品编码不能为空！")
    private String code;

    @Column
    @NotBlank(message = "商品名称不能为空！")
    private String name;

    @Temporal(value=TemporalType.TIMESTAMP)
    @Column
    private Date createTime;


    public static void main(String[] args) {
        Item item = new Item();
        item.setCode("1000");
        item.setName("西游记");
        item.setCreateTime(new Date());

        System.out.println(JSON.toJSONString(item));
    }
}
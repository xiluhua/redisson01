package com.debug.boot.redisson.model.entity;

import lombok.Data;

import javax.persistence.*;
import java.io.Serializable;
import java.util.Date;

@Table(name="product_kill")
@Entity
@Data
public class ProductKill implements Serializable{

    @GeneratedValue(strategy = GenerationType.IDENTITY)
    @Id
    @Column
    private Integer id;
    @Column(name = "prod_code")
    private String prodCode;
    @Column
    private Integer total;
    @Column(name = "is_active")
    private Byte isActive;
    @Column(name = "create_time")
    private Date createTime;

    //临时字段
    @Transient
    private String prodName;
}
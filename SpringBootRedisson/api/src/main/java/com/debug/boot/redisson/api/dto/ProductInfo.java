package com.debug.boot.redisson.api.dto;/**
 * Created by Administrator on 2020/1/13.
 */

import lombok.AllArgsConstructor;
import lombok.Data;
import lombok.NoArgsConstructor;

import javax.persistence.Entity;
import javax.persistence.Id;
import java.io.Serializable;
import java.lang.annotation.Retention;
import java.util.Date;

/**
 **/
@Entity
@Data
@NoArgsConstructor
@AllArgsConstructor
public class ProductInfo implements Serializable{

    @Id
    private Integer id;

    private String prodCode;

    private Integer total;

    private Byte isActive;

    private Date createTime;


    //临时字段
    private String prodName;

}
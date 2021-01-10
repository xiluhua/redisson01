package com.debug.boot.redisson.model.entity;

import com.alibaba.fastjson.JSON;
import lombok.Data;
import lombok.EqualsAndHashCode;
import org.hibernate.validator.constraints.NotBlank;

import javax.persistence.*;
import java.io.Serializable;
import java.util.Date;

@Table(name="sys_config")
@Entity
@Data
@EqualsAndHashCode
public class SysConfig implements Serializable{

    @GeneratedValue(strategy = GenerationType.IDENTITY)
    @Id
    @Column
    private Integer id;
    @Column
    @NotBlank(message = "字典类型不能为空！")
    private String type;
    @Column
    @NotBlank(message = "字典名称不能为空！")
    private String name;
    @Column
    @NotBlank(message = "选项编码不能为空！")
    private String code;
    @Column
    @NotBlank(message = "选项名称不能为空！")
    private String value;
    @Column(name = "order_by")
    private Integer orderBy;
    @Column(name = "is_active")
    private Integer isActive=1;
    @Column(name = "create_time")
    private Date createTime;

    public static void main(String[] args) {
        SysConfig sysConfig = new SysConfig();
        sysConfig.setType("ReviewStatus");
        sysConfig.setIsActive(1);
        sysConfig.setCode("uncertern");
        sysConfig.setName("审核状态");
        sysConfig.setOrderBy(4);
        sysConfig.setValue("不确定");
        System.out.println(JSON.toJSONString(sysConfig));
    }
}
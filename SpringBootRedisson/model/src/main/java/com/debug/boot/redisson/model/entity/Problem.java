package com.debug.boot.redisson.model.entity;

import com.alibaba.fastjson.JSON;
import lombok.Data;
import org.hibernate.validator.constraints.NotBlank;

import javax.persistence.*;
import java.io.Serializable;

@Table(name="problem")
@Entity
@Data
public class Problem implements Serializable{

    @GeneratedValue(strategy = GenerationType.IDENTITY)
    @Id
    @Column
    private Integer id;
    @Column
    @NotBlank(message = "标题不能为空！")
    private String title;
    @Column(name = "choice_a")
    private String choiceA;
    @Column(name = "choice_b")
    private String choiceB;
    @Column(name = "is_active")
    private Integer isActive;
    @Column(name = "order_by")
    private Integer orderBy;

    @Override
    public boolean equals(Object o) {
        if (this == o) return true;
        if (o == null || getClass() != o.getClass()) return false;

        Problem problem = (Problem) o;

        return title.equals(problem.title);
    }

    @Override
    public int hashCode() {
        return title.hashCode();
    }

    public static void main(String[] args) {
        Problem problem = new Problem();
        problem.setTitle("语文");
        problem.setChoiceA("Y");
        problem.setChoiceB("N");
        problem.setIsActive(1);
        problem.setOrderBy(100);
        System.out.println(JSON.toJSONString(problem));
    }
}
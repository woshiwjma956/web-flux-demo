package com.ycorn.weblfux.entity;

import lombok.Getter;
import lombok.Setter;
import lombok.ToString;
import lombok.experimental.Accessors;
import org.hibernate.validator.constraints.Length;
import org.hibernate.validator.constraints.Range;
import org.springframework.data.annotation.Id;
import org.springframework.data.mongodb.core.mapping.Document;

import javax.validation.constraints.NotBlank;

/**
 * @Author: wujianmin
 * @Date: 2020/4/1 11:19
 * @Function:
 * @Version 1.0
 */
@Setter
@Getter
@Accessors(chain = true)
@ToString
@Document(value = "user")
public class User {
    @Id
    @NotBlank
    private String id;

    @Length(min = 1, max = 20)
    private String name;

    @Range(min = 10, max = 50)
    private Integer age;

}

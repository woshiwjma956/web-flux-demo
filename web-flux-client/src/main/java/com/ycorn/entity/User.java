package com.ycorn.entity;

import lombok.Getter;
import lombok.Setter;
import lombok.ToString;
import lombok.experimental.Accessors;

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
public class User {
    private String id;

    private String name;

    private Integer age;

}

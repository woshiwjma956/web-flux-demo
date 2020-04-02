package com.ycorn.entity;

import lombok.AllArgsConstructor;
import lombok.Builder;
import lombok.Data;
import lombok.NoArgsConstructor;
import lombok.experimental.Accessors;

import java.util.ArrayList;
import java.util.List;

/**
 * @Author: wujianmin
 * @Date: 2020/4/1 16:38
 * @Function:
 * @Version 1.0
 */
@Data
@AllArgsConstructor
@NoArgsConstructor
@Builder
public class User {

    private String id;

    private String name;

    public static List<User> testUsers() {
        List<User> userList = new ArrayList<>();
        for (int i = 0; i < 10; i++) {
            userList.add(User.builder().id(i + "").name(i + "name").build());
        }
        return userList;
    }
}

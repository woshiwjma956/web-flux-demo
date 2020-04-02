package com.ycorn.entity;


import lombok.AllArgsConstructor;
import lombok.Builder;
import lombok.Data;
import lombok.NoArgsConstructor;

/**
 * @Author: wujianmin
 * @Date: 2020/4/2 15:43
 * @Function:
 * @Version 1.0
 */
@Data
@AllArgsConstructor
@NoArgsConstructor
@Builder
public class ServerInfo {

    private String url;

}

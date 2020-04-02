package com.ycorn.weblfux.repository;

import com.ycorn.weblfux.entity.User;
import org.springframework.data.mongodb.repository.ReactiveMongoRepository;
import org.springframework.stereotype.Repository;

/**
 * @Author: wujianmin
 * @Date: 2020/4/1 11:22
 * @Function:
 * @Version 1.0
 */
@Repository
public interface UserRepository extends ReactiveMongoRepository<User,String> {
}

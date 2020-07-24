package com.ssw.service;

import com.ssw.entity.User;

/**
 * Created with IntelliJ IDEA.
 *
 * @Auther: ssw
 * @Date: 2020/03/13/11:13
 * @Description:
 */
public interface IUserService {
    User getUserById(int userId);
}

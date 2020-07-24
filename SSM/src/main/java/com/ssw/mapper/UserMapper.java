package com.ssw.mapper;


import com.ssw.entity.User;
import org.apache.ibatis.annotations.Param;

public interface UserMapper {

    User selectByPrimaryKey(@Param("id") int id);
}

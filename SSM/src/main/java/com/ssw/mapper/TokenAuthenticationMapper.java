package com.ssw.mapper;

import com.ssw.entity.AuthenticationInfo;
import org.apache.ibatis.annotations.Param;

/**
 * Created with IntelliJ IDEA.
 *
 * @Auther: ssw
 * @Date: 2020/03/16/10:28
 * @Description:
 */
public interface TokenAuthenticationMapper {

    //查找匹配appkey以及appSecret
    AuthenticationInfo match(@Param("appKey") String appKey, @Param("appSecret") String appSecret);

}

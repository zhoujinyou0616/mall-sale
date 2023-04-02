package com.eugene.mapper;

import com.baomidou.mybatisplus.core.mapper.BaseMapper;
import com.eugene.pojo.User;
import org.apache.ibatis.annotations.Param;
import org.springframework.stereotype.Repository;

import java.util.List;

@Repository
public interface UserMapper extends BaseMapper<User> {
    Boolean saveBatch(@Param("userList") List<User> userList);
}
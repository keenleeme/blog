package com.monster.blog.service;

import com.monster.blog.domain.UserEntity;
import com.baomidou.mybatisplus.extension.service.IService;
import com.monster.blog.vo.RegisterVo;

/**
 * <p>
 *  服务类
 * </p>
 *
 * @author liz
 * @since 2020-05-28
 */
public interface UserService extends IService<UserEntity> {

    UserEntity registerAccount(RegisterVo registerVo);

    boolean selectUserByUserName(String userName);

    UserEntity userLogin(String userName, String password);

    UserEntity getUserInfo(String userName);

}

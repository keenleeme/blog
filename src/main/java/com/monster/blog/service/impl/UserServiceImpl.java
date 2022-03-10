package com.monster.blog.service.impl;

import com.baomidou.mybatisplus.core.conditions.query.QueryWrapper;
import com.monster.blog.config.shiro.ShiroUtil;
import com.monster.blog.constant.BlogConstant;
import com.monster.blog.domain.UserEntity;
import com.monster.blog.mapper.UserMapper;
import com.monster.blog.service.UserService;
import com.baomidou.mybatisplus.extension.service.impl.ServiceImpl;
import com.monster.blog.util.Md5Utils;
import com.monster.blog.util.UUIDUtil;
import com.monster.blog.vo.RegisterVo;
import org.springframework.stereotype.Service;

import javax.annotation.Resource;
import java.util.Date;

/**
 * <p>
 *  服务实现类
 * </p>
 *
 * @author liz
 * @since 2020-05-28
 */
@Service
public class UserServiceImpl extends ServiceImpl<UserMapper, UserEntity> implements UserService {

    @Resource
    private UserMapper userMapper;

    @Override
    public UserEntity getUserInfo(String userName) {
        return getUserInfoByUserName(userName);
    }

    @Override
    public UserEntity registerAccount(RegisterVo registerVo) {
        String salt = UUIDUtil.createSalt();
        UserEntity user = new UserEntity();
        user.setUserName(registerVo.getUserName());
        user.setPhoneNumber(registerVo.getPhoneNum());
        user.setPassword(ShiroUtil.sha256(registerVo.getPassword(), salt));
        user.setSaltValue(salt);
        user.setAvatar(BlogConstant.DEFAULT_AVATAR_URL);
        user.setCreatedTime(new Date());
        user.setIsRemoved(BlogConstant.NOT_REMOVED);
        user.setRoleId(BlogConstant.COMMON_USER);
        user.setUpdatedTime(new Date());
        userMapper.insert(user);
        return user;
    }

    @Override
    public boolean selectUserByUserName(String userName) {
        UserEntity user = getUserInfoByUserName(userName);
        if (user == null) {
            return false;
        }
        return true;
    }

    @Override
    public UserEntity userLogin(String userName, String password) {
        UserEntity user = getUserInfoByUserName(userName);
        String md5Psw = ShiroUtil.sha256(password, user.getSaltValue());
        // 判断密码是否正确
        if (!user.getPassword().equals(md5Psw)) {
            return null;
        }
        user.setPassword(null);
        return user;
    }

    public UserEntity getUserInfoByUserName(String userName) {
        QueryWrapper<UserEntity> query = new QueryWrapper<>();
        query.eq("USER_NAME", userName);
        query.eq("IS_REMOVED",BlogConstant.NOT_REMOVED);
        return userMapper.selectOne(query);
    }
}

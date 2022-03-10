package com.monster.blog.config.shiro;

import com.baomidou.mybatisplus.core.conditions.query.QueryWrapper;
import com.monster.blog.domain.UserEntity;
import com.monster.blog.domain.UserRoleEntity;
import com.monster.blog.mapper.UserMapper;
import com.monster.blog.mapper.UserRoleMapper;
import org.apache.commons.lang3.StringUtils;
import org.apache.shiro.authc.*;
import org.apache.shiro.authc.credential.CredentialsMatcher;
import org.apache.shiro.authc.credential.HashedCredentialsMatcher;
import org.apache.shiro.authz.AuthorizationInfo;
import org.apache.shiro.authz.SimpleAuthorizationInfo;
import org.apache.shiro.realm.AuthorizingRealm;
import org.apache.shiro.subject.PrincipalCollection;
import org.apache.shiro.util.ByteSource;
import org.springframework.stereotype.Component;

import javax.annotation.Resource;
import java.util.*;

/**
 * @author liz
 * @Description TODO
 * @date 2020/8/5-15:50
 */
//@Component
public class UserRealm extends AuthorizingRealm {

    @Resource
    private UserMapper userMapper;

    @Resource
    private UserRoleMapper userRoleMapper;

    /**
     * 授权(验证权限时调用)
     * 获取用户权限集合
     */
    @Override
    public AuthorizationInfo doGetAuthorizationInfo(PrincipalCollection principals) {
        UserEntity user = (UserEntity) principals.getPrimaryPrincipal();
        if (user == null) {
            throw new UnknownAccountException("账号不存在");
        }
        List<String> permsList;
        // 默认用户拥有最高权限
        QueryWrapper<UserRoleEntity> query = new QueryWrapper<>();
        query.eq("IS_REMOVED", 0);
        List<UserRoleEntity> menuList = userRoleMapper.selectList(query);
        permsList = new ArrayList<>(menuList.size());
        for (UserRoleEntity role : menuList) {
            permsList.add(role.getName());
        }
        // 用户权限列表
        Set<String> permsSet = new HashSet<>();
        for (String perms : permsList) {
            if (StringUtils.isEmpty(perms)) {
                continue;
            }
            permsSet.addAll(Arrays.asList(perms.trim().split(",")));
        }
        SimpleAuthorizationInfo info = new SimpleAuthorizationInfo();
        info.setStringPermissions(permsSet);
        return info;
    }

    /**
     * 认证(登录时调用)
     * 验证用户登录
     */
    @Override
    protected AuthenticationInfo doGetAuthenticationInfo(AuthenticationToken authToken) throws AuthenticationException {
        UsernamePasswordToken token = (UsernamePasswordToken) authToken;
        QueryWrapper<UserEntity> query = new QueryWrapper<>();
        query.eq("USER_NAME", token.getUsername());
        // 查询用户信息
        UserEntity user = userMapper.selectOne(query);
        // 账号不存在
        if (user == null) {
            throw new UnknownAccountException("账号或密码不正确");
        }
        // 账号锁定
        if (user.getIsRemoved() == 1) {
            throw new LockedAccountException("账号已被锁定,请联系管理员");
        }
        SimpleAuthenticationInfo info = new SimpleAuthenticationInfo(user, user.getPassword(), ByteSource.Util.bytes(user.getSaltValue()), getName());
        return info;
    }

    @Override
    public void setCredentialsMatcher(CredentialsMatcher credentialsMatcher) {
        HashedCredentialsMatcher shaCredentialsMatcher = new HashedCredentialsMatcher();
        shaCredentialsMatcher.setHashAlgorithmName(ShiroUtil.hashAlgorithmName);
        shaCredentialsMatcher.setHashIterations(ShiroUtil.hashIterations);
        super.setCredentialsMatcher(shaCredentialsMatcher);
    }

}

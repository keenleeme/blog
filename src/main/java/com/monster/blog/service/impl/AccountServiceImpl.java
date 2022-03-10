package com.monster.blog.service.impl;

import com.baomidou.mybatisplus.extension.service.impl.ServiceImpl;
import com.monster.blog.domain.AccountEntity;
import com.monster.blog.mapper.AccountMapper;
import com.monster.blog.service.AccountService;
import com.monster.blog.vo.AccountVo;
import org.springframework.stereotype.Service;

import javax.annotation.Resource;

/**
 * <p>
 *  服务实现类
 * </p>
 *
 * @author zhangdj
 * @since 2021-03-03
 */
@Service
public class AccountServiceImpl extends ServiceImpl<AccountMapper, AccountEntity> implements AccountService {

    @Resource
    private AccountMapper accountMapper;

    @Override
    public String chargeAccount(AccountVo accountVo) {
        return null;
    }
}

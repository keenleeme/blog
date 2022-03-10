package com.monster.blog.service;

import com.baomidou.mybatisplus.extension.service.IService;
import com.monster.blog.domain.AccountEntity;
import com.monster.blog.vo.AccountVo;

/**
 * <p>
 *  服务类
 * </p>
 *
 * @author zhangdj
 * @since 2021-03-03
 */
public interface AccountService extends IService<AccountEntity> {

    String chargeAccount(AccountVo accountVo);

}

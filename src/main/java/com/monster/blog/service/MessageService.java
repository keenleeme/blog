package com.monster.blog.service;

import com.monster.blog.domain.MessageEntity;
import com.baomidou.mybatisplus.extension.service.IService;
import com.monster.blog.vo.MessageVo;

import java.util.List;

/**
 * <p>
 * 私信表 服务类
 * </p>
 *
 * @author liz
 * @since 2020-05-28
 */
public interface MessageService extends IService<MessageEntity> {

    Integer countAllMessage(Long userId);

    List<MessageEntity> getMessageList();

    int insertMessage(MessageVo messageVo);

    int replyMessage(MessageVo messageVo);

}

package com.monster.blog.service.impl;

import cn.hutool.core.collection.CollUtil;
import com.baomidou.mybatisplus.core.conditions.query.QueryWrapper;
import com.monster.blog.constant.BlogConstant;
import com.monster.blog.domain.MessageEntity;
import com.monster.blog.domain.UserEntity;
import com.monster.blog.mapper.MessageMapper;
import com.monster.blog.mapper.UserMapper;
import com.monster.blog.service.MessageService;
import com.baomidou.mybatisplus.extension.service.impl.ServiceImpl;
import com.monster.blog.vo.MessageVo;
import org.springframework.stereotype.Service;

import javax.annotation.Resource;
import java.util.ArrayList;
import java.util.Date;
import java.util.List;

/**
 * <p>
 * 私信表 服务实现类
 * </p>
 *
 * @author liz
 * @since 2020-05-28
 */
@Service
public class MessageServiceImpl extends ServiceImpl<MessageMapper, MessageEntity> implements MessageService {

    @Resource
    private MessageMapper messageMapper;

    @Resource
    private UserMapper userMapper;

    private List<MessageEntity> replyMessage = new ArrayList<>();

    @Override
    public Integer countAllMessage(Long userId) {
        QueryWrapper<MessageEntity> query = new QueryWrapper<>();
        query.eq(BlogConstant.IS_REMOVED_STR, BlogConstant.NOT_REMOVED);
        if (userId != null) {
            query.eq(BlogConstant.CREATED_BY, userId);
        }
        return messageMapper.selectCount(query);
    }

    @Override
    public List<MessageEntity> getMessageList() {
        QueryWrapper<MessageEntity> query = new QueryWrapper<>();
        query.eq(BlogConstant.IS_REMOVED_STR, BlogConstant.NOT_REMOVED);
        query.eq(BlogConstant.PARENT_MESSAGE_ID, 0);
        MessageVo messageVo = new MessageVo();
        List<MessageEntity> messageList = messageMapper.selectList(query);
        // 为空就直接返回
        if (CollUtil.isEmpty(messageList)) {
            return messageList;
        }
        // 遍历评论父节点
        for (MessageEntity message : messageList) {
            QueryWrapper<MessageEntity> query1 = new QueryWrapper<>();
            query1.eq(BlogConstant.PARENT_MESSAGE_ID, message.getId());
            query1.eq(BlogConstant.IS_REMOVED_STR, BlogConstant.NOT_REMOVED);
            messageVo.setParentMessageId(message.getId());
            List<MessageEntity> childrenMessage = messageMapper.selectList(query1);
            getSecondComments(messageVo, childrenMessage);
            message.setReplyMessage(replyMessage);
            replyMessage = new ArrayList<>();
        }
        return messageList;
    }

    private void getSecondComments(MessageVo messageVo, List<MessageEntity> commentList) {
        // 判断二级评论是否为空
        if (CollUtil.isNotEmpty(commentList)) {
            for (MessageEntity message : commentList) {
                messageVo.setParentMessageId(message.getId());
                replyMessage.add(message);
                getMoreLevelComments(messageVo);
            }
        }
    }

    private void getMoreLevelComments(MessageVo messageVo) {
        QueryWrapper<MessageEntity> query = new QueryWrapper<>();
        query.eq(BlogConstant.IS_REMOVED_STR, BlogConstant.NOT_REMOVED);
        query.eq(BlogConstant.PARENT_MESSAGE_ID, messageVo.getParentMessageId());
        List<MessageEntity> thirdComments = messageMapper.selectList(query);
        // 判断三级级评论是否为空
        if (CollUtil.isNotEmpty(thirdComments)) {
            for (MessageEntity thirdComment : thirdComments) {
                QueryWrapper<MessageEntity> query1 = new QueryWrapper<>();
                query1.eq(BlogConstant.IS_REMOVED_STR, BlogConstant.NOT_REMOVED);
                query1.eq(BlogConstant.PARENT_MESSAGE_ID, thirdComment.getId());
                messageVo.setParentMessageId(thirdComment.getId());
                replyMessage.add(thirdComment);
                getMoreLevelComments(messageVo);
            }
        }
    }

    @Override
    public int insertMessage(MessageVo messageVo) {
        UserEntity user = userMapper.selectById(messageVo.getUserId());
        MessageEntity message = new MessageEntity();
        message.setAdminMessage(user.getRoleId() == 1 ? 1 : 0)
                .setAvatar(user.getAvatar())
                .setContent(messageVo.getContent())
                .setCreatedBy(messageVo.getUserId())
                .setCreatedTime(new Date())
                .setIsRemoved(0)
                .setNickName(user.getUserName())
                .setParentMessageId(0L);
        return messageMapper.insert(message);
    }

    @Override
    public int replyMessage(MessageVo messageVo) {
        UserEntity user = userMapper.selectById(messageVo.getUserId());
        MessageEntity message = new MessageEntity();
        MessageEntity messageEntity = messageMapper.selectById(messageVo.getParentMessageId());
        message.setNickName(user.getUserName())
                .setIsRemoved(0)
                .setCreatedTime(new Date())
                .setCreatedBy(messageVo.getUserId())
                .setContent(messageVo.getContent())
                .setAvatar(user.getAvatar())
                .setAdminMessage(user.getRoleId() == 1 ? 1 : 0)
                .setParentMessageId(messageVo.getParentMessageId())
                .setParentNickName(messageEntity.getNickName())
                .setParentUserId(messageEntity.getCreatedBy());
        return messageMapper.insert(message);
    }
}

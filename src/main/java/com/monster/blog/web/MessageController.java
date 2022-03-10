package com.monster.blog.web;


import com.github.pagehelper.PageHelper;
import com.github.pagehelper.PageInfo;
import com.monster.blog.constant.BlogConstant;
import com.monster.blog.domain.MessageEntity;
import com.monster.blog.domain.UserEntity;
import com.monster.blog.service.MessageService;
import com.monster.blog.vo.MessageVo;
import io.swagger.annotations.Api;
import io.swagger.annotations.ApiOperation;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.stereotype.Controller;
import org.springframework.ui.Model;
import org.springframework.web.bind.annotation.RequestMapping;
import org.springframework.web.bind.annotation.RequestParam;

import javax.servlet.http.HttpSession;
import java.util.List;

/**
 * <p>
 * 私信表 前端控制器
 * </p>
 *
 * @author liz
 * @since 2020-05-28
 */
@Controller
@RequestMapping("/user")
@Api(value = "留言板模块接口")
public class MessageController {

    @Autowired
    private MessageService messageService;

    private static final String MESSAGE_PAGE = "message";
    private static final String MESSAGE_LIST_PAGE = "message :: messageList";
    private static final String REDIRECT_MESSAGE_PAGE = "redirect:/user/getMessage";

    @RequestMapping("/message")
    @ApiOperation(value = "跳转留言板页面")
    public String toMessage(@RequestParam(value = "pageNum", defaultValue = "1") Integer pageNum, Model model) {
        PageHelper.startPage(pageNum, BlogConstant.PAGE_SIZE);
        List<MessageEntity> list = messageService.getMessageList();
        PageInfo<MessageEntity> pageInfo = new PageInfo<>(list);
        model.addAttribute("pageInfo", pageInfo);
        return MESSAGE_PAGE;
    }


    @RequestMapping("/getMessage")
    @ApiOperation(value = "查询留言板接口")
    public String getMessageList(@RequestParam(value = "pageNum", defaultValue = "1") Integer pageNum, Model model) {
        PageHelper.startPage(pageNum, BlogConstant.PAGE_SIZE);
        List<MessageEntity> list = messageService.getMessageList();
        PageInfo<MessageEntity> pageInfo = new PageInfo<>(list);
        model.addAttribute("pageInfo", pageInfo);
        return MESSAGE_LIST_PAGE;
    }

    @RequestMapping("/saveMessage")
    @ApiOperation(value = "新增留言接口")
    public String saveMessage(MessageVo messageVo, HttpSession session) {
        UserEntity user = (UserEntity) session.getAttribute("user");
        messageVo.setUserId(user.getId());
        messageService.insertMessage(messageVo);
        return REDIRECT_MESSAGE_PAGE;
    }

    @RequestMapping("/replyMessage")
    @ApiOperation(value = "新增留言接口")
    public String replyMessage(MessageVo messageVo, HttpSession session) {
        UserEntity user = (UserEntity) session.getAttribute("user");
        messageVo.setUserId(user.getId());
        messageService.replyMessage(messageVo);
        return REDIRECT_MESSAGE_PAGE;
    }
}


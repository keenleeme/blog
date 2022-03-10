package com.monster.blog.web;

import com.alibaba.fastjson.JSONObject;
import com.monster.blog.config.redis.RedisUtil;
import com.monster.blog.domain.UserEntity;
import com.monster.blog.service.BlogService;
import com.monster.blog.service.UserService;
import com.monster.blog.util.StringUtil;
import com.monster.blog.vo.BlogStatisticVo;
import io.swagger.annotations.Api;
import io.swagger.annotations.ApiOperation;
import lombok.extern.slf4j.Slf4j;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.stereotype.Controller;
import org.springframework.ui.Model;
import org.springframework.web.bind.annotation.GetMapping;
import org.springframework.web.bind.annotation.RequestMapping;
import org.springframework.web.bind.annotation.RequestParam;
import org.springframework.web.bind.annotation.ResponseBody;
import org.springframework.web.servlet.mvc.support.RedirectAttributes;

import javax.servlet.http.HttpSession;

/**
 * @author liz
 * @Description 后台管理接口
 * @date 2020/6/4-17:20
 */
@Slf4j
@RequestMapping("/admin")
@Controller
@Api(value = "后台管理接口")
public class AdminController {

    @Autowired
    private UserService userService;

    @Autowired
    private BlogService blogService;

    @Autowired
    private RedisUtil redisUtil;

    private static final String LOGIN_PAGE = "/admin/login";

    private static final String REDIRECT_LOGIN_PAGE = "redirect:/admin/loginPage";

    private static final String INDEX_PAGE = "/admin/index";

    private static final String BLOG_PAGE = "/admin/index :: blogStatistic";

    @GetMapping("/loginPage")
    public String loginPage() {

        return LOGIN_PAGE;
    }

    @RequestMapping("/login")
    @ApiOperation(value = "后台用户登录")
    public String login(@RequestParam String userName, @RequestParam String password, HttpSession session, RedirectAttributes attributes) {
        UserEntity user;
        Object userInfo = redisUtil.get("user:" + userName);
        user = JSONObject.parseObject(userInfo.toString(), UserEntity.class);
        log.info("用户信息：{}", userInfo);
        if (user == null) {
            user = userService.userLogin(userName, password);
        }
        if (user == null) {
            attributes.addFlashAttribute("message", "登录密码错误");
            return REDIRECT_LOGIN_PAGE;
        }
        redisUtil.set("user:" + userName, user);
        session.setAttribute("admin", user);
        return INDEX_PAGE;
    }

    @RequestMapping("/logout")
    @ApiOperation(value = "后台用户退出")
    public String logout(HttpSession session) {
        session.removeAttribute("admin");
        return REDIRECT_LOGIN_PAGE;
    }

    @RequestMapping("/blogStatistic")
    @ApiOperation(value = "后台博客数据统计")
    public String blogStatistic(Model model, HttpSession session) {
        UserEntity user = (UserEntity) session.getAttribute("admin");
        // 判断用户角色是否是管理员 是 -> 统计所有用户的总数  否 -> 统计某一个用户的数据
        BlogStatisticVo blog;
        if (user == null) {
            blog = blogService.getBlogStatistic(null);
        } else {
            blog = blogService.getBlogStatistic(user.getRoleId() == 1 ? null : user.getId());
        }
        model.addAttribute("totalBlogNums", blog.getTotalBlogNums());
        model.addAttribute("totalReadNums", blog.getTotalReadNums());
        model.addAttribute("totalCommentNums", blog.getTotalCommentNums());
        model.addAttribute("totalLikeNums", blog.getTotalLikeNums());
        model.addAttribute("totalMessageNums", blog.getTotalMessageNums());
        return BLOG_PAGE;
    }
}

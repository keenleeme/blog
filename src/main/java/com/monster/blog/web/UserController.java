package com.monster.blog.web;


import com.github.pagehelper.PageHelper;
import com.github.pagehelper.PageInfo;
import com.monster.blog.config.shiro.ShiroUtil;
import com.monster.blog.constant.BlogConstant;
import com.monster.blog.domain.UserEntity;
import com.monster.blog.service.BlogService;
import com.monster.blog.service.UserService;
import com.monster.blog.vo.BlogStatisticVo;
import com.monster.blog.vo.IndexBlogVo;
import com.monster.blog.vo.RecommendBlogVo;
import com.monster.blog.vo.RegisterVo;
import io.swagger.annotations.Api;
import io.swagger.annotations.ApiOperation;
import org.apache.shiro.authc.UsernamePasswordToken;
import org.apache.shiro.subject.Subject;
import org.slf4j.Logger;
import org.slf4j.LoggerFactory;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.stereotype.Controller;
import org.springframework.ui.Model;
import org.springframework.web.bind.annotation.*;
import org.springframework.web.servlet.mvc.support.RedirectAttributes;

import javax.servlet.http.HttpSession;
import java.util.List;

/**
 * <p>
 * 前端控制器
 * </p>
 *
 * @author liz
 * @since 2020-05-28
 */
@Controller
@RequestMapping("/user")
@Api(value = "用户模块接口", tags = {"用户模块接口"})
public class UserController {

    private final static Logger logger = LoggerFactory.getLogger(UserController.class);

    @Autowired
    private UserService userService;

    @Autowired
    private BlogService blogService;

    private static final String LOGIN_PAGE = "/user/login";
    private static final String REGISTER_PAGE = "/user/register";
    private static final String INDEX_PAGE = "/index";
    private static final String REDIRECT_REGISTER_PAGE = "redirect:/user/registerPage";
    private static final String REDIRECT_LOGIN_PAGE = "redirect:/user/loginPage";
    private static final String REDIRECT_INDEX_PAGE = "redirect:/user/index";
    private static final String BLOG_PAGE = "/index :: blogStatistic";
    private static final String MUSIC_PAGE = "/music";
    private static final String ERROR_PAGE = "500 :: recommendList";

    /**
     * 跳转登录页
     *
     * @return java.lang.String
     * @author liz
     * @date 2020-06-03 09:43:18
     **/
    @GetMapping("/loginPage")
    public String loginPage() {

        return LOGIN_PAGE;
    }

    @GetMapping("/registerPage")
    public String registerPage() {

        return REGISTER_PAGE;
    }

    @RequestMapping("/register")
    public String register(RegisterVo registerVo, RedirectAttributes attributes) {
        // 用户注册，存入数据库
        UserEntity userEntity = userService.registerAccount(registerVo);
        // 判断是否注册成功
        if (userEntity.getId() != null) {
            return LOGIN_PAGE;
        }
        attributes.addFlashAttribute("message", "注册失败");
        return REDIRECT_REGISTER_PAGE;
    }

    @RequestMapping("/checkUserIsRepeat")
    @ResponseBody
    public boolean checkUserIsRepeat(@RequestParam String userName) {

        return userService.selectUserByUserName(userName);
    }

    @RequestMapping("/login")
    @ApiOperation(value = "用户登录")
    public String login(@RequestParam String userName, @RequestParam String password, HttpSession session, RedirectAttributes attributes) {
        try{
            Subject subject = ShiroUtil.getSubject();
            UsernamePasswordToken token = new UsernamePasswordToken(userName, password);
            subject.login(token);
            logger.info("登录成功");
        }catch (Exception e) {
            e.printStackTrace();
        }

        UserEntity user = userService.getUserInfo(userName);
        if (user == null) {
            attributes.addFlashAttribute("message", "登录密码错误");
            return REDIRECT_LOGIN_PAGE;
        }
        session.setAttribute("user", user);
        return REDIRECT_INDEX_PAGE;
    }

    @RequestMapping("/index")
    @ApiOperation(value = "首页信息展示")
    public String index(@RequestParam(value = "pageNum", defaultValue = "1") Integer pageNum, Model model) {
        List<RecommendBlogVo> recommendBlogs = blogService.getRecommendBlogList();
        PageHelper.startPage(pageNum, BlogConstant.INDEX_PAGE_SIZE);
        List<IndexBlogVo> blogList = blogService.getIndexBlogList();
        PageInfo<IndexBlogVo> pageInfo = new PageInfo<>(blogList);
        model.addAttribute("pageInfo", pageInfo);
        model.addAttribute("recommendBlogs", recommendBlogs);
        return INDEX_PAGE;
    }


    @RequestMapping("/blogStatistic")
    @ApiOperation(value = "后台博客数据统计")
    public String blogStatistic(Model model) {
        BlogStatisticVo blog = blogService.getBlogStatistic(null);
        model.addAttribute("totalReadNums", blog.getTotalReadNums());
        model.addAttribute("totalBlogNums", blog.getTotalBlogNums());
        model.addAttribute("totalCommentNums", blog.getTotalCommentNums());
        model.addAttribute("totalLikeNums", blog.getTotalLikeNums());
        model.addAttribute("totalMessageNums", blog.getTotalMessageNums());
        return BLOG_PAGE;
    }

    @RequestMapping("/music")
    @ApiOperation(value = "前往音乐盒页面")
    public String goToMusic() {

        return MUSIC_PAGE;
    }

    @RequestMapping("/recommendBlogs")
    @ApiOperation(value = "推荐博客接口")
    public String recommendBlogs(Model model) {
        List<RecommendBlogVo> recommendBlogs = blogService.getRecommendBlogList();
        model.addAttribute("recommendBlogs", recommendBlogs);
        return ERROR_PAGE;
    }

}


package com.monster.blog.web;


import com.monster.blog.domain.PictureEntity;
import com.monster.blog.service.PictureService;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.stereotype.Controller;
import org.springframework.ui.Model;
import org.springframework.web.bind.annotation.RequestMapping;

import java.util.List;

/**
 * <p>
 * 图片墙表 前端控制器
 * </p>
 *
 * @author liz
 * @since 2020-05-28
 */
@Controller
@RequestMapping("/user")
public class PictureController {

    @Autowired
    private PictureService pictureService;

    private static final String PICTURE_PAGE = "picture";

    @RequestMapping("/picture")
    public String getPicture(Model model) {

        List<PictureEntity> list = pictureService.getPictureWall();
        model.addAttribute("pictures", list);
        return PICTURE_PAGE;
    }

}


package com.monster.blog.web.admin;

import com.github.pagehelper.PageHelper;
import com.github.pagehelper.PageInfo;
import com.monster.blog.constant.BlogConstant;
import com.monster.blog.domain.PictureEntity;
import com.monster.blog.domain.UserEntity;
import com.monster.blog.service.PictureService;
import com.monster.blog.util.UploadUtil;
import com.monster.blog.vo.PictureVo;
import io.swagger.annotations.ApiOperation;
import org.apache.commons.lang3.StringUtils;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.stereotype.Controller;
import org.springframework.ui.Model;
import org.springframework.web.bind.annotation.PathVariable;
import org.springframework.web.bind.annotation.RequestMapping;
import org.springframework.web.bind.annotation.RequestParam;
import org.springframework.web.multipart.MultipartFile;
import org.springframework.web.servlet.mvc.support.RedirectAttributes;

import javax.servlet.http.HttpServletRequest;
import javax.servlet.http.HttpSession;
import java.util.List;
import java.util.Map;

/**
 * @author liz
 * @Description 后台管理图片 控制器
 * @date 2020/6/27-20:34
 */
@Controller
@RequestMapping("/admin")
public class AdminPictureController {

    @Autowired
    private PictureService pictureService;

    private static  final String PICTURE_PAGE = "/admin/pictures";
    private static  final String ADD_PICTURE_PAGE = "/admin/pictures-input";
    private static  final String REDIRECT_PICTURE_PAGE = "redirect:/admin/pictures";

    @RequestMapping("/pictures")
    @ApiOperation(value = "查询后台图片列表接口")
    public  String getPictures(@RequestParam(value = "pageNum", defaultValue = "1") Integer pageNum, Model model, HttpSession session) {
        UserEntity user = (UserEntity) session.getAttribute("admin");
        PageHelper.startPage(pageNum, BlogConstant.PAGE_SIZE);
        List<PictureEntity> list = pictureService.getPictureList(user.getId());
        PageInfo<PictureEntity> pageInfo = new PageInfo<>(list);
        model.addAttribute("pageInfo", pageInfo);
        return PICTURE_PAGE;
    }

    @RequestMapping("/addPicture")
    @ApiOperation(value = "跳转到新增页面")
    public String toAddPicturePage(Model model) {
        model.addAttribute("picture", new PictureEntity());
        return ADD_PICTURE_PAGE;
    }

    @RequestMapping(value = "insertPicture")
    @ApiOperation(value = "新增图片")
    public String insertPicture(PictureVo pictureVo, MultipartFile pictureUrl, HttpSession session, RedirectAttributes attributes) {
        Map<String,Object> map = UploadUtil.upload(pictureUrl);
        Integer code = Integer.parseInt(map.get(BlogConstant.CODE).toString());
        // 判断是否成功，200 -> 成功
        if (!code.equals(BlogConstant.SUCCESS)) {
            attributes.addFlashAttribute("message", "图片上传失败");
            return ADD_PICTURE_PAGE;
        }
        String url = map.get(BlogConstant.PICTURE_SRC).toString();
        pictureVo.setUrl(BlogConstant.PICTURE_URL + url);
        UserEntity user = (UserEntity) session.getAttribute("admin");
        pictureVo.setUserId(user.getId());
        int res = pictureService.insertPicture(pictureVo);
        if (res > 0) {
            return REDIRECT_PICTURE_PAGE;
        }
        attributes.addFlashAttribute("message", "图片新增失败");
        return ADD_PICTURE_PAGE;
    }

    @RequestMapping("/editPicture/{id}")
    @ApiOperation(value = "跳转到编辑页面")
    public String toEditPicturePage(@PathVariable Long id, Model model) {
        PictureEntity picture = pictureService.getPictureById(id);
        model.addAttribute("picture", picture);
        return ADD_PICTURE_PAGE;
    }

    @RequestMapping(value = "updatePicture")
    @ApiOperation(value = "更新图片")
    public String updatePicture(PictureVo pictureVo,MultipartFile pictureUrl, HttpSession session, RedirectAttributes attributes) {
        if(StringUtils.isNotBlank(pictureUrl.getOriginalFilename())) {
            Map<String,Object> map = UploadUtil.upload(pictureUrl);
            Integer code = Integer.parseInt(map.get(BlogConstant.CODE).toString());
            // 判断是否成功，200 -> 成功
            if (!code.equals(BlogConstant.SUCCESS)) {
                attributes.addFlashAttribute("message", "图片上传失败");
                return ADD_PICTURE_PAGE;
            }
            String url = map.get(BlogConstant.PICTURE_SRC).toString();
            pictureVo.setUrl(BlogConstant.PICTURE_URL + url);
        }
        UserEntity user = (UserEntity) session.getAttribute("admin");
        pictureVo.setUserId(user.getId());
        int res = pictureService.updatePicture(pictureVo);
        if (res > 0) {
            return REDIRECT_PICTURE_PAGE;
        }
        return ADD_PICTURE_PAGE;
    }


    @RequestMapping("/delete/{id}")
    @ApiOperation(value = "删除图片接口")
    public String deletePicture(@PathVariable Long id, HttpSession session) {
        UserEntity user = (UserEntity) session.getAttribute("admin");
        pictureService.deletePicture(id, user.getId());
        return REDIRECT_PICTURE_PAGE;
    }
}

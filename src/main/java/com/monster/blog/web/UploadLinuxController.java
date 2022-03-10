//package com.monster.blog.web;
//
//import com.google.common.collect.Maps;
//import com.monster.blog.util.SftpUtil;
//import org.springframework.beans.factory.annotation.Value;
//import org.springframework.stereotype.Controller;
//import org.springframework.web.bind.annotation.RequestMapping;
//import org.springframework.web.bind.annotation.ResponseBody;
//import org.springframework.web.multipart.MultipartFile;
//
//import javax.servlet.http.HttpServletRequest;
//import java.util.HashMap;
//import java.util.Map;
//import java.util.UUID;
//
///**
// * @author liz
// * @Description 上传图片到服务器
// * @date 2020/6/17-16:29
// */
//@Controller
//@RequestMapping("/file")
//public class UploadLinuxController {
//
//    @Value("${nginx.username}")
//    private String userName;
//    @Value("${nginx.password}")
//    private String password;
//    @Value("${nginx.ip}")
//    private String ip;
//    @Value("${nginx.id}")
//    private Integer id;
//    @Value("${nginx.uploadPath}")
//    private String uploadPath;
//    @Value("${nginx.port}")
//    private String port;
//
//    @RequestMapping("/upload")
//    @ResponseBody
//    public Map<String, Object> upload(MultipartFile file, HttpServletRequest request) {
//        System.err.println("前台获取的值 file --------->" + file);
//        String savePath;
//        Map<String, Object> map = Maps.newHashMap();
//        //实例化工具类,填写用户名，密码，ip地址，端口号，用来连接linux
//        SftpUtil sftpUtil = new SftpUtil(userName, password, ip, id);
//        //连接服务器
//        sftpUtil.login();
//        try {
//            String filename = file.getOriginalFilename();
//            System.err.println("图片名称----------->" + filename);
//            //保存的文件的名称
//            String name = UUID.randomUUID().toString().replace("-", "") + filename.substring(filename.lastIndexOf("."));
//            System.out.println(uploadPath + "====上传地址===");
//            sftpUtil.upload(uploadPath, name, file.getInputStream());
//            //图片在nginx中的位置
//            savePath = name;
//            map.put("code", 200);
//            map.put("src", savePath);
//        } catch (Exception e) {
//            System.out.println("上传失败。。。。。");
//            map.put("code", 400);
//            e.printStackTrace();
//        } finally {
//            //释放连接
//            sftpUtil.logout();
//        }
//        return map;
//    }
//
//}
//

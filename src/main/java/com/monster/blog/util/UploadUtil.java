package com.monster.blog.util;

import com.google.common.collect.Maps;
import org.springframework.web.multipart.MultipartFile;

import java.util.Map;
import java.util.UUID;

/**
 * @author liz
 * @Description 上传图片工具类
 * @date 2020/6/28-21:02
 */
public class UploadUtil {

    private static String userName = "root";
    private static String password = "Xyc86418332";
    private static String ip = "39.97.214.10";
    private static Integer id = 22;
    private static String uploadPath = "/usr/local/nginx/html";
    private static String port = "80";

    public static Map<String, Object> upload(MultipartFile file) {
        System.err.println("前台获取的值 file --------->" + file);
        String savePath;
        Map<String, Object> map = Maps.newHashMap();
        //实例化工具类,填写用户名，密码，ip地址，端口号，用来连接linux
        SftpUtil sftpUtil = new SftpUtil(userName, password, ip, id);
        //连接服务器
        sftpUtil.login();
        try {
            String filename = file.getOriginalFilename();
            System.err.println("图片名称----------->" + filename);
            //保存的文件的名称
            String name = UUID.randomUUID().toString().replace("-", "") + filename.substring(filename.lastIndexOf("."));
            System.out.println(uploadPath + "====上传地址===");
            sftpUtil.upload(uploadPath, name, file.getInputStream());
            //图片在nginx中的位置
            savePath = name;
            map.put("code", 200);
            map.put("src", savePath);
        } catch (Exception e) {
            System.out.println("上传失败。。。。。");
            map.put("code", 400);
            e.printStackTrace();
        } finally {
            //释放连接
            sftpUtil.logout();
        }
        return map;
    }
}

package com.monster.blog.web.file;

import com.monster.blog.domain.RestfulResult;
import com.monster.blog.util.FileDfsUtil;
import io.swagger.annotations.Api;
import io.swagger.annotations.ApiOperation;
import org.apache.commons.lang3.StringUtils;
import org.springframework.web.bind.annotation.RequestMapping;
import org.springframework.web.bind.annotation.RequestMethod;
import org.springframework.web.bind.annotation.RequestParam;
import org.springframework.web.bind.annotation.RestController;
import org.springframework.web.multipart.MultipartFile;

import javax.annotation.Resource;

/**
 * @author liz
 * @Description 文件上传服务接口
 * @date 2020/8/5-14:01
 */
@RestController
@Api(value = "文件上传服务接口")
public class FileController {

    @Resource
    private FileDfsUtil fileDfsUtil;

    /**
     * 文件上传
     */
    @ApiOperation(value = "上传文件", notes = "测试FastDFS文件上传")
    @RequestMapping(value = "/uploadFile", headers = "content-type=multipart/form-data", method = RequestMethod.POST)
    public RestfulResult<String> uploadFile(@RequestParam("file") MultipartFile file) {
        String result;
        try {
            String path = fileDfsUtil.upload(file);
            if (!StringUtils.isEmpty(path)) {
                result = path;
            } else {
                result = "上传失败";
            }
        } catch (Exception e) {
            e.printStackTrace();
            result = "服务异常";
        }
        return RestfulResult.success(result);
    }

    /**
     * 文件删除
     */
    @RequestMapping(value = "/deleteByPath", method = RequestMethod.GET)
    public RestfulResult<String> deleteByPath() {
        String filePathName = "group1/M00/00/00/wKhIgl0n4AKABxQEABhlMYw_3Lo825.png";
        fileDfsUtil.deleteFile(filePathName);
        return RestfulResult.success("SUCCESS");
    }
}

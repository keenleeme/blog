package com.monster.blog.service;

import com.monster.blog.domain.PictureEntity;
import com.baomidou.mybatisplus.extension.service.IService;
import com.monster.blog.vo.PictureVo;

import java.util.List;

/**
 * <p>
 * 图片墙表 服务类
 * </p>
 *
 * @author liz
 * @since 2020-05-28
 */
public interface PictureService extends IService<PictureEntity> {

    List<PictureEntity> getPictureList(Long userId);

    int insertPicture(PictureVo pictureVo);

    int updatePicture(PictureVo pictureVo);

    int deletePicture(Long id,Long userId);

    PictureEntity getPictureById(Long id);

    List<PictureEntity> getPictureWall();

}

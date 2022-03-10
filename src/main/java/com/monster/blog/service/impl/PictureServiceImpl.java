package com.monster.blog.service.impl;

import com.baomidou.mybatisplus.core.conditions.query.QueryWrapper;
import com.monster.blog.constant.BlogConstant;
import com.monster.blog.domain.PictureEntity;
import com.monster.blog.mapper.PictureMapper;
import com.monster.blog.service.PictureService;
import com.baomidou.mybatisplus.extension.service.impl.ServiceImpl;
import com.monster.blog.vo.PictureVo;
import org.springframework.stereotype.Service;

import javax.annotation.Resource;
import java.util.Date;
import java.util.List;

/**
 * <p>
 * 图片墙表 服务实现类
 * </p>
 *
 * @author liz
 * @since 2020-05-28
 */
@Service
public class PictureServiceImpl extends ServiceImpl<PictureMapper, PictureEntity> implements PictureService {

    @Resource
    private PictureMapper pictureMapper;

    @Override
    public List<PictureEntity> getPictureList(Long userId) {
        QueryWrapper<PictureEntity> query = new QueryWrapper<>();
        query.eq(BlogConstant.IS_REMOVED_STR, 0);
        query.eq(BlogConstant.CREATED_BY, userId);
        query.orderByDesc(BlogConstant.CREATED_TIME);
        return pictureMapper.selectList(query);
    }

    @Override
    public int insertPicture(PictureVo pictureVo) {
        PictureEntity picture = new PictureEntity();
        picture.setCreatedBy(pictureVo.getUserId())
                .setCreatedTime(new Date())
                .setDescs(pictureVo.getDesc())
                .setIsRemoved(0)
                .setPictureName(pictureVo.getPictureName())
                .setPictureTime(pictureVo.getPictureTime())
                .setPictureUrl(pictureVo.getUrl())
                .setUpdatedTime(new Date());
        return pictureMapper.insert(picture);
    }

    @Override
    public PictureEntity getPictureById(Long id) {

        return pictureMapper.selectById(id);
    }

    @Override
    public int updatePicture(PictureVo pictureVo) {
        PictureEntity picture = pictureMapper.selectById(pictureVo.getId());
        picture.setDescs(pictureVo.getDesc())
                .setPictureName(pictureVo.getPictureName())
                .setPictureTime(pictureVo.getPictureTime())
                .setPictureUrl(pictureVo.getUrl())
                .setUpdatedTime(new Date())
                .setUpdatedBy(pictureVo.getUserId());
        return pictureMapper.updateById(picture);
    }

    @Override
    public int deletePicture(Long id,Long userId) {
        PictureEntity picture = pictureMapper.selectById(id);
        picture.setUpdatedTime(new Date())
                .setIsRemoved(1)
                .setUpdatedBy(userId);
        return pictureMapper.updateById(picture);
    }

    @Override
    public List<PictureEntity> getPictureWall() {
        QueryWrapper<PictureEntity> query = new QueryWrapper<>();
        query.eq(BlogConstant.IS_REMOVED_STR, 0);
        query.orderByDesc(BlogConstant.CREATED_TIME);
        return pictureMapper.selectList(query);
    }
}

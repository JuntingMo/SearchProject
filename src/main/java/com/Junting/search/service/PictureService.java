package com.Junting.search.service;

import com.Junting.search.model.entity.Picture;
import com.baomidou.mybatisplus.extension.plugins.pagination.Page;

/**
 * 图片服务接口
 *
 * @author Junting
 *
 */
public interface PictureService{

    Page<Picture> searchPicture(String searchText, long pageNum, long pageSize);
}

package com.yupi.springbootinit.service;

import com.baomidou.mybatisplus.core.conditions.query.QueryWrapper;
import com.baomidou.mybatisplus.extension.plugins.pagination.Page;
import com.baomidou.mybatisplus.extension.service.IService;
import com.yupi.springbootinit.model.dto.post.PostQueryRequest;
import com.yupi.springbootinit.model.entity.Picture;
import com.yupi.springbootinit.model.entity.Post;
import com.yupi.springbootinit.model.vo.PostVO;

import javax.servlet.http.HttpServletRequest;
import java.util.List;

/**
 * 图片服务接口
 *
 */
public interface PictureService{

    Page<Picture> searchPicture(String searchText, long pageNum, long pageSize);
}

package com.Junting.search.manager;

import com.Junting.search.datasource.*;
import com.Junting.search.model.dto.post.PostQueryRequest;
import com.Junting.search.model.dto.search.SearchRequest;
import com.Junting.search.model.dto.user.UserQueryRequest;
import com.Junting.search.model.entity.Picture;
import com.Junting.search.model.enums.SearchTypeEnum;
import com.Junting.search.model.vo.PostVO;
import com.Junting.search.model.vo.SearchVO;
import com.Junting.search.model.vo.UserVO;
import com.baomidou.mybatisplus.extension.plugins.pagination.Page;
import com.Junting.search.common.ErrorCode;
import com.Junting.search.datasource.*;
import com.Junting.search.exception.BusinessException;
import com.Junting.search.exception.ThrowUtils;
import lombok.extern.slf4j.Slf4j;
import org.apache.commons.lang3.StringUtils;
import org.springframework.stereotype.Component;
import org.springframework.web.bind.annotation.RequestBody;

import javax.annotation.Resource;
import javax.servlet.http.HttpServletRequest;
import java.util.concurrent.CompletableFuture;

/**
 * 搜索门面
 *
 * @author Junting
 *
 */
@Component
@Slf4j
public class SearchFacade {

    @Resource
    private PostDataSource postDataSource;

    @Resource
    private UserDataSource userDataSource;

    @Resource
    private PictureDataSource pictureDataSource;

    @Resource
    private DataSourceRegistry dataSourceRegistry;

    public SearchVO searchAll(@RequestBody SearchRequest searchRequest, HttpServletRequest request) {
//        //非多线程
//        String searchText = searchRequest.getSearchText();
//        Page<Picture> picturePage = pictureService.searchPicture(searchText, 1, 10);
//
//        UserQueryRequest userQueryRequest = new UserQueryRequest();
//        userQueryRequest.setUserName(searchText);
//        Page<UserVO> userVOPage = userService.listUserVOByPage(userQueryRequest);
//
//        PostQueryRequest postQueryRequest = new PostQueryRequest();
//        postQueryRequest.setSearchText(searchText);
//        Page<PostVO> postVOPage = postService.listPostVOByPage(postQueryRequest, request);
//
//        SearchVO searchVO = new SearchVO();
//        searchVO.setUserList(userVOPage.getRecords());
//        searchVO.setPostList(postVOPage.getRecords());
//        searchVO.setPictureList(picturePage.getRecords());
//
//        return ResultUtils.success(searchVO);

//        //多线程
//        String searchText = searchRequest.getSearchText();
//
//        CompletableFuture<Page<UserVO>> userTask = CompletableFuture.supplyAsync(() -> {
//            UserQueryRequest userQueryRequest = new UserQueryRequest();
//            userQueryRequest.setUserName(searchText);
//            Page<UserVO> userVOPage = userService.listUserVOByPage(userQueryRequest);
//            return userVOPage;
//        });
//
//        CompletableFuture<Page<PostVO>> postTask = CompletableFuture.supplyAsync(() -> {
//            PostQueryRequest postQueryRequest = new PostQueryRequest();
//            postQueryRequest.setSearchText(searchText);
//            Page<PostVO> postVOPage = postService.listPostVOByPage(postQueryRequest, request);
//            return postVOPage;
//        });
//
//        CompletableFuture<Page<Picture>> pictureTask = CompletableFuture.supplyAsync(() -> {
//            Page<Picture> picturePage = pictureService.searchPicture(searchText, 1, 10);
//            return picturePage;
//        });
//
//        CompletableFuture.allOf(userTask, postTask, pictureTask).join();
//        try {
//            Page<UserVO> userVOPage = userTask.get();
//            Page<PostVO> postVOPage = postTask.get();
//            Page<Picture> picturePage = pictureTask.get();
//
//            SearchVO searchVO = new SearchVO();
//            searchVO.setUserList(userVOPage.getRecords());
//            searchVO.setPostList(postVOPage.getRecords());
//            searchVO.setPictureList(picturePage.getRecords());
//            return ResultUtils.success(searchVO);
//        } catch (Exception e) {
//            log.error("查询异常", e);
//            throw new BusinessException(ErrorCode.SYSTEM_ERROR, "查询异常");
//        }

        String type = searchRequest.getType();
        SearchTypeEnum searchTypeEnum = SearchTypeEnum.getEnumByValue(type);
        ThrowUtils.throwIf(StringUtils.isBlank(type), ErrorCode.PARAMS_ERROR);
        String searchText = searchRequest.getSearchText();
        long current = searchRequest.getCurrent();
        long pageSize = searchRequest.getPageSize();

        // 如果为空则搜索出所有数据
        if (searchTypeEnum == null) {

            CompletableFuture<Page<UserVO>> userTask = CompletableFuture.supplyAsync(() -> {
                UserQueryRequest userQueryRequest = new UserQueryRequest();
                userQueryRequest.setUserName(searchText);
                Page<UserVO> userVOPage = userDataSource.doSearch(searchText, current, pageSize);
                return userVOPage;
            });

            CompletableFuture<Page<PostVO>> postTask = CompletableFuture.supplyAsync(() -> {
                PostQueryRequest postQueryRequest = new PostQueryRequest();
                postQueryRequest.setSearchText(searchText);
                Page<PostVO> postVOPage = postDataSource.doSearch(searchText, current, pageSize);
                return postVOPage;
            });

            CompletableFuture<Page<Picture>> pictureTask = CompletableFuture.supplyAsync(() -> {
                Page<Picture> picturePage = pictureDataSource.doSearch(searchText, 1, 10);
                return picturePage;
            });

            CompletableFuture.allOf(userTask, postTask, pictureTask).join();
            try {
                Page<UserVO> userVOPage = userTask.get();
                Page<PostVO> postVOPage = postTask.get();
                Page<Picture> picturePage = pictureTask.get();

                SearchVO searchVO = new SearchVO();
                searchVO.setUserList(userVOPage.getRecords());
                searchVO.setPostList(postVOPage.getRecords());
                searchVO.setPictureList(picturePage.getRecords());
                return searchVO;
            } catch (Exception e) {
                log.error("查询异常", e);
                throw new BusinessException(ErrorCode.SYSTEM_ERROR, "查询异常");
            }
        } else {
            SearchVO searchVO = new SearchVO();
            DataSource<?> dataSource = dataSourceRegistry.getDataSourceByType(type);
            Page<?> page = dataSource.doSearch(searchText, current, pageSize);
            searchVO.setDataList(page.getRecords());
            return searchVO;
        }

    }
}

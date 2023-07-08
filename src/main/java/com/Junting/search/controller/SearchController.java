package com.Junting.search.controller;

import com.Junting.search.model.dto.search.SearchRequest;
import com.Junting.search.model.vo.SearchVO;
import com.Junting.search.common.BaseResponse;
import com.Junting.search.common.ResultUtils;
import com.Junting.search.manager.SearchFacade;
import com.Junting.search.service.PictureService;
import com.Junting.search.service.PostService;
import com.Junting.search.service.UserService;
import lombok.extern.slf4j.Slf4j;
import org.springframework.web.bind.annotation.PostMapping;
import org.springframework.web.bind.annotation.RequestBody;
import org.springframework.web.bind.annotation.RequestMapping;
import org.springframework.web.bind.annotation.RestController;

import javax.annotation.Resource;
import javax.servlet.http.HttpServletRequest;

/**
 * 聚合搜索Controller
 *
 * @author Junting
 *
 */
@RestController
@RequestMapping("/search")
@Slf4j
public class SearchController {

    @Resource
    private UserService userService;

    @Resource
    private PostService postService;

    @Resource
    private PictureService pictureService;

    @Resource
    private SearchFacade searchFacade;

    @PostMapping("/all")
    public BaseResponse<SearchVO> searchAll(@RequestBody SearchRequest searchRequest, HttpServletRequest request) {
        return ResultUtils.success(searchFacade.searchAll(searchRequest, request));
    }
}

package com.Junting.search.model.entity;

import lombok.Data;

import java.io.Serializable;

/**
 * 图片实体
 *
 * @author Junting
 *
 */

@Data
public class Picture implements Serializable {

    private String title;

    private String url;

    private static final long serialVersionUID = 1L;
}

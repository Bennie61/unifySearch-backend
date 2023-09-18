package com.yupi.unifySearch.model.entity;

import lombok.Data;

import java.io.Serializable;

/**
 * @Description 图片
 * @Author benny
 **/

@Data
public class Picture implements Serializable {
    private String title;
    private String url;
    private static final long serialVersionUID = 1L;
}

package com.alkemy.ong.dto;

import lombok.Getter;
import lombok.Setter;

import java.util.List;
@Getter
@Setter
public class PageFormatter<T> {
    private List<T> pageContent;
    private String previousPageUrl;
    private String nextPageUrl;
}

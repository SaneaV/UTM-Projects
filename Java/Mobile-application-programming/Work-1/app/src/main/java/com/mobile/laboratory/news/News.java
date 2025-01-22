package com.mobile.laboratory.news;

import lombok.Getter;
import lombok.Builder;
import lombok.RequiredArgsConstructor;

import java.io.Serializable;

@Getter
@RequiredArgsConstructor
public class News implements Serializable {
    private final String name;
    private final String description;
    private final String image;
    private final String link;
}
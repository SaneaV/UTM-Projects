package com.mobile.laboratory.news;

import java.util.List;

public interface NewsRepository {
    List<News> getLastNews();

    String getDescription();
}
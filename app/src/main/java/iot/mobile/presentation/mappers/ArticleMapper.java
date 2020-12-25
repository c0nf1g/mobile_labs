package iot.mobile.presentation.mappers;

import io.reactivex.annotations.NonNull;
import io.reactivex.functions.Function;
import iot.mobile.domain.entity.Article;
import iot.mobile.domain.entity.NewsData;
import iot.mobile.presentation.uiData.NewsViewData;

public class ArticleMapper implements Function<NewsData, NewsViewData> {
    private String publishedAt;

    public ArticleMapper(String publishedAt) {
        this.publishedAt = publishedAt;
    }

    @Override
    public NewsViewData apply(@NonNull NewsData newsData) throws Exception {
        NewsViewData newsViewData = null;
        for (Article item : newsData.getArticles()) {
            if (item.getPublishedAt().equals(publishedAt)) {
                newsViewData = new NewsViewData(
                        item.getAuthor(),
                        item.getTitle(),
                        item.getDescription(),
                        item.getUrlToImage(),
                        item.getContent());
            }
        }
        return newsViewData;
    }
}

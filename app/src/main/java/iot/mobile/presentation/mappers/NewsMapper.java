package iot.mobile.presentation.mappers;

import java.util.ArrayList;
import java.util.List;
import java.util.function.Function;

import iot.mobile.domain.entity.Article;
import iot.mobile.domain.entity.NewsData;
import iot.mobile.presentation.uiData.NewsViewData;

public class NewsMapper implements Function<NewsData, List<NewsViewData>> {
    @Override
    public List<NewsViewData> apply(NewsData newsData) {
        List<NewsViewData> result = new ArrayList<>();
        for (Article item : newsData.getArticles()) {
            NewsViewData newsViewData = new NewsViewData(
                    item.getAuthor(),
                    item.getTitle(),
                    item.getDescription(),
                    item.getUrlToImage());
            result.add(newsViewData);
        }

        return result;
    }
}

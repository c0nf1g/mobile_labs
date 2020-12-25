package iot.mobile.domain.repository;

import io.reactivex.Single;
import iot.mobile.domain.entity.NewsData;

public interface IRepository {
    Single<NewsData> loadNews();
}

package iot.mobile.data;

import io.reactivex.Single;
import iot.mobile.domain.entity.NewsData;
import retrofit2.http.GET;
import retrofit2.http.Query;

public interface RandomNewsService {
    @GET(".")
    Single<NewsData> loadNews(@Query("sources") String sources,
                               @Query("apiKey") String apiKey);
}

package iot.mobile.data.repository;

import io.reactivex.Single;
import iot.mobile.data.RandomNewsService;
import iot.mobile.domain.entity.Article;
import iot.mobile.domain.entity.NewsData;
import iot.mobile.domain.repository.IRepository;
import okhttp3.OkHttpClient;
import okhttp3.logging.HttpLoggingInterceptor;
import retrofit2.Retrofit;
import retrofit2.adapter.rxjava2.RxJava2CallAdapterFactory;
import retrofit2.converter.gson.GsonConverterFactory;

public class RemoteRepository implements IRepository {
    private static final String BASE_URL = "http://newsapi.org/v2/top-headlines/";
    private static final String SOURCES = "techcrunch";
    private static final String API_KEY = "67f2f2bd2fde46abb048f6ac610fe3ca";

    private final RandomNewsService service;

    public RemoteRepository() {
        Retrofit retrofit = new Retrofit.Builder()
                .baseUrl(BASE_URL)
                .addConverterFactory(GsonConverterFactory.create())
                .client(getLoggingClient())
                .addCallAdapterFactory(RxJava2CallAdapterFactory.create())
                .build();
        service = retrofit.create(RandomNewsService.class);
    }

    public OkHttpClient getLoggingClient() {
        HttpLoggingInterceptor logging = new HttpLoggingInterceptor();
        logging.setLevel(HttpLoggingInterceptor.Level.BASIC);
        return  new OkHttpClient.Builder()
                .addInterceptor(logging)
                .build();
    }

    @Override
    public Single<NewsData> loadNews() {
        return service.loadNews(SOURCES, API_KEY);
    }
}

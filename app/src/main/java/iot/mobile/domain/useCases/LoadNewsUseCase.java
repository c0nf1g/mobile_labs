
package iot.mobile.domain.useCases;

import io.reactivex.Single;
import iot.mobile.domain.entity.NewsData;
import iot.mobile.domain.repository.IRepository;

public class LoadNewsUseCase {
    private final IRepository repository;

    public LoadNewsUseCase(IRepository repository) {
        this.repository = repository;
    }

    public Single<NewsData> loadNews() {
        return repository.loadNews();
    }
}
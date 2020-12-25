package iot.mobile.presentation.viewModels;

import androidx.lifecycle.MutableLiveData;
import androidx.lifecycle.ViewModel;


import org.jetbrains.annotations.NotNull;

import io.reactivex.android.schedulers.AndroidSchedulers;
import io.reactivex.disposables.CompositeDisposable;
import io.reactivex.schedulers.Schedulers;
import iot.mobile.domain.useCases.LoadNewsUseCase;
import iot.mobile.presentation.mappers.ArticleMapper;
import iot.mobile.presentation.uiData.NewsViewData;
import timber.log.Timber;

public class ArticleViewModel extends ViewModel {
    private CompositeDisposable compositeDisposable = new CompositeDisposable();
    private MutableLiveData<String> errorMessage = new MutableLiveData<>();

    private MutableLiveData<NewsViewData> article = new MutableLiveData<>();
    private LoadNewsUseCase loadNewsUseCase;

    public ArticleViewModel(LoadNewsUseCase loadNewsUseCase) {
        this.loadNewsUseCase = loadNewsUseCase;
    }

    public void loadSpecificArticles(String publishedAt) {
        ArticleMapper mapper = new ArticleMapper(publishedAt);
        compositeDisposable.add(
                loadNewsUseCase.loadNews()
                    .map(mapper::apply)
                    .subscribeOn(Schedulers.io())
                    .observeOn(AndroidSchedulers.mainThread())
                    .subscribe(
                            data -> article.setValue(data),
                            error -> {
                                Timber.e(error);
                                errorMessage.setValue(error.getMessage());
                            }
                    )
        );
    }

    public MutableLiveData<NewsViewData> getArticle() {
        return article;
    }
}

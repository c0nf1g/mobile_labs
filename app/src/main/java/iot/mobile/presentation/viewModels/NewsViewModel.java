package iot.mobile.presentation.viewModels;

import androidx.lifecycle.MutableLiveData;
import androidx.lifecycle.ViewModel;

import java.util.List;

import io.reactivex.android.schedulers.AndroidSchedulers;
import io.reactivex.disposables.CompositeDisposable;
import io.reactivex.schedulers.Schedulers;
import iot.mobile.domain.useCases.LoadNewsUseCase;
import iot.mobile.presentation.mappers.NewsMapper;
import iot.mobile.presentation.uiData.NewsViewData;
import timber.log.Timber;

public class NewsViewModel extends ViewModel {
    private CompositeDisposable compositeDisposable = new CompositeDisposable();
    private final LoadNewsUseCase loadNewsUseCase;
    private MutableLiveData<String> errorMessage = new MutableLiveData<>();
    private MutableLiveData<List<NewsViewData>> response = new MutableLiveData<>();

    public NewsViewModel(LoadNewsUseCase loadNewsUseCase) {
        this.loadNewsUseCase = loadNewsUseCase;
    }

    public void loadNewsList() {
        NewsMapper mapper = new NewsMapper();
        compositeDisposable.add(
            loadNewsUseCase.loadNews()
                    .map(mapper::apply)
                    .subscribeOn(Schedulers.io())
                    .observeOn(AndroidSchedulers.mainThread())
                    .subscribe(
                            data -> response.setValue(data),
                            error -> {
                                Timber.e(error);
                                errorMessage.setValue(error.getMessage());
                            }
                    )
        );
    }



    @Override
    protected void onCleared() {
        super.onCleared();

        compositeDisposable.dispose();
    }

    public MutableLiveData<String> getErrorMessage() {
        return errorMessage;
    }

    public MutableLiveData<List<NewsViewData>> getResponse() {
        return response;
    }
}

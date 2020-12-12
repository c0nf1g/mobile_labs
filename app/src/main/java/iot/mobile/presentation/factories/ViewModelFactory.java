package iot.mobile.presentation.factories;

import androidx.annotation.NonNull;
import androidx.lifecycle.ViewModel;
import androidx.lifecycle.ViewModelProvider;

import iot.mobile.data.repository.RemoteRepository;
import iot.mobile.domain.repository.IRepository;
import iot.mobile.domain.useCases.LoadNewsUseCase;
import iot.mobile.presentation.viewModels.NewsViewModel;

public class ViewModelFactory implements ViewModelProvider.Factory {
    private IRepository repository = new RemoteRepository();
    private LoadNewsUseCase loadNewsUseCase = new LoadNewsUseCase(repository);

    @NonNull
    @Override
    public <T extends ViewModel> T create(@NonNull Class<T> modelClass) {
        return (T) new NewsViewModel(loadNewsUseCase);
    }
}

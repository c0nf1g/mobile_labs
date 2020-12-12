package iot.mobile.presentation.fragments;

import android.os.Bundle;

import androidx.fragment.app.Fragment;
import androidx.lifecycle.ViewModelProvider;
import androidx.recyclerview.widget.LinearLayoutManager;
import androidx.recyclerview.widget.RecyclerView;

import android.view.LayoutInflater;
import android.view.View;
import android.view.ViewGroup;
import android.widget.Toast;

import iot.mobile.R;
import iot.mobile.presentation.factories.ViewModelFactory;
import iot.mobile.presentation.holders.NewsAdapter;
import iot.mobile.presentation.viewModels.NewsViewModel;
import timber.log.Timber;

public class NewsFragment extends Fragment {
    private RecyclerView newsList;
    private NewsAdapter newsAdapter = new NewsAdapter();

    @Override
    public void onCreate(Bundle savedInstanceState) {
        super.onCreate(savedInstanceState);
    }

    @Override
    public View onCreateView(LayoutInflater inflater, ViewGroup container,
                             Bundle savedInstanceState) {
        // Inflate the layout for this fragment
        View peopleView = inflater.inflate(R.layout.fragment_start_screen, container, false);

        initRecycler(peopleView);

        initViewModel();

        return peopleView;
    }

    private void initViewModel() {
        NewsViewModel newsViewModel = new ViewModelProvider(this, new ViewModelFactory())
                .get(NewsViewModel.class);

        getNewsData(newsViewModel);
        getDataErrorMessage(newsViewModel);
        newsViewModel.loadNewsList();
    }

    private void getNewsData(NewsViewModel newsViewModel) {
        newsViewModel.getResponse().observe(getActivity(), response -> {
            newsAdapter.setItems(response);
        });
    }

    private void getDataErrorMessage(NewsViewModel newsViewModel) {
        newsViewModel.getErrorMessage().observe(getActivity(), errorMessage -> {
            Timber.e(errorMessage);
            Toast.makeText(getActivity(), errorMessage, Toast.LENGTH_LONG).show();
        });
    }

    private void initRecycler(View peopleView) {
        newsList = peopleView.findViewById(R.id.news_list);
        newsList.setLayoutManager(new LinearLayoutManager(getActivity(), LinearLayoutManager.VERTICAL, false));
        newsList.setAdapter(newsAdapter);
    }
}
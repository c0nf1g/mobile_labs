package iot.mobile.presentation.fragments;

import android.content.Context;
import android.os.Bundle;

import androidx.fragment.app.Fragment;
import androidx.lifecycle.ViewModelProvider;
import androidx.recyclerview.widget.LinearLayoutManager;
import androidx.recyclerview.widget.RecyclerView;
import androidx.swiperefreshlayout.widget.SwipeRefreshLayout;

import android.view.LayoutInflater;
import android.view.View;
import android.view.ViewGroup;
import android.widget.Button;
import android.widget.ProgressBar;

import org.jetbrains.annotations.NotNull;

import iot.mobile.R;
import iot.mobile.presentation.helpers.AlertManager;
import iot.mobile.presentation.listeners.NewsListener;
import iot.mobile.presentation.holders.NewsAdapter;
import iot.mobile.presentation.viewModels.NewsViewModel;
import iot.mobile.presentation.factories.ViewModelFactory;

public class NewsFragment extends Fragment {
    private RecyclerView newsList;
    private NewsAdapter newsAdapter = new NewsAdapter();
    private SwipeRefreshLayout swipeRefreshLayout;
    private NewsViewModel newsViewModel;
    private ProgressBar loading_indicator;
    private Button myProfileButton;
    private NewsListener onNewsItemClickListener;
    private NewsListener onMyProfileClickListener;
    private AlertManager alertManager = new AlertManager(getActivity());

    @Override
    public void onAttach(@NotNull Context context) {
        super.onAttach(context);

        try {
            onNewsItemClickListener = (NewsListener) context;
            newsAdapter.setListener(onNewsItemClickListener);
            onMyProfileClickListener = (NewsListener) context;
        } catch (ClassCastException e) {
            throw new ClassCastException(context.toString() + " must implement one of NewsListener methods");
        }
    }

    @Override
    public void onDetach() {
        super.onDetach();
        onNewsItemClickListener = null;
        onMyProfileClickListener = null;
    }

    @Override
    public void onCreate(Bundle savedInstanceState) {
        super.onCreate(savedInstanceState);
    }

    @Override
    public View onCreateView(LayoutInflater inflater, ViewGroup container,
                             Bundle savedInstanceState) {
        // Inflate the layout for this fragment
        View newsView = inflater.inflate(R.layout.fragment_news, container, false);
        initUI(newsView);
        initRecycler(newsView);
        initViewModel();

        return newsView;
    }

    private void initUI(View newsView) {
        swipeRefreshLayout = newsView.findViewById(R.id.swipe_refresh_layout);
        swipeRefreshLayout.setOnRefreshListener(() -> {
            newsViewModel.loadNewsList();
        });
        loading_indicator = newsView.findViewById(R.id.loading_indicator);
        loading_indicator.setVisibility(View.VISIBLE);
        myProfileButton = newsView.findViewById(R.id.profile_button);
        myProfileButton.setOnClickListener(view -> {
            onMyProfileClickListener.onMyProfileClick();
        });
    }

    private void initViewModel() {
        newsViewModel = new ViewModelProvider(this, new ViewModelFactory())
                .get(NewsViewModel.class);

        getNewsData(newsViewModel);
        getDataErrorMessage(newsViewModel);
        newsViewModel.loadNewsList();
    }

    private void getNewsData(NewsViewModel newsViewModel) {
        newsViewModel.getResponse().observe(getActivity(), response -> {
            newsAdapter.setItems(response);
            hideLoading();
        });
    }

    private void hideLoading() {
        swipeRefreshLayout.setRefreshing(false);
        loading_indicator.setVisibility(View.GONE);
    }

    private void getDataErrorMessage(NewsViewModel newsViewModel) {
        newsViewModel.getErrorMessage().observe(getActivity(), errorMessage -> {
            hideLoading();
            alertManager.showAlertDialog(
                    "Error",
                    "Failed to load news!"
            );
        });
    }

    private void initRecycler(View newsView) {
        newsList = newsView.findViewById(R.id.news_list);
        newsList.setLayoutManager(new LinearLayoutManager(getActivity(), LinearLayoutManager.VERTICAL, false));
        newsList.setAdapter(newsAdapter);
    }
}
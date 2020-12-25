package iot.mobile.presentation.activities;

import android.os.Bundle;

import androidx.annotation.Nullable;
import androidx.appcompat.app.AppCompatActivity;
import androidx.lifecycle.ViewModelProvider;

import iot.mobile.R;
import iot.mobile.presentation.factories.ArticleViewModelFactory;
import iot.mobile.presentation.viewModels.ArticleViewModel;
import timber.log.Timber;

public class NotificationActivity extends AppCompatActivity {
    private ArticleViewModel articleViewModel;

    @Override
    public void onCreate(@Nullable Bundle savedInstanceState) {
        super.onCreate(savedInstanceState);
        setContentView(R.layout.activity_notification);

        String publishedAt = getIntent().getExtras().getString("PUBLISHED_AT");
        initViewModel(publishedAt);
    }

    private void initViewModel(String publishedAt) {
        articleViewModel = new ViewModelProvider(this, new ArticleViewModelFactory())
                .get(ArticleViewModel.class);;

        articleViewModel.getArticle().observe(this, article -> {
            Timber.e(article.getContent());
            ArticleActivity.start(this, article);
        });
        articleViewModel.loadSpecificArticles(publishedAt);
    }
}

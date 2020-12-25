package iot.mobile.presentation.activities;

import android.content.Context;
import android.content.Intent;
import android.os.Bundle;
import android.widget.ImageView;
import android.widget.TextView;

import androidx.activity.OnBackPressedCallback;
import androidx.appcompat.app.AppCompatActivity;

import com.bumptech.glide.Glide;

import iot.mobile.R;
import iot.mobile.presentation.uiData.NewsViewData;
import timber.log.Timber;

public class ArticleActivity extends AppCompatActivity {
    private NewsViewData newsViewData;
    private TextView author;
    private TextView title;
    private TextView description;
    private TextView content;
    private ImageView photo;

    private static final String NEWS_VIEW_DATA_KEY = "newsViewData";

    public static void start(Context context, NewsViewData newsViewData) {
        Timber.i("Starter Activity");
        Intent starter = new Intent(context, ArticleActivity.class);
        starter.putExtra(NEWS_VIEW_DATA_KEY, newsViewData);
        context.startActivity(starter);
    }

    @Override
    protected void onCreate(Bundle savedInstanceState) {
        super.onCreate(savedInstanceState);
        setContentView(R.layout.activity_article);

        newsViewData = (NewsViewData) getIntent().getParcelableExtra(NEWS_VIEW_DATA_KEY);

        initUI();

        setUIValues();
    }

    private void setUIValues() {
        author.setText(newsViewData.getAuthor());
        title.setText(newsViewData.getTitle());
        description.setText(newsViewData.getDescription());
        content.setText(newsViewData.getContent());

        Glide.with(photo.getContext())
                .load(newsViewData.getPhotoUrl())
                .centerCrop()
                .into(photo);
    }

    private void initUI() {
        author = findViewById(R.id.news_author);
        title = findViewById(R.id.news_title);
        description = findViewById(R.id.news_description);
        content = findViewById(R.id.news_content);
        photo = findViewById(R.id.news_photo);
    }
}
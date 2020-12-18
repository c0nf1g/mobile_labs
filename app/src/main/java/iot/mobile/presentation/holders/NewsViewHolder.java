package iot.mobile.presentation.holders;

import android.view.View;
import android.widget.Button;
import android.widget.ImageView;
import android.widget.TextView;

import androidx.annotation.NonNull;

import com.bumptech.glide.Glide;

import iot.mobile.R;
import iot.mobile.presentation.uiData.NewsViewData;

public class NewsViewHolder extends BaseViewHolder<NewsViewData> {
    private TextView author;
    private TextView title;
    private TextView description;

    private ImageView articlePhoto;

    public NewsViewHolder(@NonNull View itemView) {
        super(itemView);

        author = itemView.findViewById(R.id.news_author);
        title = itemView.findViewById(R.id.news_title);
        description = itemView.findViewById(R.id.news_description);
        articlePhoto = itemView.findViewById(R.id.news_photo);
    }

    public void bindTo(NewsViewData newsViewData) {
        if (newsViewData != null) {
            author.setText(newsViewData.getAuthor());
            title.setText(newsViewData.getTitle());
            description.setText(newsViewData.getDescription());

            Glide.with(articlePhoto.getContext())
                    .load(newsViewData.getPhotoUrl())
                    .fitCenter()
                    .into(articlePhoto);
        }
    }
}

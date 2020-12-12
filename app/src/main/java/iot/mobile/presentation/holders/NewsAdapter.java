package iot.mobile.presentation.holders;

import android.view.LayoutInflater;
import android.view.View;
import android.view.ViewGroup;

import androidx.annotation.NonNull;
import androidx.recyclerview.widget.RecyclerView;

import java.util.ArrayList;
import java.util.List;

import iot.mobile.R;
import iot.mobile.presentation.uiData.NewsViewData;

public class NewsAdapter extends RecyclerView.Adapter<NewsViewHolder> {
    private final List<NewsViewData> newsList = new ArrayList<>();

    public void setItems(List<NewsViewData>  newsList) {
        this.newsList.clear();
        this.newsList.addAll(newsList);
        notifyDataSetChanged();
    }

    @NonNull
    @Override
    public NewsViewHolder onCreateViewHolder(@NonNull ViewGroup viewGroup, int viewType) {
        View rootView = LayoutInflater.from(viewGroup.getContext()).inflate(R.layout.news_item, viewGroup, false);
        return new NewsViewHolder(rootView);
    }

    @Override
    public void onBindViewHolder(@NonNull NewsViewHolder viewHolder, int position) {
        viewHolder.bindTo(newsList.get(position));
    }

    @Override
    public int getItemCount() {
        return newsList.size();
    }
}

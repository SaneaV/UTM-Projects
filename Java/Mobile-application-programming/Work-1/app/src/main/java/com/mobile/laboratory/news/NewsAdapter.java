package com.mobile.laboratory.news;

import android.content.Context;
import android.view.LayoutInflater;
import android.view.View;
import android.view.ViewGroup;
import android.widget.ImageView;
import android.widget.TextView;
import androidx.annotation.NonNull;
import androidx.recyclerview.widget.RecyclerView;
import com.mobile.laboratory.R;
import com.squareup.picasso.Picasso;
import java.util.List;

public class NewsAdapter extends RecyclerView.Adapter<NewsAdapter.NewsViewHolder> {

    private Context context;
    private List<News> newsList;
    private OnNewsClickListener listener;

    public interface OnNewsClickListener {
        void onNewsClick(News news);
    }

    public NewsAdapter(Context context, List<News> newsList, OnNewsClickListener listener) {
        this.context = context;
        this.newsList = newsList;
        this.listener = listener;
    }

    @NonNull
    @Override
    public NewsViewHolder onCreateViewHolder(@NonNull ViewGroup parent, int viewType) {
        View view = LayoutInflater.from(context).inflate(R.layout.item_news, parent, false);
        return new NewsViewHolder(view);
    }

    @Override
    public void onBindViewHolder(@NonNull NewsViewHolder holder, int position) {
        News news = newsList.get(position);
        holder.nameTextView.setText(news.getName());
        Picasso.get().load(news.getImage()).into(holder.imageView);
        holder.itemView.setOnClickListener(v -> listener.onNewsClick(news));
    }

    @Override
    public int getItemCount() {
        return newsList.size();
    }

    static class NewsViewHolder extends RecyclerView.ViewHolder {
        TextView nameTextView;
        ImageView imageView;

        NewsViewHolder(View itemView) {
            super(itemView);
            nameTextView = itemView.findViewById(R.id.nameTextView);
            imageView = itemView.findViewById(R.id.imageView);
        }
    }
}
package com.example.evliveraya.newsapplication.View;

import android.content.Context;
import android.support.design.widget.NavigationView;
import android.view.LayoutInflater;
import android.view.View;
import android.view.ViewGroup;
import android.widget.BaseAdapter;
import android.widget.ImageView;
import android.widget.TextClock;
import android.widget.TextView;

import com.example.evliveraya.newsapplication.Model.News;
import com.example.evliveraya.newsapplication.R;
import com.squareup.picasso.Picasso;

import java.lang.reflect.Array;
import java.util.ArrayList;
import java.util.List;

/**
 * Created by EvliveRaya on 11/04/2018.
 */

public class ListViewAdapter extends BaseAdapter {

    private Context context;
    private List<News> listNews = new ArrayList<>();

    public ListViewAdapter(Context context, List<News> listNews) {
        this.context = context;
        this.listNews = listNews;
    }

    @Override
    public int getCount() {
        return listNews.size();
    }

    @Override
    public Object getItem(int position) {
        return listNews.get(position);
    }

    @Override
    public long getItemId(int position) {
        return listNews.indexOf(getItem(position));
    }

    private class ViewHolder {
        ImageView image;
        TextView author;
        TextView title;
        TextView description;
        TextView date;
    }

    @Override
    public View getView(int position, View convertView, ViewGroup parent) {
        ViewHolder holder = null;
        if (convertView == null) {
            holder = new ViewHolder();
            convertView = LayoutInflater.from(context).inflate(R.layout.layout_list, parent, false);
            holder.image = (ImageView) convertView.findViewById(R.id.layout_list_image);
            holder.author = (TextView) convertView.findViewById(R.id.layout_list_author);
            holder.title = (TextView) convertView.findViewById(R.id.layout_list_title);
            holder.description = (TextView) convertView.findViewById(R.id.layout_list_description);
            holder.date = (TextView) convertView.findViewById(R.id.layout_list_date);
            convertView.setTag(holder);
        } else {
            holder = (ViewHolder) convertView.getTag();
        }
        News news = listNews.get(position);
        Picasso.get().load(news.getUrlToImage()).resize(300, 200).into(holder.image);
        holder.author.setText(news.getAuthor());
        holder.title.setText(news.getTitle());
        holder.description.setText(news.getDescription());
        holder.date.setText(news.getDate());
        return convertView;
    }
}

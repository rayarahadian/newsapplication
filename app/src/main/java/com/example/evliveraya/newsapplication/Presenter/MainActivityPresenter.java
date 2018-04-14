package com.example.evliveraya.newsapplication.Presenter;

import com.example.evliveraya.newsapplication.Model.News;
import com.example.evliveraya.newsapplication.View.MainActivityView;

import java.io.BufferedReader;
import java.io.InputStreamReader;
import java.net.HttpURLConnection;
import java.net.URL;
import java.util.ArrayList;
import java.util.List;

/**
 * Created by EvliveRaya on 10/04/2018.
 */

public class MainActivityPresenter implements Presenter {

    private MainActivityView view;
    private News news;
    private List<News> listNews = new ArrayList<>();
    private String news_Url, category;

    public MainActivityPresenter(MainActivityView view) {
        this.view = view;
    }

    @Override
    public void onCreate() {
    }

    @Override
    public void onPause() {

    }

    @Override
    public void onResume() {

    }

    @Override
    public void onDestroyer() {

    }

    public static String getData(String newsUrl) {
        URL url;
        HttpURLConnection urlConnection = null;

        try {
            url = new URL(newsUrl);
            urlConnection = (HttpURLConnection) url.openConnection();
            BufferedReader bufferedReader = new BufferedReader(new InputStreamReader(urlConnection.getInputStream()));

            //Stream to string
            String data;
            StringBuffer result = new StringBuffer();
            while ((data = bufferedReader.readLine()) != null) {
                result.append(data);
                result.append('\r');
            }
            bufferedReader.close();
            return result.toString();
        } catch (Exception e) {
            e.printStackTrace();
            return null;
        }
    }

}

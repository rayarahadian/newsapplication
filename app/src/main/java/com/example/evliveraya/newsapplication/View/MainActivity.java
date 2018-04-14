package com.example.evliveraya.newsapplication.View;

import android.app.ActivityOptions;
import android.content.Intent;
import android.os.AsyncTask;
import android.support.annotation.NonNull;
import android.support.design.widget.NavigationView;
import android.support.v4.app.ActivityOptionsCompat;
import android.support.v4.view.GravityCompat;
import android.support.v4.widget.DrawerLayout;
import android.support.v7.app.ActionBarDrawerToggle;
import android.support.v7.app.AppCompatActivity;
import android.os.Bundle;
import android.util.Pair;
import android.view.MenuItem;
import android.view.View;
import android.widget.AdapterView;
import android.widget.ListView;
import android.widget.ProgressBar;

import com.example.evliveraya.newsapplication.Model.News;
import com.example.evliveraya.newsapplication.Presenter.MainActivityPresenter;
import com.example.evliveraya.newsapplication.R;

import org.json.JSONArray;
import org.json.JSONException;
import org.json.JSONObject;

import java.util.ArrayList;
import java.util.List;

public class MainActivity extends AppCompatActivity implements MainActivityView, NavigationView.OnNavigationItemSelectedListener {

    private DrawerLayout drawerLayout;
    private ActionBarDrawerToggle actionBarDrawerToggle;
    private NavigationView navigationView;
    private MainActivityPresenter mainActivityPresenter;
    private Bundle bundle;
    private String category = "";
    private ListViewAdapter listViewAdapter;
    private ListView listView;
    private List<News> listNews = new ArrayList<>();
    private News news;
    private String newsUrl;
    private ProgressBar progressBar;

    @Override
    protected void onCreate(Bundle savedInstanceState) {
        super.onCreate(savedInstanceState);
        setContentView(R.layout.activity_main);

        drawerLayout = (DrawerLayout) findViewById(R.id.main_drawer_layout);
        actionBarDrawerToggle = new ActionBarDrawerToggle(this, drawerLayout, R.string.open, R.string.close);
        navigationView = (NavigationView) findViewById(R.id.main_navigation);
        listView = (ListView) findViewById(R.id.main_listview);
        progressBar = (ProgressBar) findViewById(R.id.main_progressbar);

        drawerLayout.addDrawerListener(actionBarDrawerToggle);
        actionBarDrawerToggle.syncState();

        getSupportActionBar().setDisplayHomeAsUpEnabled(true);
        navigationView.setNavigationItemSelectedListener(this);

        bundle = getIntent().getExtras();
        if (bundle != null) {
            category = bundle.getString("category");
        } else {
            category = "Headline";
        }
        newsUrl = newsUrlFromCategory(category);
        mainActivityPresenter = new MainActivityPresenter(this);
        GetNews getNews =  new GetNews(this);
        getNews.execute();
    }

    public ProgressBar getProgressBar() {
        return progressBar;
    }

    @Override
    public boolean onOptionsItemSelected(MenuItem item) {
        if (actionBarDrawerToggle.onOptionsItemSelected(item)) {
            return true;
        }

        return super.onOptionsItemSelected(item);
    }

    @Override
    public void categorySelected(int itemID) {
        Intent intent = new Intent(this, MainActivity.class);
        switch (itemID) {
            case R.id.navigation_headline:
                category = "Headline";
                intent.putExtra("category", category);
                break;
            case R.id.navigation_business:
                category = "Business";
                intent.putExtra("category", category);
                break;
            case R.id.navigation_entertainment:
                category = "Entertainment";
                intent.putExtra("category", category);
                break;
            case R.id.navigation_health:
                category = "Health";
                intent.putExtra("category", category);
                break;
            case R.id.navigation_science:
                category = "Science";
                intent.putExtra("category", category);
                break;
            case R.id.navigation_sports:
                category = "Sports";
                intent.putExtra("category", category);
                break;
            case R.id.navigation_technology:
                category = "Technology";
                intent.putExtra("category", category);
                break;
        }

        startActivity(intent);
        DrawerLayout drawerLayout = (DrawerLayout) findViewById(R.id.main_drawer_layout);
        drawerLayout.closeDrawer(GravityCompat.START);
    }

    @Override
    public boolean onNavigationItemSelected(@NonNull MenuItem item) {
        categorySelected(item.getItemId());
        return true;
    }

    @Override
    public String newsUrlFromCategory(String category) {
        String news_Url = "";
        if (category.equals("Headline")) {
            news_Url = "https://newsapi.org/v2/top-headlines?country=id&apiKey=6a4f7c3b694b4b9b97a22238dbc2a370";
        } else if (category.equals("Business")) {
            news_Url = "https://newsapi.org/v2/top-headlines?country=id&category=business&apiKey=6a4f7c3b694b4b9b97a22238dbc2a370";
        } else if (category.equals("Entertainment")) {
            news_Url = "https://newsapi.org/v2/top-headlines?country=id&category=entertainment&apiKey=6a4f7c3b694b4b9b97a22238dbc2a370";
        } else if (category.equals("Health")) {
            news_Url = "https://newsapi.org/v2/top-headlines?country=id&category=health&apiKey=6a4f7c3b694b4b9b97a22238dbc2a370";
        } else if (category.equals("Science")) {
            news_Url = "https://newsapi.org/v2/top-headlines?country=id&category=science&apiKey=6a4f7c3b694b4b9b97a22238dbc2a370";
        } else if (category.equals("Sports")) {
            news_Url = "https://newsapi.org/v2/top-headlines?country=id&category=sports&apiKey=6a4f7c3b694b4b9b97a22238dbc2a370";
        } else if (category.equals("Technology")) {
            news_Url = "https://newsapi.org/v2/top-headlines?country=id&category=technology&apiKey=6a4f7c3b694b4b9b97a22238dbc2a370";
        }
        return news_Url;
    }

    public class GetNews extends AsyncTask<String, Void, String> {

        private MainActivity mainActivity;

        public GetNews(MainActivity mainActivity) {
            this.mainActivity = mainActivity;
        }

        @Override
        protected void onPreExecute() {
            super.onPreExecute();
            mainActivity.getProgressBar().setVisibility(View.VISIBLE);
        }

        @Override
        protected String doInBackground(String... urls) {
            String result = "";
            result = mainActivityPresenter.getData(newsUrl);
            return result;
        }

        @Override
        protected void onPostExecute(String result) {
            JSONObject jsonObject = null;
            try {
                jsonObject = new JSONObject(result);
                JSONArray articles = jsonObject.optJSONArray("articles");

                for (int i = 0; i < articles.length(); i++) {
                    JSONObject article = articles.optJSONObject(i);
                    news = new News();
                    String author = article.optString("author").toString();
                    String title = article.optString("title").toString();
                    String description = article.optString("description").toString();
                    String url = article.optString("url").toString();
                    String urlToImage = article.optString("urlToImage").toString();
                    String date = article.optString("publishedAt").toString();

                    if (author != "null") {
                        news.setAuthor(author);
                    } else {
                        news.setAuthor("Unknown");
                    }
                    if (title != "null") {
                        news.setTitle(title);
                    } else {
                        news.setTitle("No title");
                    }
                    if (description != "description") {
                        news.setDescription(description);
                    } else {
                        news.setDescription("");
                    }
                    if (urlToImage != "null") {
                        news.setUrlToImage(urlToImage);
                    } else {
                        news.setUrlToImage("@drawable/noimageavailable.png");
                    }

                    news.setUrl(url);

                    if (date != "null") {
                        String day = "";
                        String month = "";
                        String year = "";
                        for (int j = 0; j < date.length(); j++) {
                            if (j < 4) {
                                year = year + date.charAt(j);
                            } else if ((j > 4) && (j < 7)) {
                                month = month + date.charAt(j);
                            } else if ((j > 7) && (j<10)) {
                                day = day + date.charAt(j);
                            }
                        }
                        news.setDate(day + "-" + month + "-" + year);
                    } else {
                        news.setDate("");
                    }

                    listNews.add(news);
                    listViewAdapter = new ListViewAdapter(MainActivity.this, listNews);
                    listView.setAdapter(listViewAdapter);

                    listView.setOnItemClickListener(new AdapterView.OnItemClickListener() {
                        public void onItemClick(AdapterView<?> parent, View view,
                                                int position, long id) {
                            Intent i = new Intent(MainActivity.this, NewsDetailsActivity.class);
                            i.putExtra("url", listNews.get(position).getUrl());
                            startActivity(i, ActivityOptions.makeSceneTransitionAnimation(mainActivity).toBundle());
                        }
                    });
                }
            } catch (JSONException e) {
                e.printStackTrace();
            }
            mainActivity.getProgressBar().setVisibility(View.GONE);
        }
    }

    @Override
    public void onBackPressed() {
        Intent intent = new Intent(this, StartActivity.class);
        startActivity(intent);
    }
}

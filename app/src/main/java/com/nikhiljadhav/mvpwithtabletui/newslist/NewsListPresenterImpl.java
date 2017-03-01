package com.nikhiljadhav.mvpwithtabletui.newslist;

import android.os.Bundle;
import android.os.Handler;
import android.os.Looper;
import android.util.Log;

import com.nikhiljadhav.mvpwithtabletui.R;
import com.nikhiljadhav.mvpwithtabletui.models.NewsEntity;

import org.json.JSONArray;
import org.json.JSONException;
import org.json.JSONObject;

import java.util.ArrayList;
import java.util.List;

/**
 * Created by nikhil.jadhav on 12/1/17.
 */

/**
 * Presenter class for NewsList view. Handles fetching of news list from server, parsing them as well as showing them
 */
public class NewsListPresenterImpl implements NewsListPresenter, NewsAPIInteractor.OnNewsReadingFinishedListener{

    private NewsListView newsListView;
    private NewsAPIInteractor newsAPIInteractor;

    private static final String TAG = NewsListPresenterImpl.class.getSimpleName();
    private List<NewsEntity> newsItemList;
    private Handler handler = new Handler(Looper.getMainLooper());

    public NewsListPresenterImpl(NewsListView newsListView) {
        this.newsListView = newsListView;
        this.newsAPIInteractor= new NewsAPIInteractorImpl();
    }

    @Override
    public void loadList() {
//        if(newsListView!=null){
//            newsListView.showProgress();
//        }
        newsAPIInteractor.getNewsList(this);
    }

    @Override
    public void onResume() {
    }

    /**
     * Handles news list click action
     * @param position
     */
    @Override
    public void onItemClicked(int position) {
        Bundle extras = new Bundle();
        NewsEntity newsEntity = newsItemList.get(position);
        extras.putString("storyURL", newsEntity.getArticleUrl());
        extras.putString("title", newsEntity.getTitle());
        extras.putString("summary", newsEntity.getSummary());
        if(newsEntity.getMediaEntity()!=null && newsEntity.getMediaEntity().size()>0)
            extras.putString("imageURL", newsEntity.getMediaEntity().get(0).getUrl());
        else{
            extras.putString("imageURL", "");
        }

        newsListView.loadNewsDetail(extras);
    }

    @Override
    public void onDestroy() {

    }

    @Override
    public List<NewsEntity> getList() {
        return newsItemList;
    }

    @Override
    public void onError(Exception e) {
        newsListView.showMessage(R.string.msg_api_error, true);
    }

    /**
     * Callback used when API fetches data successfully
     * @param response
     */
    @Override
    public void onSuccess(final String response) {
        handler.postDelayed(new Runnable() {
            @Override
            public void run() {
                JSONObject jsonObject;
                newsItemList = new ArrayList<NewsEntity>();
                try {
                    jsonObject = new JSONObject(response);
                    JSONArray resultArray = jsonObject.getJSONArray("results");
                    for (int i = 0; i < resultArray.length(); i++) {
                        JSONObject newsObject = resultArray.getJSONObject(i);
                        NewsEntity newsEntity = new NewsEntity(newsObject);
                        newsItemList.add(newsEntity);
                    }
                    newsListView.initList(newsItemList);
                } catch (JSONException e) {
                    Log.e(TAG, "fail to parse json string");
                }

            }
        }, 0);
    }
}

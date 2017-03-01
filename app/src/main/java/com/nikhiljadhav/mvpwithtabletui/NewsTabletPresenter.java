/*
 * Copyright 2016, The Android Open Source Project
 *
 * Licensed under the Apache License, Version 2.0 (the "License");
 * you may not use this file except in compliance with the License.
 * You may obtain a copy of the License at
 *
 *      http://www.apache.org/licenses/LICENSE-2.0
 *
 * Unless required by applicable law or agreed to in writing, software
 * distributed under the License is distributed on an "AS IS" BASIS,
 * WITHOUT WARRANTIES OR CONDITIONS OF ANY KIND, either express or implied.
 * See the License for the specific language governing permissions and
 * limitations under the License.
 */

package com.nikhiljadhav.mvpwithtabletui;

import android.os.Bundle;

import com.nikhiljadhav.mvpwithtabletui.models.NewsEntity;
import com.nikhiljadhav.mvpwithtabletui.newsdetail.DetailPresenter;
import com.nikhiljadhav.mvpwithtabletui.newslist.NewsListPresenter;
import static com.google.gson.internal.$Gson$Preconditions.checkNotNull;

import java.util.List;

/**
 * Presenter for the tablet screen that can act as a NewsList Presenter and a News Detail Presenter.
 * Mostly passes control to respective presenter but manages functionality ot load the already viewing fragment with new data
 */
public class NewsTabletPresenter implements DetailPresenter, NewsListPresenter{

    private NewsListPresenter newsListPresenter;
    private DetailPresenter detailPresenter;

    public NewsTabletPresenter(
            NewsListPresenter tasksPresenter, DetailPresenter detailPresenter) {
        newsListPresenter = checkNotNull(tasksPresenter);
        this.detailPresenter = checkNotNull(detailPresenter);
    }

    @Override
    public void onFullStoryClicked(String url) {
        detailPresenter.onFullStoryClicked(url);
    }

    @Override
    public void loadUI(Bundle extras) {
        detailPresenter.loadUI(extras);
    }

    @Override
    public void loadList() {
        newsListPresenter.loadList();
    }

    @Override
    public void onResume() {
    }

    /**
     * handles List item click to load into already loaded fragment
     * @param position position of item in list
     */
    @Override
    public void onItemClicked(int position) {
        Bundle extras = new Bundle();
        NewsEntity newsEntity = newsListPresenter.getList().get(position);
        extras.putString("storyURL", newsEntity.getArticleUrl());
        extras.putString("title", newsEntity.getTitle());
        extras.putString("summary", newsEntity.getSummary());
        if(newsEntity.getMediaEntity()!=null && newsEntity.getMediaEntity().size()>0)
            extras.putString("imageURL", newsEntity.getMediaEntity().get(0).getUrl());
        else{
            extras.putString("imageURL", "");
        }
        detailPresenter.loadUI(extras);
    }

    @Override
    public void onDestroy() {

    }

    @Override
    public List<NewsEntity> getList() {
        return newsListPresenter.getList();
    }
}

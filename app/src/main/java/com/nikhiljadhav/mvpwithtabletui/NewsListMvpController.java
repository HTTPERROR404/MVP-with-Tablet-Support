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

import android.support.annotation.IdRes;
import android.support.annotation.NonNull;
import android.support.v4.app.Fragment;
import android.support.v4.app.FragmentActivity;
import android.support.v4.app.FragmentManager;

import com.nikhiljadhav.mvpwithtabletui.newsdetail.DetailFragment;
import com.nikhiljadhav.mvpwithtabletui.newsdetail.DetailPresenter;
import com.nikhiljadhav.mvpwithtabletui.newsdetail.DetailPreseterImpl;
import com.nikhiljadhav.mvpwithtabletui.newslist.NewsListFragment;
import com.nikhiljadhav.mvpwithtabletui.newslist.NewsListPresenter;
import com.nikhiljadhav.mvpwithtabletui.newslist.NewsListPresenterImpl;
import com.nikhiljadhav.mvpwithtabletui.util.ActivityUtils;

import static com.nikhiljadhav.mvpwithtabletui.util.ActivityUtils.isTablet;

/**
 * Class that creates fragments (MVP views) and makes the necessary connections between them.
 * Controller that decides if tablet or phone views are to be populated
 */
public class NewsListMvpController {

    private final FragmentActivity mFragmentActivity;

    private NewsTabletPresenter mNewsTabletPresenter;

    private NewsListPresenter newsListPresenter;
    private DetailPresenter mDetailPresenter;

    private NewsListMvpController(
            @NonNull FragmentActivity fragmentActivity) {
        mFragmentActivity = fragmentActivity;
//        mTaskId = taskId;
    }

    /**
     * Creates a controller for a task view for phones or tablets.
     * @param fragmentActivity the context activity
     * @return a NewsListMvpController
     */
    public static NewsListMvpController createTasksView(
            @NonNull FragmentActivity fragmentActivity/*, @Nullable String taskId*/) {
        NewsListMvpController tasksMvpController =
                new NewsListMvpController(fragmentActivity/*, taskId*/);
        tasksMvpController.initTasksView();
        return tasksMvpController;
    }

    /**
     * Depending on the flag value stored in values file, loads appropriate UI components
     */

    private void initTasksView() {
        if (isTablet(mFragmentActivity)) {
            createTabletElements();
        } else {
            createNewsListElements();
        }
    }

    private void createNewsListElements() {
        NewsListFragment tasksFragment = findOrCreateTasksFragment(R.id.contentFrame);
        newsListPresenter = createListPresenter(tasksFragment);
        tasksFragment.setPresenter(newsListPresenter);
    }


    /**
     * Loads Tablet views & assigns presentor and other INIT part
     */
    private void createTabletElements() {
        NewsListFragment newsListFragment = findOrCreateTasksFragment(R.id.contentFrame_list);
        newsListPresenter = createListPresenter(newsListFragment);
        DetailFragment detailFragment = findOrCreateTaskDetailFragmentForTablet();
        mDetailPresenter = createTaskDetailPresenter(detailFragment);
         // Fragments connect to their presenters through a tablet presenter:
        mNewsTabletPresenter = new NewsTabletPresenter(newsListPresenter, mDetailPresenter);
        newsListFragment.setPresenter(mNewsTabletPresenter);
        detailFragment.setPresenter(mNewsTabletPresenter);
//
    }

    @NonNull
    private DetailPresenter createTaskDetailPresenter(DetailFragment taskDetailFragment) {
        return new DetailPreseterImpl(taskDetailFragment);

    }

    private NewsListPresenter createListPresenter(NewsListFragment tasksFragment) {
        return new NewsListPresenterImpl(tasksFragment);

    }

    @NonNull
    private NewsListFragment findOrCreateTasksFragment(@IdRes int fragmentId) {
        NewsListFragment tasksFragment =
                (NewsListFragment) getFragmentById(fragmentId);
        if (tasksFragment == null) {
            // Create the fragment
            tasksFragment = NewsListFragment.newInstance();
            ActivityUtils.addFragmentToActivity(
                    getSupportFragmentManager(), tasksFragment, fragmentId);
        }
        return tasksFragment;
    }

    @NonNull
    private DetailFragment findOrCreateTaskDetailFragmentForTablet() {
        DetailFragment taskDetailFragment =
                (DetailFragment) getFragmentById(R.id.contentFrame_detail);
        if (taskDetailFragment == null) {
            // Create the fragment
            taskDetailFragment = DetailFragment.newInstance();
            ActivityUtils.addFragmentToActivity(
                    getSupportFragmentManager(), taskDetailFragment, R.id.contentFrame_detail);
        }
        return taskDetailFragment;
    }

    private Fragment getFragmentById(int contentFrame_detail) {
        return getSupportFragmentManager().findFragmentById(contentFrame_detail);
    }


    private FragmentManager getSupportFragmentManager() {
        return mFragmentActivity.getSupportFragmentManager();
    }
}

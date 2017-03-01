/*
 *
 *  * Copyright (C) 2014 Antonio Leiva Gordillo.
 *  *
 *  * Licensed under the Apache License, Version 2.0 (the "License");
 *  * you may not use this file except in compliance with the License.
 *  * You may obtain a copy of the License at
 *  *
 *  *      http://www.apache.org/licenses/LICENSE-2.0
 *  *
 *  * Unless required by applicable law or agreed to in writing, software
 *  * distributed under the License is distributed on an "AS IS" BASIS,
 *  * WITHOUT WARRANTIES OR CONDITIONS OF ANY KIND, either express or implied.
 *  * See the License for the specific language governing permissions and
 *  * limitations under the License.
 *
 */

package com.nikhiljadhav.mvpwithtabletui.newslist;

import android.os.Bundle;

import com.nikhiljadhav.mvpwithtabletui.BaseView;
import com.nikhiljadhav.mvpwithtabletui.models.NewsEntity;

import java.util.List;


public interface NewsListView extends BaseView {

    void showProgress();

    void hideProgress();

    void initList(List<NewsEntity> newsItemList);

    void showMessage(int message, boolean isExit);

    void loadNewsDetail(Bundle extras);
}

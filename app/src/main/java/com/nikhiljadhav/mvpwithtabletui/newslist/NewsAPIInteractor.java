package com.nikhiljadhav.mvpwithtabletui.newslist;

/**
 * Created by nikhil.jadhav on 12/1/17.
 */

public interface NewsAPIInteractor {


    interface OnNewsReadingFinishedListener {
        void onError(Exception e);

        void onSuccess(String response);
    }

    void getNewsList(OnNewsReadingFinishedListener listener);
}

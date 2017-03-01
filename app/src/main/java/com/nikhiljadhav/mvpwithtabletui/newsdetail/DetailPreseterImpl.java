package com.nikhiljadhav.mvpwithtabletui.newsdetail;

import android.content.Intent;
import android.net.Uri;
import android.os.Bundle;

/**
 * Created by nikhil.jadhav on 14/1/17.
 */

public class DetailPreseterImpl implements DetailPresenter {
    DetailView detailView;

    public DetailPreseterImpl (DetailView detailView) {
        this.detailView = detailView;
    }

    public void onFullStoryClicked( String storyURL) {
        Intent intent = new Intent(Intent.ACTION_VIEW);
        intent.setData(Uri.parse(storyURL));
        detailView.loadFullNews(intent);
    }

    @Override
    public void loadUI(Bundle extras) {
detailView.initView(extras);
    }


}

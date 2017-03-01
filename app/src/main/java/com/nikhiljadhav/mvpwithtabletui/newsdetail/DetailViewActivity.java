package com.nikhiljadhav.mvpwithtabletui.newsdetail;

import android.os.Bundle;
import android.support.v7.app.AppCompatActivity;

import com.nikhiljadhav.mvpwithtabletui.R;
import com.nikhiljadhav.mvpwithtabletui.util.ActivityUtils;


/**
 * News detail view
 */
public class DetailViewActivity extends AppCompatActivity {
    @Override
    protected void onCreate(Bundle savedInstanceState) {
        super.onCreate(savedInstanceState);
        setContentView(R.layout.activity_main);

        DetailFragment detailFragment = (DetailFragment) getSupportFragmentManager()
                .findFragmentById(R.id.contentFrame);

        if (detailFragment == null) {
            detailFragment = DetailFragment.newInstance();
            detailFragment.setPresenter(new DetailPreseterImpl(detailFragment));
            ActivityUtils.addFragmentToActivity(getSupportFragmentManager(),
                    detailFragment, R.id.contentFrame);
        }
    }
}

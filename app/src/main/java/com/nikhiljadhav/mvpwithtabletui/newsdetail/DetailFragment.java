package com.nikhiljadhav.mvpwithtabletui.newsdetail;

import android.content.Intent;
import android.os.Bundle;
import android.support.annotation.Nullable;
import android.support.v4.app.Fragment;
import android.view.LayoutInflater;
import android.view.View;
import android.view.ViewGroup;
import android.widget.Button;
import android.widget.ImageView;
import android.widget.TextView;

import com.bumptech.glide.Glide;
import com.bumptech.glide.load.engine.DiskCacheStrategy;
import com.bumptech.glide.request.RequestListener;
import com.bumptech.glide.request.target.Target;
import com.nikhiljadhav.mvpwithtabletui.R;


/**
 * Created by nikhil.jadhav on 14/1/17.
 */

/**
 * Fragment class that populates view for the Detail News screen
 */
public class DetailFragment extends Fragment implements DetailView {
    private String storyURL = "";
    private DetailPresenter detailPresenter;
    View v;

    public static DetailFragment newInstance() {
        return new DetailFragment();
    }

    @Override
    public void onCreate(@Nullable Bundle savedInstanceState) {
        super.onCreate(savedInstanceState);
        setRetainInstance(true);
    }

    @Nullable
    @Override
    public View onCreateView(LayoutInflater inflater, @Nullable ViewGroup container, @Nullable Bundle savedInstanceState) {

        v = inflater.inflate(R.layout.frg_news_detail, null);
        initView();
        return v;
    }

    @Override
    public void setPresenter(Object presenter) {
        detailPresenter = (DetailPresenter) presenter;
    }

    private void initView(){

        Bundle extras = getActivity().getIntent().getExtras();
        if(extras!=null)
        initView(extras);
    }


    /**
     * Loads thumbnail form URL
     * @param imageURL
     * @param imageView
     */
    public void loadImage(String imageURL, final ImageView imageView){
        Glide.with(this).load(imageURL).asBitmap().override(Target.SIZE_ORIGINAL, Target.SIZE_ORIGINAL).centerCrop().diskCacheStrategy(DiskCacheStrategy.RESULT).skipMemoryCache(false).listener(new RequestListener() {
            @Override
            public boolean onException(Exception e, Object model, Target target, boolean isFirstResource) {
//                    showThumbnailView(holder, levels, position);
                imageView.setImageResource(R.drawable.place_holder);
                return true;
            }

            @Override
            public boolean onResourceReady(Object resource, Object model, Target target, boolean isFromMemoryCache, boolean isFirstResource) {
                return false;
            }
        }).into(imageView);
    }

    @Override
    public void loadFullNews(Intent intent) {
        startActivity(intent);
    }


    /**
     * INITs view, sets values to it
     * @param extras
     */
    @Override
    public void initView(Bundle extras) {
        storyURL = extras.getString("storyURL");
        String title = extras.getString("title");
        String summary = extras.getString("summary");
        String imageURL = extras.getString("imageURL");

        TextView titleView = (TextView) v.findViewById(R.id.title);
        ImageView imageView = (ImageView) v.findViewById(R.id.news_image);
        TextView summaryView = (TextView) v.findViewById(R.id.summary_content);
        Button btnFullStory = (Button) v.findViewById(R.id.full_story_link);
        btnFullStory.setOnClickListener(new View.OnClickListener() {
            @Override
            public void onClick(View v) {
                detailPresenter.onFullStoryClicked(storyURL);
            }
        });

        titleView.setText(title);
        summaryView.setText(summary);
        loadImage(imageURL, imageView);
    }


}

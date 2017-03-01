package com.nikhiljadhav.mvpwithtabletui.newslist;

import android.content.Context;
import android.content.Intent;
import android.os.Bundle;
import android.support.annotation.Nullable;
import android.support.design.widget.CoordinatorLayout;
import android.support.design.widget.Snackbar;
import android.support.v4.app.Fragment;
import android.support.v7.widget.CardView;
import android.support.v7.widget.LinearLayoutManager;
import android.support.v7.widget.RecyclerView;
import android.view.LayoutInflater;
import android.view.View;
import android.view.ViewGroup;
import android.widget.ImageView;
import android.widget.ProgressBar;
import android.widget.TextView;

import com.bumptech.glide.Glide;
import com.bumptech.glide.load.engine.DiskCacheStrategy;
import com.bumptech.glide.request.RequestListener;
import com.bumptech.glide.request.target.Target;
import com.nikhiljadhav.mvpwithtabletui.R;
import com.nikhiljadhav.mvpwithtabletui.models.MediaEntity;
import com.nikhiljadhav.mvpwithtabletui.models.NewsEntity;
import com.nikhiljadhav.mvpwithtabletui.newsdetail.DetailViewActivity;

import java.util.ArrayList;
import java.util.List;

/**
 * Created by nikhil.jadhav on 14/1/17.
 */

public class NewsListFragment extends Fragment implements NewsListView {

    private static final String TAG = NewsListActivity.class.getSimpleName();
    private ProgressBar progressBar;
    private NewsListPresenter presenter;
    private CoordinatorLayout coordinatorLayout;
    private View v;

    public static NewsListFragment newInstance() {
        return new NewsListFragment();
    }

    @Override
    public void onCreate(@Nullable Bundle savedInstanceState) {
        super.onCreate(savedInstanceState);
        setRetainInstance(true);
    }

    @Nullable
    @Override
    public View onCreateView(LayoutInflater inflater, @Nullable ViewGroup container, @Nullable Bundle savedInstanceState) {
        v = inflater.inflate(R.layout.frg_news_list, null);
//        coordinatorLayout = (CoordinatorLayout) v.findViewById(R.id.coordinatorLayout2);
        progressBar = (ProgressBar) v.findViewById(R.id.progress);
        showProgress();
//        presenter = new NewsListPresenterImpl(this);
//        presenter.loadList();
        return v;
    }

    @Override
    public void showProgress() {
        progressBar.setVisibility(View.VISIBLE);
    }

    @Override
    public void hideProgress() {
        progressBar.setVisibility(View.GONE);
    }


    @Override
    public void initList(List<NewsEntity> newsItemList) {
        hideProgress();
        NewsListAdapter adapter = new NewsListAdapter(this.getContext(), newsItemList);
        RecyclerView mRecyclerView = (RecyclerView) v.findViewById(R.id.list);
        LinearLayoutManager levelLayoutManager = new LinearLayoutManager(this.getContext());
        mRecyclerView.setLayoutManager(levelLayoutManager);
        mRecyclerView.setAdapter(adapter);

    }

    @Override
    public void showMessage(final int message, final boolean isExit) {
        //TODO: show snackbar with finish activity control
        NewsListFragment.this.getActivity().runOnUiThread(new Runnable() {
            @Override
            public void run() {
                hideProgress();

                Snackbar snackbar = Snackbar
                        .make(v.findViewById(R.id.rlParent), getString(message), Snackbar.LENGTH_INDEFINITE)
                        .setAction(getString(R.string.btn_ok), new View.OnClickListener() {
                            @Override
                            public void onClick(View view) {
                                if(isExit){
                                    NewsListFragment.this.getActivity().finish();
                                }
                            }
                        });
                snackbar.show();
            }
        });

    }

    @Override
    public void loadNewsDetail(Bundle extras) {
        Intent intent = new Intent(this.getActivity(), DetailViewActivity.class);
        intent.putExtras(extras);
        startActivity(intent);
    }

    @Override
    public void setPresenter(Object presenter) {
        this.presenter = (NewsListPresenter) presenter;
        this.presenter.loadList();
    }

    class NewsListAdapter extends RecyclerView.Adapter<NewsListAdapter.ViewHolder> {
        Context context;
        ArrayList<NewsEntity> data;

        public NewsListAdapter(Context context, List<NewsEntity> levels) {
            this.context = context;
            data = new ArrayList<>();
            data.addAll(levels);
        }


        @Override
        public ViewHolder onCreateViewHolder(ViewGroup parent, int viewType) {
            View v = LayoutInflater.from(parent.getContext())
                    .inflate(R.layout.list_item_news, parent, false);
            ViewHolder vh = new ViewHolder(v);
            return vh;
        }

        @Override
        public void onBindViewHolder(final ViewHolder holder, final int position) {
            NewsEntity newsEntity = data.get(holder.getAdapterPosition());
            List<MediaEntity> mediaEntityList = newsEntity.getMediaEntity();
            String thumbnailUrl = "";
            holder.newsTitle.setText(newsEntity.getTitle());
            if (mediaEntityList != null && mediaEntityList.size() > 0) {
                thumbnailUrl = mediaEntityList.get(0).getUrl();
                Glide.with(context).load(thumbnailUrl).asBitmap().override(Target.SIZE_ORIGINAL, Target.SIZE_ORIGINAL).centerCrop().diskCacheStrategy(DiskCacheStrategy.RESULT).skipMemoryCache(false).listener(new RequestListener() {
                    @Override
                    public boolean onException(Exception e, Object model, Target target, boolean isFirstResource) {
//                    showThumbnailView(holder, levels, position);
//                    fresco:placeholderImage="@drawable/place_holder"
                        holder.imageView.setImageResource(R.drawable.place_holder);
                        return true;
                    }

                    @Override
                    public boolean onResourceReady(Object resource, Object model, Target target, boolean isFromMemoryCache, boolean isFirstResource) {
                        return false;
                    }
                }).into(holder.imageView);
            } else {
                holder.imageView.setImageResource(R.drawable.place_holder);
            }
            holder.cardView.setOnClickListener(new View.OnClickListener() {
                @Override
                public void onClick(View v) {
                    presenter.onItemClicked(position);
                }
            });
        }

        @Override
        public int getItemCount() {
            if (data != null) {
                return data.size();
            }
            return 0;
        }


        public class ViewHolder extends RecyclerView.ViewHolder {
            // each data item is just a string in this case
            CardView cardView;
            TextView newsTitle;
            ImageView imageView;

            public ViewHolder(View v) {
                super(v);
                cardView = (CardView) v.findViewById(R.id.card_view);
                newsTitle = (TextView) v.findViewById(R.id.news_title);
                imageView = (ImageView) v.findViewById(R.id.news_item_image);
            }
        }
    }
}

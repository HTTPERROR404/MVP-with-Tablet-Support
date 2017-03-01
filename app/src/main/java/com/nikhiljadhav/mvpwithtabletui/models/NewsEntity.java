package com.nikhiljadhav.mvpwithtabletui.models;

import android.util.Log;

import org.json.JSONArray;
import org.json.JSONException;
import org.json.JSONObject;
import org.json.JSONTokener;

import java.util.ArrayList;
import java.util.List;

/**
 * This represents a news item
 */
public class NewsEntity {
    private static final String TAG = NewsEntity.class.getSimpleName();
    private String title;
    private String summary;
    private String articleUrl;
    private String byline;
    private String publishedDate;
    private List<MediaEntity> mediaEntityList;

    public void setTitle(String title) {
        this.title = title;
    }

    public void setSummary(String summary) {
        this.summary = summary;
    }

    public void setArticleUrl(String articleUrl) {
        this.articleUrl = articleUrl;
    }

    public void setByline(String byline) {
        this.byline = byline;
    }

    public void setPublishedDate(String publishedDate) {
        this.publishedDate = publishedDate;
    }

    public void setMediaEntityList(List<MediaEntity> mediaEntityList) {
        this.mediaEntityList = mediaEntityList;
    }

    public NewsEntity(JSONObject jsonObject) {
        try {
            mediaEntityList = new ArrayList<>();
            title = jsonObject.getString("title");
            summary = jsonObject.getString("abstract");
            articleUrl = jsonObject.getString("url");
            byline = jsonObject.getString("byline");
            publishedDate = jsonObject.getString("published_date");
            JSONArray mediaArray = null;
            try {
                mediaArray = jsonObject.getJSONArray("multimedia");
            }
            catch (Exception e){
            }
            if(mediaArray!=null)
            for (int i = 0;i < mediaArray.length(); i++) {
                JSONObject mediaObject = mediaArray.getJSONObject(i);
                MediaEntity mediaEntity = new MediaEntity(mediaObject);

                mediaEntityList.add(mediaEntity);
            }

        } catch (JSONException exception) {
            Log.e(TAG, exception.getMessage());
        }
    }

    public NewsEntity(){

    }

    public static JSONArray objectToJSONArray(Object object){
        Object json = null;
        JSONArray jsonArray = null;
        try {
            json = new JSONTokener(object.toString()).nextValue();
        } catch (JSONException e) {
            e.printStackTrace();
        }
        if (json instanceof JSONArray) {
            jsonArray = (JSONArray) json;
        }
        return jsonArray;
    }

    public String getTitle() {
        return title;
    }

    public String getSummary() {
        return summary;
    }

    public String getArticleUrl() {
        return articleUrl;
    }

    public String getByline() {
        return byline;
    }

    public String getPublishedDate() {
        return publishedDate;
    }

    public List<MediaEntity> getMediaEntity() {
        return mediaEntityList;
    }
}

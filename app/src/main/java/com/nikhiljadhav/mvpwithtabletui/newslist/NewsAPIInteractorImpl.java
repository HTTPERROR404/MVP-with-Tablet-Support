package com.nikhiljadhav.mvpwithtabletui.newslist;

import java.io.BufferedReader;
import java.io.IOException;
import java.io.InputStream;
import java.io.InputStreamReader;
import java.net.HttpURLConnection;
import java.net.URL;

/**
 * Created by nikhil.jadhav on 12/1/17.
 */

public class NewsAPIInteractorImpl implements NewsAPIInteractor {

    private static final String URL = "http://www.mocky.io/v2/573c89f31100004a1daa8adb";


    /**
     * API that fetches NewsList JSON from the URL
     * @param listener
     */
    @Override
    public void getNewsList(final OnNewsReadingFinishedListener listener) {
        new Thread(new Runnable() {
            @Override
            public void run() {
                try {
                    java.net.URL url = new URL(URL);
                    HttpURLConnection con = (HttpURLConnection) url.openConnection();
                    String readStream = readStream(con.getInputStream());
                    listener.onSuccess(readStream);
                } catch (Exception e) {
                    listener.onError(e);
                    e.printStackTrace();
                }
            }
        }).start();
    }

    /**
     *
     * @param in Reads the inputStream contents into String
     * @return
     */
    private static String readStream(InputStream in) {
        StringBuilder sb = new StringBuilder();
        try (BufferedReader reader = new BufferedReader(new InputStreamReader(in));) {

            String nextLine = "";
            while ((nextLine = reader.readLine()) != null) {
                sb.append(nextLine);
            }
        } catch (IOException e) {
            e.printStackTrace();
        }
        return sb.toString();
    }
}

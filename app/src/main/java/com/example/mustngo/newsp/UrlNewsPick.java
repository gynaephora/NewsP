package com.example.mustngo.newsp;

import android.util.Log;

import org.jsoup.Jsoup;
import org.jsoup.select.Elements;

import java.io.IOException;

/**
 * Created by kalmaro on 08.06.2016.
 */
public class UrlNewsPick implements jsoupParse {
    private String mUrlNewsPick="null";
    @Override
    public String parse(String mUrlNews) {
        org.jsoup.nodes.Document doc = null;
        try {
            doc = Jsoup.connect(mUrlNews).get();
        } catch (IOException e) {
            e.printStackTrace();
        }
        if (doc != null) {
            try {


                //get news pick url
                try {
                    Elements newsPickUrlBox = doc.select("meta[itemprop=image");
                    mUrlNewsPick = newsPickUrlBox.attr("content");
                    //show all news information in logs form
                    Log.i("News_pick_URL", mUrlNewsPick);
                }catch(Exception e){
                    mUrlNewsPick="null";
                    Log.i("News_pick_URL", mUrlNewsPick);
                }

            } catch (Exception e) {
                //newsPickUrl="null";

                Log.i("News_page_out", mUrlNewsPick);
            }}

        return mUrlNewsPick;
    }
}

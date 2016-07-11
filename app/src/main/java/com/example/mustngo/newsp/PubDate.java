package com.example.mustngo.newsp;

import android.util.Log;

import org.jsoup.Jsoup;
import org.jsoup.nodes.Element;
import org.jsoup.select.Elements;

import java.io.IOException;

/**
 * Created by mustango on 08.06.2016.
 */
public class PubDate implements jsoupParse {

    private String mPubDate="null";
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
                Elements newsBox = doc.select("div.print_container");
               // String pubDateString = "null";
                //get publication date of news
                try {
                    Element pubDateElement = newsBox.select("span").first();
                    mPubDate = pubDateElement.text();
                } catch (Exception e) {
                    mPubDate = "null";
                }
                //show all news information in logs form
                Log.i("Pub_date", mPubDate);
            } catch (Exception e) {
                Log.i("News_page_out", "null");

            }}
            return mPubDate;
        }
}

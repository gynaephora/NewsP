package com.example.mustngo.newsp;

import android.util.Log;

import org.jsoup.Jsoup;
import org.jsoup.nodes.Element;
import org.jsoup.select.Elements;

import java.io.IOException;

/**
 * Created by mustango on 08.06.2016.
 */
public class Newsmaker implements jsoupParse {
   // private String mUrlNews;
    private String mNewsmaker;
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
                Element NewsmakerElement=null;

                try {
                    NewsmakerElement = newsBox.select("span.author").get(0).select("a").first();
                    mNewsmaker = NewsmakerElement.text();
                }catch (Exception e){
                    mNewsmaker="null";
                }

                //show all news information in logs form
                Log.i("Newsmaker",  mNewsmaker);
            } catch (Exception e) {
                Log.i("News_page_out", "null");

            }}
        return mNewsmaker;
    }
}

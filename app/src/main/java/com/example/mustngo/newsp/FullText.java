package com.example.mustngo.newsp;

import android.util.Log;

import org.jsoup.Jsoup;
import org.jsoup.nodes.Element;
import org.jsoup.select.Elements;

import java.io.IOException;

/**
 * Created by Volodymyr Korzhovsky on 08.06.2016.
 */
public class FullText implements jsoupParse {
    private String mUrlNews;
    private String mFullText;
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

                try {
                    mFullText = newsBox.select("p").text();
                }catch(Exception e){
                    mFullText="null";
                }
                //show all news information in logs form
                Log.i("Full_text_news", mFullText);
            } catch (Exception e) {
                Log.i("News_page_out", "null");

            }}
        return mFullText;
    }
}

package com.example.mustngo.newsp;

import android.util.Log;

import org.jsoup.Jsoup;
import org.jsoup.nodes.Element;
import org.jsoup.select.Elements;

import java.io.IOException;

/**
 * Created by kalmaro on 08.06.2016.
 */
public class Author implements jsoupParse {
    private String mUrlNews;
    private String mAuthor;
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

                //get publication date of news
                try {
                    Element AuthorElement = newsBox.select("span.author").get(1).select("a").first();
                    mAuthor=AuthorElement.text();
                }catch(Exception e){
                    mAuthor="null";
                }
                //show all news information in logs form
                Log.i("Author", mAuthor);
            } catch (Exception e) {
                Log.i("News_page_out", "null");

            }}


        return mAuthor;
    }
}

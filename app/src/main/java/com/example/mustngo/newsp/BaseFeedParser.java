package com.example.mustngo.newsp;

import java.io.IOException;
import java.io.InputStream;
import java.net.MalformedURLException;
import java.net.URL;

/**
 * Created by yu-sat on 28.01.2016.
 */
public abstract class BaseFeedParser implements FeedParser{

    //Имена тегов
  //  static final String PUB_DATE="pubDate";
    static final String DESCRIPTION="description";
    static final String LINK="link";
    static final String TITLE="title";
    static final String ITEM="item";

    final URL feedUrl;

    protected BaseFeedParser(String feedUrl){
        try{
            this.feedUrl=new URL(feedUrl);
        }catch (MalformedURLException e){
            throw new RuntimeException(e);
        }
    }

    protected InputStream getInputStream(){
        try{
            return feedUrl.openConnection().getInputStream();
        }catch (IOException e){
            throw new RuntimeException(e);
        }

    }

}

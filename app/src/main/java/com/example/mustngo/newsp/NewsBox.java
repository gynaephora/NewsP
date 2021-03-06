package com.example.mustngo.newsp;

import android.content.Context;
import android.util.Log;

import java.util.ArrayList;
import java.util.UUID;

/**
 * Created by Volodymyr Korzhovsky on 19.01.2016.
 */

//central repository for objects News
public class NewsBox {

    private static final String TAG="NewsBox";
    private static final String FILENAME="news.json";


    private  ArrayList<News> mNews;
    private  NewsIntentJSONSerializer mSerializer;


    private static NewsBox sNewsBox;
    private Context mAppContext;

    private NewsBox(Context appContext){

        mAppContext=appContext;
        mSerializer=new NewsIntentJSONSerializer(mAppContext,FILENAME);
        try{
              mNews=mSerializer.loadNews();
              Log.d(TAG,"news load succsesfully");
        }catch(Exception e){
            mNews=new ArrayList<News>();
            Log.e(TAG,"Error loading news:",e);
        }
    }

    public  void addNews(News c){
        mNews.add(c);
        saveNews();
    }
    public void addNewsBox(ArrayList<News> news){
        mNews=news;

    }


    public  boolean saveNews(){
        try{
            mSerializer.saveNews(mNews);
            Log.d(TAG,"news saved to file");
            return true;
        }catch (Exception e){
            Log.e(TAG,"Error saving news:",e);
            return false;
        }
    }


    public static NewsBox get(Context c){
        /*
         To ensure that the singlet for the class will be available
         Context object with a long life, we call getApplicationContext ()
         and substitute the object passed in Context application context.
        */

        if (sNewsBox==null){

        try {
            sNewsBox = new NewsBox(c.getApplicationContext());
        }catch(Exception e){}

        }
        return sNewsBox;
    }


    public ArrayList<News> getNews(){
        return mNews;
    }

    public News getNewsm(UUID id){
        for(News c:mNews){
            if (c.getId().equals(id))
                return c;
            }
        return null;
    }
}

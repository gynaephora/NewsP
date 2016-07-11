package com.example.mustngo.newsp;

import android.content.Context;
import android.util.Log;

import java.util.ArrayList;
import java.util.UUID;

/**
 * Created by yu-sat on 19.01.2016.
 */
//централизованное хранилище для обьектов News
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
          //  mNews=mSerializer.saveNews();
            Log.d(TAG,"news load succsesfully");
        }catch(Exception e){
            mNews=new ArrayList<News>();
            Log.e(TAG,"Error loading news:",e);
        }

       /*   mAppContext=appContext;
        mNews = new ArrayList<News>();
*/
        //пустышка из 20 обьектов списка,
        //убрать после реализации парсинга из сети
      /*  for (int i=0;i<20;i++){
        News c=new News();
           // c.setTitle();
            Log.i("News", "Получили: " + c.getTitle());

          //  c.setTitle("Новости");
          //  c.setDiscription("Описание новости");
        }*/
    }

    public  void addNews(News c){
        mNews.add(c);
        saveNews();
    }
    public void addNewsBox(ArrayList<News> news){
        mNews=news;
       // saveNews();
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
        Чтобы гарантировать, что синглетному классу для работы будет доступен
        объект Context с долгим сроком жизни, мы вызываем getApplicationContext()
        и подменяем                                                                                                                                                                                              переданный объект Context контекстом приложения.                                                                               
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

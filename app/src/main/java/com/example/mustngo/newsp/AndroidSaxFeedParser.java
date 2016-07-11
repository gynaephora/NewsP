package com.example.mustngo.newsp;


import android.sax.Element;
import android.sax.EndElementListener;
import android.sax.EndTextElementListener;
import android.sax.RootElement;
import android.util.Log;
import android.util.Xml;

import java.util.ArrayList;

/**
 * Created by yu-sat on 28.01.2016.
 */
public class AndroidSaxFeedParser extends BaseFeedParser{
   // private final String URL_STRING="http://podrobnosti.ua/rss/feed/?category=all-news";
    public AndroidSaxFeedParser(String feedUrl){
          super(feedUrl);
    }

    @Override
    public ArrayList<News> parse() {
        final  News currentMessage=new News();
        RootElement root=new RootElement("rss");
        final ArrayList<News> messages=new ArrayList<News>();
        Element channel=root.getChild("channel");
        Element item=channel.getChild(ITEM);
      /*  item.setEndElementListener(new EndElementListener() {
            public void end(){
                messages.add(currentMessage.compareTo());
            }
        });*/


        item.getChild(TITLE).setEndTextElementListener(new EndTextElementListener() {
            public void end(String body) {
                //  messages.add(currentMessage);
                currentMessage.setTitle(body);

                Log.i("News", "Received xml: " + body);
            }
        });
     /*   item.getChild(LINK).setEndTextElementListener(new EndTextElementListener(){
            public void end(String body){
                currentMessage.setLink(body);
            }
        });
        item.getChild(DESCRIPTION).setEndTextElementListener(new EndTextElementListener(){
            public void end(String body){
                currentMessage.setDescription(body);
            }
        });
       /* item.getChild(PUB_DATE).setEndTextElementListener(new EndTextElementListener(){
            public void end(String body){
                currentMessage.setDate(body);
            }
        });*/

        try{
            Xml.parse(this.getInputStream(),Xml.Encoding.UTF_8,root.getContentHandler());
        }catch (Exception e){
            throw new RuntimeException(e);
        }
      /*  for(ArrayList<News> n:messages){
            n.add(currentMessage);
        }*/

        return messages;
    }
}

package com.example.mustngo.newsp;

import android.util.Log;
import org.jsoup.Jsoup;
import org.jsoup.nodes.Element;
import org.jsoup.select.Elements;
import org.w3c.dom.Document;
import org.w3c.dom.Node;
import org.w3c.dom.NodeList;
import java.io.IOException;
import java.util.ArrayList;
import javax.xml.parsers.DocumentBuilder;
import javax.xml.parsers.DocumentBuilderFactory;

/**
 * Created by yu-sat on 29.01.2016.
 */
public class DomFeedParser extends BaseFeedParser {


    protected DomFeedParser(String feedUrl) {
        super(feedUrl);
    }



    public ArrayList<News> parse() {
       DocumentBuilderFactory factory = DocumentBuilderFactory.newInstance();
       ArrayList<News> messages = new ArrayList<News>();


        try {
            DocumentBuilder builder = factory.newDocumentBuilder();
            Document dom = builder.parse(this.getInputStream());
            org.w3c.dom.Element root = dom.getDocumentElement();
            NodeList items = root.getElementsByTagName(ITEM);
            for (int i=0;i<items.getLength();i++){
                News message = new News();

                Node item = items.item(i);
                NodeList properties = item.getChildNodes();
                String pd="null";
                String unp="null";
                String nm="null";
                PubDate pubDateParse=new PubDate();
                Newsmaker newsmakerParse = new Newsmaker();
         /*       PubDate pubDateParse=new PubDate();
                Newsmaker newsmakerParse = new Newsmaker();
                FullText fullTextParse = new FullText();
                Author AuthorParse = new Author();
                UrlNewsPick UrlNewsPickParse = new UrlNewsPick();*/

                for (int j=0;j<properties.getLength();j++){
                    Node property = properties.item(j);
                    String name = property.getNodeName();
                    if (name.equalsIgnoreCase(TITLE)){
                        message.setTitle(property.getFirstChild().getNodeValue());
                    } else if (name.equalsIgnoreCase(LINK)){
                        String link=property.getFirstChild().getNodeValue();
                        message.setLink(link);
                        Log.i("Link", link);

                        pd=pubDateParse.parse(link);
                        message.setPubDate(pd);

                        nm = newsmakerParse.parse(link);
                        message.setNewsmaker(nm);




                            Author AuthorParse = new Author();
                            String au = AuthorParse.parse(link);
                            message.setAuthor(au);

                            FullText fullTextParse = new FullText();
                            String ft = fullTextParse.parse(link);
                            message.setFullText(ft);

                            UrlNewsPick UrlNewsPickParse = new UrlNewsPick();
                            unp = UrlNewsPickParse.parse(link);
                            message.setUrlPick(unp);

                    } else if (name.equalsIgnoreCase(DESCRIPTION)){
                        StringBuilder text = new StringBuilder();
                        NodeList chars = property.getChildNodes();
                        for (int k=0;k<chars.getLength();k++){
                            text.append(chars.item(k).getNodeValue());
                        }
                        message.setDescription(text.toString());
                    } /*else if (name.equalsIgnoreCase(PUB_DATE)){
                        message.setDate(property.getFirstChild().getNodeValue());
                    }*/
                }
                   if(pd!="null" && nm!="null") {
                        messages.add(message);

                    /*   NewsBox.get(getContext()).saveNews();
                        NewsBox.get(getActivity()).addNews(message);
                    */}


            }
        } catch (Exception e) {
            throw new RuntimeException(e);
        }
        return messages;
    }


/*
    public void jsoupParse(String mUrl){
        String author;
        org.jsoup.nodes.Document doc=null;
        String url_pick="null";
      try{
            doc= Jsoup.connect(mUrl).get();
          try {
              Element newsUrl = doc.select("img[src$=.jpg]").get(0);
              url_pick = "http://podrobnosti.ua/" + newsUrl.attr("src");
          }catch(Exception e){
              url_pick="null";
          }
        }catch (IOException e){
            e.printStackTrace();
        }

        if(doc!=null) {
         try{
                 Elements newsBox=doc.select("div.print_container");
                 //Elements newsPickUrlBox=doc.select("meta[itemprop=image]");
                 Elements newsPickUrlBox=doc.select("meta[itemprop=image");
                 String newsPickUrl="null";
                 String pubDateString="null";
                 String NewsmakerString="null";
                 String AuthorString="null";

                 String textOfNews="null";

             //get news url

             //get news pick url

             try{
                 newsPickUrl=newsPickUrlBox.attr("content");
             }catch(Exception e){
                 newsPickUrl="null";
             }
             //get publication date of news
             try{
                 Element pubDateElement=newsBox.select("span").first();
                 pubDateString=pubDateElement.text();
             }catch(Exception e){
                 pubDateString="null";
             }
             //get news maker
             try {
                 Element NewsmakerElement = newsBox.select("span.author").get(0).select("a").first();
                 NewsmakerString = NewsmakerElement.text();
             }catch (Exception e){
                 NewsmakerString="null";
             }

            //get news author
            try {
                Element AuthorElement = newsBox.select("span.author").get(1).select("a").first();
                AuthorString=AuthorElement.text();
            }catch(Exception e){
                AuthorString="null";
                }
            //get news text
            try {
                textOfNews = newsBox.select("p").text();
            }catch(Exception e){
                textOfNews="null";
            }
            //show all news information in logs form
           // Log.i("Pick_Url", newsPickUrlBox.toString());
            Log.i("Pick_Url_short", newsPickUrl);
            Log.i("Pub_date", pubDateString);
            Log.i("Newsmaker", NewsmakerString);
            Log.i("Author", AuthorString);
            Log.i("Url_of_news_picture", url_pick);
            Log.i("Text_of_news", textOfNews);

            }catch (Exception e){
                Log.i("Author", "null");

            }
        }
        else {author="Error";
            Log.i("Author", author);}

    }*/

}
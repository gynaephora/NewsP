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
 * Created by Volodymyr Korzhovsky on 29.01.2016.
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
                        Log.i("NM", nm);




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
                    }
                }
                   if(pd!="null" && nm!="null") {
                       messages.add(message);
                  }

            }
        } catch (Exception e) {
            throw new RuntimeException(e);
        }
        return messages;
    }

}
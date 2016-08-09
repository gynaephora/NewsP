package com.example.mustngo.newsp;

import org.json.JSONException;
import org.json.JSONObject;

import java.util.UUID;

/**
 * Created by Volodymyr Korzhovsky on 16.01.2016.
 */

public class News {

    private static final String JSON_ID="id";
    private static final String JSON_TITLE="title";
    private static final String JSON_DESCRIPTION="description";
    private static final String JSON_PUB_DATE="pubdate";
    private static final String JSON_NEWSMAKER="newsmaker";
    private static final String JSON_AUTHOR="author";
    private static final String JSON_FULL_TEXT="fulltext";
    private static final String JSON_URL_PICK="urlpick";
    private static final String JSON_LINK="link";


    private UUID mId;
    private String mTitle;
    private String mDescription;
    private String mUrlNews;
    private String mPubDate;
    private String mNewsmaker;
    private String mAuthor;
    private String mFullText;
    private String mUrlPick;
    private String mLink;

    public String getLink() {
        return mLink;
    }

    public void setLink(String mlink) {
        mLink = mlink;
    }

   public JSONObject toJSON() throws JSONException{
       JSONObject json=new JSONObject();
       json.put(JSON_ID,mId.toString());
       json.put(JSON_TITLE,mTitle);
       json.put(JSON_DESCRIPTION,mDescription);
       json.put(JSON_PUB_DATE,mPubDate);
       json.put(JSON_NEWSMAKER,mNewsmaker);
       json.put(JSON_AUTHOR,mAuthor);
       json.put(JSON_FULL_TEXT,mFullText);
       json.put(JSON_URL_PICK,mUrlPick);
       json.put(JSON_LINK,mLink);
       return json;
   }

    public News() {
        //generating a unique identifier
        mId = UUID.randomUUID();
    }

    public News(JSONObject json) throws JSONException{
        mId=UUID.fromString(json.getString(JSON_ID));
        mTitle=json.getString(JSON_TITLE);
        mDescription=json.getString(JSON_DESCRIPTION);
        mPubDate=json.getString(JSON_PUB_DATE);
        mNewsmaker=json.getString(JSON_NEWSMAKER);
        mAuthor=json.getString(JSON_AUTHOR);
        mFullText=json.getString(JSON_FULL_TEXT);
        mUrlPick=json.getString(JSON_URL_PICK);
        mLink=json.getString(JSON_LINK);
    }
    @Override
    public String toString(){
        return mTitle;
    }


    public void setId(UUID id) {
        mId = id;
    }

    public UUID getId(){
        return mId;
    }
    public String getTitle(){
        return mTitle;
    }
    public String getDescription() {
        return mDescription;
    }
    public String getNewsmaker() {
        return mNewsmaker;
    }
    public String getAuthor() {
        return mAuthor;
    }
    public String getPubDate() {
        return mPubDate;
    }
    public String getFullText() {
        return mFullText;
    }
    public String getUrlPick() {
        return mUrlPick;
    }

    public void setDescription(String description) {
        mDescription = description;
    }
    public void setAuthor(String author) {
        mAuthor = author;
    }
    public void setTitle(String title){
        mTitle=title;
    }
    public void setFullText(String fullText) {
        mFullText = fullText;
    }
    public void setNewsmaker(String newsmaker) {
        mNewsmaker = newsmaker;
    }
    public void setUrlPick(String urlPick) {
        mUrlPick = urlPick;
    }
    public void setPubDate(String pubDate) {
        mPubDate = pubDate;
    }
}
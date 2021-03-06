package com.example.mustngo.newsp;

import java.net.MalformedURLException;
import java.net.URL;
import java.text.ParseException;
import java.text.SimpleDateFormat;
import java.util.Date;
import java.util.Objects;

/**
 * Created by Volodymyr Korzhovsky on 28.01.2016.
 */
public class Message implements Comparable<Message> {
    static SimpleDateFormat FORMATTER = new SimpleDateFormat("EEE,dd yyyy HH:mm:ss z");

    private String title;
    private URL link;
    private String description;
    private Date date;

    public String getTitle() {
        return title;
    }

    public void setTitle(String title) {
        this.title = title;
    }

    public URL getLink() {
        return link;
    }

    public void setLink(String link) {
        try{
            this.link = new URL(link);
        }catch(MalformedURLException e){
            throw new RuntimeException(e);
        }

    }

    public String getDescription() {
        return description;
    }

    public void setDescription(String description) {
        this.description = description;
    }

    public String getDate() {
        return FORMATTER.format(this.date);
    }

    public void setDate(String date) {
        //if necessary, the full date format
        while (!date.endsWith("00")){
            date+="0";
        }
        try{
            this.date = FORMATTER.parse(date.trim());
        }catch (ParseException e){
            throw new RuntimeException(e);
        }

    }

    public int compareTo(Message another){
        if(another == null) return 1;
        //sorted in descending order, the most recent entries are displayed on top
        return another.date.compareTo(date);
    }

}

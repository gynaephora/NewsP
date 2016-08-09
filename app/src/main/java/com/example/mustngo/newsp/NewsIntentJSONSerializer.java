package com.example.mustngo.newsp;

import android.content.Context;

import org.json.JSONArray;
import org.json.JSONException;
import org.json.JSONTokener;

import java.io.BufferedReader;
import java.io.FileNotFoundException;
import java.io.IOException;
import java.io.InputStream;
import java.io.InputStreamReader;
import java.io.OutputStream;
import java.io.OutputStreamWriter;
import java.io.Writer;
import java.util.ArrayList;

/**
 * Created by Volodymyr Korzhovsky on 13.06.2016.
 */
public class NewsIntentJSONSerializer {
    private Context mContext;
    private String mFilename;

    public NewsIntentJSONSerializer(Context c, String f){
        mContext=c;
        mFilename=f;
    }

    public void saveNews(ArrayList<News> news) throws JSONException,IOException{

        //build array data in JSON
        JSONArray array=new JSONArray();
        for(News c:news){
            array.put(c.toJSON());
        }

        //write file to disk
        Writer writer=null;
        try{
            OutputStream out=mContext.openFileOutput(mFilename,Context.MODE_PRIVATE);
            writer=new OutputStreamWriter(out);
            writer.write(array.toString());
        }finally {
            if(writer!=null) writer.close();
        }
    }

    public ArrayList<News> loadNews() throws IOException, JSONException{
        ArrayList<News> news=new ArrayList<News>();
        BufferedReader reader=null;
        try{
            //opening and reading file of StringBuilder
            InputStream in=mContext.openFileInput(mFilename);
            reader = new BufferedReader(new InputStreamReader(in));
            StringBuilder jsonString=new StringBuilder();
            String line =null;
            while((line=reader.readLine())!=null){
                jsonString.append(line);
            }
            //defrag JSON whithout JSONTokener
            JSONArray array=(JSONArray) new JSONTokener(jsonString.toString()).nextValue();
            //build array of Newses object from JSONObject data
            for(int i=0;i<array.length();i++){
                news.add(new News(array.getJSONObject(i)));
            }


        }catch (FileNotFoundException e){}
        finally{
            if(reader!=null)
                reader.close();
        }
        return news;
    }

}

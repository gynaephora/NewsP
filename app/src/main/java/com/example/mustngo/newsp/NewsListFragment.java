package com.example.mustngo.newsp;

import android.app.Dialog;
import android.content.Context;
import android.content.DialogInterface;
import android.content.Intent;
import android.content.pm.ActivityInfo;
import android.graphics.drawable.Drawable;
import android.graphics.drawable.Icon;
import android.net.ConnectivityManager;
import android.net.NetworkInfo;
import android.net.Uri;
import android.os.AsyncTask;
import android.os.Bundle;
import android.support.v4.app.Fragment;
import android.support.v4.app.FragmentManager;
import android.support.v4.app.FragmentTransaction;
import android.support.v4.app.ListFragment;
import android.support.v7.app.AlertDialog;
import android.util.Log;
import android.view.LayoutInflater;
import android.view.Menu;
import android.view.MenuInflater;
import android.view.MenuItem;
import android.view.View;
import android.view.ViewGroup;
import android.view.animation.Animation;
import android.view.animation.AnimationUtils;
import android.view.animation.RotateAnimation;
import android.widget.ArrayAdapter;
import android.widget.ImageView;
import android.widget.ListView;
import android.widget.ProgressBar;
import android.widget.TextView;
import android.widget.Toast;


import com.squareup.picasso.Picasso;

import org.json.JSONException;

import java.io.BufferedWriter;
import java.io.File;
import java.io.FileNotFoundException;
import java.io.FileOutputStream;
import java.io.FileWriter;
import java.io.IOException;
import java.io.OutputStreamWriter;
import java.net.URI;
import java.util.ArrayList;
import java.util.concurrent.Executor;
import java.util.concurrent.ThreadPoolExecutor;
import java.util.concurrent.TimeUnit;

/**
 * Created by Volodymyr Korzhovsky on 19.01.2016.
 */
public class NewsListFragment extends ListFragment {

    final static String ARG_NEWS_NUMBER="number";
    int mCurrentPosition = 0;

    private Boolean IfNewsRefreshDone=false;
    private String[] mNewsTheme;
    private String[] mNewsItem;
    private Menu refreshItem;
    //flag, false -when internet connection true; true - when it happend off internet
    private Boolean intNoYes=true;


    final String LOG_TAG = "myLogs";
    final String FILENAME = "file";

    private ArrayList<News> mNews;
    private String mNiwsItemLink="http://podrobnosti.ua/rss/feed/?category=all-news";
    private static final String TAG = "NewsListFragment";
    PodrobnostiItemTask podrobnostiItemTask=null;

    @Override
    public void onCreate(Bundle savedInstanceState) {
        super.onCreate(savedInstanceState);

        setHasOptionsMenu(true);
        setMenuVisibility(true);

        setRetainInstance(true);
        //enable only portrait orientation
        getActivity().setRequestedOrientation(ActivityInfo.SCREEN_ORIENTATION_PORTRAIT);
        mNewsTheme = getResources().getStringArray(R.array.news_url);
        mNewsItem = getResources().getStringArray(R.array.news_theme);

        Bundle args = getArguments();

        if (args != null) {
            // Set article based on argument passed in
            mCurrentPosition=args.getInt(ARG_NEWS_NUMBER);

            try {
                // severing stream for writing
                BufferedWriter bw = new BufferedWriter(new OutputStreamWriter(
                        getActivity().openFileOutput(FILENAME, Context.MODE_PRIVATE)));
                // write data
                bw.write(mNewsItem[mCurrentPosition]);
                // close strean
                bw.close();
                Log.d(LOG_TAG, "File write. Title:"+mNewsItem[mCurrentPosition]);
            } catch (FileNotFoundException e) {
                e.printStackTrace();
            } catch (IOException e) {
                e.printStackTrace();
            }

          if(mCurrentPosition!=11){

              Log.d("news_theme_link",mNewsTheme[args.getInt(ARG_NEWS_NUMBER)]);
              startLoadNews(mNewsTheme[mCurrentPosition]);

          }
        } else if (mCurrentPosition != -1) {
            Log.d("www",mNewsTheme[args.getInt(ARG_NEWS_NUMBER)]);
        }
    }

    @Override
    public View onCreateView(LayoutInflater inflater, ViewGroup container,
                             Bundle savedInstanceState) {
        if (NetworkManager.isNetworkAvailable(getContext()))
       return inflater.inflate(R.layout.news_list_fragment,null);
        else return inflater.inflate(R.layout.news_list_fragment_empty,null);
    }


    private void startLoadNews(String n) {

        if (NetworkManager.isNetworkAvailable(getContext())) {
            //do request

        if (mNews != null
                && podrobnostiItemTask.getStatus() != PodrobnostiItemTask.Status.FINISHED) {
            podrobnostiItemTask.cancel(true);
        }else{
            //if internet is reconnection, then recreate activity view
            if(intNoYes!=false){
                getActivity().recreate();
            }
           // execute(String[]) you can put array of links to web pages, or array of Integer[]
           // if first param is Integer[] etc.
          //  podrobnostiItemTask.cancel(true);
            podrobnostiItemTask = (PodrobnostiItemTask) new PodrobnostiItemTask()
            .execute(n);

        }
        } else {
            Toast toast = Toast.makeText(getContext(),
                    "Отсутствует интернет подключение", Toast.LENGTH_LONG);
            toast.show();
            //dissconnect internet
            intNoYes=true;
        }
    }



    @Override
    public void onActivityResult(int requestCode, int resultCode, Intent data){

        ((NewsAdapter)getListAdapter()).notifyDataSetChanged();
    }

    public boolean onOptionsItemSelected(MenuItem item){
        switch(item.getItemId()){
            case R.id.refresh:
                IfNewsRefreshDone=true;
                startLoadNews(mNewsTheme[mCurrentPosition]);
                return true;

                }
        return super.onOptionsItemSelected(item);
    }

    @Override
    public void onCreateOptionsMenu(Menu menu, MenuInflater inflater){
        super.onCreateOptionsMenu(menu,inflater);
        inflater.inflate(R.menu.news_fragment_list,menu);
        getActivity().setTitle(mNewsItem[mCurrentPosition]);

        refreshItem=menu;
        if (NetworkManager.isNetworkAvailable(getContext()))
        Animation(IfNewsRefreshDone);
        else {
            IfNewsRefreshDone=true;
            Animation(IfNewsRefreshDone);

        }
    }


    @Override
    public void onListItemClick(ListView l, View v, int position, long id) {
        News n = ((NewsAdapter)getListAdapter()).getItem(position);
        Log.d(TAG, "opa");
        Intent i=new Intent(getActivity(),NewsPagerActivity.class);
        i.putExtra(NewsFragment.EXTRA_NEWS_ID,n.getId());
        startActivity(i);
    }

    private class NewsAdapter extends ArrayAdapter<News> {
        public NewsAdapter(ArrayList<News> news) {
            super(getActivity(),0,news);
        }

        @Override
        public View getView(int position, View convertView, ViewGroup parent) {
            // If we do not have an idea, fill it

            if (convertView == null) {
                convertView = getActivity().getLayoutInflater()
                        .inflate(R.layout.list_item_news, null);

            }
            News n=getItem(position);

            TextView tv1=(TextView)convertView.findViewById(R.id.textView1);
            tv1.setText(n.getTitle());

            TextView tv2=(TextView)convertView.findViewById(R.id.textView2);
            tv2.setText(n.getPubDate());

            ImageView imageView1=(ImageView)convertView.findViewById(R.id.imageView1);
            String newsPickUrl=n.getUrlPick();

            try{

                Picasso.with(getContext()) //transfer context app
                        .load(newsPickUrl) //image source
                        .into(imageView1); //link on ImageView
            }catch(Exception e){
                imageView1.setImageResource(R.drawable.news_background);
            }

            // presentation setting for News object
            return convertView;
        }
    }

    public void Animation(Boolean ifNewsRefreshDone){
        final MenuItem item=refreshItem.findItem(R.id.refresh);
        item.setActionView(R.layout.rotating_refresh);
        ImageView refresh = (ImageView) item.getActionView().findViewById(R.id.refreshButton);
        Animation rotation = AnimationUtils.loadAnimation(getContext(), R.anim.rotate);

        if(ifNewsRefreshDone==false) {
            refresh.startAnimation(rotation);
            IfNewsRefreshDone=false;
        }else {refresh.clearAnimation();
            refresh.setOnClickListener(new View.OnClickListener() {
                @Override
                public void onClick(View v) {
                    onOptionsItemSelected(item);
                }
            });
        }
    }

    private class PodrobnostiItemTask extends AsyncTask<String,Void,ArrayList<News>>{

        @Override
        protected void onPreExecute() {
            if(IfNewsRefreshDone==true)
            Animation(false);
            super.onPreExecute();
        }
        @Override
        protected ArrayList<News> doInBackground(String...string){
               String n=string[0];
                try{
                   return new DomFeedParser(n).parse();
                }catch(Throwable t){
                   return null;
                  }
        }
        @Override
        protected void onPostExecute(ArrayList<News> items){
          if(items==null){
              getActivity().recreate();
          }else {
              NewsBox.get(getContext()).getNews();
              NewsBox.get(getContext()).addNewsBox(items);
              NewsBox.get(getContext()).saveNews();

              NewsAdapter adapter = new NewsAdapter(items);
              setListAdapter(adapter);

              Animation(true);
              IfNewsRefreshDone = true;
          }
        }
    }
}
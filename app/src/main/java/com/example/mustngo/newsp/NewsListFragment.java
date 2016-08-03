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

import java.io.IOException;
import java.net.URI;
import java.util.ArrayList;

/**
 * Created by yu-sat on 19.01.2016.
 */
public class NewsListFragment extends ListFragment {

    final static String ARG_NEWS_NUMBER="number";
    int mCurrentPosition = 0;

    private Boolean IfNewsRefreshDone=false;
    private String[] mNewsTheme;
    private Menu refreshItem;

    private ArrayList<News> mNews;
    private String mNiwsItemLink="http://podrobnosti.ua/rss/feed/?category=all-news";
    private static final String TAG = "NewsListFragment";
    //private static final String TAG2 = "";
    private ProgressBar progressBar;
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

        //getActivity().setTitle(R.string.app_name);
        //starLoadNews(mNiwsItemLink);
        Bundle args = getArguments();
      //  starLoadNews(args.getInt(ARG_NEWS_NUMBER));

        if (args != null) {
            // Set article based on argument passed in
            mCurrentPosition=args.getInt(ARG_NEWS_NUMBER);
          //  Log.d("www",toString(mCurrentPosition));



          if(mCurrentPosition==10){
               /* AboutProgramDialogFragment aboutProgramDialogFragment=new AboutProgramDialogFragment();
                aboutProgramDialogFragment.show(getFragmentManager(), "dlg1");
               */
            /*  FragmentManager fm=getActivity().getSupportFragmentManager();
              AboutProgramDialogFragment aboutProgramDialogFragment=new AboutProgramDialogFragment();
              aboutProgramDialogFragment.show(fm, "dlg1");
             /* Intent i=new Intent(getActivity(),AboutProgramActivity.class);
              startActivity(i);
*/
            }
                else {
              Log.d("news_theme_link",mNewsTheme[args.getInt(ARG_NEWS_NUMBER)]);

              startLoadNews(mNewsTheme[mCurrentPosition]);}
        } else if (mCurrentPosition != -1) {
            // Set article based on saved instance state defined during onCreateView
           // starLoadNews(0);
            Log.d("www",mNewsTheme[args.getInt(ARG_NEWS_NUMBER)]);
        }
      //  starLoadNews(mNiwsItemLink);
       // new PodrobnostiItemTask().execute();

        /* getActivity() - этот вспомогательный метод Fragment
        возвращает активность-хоста и позволяет фрагменту более
        активно выполнять функции активности.                                                                                                                                                                                   */

        //включаем синглетный экземпляр, чтобы получить список новостей
      //  mNews = NewsBox.get(getActivity()).getNews();
       // ArrayAdapter<News> adapter = new ArrayAdapter<News>(getActivity(), android.R.layout.simple_list_item_1, mNews);
        //NewsAdapter adapter=new NewsAdapter(mNews);
        //setListAdapter(adapter);
      //  mNews = NewsBox.get(getActivity()).getNews();

    }

    private void startLoadNews(String n) {
      /*  if (podrobnostiItemTask != null
                && podrobnostiItemTask.getStatus() != PodrobnostiItemTask.Status.FINISHED) {
            podrobnostiItemTask.cancel(true);
        }else
        // execute(String[]) you can put array of links to web pages, or array of Integer[]
        // if first param is Integer[] etc.
        podrobnostiItemTask = (PodrobnostiItemTask) new PodrobnostiItemTask()
                .execute();*/
        if (NetworkManager.isNetworkAvailable(getContext())) {
            // делаем спокойно запрос

        if (mNews != null
                && podrobnostiItemTask.getStatus() != PodrobnostiItemTask.Status.FINISHED) {
            podrobnostiItemTask.cancel(true);
        }else
            // execute(String[]) you can put array of links to web pages, or array of Integer[]
            // if first param is Integer[] etc.
            podrobnostiItemTask = (PodrobnostiItemTask) new PodrobnostiItemTask()
                    .execute(n);
        } else {
            // если сети нет показываем Тост или
            // кидаем на активити с красивым дизайном где просим сделать реконнект
            Toast toast = Toast.makeText(getContext(),
                    "Отсутствует интернет подключение", Toast.LENGTH_LONG);
            toast.show();
        }
    }



    @Override
    public void onActivityResult(int requestCode, int resultCode, Intent data){
        ((NewsAdapter)getListAdapter()).notifyDataSetChanged();
    }
/*
    public void onPrepareOptionsMenu(Menu menu)
    {
     //   MenuItem register = menu.findItem(R.id.action_settings);
        register.setVisible(true);
       // return true;
    }*/

    public boolean onOptionsItemSelected(MenuItem item){
        switch(item.getItemId()){
            case R.id.refresh:
            //  new PodrobnostiItemTask().execute();
                IfNewsRefreshDone=true;
                startLoadNews(mNewsTheme[mCurrentPosition]);
             /*   ImageView refresh = (ImageView) item.getActionView().findViewById(R.id.refreshButton);
                Animation rotation = AnimationUtils.loadAnimation(getContext(), R.anim.rotate);
                refresh.startAnimation(rotation);
                if (IfNewsRefreshDone==true)
                refresh.clearAnimation();

                /* Animation animation = new RotateAnimation(0.0f, 360.0f,
                        Animation.RELATIVE_TO_SELF, 0.5f, Animation.RELATIVE_TO_SELF,
                        0.5f);
                animation.setRepeatCount(-1);
                animation.setDuration(2000);
                item.getItemId().findViewById(R.id.refresh).setAnimation(animation);

                */
                return true;
          /*  case R.id.action_settings:
                AboutProgramDialogFragment aboutProgramDialogFragment=new AboutProgramDialogFragment();
                aboutProgramDialogFragment.show(getFragmentManager(), "dlg1");
                return true;*/
                }
        return super.onOptionsItemSelected(item);
    }

    @Override
    public void onCreateOptionsMenu(Menu menu, MenuInflater inflater){
        super.onCreateOptionsMenu(menu,inflater);
        inflater.inflate(R.menu.news_fragment_list,menu);
      //  MenuItem menuItem=menu.findItem(R.id.action_settings);
      //  menuItem.setTitle(R.string.about_program);
        refreshItem=menu;
        final MenuItem item=menu.findItem(R.id.refresh);
        item.setActionView(R.layout.rotating_refresh);
        ImageView refresh = (ImageView) item.getActionView().findViewById(R.id.refreshButton);

        refresh.setOnClickListener(new View.OnClickListener() {

            @Override
            public void onClick(View v) {
                onOptionsItemSelected(item);
            }
        });
    }


    @Override
    public void onListItemClick(ListView l, View v, int position, long id) {
        News n = ((NewsAdapter)getListAdapter()).getItem(position);
        Log.d(TAG, "opa");
      //  mNews=NewsBox.get(getActivity()).getNews();
        //Start NewsActivity

        Intent i=new Intent(getActivity(),NewsPagerActivity.class);
        i.putExtra(NewsFragment.EXTRA_NEWS_ID,n.getId());
       // startActivity(i);
        startActivity(i);
    }

    private class NewsAdapter extends ArrayAdapter<News> {
        public NewsAdapter(ArrayList<News> news) {

            super(getActivity(),0,news);

          //    mNews=NewsBox.get(getContext()).getNews();
        }
     /*   public void refresh() {

            LayoutInflater inflater = (LayoutInflater) getActivity().getSystemService(Context.LAYOUT_INFLATER_SERVICE);
            ImageView iv = (ImageView) inflater.inflate(R.layout.refresh_action_view, null);

            Animation rotation = AnimationUtils.loadAnimation(getActivity(), R.anim.clockwise_refresh);
            rotation.setRepeatCount(Animation.INFINITE);
            iv.startAnimation(rotation);

            refreshItem.setActionView(iv);

            //TODO trigger loading
        }

        public void completeRefresh() {
            refreshItem.getActionView().clearAnimation();
            refreshItem.setActionView(null);
        }*/

        @Override
        public View getView(int position, View convertView, ViewGroup parent) {
            // Если мы не получили представление, заполняем его

            if (convertView == null) {
                convertView = getActivity().getLayoutInflater()
                        .inflate(R.layout.list_item_news, null);

            }
            News n=getItem(position);

            TextView tv1=(TextView)convertView.findViewById(R.id.textView1);
            tv1.setText(n.getTitle());

            TextView tv2=(TextView)convertView.findViewById(R.id.textView2);
            tv2.setText(n.getPubDate());
          /*
            //piece of news
            TextView tv2=(TextView)convertView.findViewById(R.id.textView2);
            tv2.setText(n.getDescription());*/

            ImageView imageView1=(ImageView)convertView.findViewById(R.id.imageView1);
            String newsPickUrl=n.getUrlPick();

            try{

                Picasso.with(getContext()) //передаем контекст приложения
                        .load(newsPickUrl) //адрес изображения
                        .into(imageView1); //ссылка на ImageView
            }catch(Exception e){ Picasso.with(getContext()) //передаем контекст приложения
                    .load("http://podrobnosti.ua/media/pictures/2016/6/10/thumbs/472x246/chto-nelzja-delat-zhenschinam-vo-vremja-prosmotra-futbolnogo-matcha_rect_7e155568a54a71a8547feca3641e0192.jpg") //адрес изображения
                    .into(imageView1);}

            // Настройка представления для объекта Crime
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
        }else {refresh.clearAnimation();
            refresh.setOnClickListener(new View.OnClickListener() {
                @Override
                public void onClick(View v) {
                    onOptionsItemSelected(item);
                }
            });
        }
    }

    public static class NetworkManager {

        public  static boolean isNetworkAvailable(Context context) {
            ConnectivityManager cm = (ConnectivityManager) context.getSystemService(Context.CONNECTIVITY_SERVICE);
            NetworkInfo networkInfo = cm.getActiveNetworkInfo();
            if (networkInfo != null && networkInfo.isConnected()) {
                return true;
            } else {
                return false;
            }
        }
    }



    private class PodrobnostiItemTask extends AsyncTask<String,Void,ArrayList<News>>{

        @Override
        protected void onPreExecute() {
            //textView.setText("Hello !!!");
        /*    progressBar = (ProgressBar) getActivity().findViewById(R.id.progressBar1);
            progressBar.setVisibility(View.VISIBLE);*/
            if(IfNewsRefreshDone==true)
            Animation(false);
          // IfNewsRefreshDone=false;
            super.onPreExecute();

        }





        @Override
        protected ArrayList<News> doInBackground(String...string){
               String n=string[0];

          //  String string_url=getResources().getString(R.string.all);
         //   return new DomFeedParser(string_url).parse();
            return new DomFeedParser(n).parse();
            // return new DomFeedParser("http://podrobnosti.ua/rss/feed/?category=all-news").parse();
            // return new DomFeedParser("https://www.jw.org/uk/%D0%BD%D0%BE%D0%B2%D0%B8%D0%BD%D0%B8/rss/FullNewsRSS/feed.xml").parse();
           // return new AndroidSaxFeedParser("http://podrobnosti.ua/rss/feed/?category=all-news").parse();
        }
        @Override
        protected void onPostExecute(ArrayList<News> items){
          //  mNews=items;
           //NewsBox.get(getActivity());

       /*    NewsIntentJSONSerializer js=new NewsIntentJSONSerializer(getActivity(),"news.json");
            try {
                js.saveNews(items);
            } catch (JSONException e) {
                e.printStackTrace();
            } catch (IOException e) {
                e.printStackTrace();
            }*/
         /*    progressBar.setVisibility(View.INVISIBLE);*/

             NewsBox.get(getContext()).getNews();
             NewsBox.get(getContext()).addNewsBox(items);
             NewsBox.get(getContext()).saveNews();

          //  mNews=NewsBox.get(getActivity()).getNews();
            NewsAdapter adapter=new NewsAdapter(items);
            setListAdapter(adapter);

            //IfNewsRefreshDone=false;
            if(IfNewsRefreshDone==true){
                Animation(true);

            }  IfNewsRefreshDone=false;

           // mNews = NewsBox.get(getActivity()).getNews();

            // setupAdapter();
        }
    }
}
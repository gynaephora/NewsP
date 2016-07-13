package com.example.mustngo.newsp;

import android.annotation.TargetApi;
import android.app.ActionBar;
import android.app.Activity;
import android.content.Intent;
import android.content.pm.ActivityInfo;
import android.os.Build;
import android.os.Bundle;
import android.support.v4.app.Fragment;
import android.support.v4.app.NavUtils;
import android.support.v7.app.AppCompatActivity;
import android.util.Log;
import android.view.KeyEvent;
import android.view.LayoutInflater;
import android.view.Menu;
import android.view.MenuInflater;
import android.view.MenuItem;
import android.view.View;
import android.view.ViewGroup;
import android.widget.Button;
import android.widget.ImageView;
import android.widget.TextView;
import android.widget.Toolbar;

import com.squareup.picasso.Picasso;

import org.w3c.dom.Text;

import java.util.UUID;

/**
 * Created by mustango on 20.01.16.
 */
public  class NewsFragment extends Fragment{
    News mNews;
    public static final String EXTRA_NEWS_ID = "com.mustngo.newsintent.NEWS_ID";
    private static final String TAG="NewsFragment";
    ImageView mNewsPick;
    TextView mPubDate;
    TextView mAuthor;
    TextView mNewsmaker;
    TextView mTitle;
    TextView mFullText;
    TextView mNewsUrl;

      public static NewsFragment newInstance(UUID newsId){
        Bundle args=new Bundle();
        args.putSerializable(EXTRA_NEWS_ID, newsId);
        NewsFragment fragment=new NewsFragment();
        fragment.setArguments(args);
        return fragment;
    }

    @Override
    public void onCreate(Bundle savedInstanceState){
        super.onCreate(savedInstanceState);
        setMenuVisibility(true);
        setHasOptionsMenu(true);

        getActivity().setRequestedOrientation (ActivityInfo.SCREEN_ORIENTATION_PORTRAIT);



//        toolbar.setLogo(R.drawable.ic_action_back);
       // toolbar.inflateMenu(R.menu.news_fragment);
/*
        // setDisplayHomeAsUpEnable(true);
        android.support.v7.widget.Toolbar toolbar = (android.support.v7.widget.Toolbar) findViewById(R.id.toolbar);
        toolbar.setNavigationIcon(R.drawable.ic_action_back);
        // Get a support ActionBar corresponding to this toolbar
        android.support.v7.app.ActionBar ab = getSupportActionBar();
                // Enable the Up button
        ab.setDisplayHomeAsUpEnabled(true);*/

        UUID newsId = (UUID)getArguments().getSerializable(EXTRA_NEWS_ID);
        mNews=NewsBox.get(getActivity()).getNewsm(newsId);
        Log.d(TAG, mNews.toString());
    }
/*
    public void onPrepareOptionsMenu(Menu menu)
    {
        MenuItem register = menu.findItem(R.id.action_back);
        register.setVisible(true);
        // return true;
    }*/

   /* public boolean onOptionsItemSelected(MenuItem item){
        int id=item.getItemId();
        if (id==R.id.action_settings){
            return true;
        }
        return super.onOptionsItemSelected(item);
    }*/



   public boolean onOptionsItemSelected(MenuItem item){
       switch(item.getItemId()) {
           case android.R.id.home:
               //   case R.id.action_back:
               getActivity().finish();
               /*
               if (NavUtils.getParentActivityName(getActivity()) != null) {
                   NavUtils.navigateUpFromSameTask(getActivity());
               }*/
               return true;
           case R.id.action_settings:
               AboutProgramDialogFragment aboutProgramDialogFragment = new AboutProgramDialogFragment();
               aboutProgramDialogFragment.show(getFragmentManager(), "about_program");
               return true;
       }
       return super.onOptionsItemSelected(item);
   }
    @Override
    public void onCreateOptionsMenu(Menu menu, MenuInflater inflater){
         super.onCreateOptionsMenu(menu,inflater);
         menu.clear();
         inflater.inflate(R.menu.news_fragment,menu);

         MenuItem menuItem=menu.findItem(R.id.action_settings);
         menuItem.setTitle(R.string.about_program);
    }

    @TargetApi(11)
    @Override
    public View onCreateView(LayoutInflater inflater, ViewGroup parent, Bundle savedInstanceState){
        View vv = inflater.inflate(R.layout.fragment_news, parent, false);
/*
       if(mNews.getTitle()!=null){
           ((AppCompatActivity)getActivity()).setTitle(mNews.getTitle());
        }
*/
/*
        if (Build.VERSION.SDK_INT>=Build.VERSION_CODES.HONEYCOMB){
           if(NavUtils.getParentActivityName(getActivity())!=null) {
             //  getActivity().getActionBar().setDisplayHomeAsUpEnabled(true);
            //   ((AppCompatActivity)getActivity()).getActionBar().setDisplayHomeAsUpEnabled(true);
           }
        }*/


        ImageView imageView1=(ImageView)vv.findViewById(R.id.news_pick);
        String newsPickUrl=mNews.getUrlPick();

       try{

            Picasso.with(getContext()) //передаем контекст приложения
                    .load(newsPickUrl) //адрес изображения
                    .into(imageView1); //ссылка на ImageView
        }catch(Exception e){ Picasso.with(getContext()) //передаем контекст приложения
                .load("http://podrobnosti.ua/media/pictures/2016/6/10/thumbs/472x246/chto-nelzja-delat-zhenschinam-vo-vremja-prosmotra-futbolnogo-matcha_rect_7e155568a54a71a8547feca3641e0192.jpg") //адрес изображения
                .into(imageView1);}

        mPubDate=(TextView) vv.findViewById(R.id.pub_date);
        mPubDate.setText(mNews.getPubDate());


        mAuthor=(TextView) vv.findViewById(R.id.author);
        mAuthor.setText(mNews.getAuthor());

        mNewsmaker=(TextView) vv.findViewById(R.id.newsmaker);
        mNewsmaker.setText(mNews.getNewsmaker());

        mTitle=(TextView) vv.findViewById(R.id.news_title);
        mTitle.setText(mNews.getTitle());

        mFullText=(TextView) vv.findViewById(R.id.full_text);
        mFullText.setText(mNews.getFullText());

        mNewsUrl=(TextView) vv.findViewById(R.id.news_link);
        mNewsUrl.setText(mNews.getLink());

        return vv;
    }

    @Override
    public void onActivityResult(int requestCode, int resultCode, Intent data){
        if(resultCode!= Activity.RESULT_OK) return;
    }

    @Override
    public void onPause(){
        super.onPause();
        //NewsBox.get(getActivity()).saveNews();
    }

}
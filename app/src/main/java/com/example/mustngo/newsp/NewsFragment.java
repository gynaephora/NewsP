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
 * Created by Volodymyr Korzhovsky on 20.01.16.
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

        UUID newsId = (UUID)getArguments().getSerializable(EXTRA_NEWS_ID);
        mNews=NewsBox.get(getActivity()).getNewsm(newsId);
        Log.d(TAG, mNews.toString());
    }

   public boolean onOptionsItemSelected(MenuItem item){
       switch(item.getItemId()) {
           case android.R.id.home:
               getActivity().finish();
               return true;
           }
       return super.onOptionsItemSelected(item);
   }
    @Override
    public void onCreateOptionsMenu(Menu menu, MenuInflater inflater){
         super.onCreateOptionsMenu(menu,inflater);
         inflater.inflate(R.menu.news_fragment,menu);
    }

    @TargetApi(11)
    @Override
    public View onCreateView(LayoutInflater inflater, ViewGroup parent, Bundle savedInstanceState){
        View vv = inflater.inflate(R.layout.fragment_news, parent, false);

        ImageView imageView1=(ImageView)vv.findViewById(R.id.news_pick);
        String newsPickUrl=mNews.getUrlPick();

       try{

            Picasso.with(getContext()) //transfer of the application context.
                    .load(newsPickUrl) //source of image
                    .into(imageView1); //link to ImageView
        }catch(Exception e){
           imageView1.setImageResource(R.drawable.news_background);
       }

        mPubDate=(TextView) vv.findViewById(R.id.pub_date);
        mPubDate.setText(mNews.getPubDate());

        if(mNews.getAuthor()!="null") {
            mAuthor = (TextView) vv.findViewById(R.id.author);
            mAuthor.setText("Автор: " + mNews.getAuthor());
        }
        mNewsmaker=(TextView) vv.findViewById(R.id.newsmaker);
        mNewsmaker.setText("По материалам: "+mNews.getNewsmaker());

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
        }

}
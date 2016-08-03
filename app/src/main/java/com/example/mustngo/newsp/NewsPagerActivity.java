package com.example.mustngo.newsp;

//import android.app.FragmentManager;

import android.os.Bundle;
import android.support.v4.app.Fragment;
import android.support.v4.app.FragmentActivity;
import android.support.v4.app.FragmentManager;
import android.support.v4.app.FragmentStatePagerAdapter;
import android.support.v4.view.ViewPager;
import android.support.v4.widget.DrawerLayout;
import android.support.v7.app.ActionBar;
import android.support.v7.app.AppCompatActivity;
import android.support.v7.widget.Toolbar;

import java.util.ArrayList;
import java.util.UUID;


/**
 * Created by mustango on 20.01.16.
 */
public class NewsPagerActivity extends AppCompatActivity {
    private ViewPager mViewPager;
    //private ArrayList<News> mNews;

    @Override
    public  void onCreate(Bundle savedInstanceState){
        super.onCreate(savedInstanceState);
        setContentView(R.layout.news_pager_layout);


        //toolbar
        android.support.v7.widget.Toolbar toolbar = (android.support.v7.widget.Toolbar) findViewById(R.id.toolbar);
        setSupportActionBar(toolbar);
        // Get a support ActionBar corresponding to this toolbar
        ActionBar ab = getSupportActionBar();
        ab.setTitle("good");
        // Enable the Up button
        ab.setDisplayHomeAsUpEnabled(true);
        toolbar.setNavigationIcon(R.drawable.ic_action_back);
       // mViewPager=new ViewPager(this);
        mViewPager=(ViewPager) findViewById(R.id.pager);



     //   mViewPager.setId(R.id.viewPager);
       // setContentView(mViewPager);

        final ArrayList<News> mNews=NewsBox.get(this).getNews();
        FragmentManager fm=getSupportFragmentManager();
        mViewPager.setAdapter(new FragmentStatePagerAdapter(fm) {
            @Override
            public int getCount() {
                return mNews.size();
            }

            @Override
            public Fragment getItem(int pos) {
            /*News news=mNews.get(pos);
            return NewsFragment.newInstance(news.getId());*/
                UUID crimeId =  mNews.get(pos).getId();
                return NewsFragment.newInstance(crimeId);

            }
    });
        UUID newsId=(UUID)getIntent().getSerializableExtra(NewsFragment.EXTRA_NEWS_ID);
        for(int i=0;i<mNews.size();i++) {
            if (mNews.get(i).getId().equals(newsId)) {
                mViewPager.setCurrentItem(i);
               break;
            }
        }
        mViewPager.setOnPageChangeListener(new ViewPager.OnPageChangeListener() {
            public void onPageScrollStateChanged(int state) {
            }

            public void onPageScrolled(int pos, float posOffset, int posOffsetPixels) {
            }

            public void onPageSelected(int pos) {
                News news=mNews.get(pos);
            /*    if (news.getTitle()!=null){
                    setTitle(news.getTitle());
                }*/
            }
        });


    }
}

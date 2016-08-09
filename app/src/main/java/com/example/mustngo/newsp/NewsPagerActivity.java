package com.example.mustngo.newsp;


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
import android.util.Log;

import java.io.BufferedReader;
import java.io.FileNotFoundException;
import java.io.IOException;
import java.io.InputStreamReader;
import java.util.ArrayList;
import java.util.UUID;


/**
 * Created by Volodymyr Korzhovsky on 20.01.16.
 */
public class NewsPagerActivity extends AppCompatActivity {
    private ViewPager mViewPager;

    final String LOG_TAG = "myLogs";
    final String FILENAME = "file";
    String str = "";
    String mTitle;
    @Override
    public  void onCreate(Bundle savedInstanceState){
        super.onCreate(savedInstanceState);
        setContentView(R.layout.news_pager_layout);

        //toolbar
        android.support.v7.widget.Toolbar toolbar = (android.support.v7.widget.Toolbar) findViewById(R.id.toolbar);
        setSupportActionBar(toolbar);
        // Get a support ActionBar corresponding to this toolbar

        try {
            // oopen stream for read
            BufferedReader br = new BufferedReader(new InputStreamReader(
                    openFileInput(FILENAME)));

            // reading content
            while ((str = br.readLine()) != null) {
                Log.d(LOG_TAG, "File read");
                mTitle=str;

            }
        } catch (FileNotFoundException e) {
            e.printStackTrace();
        } catch (IOException e) {
            e.printStackTrace();
        }
        Log.d(LOG_TAG, "Title:"+mTitle);
        ActionBar ab = getSupportActionBar();
        ab.setTitle(mTitle);
        // Enable the Up button
        ab.setDisplayHomeAsUpEnabled(true);
        toolbar.setNavigationIcon(R.drawable.ic_action_back);

        mViewPager=(ViewPager) findViewById(R.id.pager);

        final ArrayList<News> mNews=NewsBox.get(this).getNews();
        FragmentManager fm=getSupportFragmentManager();
        mViewPager.setAdapter(new FragmentStatePagerAdapter(fm) {
            @Override
            public int getCount() {
                return mNews.size();
            }

            @Override
            public Fragment getItem(int pos) {
                UUID newsId =  mNews.get(pos).getId();
                return NewsFragment.newInstance(newsId);
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
                }
        });
    }
}

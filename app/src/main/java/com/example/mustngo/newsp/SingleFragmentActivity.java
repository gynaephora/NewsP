package com.example.mustngo.newsp;

import android.app.ActionBar;
import android.content.Intent;
import android.content.pm.ActivityInfo;
import android.os.Bundle;
import android.support.v4.app.Fragment;
import android.support.v4.app.FragmentActivity;
import android.support.v4.app.FragmentManager;
import android.support.v4.widget.DrawerLayout;

import android.support.v7.app.ActionBarDrawerToggle;
import android.support.v7.app.AppCompatActivity;
import android.support.v7.widget.Toolbar;
import android.util.Log;
import android.view.Menu;
import android.view.MenuInflater;
import android.view.MenuItem;
import android.view.View;
import android.widget.AdapterView;
import android.widget.ArrayAdapter;
import android.widget.ListView;

import java.io.IOException;

/**
 * Created by Volodymyr Korzhovsky on 20.01.16.
 */
public abstract class SingleFragmentActivity extends AppCompatActivity{
    protected abstract Fragment createFragment();
    private String[] mNewsTheme;
    private DrawerLayout mDrawerLayout;
    private ListView mDrawerList;

    private CharSequence mDrawerTitle;
    private CharSequence mTitle;

    private ActionBarDrawerToggle mDrawerToggle;



    @Override
    public void onCreate(Bundle savedInstanceState){
        super.onCreate(savedInstanceState);
        setContentView(R.layout.drawer_layout);

        mTitle = mDrawerTitle = getTitle();


        mNewsTheme = getResources().getStringArray(R.array.news_theme);



        mDrawerLayout = (DrawerLayout) findViewById(R.id.drawer_layout);
        mDrawerList = (ListView) findViewById(R.id.left_drawer);

        // Set the adapter for the list view
        mDrawerList.setAdapter(new ArrayAdapter<String>(this,
               R.layout.drawer_list_item, mNewsTheme));
        // Set the list's click listener
        // enabling action bar app icon and behaving it as toggle button

        mDrawerLayout = (DrawerLayout) findViewById(R.id.drawer_layout);
        Toolbar toolbar = (Toolbar) findViewById(R.id.toolbar);
        setSupportActionBar(toolbar);
        toolbar.setNavigationIcon(R.drawable.ic_drawer);

        mDrawerToggle = new ActionBarDrawerToggle(this,mDrawerLayout, toolbar, R.string.drawer_open, R.string.drawer_close) {

            /** Called when a drawer has settled in a completely closed state.*/
            public void onDrawerClosed(View view) {
                super.onDrawerClosed(view);
                invalidateOptionsMenu(); // creates call to onPrepareOptionsMenu()
            }

            /** Called when a drawer has settled in a completely open state.*/
            public void onDrawerOpened(View drawerView) {
                super.onDrawerOpened(drawerView);
               // Set the list's click listener
                mDrawerList.setOnItemClickListener(new DrawerItemClickListener());
                invalidateOptionsMenu(); // creates call to onPrepareOptionsMenu()
            }
        };

        // Set the drawer toggle as the DrawerListener
        mDrawerLayout.setDrawerListener(mDrawerToggle);

///////////////////////////////
        Bundle args = new Bundle();

        // Insert the fragment by replacing any existing fragment
        FragmentManager fm = getSupportFragmentManager();
        Fragment fragment = fm.findFragmentById(R.id.fragmentContainer);

        if (fragment == null){

            fragment = createFragment();
            args.putInt(NewsListFragment.ARG_NEWS_NUMBER,0);
          fragment.setArguments(args);
            fm.beginTransaction().replace(R.id.fragmentContainer, fragment).commit();
        }
////////////////////////
    }

    private class DrawerItemClickListener implements ListView.OnItemClickListener {
        @Override
        public void onItemClick(AdapterView parent, View view, int position, long id) {
            selectItem(position);
        }
    }
    /** Swaps fragments in the main content view */
    private void selectItem(int position) {
        // Create a new fragment and specify the planet to show based on position

         Bundle args = new Bundle();
         args.putInt(NewsListFragment.ARG_NEWS_NUMBER,position);

        // Insert the fragment by replacing any existing fragment
        FragmentManager fm = getSupportFragmentManager();
        Fragment fragment = fm.findFragmentById(R.id.fragmentContainer);

       if (fragment != null){
           fragment = createFragment();
           args.putInt(NewsListFragment.ARG_NEWS_NUMBER,position);
           fragment.setArguments(args);

           if(position!=10 && position!=11){fm.beginTransaction().replace(R.id.fragmentContainer, fragment).commit();
               setTitle(mNewsTheme[position]);

          }
           else if(position==11){

               Intent intent = new Intent(this, AboutProgramActivity.class);
               startActivity(intent);

           }
       }
        // Highlight the selected item, update the title, and close the drawer
        if (position!=10) {
            mDrawerList.setItemChecked(position, true);
            mDrawerLayout.closeDrawer(mDrawerList);
        }
    }

    @Override
    public void setTitle(CharSequence title) {
        mTitle = title;
        android.support.v7.app.ActionBar ab = getSupportActionBar();
        ab.setTitle(mTitle);


    }
}

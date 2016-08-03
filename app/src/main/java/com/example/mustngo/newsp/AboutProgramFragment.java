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

import java.util.UUID;

/**
 * Created by kalmaro on 03.08.2016.
 */
public class AboutProgramFragment extends Fragment {

    TextView mAboutProgram;

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
        menu.clear();
        inflater.inflate(R.menu.news_fragment,menu);

    }

    @TargetApi(11)
    @Override
    public View onCreateView(LayoutInflater inflater, ViewGroup parent, Bundle savedInstanceState){
        View vv = inflater.inflate(R.layout.fragment_about_program, parent, false);
        mAboutProgram=(TextView) vv.findViewById(R.id.about_program_text);
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

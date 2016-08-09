package com.example.mustngo.newsp;

import android.content.pm.ActivityInfo;
import android.os.Bundle;
import android.support.v4.app.Fragment;
import android.support.v4.app.FragmentActivity;
import android.support.v4.app.FragmentManager;
import android.support.v4.app.FragmentStatePagerAdapter;
import android.support.v4.app.FragmentTransaction;
import android.support.v4.view.ViewPager;
import android.support.v7.app.ActionBar;
import android.support.v7.app.AppCompatActivity;
import android.view.MenuItem;

import java.util.ArrayList;
import java.util.UUID;

/**
 * Created by kalmaro on 03.08.2016.
 */
public class AboutProgramActivity extends AppCompatActivity {

    @Override
    public  void onCreate(Bundle savedInstanceState){
        super.onCreate(savedInstanceState);
        setContentView(R.layout.activity_about_program);


        setRequestedOrientation (ActivityInfo.SCREEN_ORIENTATION_PORTRAIT);
       /* setMenuVisibility(true);
        setHasOptionsMenu(true);*/
        //toolbar
        android.support.v7.widget.Toolbar toolbar = (android.support.v7.widget.Toolbar) findViewById(R.id.toolbar);
        setSupportActionBar(toolbar);
        // Get a support ActionBar corresponding to this toolbar
        ActionBar ab = getSupportActionBar();

        // Enable the Up button
        ab.setTitle("О программе");
        ab.setDisplayHomeAsUpEnabled(true);
        toolbar.setNavigationIcon(R.drawable.ic_action_back);

       }

    public boolean onOptionsItemSelected(MenuItem item){
        switch(item.getItemId()) {
            case android.R.id.home:
                //   case R.id.action_back:
               finish();
               /*
               if (NavUtils.getParentActivityName(getActivity()) != null) {
                   NavUtils.navigateUpFromSameTask(getActivity());
               }*/
                return true;
          /* case R.id.action_settings:
               AboutProgramDialogFragment aboutProgramDialogFragment = new AboutProgramDialogFragment();
               aboutProgramDialogFragment.show(getFragmentManager(), "about_program");
               return true;*/
        }
        return super.onOptionsItemSelected(item);
    }

}

package com.example.mustngo.newsp;

import android.support.v4.app.Fragment;

public class NewsListActivity extends SingleFragmentActivity {

    @Override
    protected Fragment createFragment(){
        return new NewsListFragment();
    }
}

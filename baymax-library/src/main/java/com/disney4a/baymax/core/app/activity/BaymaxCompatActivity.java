package com.disney4a.baymax.core.app.activity;

import android.support.annotation.LayoutRes;
import android.support.v7.app.AppCompatActivity;
import android.view.View;
import android.view.ViewGroup;

import com.disney4a.baymax.utils.ViewSelector;

/**
 * Created by tjy on 2017/8/24 0024.
 */

public class BaymaxCompatActivity extends AppCompatActivity {
    @Override
    public void setContentView(@LayoutRes int layoutResID) {
        super.setContentView(layoutResID);
        ViewSelector.setAnnotationsFrom(this).select(this).release();
    }

    @Override
    public void setContentView(View view) {
        super.setContentView(view);
        ViewSelector.setAnnotationsFrom(this).select(this).release();
    }

    @Override
    public void setContentView(View view, ViewGroup.LayoutParams params) {
        super.setContentView(view, params);
        ViewSelector.setAnnotationsFrom(this).select(this).release();
    }
}

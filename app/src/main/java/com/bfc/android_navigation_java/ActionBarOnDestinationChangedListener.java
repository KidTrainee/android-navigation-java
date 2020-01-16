package com.bfc.android_navigation_java;

import android.graphics.drawable.Drawable;

import androidx.annotation.NonNull;
import androidx.annotation.StringRes;
import androidx.appcompat.app.ActionBar;
import androidx.appcompat.app.ActionBarDrawerToggle;
import androidx.appcompat.app.AppCompatActivity;
import androidx.navigation.ui.AppBarConfiguration;

//
// Created by  on 2020-01-16.
//
class ActionBarOnDestinationChangedListener extends
        AbstractAppBarOnDestinationChangedListener {
    private final AppCompatActivity mActivity;

    ActionBarOnDestinationChangedListener(@NonNull AppCompatActivity activity,
                                          @NonNull AppBarConfiguration configuration) {
        super(activity.getDrawerToggleDelegate().getActionBarThemedContext(), configuration);
        mActivity = activity;
    }

    @Override
    protected void setTitle(CharSequence title) {
        ActionBar actionBar = mActivity.getSupportActionBar();
        actionBar.setTitle(title);
    }

    @Override
    protected void setNavigationIcon(Drawable icon,
                                     @StringRes int contentDescription) {
        ActionBar actionBar = mActivity.getSupportActionBar();
        if (icon == null) {
            actionBar.setDisplayHomeAsUpEnabled(false);
        } else {
            actionBar.setDisplayHomeAsUpEnabled(true);
            ActionBarDrawerToggle.Delegate delegate = mActivity.getDrawerToggleDelegate();
            delegate.setActionBarUpIndicator(icon, contentDescription);
        }
    }
}

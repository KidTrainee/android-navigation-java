package com.bfc.android_navigation_java;

import android.animation.ObjectAnimator;
import android.animation.ValueAnimator;
import android.content.Context;
import android.graphics.drawable.Drawable;
import android.os.Bundle;
import android.text.TextUtils;

import java.lang.ref.WeakReference;
import java.util.Set;
import java.util.regex.Matcher;
import java.util.regex.Pattern;

import androidx.annotation.NonNull;
import androidx.annotation.Nullable;
import androidx.annotation.StringRes;
import androidx.appcompat.graphics.drawable.DrawerArrowDrawable;
import androidx.drawerlayout.widget.DrawerLayout;
import androidx.navigation.FloatingWindow;
import androidx.navigation.NavController;
import androidx.navigation.NavDestination;
import androidx.navigation.ui.AppBarConfiguration;

//
// Created by  on 2020-01-16.
//
abstract class AbstractAppBarOnDestinationChangedListener
        implements NavController.OnDestinationChangedListener {
    private final Context mContext;
    private final Set<Integer> mTopLevelDestinations;
    @Nullable
    private final WeakReference<DrawerLayout> mDrawerLayoutWeakReference;
    private DrawerArrowDrawable mArrowDrawable;
    private ValueAnimator mAnimator;

    AbstractAppBarOnDestinationChangedListener(@NonNull Context context,
                                               @NonNull AppBarConfiguration configuration) {
        mContext = context;
        mTopLevelDestinations = configuration.getTopLevelDestinations();
        DrawerLayout drawerLayout = configuration.getDrawerLayout();
        if (drawerLayout != null) {
            mDrawerLayoutWeakReference = new WeakReference<>(drawerLayout);
        } else {
            mDrawerLayoutWeakReference = null;
        }
    }

    protected abstract void setTitle(CharSequence title);

    protected abstract void setNavigationIcon(Drawable icon, @StringRes int contentDescription);

    @Override
    public void onDestinationChanged(@NonNull NavController controller,
                                     @NonNull NavDestination destination, @Nullable Bundle arguments) {
        if (destination instanceof FloatingWindow) {
            return;
        }
        DrawerLayout drawerLayout = mDrawerLayoutWeakReference != null
                ? mDrawerLayoutWeakReference.get()
                : null;
        if (mDrawerLayoutWeakReference != null && drawerLayout == null) {
            controller.removeOnDestinationChangedListener(this);
            return;
        }
        CharSequence label = destination.getLabel();
        if (!TextUtils.isEmpty(label)) {
            // Fill in the data pattern with the args to build a valid URI
            StringBuffer title = new StringBuffer();
            Pattern fillInPattern = Pattern.compile("\\{(.+?)\\}");
            Matcher matcher = fillInPattern.matcher(label);
            while (matcher.find()) {
                String argName = matcher.group(1);
                if (arguments != null && arguments.containsKey(argName)) {
                    matcher.appendReplacement(title, "");
                    //noinspection ConstantConditions
                    title.append(arguments.get(argName).toString());
                } else {
                    throw new IllegalArgumentException("Could not find " + argName + " in "
                            + arguments + " to fill label " + label);
                }
            }
            matcher.appendTail(title);
            setTitle(title);
        }
        boolean isTopLevelDestination = AppNavigationUI.matchDestinations(destination,
                mTopLevelDestinations);
        if (drawerLayout == null && isTopLevelDestination) {
            setNavigationIcon(null, 0);
        } else {
            setActionBarUpIndicator(drawerLayout != null && isTopLevelDestination);
        }
    }

    private void setActionBarUpIndicator(boolean showAsDrawerIndicator) {
        boolean animate = true;
        if (mArrowDrawable == null) {
            mArrowDrawable = new DrawerArrowDrawable(mContext);
            // We're setting the initial state, so skip the animation
            animate = false;
        }
        setNavigationIcon(mArrowDrawable, showAsDrawerIndicator
                ? R.string.nav_app_bar_open_drawer_description
                : R.string.nav_app_bar_navigate_up_description);
        float endValue = showAsDrawerIndicator ? 0f : 1f;
        if (animate) {
            float startValue = mArrowDrawable.getProgress();
            if (mAnimator != null) {
                mAnimator.cancel();
            }
            mAnimator = ObjectAnimator.ofFloat(mArrowDrawable, "progress",
                    startValue, endValue);
            mAnimator.start();
        } else {
            mArrowDrawable.setProgress(endValue);
        }
    }
}
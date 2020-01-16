package com.bfc.android_navigation_java;

import android.view.Menu;
import android.view.MenuItem;

import java.util.Set;

import androidx.annotation.IdRes;
import androidx.annotation.NonNull;
import androidx.appcompat.app.AppCompatActivity;
import androidx.navigation.NavController;
import androidx.navigation.NavDestination;
import androidx.navigation.NavGraph;
import androidx.navigation.NavOptions;

//
// Created by  on 2020-01-16.
//
abstract class BaseActivity extends AppCompatActivity {

    protected NavDestination findStartDestination(NavGraph graph) {
        NavDestination startDestination = graph;
        while (startDestination instanceof NavGraph) {
            NavGraph parent = (NavGraph) startDestination;
            startDestination = parent.findNode(parent.getStartDestination());
        }
        return startDestination;
    }

    protected boolean onNavDestinationSelected(@NonNull MenuItem item,
                                                   @NonNull NavController navController) {
        NavOptions.Builder builder = new NavOptions.Builder()
                .setLaunchSingleTop(true)
                .setEnterAnim(R.anim.nav_default_enter_anim)
                .setExitAnim(R.anim.nav_default_exit_anim)
                .setPopEnterAnim(R.anim.nav_default_pop_enter_anim)
                .setPopExitAnim(R.anim.nav_default_pop_exit_anim);
        if ((item.getOrder() & Menu.CATEGORY_SECONDARY) == 0) {
            builder.setPopUpTo(findStartDestination(navController.getGraph()).getId(), false);
        }
        NavOptions options = builder.build();
        try {
            //TODO provide proper API instead of using Exceptions as Control-Flow.
            navController.navigate(item.getItemId(), null, options);
            return true;
        } catch (IllegalArgumentException e) {
            return false;
        }
    }

}

package com.bfc.android_navigation_java;

import android.content.res.Resources;
import android.os.Bundle;
import android.util.Log;
import android.view.Menu;
import android.view.MenuItem;
import android.view.ViewParent;
import android.widget.Toast;

import com.google.android.material.navigation.NavigationView;

import java.lang.ref.WeakReference;
import java.util.Set;

import androidx.annotation.NonNull;
import androidx.annotation.Nullable;
import androidx.appcompat.widget.Toolbar;
import androidx.core.view.GravityCompat;
import androidx.drawerlayout.widget.DrawerLayout;
import androidx.navigation.NavController;
import androidx.navigation.NavDestination;
import androidx.navigation.NavOptions;
import androidx.navigation.fragment.NavHostFragment;
import androidx.navigation.ui.AppBarConfiguration;

import static androidx.navigation.Navigation.findNavController;

/**
 * A simple activity demonstrating use of a NavHostFragment with a navigation drawer.
 */
public class MainActivity extends BaseActivity {

    private static final String TAG = MainActivity.class.getSimpleName();
    public static int fragmentCounter = 0;
    private AppBarConfiguration appBarConfiguration;
    private DrawerLayout mDrawerLayout;

    @Override
    protected void onCreate(Bundle savedInstanceState) {
        super.onCreate(savedInstanceState);
        setContentView(R.layout.activity_main);

        Toolbar toolbar = findViewById(R.id.toolbar);
        setSupportActionBar(toolbar);

        @Nullable NavHostFragment host = (NavHostFragment) getSupportFragmentManager().findFragmentById(R.id.my_nav_host_fragment);

        if (host == null) throw new NullPointerException("NavHostFragment cannot be null");

        NavController navController = host.getNavController();

        // You should also remove the old appBarConfiguration setup above
        mDrawerLayout = findViewById(R.id.drawer_layout);

        appBarConfiguration = new AppBarConfiguration.Builder(R.id.home_dest, R.id.deeplink_dest)
                .setDrawerLayout(mDrawerLayout)
                .build();

        setupActionBar(navController, appBarConfiguration);

        setupNavigationMenu(navController);

//        navController.addOnDestinationChangedListener(new NavController.OnDestinationChangedListener() {
//            @Override
//            public void onDestinationChanged(@NonNull NavController controller, @NonNull NavDestination destination, @Nullable Bundle arguments) {
//                String dest;
//                try {
//                    dest = getResources().getResourceName(destination.getId());
//                } catch (Resources.NotFoundException e) {
//                    dest = Integer.toString(destination.getId());
//                }
//
//                Toast.makeText(MainActivity.this, "Navigated to " + dest, Toast.LENGTH_SHORT).show();
//                Log.d(TAG, "onDestinationChanged: Navigated to " + dest);
//            }
//        });
    }

    @Override
    public void onBackPressed() {
        super.onBackPressed();
    }

    private void setupNavigationMenu(final NavController navController) {
        final NavigationView navigationView = findViewById(R.id.nav_view);
        navigationView.setNavigationItemSelectedListener(
                new NavigationView.OnNavigationItemSelectedListener() {
                    @Override
                    public boolean onNavigationItemSelected(@NonNull MenuItem item) {
                        boolean handled = onNavDestinationSelected(item, navController);
                        if (handled) {
                            ViewParent parent = navigationView.getParent();
                            if (parent instanceof DrawerLayout) {
                                ((DrawerLayout) parent).closeDrawer(navigationView);
                            }
                        }
                        return handled;
                    }
                });
        final WeakReference<NavigationView> weakReference = new WeakReference<>(navigationView);
        navController.addOnDestinationChangedListener(
                new NavController.OnDestinationChangedListener() {
                    @Override
                    public void onDestinationChanged(@NonNull NavController controller,
                                                     @NonNull NavDestination destination, @Nullable Bundle arguments) {
                        NavigationView view = weakReference.get();
                        if (view == null) {
                            navController.removeOnDestinationChangedListener(this);
                            return;
                        }
                        Menu menu = view.getMenu();
                        for (int i = 0, size = menu.size(); i < size; i++) {
                            MenuItem item = menu.getItem(i);
                            item.setChecked(AppNavigationUI.matchDestination(destination, item.getItemId()));
                        }
                    }
                });
    }

    private void setupActionBar(NavController navController, AppBarConfiguration appBarConfiguration) {
//        // By using appBarConfig, it will also determine whether to
//        // show the up arrow or drawer menu icon
        navController.addOnDestinationChangedListener(
                new ActionBarOnDestinationChangedListener(this, appBarConfiguration));
    }

    @Override
    public boolean onOptionsItemSelected(@NonNull MenuItem item) {
        // Have the NavigationUI look for an action or destination matching the menu
        // item id and navigate there if found.
        // Otherwise, bubble up to the parent.
        NavController navController = findNavController(this, R.id.my_nav_host_fragment);
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
            navController.navigate(item.getItemId(), null, options);
            return true;
        } catch (IllegalArgumentException e) {
            return super.onOptionsItemSelected(item);
        }
    }

    @Override
    public boolean onSupportNavigateUp() {
        NavController navController = findNavController(this, R.id.my_nav_host_fragment);

        NavDestination currentDestination = navController.getCurrentDestination();
        Set<Integer> topLevelDestinations = appBarConfiguration.getTopLevelDestinations();
        if (mDrawerLayout != null && currentDestination != null
                && AppNavigationUI.matchDestinations(currentDestination, topLevelDestinations)) {
            mDrawerLayout.openDrawer(GravityCompat.START);
            return true;
        } else {
            if (navController.navigateUp()) {
                return true;
            } else if (appBarConfiguration.getFallbackOnNavigateUpListener() != null) {
                return appBarConfiguration.getFallbackOnNavigateUpListener().onNavigateUp();
            } else {
                return false;
            }
        }
    }

}

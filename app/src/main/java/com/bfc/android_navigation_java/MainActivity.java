package com.bfc.android_navigation_java;

import android.content.res.Resources;
import android.os.Bundle;
import android.util.Log;
import android.view.Menu;
import android.view.MenuItem;
import android.widget.Toast;

import com.google.android.material.bottomnavigation.BottomNavigationView;
import com.google.android.material.navigation.NavigationView;

import java.util.HashSet;

import androidx.annotation.NonNull;
import androidx.annotation.Nullable;
import androidx.appcompat.app.AppCompatActivity;
import androidx.appcompat.widget.Toolbar;
import androidx.drawerlayout.widget.DrawerLayout;
import androidx.navigation.NavController;
import androidx.navigation.NavDestination;
import androidx.navigation.fragment.NavHostFragment;
import androidx.navigation.ui.AppBarConfiguration;
import androidx.navigation.ui.NavigationUI;

import static androidx.navigation.Navigation.findNavController;

/**
 * A simple activity demonstrating use of a NavHostFragment with a navigation drawer.
 */
public class MainActivity extends AppCompatActivity {

    private static final String TAG = MainActivity.class.getSimpleName();
    private AppBarConfiguration appBarConfiguration;

    @Override
    protected void onCreate(Bundle savedInstanceState) {
        super.onCreate(savedInstanceState);
        setContentView(R.layout.activity_main);

        Toolbar toolbar = findViewById(R.id.toolbar);
        setSupportActionBar(toolbar);

        @Nullable NavHostFragment host = (NavHostFragment) getSupportFragmentManager().findFragmentById(R.id.my_nav_host_fragment);

        if (host == null) throw new NullPointerException("NavHostFragment cannot be null");

        NavController navController = host.getNavController();

        appBarConfiguration = new AppBarConfiguration.Builder(navController.getGraph()).build();

        // You should also remove the old appBarConfiguration setup above
        DrawerLayout drawerLayout = findViewById(R.id.drawer_layout);
        if (drawerLayout != null) {
            appBarConfiguration = new AppBarConfiguration.Builder(R.id.home_dest, R.id.deeplink_dest)
                    .setDrawerLayout(drawerLayout)
                    .build();
        }

        setupActionBar(navController, appBarConfiguration);

        setupNavigationMenu(navController);

        setupBottomNavMenu(navController);

        navController.addOnDestinationChangedListener(new NavController.OnDestinationChangedListener() {
            @Override
            public void onDestinationChanged(@NonNull NavController controller, @NonNull NavDestination destination, @Nullable Bundle arguments) {
                String dest;
                try {
                    dest = getResources().getResourceName(destination.getId());
                } catch (Resources.NotFoundException e) {
                    dest = Integer.toString(destination.getId());
                }

                Toast.makeText(MainActivity.this, "Navigated to " + dest, Toast.LENGTH_SHORT).show();
                Log.d(TAG, "onDestinationChanged: Navigated to " + dest);
            }
        });
    }

    private void setupBottomNavMenu(NavController navController) {

//        val bottomNav = findViewById<BottomNavigationView>(R.id.bottom_nav_view)
//        bottomNav?.setupWithNavController(navController)
        BottomNavigationView bottomNav = findViewById(R.id.bottom_nav_view);
        if (bottomNav != null) {
            NavigationUI.setupWithNavController(bottomNav, navController);
        }
    }

    private void setupNavigationMenu(NavController navController) {
//        // In split screen mode, you can drag this view out from the left
//        // This does NOT modify the actionbar
        NavigationView sideNavView = findViewById(R.id.nav_view);
        if (sideNavView != null) {
            NavigationUI.setupWithNavController(sideNavView, navController);
        }
    }

    private void setupActionBar(NavController navController, AppBarConfiguration appBarConfiguration) {
//        // This allows NavigationUI to decide what label to show in the action bar
//        // By using appBarConfig, it will also determine whether to
//        // show the up arrow or drawer menu icon
        NavigationUI.setupActionBarWithNavController(this, navController, appBarConfiguration);

    }

    @Override
    public boolean onCreateOptionsMenu(Menu menu) {
        boolean retValue = super.onCreateOptionsMenu(menu);
        NavigationView navigationView = findViewById(R.id.nav_view);
        // The NavigationView already has these same navigation items, so we only add
        // navigation items to the menu here if there isn't a NavigationView
        if (navigationView == null) {
            getMenuInflater().inflate(R.menu.overflow_menu, menu);
            return true;
        }
        return retValue;
    }

    @Override
    public boolean onOptionsItemSelected(@NonNull MenuItem item) {
        // Have the NavigationUI look for an action or destination matching the menu
        // item id and navigate there if found.
        // Otherwise, bubble up to the parent.

        return NavigationUI.onNavDestinationSelected(item, findNavController(this, R.id.my_nav_host_fragment))
                || super.onOptionsItemSelected(item);
    }

    @Override
    public boolean onSupportNavigateUp() {
        return NavigationUI.navigateUp(findNavController(this, R.id.my_nav_host_fragment), appBarConfiguration);
    }
}

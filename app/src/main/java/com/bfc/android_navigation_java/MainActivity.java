package com.bfc.android_navigation_java;

import android.content.res.Resources;
import android.os.Bundle;
import android.util.Log;
import android.view.Menu;
import android.view.MenuItem;
import android.widget.Toast;

import com.google.android.material.navigation.NavigationView;

import androidx.annotation.NonNull;
import androidx.annotation.Nullable;
import androidx.appcompat.app.AppCompatActivity;
import androidx.appcompat.widget.Toolbar;
import androidx.navigation.NavController;
import androidx.navigation.NavDestination;
import androidx.navigation.fragment.NavHostFragment;
import androidx.navigation.ui.AppBarConfiguration;

/**
 * A simple activity demonstrating use of a NavHostFragment with a navigation drawer.
 */
public class MainActivity extends AppCompatActivity {

    private static final String TAG = MainActivity.class.getSimpleName();

    @Override
    protected void onCreate(Bundle savedInstanceState) {
        super.onCreate(savedInstanceState);
        setContentView(R.layout.activity_main);

        Toolbar toolbar = findViewById(R.id.toolbar);
        setSupportActionBar(toolbar);

        @Nullable NavHostFragment host = (NavHostFragment) getSupportFragmentManager().findFragmentById(R.id.my_nav_host_fragment);

        if (host == null) throw new NullPointerException("NavHostFragment cannot be null");

        NavController navController = host.getNavController();

        AppBarConfiguration appBarConfiguration = new AppBarConfiguration.Builder(navController.getGraph()).build();

        // TODO STEP 9.5 - Create an AppBarConfiguration with the correct top-level destinations
        // You should also remove the old appBarConfiguration setup above
//        val drawerLayout : DrawerLayout? = findViewById(R.id.drawer_layout)
//        appBarConfiguration = AppBarConfiguration(
//                setOf(R.id.home_dest, R.id.deeplink_dest),
//                drawerLayout)
        // TODO END STEP 9.5

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
        // TODO STEP 9.3 - Use NavigationUI to set up Bottom Nav
//        val bottomNav = findViewById<BottomNavigationView>(R.id.bottom_nav_view)
//        bottomNav?.setupWithNavController(navController)
        // TODO END STEP 9.3
    }

    private void setupNavigationMenu(NavController navController) {
        // TODO STEP 9.4 - Use NavigationUI to set up a Navigation View
//        // In split screen mode, you can drag this view out from the left
//        // This does NOT modify the actionbar
//        val sideNavView = findViewById<NavigationView>(R.id.nav_view)
//        sideNavView?.setupWithNavController(navController)
        // TODO END STEP 9.4
    }

    private void setupActionBar(NavController navController, AppBarConfiguration appBarConfiguration) {
        // TODO STEP 9.6 - Have NavigationUI handle what your ActionBar displays
//        // This allows NavigationUI to decide what label to show in the action bar
//        // By using appBarConfig, it will also determine whether to
//        // show the up arrow or drawer menu icon
//        setupActionBarWithNavController(navController, appBarConfig)
        // TODO END STEP 9.6
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
        return super.onOptionsItemSelected(item);
        // TODO STEP 9.2 - Have Navigation UI Handle the item selection - make sure to delete
        //  the old return statement above
//        // Have the NavigationUI look for an action or destination matching the menu
//        // item id and navigate there if found.
//        // Otherwise, bubble up to the parent.
//        return item.onNavDestinationSelected(findNavController(R.id.my_nav_host_fragment))
//                || super.onOptionsItemSelected(item)
        // TODO END STEP 9.2
    }

    // TODO STEP 9.7 - Have NavigationUI handle up behavior in the ActionBar
//    override fun onSupportNavigateUp(): Boolean {
//        // Allows NavigationUI to support proper up navigation or the drawer layout
//        // drawer menu, depending on the situation
//        return findNavController(R.id.my_nav_host_fragment).navigateUp(appBarConfiguration)
//    }
    // TODO END STEP 9.7
}

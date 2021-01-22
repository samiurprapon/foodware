package life.nsu.foodware.views.customer;

import android.annotation.SuppressLint;
import android.os.Bundle;
import android.view.MenuItem;

import androidx.annotation.NonNull;
import androidx.appcompat.app.AppCompatActivity;
import androidx.fragment.app.Fragment;
import androidx.fragment.app.FragmentTransaction;

import com.google.android.material.bottomnavigation.BottomNavigationView;

import life.nsu.foodware.R;

public class CustomerHomeActivity extends AppCompatActivity {

    BottomNavigationView bottomNavigationView;

    @Override
    protected void onCreate(Bundle savedInstanceState) {
        super.onCreate(savedInstanceState);
        setContentView(R.layout.activity_customer_home);

        bottomNavigationView = findViewById(R.id.navigation);
        bottomNavigationView.setOnNavigationItemSelectedListener(mOnNavigationItemSelectedListener);

        loadFragment(RestaurantsFragment.newInstance());
    }


    private BottomNavigationView.OnNavigationItemSelectedListener mOnNavigationItemSelectedListener
            = new BottomNavigationView.OnNavigationItemSelectedListener() {
        @Override
        public boolean onNavigationItemSelected(@NonNull MenuItem item) {
            Fragment fragment;

            switch (item.getItemId()) {
                case R.id.nav_restaurant:
                    fragment = RestaurantsFragment.newInstance();
                    loadFragment(fragment);
                    return true;
                case R.id.nav_cart:
                    fragment = CustomerCartFragment.newInstance();
                    loadFragment(fragment);
                    return true;
                case R.id.nav_history:
                    fragment = CustomerOrderHistoryFragment.newInstance();
                    loadFragment(fragment);
                    return true;

                case R.id.nav_more:
                    fragment = CustomerMoreFragment.newInstance();
                    loadFragment(fragment);
                    return true;
            }
            return false;
        }
    };

    /**
     * load fragment into FrameLayout
     *
     */
    private void loadFragment(Fragment fragment) {
        // load fragment
        FragmentTransaction transaction = getSupportFragmentManager().beginTransaction();
        transaction.replace(R.id.frame_container, fragment);
        transaction.addToBackStack(null);
        transaction.commit();
    }

    @Override
    public void onBackPressed() {

        int count = getSupportFragmentManager().getBackStackEntryCount();

        if (count == 0) {
            super.onBackPressed();
            //additional code
        } else {
            getSupportFragmentManager()
                    .beginTransaction()
                    .replace(R.id.frame_container, RestaurantsFragment.newInstance())
                    .setCustomAnimations(R.anim.enter_from_right, R.anim.exit_to_left)
                    .commit();
        }
    }
}
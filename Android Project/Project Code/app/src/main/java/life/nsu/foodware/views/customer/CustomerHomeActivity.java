package life.nsu.foodware.views.customer;

import android.annotation.SuppressLint;
import android.os.Bundle;

import androidx.appcompat.app.AppCompatActivity;
import androidx.fragment.app.Fragment;
import androidx.fragment.app.FragmentTransaction;

import com.google.android.material.bottomnavigation.BottomNavigationView;

import life.nsu.foodware.R;

public class CustomerHomeActivity extends AppCompatActivity {

    @Override
    protected void onCreate(Bundle savedInstanceState) {
        super.onCreate(savedInstanceState);
        setContentView(R.layout.activity_customer_home);
    }


    @SuppressLint("NonConstantResourceId")
    private final BottomNavigationView.OnNavigationItemSelectedListener mOnNavigationItemSelectedListener = item -> {

                Fragment fragment;

                switch (item.getItemId()) {
                    case R.id.nav_restaurant:
                        fragment = new RestaurantsFragment();
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

                    default:
                        fragment = CustomerMoreFragment.newInstance();
                        loadFragment(fragment);
                        return true;
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
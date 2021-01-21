package life.nsu.foodware.views.vendor;

import android.annotation.SuppressLint;
import android.content.Context;
import android.content.Intent;
import android.content.SharedPreferences;
import android.content.res.Configuration;
import android.os.Bundle;
import android.view.MenuItem;
import android.view.View;
import android.widget.Button;
import android.widget.TextView;

import androidx.annotation.NonNull;
import androidx.appcompat.app.ActionBarDrawerToggle;
import androidx.appcompat.app.AppCompatActivity;
import androidx.appcompat.widget.Toolbar;
import androidx.core.view.GravityCompat;
import androidx.drawerlayout.widget.DrawerLayout;
import androidx.fragment.app.Fragment;
import androidx.fragment.app.FragmentManager;

import com.google.android.material.navigation.NavigationView;
import com.google.android.material.snackbar.Snackbar;
import com.google.gson.Gson;

import org.jetbrains.annotations.NotNull;

import java.io.IOException;

import am.appwise.components.ni.NoInternetDialog;
import life.nsu.foodware.R;
import life.nsu.foodware.utils.networking.ServerClient;
import life.nsu.foodware.utils.networking.responses.MessageResponse;
import life.nsu.foodware.utils.networking.responses.StatusResponse;
import life.nsu.foodware.views.auth.AuthenticationActivity;
import life.nsu.foodware.views.vendor.profile.VendorProfileFragment;
import retrofit2.Call;
import retrofit2.Callback;
import retrofit2.Response;

public class VendorHomeActivity extends AppCompatActivity implements NavigationView.OnNavigationItemSelectedListener {

    private FragmentManager fragmentManager;
    private NavigationView navigationView;
    private DrawerLayout mDrawer;
    Toolbar toolbar;
    private ActionBarDrawerToggle drawerToggle;
    Button mStatus;

    TextView mLogout;

    SharedPreferences sharedPreferences;
    NoInternetDialog noInternetDialog;

    @Override
    protected void onCreate(Bundle savedInstanceState) {
        super.onCreate(savedInstanceState);
        overridePendingTransition(android.R.anim.fade_in, android.R.anim.fade_out);
        setContentView(R.layout.activity_vendor_home);

        toolbar = findViewById(R.id.toolbar);
        setSupportActionBar(toolbar);

        // Find our drawer view
        navigationView = findViewById(R.id.navigation_view);
        mDrawer = findViewById(R.id.drawer_layout);
        mLogout = findViewById(R.id.tv_logout);
        mStatus = findViewById(R.id.btn_restaurant_status);

        sharedPreferences = getSharedPreferences("user", Context.MODE_PRIVATE);

        noInternetDialog = new NoInternetDialog.Builder(this).build();

        // Setup toggle to display hamburger icon with nice animation
        drawerToggle = new ActionBarDrawerToggle(this, mDrawer, toolbar, R.string.drawer_open, R.string.drawer_close) {
            @Override
            public void onDrawerClosed(View view) {
                super.onDrawerClosed(view);
                invalidateOptionsMenu(); // creates call to onPrepareOptionsMenu()
            }

            @Override
            public void onDrawerOpened(View drawerView) {
                super.onDrawerOpened(drawerView);
                invalidateOptionsMenu(); // creates call to onPrepareOptionsMenu()
            }
        };

        drawerToggle.setDrawerIndicatorEnabled(false);
        drawerToggle.setDrawerSlideAnimationEnabled(true);

        // custom hamburger button
        toolbar.setNavigationIcon(R.drawable.ic_hamburger);

        // Tie DrawerLayout events to the ActionBarToggle
        mDrawer.addDrawerListener(drawerToggle);
        drawerToggle.syncState();

        getSupportActionBar().setHomeButtonEnabled(true);
        getSupportActionBar().setDisplayHomeAsUpEnabled(true);
        getSupportActionBar().setHomeAsUpIndicator(R.drawable.ic_hamburger);
        getSupportActionBar().show();

        navigationView.setNavigationItemSelectedListener(this);

        fragmentManager = getSupportFragmentManager();
        fragmentManager.beginTransaction()
                .replace(R.id.content_frame, VendorHomeFragment.newInstance())
                .commit();

        navigationView.getHeaderView(0).setOnClickListener(view -> {
            fragmentManager.beginTransaction()
                    .replace(R.id.content_frame, VendorProfileFragment.newInstance())
                    .commit();

            mDrawer.closeDrawer(GravityCompat.START);
        });

        mStatus.setOnClickListener(new View.OnClickListener() {
            @Override
            public void onClick(View v) {
                updateRestaurantStatus(sharedPreferences.getString("accessToken", "null"));
            }
        });

        mLogout.setOnClickListener(v -> {
            mDrawer.closeDrawer(GravityCompat.START);

            syncLogout(v);
        });
    }

    private void updateRestaurantStatus(String accessToken) {
        String status = sharedPreferences.getString("status", "close");
        if(status.equals("close")) {
            status = "open";
        } else {
            status = "close";
        }

        Call<StatusResponse> call = ServerClient.getInstance().getRoute().updateStatus(accessToken, status);

        call.enqueue(new Callback<StatusResponse>() {
            @Override
            public void onResponse(@NotNull Call<StatusResponse> call, @NotNull Response<StatusResponse> response) {
                if(response.isSuccessful()) {
                    if (response.body() != null) {
                        String finalStatus = response.body().getStatus();

                        sharedPreferences.edit().putString("status", finalStatus).apply();
                        mStatus.setText(finalStatus);

                    }
                }
            }

            @Override
            public void onFailure(@NotNull Call<StatusResponse> call, @NotNull Throwable t) {

            }
        });
    }

    private void removeCredentials() {
        SharedPreferences.Editor editor = sharedPreferences.edit();

        editor.remove("type");
        editor.remove("accessToken");
        editor.remove("refreshToken");
        editor.apply();
    }

    private void syncLogout(View view) {
        String accessToken = sharedPreferences.getString("accessToken", "null");

        Call<MessageResponse> call = ServerClient.getInstance().getRoute().deAuthentication(accessToken);

        call.enqueue(new Callback<MessageResponse>() {
            @Override
            public void onResponse(@NotNull Call<MessageResponse> call, @NotNull Response<MessageResponse> response) {
                if(response.isSuccessful()) {
                    removeCredentials();

                    Intent intent = new Intent(getApplication().getApplicationContext(), AuthenticationActivity.class);
                    intent.setFlags(Intent.FLAG_ACTIVITY_NEW_TASK | Intent.FLAG_ACTIVITY_CLEAR_TASK);
                    startActivity(intent);
                } else {
                    Gson gson = new Gson();
                    try {
                        MessageResponse messageResponse = gson.fromJson(response.errorBody().string(), MessageResponse.class);
                        Snackbar.make(view, messageResponse.getMessage(), Snackbar.LENGTH_SHORT).show();
                    } catch (IOException e) {
                        e.printStackTrace();
                    }
                }
            }

            @Override
            public void onFailure(@NotNull Call<MessageResponse> call, @NotNull Throwable t) {

            }
        });
    }

    @Override
    public void onBackPressed() {
        if (mDrawer.isDrawerOpen(GravityCompat.START)) {
            mDrawer.closeDrawer(GravityCompat.START);
        } else {
            super.onBackPressed();
            overridePendingTransition(android.R.anim.fade_in, android.R.anim.fade_out);
        }
    }

    @Override
    public boolean onOptionsItemSelected(MenuItem item) {
//        Log.d("Hamburger", "onOptionsItemSelected: "+item.getItemId());
        if (item != null && item.getItemId() == R.id.home) {
            toggle();
            return true;
        }

        return super.onOptionsItemSelected(item);
    }

    @Override
    protected void onPostCreate(Bundle savedInstanceState) {
        super.onPostCreate(savedInstanceState);
        // Sync the toggle state after onRestoreInstanceState has occurred.
        drawerToggle.syncState();
    }

    @Override
    public void onConfigurationChanged(@NotNull Configuration newConfig) {
        super.onConfigurationChanged(newConfig);
        // Pass any configuration change to the drawer toggles
        drawerToggle.onConfigurationChanged(newConfig);
    }

    @SuppressLint("NonConstantResourceId")
    @Override
    public boolean onNavigationItemSelected(@NonNull MenuItem menuItem) {
        Fragment fragment;

        switch (menuItem.getItemId()) {
            case R.id.vendor_home:
                fragment = VendorHomeFragment.newInstance();
                break;
            case R.id.menu:
                fragment = MenuItemsFragment.newInstance();
                break;
            case R.id.promotions:
                fragment = PromotionFragment.newInstance();
                break;
            case R.id.transactions:
                fragment = TransactionsFragment.newInstance();
                break;
            case R.id.row_container:
                fragment = VendorProfileFragment.newInstance();
                break;
            default:
                fragment = new VendorHomeFragment();
                break;
        }

        navigationView.setCheckedItem(menuItem);

        fragmentManager = getSupportFragmentManager();
        fragmentManager.beginTransaction()
                .replace(R.id.content_frame, fragment)
                .commit();

        mDrawer.closeDrawer(GravityCompat.START);
        return true;
    }

    private void toggle() {
        if (mDrawer.isDrawerVisible(GravityCompat.START)) {
            mDrawer.closeDrawer(GravityCompat.START);
        } else {
            mDrawer.openDrawer(GravityCompat.START);
        }
    }

    @Override
    protected void onDestroy() {
        super.onDestroy();
        noInternetDialog.onDestroy();
    }
}
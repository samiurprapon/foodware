package life.nsu.foodware;

import android.content.Context;
import android.content.Intent;
import android.content.SharedPreferences;
import android.os.Bundle;
import android.os.Handler;
import android.os.Looper;

import androidx.appcompat.app.AppCompatActivity;

import com.google.firebase.FirebaseApp;
import com.google.firebase.auth.FirebaseAuth;

import am.appwise.components.ni.NoInternetDialog;
import life.nsu.foodware.utils.Constants;
import life.nsu.foodware.views.auth.AuthenticationActivity;
import life.nsu.foodware.views.customer.CustomerHomeActivity;
import life.nsu.foodware.views.rider.RiderHomeActivity;
import life.nsu.foodware.views.vendor.VendorHomeActivity;
import life.nsu.foodware.views.vendor.profile.CreateVendorProfileActivity;

public class SplashActivity extends AppCompatActivity {

    SharedPreferences preferences;
    String type;

    NoInternetDialog noInternetDialog;

    @Override
    protected void onCreate(Bundle savedInstanceState) {
        super.onCreate(savedInstanceState);
        overridePendingTransition(android.R.anim.fade_in, android.R.anim.fade_out);
        setContentView(R.layout.activity_splash);

        FirebaseApp.initializeApp(this);

        preferences = getApplication().getApplicationContext().getSharedPreferences(Constants.USER, Context.MODE_PRIVATE);
        type = preferences.getString("type", "null");

        noInternetDialog = new NoInternetDialog.Builder(this).build();

        new Handler(Looper.myLooper()).postDelayed(() -> {
            if (!noInternetDialog.isShowing())
                if (FirebaseAuth.getInstance().getCurrentUser() != null) {
                    activitySwitch(type);
                } else {
                    activitySwitch(type);
                }
        }, 200);

    }


    public void activitySwitch(String type) {
        Intent intent;

        switch (type) {
            case "customer":
                intent = new Intent(SplashActivity.this, CustomerHomeActivity.class);
                break;
            case "vendor":
                intent = new Intent(SplashActivity.this, VendorHomeActivity.class);
                break;
            case "rider":
                intent = new Intent(SplashActivity.this, RiderHomeActivity.class);
                break;
            default:
                intent = new Intent(SplashActivity.this, AuthenticationActivity.class);
                break;
        }

        if (type.equals("vendor") && !checkValidation(type)) {
            intent = new Intent(SplashActivity.this, CreateVendorProfileActivity.class);
        }

        intent.addFlags(Intent.FLAG_ACTIVITY_CLEAR_TASK | Intent.FLAG_ACTIVITY_NEW_TASK);
        getApplication().startActivity(intent);
    }

    @Override
    protected void onDestroy() {
        super.onDestroy();
        noInternetDialog.onDestroy();
    }

    private boolean checkValidation(String type) {

        boolean flag = false;

        if (type.equals(Constants.VENDOR)) {
            SharedPreferences preferences = getSharedPreferences(Constants.RESTAURANT, Context.MODE_PRIVATE);

            String logo = preferences.getString("logo", null);
            String name = preferences.getString("name", null);
            String owner = preferences.getString("owner", null);
            String bkash = preferences.getString("bkash", null);
            String phone = preferences.getString("phone", null);
            String status = preferences.getString("status", null);
            String latitude = preferences.getString("latitude", null);
            String longitude = preferences.getString("longitude", null);

            String opening = preferences.getString("opening", null);
            String closing = preferences.getString("closing", null);

            if (logo == null || name == null || owner == null || bkash == null || phone == null || status == null || latitude == null || longitude == null || opening == null || closing == null) {
                flag = true;
            }
        }

        return flag;
    }
}
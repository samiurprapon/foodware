package life.nsu.foodware;

import android.content.Context;
import android.content.Intent;
import android.content.SharedPreferences;
import android.os.Bundle;
import android.os.Handler;
import android.os.Looper;

import androidx.appcompat.app.AppCompatActivity;

import com.google.firebase.auth.FirebaseAuth;

import am.appwise.components.ni.NoInternetDialog;
import life.nsu.foodware.views.auth.AuthenticationActivity;
import life.nsu.foodware.views.customer.CustomerHomeActivity;
import life.nsu.foodware.views.vendor.VendorHomeActivity;
import life.nsu.foodware.views.vendor.profile.CreateVendorProfileActivity;

public class SplashActivity extends AppCompatActivity {

    SharedPreferences preferences;
    String type;
    boolean validate;

    NoInternetDialog noInternetDialog;

    @Override
    protected void onCreate(Bundle savedInstanceState) {
        super.onCreate(savedInstanceState);
        overridePendingTransition(android.R.anim.fade_in, android.R.anim.fade_out);
        setContentView(R.layout.activity_splash);

        preferences = getApplication().getApplicationContext().getSharedPreferences("user", Context.MODE_PRIVATE);
        type = preferences.getString("type", "null");

        validate = preferences.getBoolean("validation", false);

        noInternetDialog = new NoInternetDialog.Builder(this).build();

        new Handler(Looper.myLooper()).postDelayed(() -> {
            if (!noInternetDialog.isShowing()) {
                if(FirebaseAuth.getInstance().getCurrentUser() != null) {
                    activitySwitch(type);
                } else {
                    activitySwitch(type);
                }

            }
        }, 200);

    }


    public void activitySwitch(String type) {
        Intent intent = null;

        switch (type) {
            case "customer":
                intent = new Intent(SplashActivity.this, CustomerHomeActivity.class);
                break;
            case "vendor":
                intent = new Intent(SplashActivity.this, VendorHomeActivity.class);
                break;
            case "rider":
                //TODO
                // redirect to rider homepage
                break;
            default:
                intent = new Intent(SplashActivity.this, AuthenticationActivity.class);
                break;

        }

        if(type.equals("vendor") && !validate) {
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
}
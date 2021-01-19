package life.nsu.foodware;

import androidx.appcompat.app.AppCompatActivity;

import android.content.Context;
import android.content.Intent;
import android.content.SharedPreferences;
import android.os.Bundle;
import android.os.Handler;
import android.os.Looper;

import life.nsu.foodware.views.AuthenticationActivity;
import life.nsu.foodware.views.vendor.VendorHomeActivity;

public class SplashActivity extends AppCompatActivity {

    SharedPreferences preferences;
    String type;

    @Override
    protected void onCreate(Bundle savedInstanceState) {
        super.onCreate(savedInstanceState);
        overridePendingTransition(android.R.anim.fade_in, android.R.anim.fade_out);
        setContentView(R.layout.activity_splash);

        preferences = getApplication().getApplicationContext().getSharedPreferences("user", Context.MODE_PRIVATE);
        type = preferences.getString("type", "none");

        new Handler(Looper.myLooper()).postDelayed(new Runnable() {
            @Override
            public void run() {
                activitySwitch(type);
            }
        }, 500);

    }


    public void activitySwitch(String type) {
        switch (type) {
            case "customer":
                //TODO
                // redirect to customer home page
                break;
            case "vendor":
                //TODO
                // redirect to vendor home page
                break;
            case "rider":
                //TODO
                // redirect to rider homepage
                break;
            default:
                //TODO
                // redirect to authentication page
                Intent intent = new Intent(SplashActivity.this, AuthenticationActivity.class);
                intent.addFlags(Intent.FLAG_ACTIVITY_CLEAR_TASK | Intent.FLAG_ACTIVITY_NEW_TASK);
                getApplication().startActivity(intent);

        }
    }

}
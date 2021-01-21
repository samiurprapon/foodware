package life.nsu.foodware;

import android.content.Context;
import android.content.Intent;
import android.content.SharedPreferences;
import android.os.Bundle;
import android.os.Handler;
import android.os.Looper;
import android.util.Log;

import androidx.appcompat.app.AppCompatActivity;

import org.jetbrains.annotations.NotNull;

import am.appwise.components.ni.NoInternetDialog;
import life.nsu.foodware.utils.networking.ServerClient;
import life.nsu.foodware.utils.networking.responses.RefreshResponse;
import life.nsu.foodware.utils.networking.responses.ValidationResponse;
import life.nsu.foodware.views.auth.AuthenticationActivity;
import life.nsu.foodware.views.customer.CustomerHomeActivity;
import life.nsu.foodware.views.vendor.VendorHomeActivity;
import life.nsu.foodware.views.vendor.profile.CreateVendorProfileActivity;
import retrofit2.Call;
import retrofit2.Callback;
import retrofit2.Response;

public class SplashActivity extends AppCompatActivity {

    SharedPreferences preferences;
    String type;
    String refreshToken;
    String accessToken;
    boolean validate;

    NoInternetDialog noInternetDialog;

    @Override
    protected void onCreate(Bundle savedInstanceState) {
        super.onCreate(savedInstanceState);
        overridePendingTransition(android.R.anim.fade_in, android.R.anim.fade_out);
        setContentView(R.layout.activity_splash);

        preferences = getApplication().getApplicationContext().getSharedPreferences("user", Context.MODE_PRIVATE);
        type = preferences.getString("type", "null");
        refreshToken = preferences.getString("refreshToken", "null");
        accessToken = preferences.getString("accessToken", "null");
        validate = preferences.getBoolean("validation", false);

        noInternetDialog = new NoInternetDialog.Builder(this).build();

        new Handler(Looper.myLooper()).postDelayed(() -> {
            if (!noInternetDialog.isShowing()) {
                sync(refreshToken);
            }
        }, 200);

    }

    private void sync(String refreshToken) {
        Call<RefreshResponse> responseCall = ServerClient.getInstance().getRoute().refresh(refreshToken);

        responseCall.enqueue(new Callback<RefreshResponse>() {
            @Override
            public void onResponse(@NotNull Call<RefreshResponse> call, @NotNull Response<RefreshResponse> response) {
                if(response.isSuccessful()) {
                    RefreshResponse refreshResponse = response.body();
                    preferences.edit().putString("accessToken", refreshResponse.getAccessToken()).apply();
                    Log.d("accessToken", refreshResponse.getAccessToken());

                    if(type.equals("vendor")) {
                        validation();

                    }

                    activitySwitch(type);

                } else {
                    activitySwitch("null");

                }

            }

            @Override
            public void onFailure(@NotNull Call<RefreshResponse> call, @NotNull Throwable t) {
                Log.d("RefreshResponse", t.getMessage());
                activitySwitch("null");
            }
        });

    }


    private void validation() {
        Call<ValidationResponse> responseCall = ServerClient.getInstance().getRoute().validation(accessToken);

        responseCall.enqueue(new Callback<ValidationResponse>() {
            @Override
            public void onResponse(@NotNull Call<ValidationResponse> call, @NotNull Response<ValidationResponse> response) {
                if(response.isSuccessful()) {
                    if(response.body() != null) {
                        preferences.edit().putBoolean("", response.body().isCompleted()).apply();
                    }
                }
            }

            @Override
            public void onFailure(@NotNull Call<ValidationResponse> call, @NotNull Throwable t) {

            }
        });
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
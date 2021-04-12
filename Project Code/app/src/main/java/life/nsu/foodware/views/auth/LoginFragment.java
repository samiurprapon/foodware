package life.nsu.foodware.views.auth;

import android.annotation.SuppressLint;
import android.content.Context;
import android.content.Intent;
import android.content.SharedPreferences;
import android.os.Bundle;
import android.os.Handler;
import android.os.Looper;
import android.view.LayoutInflater;
import android.view.View;
import android.view.ViewGroup;
import android.widget.Button;
import android.widget.EditText;
import android.widget.TextView;

import androidx.annotation.NonNull;
import androidx.annotation.Nullable;
import androidx.fragment.app.Fragment;

import com.google.android.material.snackbar.Snackbar;
import com.google.gson.Gson;

import org.jetbrains.annotations.NotNull;

import java.io.IOException;
import java.util.Objects;

import life.nsu.foodware.R;
import life.nsu.foodware.utils.CustomLoadingDialog;
import life.nsu.foodware.utils.networking.ServerClient;
import life.nsu.foodware.utils.networking.requests.AuthenticationRequest;
import life.nsu.foodware.utils.networking.responses.AuthenticationResponse;
import life.nsu.foodware.views.customer.CustomerHomeActivity;
import life.nsu.foodware.views.vendor.VendorHomeActivity;
import retrofit2.Call;
import retrofit2.Callback;
import retrofit2.Response;


public class LoginFragment extends Fragment {

    @SuppressLint("StaticFieldLeak")
    static LoginFragment fragment = null;

    private EditText mEmail;
    private EditText mPassword;

    TextView mForgetPassword;
    Button mLogin;

    private SharedPreferences preferences;
    private CustomLoadingDialog loadingDialog;

    public synchronized static LoginFragment newInstance() {
        if(fragment == null) {
            fragment = new LoginFragment();
        }
        return fragment;
    }

    @Override
    public void onCreate(@Nullable Bundle savedInstanceState) {
        super.onCreate(savedInstanceState);

        preferences = getContext().getSharedPreferences("user", Context.MODE_PRIVATE);
    }

    @Override
    public View onCreateView(LayoutInflater inflater, ViewGroup container,
                             Bundle savedInstanceState) {
        // Inflate the layout for this fragment
        return inflater.inflate(R.layout.fragment_login, container, false);
    }

    @Override
    public void onViewCreated(@NonNull View view, @Nullable Bundle savedInstanceState) {
        super.onViewCreated(view, savedInstanceState);

        mEmail = view.findViewById(R.id.et_email);
        mPassword = view.findViewById(R.id.et_password);

        mForgetPassword = view.findViewById(R.id.tv_forget_password);
        mLogin = view.findViewById(R.id.btn_login);

        loadingDialog = new CustomLoadingDialog(getContext());

        mLogin.setOnClickListener(v -> {
            if (!validation()) {
                mPassword.setError("wrong password.");
                return;
            }

            loadingDialog.show();

            String password = mPassword.getText().toString().trim();
            String email = mEmail.getText().toString().trim();

            new Handler(Looper.myLooper()).postDelayed(new Runnable() {
                @Override
                public void run() {
                    authentication(email, password);

                    loadingDialog.hide();

                }
            }, 250);

        });

        mForgetPassword.setOnClickListener(v -> Snackbar.make(getView(), "This feature is not available right now.", Snackbar.LENGTH_SHORT).show());

    }

    private void authentication(String email, String password) {
        Call<AuthenticationResponse> call = ServerClient.getInstance().getRoute().authentication(new AuthenticationRequest(email, password));

        call.enqueue(new Callback<AuthenticationResponse>() {
            @Override
            public void onResponse(@NotNull Call<AuthenticationResponse> call, @NotNull Response<AuthenticationResponse> response) {
                if(response.isSuccessful()) {
                    preferences.edit().putString("refreshToken", response.body().getRefreshToken()).apply();
                    preferences.edit().putString("accessToken", response.body().getAccessToken()).apply();
                    preferences.edit().putString("type", response.body().getType()).apply();

                    activitySwitch(response.body().getType());

                } else {
                    Gson gson = new Gson();
                    try {
                        AuthenticationResponse authResponse = gson.fromJson(response.errorBody().string(), AuthenticationResponse.class);

                        Snackbar.make(getView(), authResponse.getMessage(), Snackbar.LENGTH_SHORT).show();
                    } catch (IOException e) {
                        e.printStackTrace();
                    }
                }

            }

            @Override
            public void onFailure(@NotNull Call<AuthenticationResponse> call, @NotNull Throwable t) {

            }
        });

    }


    private boolean validation() {
        String password = mPassword.getText().toString();
        String email = mEmail.getText().toString();

        if(email.isEmpty()) {
            return false;
        } else if(password.length() >= 6) {
            return true;
        } else {
            return  true;
        }
    }

    private void activitySwitch(String type) {
        Intent intent = null;

        if (Objects.equals(type, "customer")) {
            //TODO
            // clear tasks
            intent = new Intent(getContext(), CustomerHomeActivity.class);
            // if user is new
            // redirect to @CustomerCreateProfileFragment
        } else if (Objects.equals(type, "rider")) {
            //TODO
            // clear tasks
            // redirect to @RiderHomeActivity
            // if user is new
            // redirect to @RiderCreateProfileFragment
        } else if (Objects.equals(type, "vendor")) {
            intent = new Intent(getContext(), VendorHomeActivity.class);

            //TODO
            // if user is new
            // redirect to @VendorCreateProfileFragment
        }

        if (intent != null) {
            intent.setFlags(Intent.FLAG_ACTIVITY_CLEAR_TASK | Intent.FLAG_ACTIVITY_NEW_TASK);
            Objects.requireNonNull(getActivity()).startActivity(intent);
        }
    }
}
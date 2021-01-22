package life.nsu.foodware.views.customer;

import android.content.Context;
import android.content.Intent;
import android.content.SharedPreferences;
import android.os.Bundle;

import androidx.annotation.NonNull;
import androidx.annotation.Nullable;
import androidx.cardview.widget.CardView;
import androidx.fragment.app.Fragment;

import android.os.Handler;
import android.os.Looper;
import android.view.LayoutInflater;
import android.view.View;
import android.view.ViewGroup;

import com.google.android.material.snackbar.Snackbar;
import com.google.gson.Gson;

import org.jetbrains.annotations.NotNull;

import java.io.IOException;

import life.nsu.foodware.R;
import life.nsu.foodware.utils.CustomLoadingDialog;
import life.nsu.foodware.utils.networking.ServerClient;
import life.nsu.foodware.utils.networking.responses.MessageResponse;
import life.nsu.foodware.views.auth.AuthenticationActivity;
import retrofit2.Call;
import retrofit2.Callback;
import retrofit2.Response;


public class CustomerMoreFragment extends Fragment {

    private static CustomerMoreFragment fragment = null;

    CardView mProfile;
    CardView mDiscount;
    CardView mSetting;
    CardView mLegal;
    CardView mLogout;

    SharedPreferences sharedPreferences;
    SharedPreferences userPreferences;

    CustomLoadingDialog loadingDialog;

    public synchronized static CustomerMoreFragment newInstance() {
        if (fragment == null) {
            fragment = new CustomerMoreFragment();
        }
        return fragment;
    }

    @Override
    public void onCreate(@Nullable Bundle savedInstanceState) {
        super.onCreate(savedInstanceState);

        userPreferences = getContext().getSharedPreferences("user", Context.MODE_PRIVATE);
        sharedPreferences = getContext().getSharedPreferences("customer", Context.MODE_PRIVATE);
    }

    @Override
    public View onCreateView(LayoutInflater inflater, ViewGroup container,
                             Bundle savedInstanceState) {
        // Inflate the layout for this fragment
        return inflater.inflate(R.layout.fragment_customer_more, container, false);
    }

    @Override
    public void onViewCreated(@NonNull View view, @Nullable Bundle savedInstanceState) {
        super.onViewCreated(view, savedInstanceState);

        mProfile = view.findViewById(R.id.cv_profile);
        mDiscount = view.findViewById(R.id.cv_discount);
        mSetting = view.findViewById(R.id.cv_settings);
        mLegal = view.findViewById(R.id.cv_legal);
        mLogout = view.findViewById(R.id.cv_logout);

        loadingDialog = new CustomLoadingDialog(getContext());

        mLogout.setOnClickListener(v -> {
            mLogout.setEnabled(false);

            loadingDialog.show();

            new Handler(Looper.myLooper()).postDelayed(new Runnable() {
                @Override
                public void run() {
                    mLogout.setEnabled(true);

                    syncLogout(v);
                }
            }, 400);

        });

        mProfile.setOnClickListener(new View.OnClickListener() {
            @Override
            public void onClick(View v) {
                upcoming(v);
            }
        });
        mDiscount.setOnClickListener(new View.OnClickListener() {
            @Override
            public void onClick(View v) {
                upcoming(v);
            }
        });
        mLegal.setOnClickListener(new View.OnClickListener() {
            @Override
            public void onClick(View v) {
                upcoming(v);
            }
        });
        mSetting.setOnClickListener(new View.OnClickListener() {
            @Override
            public void onClick(View v) {
                upcoming(v);
            }
        });
    }

    private void syncLogout(View view) {
        String accessToken = sharedPreferences.getString("accessToken", "null");

        Call<MessageResponse> call = ServerClient.getInstance().getRoute().deAuthentication(accessToken);

        call.enqueue(new Callback<MessageResponse>() {
            @Override
            public void onResponse(@NotNull Call<MessageResponse> call, @NotNull Response<MessageResponse> response) {
                loadingDialog.hide();
                removeCredentials();

                if(response.isSuccessful()) {
                    Intent intent = new Intent(getContext(), AuthenticationActivity.class);
                    intent.setFlags(Intent.FLAG_ACTIVITY_NEW_TASK | Intent.FLAG_ACTIVITY_CLEAR_TASK);
                    getActivity().startActivity(intent);
                } else {
                    Gson gson = new Gson();
                    try {
                        assert response.errorBody() != null;
                        MessageResponse messageResponse = gson.fromJson(response.errorBody().string(), MessageResponse.class);
//                        Snackbar.make(view, messageResponse.getMessage(), Snackbar.LENGTH_SHORT).show();
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

    private void removeCredentials() {
        SharedPreferences.Editor editor = userPreferences.edit();

        editor.remove("type");
        editor.remove("accessToken");
        editor.remove("refreshToken");
        editor.apply();
    }

    private void upcoming(View view) {
        Snackbar.make(view, "we are working on it.", Snackbar.LENGTH_SHORT).show();
    }


}
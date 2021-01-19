package life.nsu.foodware.views.auth;

import android.annotation.SuppressLint;
import android.content.Context;
import android.content.SharedPreferences;
import android.os.Bundle;
import android.util.Log;
import android.view.LayoutInflater;
import android.view.View;
import android.view.ViewGroup;
import android.widget.Button;
import android.widget.EditText;
import android.widget.RadioGroup;

import androidx.annotation.NonNull;
import androidx.annotation.Nullable;
import androidx.fragment.app.Fragment;

import com.google.android.material.snackbar.Snackbar;
import com.google.gson.Gson;

import org.jetbrains.annotations.NotNull;

import java.io.IOException;

import life.nsu.foodware.R;
import life.nsu.foodware.utils.networking.ServerClient;
import life.nsu.foodware.utils.networking.requests.RegistrationRequest;
import life.nsu.foodware.utils.networking.responses.RegistrationResponse;
import retrofit2.Call;
import retrofit2.Callback;
import retrofit2.Response;

public class RegisterFragment extends Fragment {

    @SuppressLint("StaticFieldLeak")
    private static RegisterFragment fragment = null;

    private EditText mEmail;
    private EditText mPassword;
    private EditText mConfirmPassword;

    RadioGroup mType;
    private Button mSignUp;

    String type = "null";
    SharedPreferences preferences;

    @Override
    public void onCreate(@Nullable Bundle savedInstanceState) {
        super.onCreate(savedInstanceState);
        preferences = getContext().getSharedPreferences("user", Context.MODE_PRIVATE);

    }

    public synchronized static RegisterFragment newInstance() {
        if(fragment == null) {
            fragment = new RegisterFragment();
        }
        return fragment;
    }

    @Override
    public View onCreateView(LayoutInflater inflater, ViewGroup container,
                             Bundle savedInstanceState) {
        // Inflate the layout for this fragment
        return inflater.inflate(R.layout.fragment_register, container, false);
    }

    @SuppressLint("NonConstantResourceId")
    @Override
    public void onViewCreated(@NonNull View view, @Nullable Bundle savedInstanceState) {
        super.onViewCreated(view, savedInstanceState);

        mEmail = view.findViewById(R.id.et_email);
        mPassword = view.findViewById(R.id.et_password);
        mConfirmPassword = view.findViewById(R.id.et_confirm_password);

        mType = view.findViewById(R.id.radioGroup);

        mSignUp = view.findViewById(R.id.btn_sign_up);


        mSignUp.setOnClickListener(v -> {

            String email = mEmail.getText().toString();
            String password = mPassword.getText().toString();
            String confirmPassword = mConfirmPassword.getText().toString();

            if (!validation(password, confirmPassword)) {
                return;
            }

//            mSignUp.setError(null);

            registration(email, password, type);
        });

        mType.setOnCheckedChangeListener((group, checkedId) -> {

//            mSignUp.setError(null);

            switch (checkedId) {
                case R.id.rb_customer:
                    type = "customer";
                    break;
                case R.id.rb_vendor:
                    type = "vendor";
                    break;
                case R.id.rb_rider:
                    type = "rider";
                    break;

                default:
                    type = "null";
                    break;
            }
        });

    }

    private void registration(String email, String password, String type) {
        Call<RegistrationResponse> registrationCall = ServerClient.getInstance().getRoute().registration(new RegistrationRequest(email, password, type));

        //ToDo
        // Create loading dialog

        registrationCall.enqueue(new Callback<RegistrationResponse>() {
            @Override
            public void onResponse(@NotNull Call<RegistrationResponse> call, @NotNull Response<RegistrationResponse> response) {
                RegistrationResponse registrationResponse;

                if(response.body() != null) {
                    registrationResponse = response.body();

                    Log.d("Request Body", "onResponse: "+registrationResponse);

                    if (response.isSuccessful()) {
                        ((AuthenticationActivity) getActivity()).selectTab(0);

                        Snackbar.make(getView(), registrationResponse.getMessage(), Snackbar.LENGTH_SHORT).show();
                    }
                } else {
                    try {
                        Gson gson = new Gson();
                        RegistrationResponse errorResponse = gson.fromJson(response.errorBody().string(), RegistrationResponse.class);
                        Snackbar.make(getView(), errorResponse.getMessage(), Snackbar.LENGTH_SHORT).show();
                    } catch (IOException e) {
                        e.printStackTrace();
                    }
                }
            }


            @Override
            public void onFailure(@NotNull Call<RegistrationResponse> call, @NotNull Throwable t) {
                Log.d("RegistrationFailed", t.getMessage());
            }
        });

        //ToDo
        // Close dialog
    }

    private boolean validation(String password, String confirmPassword) {
        if (type.equals("null")) {
            mSignUp.setError("Select type");

            return false;
        }

        if (!password.equals(confirmPassword)) {
            mConfirmPassword.setError("Not matched.");
            return false;
        }

        return password.length() >= 6;
    }

}
package life.nsu.foodware.views;

import android.content.Intent;
import android.os.Bundle;

import androidx.annotation.NonNull;
import androidx.annotation.Nullable;
import androidx.fragment.app.Fragment;

import android.view.LayoutInflater;
import android.view.View;
import android.view.ViewGroup;
import android.widget.Button;
import android.widget.EditText;
import android.widget.TextView;

import java.util.Objects;

import life.nsu.foodware.R;
import life.nsu.foodware.views.vendor.VendorHomeActivity;


public class LoginFragment extends Fragment {

    private EditText mEmail;
    private EditText mPassword;

    private TextView mForgetPassword;

    private Button mLogin;


    public static LoginFragment newInstance() {
        LoginFragment fragment = new LoginFragment();
        return fragment;
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

        mLogin.setOnClickListener(new View.OnClickListener() {
            @Override
            public void onClick(View v) {
                if (validation()) {
                    mPassword.setError("wrong password.");
                }

                //TODO
                // Call /api/auth/login
                // store refresh token, access token, user type inside SharedPreference
                // intent to rider/customer/vendor Home Activity
                activitySwitch("vendor");
            }
        });

        mForgetPassword.setOnClickListener(new View.OnClickListener() {
            @Override
            public void onClick(View v) {
                //TODO
                // Intent to forget password activity
            }
        });

    }

    private boolean validation() {
        String password = mPassword.getText().toString();

        return !mEmail.getText().toString().isEmpty() && password.length() >= 6;
    }

    private void activitySwitch(String type) {
        Intent intent = null;

        if (Objects.equals(type, "customer")) {
            //TODO
            // clear tasks
            // redirect to @CustomerHomeActivity
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
            getActivity().startActivity(intent);
        }
    }
}
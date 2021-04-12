package life.nsu.foodware.views.auth;

import android.annotation.SuppressLint;
import android.content.Context;
import android.content.Intent;
import android.content.SharedPreferences;
import android.os.Bundle;
import android.os.Handler;
import android.os.Looper;
import android.util.Log;
import android.view.LayoutInflater;
import android.view.View;
import android.view.ViewGroup;
import android.widget.Button;
import android.widget.EditText;
import android.widget.TextView;
import android.widget.Toast;

import androidx.annotation.NonNull;
import androidx.annotation.Nullable;
import androidx.fragment.app.Fragment;

import com.google.android.gms.tasks.OnCompleteListener;
import com.google.android.gms.tasks.Task;
import com.google.android.material.snackbar.Snackbar;
import com.google.firebase.auth.AuthResult;
import com.google.firebase.auth.FirebaseAuth;
import com.google.firebase.auth.FirebaseUser;

import java.util.Objects;

import life.nsu.foodware.R;
import life.nsu.foodware.utils.CustomLoadingDialog;
import life.nsu.foodware.views.customer.CustomerHomeActivity;
import life.nsu.foodware.views.vendor.VendorHomeActivity;

import static android.content.ContentValues.TAG;


public class LoginFragment extends Fragment {

    @SuppressLint("StaticFieldLeak")
    static LoginFragment fragment = null;

    private EditText mEmail;
    private EditText mPassword;

    TextView mForgetPassword;
    Button mLogin;

    private SharedPreferences preferences;
   private String userType;

    private CustomLoadingDialog loadingDialog;

    private FirebaseAuth mAuth;

    public synchronized static LoginFragment newInstance() {
        if(fragment == null) {
            fragment = new LoginFragment();
        }
        return fragment;
    }

    @Override
    public void onCreate(@Nullable Bundle savedInstanceState) {
        super.onCreate(savedInstanceState);

        preferences = getActivity().getSharedPreferences("user", Context.MODE_PRIVATE);

        mAuth = FirebaseAuth.getInstance();
        userType = preferences.getString("type", "null");

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

        mAuth.signInWithEmailAndPassword(email, password)
                .addOnCompleteListener(getActivity(), new OnCompleteListener<AuthResult>() {
                    @Override
                    public void onComplete(@NonNull Task<AuthResult> task) {
                        if (task.isSuccessful()) {
                            // Sign in success, update UI with the signed-in user's information
                            Log.d(TAG, "signInWithEmail:success");
                            FirebaseUser user = mAuth.getCurrentUser();

                            // fetch data "userType" from firebase database
                            userType = "customer";
                            updatePreference(userType);

                            activitySwitch(userType);

                        } else {
                            // If sign in fails, display a message to the user.
                            Log.w(TAG, "signInWithEmail:failure", task.getException());
                            Toast.makeText(getContext(), "Authentication failed.", Toast.LENGTH_SHORT).show();

                        }
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

    private void updatePreference(String userType) {
        preferences.edit().putString("type", userType).apply();
    }
}
package life.nsu.foodware.views.auth;

import android.annotation.SuppressLint;
import android.content.Context;
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
import android.widget.RadioGroup;
import android.widget.Toast;

import androidx.annotation.NonNull;
import androidx.annotation.Nullable;
import androidx.fragment.app.Fragment;

import com.google.android.material.snackbar.Snackbar;
import com.google.firebase.auth.FirebaseAuth;
import com.google.firebase.database.DatabaseReference;
import com.google.firebase.database.FirebaseDatabase;

import java.util.HashMap;
import java.util.Objects;

import life.nsu.foodware.R;
import life.nsu.foodware.utils.CustomLoadingDialog;

import static android.content.ContentValues.TAG;

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
    private CustomLoadingDialog loadingDialog;

    FirebaseAuth mAuth;
    FirebaseDatabase mDatabase;
    DatabaseReference mReference;

    @Override
    public void onCreate(@Nullable Bundle savedInstanceState) {
        super.onCreate(savedInstanceState);

        preferences = requireContext().getSharedPreferences("user", Context.MODE_PRIVATE);
        mAuth = FirebaseAuth.getInstance();
    }

    public synchronized static RegisterFragment newInstance() {
        if (fragment == null) {
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

        loadingDialog = new CustomLoadingDialog(getContext());

        mSignUp.setOnClickListener(v -> {

            String email = mEmail.getText().toString();
            String password = mPassword.getText().toString();
            String confirmPassword = mConfirmPassword.getText().toString();

            if (!validation(password, confirmPassword)) {
                return;
            }

//            mSignUp.setError(null);
            loadingDialog.show("");

            new Handler(Looper.myLooper()).postDelayed(() -> {
                // call firebase database
                registration(email, password, type);
            }, 250);

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
        mAuth.setLanguageCode("en");


        mAuth.createUserWithEmailAndPassword(email, password).addOnCompleteListener(requireActivity(), task -> {
            if (task.isSuccessful()) {
                // save to firebase database
                mDatabase = FirebaseDatabase.getInstance();
                mReference = mDatabase.getReference("users").child(Objects.requireNonNull(FirebaseAuth.getInstance().getUid()));

                HashMap<String, String> userMap = new HashMap<>();
                userMap.put("email", email);
                userMap.put("password", password);
                userMap.put("type", type);


                mReference.setValue(userMap).addOnCompleteListener(task2 -> {
                    if (task.isComplete()) {
                        route();
                    } else {
                        Log.w(TAG, "databaseInsert:failure", task.getException());
                    }

                    loadingDialog.hide();

                });

            } else {
                // If sign in fails, display a message to the user.
                Log.w(TAG, "signInWithEmail:failure", task.getException());
                Toast.makeText(getContext(), "Authentication failed.", Toast.LENGTH_SHORT).show();

            }
        });

    }

    private void route() {
        ((AuthenticationActivity) requireActivity()).selectTab(0);

        Snackbar.make(requireView(), "Signed up successfully!", Snackbar.LENGTH_SHORT).show();

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
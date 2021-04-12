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

import com.google.android.gms.tasks.OnCompleteListener;
import com.google.android.gms.tasks.Task;
import com.google.android.material.snackbar.Snackbar;
import com.google.firebase.auth.AuthResult;
import com.google.firebase.auth.FirebaseAuth;

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

    @Override
    public void onCreate(@Nullable Bundle savedInstanceState) {
        super.onCreate(savedInstanceState);

        preferences = Objects.requireNonNull(getContext()).getSharedPreferences("user", Context.MODE_PRIVATE);
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
            new Handler(Looper.myLooper()).postDelayed(() -> {
                registration(email, password, type);

                loadingDialog.hide();

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

        mAuth.createUserWithEmailAndPassword(email, password).addOnCompleteListener(getActivity(),  new OnCompleteListener<AuthResult>() {
            @Override
            public void onComplete(@NonNull Task<AuthResult> task) {
                if (task.isSuccessful()) {
                    ((AuthenticationActivity) Objects.requireNonNull(getActivity())).selectTab(0);

                    Snackbar.make(Objects.requireNonNull(getView()), "Signed up successfully!", Snackbar.LENGTH_SHORT).show();

                } else {
                    // If sign in fails, display a message to the user.
                    Log.w(TAG, "signInWithEmail:failure", task.getException());
                    Toast.makeText(getContext(), "Authentication failed.", Toast.LENGTH_SHORT).show();

                }
            }
        });

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
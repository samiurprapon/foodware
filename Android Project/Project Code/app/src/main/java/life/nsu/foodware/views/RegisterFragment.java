package life.nsu.foodware.views;

import android.os.Bundle;
import android.view.LayoutInflater;
import android.view.View;
import android.view.ViewGroup;
import android.widget.Button;
import android.widget.EditText;
import android.widget.RadioGroup;

import androidx.annotation.NonNull;
import androidx.annotation.Nullable;
import androidx.fragment.app.Fragment;

import life.nsu.foodware.R;

public class RegisterFragment extends Fragment {

    private EditText mEmail;
    private EditText mPassword;
    private EditText mConfirmPassword;

    private RadioGroup mType;
    private Button mSignUp;

    String type = "null";

    public static RegisterFragment newInstance() {
        RegisterFragment fragment = new RegisterFragment();
        return fragment;
    }

    @Override
    public View onCreateView(LayoutInflater inflater, ViewGroup container,
                             Bundle savedInstanceState) {
        // Inflate the layout for this fragment
        return inflater.inflate(R.layout.fragment_register, container, false);
    }

    @Override
    public void onViewCreated(@NonNull View view, @Nullable Bundle savedInstanceState) {
        super.onViewCreated(view, savedInstanceState);

        mEmail = view.findViewById(R.id.et_email);
        mPassword = view.findViewById(R.id.et_password);
        mConfirmPassword = view.findViewById(R.id.et_confirm_password);

        mType = view.findViewById(R.id.radioGroup);

        mSignUp = view.findViewById(R.id.btn_sign_up);


        mSignUp.setOnClickListener(new View.OnClickListener() {
            @Override
            public void onClick(View v) {

                String email = mEmail.getText().toString();
                String password = mPassword.getText().toString();
                String confirmPassword = mConfirmPassword.getText().toString();

                if (!validation(email, password, confirmPassword)) {
                    return;
                }

                mSignUp.setError(null);

//                Toast.makeText(getContext(), ""+type, Toast.LENGTH_SHORT).show();

                //ToDo
                // call /api/auth/register, body: email, password, type
                // store refresh token, access token, user type inside SharedPreference
                // switch to fragment @LoginFragment
            }
        });

        mType.setOnCheckedChangeListener(new RadioGroup.OnCheckedChangeListener() {
            @Override
            public void onCheckedChanged(RadioGroup group, int checkedId) {

                mSignUp.setError(null);

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
            }
        });

    }

    private boolean validation(String email, String password, String confirmPassword) {
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
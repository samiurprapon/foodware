package life.nsu.signapplication;

import androidx.appcompat.app.AppCompatActivity;

import android.os.Bundle;
import android.view.View;
import android.widget.Button;
import android.widget.EditText;
import android.widget.Toast;

public class MainActivity extends AppCompatActivity {

    EditText mUsername;
    EditText mPassword;

    Button mLogin;

    @Override
    protected void onCreate(Bundle savedInstanceState) {
        super.onCreate(savedInstanceState);
        setContentView(R.layout.activity_main);

        mUsername = findViewById(R.id.et_username);
        mPassword = findViewById(R.id.et_password);
        mLogin = findViewById(R.id.btn_login);
        
        mLogin.setOnClickListener(v -> {

            String username = mUsername.getText().toString().trim();
            String password =   mPassword.getText().toString().trim();

            if(username.equals("sami") && password.equals("123456")) {
                Toast.makeText(MainActivity.this, "Successfully Logged In.", Toast.LENGTH_LONG).show();
            } else {
                Toast.makeText(MainActivity.this, "Login failed", Toast.LENGTH_LONG).show();
            }
        });
    }
}
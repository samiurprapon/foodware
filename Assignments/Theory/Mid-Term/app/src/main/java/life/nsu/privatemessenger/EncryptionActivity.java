package life.nsu.privatemessenger;

import android.content.Intent;
import android.os.Bundle;
import android.widget.Button;
import android.widget.EditText;
import android.widget.TextView;

import androidx.appcompat.app.AppCompatActivity;

public class EncryptionActivity extends AppCompatActivity {

    TextView mDecryption;
    Button mEncrypt;

    EditText mPhone;
    EditText mSecret;
    EditText mMessage;

    private CaesarCipher caesarCipher;

    @Override
    protected void onCreate(Bundle savedInstanceState) {
        super.onCreate(savedInstanceState);
        overridePendingTransition(android.R.anim.fade_in, android.R.anim.fade_out);
        setContentView(R.layout.activity_encryption);

        mDecryption = findViewById(R.id.tv_decryption);
        mEncrypt = findViewById(R.id.btn_encrypt);

        mPhone = findViewById(R.id.et_phone);
        mSecret = findViewById(R.id.et_secret);
        mMessage = findViewById(R.id.et_message_body);

        caesarCipher = new CaesarCipher();

        mDecryption.setOnClickListener(v -> {
            Intent intent = new Intent(EncryptionActivity.this, DecryptionActivity.class);
            startActivity(intent);
        });

        mEncrypt.setOnClickListener(view -> {
            mEncrypt.setError(null);
            String phoneNumber = mPhone.getText().toString();
            String message = mMessage.getText().toString();
            String secret = mSecret.getText().toString();

            // validation user input
            if(phoneNumber.isEmpty() || message.isEmpty() || secret.isEmpty()) {
                mEncrypt.setError("");
                 return;
            }

            //ToDo
            // Call method to encrypt
            int secretCode = secret.hashCode() % 26;
            String cypherText = caesarCipher.encrypt(message, secretCode);

            Intent intent = new Intent(EncryptionActivity.this, FurtherActivity.class);
            intent.putExtra("phoneNumber", phoneNumber);
            intent.putExtra("secret", secretCode);
            intent.putExtra("cypher", cypherText);
            startActivity(intent);
        });
    }

    @Override
    public void onBackPressed() {
        overridePendingTransition(android.R.anim.fade_out, android.R.anim.fade_in);
        super.onBackPressed();
    }


}
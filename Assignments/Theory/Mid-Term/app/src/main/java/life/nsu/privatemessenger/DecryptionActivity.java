package life.nsu.privatemessenger;

import android.content.Intent;
import android.os.Bundle;
import android.widget.Button;
import android.widget.EditText;
import android.widget.TextView;

import androidx.appcompat.app.AppCompatActivity;

public class DecryptionActivity extends AppCompatActivity {

    TextView mEncryption;
    TextView mOutput;
    EditText mCypher;
    Button mDecrypt;

    CaesarCipher caesarCipher;

    @Override
    protected void onCreate(Bundle savedInstanceState) {
        super.onCreate(savedInstanceState);
        overridePendingTransition(android.R.anim.fade_in, android.R.anim.fade_out);

        setContentView(R.layout.activity_decryption);

        mEncryption = findViewById(R.id.tv_encryption);
        mOutput = findViewById(R.id.tv_output);
        mCypher = findViewById(R.id.et_message_body);
        mDecrypt = findViewById(R.id.btn_decrypt);

        caesarCipher = new CaesarCipher();

        mEncryption.setOnClickListener(v -> {
            Intent intent = new Intent(DecryptionActivity.this, EncryptionActivity.class);
            startActivity(intent);
        });

        mDecrypt.setOnClickListener(v -> {
            // tokenize the message into secret and cypher text
            // call the decrypt method
            // setText of @mOutput
            // make visible mOutput
            String cypherText = mCypher.getText().toString();

            if(!cypherText.isEmpty()) {
                String deCypherMessage = caesarCipher.decrypt(cypherText);

                mCypher.setError(null);
                mOutput.setText(deCypherMessage);
            } else {
                mOutput.setText("");
                mCypher.setError("empty!");
            }
        });
    }

    @Override
    public void onBackPressed() {
        super.onBackPressed();
        overridePendingTransition(android.R.anim.fade_out, android.R.anim.fade_in);
    }
}
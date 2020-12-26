package life.nsu.privatemessenger;

import android.annotation.SuppressLint;
import android.content.Intent;
import android.net.Uri;
import android.os.Bundle;
import android.widget.Button;
import android.widget.TextView;

import androidx.appcompat.app.AppCompatActivity;

public class FurtherActivity extends AppCompatActivity {

    TextView mCypher;
    Button mSend;

    @SuppressLint("SetTextI18n")
    @Override
    protected void onCreate(Bundle savedInstanceState) {
        super.onCreate(savedInstanceState);
        overridePendingTransition(android.R.anim.fade_in, android.R.anim.fade_out);

        setContentView(R.layout.activity_further);

        mCypher = findViewById(R.id.tv_cypher_text);
        mSend = findViewById(R.id.btn_sms_send);

        String phoneNumber = getIntent().getStringExtra("phoneNumber");
        String message = getIntent().getStringExtra("cypher");
        int secretKey = getIntent().getIntExtra("secret", 0);

        mCypher.setText(secretKey+" "+message);


        mSend.setOnClickListener(view -> {
            //ToDo
            // intent to sms activity
//            Log.d("Activity", "phone: "+phoneNumber+"\n message: "+message+"\n secret: "+secretKey);
            startSMSIntent(phoneNumber, secretKey+" "+message);
        });
    }

    @Override
    public void onBackPressed() {
        overridePendingTransition(android.R.anim.fade_out, android.R.anim.fade_in);
        super.onBackPressed();
    }

    private void startSMSIntent(String phoneNumber, String message) {
        try {

            Intent intent = new Intent(Intent.ACTION_SENDTO);
            // This ensures only SMS apps respond
            intent.setData(Uri.parse("smsto:"+phoneNumber));
            intent.putExtra("sms_body", message);
            startActivity(intent);
        } catch (Exception e) {
//            Log.d("ERROR", e.getMessage());
        }
    }
}
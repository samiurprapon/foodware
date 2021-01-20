package life.nsu.foodware.views.vendor.profile;

import androidx.appcompat.app.AppCompatActivity;

import android.os.Bundle;
import android.widget.EditText;

import de.hdodenhof.circleimageview.CircleImageView;
import life.nsu.foodware.R;

public class CreateVendorProfileActivity extends AppCompatActivity {

    CircleImageView mLogo;

    EditText mRestaurantName;
    EditText mOwnerName;
    EditText mPhoneNumber;
    EditText mBkash;

    EditText mOpeningAt;
    EditText mClosingAt;

    @Override
    protected void onCreate(Bundle savedInstanceState) {
        super.onCreate(savedInstanceState);
        setContentView(R.layout.activity_create_vendor_profile);

        mLogo = findViewById(R.id.img_logo);
        mRestaurantName = findViewById(R.id.et_restaurant_name);
        mOwnerName = findViewById(R.id.et_owner_name);
        mPhoneNumber = findViewById(R.id.et_owner_phone_number);
        mBkash = findViewById(R.id.et_bkash_number);

        mOpeningAt = findViewById(R.id.et_opening);
        mClosingAt = findViewById(R.id.et_closing);
    }
}
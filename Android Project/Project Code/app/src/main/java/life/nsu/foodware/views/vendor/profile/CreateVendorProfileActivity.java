package life.nsu.foodware.views.vendor.profile;

import android.Manifest;
import android.content.Intent;
import android.content.pm.PackageManager;
import android.net.Uri;
import android.os.Bundle;
import android.provider.Settings;
import android.widget.EditText;
import android.widget.TextView;
import android.widget.Toast;

import androidx.appcompat.app.AlertDialog;
import androidx.appcompat.app.AppCompatActivity;
import androidx.core.app.ActivityCompat;
import androidx.core.content.ContextCompat;

import com.karumi.dexter.Dexter;
import com.karumi.dexter.MultiplePermissionsReport;
import com.karumi.dexter.PermissionToken;
import com.karumi.dexter.listener.PermissionRequest;
import com.karumi.dexter.listener.multi.MultiplePermissionsListener;

import java.util.List;

import de.hdodenhof.circleimageview.CircleImageView;
import life.nsu.foodware.R;
import life.nsu.foodware.utils.GpsTracker;

public class CreateVendorProfileActivity extends AppCompatActivity {

    CircleImageView mLogo;

    EditText mRestaurantName;
    EditText mOwnerName;
    EditText mPhoneNumber;
    EditText mBkash;
    TextView mLocation;

    EditText mOpeningAt;
    EditText mClosingAt;

    GpsTracker gpsTracker;

    @Override
    protected void onCreate(Bundle savedInstanceState) {
        super.onCreate(savedInstanceState);
        overridePendingTransition(android.R.anim.fade_in, android.R.anim.fade_out);
        setContentView(R.layout.activity_create_vendor_profile);

        mLogo = findViewById(R.id.img_logo);
        mRestaurantName = findViewById(R.id.et_restaurant_name);
        mOwnerName = findViewById(R.id.et_owner_name);
        mPhoneNumber = findViewById(R.id.et_owner_phone_number);
        mBkash = findViewById(R.id.et_bkash_number);
        mLocation = findViewById(R.id.tv_location);

        mOpeningAt = findViewById(R.id.et_opening);
        mClosingAt = findViewById(R.id.et_closing);

        requestPermission();

        try {
            if (ContextCompat.checkSelfPermission(getApplicationContext(), android.Manifest.permission.ACCESS_FINE_LOCATION) != PackageManager.PERMISSION_GRANTED ) {
                ActivityCompat.requestPermissions(this, new String[]{android.Manifest.permission.ACCESS_FINE_LOCATION}, 101);
            } else {
                getLocation();
            }
        } catch (Exception e){
            e.printStackTrace();
            requestPermission();

        }

    }

    public void getLocation(){
        gpsTracker = new GpsTracker(this);
        if(gpsTracker.canGetLocation()){
            double latitude = gpsTracker.getLatitude();
            double longitude = gpsTracker.getLongitude();

            String location = latitude +", "+ longitude;
            mLocation.setText(location);
        }else{
            gpsTracker.showSettingsAlert();
        }
    }

    private void requestPermission() {
        Dexter.withContext(this)
                .withPermissions(Manifest.permission.READ_EXTERNAL_STORAGE, Manifest.permission.ACCESS_FINE_LOCATION, Manifest.permission.ACCESS_COARSE_LOCATION)
                .withListener(new MultiplePermissionsListener() {
                    @Override
                    public void onPermissionsChecked(MultiplePermissionsReport multiplePermissionsReport) {
                        if(!multiplePermissionsReport.areAllPermissionsGranted()) {
                            showSettingsDialog();
                        }

                    }

                    @Override
                    public void onPermissionRationaleShouldBeShown(List<PermissionRequest> list, PermissionToken permissionToken) {
                        permissionToken.continuePermissionRequest();
                    }
                })
                .withErrorListener(dexterError -> Toast.makeText(CreateVendorProfileActivity.this, "Error occurred"+dexterError.toString(), Toast.LENGTH_SHORT).show())
                .check();
    }

    private void showSettingsDialog() {
        AlertDialog.Builder builder = new AlertDialog.Builder(this, R.style.CustomAlertDialog);
        builder.setTitle("Storage Permission");

        builder.setMessage("Storage Permission is needed to select your profile picture");
        builder.setPositiveButton("OPEN SETTINGS", (dialog, which) -> {
            dialog.cancel();
            openSettings();
        });

        builder.setNegativeButton("Cancel", (dialog, which) -> dialog.cancel());

        builder.setCancelable(false);
        builder.show();

    }


    // navigating user to app settings
    private void openSettings() {
        Intent intent = new Intent(Settings.ACTION_APPLICATION_DETAILS_SETTINGS);
        Uri uri = Uri.fromParts("package", getPackageName(), null);
        intent.setData(uri);
        startActivityForResult(intent, 101);
    }

    @Override
    protected void onDestroy() {
        super.onDestroy();
        gpsTracker.stopUsingGPS();
    }
}
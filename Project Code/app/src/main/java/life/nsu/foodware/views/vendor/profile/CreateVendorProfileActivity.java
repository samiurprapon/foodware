package life.nsu.foodware.views.vendor.profile;

import android.Manifest;
import android.content.Context;
import android.content.Intent;
import android.content.SharedPreferences;
import android.content.pm.PackageManager;
import android.net.Uri;
import android.os.Bundle;
import android.os.Handler;
import android.os.Looper;
import android.provider.Settings;
import android.util.Log;
import android.widget.Button;
import android.widget.EditText;
import android.widget.TextView;
import android.widget.Toast;

import androidx.appcompat.app.AlertDialog;
import androidx.appcompat.app.AppCompatActivity;
import androidx.core.app.ActivityCompat;
import androidx.core.content.ContextCompat;

import com.google.firebase.auth.FirebaseAuth;
import com.google.firebase.database.DatabaseReference;
import com.google.firebase.database.FirebaseDatabase;
import com.google.firebase.storage.FirebaseStorage;
import com.google.firebase.storage.StorageReference;
import com.karumi.dexter.Dexter;
import com.karumi.dexter.MultiplePermissionsReport;
import com.karumi.dexter.PermissionToken;
import com.karumi.dexter.listener.PermissionRequest;
import com.karumi.dexter.listener.multi.MultiplePermissionsListener;
import com.theartofdev.edmodo.cropper.CropImage;
import com.theartofdev.edmodo.cropper.CropImageView;

import java.util.List;
import java.util.Objects;

import am.appwise.components.ni.NoInternetDialog;
import de.hdodenhof.circleimageview.CircleImageView;
import life.nsu.foodware.R;
import life.nsu.foodware.models.Location;
import life.nsu.foodware.models.Restaurant;
import life.nsu.foodware.utils.Constants;
import life.nsu.foodware.utils.CustomLoadingDialog;
import life.nsu.foodware.utils.GpsTracker;
import life.nsu.foodware.views.vendor.VendorHomeActivity;

public class CreateVendorProfileActivity extends AppCompatActivity {

    CircleImageView mLogo;

    private EditText mRestaurantName;
    private EditText mOwnerName;
    private EditText mPhoneNumber;
    private EditText mBkash;
    private TextView mLocation;

    private EditText mOpeningAt;
    private EditText mClosingAt;

    Button mCreate;

    private GpsTracker gpsTracker;
    private CustomLoadingDialog loadingDialog;

    NoInternetDialog noInternetDialog;
    Location location;

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
        mCreate = findViewById(R.id.btn_create);

        mOpeningAt = findViewById(R.id.et_opening);
        mClosingAt = findViewById(R.id.et_closing);

        noInternetDialog = new NoInternetDialog.Builder(this).build();
        loadingDialog = new CustomLoadingDialog(this);

        requestPermission();

        try {
            if (ContextCompat.checkSelfPermission(getApplicationContext(), android.Manifest.permission.ACCESS_FINE_LOCATION) != PackageManager.PERMISSION_GRANTED) {
                ActivityCompat.requestPermissions(this, new String[]{android.Manifest.permission.ACCESS_FINE_LOCATION}, 101);
            } else {
                getLocation();
            }
        } catch (Exception e) {
            e.printStackTrace();
            requestPermission();

        }

        mLogo.setOnClickListener(v -> {
            try {
                if (ContextCompat.checkSelfPermission(getApplicationContext(), Manifest.permission.READ_EXTERNAL_STORAGE) != PackageManager.PERMISSION_GRANTED) {
                    ActivityCompat.requestPermissions(CreateVendorProfileActivity.this, new String[]{Manifest.permission.READ_EXTERNAL_STORAGE}, 101);
                } else {
                    chooseImageFromGallery();
                }
            } catch (Exception e) {
                e.printStackTrace();
                requestPermission();

            }
        });

        mCreate.setOnClickListener(v -> {

            loadingDialog.show("");

            new Handler(Looper.myLooper()).postDelayed(() -> {
//            validation

                if (!isValidated()) {
                    return;
                }

                getUserDetails();
            }, 100);

        });


    }

    private boolean isValidated() {
        return !mOwnerName.getText().toString().trim().isEmpty() && !mRestaurantName.getText().toString().trim().isEmpty() && !mPhoneNumber.getText().toString().trim().isEmpty() ||
                !mBkash.getText().toString().trim().isEmpty();
    }

    public void getLocation() {
        gpsTracker = new GpsTracker(this);

        if (gpsTracker.canGetLocation()) {
            double latitude = gpsTracker.getLatitude();
            double longitude = gpsTracker.getLongitude();

            mLocation.setText(latitude + ", " + longitude);
        } else {
            gpsTracker.showSettingsAlert();
        }
    }

    public void chooseImageFromGallery() {
        CropImage.activity()
                .setAspectRatio(1, 1)
                .setCropShape(CropImageView.CropShape.OVAL)
                .setAutoZoomEnabled(true)
                .setGuidelines(CropImageView.Guidelines.ON)
                .start(this);
    }

    @Override
    public void onActivityResult(int requestCode, int resultCode, Intent data) {
        super.onActivityResult(requestCode, resultCode, data);

        if (requestCode == CropImage.CROP_IMAGE_ACTIVITY_REQUEST_CODE) {
            CropImage.ActivityResult result = CropImage.getActivityResult(data);
            if (resultCode == RESULT_OK) {
                mLogo.setImageURI(result.getUri());
                mLogo.setAlpha((float) 0.15);

                uploadLogo(result.getUri());


            } else if (resultCode == CropImage.CROP_IMAGE_ACTIVITY_RESULT_ERROR_CODE) {
                Log.e("ImageCrop", Objects.requireNonNull(result.getError().getMessage()));
            }
        }
    }

    private void uploadLogo(Uri uri) {
        loadingDialog.show("");

        StorageReference logoReference = FirebaseStorage.getInstance().getReference(Constants.RESTAURANT_LOGO).child(FirebaseAuth.getInstance().getUid());
        DatabaseReference vendorReference = FirebaseDatabase.getInstance().getReference(Constants.VENDOR_TABLE).child(FirebaseAuth.getInstance().getUid()).child(Constants.LOGO);

        logoReference.putFile(uri).addOnSuccessListener(taskSnapshot -> {
            // get url to put on firebase database
            taskSnapshot.getStorage().getDownloadUrl().addOnCompleteListener(task -> {
                String logoLink = task.getResult().toString();

                vendorReference.setValue(logoLink).addOnCompleteListener(task1 -> {
                    if (task1.isSuccessful()) {

                        Restaurant restaurant = new Restaurant();
                        restaurant.setLogo(logoLink);
                        setSharedPreferences(restaurant);

                        mLogo.setAlpha((float) 1.0);
                        loadingDialog.hide();
                    } else {
                        //
                        loadingDialog.hide();
                        Toast.makeText(this, "upload Failed!", Toast.LENGTH_SHORT).show();
                    }
                });

            });
        });

        loadingDialog.hide();

    }

    private void getUserDetails() {
        String name = mRestaurantName.getText().toString().trim();
        String ownerName = mOwnerName.getText().toString().trim();
        String phone = mPhoneNumber.getText().toString().trim();
        String bkash = mBkash.getText().toString().trim();
        String openingAt = mOpeningAt.getText().toString().trim();
        String closingAt = mClosingAt.getText().toString().trim();

        if (openingAt == null) {
            openingAt = "7:00";
        }

        if (closingAt == null) {
            closingAt = "7:00";
        }

        String timestamps = String.valueOf(System.currentTimeMillis());
        location = new Location(gpsTracker.getLongitude(), gpsTracker.getLatitude(), timestamps);

        Log.d("timestamps", timestamps);
        Restaurant restaurant = new Restaurant(name, ownerName, phone, bkash, Constants.RESTAURANT_OFF, location, openingAt, closingAt);
        createRestaurant(restaurant);

    }

    private void createRestaurant(Restaurant restaurant) {
        DatabaseReference vendorReference = FirebaseDatabase.getInstance().getReference(Constants.VENDOR_TABLE).child(FirebaseAuth.getInstance().getUid());

        vendorReference.setValue(restaurant).addOnCompleteListener(task2 -> {

            if (task2.isSuccessful()) {
                loadingDialog.hide();
                setSharedPreferences(restaurant);
                route();
            } else {
                Toast.makeText(this, "Failed to create Account", Toast.LENGTH_SHORT).show();
                loadingDialog.hide();
            }

        });

    }

    private void route() {
        loadingDialog.hide();

        Intent intent = new Intent(CreateVendorProfileActivity.this, VendorHomeActivity.class);
        intent.addFlags(Intent.FLAG_ACTIVITY_CLEAR_TASK | Intent.FLAG_ACTIVITY_NEW_TASK);
        getApplication().startActivity(intent);
    }

    private void requestPermission() {
        Dexter.withContext(this)
                .withPermissions(Manifest.permission.READ_EXTERNAL_STORAGE, Manifest.permission.ACCESS_FINE_LOCATION, Manifest.permission.ACCESS_COARSE_LOCATION)
                .withListener(new MultiplePermissionsListener() {
                    @Override
                    public void onPermissionsChecked(MultiplePermissionsReport multiplePermissionsReport) {
                        if (!multiplePermissionsReport.areAllPermissionsGranted()) {
                            showSettingsDialog();
                        }

                    }

                    @Override
                    public void onPermissionRationaleShouldBeShown(List<PermissionRequest> list, PermissionToken permissionToken) {
                        permissionToken.continuePermissionRequest();
                    }
                })
                .withErrorListener(dexterError -> Toast.makeText(CreateVendorProfileActivity.this, "Error occurred" + dexterError.toString(), Toast.LENGTH_SHORT).show())
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

    // set shared preference
    public void setSharedPreferences(Restaurant restaurant) {
        SharedPreferences.Editor editor = getSharedPreferences("restaurant", Context.MODE_PRIVATE).edit();

        if(restaurant.getLogo() != null) {
            editor.putString("logo", restaurant.getLogo());
        }

        if(restaurant.getName() != null) {
            editor.putString("name", restaurant.getName());
        }

        if(restaurant.getOwnerName() != null) {
            editor.putString("owner", restaurant.getOwnerName());
        }

        if(restaurant.getBkash() != null) {
            editor.putString("bkash", restaurant.getBkash());
        }

        if(restaurant.getStatus() != null) {
            editor.putString("status", restaurant.getStatus());
        }

        if(restaurant.getLocation() != null) {
            editor.putString("latitude", String.valueOf(restaurant.getLocation().getLatitude()));
            editor.putString("longitude", String.valueOf(restaurant.getLocation().getLongitude()));
            editor.putString("timestamps", String.valueOf(restaurant.getLocation().getTimestamps()));
        }

        if(restaurant.getOpeningAt() != null) {
            editor.putString("opening", restaurant.getOpeningAt());
            editor.putString("closing", restaurant.getClosingAt());
        }

        editor.apply();
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
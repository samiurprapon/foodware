package life.nsu.foodware.views.vendor.profile;

import android.Manifest;
import android.content.Context;
import android.content.Intent;
import android.content.SharedPreferences;
import android.content.pm.PackageManager;
import android.net.Uri;
import android.os.Bundle;
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

import com.karumi.dexter.Dexter;
import com.karumi.dexter.MultiplePermissionsReport;
import com.karumi.dexter.PermissionToken;
import com.karumi.dexter.listener.PermissionRequest;
import com.karumi.dexter.listener.multi.MultiplePermissionsListener;
import com.theartofdev.edmodo.cropper.CropImage;
import com.theartofdev.edmodo.cropper.CropImageView;

import org.jetbrains.annotations.NotNull;

import java.io.File;
import java.util.List;
import java.util.Objects;

import am.appwise.components.ni.NoInternetDialog;
import de.hdodenhof.circleimageview.CircleImageView;
import life.nsu.foodware.R;
import life.nsu.foodware.models.Restaurant;
import life.nsu.foodware.utils.CustomLoadingDialog;
import life.nsu.foodware.utils.GpsTracker;
import life.nsu.foodware.utils.networking.ServerClient;
import life.nsu.foodware.utils.networking.responses.MessageResponse;
import life.nsu.foodware.utils.networking.responses.RestaurantResponse;
import life.nsu.foodware.views.vendor.VendorHomeActivity;
import okhttp3.MediaType;
import okhttp3.MultipartBody;
import okhttp3.RequestBody;
import retrofit2.Call;
import retrofit2.Callback;
import retrofit2.Response;

public class CreateVendorProfileActivity extends AppCompatActivity {

    CircleImageView mLogo;

    EditText mRestaurantName;
    EditText mOwnerName;
    EditText mPhoneNumber;
    EditText mBkash;
    TextView mLocation;

    EditText mOpeningAt;
    EditText mClosingAt;

    Button mCreate;

    SharedPreferences preferences;
    SharedPreferences restaurantPreference;
    GpsTracker gpsTracker;
    NoInternetDialog noInternetDialog;
    private CustomLoadingDialog loadingDialog;

    String accessToken;
    String location;

    @Override
    protected void onCreate(Bundle savedInstanceState) {
        super.onCreate(savedInstanceState);
        overridePendingTransition(android.R.anim.fade_in, android.R.anim.fade_out);
        setContentView(R.layout.activity_create_vendor_profile);

        preferences = getApplication().getApplicationContext().getSharedPreferences("user", Context.MODE_PRIVATE);
        restaurantPreference = getApplication().getApplicationContext().getSharedPreferences("restaurant", Context.MODE_PRIVATE);

        accessToken = preferences.getString("accessToken", "null");
        location = "null";

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
            if (ContextCompat.checkSelfPermission(getApplicationContext(), android.Manifest.permission.ACCESS_FINE_LOCATION) != PackageManager.PERMISSION_GRANTED ) {
                ActivityCompat.requestPermissions(this, new String[]{android.Manifest.permission.ACCESS_FINE_LOCATION}, 101);
            } else {
                getLocation();
            }
        } catch (Exception e){
            e.printStackTrace();
            requestPermission();

        }

        mLogo.setOnClickListener(v -> {
            try {
                if (ContextCompat.checkSelfPermission(getApplicationContext(), Manifest.permission.READ_EXTERNAL_STORAGE) != PackageManager.PERMISSION_GRANTED ) {
                    ActivityCompat.requestPermissions(CreateVendorProfileActivity.this, new String[]{Manifest.permission.READ_EXTERNAL_STORAGE}, 101);
                } else {
                    chooseImageFromGallery();
                }
            } catch (Exception e){
                e.printStackTrace();
                requestPermission();

            }
        });

        mCreate.setOnClickListener(v -> {
            if(!isValidated()) {
                return;
            }

            createRestaurant();
        });

    }

    private boolean isValidated() {
        return !mOwnerName.getText().toString().trim().isEmpty() && !mRestaurantName.getText().toString().trim().isEmpty() && !mPhoneNumber.getText().toString().trim().isEmpty() ||
                !mBkash.getText().toString().trim().isEmpty() && !mOpeningAt.getText().toString().trim().isEmpty() && !mClosingAt.getText().toString().trim().isEmpty();
    }

    public void getLocation(){
        gpsTracker = new GpsTracker(this);
        if(gpsTracker.canGetLocation()){
            double latitude = gpsTracker.getLatitude();
            double longitude = gpsTracker.getLongitude();

            location = latitude +", "+ longitude;
            mLocation.setText(location);
        }else{
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
        loadingDialog.show();
        File file = new File(uri.getPath());
        RequestBody reqFile = RequestBody.create(MediaType.parse("image"), file);
        MultipartBody.Part body = MultipartBody.Part.createFormData("photo", file.getName(), reqFile);

        // call server to upload
        Call<MessageResponse> call = ServerClient.getInstance().getRoute().uploadLogo(accessToken, body);

        call.enqueue(new Callback<MessageResponse>() {
            @Override
            public void onResponse(@NotNull Call<MessageResponse> call, @NotNull Response<MessageResponse> response) {
                if(response.isSuccessful()) {
                    if(response.body() != null) {
                        Toast.makeText(CreateVendorProfileActivity.this, response.body().getMessage(), Toast.LENGTH_SHORT).show();
                        mLogo.setAlpha((float) 1.0);

                    }
                }

            }

            @Override
            public void onFailure(@NotNull Call<MessageResponse> call, @NotNull Throwable t) {

            }
        });

        loadingDialog.hide();

    }

    private void createRestaurant() {
        loadingDialog.show();

        Restaurant restaurant = new Restaurant(mRestaurantName.getText().toString().trim(), mOwnerName.getText().toString().trim(),
                            mPhoneNumber.getText().toString().trim(), mBkash.getText().toString().trim(), "open", location, mOpeningAt.getText().toString(), mClosingAt.getText().toString()) ;

        Call<RestaurantResponse> call = ServerClient.getInstance().getRoute().createRestaurant(accessToken, restaurant);

        call.enqueue(new Callback<RestaurantResponse>() {
            @Override
            public void onResponse(@NotNull Call<RestaurantResponse> call, @NotNull Response<RestaurantResponse> response) {
                if(response.isSuccessful()) {
                    if(response.body() != null) {
                        Restaurant restaurant = response.body().getRestaurant();

                        saveOnSharedPreference(restaurant);

                        route();
                    }
                }
            }

            @Override
            public void onFailure(@NotNull Call<RestaurantResponse> call, @NotNull Throwable t) {
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

    private void saveOnSharedPreference(Restaurant restaurant) {
        SharedPreferences.Editor  editor= restaurantPreference.edit();

        editor.putString("photo", restaurant.getName());
        editor.putString("name", restaurant.getName());
        editor.putString("ownerName", restaurant.getName());
        editor.putString("phone", restaurant.getName());
        editor.putString("bkash", restaurant.getName());
        editor.putString("status", restaurant.getName());
        editor.putString("location", restaurant.getName());

        editor.apply();

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
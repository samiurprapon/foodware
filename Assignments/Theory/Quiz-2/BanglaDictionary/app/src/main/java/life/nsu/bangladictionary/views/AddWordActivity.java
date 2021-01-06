package life.nsu.bangladictionary.views;

import android.Manifest;
import android.content.Intent;
import android.content.pm.PackageManager;
import android.net.Uri;
import android.os.Bundle;
import android.provider.Settings;
import android.util.Log;
import android.widget.Button;
import android.widget.EditText;
import android.widget.Toast;

import androidx.appcompat.app.AlertDialog;
import androidx.appcompat.app.AppCompatActivity;
import androidx.core.content.ContextCompat;

import com.karumi.dexter.Dexter;
import com.karumi.dexter.PermissionToken;
import com.karumi.dexter.listener.PermissionDeniedResponse;
import com.karumi.dexter.listener.PermissionGrantedResponse;
import com.karumi.dexter.listener.PermissionRequest;
import com.karumi.dexter.listener.single.PermissionListener;
import com.theartofdev.edmodo.cropper.CropImage;
import com.theartofdev.edmodo.cropper.CropImageView;

import java.util.ArrayList;
import java.util.Objects;

import de.hdodenhof.circleimageview.CircleImageView;
import io.paperdb.Paper;
import life.nsu.bangladictionary.R;
import life.nsu.bangladictionary.models.Vocabulary;

public class AddWordActivity extends AppCompatActivity {

    CircleImageView mImage;

    private EditText mEnglish;
    private EditText mBangla;
    Button mInsert;

    private Uri imageUri;
    private Vocabulary vocabulary;
    ArrayList<Vocabulary> wordList;


    @Override
    protected void onCreate(Bundle savedInstanceState) {
        super.onCreate(savedInstanceState);
        overridePendingTransition(R.anim.slide_in_right, R.anim.slide_out_left);
        setContentView(R.layout.activity_add_word);

        mImage = findViewById(R.id.circleImageView);
        mEnglish = findViewById(R.id.et_english);
        mBangla = findViewById(R.id.et_bangla);
        mInsert = findViewById(R.id.btn_insert);

        Paper.init(this);
        wordList = Paper.book().read("words", new ArrayList<>());

        if(getIntent().getStringExtra("english") != null ) {
            mEnglish.setText(getIntent().getStringExtra("english"));
        }

        mInsert.setOnClickListener(view -> {
            if(!mEnglish.getText().toString().isEmpty() && !mBangla.getText().toString().isEmpty()) {
                String english = mEnglish.getText().toString();
                String bangla = mBangla.getText().toString();

                if(imageUri == null) {
                    vocabulary = new Vocabulary(english, bangla);
                } else {
                    vocabulary = new Vocabulary(imageUri, english, bangla);
                }

                wordList.add(vocabulary);

                // insert data into paperDB
                Paper.book().write("words", wordList);

                // intent to previous activity
                Intent intent = new Intent(AddWordActivity.this, MainActivity.class);
                intent.setFlags(Intent.FLAG_ACTIVITY_NEW_TASK | Intent.FLAG_ACTIVITY_CLEAR_TASK);
                Toast.makeText(this, "New word added!", Toast.LENGTH_LONG).show();
                startActivity(intent);


            }
        });

        mImage.setOnClickListener(view -> {
            if (ContextCompat.checkSelfPermission(AddWordActivity.this, Manifest.permission.READ_EXTERNAL_STORAGE) == PackageManager.PERMISSION_GRANTED) {
                chooseImageFromGallery();
            } else {
                requestPermission();
            }
        });

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

                //Store image ui inside paperDb
                imageUri = result.getUri();
                mImage.setImageURI(imageUri);

            } else if (resultCode == CropImage.CROP_IMAGE_ACTIVITY_RESULT_ERROR_CODE) {
                Log.e("ImageCrop", Objects.requireNonNull(result.getError().getMessage()));
            }
        }
    }

    private void requestPermission() {

        Dexter.withContext(this)
                .withPermission(Manifest.permission.READ_EXTERNAL_STORAGE)
                .withListener(new PermissionListener() {
                    @Override
                    public void onPermissionGranted(PermissionGrantedResponse response) {
                        Toast.makeText(AddWordActivity.this, "Permission is now granted!", Toast.LENGTH_SHORT).show();
                        chooseImageFromGallery();

                    }

                    @Override
                    public void onPermissionDenied(PermissionDeniedResponse response) {
                        showSettingsDialog();
                    }

                    @Override
                    public void onPermissionRationaleShouldBeShown(PermissionRequest permission, PermissionToken token) {
                        token.continuePermissionRequest();
                    }
                }).check();
    }

    private void showSettingsDialog() {
        AlertDialog.Builder builder = new AlertDialog.Builder(this, R.style.CustomDialog);
        builder.setTitle("Storage Permission");

        builder.setMessage("Storage Permission is needed to select your profile picture");
        builder.setPositiveButton("OPEN SETTINGS", (dialog, which) -> {
            dialog.cancel();
            openSettings();
        });

        builder.setNegativeButton("Cancel", (dialog, which) -> dialog.cancel());

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
    public void onBackPressed() {
        super.onBackPressed();
        overridePendingTransition(R.anim.slide_in_left, R.anim.slide_out_right);
    }
}
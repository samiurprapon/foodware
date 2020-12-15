package life.nsu.foodware;

import androidx.appcompat.app.AppCompatActivity;
import androidx.lifecycle.ViewModel;
import androidx.lifecycle.ViewModelProvider;

import android.os.Bundle;

import life.nsu.foodware.viewmodels.SplashActivityViewModel;

public class SplashActivity extends AppCompatActivity {

    private SplashActivityViewModel viewModel;

    @Override
    protected void onCreate(Bundle savedInstanceState) {
        super.onCreate(savedInstanceState);
        setContentView(R.layout.activity_splash);

        viewModel = new ViewModelProvider(this).get(SplashActivityViewModel.class);


    }

}
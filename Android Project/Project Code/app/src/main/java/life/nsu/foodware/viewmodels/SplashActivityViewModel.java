package life.nsu.foodware.viewmodels;

import android.app.Application;
import android.content.Context;
import android.content.SharedPreferences;

import androidx.annotation.NonNull;
import androidx.lifecycle.AndroidViewModel;
import androidx.lifecycle.MutableLiveData;

import life.nsu.foodware.utils.UserConfirmation;

public class SplashActivityViewModel extends AndroidViewModel implements UserConfirmation {

    SharedPreferences preferences;

    public SplashActivityViewModel(@NonNull Application application) {
        super(application);

        preferences = getApplication().getApplicationContext().getSharedPreferences("user", Context.MODE_PRIVATE);
    }

    @Override
    public MutableLiveData<Boolean> isLoggedIn() {
        MutableLiveData<Boolean> isLogin = new MutableLiveData<Boolean>(false);

        //TODO
        // will call server for validation
        return isLogin;
    }

    @Override
    public MutableLiveData<String> userType() {
        // Three types of user
        // 1. customer, 2. vendor, 3. rider
        MutableLiveData<String> user = new MutableLiveData<String>("customer");

        return user;
    }

    @Override
    public void activitySwitch(String user) {
        switch (user) {
            case "customer" :
                //TODO
                // redirect to customer home page
                break;
            case "vendor" :
                //TODO
                // redirect to vendor home page
                break;
            case "rider" :
                //TODO
                // redirect to rider homepage
                break;
            default:
                //TODO
                // redirect to authentication page

        }
    }
}
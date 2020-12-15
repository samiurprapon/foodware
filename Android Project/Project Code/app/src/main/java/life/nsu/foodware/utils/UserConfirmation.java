package life.nsu.foodware.utils;

import androidx.lifecycle.MutableLiveData;

public interface UserConfirmation {
    MutableLiveData<Boolean> isLoggedIn();
    MutableLiveData<String> userType();
    void activitySwitch(String user);
}

package life.nsu.foodware.views.vendor.profile;

import android.os.Bundle;

import androidx.fragment.app.Fragment;

import android.view.LayoutInflater;
import android.view.View;
import android.view.ViewGroup;

import life.nsu.foodware.R;

public class UpdateVendorProfile extends Fragment {

    public static UpdateVendorProfile newInstance() {
        return new UpdateVendorProfile();
    }

    @Override
    public View onCreateView(LayoutInflater inflater, ViewGroup container,
                             Bundle savedInstanceState) {
        // Inflate the layout for this fragment
        return inflater.inflate(R.layout.fragment_update_vendor_profile, container, false);
    }
}
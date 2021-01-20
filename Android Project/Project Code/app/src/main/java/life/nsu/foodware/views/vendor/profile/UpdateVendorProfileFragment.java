package life.nsu.foodware.views.vendor.profile;

import android.os.Bundle;

import androidx.fragment.app.Fragment;

import android.view.LayoutInflater;
import android.view.View;
import android.view.ViewGroup;

import life.nsu.foodware.R;

public class UpdateVendorProfileFragment extends Fragment {

    public static UpdateVendorProfileFragment newInstance() {
        return new UpdateVendorProfileFragment();
    }

    @Override
    public View onCreateView(LayoutInflater inflater, ViewGroup container,
                             Bundle savedInstanceState) {
        // Inflate the layout for this fragment
        return inflater.inflate(R.layout.fragment_update_vendor_profile, container, false);
    }
}
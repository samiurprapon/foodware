package life.nsu.foodware.views.customer;

import android.os.Bundle;

import androidx.fragment.app.Fragment;

import android.view.LayoutInflater;
import android.view.View;
import android.view.ViewGroup;

import life.nsu.foodware.R;

public class CustomerOrderHistoryFragment extends Fragment {
    private static CustomerOrderHistoryFragment fragment = null;

    public static CustomerOrderHistoryFragment newInstance() {
        if(fragment == null) {
            fragment = new CustomerOrderHistoryFragment();
        }

        return fragment;
    }


    @Override
    public View onCreateView(LayoutInflater inflater, ViewGroup container,
                             Bundle savedInstanceState) {
        // Inflate the layout for this fragment
        return inflater.inflate(R.layout.fragment_customer_order_history, container, false);
    }
}
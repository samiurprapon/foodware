package life.nsu.foodware.views.vendor;

import android.annotation.SuppressLint;
import android.os.Bundle;
import android.view.LayoutInflater;
import android.view.View;
import android.view.ViewGroup;
import android.widget.Button;
import android.widget.TextView;

import androidx.annotation.NonNull;
import androidx.annotation.Nullable;
import androidx.fragment.app.Fragment;
import androidx.recyclerview.widget.DividerItemDecoration;
import androidx.recyclerview.widget.LinearLayoutManager;
import androidx.recyclerview.widget.RecyclerView;

import java.util.ArrayList;

import life.nsu.foodware.R;
import life.nsu.foodware.models.Order;
import life.nsu.foodware.utils.adapters.OrderAdapter;


public class VendorHomeFragment extends Fragment {
    @SuppressLint("StaticFieldLeak")
    private static VendorHomeFragment fragment = null;

    private TextView mFragmentTitle;
    private TextView mFragmentSubTitle;

    private Button mDelivered;
    private Button mConcurrent;
    Button mPreOrder;

    RecyclerView mRecyclerView;

    OrderAdapter adapter;
    private ArrayList<Order> orderList;

    int selection = 0; // 0 => Current orders, 1 => Delivered order, 2 => pre-order

    public static VendorHomeFragment newInstance() {
        if (fragment == null) {
            fragment = new VendorHomeFragment();
        }

        return fragment;
    }

    @Override
    public void onCreate(Bundle savedInstanceState) {
        super.onCreate(savedInstanceState);
    }

    @Override
    public View onCreateView(LayoutInflater inflater, ViewGroup container,
                             Bundle savedInstanceState) {
        // Inflate the layout for this fragment
        return inflater.inflate(R.layout.fragment_vendor_home, container, false);
    }

    @Override
    public void onViewCreated(@NonNull View view, @Nullable Bundle savedInstanceState) {
        super.onViewCreated(view, savedInstanceState);

        mFragmentTitle = view.findViewById(R.id.tv_fragment_title);
        mFragmentSubTitle = view.findViewById(R.id.tv_sub_title);

        mDelivered = view.findViewById(R.id.btn_completed);
        mConcurrent = view.findViewById(R.id.btn_current_orders);
        mPreOrder = view.findViewById(R.id.btn_pre_order);

        mRecyclerView = view.findViewById(R.id.recyclerView);

        mRecyclerView.setHasFixedSize(true);
        mRecyclerView.setLayoutManager(new LinearLayoutManager(getContext()));
        mRecyclerView.addItemDecoration(new DividerItemDecoration(mRecyclerView.getContext(), DividerItemDecoration.VERTICAL));

        defaultLoad();
        mConcurrent.setOnClickListener(v -> {
            defaultLoad();
        });

        mDelivered.setOnClickListener(v -> {
            selection = 1;
            buttonSelection(selection);

            // call server for data it will be inside of tread
            historySync();
            // add data array to adapter
            adapter = new OrderAdapter(getContext(), orderList, selection);
            // set adapter
            mRecyclerView.setAdapter(adapter);

        });

        mPreOrder.setEnabled(false);

    }

    private void defaultLoad() {
        selection = 0;
        buttonSelection(selection);

        // call server for data
        sync();
        // add data array to adapter
        adapter = new OrderAdapter(getContext(), orderList, selection);
        // set adapter
        mRecyclerView.setAdapter(adapter);
    }

    private void sync() {
        // call sync on background as well
    }

    private void historySync() {
        // call server to get data
    }

    private void buttonSelection(int selected) {
        if(selected == 0) {
            mFragmentTitle.setText(R.string.ven_title_ongoing);
            mFragmentSubTitle.setText(R.string.ven_sub_title_ongoing);

            mConcurrent.setSelected(true);
            mDelivered.setSelected(false);
        } else if(selected == 1) {
            mFragmentTitle.setText(R.string.ven_title_delivered);
            mFragmentSubTitle.setText(R.string.ven_sub_title_delivered);

            mConcurrent.setSelected(false);
            mDelivered.setSelected(true);
        }

    }

}
package life.nsu.foodware.views.vendor;

import android.annotation.SuppressLint;
import android.content.ClipData;
import android.os.Bundle;
import android.view.LayoutInflater;
import android.view.View;
import android.view.ViewGroup;
import android.widget.Button;
import android.widget.ImageButton;
import android.widget.TextView;

import androidx.annotation.NonNull;
import androidx.annotation.Nullable;
import androidx.fragment.app.Fragment;
import androidx.fragment.app.FragmentManager;
import androidx.fragment.app.FragmentTransaction;
import androidx.recyclerview.widget.DividerItemDecoration;
import androidx.recyclerview.widget.LinearLayoutManager;
import androidx.recyclerview.widget.RecyclerView;

import java.util.ArrayList;

import life.nsu.foodware.R;
import life.nsu.foodware.models.MenuItem;
import life.nsu.foodware.utils.adapters.MenuAdapter;

public class MenuItemsFragment extends Fragment {
    @SuppressLint("StaticFieldLeak")
    private static MenuItemsFragment fragment = null;

    private TextView mFragmentTitle;
    private TextView mFragmentSubTitle;

    private ImageButton mAdd;
    private Button mItems;
    private Button mToday;
    private Button mTomorrow;
    RecyclerView mRecyclerView;

    MenuAdapter adapter;
    ArrayList<MenuItem> itemList;

    int selection = 0; // 0 => all items, 1 => today's item, 2 => tomorrow't items

    public static MenuItemsFragment newInstance() {
        if (fragment == null) {
            fragment = new MenuItemsFragment();
        }

        return fragment;
    }

    @Override
    public View onCreateView(LayoutInflater inflater, ViewGroup container,
                             Bundle savedInstanceState) {
        // Inflate the layout for this fragment
        return inflater.inflate(R.layout.fragment_menu_items, container, false);
    }

    @Override
    public void onViewCreated(@NonNull View view, @Nullable Bundle savedInstanceState) {
        super.onViewCreated(view, savedInstanceState);

        mFragmentTitle = view.findViewById(R.id.tv_fragment_title);
        mFragmentSubTitle = view.findViewById(R.id.tv_sub_title);

        mAdd = view.findViewById(R.id.ib_add_menu);
        mItems = view.findViewById(R.id.btn_items);
        mToday = view.findViewById(R.id.btn_today_items);
        mTomorrow = view.findViewById(R.id.btn_tomorrow_items);
        mRecyclerView = view.findViewById(R.id.recyclerView);

        mRecyclerView.setHasFixedSize(true);
        mRecyclerView.setLayoutManager(new LinearLayoutManager(getContext()));
        mRecyclerView.addItemDecoration(new DividerItemDecoration(mRecyclerView.getContext(), DividerItemDecoration.VERTICAL));

        mAdd.setOnClickListener(v -> changeFragment(AddMenuItemFragment.newInstance()));

        // when items are shown
        defaultLoad();

        mItems.setOnClickListener(v -> {
            defaultLoad();
        });

        mToday.setOnClickListener(v -> {
            selection = 1;
            buttonSelection(selection);

            // call server for data
            syncToday();
            // add data array to adapter
            adapter = new MenuAdapter(getContext(), itemList, selection);
            // set adapter
            mRecyclerView.setAdapter(adapter);

        });

        mTomorrow.setOnClickListener(v -> {
            selection = 2;
            buttonSelection(selection);

            // call server for data
            syncTomorrow();
            // add data array to adapter
            adapter = new MenuAdapter(getContext(), itemList, selection);
            // set adapter
            mRecyclerView.setAdapter(adapter);
        });

    }

    private void defaultLoad() {
        selection = 0;
        buttonSelection(selection);

        // call server for data
        syncItems();
        // add data array to adapter
        adapter = new MenuAdapter(getContext(), itemList, selection);
        // set adapter
        mRecyclerView.setAdapter(adapter);
    }

    private void syncItems() {
        itemList = new ArrayList<>();

        // test case
        itemList.add(new MenuItem("https://i.imgur.com/pwpWaWu.jpg", "jhdbcsdhbc", "bvashdbasjhc", "bdhcbd", "asdfasdd"));
        itemList.add(new MenuItem("https://i.imgur.com/KIPtISY.jpg", "tgegreg", "bvashdbasjhc", "bdhcbd", "asdfasdd"));
        itemList.add(new MenuItem("https://i.imgur.com/pwpWaWu.jpg", "jhdbcsdhbc", "bvashdbasjhc", "bdhcbd", "asdfasdd"));
        itemList.add(new MenuItem("https://i.imgur.com/KIPtISY.jpg", "evrgergerg", "bvashdbasjhc", "bdhcbd", "asdfasdd"));
        itemList.add(new MenuItem("https://i.imgur.com/pwpWaWu.jpg", "jhdgergergbcsdhbc", "bvashdbasjhc", "bdhcbd", "asdfasdd"));
        itemList.add(new MenuItem("https://i.imgur.com/pwpWaWu.jpg", "berbgb", "bvashdbasjhc", "bdhcbd", "asdfasdd"));
        itemList.add(new MenuItem("https://i.imgur.com/pwpWaWu.jpg", "jhdbebvrevbcsdhbc", "bvashdbasjhc", "bdhcbd", "asdfasdd"));
        itemList.add(new MenuItem("https://i.imgur.com/KIPtISY.jpg", "eberg", "bvashdbasjhc", "bdhcbd", "asdfasdd"));

        // call server for data

    }

    private void syncToday() {
        itemList = new ArrayList<>();

        // test case
        itemList.add(new MenuItem("https://i.imgur.com/pwpWaWu.jpg", "jhdbcsdhbc", "bvashdbasjhc", "500", "asdfasdd", true));
        itemList.add(new MenuItem("https://i.imgur.com/KIPtISY.jpg", "tgegreg", "bvashdbasjhc", "400", "asdfasdd", false));
        itemList.add(new MenuItem("https://i.imgur.com/pwpWaWu.jpg", "jhdbcsdhbc", "bvashdbasjhc", "200", "asdfasdd", true));
        itemList.add(new MenuItem("https://i.imgur.com/KIPtISY.jpg", "evrgergerg", "bvashdbasjhc", "100", "asdfasdd", true));
        itemList.add(new MenuItem("https://i.imgur.com/pwpWaWu.jpg", "jhdgergergbcsdhbc", "bvashdbasjhc", "200", "asdfasdd", true));
        itemList.add(new MenuItem("https://i.imgur.com/pwpWaWu.jpg", "berbgb", "bvashdbasjhc", "20", "asdfasdd", true));
        itemList.add(new MenuItem("https://i.imgur.com/pwpWaWu.jpg", "jhdbebvrevbcsdhbc", "bvashdbasjhc", "100", "asdfasdd", false));
        itemList.add(new MenuItem("https://i.imgur.com/KIPtISY.jpg", "eberg", "bvashdbasjhc", "bdhcbd", "50", false));

        // call server for data


    }

    private void syncTomorrow() {
        itemList = new ArrayList<>();

        // test case
        itemList.add(new MenuItem("https://i.imgur.com/pwpWaWu.jpg", "jhdbcsdhbc", "bvashdbasjhc", "bdhcbd", "asdfasdd"));
        itemList.add(new MenuItem("https://i.imgur.com/KIPtISY.jpg", "tgegreg", "bvashdbasjhc", "bdhcbd", "asdfasdd"));
        itemList.add(new MenuItem("https://i.imgur.com/pwpWaWu.jpg", "jhdbcsdhbc", "bvashdbasjhc", "bdhcbd", "asdfasdd"));
        itemList.add(new MenuItem("https://i.imgur.com/KIPtISY.jpg", "evrgergerg", "bvashdbasjhc", "bdhcbd", "asdfasdd"));
        itemList.add(new MenuItem("https://i.imgur.com/pwpWaWu.jpg", "jhdgergergbcsdhbc", "bvashdbasjhc", "bdhcbd", "asdfasdd"));
        itemList.add(new MenuItem("https://i.imgur.com/pwpWaWu.jpg", "berbgb", "bvashdbasjhc", "bdhcbd", "asdfasdd"));
        itemList.add(new MenuItem("https://i.imgur.com/pwpWaWu.jpg", "jhdbebvrevbcsdhbc", "bvashdbasjhc", "bdhcbd", "asdfasdd"));
        itemList.add(new MenuItem("https://i.imgur.com/KIPtISY.jpg", "eberg", "bvashdbasjhc", "bdhcbd", "asdfasdd"));

        // call server for data

    }

    private void buttonSelection(int selected) {
        if(selected == 0) {
            mAdd.setVisibility(View.VISIBLE);
            mFragmentTitle.setText(R.string.ven_title_menu);
            mFragmentSubTitle.setText(R.string.ven_sub_title_menu);

            mItems.setSelected(true);
            mToday.setSelected(false);
            mTomorrow.setSelected(false);
        } else if(selected == 1) {
            mAdd.setVisibility(View.GONE);
            mFragmentTitle.setText(R.string.ven_title_today);
            mFragmentSubTitle.setText(R.string.ven_sub_title_today);


            mItems.setSelected(false);
            mToday.setSelected(true);
            mTomorrow.setSelected(false);
        } else if(selected == 2) {
            mAdd.setVisibility(View.GONE);

            mFragmentTitle.setText(R.string.ven_title_tomorrow);
            mFragmentSubTitle.setText(R.string.ven_sub_title_tomorrow);

            mItems.setSelected(false);
            mToday.setSelected(false);
            mTomorrow.setSelected(true);
        }

    }

    private void changeFragment(Fragment fragment) {
        FragmentManager fragmentManager = getParentFragmentManager();
        FragmentTransaction fragmentTransaction = fragmentManager.beginTransaction();
        fragmentTransaction.addToBackStack(null);
        fragmentTransaction.setCustomAnimations(R.anim.enter_from_right, R.anim.exit_to_left,
                R.anim.left_enter, R.anim.right_out);
        fragmentTransaction.replace(R.id.content_frame, fragment);
        fragmentTransaction.commit();
    }
}
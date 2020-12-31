package life.nsu.foodware.views.vendor;

import android.annotation.SuppressLint;
import android.os.Bundle;
import android.view.LayoutInflater;
import android.view.View;
import android.view.ViewGroup;
import android.widget.ImageButton;

import androidx.annotation.NonNull;
import androidx.annotation.Nullable;
import androidx.fragment.app.Fragment;
import androidx.fragment.app.FragmentManager;
import androidx.fragment.app.FragmentTransaction;

import life.nsu.foodware.R;

public class MenuItemsFragment extends Fragment {
    @SuppressLint("StaticFieldLeak")
    private static MenuItemsFragment fragment = null;
    ImageButton mAdd;

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

        mAdd = view.findViewById(R.id.ib_add_menu);

        mAdd.setOnClickListener(v -> {
            changeFragment(AddMenuItemFragment.newInstance());
        });
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
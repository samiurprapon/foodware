package life.nsu.foodware.views;

import android.os.Bundle;

import androidx.appcompat.app.AppCompatActivity;
import androidx.viewpager.widget.ViewPager;

import com.google.android.material.tabs.TabLayout;

import life.nsu.foodware.R;
import life.nsu.foodware.utils.adapters.FragmentAdapter;

public class AuthenticationActivity extends AppCompatActivity {

    TabLayout mTabLayout;
    ViewPager mViewPager;
    FragmentAdapter adapter;

    @Override
    protected void onCreate(Bundle savedInstanceState) {
        super.onCreate(savedInstanceState);
        overridePendingTransition(android.R.anim.fade_in, android.R.anim.fade_out);
        setContentView(R.layout.activity_authentication);

        mTabLayout = findViewById(R.id.tabLayout);
        mViewPager = findViewById(R.id.viewPager);

        adapter = new FragmentAdapter(getSupportFragmentManager());

        adapter.addFragment(LoginFragment.newInstance());
        adapter.addFragment(RegisterFragment.newInstance());

        mViewPager.setAdapter(adapter);

        mTabLayout.setupWithViewPager(mViewPager, true);

        // set tab layout text
        mTabLayout.getTabAt(0).setText(R.string.tab_sign_in);
        mTabLayout.getTabAt(1).setText(R.string.tab_sign_up);

    }

    @Override
    public void onBackPressed() {
        super.onBackPressed();
        overridePendingTransition(android.R.anim.fade_in, android.R.anim.fade_out);
    }
}
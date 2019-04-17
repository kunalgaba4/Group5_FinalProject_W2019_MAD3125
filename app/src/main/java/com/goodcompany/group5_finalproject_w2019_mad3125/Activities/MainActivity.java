package com.goodcompany.group5_finalproject_w2019_mad3125.Activities;

import android.os.Bundle;
import android.view.View;
import android.widget.FrameLayout;
import android.widget.ImageView;
import android.widget.LinearLayout;

import com.goodcompany.group5_finalproject_w2019_mad3125.Adapters.CustomViewPager;
import com.goodcompany.group5_finalproject_w2019_mad3125.Adapters.ViewPagerAdapter;
import com.goodcompany.group5_finalproject_w2019_mad3125.Fragments.CartFragment;
import com.goodcompany.group5_finalproject_w2019_mad3125.Fragments.ProductsFragment;
import com.goodcompany.group5_finalproject_w2019_mad3125.Fragments.ProfileFragment;
import com.goodcompany.group5_finalproject_w2019_mad3125.R;

import butterknife.BindView;
import butterknife.ButterKnife;
import butterknife.OnClick;

import static com.goodcompany.group5_finalproject_w2019_mad3125.Utils.Constants.sWidth;

public class MainActivity extends BaseActivity {

    @BindView(R.id.dashboard_foot_iv)
    ImageView dashboardFootIv;
    @BindView(R.id.list_foot_iv)
    ImageView listFootIv;
    @BindView(R.id.family_foot_iv)
    ImageView familyFootIv;
    @BindView(R.id.viewpager)
    CustomViewPager viewpager;
    @BindView(R.id.footer)
    LinearLayout footer;
    @BindView(R.id.calendar_main_fl)
    FrameLayout calendarMainFl;
    private ProductsFragment productsFragment;
    private ProfileFragment profileFragment;
    private CartFragment cartFragment;

    @Override
    protected void onCreate(Bundle savedInstanceState) {
        super.onCreate(savedInstanceState);
        setContentView(R.layout.activity_main);
        ButterKnife.bind(this);
        getDisplayMetrics();
        initializeFragments();
        setupViewPager(viewpager);
        setupDashBoardImage();
        footerImageHandler(dashboardFootIv);
        footerImageHandler(listFootIv);
        footerImageHandler(familyFootIv);

    }

    private void setupViewPager(CustomViewPager viewPager) {
        ViewPagerAdapter adapter = new ViewPagerAdapter(getSupportFragmentManager());
        adapter.addFragment(productsFragment, "product");
        adapter.addFragment(cartFragment, "cart");
        adapter.addFragment(profileFragment, "profile");
        viewPager.setAdapter(adapter);
        viewPager.setPagingEnabled(false);
        viewPager.setOffscreenPageLimit(3);
    }

    private void initializeFragments(){
        productsFragment = new ProductsFragment();
        profileFragment = new ProfileFragment();
        cartFragment = new CartFragment();

    }


    @OnClick({R.id.dashboard_foot_iv, R.id.list_foot_iv, R.id.family_foot_iv})
    public void onViewClicked(View view) {
        switch (view.getId()) {
            case R.id.dashboard_foot_iv:
                setupDashBoardImage();
                break;
            case R.id.list_foot_iv:
                setupListImage();
                break;
            case R.id.family_foot_iv:
                setupFamilyImage();
                break;
        }
    }

    private void footerImageHandler(View view) {
        view.getLayoutParams().height = (int) (0.11 * sWidth);
        view.getLayoutParams().width = (int) (0.11 * sWidth);
    }

    public void setupDashBoardImage() {
        viewpager.setCurrentItem(0);
        dashboardFootIv.setImageResource(R.drawable.dashboard_active);
        listFootIv.setImageResource(R.drawable.list);
        familyFootIv.setImageResource(R.drawable.family);
    }

    public void setupListImage() {
        viewpager.setCurrentItem(1);
        dashboardFootIv.setImageResource(R.drawable.dashboard);
        listFootIv.setImageResource(R.drawable.list_active);
        familyFootIv.setImageResource(R.drawable.family);
    }

    public void setupFamilyImage() {
        viewpager.setCurrentItem(2);
        dashboardFootIv.setImageResource(R.drawable.dashboard);
        listFootIv.setImageResource(R.drawable.list);
        familyFootIv.setImageResource(R.drawable.family_active);
    }
}

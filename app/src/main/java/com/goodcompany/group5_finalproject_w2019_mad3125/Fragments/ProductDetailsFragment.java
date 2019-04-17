package com.goodcompany.group5_finalproject_w2019_mad3125.Fragments;


import android.os.Bundle;
import android.support.annotation.NonNull;
import android.support.annotation.Nullable;
import android.support.v4.app.Fragment;
import android.view.LayoutInflater;
import android.view.View;
import android.view.ViewGroup;
import android.widget.ImageView;
import android.widget.TextView;

import com.bumptech.glide.Glide;
import com.bumptech.glide.request.RequestOptions;
import com.goodcompany.group5_finalproject_w2019_mad3125.Modals.ProductsModal;
import com.goodcompany.group5_finalproject_w2019_mad3125.R;
import com.goodcompany.group5_finalproject_w2019_mad3125.Singelton.ShoppingCart;
import com.goodcompany.group5_finalproject_w2019_mad3125.Utils.ShadowLayout;

import butterknife.BindView;
import butterknife.ButterKnife;
import butterknife.OnClick;
import butterknife.Unbinder;

/**
 * A simple {@link Fragment} subclass.
 */
public class ProductDetailsFragment extends Fragment {


    @BindView(R.id.heading)
    TextView heading;
    @BindView(R.id.back)
    TextView back;
    @BindView(R.id.sl_back)
    ShadowLayout slBack;
    @BindView(R.id.add_to_cart)
    TextView addToCart;
    @BindView(R.id.sl_share)
    ShadowLayout slShare;
    @BindView(R.id.product_iv)
    ImageView productIv;
    @BindView(R.id.tvQuantity)
    TextView tvQuantity;
    @BindView(R.id.tvDecription)
    TextView tvDecription;
    Unbinder unbinder;
    private ProductsModal productsModal;

    public ProductDetailsFragment() {
    }


    @Override
    public View onCreateView(LayoutInflater inflater, ViewGroup container,
                             Bundle savedInstanceState) {
        // Inflate the layout for this fragment
        View view = inflater.inflate(R.layout.fragment_product_details, container, false);
        unbinder = ButterKnife.bind(this, view);
        return view;
    }

    @Override
    public void onViewCreated(@NonNull View view, @Nullable Bundle savedInstanceState) {
        super.onViewCreated(view, savedInstanceState);
        productsModal = (ProductsModal) getArguments().getSerializable("product");
        tvDecription.setText(productsModal.getDescription());
        tvQuantity.setText("1");

        RequestOptions options = new RequestOptions()
                .centerCrop()
                .placeholder(R.mipmap.ic_launcher_round)
                .error(R.mipmap.ic_launcher_round);
        Glide.with(getActivity()).load(productsModal.getImgUrl()).apply(options).into(productIv);


    }

    @Override
    public void onDestroyView() {
        super.onDestroyView();
        unbinder.unbind();
    }

    @OnClick({R.id.sl_back, R.id.sl_share})
    public void onViewClicked(View view) {
        switch (view.getId()) {
            case R.id.sl_back:
                getFragmentManager().popBackStack();
                break;
            case R.id.sl_share:
                ShoppingCart.ourInstance.addProductToCart();
                break;
        }
    }
}

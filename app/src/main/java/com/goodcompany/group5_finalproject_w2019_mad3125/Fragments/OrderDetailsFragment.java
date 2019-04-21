package com.goodcompany.group5_finalproject_w2019_mad3125.Fragments;


import android.content.Context;
import android.content.DialogInterface;
import android.content.Intent;
import android.os.Bundle;
import android.os.Handler;
import android.support.annotation.NonNull;
import android.support.annotation.Nullable;
import android.support.v4.app.Fragment;
import android.support.v7.app.AlertDialog;
import android.support.v7.widget.LinearLayoutManager;
import android.support.v7.widget.RecyclerView;
import android.util.Log;
import android.view.LayoutInflater;
import android.view.View;
import android.view.ViewGroup;
import android.widget.RelativeLayout;
import android.widget.TextView;

import com.goodcompany.group5_finalproject_w2019_mad3125.Activities.BaseActivity;
import com.goodcompany.group5_finalproject_w2019_mad3125.Activities.CheckoutActivity;
import com.goodcompany.group5_finalproject_w2019_mad3125.Adapters.CartAdapter;
import com.goodcompany.group5_finalproject_w2019_mad3125.Adapters.OrderDetailsAdapter;
import com.goodcompany.group5_finalproject_w2019_mad3125.Dialogs.MyProgressDialog;
import com.goodcompany.group5_finalproject_w2019_mad3125.Listeners.ProductSelectListener;
import com.goodcompany.group5_finalproject_w2019_mad3125.Modals.ProductsModal;
import com.goodcompany.group5_finalproject_w2019_mad3125.R;
import com.goodcompany.group5_finalproject_w2019_mad3125.Singelton.ShoppingCart;
import com.goodcompany.group5_finalproject_w2019_mad3125.Utils.ShadowLayout;

import java.util.ArrayList;

import butterknife.BindView;
import butterknife.ButterKnife;
import butterknife.OnClick;
import butterknife.Unbinder;

/**
 * A simple {@link Fragment} subclass.
 */
public class OrderDetailsFragment extends Fragment implements ProductSelectListener {


    @BindView(R.id.rvProducts)
    RecyclerView rvProducts;
    Unbinder unbinder;
    @BindView(R.id.heading)
    TextView heading;
    @BindView(R.id.back)
    TextView back;
    @BindView(R.id.sl_back)
    ShadowLayout slBack;
    @BindView(R.id.checkout)
    TextView checkout;
    @BindView(R.id.sl_share)
    ShadowLayout slShare;
    @BindView(R.id.header)
    RelativeLayout header;
    private Context mContext;
    private OrderDetailsAdapter productsAdapter;
    private ArrayList<ProductsModal> productsModals = new ArrayList<>();

    public OrderDetailsFragment() {
    }





    @Override
    public View onCreateView(LayoutInflater inflater, ViewGroup container,
                             Bundle savedInstanceState) {
        View view = inflater.inflate(R.layout.fragment_your_cart1, container, false);
        unbinder = ButterKnife.bind(this, view);
        return view;
    }

    @Override
    public void onAttach(Context context) {
        super.onAttach(context);
        mContext = context;
    }


    @Override
    public void onViewCreated(@NonNull View view, @Nullable Bundle savedInstanceState) {
        super.onViewCreated(view, savedInstanceState);
        productsModals.clear();
        productsModals.addAll(ShoppingCart.ourInstance.getOrderdById(getArguments().getInt("pos")));
        setupProductsAdapter();
    }


    private void setupProductsAdapter() {
        productsAdapter = new OrderDetailsAdapter(mContext, productsModals, this);
        LinearLayoutManager linearLayoutManager = new LinearLayoutManager(getActivity());
        rvProducts.setLayoutManager(linearLayoutManager);
        rvProducts.setAdapter(productsAdapter);
    }

    @Override
    public void onDestroyView() {
        super.onDestroyView();
        unbinder.unbind();
    }

    @Override
    public void onProductSelected(int position) {

    }

    @Override
    public void onProductDelete(final int position) {

    }



    @OnClick({R.id.sl_back, R.id.sl_share})
    public void onViewClicked(View view) {
        switch (view.getId()) {
            case R.id.sl_back:
                getFragmentManager().popBackStack();
                break;
            case R.id.sl_share:
                if (ShoppingCart.ourInstance.getCartCount() == 0){
                    ((BaseActivity)getActivity()).showMessage("There is nothing in yout cart");
                }else{
                    Intent i = new Intent(getActivity(), CheckoutActivity.class);
                    startActivity(i);
                }
                break;
        }
    }
}

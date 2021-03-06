package com.goodcompany.group5_finalproject_w2019_mad3125.Fragments;


import android.app.Activity;
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

import static android.app.Activity.RESULT_OK;

/**
 * A simple {@link Fragment} subclass.
 */
public class CartFragment extends Fragment implements ProductSelectListener {


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
    @BindView(R.id.tvTotalPrice)
    TextView tvTotalPrice;
    @BindView(R.id.tvPrice)
    TextView tvPrice;
    private Context mContext;
    private CartAdapter productsAdapter;
    private ArrayList<ProductsModal> productsModals = new ArrayList<>();

    public CartFragment() {
    }


    @Override
    public void setUserVisibleHint(boolean isVisibleToUser) {
        super.setUserVisibleHint(isVisibleToUser);
        Log.e("Check", "setUserVisibleHint: "+isVisibleToUser );
        if (isVisibleToUser) {
            productsModals.clear();
            productsModals.addAll(ShoppingCart.ourInstance.getItemsInCart());
            setupProductsAdapter();
            tvPrice.setText(String.valueOf("$"+ShoppingCart.ourInstance.getTotalPrice()));
        }
    }

    @Override
    public View onCreateView(LayoutInflater inflater, ViewGroup container,
                             Bundle savedInstanceState) {
        Log.e("Check", "onCreateView: " );
        View view = inflater.inflate(R.layout.fragment_cart, container, false);
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
        updateProducts();
        setupProductsAdapter();
    }


    private void setupProductsAdapter() {
        productsAdapter = new CartAdapter(mContext, productsModals, this);
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
        new AlertDialog.Builder(getActivity())
                .setTitle("Alert")
                .setMessage("Are you sure you want to remove the event from cart")
                .setPositiveButton(android.R.string.yes, new DialogInterface.OnClickListener() {
                    public void onClick(DialogInterface dialog, int which) {
                        ShoppingCart.ourInstance.removeProductFromCart(String.valueOf(position));
                        updateProducts();
                        MyProgressDialog.show(mContext);
                        new Handler().postDelayed(new Runnable() {
                            @Override
                            public void run() {
                                tvPrice.setText(String.valueOf("$"+ShoppingCart.ourInstance.getTotalPrice()));
                                productsAdapter.notifyDataSetChanged();
                                MyProgressDialog.dismiss();
                                ((BaseActivity) getActivity()).showMessage("Product Successfully removed from cart");
                            }
                        }, 5000);
                    }
                })
                .setNegativeButton(android.R.string.no, new DialogInterface.OnClickListener() {
                    public void onClick(DialogInterface dialog, int which) {
                    }
                })
                .setIcon(android.R.drawable.ic_dialog_alert)
                .show();

    }

    private void updateProducts() {
        productsModals.clear();
        productsModals.addAll(ShoppingCart.ourInstance.getItemsInCart());
        tvPrice.setText(String.valueOf("$"+ShoppingCart.ourInstance.getTotalPrice()));
    }

    @OnClick({R.id.sl_back, R.id.sl_share})
    public void onViewClicked(View view) {
        switch (view.getId()) {
            case R.id.sl_back:
                getFragmentManager().popBackStack();
                break;
            case R.id.sl_share:
                if (ShoppingCart.ourInstance.getCartCount() == 0){
                    ((BaseActivity)getActivity()).showMessage("There is nothing in your cart");
                }else{
                    Intent i = new Intent(getActivity(), CheckoutActivity.class);
                    startActivityForResult(i, 45);
                }
                break;
        }
    }

    @Override
    public void onActivityResult(int requestCode, int resultCode, Intent data) {
        super.onActivityResult(requestCode, resultCode, data);
        Log.e("check", "onActivityResult: " );
        if (requestCode == 45){
            if (resultCode == RESULT_OK){
                Log.e("check", "onActivityResult: inisde ok" );
                if (productsModals != null && productsAdapter != null)
                updateProducts();
                setupProductsAdapter();
            }
        }
    }
}

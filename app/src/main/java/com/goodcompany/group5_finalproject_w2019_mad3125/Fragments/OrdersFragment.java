package com.goodcompany.group5_finalproject_w2019_mad3125.Fragments;


import android.content.Context;
import android.os.Bundle;
import android.support.annotation.NonNull;
import android.support.annotation.Nullable;
import android.support.constraint.ConstraintLayout;
import android.support.v4.app.Fragment;
import android.support.v7.widget.LinearLayoutManager;
import android.support.v7.widget.RecyclerView;
import android.view.LayoutInflater;
import android.view.View;
import android.view.ViewGroup;
import android.widget.RelativeLayout;
import android.widget.TextView;

import com.goodcompany.group5_finalproject_w2019_mad3125.Adapters.OrdersAdapter;
import com.goodcompany.group5_finalproject_w2019_mad3125.Listeners.ProductSelectListener;
import com.goodcompany.group5_finalproject_w2019_mad3125.R;
import com.goodcompany.group5_finalproject_w2019_mad3125.Utils.ShadowLayout;

import butterknife.BindView;
import butterknife.ButterKnife;
import butterknife.OnClick;
import butterknife.Unbinder;

/**
 * A simple {@link Fragment} subclass.
 */
public class OrdersFragment extends Fragment implements ProductSelectListener {


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
    @BindView(R.id.rvProducts)
    RecyclerView rvProducts;
    @BindView(R.id.parent)
    ConstraintLayout parent;
    Unbinder unbinder;
    private Context mContext;

    public OrdersFragment() {
        // Required empty public constructor
    }


    @Override
    public View onCreateView(LayoutInflater inflater, ViewGroup container,
                             Bundle savedInstanceState) {
        // Inflate the layout for this fragment
        View view = inflater.inflate(R.layout.fragment_orders, container, false);
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
        setupOrdersAdapter();
    }

    private void setupOrdersAdapter() {

        OrdersAdapter productsAdapter = new OrdersAdapter(mContext, this);
        LinearLayoutManager linearLayoutManager = new LinearLayoutManager(getActivity());
        rvProducts.setLayoutManager(linearLayoutManager);
        rvProducts.setAdapter(productsAdapter);
    }

    @Override
    public void onDestroyView() {
        super.onDestroyView();
        unbinder.unbind();
    }

    @OnClick(R.id.sl_back)
    public void onViewClicked() {
        getFragmentManager().popBackStack();
    }

    @Override
    public void onProductSelected(int position) {

    }

    @Override
    public void onProductDelete(int position) {

    }
}

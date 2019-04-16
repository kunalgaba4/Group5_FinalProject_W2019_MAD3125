package com.goodcompany.group5_finalproject_w2019_mad3125.Fragments;


import android.content.Context;
import android.os.Bundle;
import android.support.annotation.NonNull;
import android.support.annotation.Nullable;
import android.support.v4.app.Fragment;
import android.support.v7.widget.LinearLayoutManager;
import android.support.v7.widget.RecyclerView;
import android.view.LayoutInflater;
import android.view.View;
import android.view.ViewGroup;

import com.goodcompany.group5_finalproject_w2019_mad3125.Adapters.ProductsAdapter;
import com.goodcompany.group5_finalproject_w2019_mad3125.Modals.ProductsModal;
import com.goodcompany.group5_finalproject_w2019_mad3125.R;
import com.goodcompany.group5_finalproject_w2019_mad3125.Utils.ReadJSONUtils;
import com.google.gson.Gson;
import com.google.gson.reflect.TypeToken;

import java.lang.reflect.Type;
import java.text.DecimalFormat;
import java.util.ArrayList;

import butterknife.BindView;
import butterknife.ButterKnife;
import butterknife.Unbinder;

/**
 * A simple {@link Fragment} subclass.
 */
public class ProductsFragment extends Fragment {

    @BindView(R.id.rvProducts)
    RecyclerView rvProducts;
    Unbinder unbinder;
    private Context mContext;
    private ProductsAdapter productsAdapter;
    private String jsonString;
    private ArrayList<ProductsModal> productsModals;

    public ProductsFragment() {
    }


    @Override
    public View onCreateView(LayoutInflater inflater, ViewGroup container,
                             Bundle savedInstanceState) {
        // Inflate the layout for this fragment
        View view = inflater.inflate(R.layout.fragment_products, container, false);
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
        loadDatafromJson();
        setupProductsAdapter();
    }

    private void loadDatafromJson() {
        jsonString = ReadJSONUtils.loadJSONFromAsset(mContext,"products.json");
        Gson gson = new Gson();
        productsModals = new ArrayList<>();
        Type founderListType = new TypeToken<ArrayList<ProductsModal>>(){}.getType();
        productsModals = gson.fromJson(jsonString,founderListType);
    }

    private void setupProductsAdapter() {
        productsAdapter = new ProductsAdapter(mContext, productsModals);
        LinearLayoutManager linearLayoutManager = new LinearLayoutManager(getActivity());
        rvProducts.setLayoutManager(linearLayoutManager);
        rvProducts.setAdapter(productsAdapter);
    }

    @Override
    public void onDestroyView() {
        super.onDestroyView();
        unbinder.unbind();
    }
}

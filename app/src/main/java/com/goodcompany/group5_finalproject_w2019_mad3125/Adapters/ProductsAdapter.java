package com.goodcompany.group5_finalproject_w2019_mad3125.Adapters;

import android.content.Context;
import android.support.annotation.NonNull;
import android.support.v7.widget.RecyclerView;
import android.view.LayoutInflater;
import android.view.View;
import android.view.ViewGroup;

import com.goodcompany.group5_finalproject_w2019_mad3125.Modals.ProductsModal;
import com.goodcompany.group5_finalproject_w2019_mad3125.R;

import java.util.ArrayList;

public class ProductsAdapter extends RecyclerView.Adapter<ProductsAdapter.ProductsViewHolder> {

    private Context mContext;
    private ArrayList<ProductsModal> productsModals = new ArrayList<>();

    public ProductsAdapter(Context mContext, ArrayList<ProductsModal> productsModals){
        this.mContext = mContext;
        this.productsModals.addAll(productsModals);

    }
    @NonNull
    @Override
    public ProductsViewHolder onCreateViewHolder(@NonNull ViewGroup viewGroup, int i) {
        View view = LayoutInflater.from(viewGroup.getContext()).inflate(R.layout.progress_dialog, viewGroup, false);
        ProductsViewHolder productsViewHolder  = new ProductsViewHolder(view);
        return  productsViewHolder;
    }

    @Override
    public void onBindViewHolder(@NonNull ProductsViewHolder productsViewHolder, int i) {

    }

    @Override
    public int getItemCount() {
        return productsModals.size();
    }

    public class ProductsViewHolder extends RecyclerView.ViewHolder {
        public ProductsViewHolder(@NonNull View itemView) {
            super(itemView);
        }
    }
}

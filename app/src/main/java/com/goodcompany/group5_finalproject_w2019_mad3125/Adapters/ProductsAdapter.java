package com.goodcompany.group5_finalproject_w2019_mad3125.Adapters;

import android.content.Context;
import android.support.annotation.NonNull;
import android.support.v7.widget.RecyclerView;
import android.view.LayoutInflater;
import android.view.View;
import android.view.ViewGroup;
import android.widget.ImageView;
import android.widget.TextView;

import com.bumptech.glide.Glide;
import com.bumptech.glide.request.RequestOptions;
import com.goodcompany.group5_finalproject_w2019_mad3125.Listeners.ProductSelectListener;
import com.goodcompany.group5_finalproject_w2019_mad3125.Modals.ProductsModal;
import com.goodcompany.group5_finalproject_w2019_mad3125.R;

import java.util.ArrayList;

public class ProductsAdapter extends RecyclerView.Adapter<ProductsAdapter.ProductsViewHolder> {

    private Context mContext;
    private ArrayList<ProductsModal> productsModals = new ArrayList<>();
    private ProductSelectListener productSelectListener;

    public ProductsAdapter(Context mContext, ArrayList<ProductsModal> productsModals, ProductSelectListener productSelectListener){
        this.mContext = mContext;
        this.productsModals.addAll(productsModals);
        this.productSelectListener = productSelectListener;

    }
    @NonNull
    @Override
    public ProductsViewHolder onCreateViewHolder(@NonNull ViewGroup viewGroup, int i) {
        View view = LayoutInflater.from(viewGroup.getContext()).inflate(R.layout.custom_product_row, viewGroup, false);
        ProductsViewHolder productsViewHolder  = new ProductsViewHolder(view);
        return productsViewHolder;
    }

    @Override
    public void onBindViewHolder(@NonNull ProductsViewHolder productsViewHolder, int i) {
        RequestOptions options = new RequestOptions()
                .centerCrop()
                .placeholder(R.mipmap.ic_launcher_round)
                .error(R.mipmap.ic_launcher_round);
        Glide.with(mContext).load(productsModals.get(i).getImgUrl()).apply(options).into(productsViewHolder.imagView);
        productsViewHolder.description.setText(productsModals.get(i).getName());
        productsViewHolder.price.setText("Price: $"+productsModals.get(i).getPrice());

    }

    @Override
    public int getItemCount() {
        return productsModals.size();
    }

    public class ProductsViewHolder extends RecyclerView.ViewHolder {
        private ImageView imagView;
        private TextView description, price;
        public ProductsViewHolder(@NonNull View itemView) {
            super(itemView);
            imagView = itemView.findViewById(R.id.imageView);
            description = itemView.findViewById(R.id.description);
            price = itemView.findViewById(R.id.price);

            itemView.setOnClickListener(new View.OnClickListener() {
                @Override
                public void onClick(View view) {
                    productSelectListener.onProductSelected(getAdapterPosition());
                }
            });
        }
    }
}

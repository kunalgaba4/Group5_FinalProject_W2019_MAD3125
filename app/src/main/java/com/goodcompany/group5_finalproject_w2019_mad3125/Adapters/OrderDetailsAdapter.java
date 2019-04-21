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

public class OrderDetailsAdapter extends RecyclerView.Adapter<OrderDetailsAdapter.OrderViewHolder> {

    private Context mContext;
    private ArrayList<ProductsModal> productsModals = new ArrayList<>();
    private ProductSelectListener productSelectListener;

    public OrderDetailsAdapter(Context mContext, ArrayList<ProductsModal> productsModals, ProductSelectListener productSelectListener) {
        this.mContext = mContext;
        this.productsModals.addAll(productsModals);
        this.productSelectListener = productSelectListener;

    }

    @NonNull
    @Override
    public OrderDetailsAdapter.OrderViewHolder onCreateViewHolder(@NonNull ViewGroup viewGroup, int i) {
        View view = LayoutInflater.from(viewGroup.getContext()).inflate(R.layout.custom_cart_row, viewGroup, false);
        OrderDetailsAdapter.OrderViewHolder orderViewHolder = new OrderDetailsAdapter.OrderViewHolder(view);
        return orderViewHolder;
    }

    @Override
    public void onBindViewHolder(@NonNull OrderDetailsAdapter.OrderViewHolder orderViewHolder, int i) {
        RequestOptions options = new RequestOptions()
                .centerCrop()
                .placeholder(R.mipmap.ic_launcher_round)
                .error(R.mipmap.ic_launcher_round);
        Glide.with(mContext).load(productsModals.get(i).getImgUrl()).apply(options).into(orderViewHolder.imagView);
        orderViewHolder.description.setText(productsModals.get(i).getName());
        orderViewHolder.price.setText("Price: $" + productsModals.get(i).getPrice());
        orderViewHolder.price.setText("Price: "+(productsModals.get(i).getPrice()*productsModals.get(i).getQuantity()));
        orderViewHolder.quantity.setText("Quantity: "+productsModals.get(i).getQuantity());

    }

    @Override
    public int getItemCount() {
        return productsModals.size();
    }

    public class OrderViewHolder extends RecyclerView.ViewHolder {
        private ImageView imagView,delete_iv;
        private TextView description, price, quantity;

        public OrderViewHolder(@NonNull View itemView) {
            super(itemView);
            imagView = itemView.findViewById(R.id.imageView);
            description = itemView.findViewById(R.id.description);
            price = itemView.findViewById(R.id.price);
            delete_iv = itemView.findViewById(R.id.delete_iv);
            quantity = itemView.findViewById(R.id.quantity);
            delete_iv.setVisibility(View.INVISIBLE);
            itemView.setOnClickListener(new View.OnClickListener() {
                @Override
                public void onClick(View view) {
                    productSelectListener.onProductSelected(getAdapterPosition());
                }
            });
        }
    }
}
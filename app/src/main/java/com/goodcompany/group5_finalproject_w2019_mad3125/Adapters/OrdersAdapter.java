package com.goodcompany.group5_finalproject_w2019_mad3125.Adapters;

import android.content.Context;
import android.os.Bundle;
import android.support.annotation.NonNull;
import android.support.v7.widget.RecyclerView;
import android.view.LayoutInflater;
import android.view.View;
import android.view.ViewGroup;
import android.widget.ImageView;
import android.widget.TextView;

import com.goodcompany.group5_finalproject_w2019_mad3125.Activities.BaseActivity;
import com.goodcompany.group5_finalproject_w2019_mad3125.Fragments.OrderDetailsFragment;
import com.goodcompany.group5_finalproject_w2019_mad3125.Listeners.ProductSelectListener;
import com.goodcompany.group5_finalproject_w2019_mad3125.Modals.OrdersModal;
import com.goodcompany.group5_finalproject_w2019_mad3125.R;
import com.goodcompany.group5_finalproject_w2019_mad3125.Singelton.ShoppingCart;

import java.util.ArrayList;

public class OrdersAdapter extends RecyclerView.Adapter<OrdersAdapter.YourCartViewHolder> {

    private Context mContext;
    private ProductSelectListener productSelectListener;
    private  ArrayList<OrdersModal> orders =  new ArrayList();

    public OrdersAdapter(Context mContext, ProductSelectListener productSelectListener){
        this.mContext = mContext;
        this.productSelectListener = productSelectListener;
        orders.addAll(ShoppingCart.ourInstance.getOrder());

    }
    @NonNull
    @Override
    public OrdersAdapter.YourCartViewHolder onCreateViewHolder(@NonNull ViewGroup viewGroup, int i) {
        View view = LayoutInflater.from(viewGroup.getContext()).inflate(R.layout.custom_yout_cart_row, viewGroup, false);
        OrdersAdapter.YourCartViewHolder YourCartViewHolder  = new OrdersAdapter.YourCartViewHolder(view);
        return YourCartViewHolder;
    }

    @Override
    public void onBindViewHolder(@NonNull OrdersAdapter.YourCartViewHolder YourCartViewHolder, int i) {

        YourCartViewHolder.description.setText("Order Id: "+orders.get(i).getOrderId());
//        YourCartViewHolder.price.setText("Price: "+(orders.get(i).getPrice()*productsModals.get(i).getQuantity()));
        YourCartViewHolder.quantity.setText("Number of items "+orders.get(i).getProductsModals().size());

    }

    @Override
    public int getItemCount() {
        return orders.size();
    }

    public class YourCartViewHolder extends RecyclerView.ViewHolder {
        private ImageView imagView,delete;
        private TextView description, quantity;
        public YourCartViewHolder(@NonNull View itemView) {
            super(itemView);
            imagView = itemView.findViewById(R.id.imageView);
            description = itemView.findViewById(R.id.description);
            delete = itemView.findViewById(R.id.delete_iv);
            quantity = itemView.findViewById(R.id.quantity);
            itemView.setOnClickListener(new View.OnClickListener() {
                @Override
                public void onClick(View view) {
                    OrderDetailsFragment orderDetailsFragment = new OrderDetailsFragment();
                    Bundle b = new Bundle();
                    b.putInt("pos", getAdapterPosition());
                    orderDetailsFragment.setArguments(b);
                  ((BaseActivity) mContext).addFragment(R.id.parent, orderDetailsFragment, "cart", "cart", true);

                }
            });
        }
    }
}


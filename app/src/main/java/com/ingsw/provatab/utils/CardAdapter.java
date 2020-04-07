package com.ingsw.provatab.utils;

import android.animation.Animator;
import android.animation.AnimatorListenerAdapter;
import android.annotation.SuppressLint;
import android.content.Context;
import android.content.Intent;
import android.view.LayoutInflater;
import android.view.View;
import android.view.ViewGroup;
import android.widget.ImageButton;
import android.widget.ImageView;
import android.widget.TextView;

import androidx.annotation.NonNull;
import androidx.recyclerview.widget.RecyclerView;

import com.ingsw.provatab.MainActivity;
import com.ingsw.provatab.R;
import com.ingsw.provatab.com.ingsw.model.Cart;
import com.squareup.picasso.Picasso;


public class CardAdapter extends RecyclerView.Adapter<CardAdapter.CartHolder> {

    private final TextView totalPrice;
    private final TextView totalPriceShipping;
    private Context context;
    private com.ingsw.provatab.com.ingsw.model.Cart cart;


    public CardAdapter(Context context, Cart cart, TextView totalPrice, TextView totalPriceShipping){
        this.context=context;
        this.cart=cart;
        this.totalPrice=totalPrice;
        this.totalPriceShipping=totalPriceShipping;


    }
    @NonNull
    @Override
    public CartHolder onCreateViewHolder(@NonNull ViewGroup parent, int i) {
        View view = LayoutInflater.from(context).inflate(R.layout.layout_item_cart, parent, false);
        return new CartHolder(view);
    }

    @Override
    public void onBindViewHolder(@NonNull CartHolder holder, int position) {
        holder.setDetails(position);
    }

    @Override
    public int getItemCount() {
        return cart.getProducts().size();
    }

    class CartHolder extends RecyclerView.ViewHolder {

        private ImageButton delete ;
        private ImageView img ;
        private TextView txtName, txtPrice, txtShipping;

        CartHolder(View itemView) {
            super(itemView);
            txtName = itemView.findViewById(R.id.nameProd);
            txtPrice = itemView.findViewById(R.id.priceProd);
            txtShipping = itemView.findViewById(R.id.shippingProd);
            img = itemView.findViewById(R.id.imageProd);
            delete=itemView.findViewById(R.id.deleteProd);

        }

        @SuppressLint("SetTextI18n")
        void setDetails(final int position) {

            txtName.setText(cart.getProducts().get(position).getName()+"("+ cart.getColors().get(position)+")");
            txtPrice.setText("€"+cart.getProducts().get(position).getPrice()+"0");
            switch (cart.getShipping().get(position)) {
                case 0:
                    txtShipping.setText("Standard (4/5 gg) Gratis");
                    break;
                case 1:
                    txtShipping.setText("Espressa (48/72 ore) €4.00");
                    break;
                case 2:
                    txtShipping.setText("Espressa (24 ore) €8.00");
                    break;
            }

            Picasso.get().load(cart.getProducts().get(position).getPath().get(0)).resize(250, 250).centerInside().into(img);
            delete.setOnClickListener(new View.OnClickListener() {
                @Override
                public void onClick(View v) {
                    if(cart.getProducts().size()==0){
                        com.ingsw.provatab.com.ingsw.model.Cart.deleteCart();
                    }else {
                        cart.getProducts().remove(position);
                        cart.getShipping().remove(position);
                        cart.getColors().remove(position);

                        if(cart.getProducts().size()==0){
                            int position = 2;
                            Intent intent = new Intent(context, MainActivity.class);
                            intent.putExtra("fragmentPosition", position);
                            intent.setFlags(Intent.FLAG_ACTIVITY_CLEAR_TOP | Intent.FLAG_ACTIVITY_NO_ANIMATION);
                            context.startActivity(intent);
                        } else {
                            totalPrice.setText("€ " + cart.getTotalPrice() + "0");
                            totalPriceShipping.setText("€ " + cart.getTotalShipping() + "0");
                        }
                        notifyDataSetChanged();
                    }


                }
            });






        }
    }
    public static void animateView(final View view, final int toVisibility, float toAlpha, int duration) {
        boolean show = toVisibility == View.VISIBLE;
        if (show) {
            view.setAlpha(0);
        }
        view.setVisibility(View.VISIBLE);
        view.animate()
                .setDuration(duration)
                .alpha(show ? toAlpha : 0)
                .setListener(new AnimatorListenerAdapter() {
                    @Override
                    public void onAnimationEnd(Animator animation) {
                        view.setVisibility(toVisibility);
                    }
                });
    }
}

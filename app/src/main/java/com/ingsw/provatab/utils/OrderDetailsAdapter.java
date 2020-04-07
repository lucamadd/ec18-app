package com.ingsw.provatab.utils;

import android.app.Activity;
import android.content.Context;
import android.content.ContextWrapper;
import android.content.Intent;
import android.graphics.Color;
import android.graphics.drawable.Drawable;
import android.view.LayoutInflater;
import android.view.View;
import android.view.ViewGroup;
import android.widget.RatingBar;
import android.widget.TextView;
import android.widget.Toast;

import androidx.annotation.NonNull;
import androidx.core.content.ContextCompat;
import androidx.core.graphics.drawable.DrawableCompat;
import androidx.recyclerview.widget.RecyclerView;

import com.ingsw.provatab.ApiClient;
import com.ingsw.provatab.ApiInterface;
import com.ingsw.provatab.OrderDetails;
import com.ingsw.provatab.R;
import com.ingsw.provatab.com.ingsw.model.Order;

import retrofit2.Call;
import retrofit2.Callback;
import retrofit2.Response;


public class OrderDetailsAdapter extends RecyclerView.Adapter<OrderDetailsAdapter.CartHolder> {

    private Context context;
    private Order order;

    public OrderDetailsAdapter(Context context, Order order){
        this.context=context;
        this.order=order;
    }
    @NonNull
    @Override
    public com.ingsw.provatab.utils.OrderDetailsAdapter.CartHolder onCreateViewHolder(@NonNull ViewGroup parent, int i) {
        View view = LayoutInflater.from(context).inflate(R.layout.layout_order_details, parent, false);
        return new com.ingsw.provatab.utils.OrderDetailsAdapter.CartHolder(view);
    }

    @Override
    public void onBindViewHolder(@NonNull CartHolder holder, int position) {
        holder.setDetails(position);
    }



    @Override
    public int getItemCount() {
        return order.getProducts().size();
    }

    class CartHolder extends RecyclerView.ViewHolder {



        private TextView txtOrder, txtPrice, txtShipping, feedback;
        private RatingBar ratingBar;
        private ApiInterface apiInterface;

        CartHolder(View itemView) {
            super(itemView);
            txtOrder = itemView.findViewById(R.id.nameProdOrder);
            txtPrice = itemView.findViewById(R.id.priceProdOrder);
            txtShipping = itemView.findViewById(R.id.shippingProdOrder);
            ratingBar = itemView.findViewById(R.id.starsOrder);
            feedback = itemView.findViewById(R.id.feed);



        }

        void setDetails(final int position) {

            txtOrder.setText(order.getProducts().get(position).getName());
            txtPrice.setText("€"+ order.getProducts().get(position).getPrice() +"0");

            switch (order.getShipping().get(position)) {
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

            ratingBar.setRating((order.getFeedback().get(position)));
            Drawable progressDrawable5 = ratingBar.getProgressDrawable();
            if (progressDrawable5 != null) {
                DrawableCompat.setTint(progressDrawable5, ContextCompat.getColor(context, R.color.colorPrimary));
            }

            if (ratingBar.getRating()<1) {
                feedback.setText("Aggiungi feedback");
                feedback.setBackground(context.getResources().getDrawable(R.drawable.button_bg_rounded_corners, null));
                ratingBar.setIsIndicator(false);
                apiInterface=ApiClient.getClient().create(ApiInterface.class);

                feedback.setOnClickListener(new View.OnClickListener() {
                    @Override
                    public void onClick(View v) {


                        float star = ratingBar.getRating();
                        if (star > 0) {
                            Call<Boolean> call = apiInterface.checkFeedback(order.getOrderN(), position, Math.round(star));
                            call.enqueue(new Callback<Boolean>() {
                                @Override
                                public void onResponse(Call<Boolean> call, Response<Boolean> response) {
                                    if (response.body()!=null) {
                                        if (response.body()) {
                                            int duration = Toast.LENGTH_SHORT;

                                            Toast toast = Toast.makeText(context, "Feedback aggiunto!", duration);
                                            toast.show();

                                            Intent intent=new Intent(context, OrderDetails.class);
                                            intent.putExtra("itemOrder",order.getOrderN());
                                            intent.setFlags(Intent.FLAG_ACTIVITY_CLEAR_TOP | Intent.FLAG_ACTIVITY_NEW_TASK);
                                            context.startActivity(intent);

                                        } else {
                                            int duration = Toast.LENGTH_SHORT;

                                            Toast toast = Toast.makeText(context, "Errore. Riprova!", duration);
                                            toast.show();
                                        }
                                    }

                                }

                                @Override
                                public void onFailure(Call<Boolean> call, Throwable t) {

                                }
                            });
                        }else{
                            int duration = Toast.LENGTH_SHORT;

                            Toast toast = Toast.makeText(context, "Feedback non valido. Riprova!", duration);
                            toast.show();
                             }
                    }
                });


            }else{
                ratingBar.setIsIndicator(true);
                feedback.setTextColor(Color.BLACK);
            }


        }








    }


    private static Activity scanForActivity(Context cont) {
        if (cont == null)
            return null;
        else if (cont instanceof Activity)
            return (Activity)cont;
        else if (cont instanceof ContextWrapper)
            return scanForActivity(((ContextWrapper)cont).getBaseContext());

        return null;
    }
}


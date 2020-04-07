package com.ingsw.provatab.utils;

import android.content.Context;
import android.content.Intent;
import android.view.LayoutInflater;
import android.view.View;
import android.view.ViewGroup;
import android.widget.TextView;

import androidx.annotation.NonNull;
import androidx.recyclerview.widget.RecyclerView;

import com.ingsw.provatab.OrderDetails;
import com.ingsw.provatab.R;
import com.ingsw.provatab.com.ingsw.model.Order;

import java.util.List;

public class OrderAdapter extends RecyclerView.Adapter<OrderAdapter.CartHolder> {

        private Context context;
        private List<Order> orderList;

        public OrderAdapter(Context context, List<Order> orderList){
            this.context=context;
            this.orderList=orderList;
        }
        @NonNull
        @Override
        public com.ingsw.provatab.utils.OrderAdapter.CartHolder onCreateViewHolder(@NonNull ViewGroup parent, int i) {
            View view = LayoutInflater.from(context).inflate(R.layout.layout_orders, parent, false);
            return new com.ingsw.provatab.utils.OrderAdapter.CartHolder(view);
        }

    @Override
    public void onBindViewHolder(@NonNull CartHolder holder, int position) {
        holder.setDetails(position);
    }



        @Override
        public int getItemCount() {
            return orderList.size();
        }

        class CartHolder extends RecyclerView.ViewHolder {


            private TextView txtOrder, txtPrice, txtDate;

            CartHolder(View itemView) {
                super(itemView);
                txtOrder = itemView.findViewById(R.id.numOrder);
                txtPrice = itemView.findViewById(R.id.priceTotalOrder);
                txtDate = itemView.findViewById(R.id.dateOrder);


            }

            void setDetails(final int position) {

                txtOrder.setText("Ordine n° "+(orderList.get(position).getOrderN()));
                txtPrice.setText("€"+ orderList.get(position).getTotalPrice() +"0");
                txtDate.setText(getDecentDate((orderList.get(position).getDate())));

                txtOrder.setOnClickListener(new View.OnClickListener() {
                    @Override
                    public void onClick(View v) {
                        orderDetails(orderList.get(position).getOrderN());
                    }
                });

                txtPrice.setOnClickListener(new View.OnClickListener() {
                    @Override
                    public void onClick(View v) {
                        orderDetails(orderList.get(position).getOrderN());
                    }
                });

                txtDate.setOnClickListener(new View.OnClickListener() {
                    @Override
                    public void onClick(View v) {
                        orderDetails(orderList.get(position).getOrderN());
                    }
                });



            }








            }
    private String getDecentDate(String date){

        String day=date.substring(8,10);
        String month=date.substring(4,7);
        String year=date.substring(29,33);

        switch (month){
            case "Jan": month="01"; break;
            case "Feb": month="02"; break;
            case "Mar": month="03"; break;
            case "Apr": month="04"; break;
            case "May": month="05"; break;
            case "Jun": month="06"; break;
            case "Jul": month="07"; break;
            case "Aug": month="08"; break;
            case "Sep": month="09"; break;
            case "Oct": month="10"; break;
            case "Nov": month="11"; break;
            case "Dec": month="12"; break;
        }


        return day+"/"+month+"/"+year;
    }


    public void orderDetails(int orderN){
        Intent intent=new Intent(context, OrderDetails.class);
        intent.putExtra("itemOrder", orderN);
        context.startActivity(intent);
    }

}

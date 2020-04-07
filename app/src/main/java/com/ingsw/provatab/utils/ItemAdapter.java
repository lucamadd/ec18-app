package com.ingsw.provatab.utils;

import android.content.Context;
import android.graphics.Color;
import android.view.LayoutInflater;
import android.view.View;
import android.view.ViewGroup;
import android.widget.ImageButton;
import android.widget.LinearLayout;
import android.widget.RatingBar;
import android.widget.RelativeLayout;
import android.widget.TextView;

import androidx.annotation.NonNull;
import androidx.recyclerview.widget.RecyclerView;

import com.ingsw.provatab.R;
import com.ingsw.provatab.com.ingsw.model.Item;
import com.squareup.picasso.Picasso;

import java.util.List;

public class ItemAdapter extends RecyclerView.Adapter {
    private List<Item> items;
    private Context context;
    protected ItemListener listener;

    public ItemAdapter(List<Item> items, Context context, ItemListener listener){
        this.items= items;
        this.context=context;
        this.listener=listener;
    }



    @Override
    public int getItemCount() {

        return items.size();
    }

    public class ViewHolder extends RecyclerView.ViewHolder implements View.OnClickListener{
        TextView name, price, offer;
        RelativeLayout parent;
        RatingBar rating;
        ImageButton image;
        Item item;
        public ViewHolder(View itemView) {
            super(itemView);
            itemView.setOnClickListener(this);
            parent = itemView.findViewById(R.id.parent);
            name = itemView.findViewById(R.id.nameProduct);
            price = itemView.findViewById(R.id.price);
            offer = itemView.findViewById(R.id.offer);
            rating = itemView.findViewById(R.id.stars);
            image = itemView.findViewById(R.id.imgButton);
        }
        public void setData(Item item){
            this.item= item;
            parent.setBackgroundColor(Color.BLACK);
            name.setText(item.getName());
            price.setText("€ "+ Double.toString(item.getPrice())+ "0");
            if(item.getOnSale()){
                offer.setText("IN OFFERTA");
                offer.setBackgroundColor(context.getResources().getColor(R.color.colorPrimary,null));
                offer.setTextColor(Color.WHITE);
                offer.setBackground(context.getResources().getDrawable(R.drawable.button_bg_rounded_corners,null));
                price.setText("€ "+Double.toString(item.getPrice()-(item.getPrice()*0.2))+"0");
            }
            rating.setRating(item.getRating());
            Picasso.get().load(item.getPath().get(0)).resize(320,310).centerInside().into(image);
        }
        @Override
        public void onClick(View view){
            if(listener!= null){
                listener.onItemClick(item);
            }
        }
    }
    @Override
    public ItemAdapter.ViewHolder onCreateViewHolder(ViewGroup parent, int viewType){
        View view = LayoutInflater.from(context).inflate(R.layout.item_list_row, parent, false);
        return new ViewHolder(view);
    }

    @Override
    public void onBindViewHolder(@NonNull RecyclerView.ViewHolder holder, int position) {
    }
    public void onBindViewHolder(ViewHolder holder, int position) {
    holder.setData(items.get(position));
    }

    public interface ItemListener{
        void onItemClick(Item item);
    }

}

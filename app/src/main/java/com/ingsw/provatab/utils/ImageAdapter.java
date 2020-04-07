package com.ingsw.provatab.utils;

import android.content.Context;
import android.graphics.Color;
import android.view.View;
import android.view.ViewGroup;
import android.widget.ImageButton;
import android.widget.ImageView;

import androidx.annotation.DrawableRes;
import androidx.viewpager.widget.PagerAdapter;
import androidx.viewpager.widget.ViewPager;

import com.ingsw.provatab.R;
import com.ingsw.provatab.com.ingsw.model.Item;
import com.squareup.picasso.Picasso;

public class ImageAdapter extends PagerAdapter {
    Context mContext;
    Item item;
   public ImageAdapter(Context context, Item item) {
        this.mContext = context;
        this.item = item;
    }

    @Override
    public boolean isViewFromObject(View view, Object object) {
        return view == ((ImageView) object);
    }


    @Override
    public Object instantiateItem(ViewGroup container, int position) {
        ImageButton imageView = new ImageButton(mContext);
        imageView.setBackgroundColor(Color.WHITE);
        Picasso.get().load(item.getPath().get(position)).resize(600,600).centerInside().into(imageView);
        //imageView.setImageResource(sliderImageId[position]);
        ((ViewPager) container).addView(imageView, 0);
        return imageView;
    }

    @Override
    public void destroyItem(ViewGroup container, int position, Object object) {
        ((ViewPager) container).removeView((ImageView) object);
    }

    @Override
    public int getCount() {
        return item.getPath().size();
    }
}

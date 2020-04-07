package com.ingsw.provatab;

import android.annotation.SuppressLint;
import android.content.Intent;
import android.graphics.Color;
import android.graphics.drawable.Drawable;
import android.os.Bundle;
import android.view.Gravity;
import android.view.LayoutInflater;
import android.view.View;
import android.view.ViewGroup;
import android.widget.Button;
import android.widget.ImageButton;
import android.widget.LinearLayout;
import android.widget.ProgressBar;
import android.widget.RatingBar;
import android.widget.SearchView;
import android.widget.TextView;

import androidx.annotation.Nullable;
import androidx.core.content.ContextCompat;
import androidx.core.graphics.drawable.DrawableCompat;
import androidx.fragment.app.Fragment;
import com.ingsw.provatab.com.ingsw.model.Item;
import com.squareup.picasso.Picasso;

import java.util.List;

import retrofit2.Call;
import retrofit2.Callback;
import retrofit2.Response;


public class HomePage extends Fragment implements SearchView.OnQueryTextListener {
    private static final String TAG="HomePage";
    private ApiInterface apiInterface;
    private SearchView searchView;
    private ProgressBar spinner1;
    private ProgressBar spinner2;
    private LinearLayout linearLayout1, linearLayout2;

    @Nullable
    @Override
    public View onCreateView(LayoutInflater inflater, @Nullable ViewGroup container, @Nullable Bundle savedInstanceState) {
        View v = inflater.inflate(R.layout.fragment_homepage, container, false);
        apiInterface=ApiClient.getClient().create(ApiInterface.class);

        getLatestItems(v);
        getBestSellers(v);

        spinner1 = v.findViewById(R.id.progressBarHomePage);
        spinner2 = v.findViewById(R.id.progressBarHomePage2);
        linearLayout1 = v.findViewById(R.id.linear1);
        linearLayout2 = v.findViewById(R.id.linear2);


        Button btn = v.findViewById(R.id.AllItem);
        btn.setOnClickListener(new View.OnClickListener() {
            @Override
            public void onClick(View view) {
                Intent i = new Intent(getActivity(), AllItemActivity.class);
                startActivity(i);
            }
        });


        Button btnCategory = v.findViewById(R.id.buttonCategories);
        btnCategory.setOnClickListener(new View.OnClickListener() {
            @Override
            public void onClick(View view) {
                Intent i = new Intent(getActivity(), AllItemActivity.class);
                i.setFlags(Intent.FLAG_ACTIVITY_NO_ANIMATION);
                i.putExtra("spinnerValue", "Categoria");
                startActivity(i);


            }
        });

        Button btnOfferts = v.findViewById(R.id.buttonOfferts);
        btnOfferts.setOnClickListener(new View.OnClickListener() {
            @Override
            public void onClick(View view) {
                Intent i = new Intent(getActivity(), AllItemActivity.class);
                i.putExtra("itemOfferts", "itemOfferts");
                startActivity(i);


            }
        });

        searchView=v.findViewById(R.id.searchView);
        searchView.setOnQueryTextListener(this);
        searchView.setOnClickListener(new View.OnClickListener() {
            @Override
            public void onClick(View v) {
                searchView.setIconified(false);


            }
        });


        return v;
    }


    public void getLatestItems(final View v){

        Call<List<Item>> call=apiInterface.getLatestItems();
        call.enqueue(new Callback<List<Item>>() {
            @SuppressLint("SetTextI18n")
            @Override
            public void onResponse(Call<List<Item>> call, final Response<List<Item>> response) {
                if (response.body() != null) {

                    //immagine
                    final ImageButton imageButton = v.findViewById(R.id.imgButton1);
                    if (imageButton != null) {
                        Picasso.get().load(response.body().get(0).getPath().get(0)).resize(320, 310).centerInside().into(imageButton);
                        imageButton.setOnClickListener(new View.OnClickListener() {
                            @Override
                            public void onClick(View view) {
                                Intent i = new Intent(getActivity(), ViewItemActivity.class);
                                i.putExtra("item", response.body().get(0));
                                startActivity(i);
                            }
                        });
                    }
                    //nome
                    TextView name = v.findViewById(R.id.nameProduct1);
                    if (name != null) {
                        name.setText(response.body().get(0).getName());
                        name.setGravity(Gravity.CENTER);
                        name.setTextSize(15);
                    }
                    //prezzo
                    TextView price = v.findViewById(R.id.price1);
                    if (price != null) {

                        if (response.body().get(0).getOnSale()) {
                            TextView offer1 = v.findViewById(R.id.offer1);
                            offer1.setText("IN OFFERTA");
                            offer1.setBackgroundColor(getResources().getColor(R.color.colorPrimary, null));
                            offer1.setTextColor(Color.WHITE);
                            offer1.setBackground(getResources().getDrawable(R.drawable.button_bg_rounded_corners, null));
                            price.setText("€ " + Double.toString(response.body().get(0).getPrice() - (response.body().get(0).getPrice() * 0.2)) + "0");
                        } else
                            price.setText("€ " + Double.toString(response.body().get(0).getPrice()) + "0");
                        price.setGravity(Gravity.CENTER);
                        price.setTextSize(15);
                    }
                    //rating
                    RatingBar ratingBar = v.findViewById(R.id.stars1);
                    if (ratingBar != null) {

                        ratingBar.setRating(response.body().get(0).getRating());
                        Drawable progressDrawable = ratingBar.getProgressDrawable();
                        if (progressDrawable != null) {
                            DrawableCompat.setTint(progressDrawable, ContextCompat.getColor(getActivity(), R.color.colorPrimary));
                        }
                    }
                    //immagine
                    final ImageButton imageButton2 = v.findViewById(R.id.imgButton2);
                    if (imageButton2 != null) {

                        Picasso.get().load(response.body().get(1).getPath().get(0)).resize(320, 310).centerInside().into(imageButton2);
                        imageButton2.setOnClickListener(new View.OnClickListener() {
                            @Override
                            public void onClick(View view) {
                                Intent i = new Intent(getActivity(), ViewItemActivity.class);
                                i.putExtra("item", response.body().get(1));
                                startActivity(i);
                            }
                        });
                    }
                    //nome
                    TextView name2 = v.findViewById(R.id.nameProduct2);
                    if (name2 != null) {
                        name2.setText(response.body().get(1).getName());
                        name2.setGravity(Gravity.CENTER);
                        name2.setTextSize(15);
                    }
                    //prezzo
                    TextView price2 = v.findViewById(R.id.price2);
                    if (price2 != null) {
                        if (response.body().get(1).getOnSale()) {
                            TextView offer2 = v.findViewById(R.id.offer2);
                            offer2.setText("IN OFFERTA");
                            offer2.setBackgroundColor(getResources().getColor(R.color.colorPrimary, null));
                            offer2.setTextColor(Color.WHITE);
                            offer2.setBackground(getResources().getDrawable(R.drawable.button_bg_rounded_corners, null));
                            price2.setText("€ " + Double.toString(response.body().get(1).getPrice() - (response.body().get(1).getPrice() * 0.2)) + "0");
                        } else
                            price2.setText("€ " + Double.toString(response.body().get(1).getPrice()) + "0");
                        price2.setGravity(Gravity.CENTER);
                        price2.setTextSize(15);
                    }
                    //rating
                    RatingBar ratingBar2 = v.findViewById(R.id.stars2);
                    if (ratingBar2 != null) {
                        ratingBar2.setRating(response.body().get(1).getRating());
                        Drawable progressDrawable2 = ratingBar2.getProgressDrawable();
                        if (progressDrawable2 != null) {
                            DrawableCompat.setTint(progressDrawable2, ContextCompat.getColor(getActivity(), R.color.colorPrimary));
                        }
                    }
                    //immagine
                    final ImageButton imageButton3 = v.findViewById(R.id.imgButton3);
                    if (imageButton3 != null) {
                        Picasso.get().load(response.body().get(2).getPath().get(0)).resize(320, 310).centerInside().into(imageButton3);
                        imageButton3.setOnClickListener(new View.OnClickListener() {
                            @Override
                            public void onClick(View view) {
                                Intent i = new Intent(getActivity(), ViewItemActivity.class);
                                i.putExtra("item", response.body().get(2));
                                startActivity(i);
                            }
                        });
                    }
                    //nome
                    TextView name3 = v.findViewById(R.id.nameProduct3);
                    if (name3 != null) {
                        name3.setText(response.body().get(2).getName());
                        name3.setGravity(Gravity.CENTER);
                        name3.setTextSize(15);
                    }
                    //prezzo
                    TextView price3 = v.findViewById(R.id.price3);
                    if (price3 != null) {
                        if (response.body().get(2).getOnSale()) {
                            TextView offer3 = v.findViewById(R.id.offer3);
                            offer3.setText("IN OFFERTA");
                            offer3.setBackgroundColor(getResources().getColor(R.color.colorPrimary, null));
                            offer3.setTextColor(Color.WHITE);
                            offer3.setBackground(getResources().getDrawable(R.drawable.button_bg_rounded_corners, null));
                            price3.setText("€ " + Double.toString(response.body().get(2).getPrice() - (response.body().get(2).getPrice() * 0.2)) + "0");
                        } else
                            price3.setText("€ " + Double.toString(response.body().get(2).getPrice()) + "0");
                        price3.setGravity(Gravity.CENTER);
                        price3.setTextSize(15);
                    }
                    //rating
                    RatingBar ratingBar3 = v.findViewById(R.id.stars3);
                    if (ratingBar3 != null) {
                        ratingBar3.setRating(response.body().get(2).getRating());
                        Drawable progressDrawable3 = ratingBar3.getProgressDrawable();
                        if (progressDrawable3 != null) {
                            DrawableCompat.setTint(progressDrawable3, ContextCompat.getColor(getActivity(), R.color.colorPrimary));
                        }
                    }
                    //immagine

                        final ImageButton imageButton4 = v.findViewById(R.id.imgButton4);
                        if (imageButton4 != null) {
                            Picasso.get().load(response.body().get(3).getPath().get(0)).resize(320, 310).centerInside().into(imageButton4);
                            imageButton4.setOnClickListener(new View.OnClickListener() {
                                @Override
                                public void onClick(View view) {
                                    Intent i = new Intent(getActivity(), ViewItemActivity.class);
                                    i.putExtra("item", response.body().get(3));
                                    startActivity(i);
                                }
                            });
                        }


                        //nome
                        TextView name4 = v.findViewById(R.id.nameProduct4);
                        if (name4 != null) {
                            name4.setText(response.body().get(3).getName());
                            name4.setGravity(Gravity.CENTER);
                        }
                        //prezzo
                        TextView price4 = v.findViewById(R.id.price4);
                        if (price4 != null) {
                            if (response.body().get(3).getOnSale()) {
                                TextView offer4 = v.findViewById(R.id.offer4);
                                offer4.setText("IN OFFERTA");
                                offer4.setBackgroundColor(getResources().getColor(R.color.colorPrimary, null));
                                offer4.setTextColor(Color.WHITE);
                                offer4.setBackground(getResources().getDrawable(R.drawable.button_bg_rounded_corners, null));
                                price4.setText("€ " + Double.toString(response.body().get(3).getPrice() - (response.body().get(3).getPrice() * 0.2)) + "0");
                            } else
                                price4.setText("€ " + Double.toString(response.body().get(3).getPrice()) + "0");
                            price4.setGravity(Gravity.CENTER);
                            price4.setTextSize(15);
                        }
                        //rating
                        RatingBar ratingBar4 = v.findViewById(R.id.stars4);
                        if (ratingBar != null) {
                            ratingBar4.setRating(response.body().get(3).getRating());
                            Drawable progressDrawable4 = ratingBar4.getProgressDrawable();
                            if (progressDrawable4 != null) {
                                DrawableCompat.setTint(progressDrawable4, ContextCompat.getColor(getActivity(), R.color.colorPrimary));
                            }
                        }
                    if(spinner1!=null){
                        spinner1.setVisibility(View.GONE);
                    }
                    linearLayout1.setVisibility(View.VISIBLE);
                }
            }

            @Override
            public void onFailure(Call<List<Item>> call, Throwable t) {

            }
        });
    }
    public void getBestSellers(final View v){

        final Call<List<Item>> call=apiInterface.getBestSellers();
        call.enqueue(new Callback<List<Item>>() {
            @SuppressLint("SetTextI18n")
            @Override
            public void onResponse(Call<List<Item>> call, final Response<List<Item>> response) {

                //immagine
                final ImageButton imageButton = v.findViewById(R.id.imgButton5);
                if (imageButton != null) {
                    Picasso.get().load(response.body().get(0).getPath().get(0)).resize(320, 310).centerInside().into(imageButton);
                    imageButton.setOnClickListener(new View.OnClickListener() {
                        @Override
                        public void onClick(View view) {
                            Intent i = new Intent(getActivity(), ViewItemActivity.class);
                            i.putExtra("item", response.body().get(0));
                            startActivity(i);
                        }
                    });
                }
                //nome
                TextView name = v.findViewById(R.id.nameProduct5);
                if (name != null) {
                    name.setText(response.body().get(0).getName());
                    name.setGravity(Gravity.CENTER);
                    name.setTextSize(15);
                }
                //prezzo
                TextView price = v.findViewById(R.id.price5);
                if (price != null) {

                    if (response.body().get(0).getOnSale()) {
                        TextView offer1 = v.findViewById(R.id.offer5);
                        offer1.setText("IN OFFERTA");
                        offer1.setBackgroundColor(getResources().getColor(R.color.colorPrimary, null));
                        offer1.setTextColor(Color.WHITE);
                        offer1.setBackground(getResources().getDrawable(R.drawable.button_bg_rounded_corners, null));
                        price.setText("€ " + Double.toString(response.body().get(0).getPrice() - (response.body().get(0).getPrice() * 0.2)) + "0");
                    } else
                        price.setText("€ " + Double.toString(response.body().get(0).getPrice()) + "0");
                    price.setGravity(Gravity.CENTER);
                    price.setTextSize(15);
                }
                //rating
                RatingBar ratingBar = v.findViewById(R.id.stars5);
                if (ratingBar != null) {

                    ratingBar.setRating(response.body().get(0).getRating());
                    Drawable progressDrawable = ratingBar.getProgressDrawable();
                    if (progressDrawable != null) {
                        DrawableCompat.setTint(progressDrawable, ContextCompat.getColor(getActivity(), R.color.colorPrimary));
                    }
                }
                //immagine
                final ImageButton imageButton2 = v.findViewById(R.id.imgButton6);
                if (imageButton2 != null) {

                    Picasso.get().load(response.body().get(1).getPath().get(0)).resize(320, 310).centerInside().into(imageButton2);
                    imageButton2.setOnClickListener(new View.OnClickListener() {
                        @Override
                        public void onClick(View view) {
                            Intent i = new Intent(getActivity(), ViewItemActivity.class);
                            i.putExtra("item", response.body().get(1));
                            startActivity(i);
                        }
                    });
                }
                //nome
                TextView name2 = v.findViewById(R.id.nameProduct6);
                if (name2 != null) {
                    name2.setText(response.body().get(1).getName());
                    name2.setGravity(Gravity.CENTER);
                    name2.setTextSize(15);
                }
                //prezzo
                TextView price2 = v.findViewById(R.id.price6);
                if (price2 != null) {
                    if (response.body().get(1).getOnSale()) {
                        TextView offer2 = v.findViewById(R.id.offer6);
                        offer2.setText("IN OFFERTA");
                        offer2.setBackgroundColor(getResources().getColor(R.color.colorPrimary, null));
                        offer2.setTextColor(Color.WHITE);
                        offer2.setBackground(getResources().getDrawable(R.drawable.button_bg_rounded_corners, null));
                        price2.setText("€ " + Double.toString(response.body().get(1).getPrice() - (response.body().get(1).getPrice() * 0.2)) + "0");
                    } else
                        price2.setText("€ " + Double.toString(response.body().get(1).getPrice()) + "0");
                    price2.setGravity(Gravity.CENTER);
                    price2.setTextSize(15);
                }
                //rating
                RatingBar ratingBar2 = v.findViewById(R.id.stars6);
                if (ratingBar2 != null) {
                    ratingBar2.setRating(response.body().get(1).getRating());
                    Drawable progressDrawable2 = ratingBar2.getProgressDrawable();
                    if (progressDrawable2 != null) {
                        DrawableCompat.setTint(progressDrawable2, ContextCompat.getColor(getActivity(), R.color.colorPrimary));
                    }
                }
                //immagine
                final ImageButton imageButton3 = v.findViewById(R.id.imgButton7);
                if (imageButton3 != null) {
                    Picasso.get().load(response.body().get(2).getPath().get(0)).resize(320, 310).centerInside().into(imageButton3);
                    imageButton3.setOnClickListener(new View.OnClickListener() {
                        @Override
                        public void onClick(View view) {
                            Intent i = new Intent(getActivity(), ViewItemActivity.class);
                            i.putExtra("item", response.body().get(2));
                            startActivity(i);
                        }
                    });
                }
                //nome
                TextView name3 = v.findViewById(R.id.nameProduct7);
                if (name3 != null) {
                    name3.setText(response.body().get(2).getName());
                    name3.setGravity(Gravity.CENTER);
                    name3.setTextSize(15);
                }
                //prezzo
                TextView price3 = v.findViewById(R.id.price7);
                if (price3 != null) {
                    if (response.body().get(2).getOnSale()) {
                        TextView offer3 = v.findViewById(R.id.offer7);
                        offer3.setText("IN OFFERTA");
                        offer3.setBackgroundColor(getResources().getColor(R.color.colorPrimary, null));
                        offer3.setTextColor(Color.WHITE);
                        offer3.setBackground(getResources().getDrawable(R.drawable.button_bg_rounded_corners, null));
                        price3.setText("€ " + Double.toString(response.body().get(2).getPrice() - (response.body().get(2).getPrice() * 0.2)) + "0");
                    } else
                        price3.setText("€ " + Double.toString(response.body().get(2).getPrice()) + "0");
                    price3.setGravity(Gravity.CENTER);
                    price3.setTextSize(15);
                }
                //rating
                RatingBar ratingBar3 = v.findViewById(R.id.stars7);
                if (ratingBar3 != null) {
                    ratingBar3.setRating(response.body().get(2).getRating());
                    Drawable progressDrawable3 = ratingBar3.getProgressDrawable();
                    if (progressDrawable3 != null) {
                        DrawableCompat.setTint(progressDrawable3, ContextCompat.getColor(getActivity(), R.color.colorPrimary));
                    }
                }
                //immagine

                final ImageButton imageButton4 = v.findViewById(R.id.imgButton8);
                if (imageButton4 != null) {
                    Picasso.get().load(response.body().get(3).getPath().get(0)).resize(320, 310).centerInside().into(imageButton4);
                    imageButton4.setOnClickListener(new View.OnClickListener() {
                        @Override
                        public void onClick(View view) {
                            Intent i = new Intent(getActivity(), ViewItemActivity.class);
                            i.putExtra("item", response.body().get(3));
                            startActivity(i);
                        }
                    });
                }


                //nome
                TextView name4 = v.findViewById(R.id.nameProduct8);
                if (name4 != null) {
                    name4.setText(response.body().get(3).getName());
                    name4.setGravity(Gravity.CENTER);
                }
                //prezzo
                TextView price4 = v.findViewById(R.id.price8);
                if (price4 != null) {
                    if (response.body().get(3).getOnSale()) {
                        TextView offer4 = v.findViewById(R.id.offer8);
                        offer4.setText("IN OFFERTA");
                        offer4.setBackgroundColor(getResources().getColor(R.color.colorPrimary, null));
                        offer4.setTextColor(Color.WHITE);
                        offer4.setBackground(getResources().getDrawable(R.drawable.button_bg_rounded_corners, null));
                        price4.setText("€ " + Double.toString(response.body().get(3).getPrice() - (response.body().get(3).getPrice() * 0.2)) + "0");
                    } else
                        price4.setText("€ " + Double.toString(response.body().get(3).getPrice()) + "0");
                    price4.setGravity(Gravity.CENTER);
                    price4.setTextSize(15);
                }
                //rating
                RatingBar ratingBar4 = v.findViewById(R.id.stars8);
                if (ratingBar != null) {
                    ratingBar4.setRating(response.body().get(3).getRating());
                    Drawable progressDrawable4 = ratingBar4.getProgressDrawable();
                    if (progressDrawable4 != null) {
                        DrawableCompat.setTint(progressDrawable4, ContextCompat.getColor(getActivity(), R.color.colorPrimary));
                    }
                }
                if(spinner2!=null){
                    spinner2.setVisibility(View.GONE);
                }
                linearLayout2.setVisibility(View.VISIBLE);
            }


            @Override
            public void onFailure(Call<List<Item>> call, Throwable t) {

            }
        });
    }


    @Override
    public boolean onQueryTextSubmit(String query) {
        Intent i = new Intent(getActivity(), AllItemActivity.class);
        i.putExtra("searchItem",query);
        startActivity(i);
        return false;
    }

    @Override
    public boolean onQueryTextChange(String newText) {
        return false;
    }
}
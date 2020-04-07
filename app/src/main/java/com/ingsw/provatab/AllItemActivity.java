package com.ingsw.provatab;

import androidx.appcompat.app.AppCompatActivity;
import androidx.core.content.ContextCompat;
import androidx.core.graphics.drawable.DrawableCompat;
import android.app.Activity;
import android.content.Context;
import android.content.Intent;
import android.graphics.Color;
import android.graphics.Typeface;
import android.graphics.drawable.Drawable;
import android.os.Bundle;
import android.view.Gravity;
import android.view.View;
import android.view.ViewGroup;
import android.widget.Button;
import android.widget.ImageButton;
import android.widget.LinearLayout;
import android.widget.ProgressBar;
import android.widget.RatingBar;
import android.widget.ScrollView;
import android.widget.SearchView;
import android.widget.TextView;
import android.widget.Toast;
import com.ingsw.provatab.com.ingsw.model.Item;
import com.squareup.picasso.Picasso;
import java.util.ArrayList;
import java.util.List;

import retrofit2.Call;
import retrofit2.Callback;
import retrofit2.Response;

public class AllItemActivity extends AppCompatActivity implements SearchView.OnQueryTextListener{
    private ApiInterface apiInterface;
    private String filterType;
    protected List<Item> items;
    private ProgressBar spinner;

    @Override
    protected void onCreate(Bundle savedInstanceState) {
        setTitle("Tutti i prodotti");
        apiInterface=ApiClient.getClient().create(ApiInterface.class);
        super.onCreate(savedInstanceState);
        setContentView(R.layout.activity_all_item);
        assert getSupportActionBar() != null;   //null check
        getSupportActionBar().setDisplayHomeAsUpEnabled(true);   //show back button
        final SearchView searchView =findViewById(R.id.search);
        searchView.setOnClickListener(new View.OnClickListener() {
            @Override
            public void onClick(View v) {
                searchView.setIconified(false);
            }
        });
        searchView.setOnQueryTextListener(this);

        spinner = (ProgressBar)findViewById(R.id.progressBarAllItem);

        if (getIntent().getStringExtra("searchItem") != null) {
            searchView.setQuery(getIntent().getStringExtra("searchItem"), true);
        } else {
            getItems();
        }
        Button buttonFilters = findViewById(R.id.buttonFilter);
        buttonFilters.setOnClickListener(new View.OnClickListener() {
            @Override
            public void onClick(View view) {
                Intent i = new Intent(getApplicationContext(), FilterActivity.class);
                startActivityForResult(i, 0);
            }
        });
        TextView filtri = findViewById(R.id.filterType);
        filtri.setText("Tutti i prodotti");
        if (getIntent().getStringExtra("itemOfferts") != null) {
            filtri.setText("In offerta");
            getFilters("In offerta", null);
          }
        if (getIntent().getStringExtra("spinnerValue") != null) {
            Intent i = new Intent(getApplicationContext(), FilterActivity.class);
            i.putExtra("spinnerValue", "Categoria");
            startActivityForResult(i, 0);
            }
    }

    public void setItems(List<Item> response) {
        items = new ArrayList<>();
        for (Item i : response)
            items.add(i);
    }

    public void getItems() {
        Call<List<Item>> call = apiInterface.getItems();
        call.enqueue(new Callback<List<Item>>() {

            @Override

            public void onResponse(Call<List<Item>> call, final Response<List<Item>> response) {

                        if (response.body() != null) {


                            setItems(response.body());


                            //creo la scrollview
                            ScrollView scrollView = new ScrollView(getApplicationContext());
                            LinearLayout.LayoutParams params = new LinearLayout.LayoutParams(ViewGroup.LayoutParams.MATCH_PARENT, ViewGroup.LayoutParams.WRAP_CONTENT);
                            scrollView.setLayoutParams(params);
                            scrollView.setScrollBarSize(0);

                            //creo il parent linearlayout
                            LinearLayout parent = new LinearLayout(getApplicationContext());
                            LinearLayout.LayoutParams parentParams = new LinearLayout.LayoutParams(ViewGroup.LayoutParams.MATCH_PARENT, ViewGroup.LayoutParams.MATCH_PARENT);
                            parent.setLayoutParams(parentParams);

                            //creo la colonna sinistra
                            LinearLayout left = new LinearLayout(getApplicationContext());
                            LinearLayout.LayoutParams leftParams = new LinearLayout.LayoutParams(0, ViewGroup.LayoutParams.WRAP_CONTENT, 1);
                            left.setOrientation(LinearLayout.VERTICAL);
                            left.setLayoutParams(leftParams);
                            //creo la colonna destra
                            LinearLayout right = new LinearLayout(getApplicationContext());
                            LinearLayout.LayoutParams rightParams = new LinearLayout.LayoutParams(0, ViewGroup.LayoutParams.WRAP_CONTENT, 1);
                            right.setOrientation(LinearLayout.VERTICAL);
                            right.setLayoutParams(rightParams);

                            for (int i = 0; i < response.body().size(); i++) {
                                //creo il prodotto
                                //creo il linearlayout principale
                                LinearLayout product = new LinearLayout(getApplicationContext());
                                LinearLayout.LayoutParams productParams = new LinearLayout.LayoutParams(ViewGroup.LayoutParams.MATCH_PARENT, ViewGroup.LayoutParams.WRAP_CONTENT);
                                productParams.setMargins(20, 40, 20, 70);
                                product.setOrientation(LinearLayout.VERTICAL);
                                product.setLayoutParams(productParams);
                                //creo l'imgbutton
                                ImageButton image = new ImageButton(getApplicationContext());
                                image.setBackgroundColor(Color.WHITE);
                                LinearLayout.LayoutParams imageParams = new LinearLayout.LayoutParams(450, 450);
                                imageParams.gravity = Gravity.CENTER;
                                image.setLayoutParams(imageParams);
                                Picasso.get().load(response.body().get(i).getPath().get(0)).resize(450, 450).centerInside().into(image);
                                final int finalI = i;
                                image.setOnClickListener(new View.OnClickListener() {
                                    @Override
                                    public void onClick(View view) {
                                        Intent detail = new Intent(getApplicationContext(), ViewItemActivity.class);
                                        detail.putExtra("item", response.body().get(finalI));
                                        startActivity(detail);
                                    }
                                });

                                //creo il prezzo
                                TextView price = new TextView(getApplicationContext());
                                LinearLayout.LayoutParams priceParams = new LinearLayout.LayoutParams(ViewGroup.LayoutParams.WRAP_CONTENT, ViewGroup.LayoutParams.WRAP_CONTENT);
                                priceParams.gravity = Gravity.CENTER;
                                price.setLayoutParams(priceParams);
                                price.setTextSize(16);
                                price.setTypeface(null, Typeface.BOLD);
                                price.setGravity(Gravity.CENTER);
                                //creo l'offer
                                TextView offer = new TextView(getApplicationContext());
                                if (response.body().get(i).getOnSale()) {
                                    LinearLayout.LayoutParams offerParams = new LinearLayout.LayoutParams(300, ViewGroup.LayoutParams.WRAP_CONTENT);
                                    offerParams.setMargins(10, 0, 10, 0);
                                    offerParams.gravity = Gravity.CENTER;
                                    offer.setLayoutParams(offerParams);
                                    offer.setBackgroundColor(getResources().getColor(R.color.colorPrimary, null));
                                    offer.setBackground(getResources().getDrawable(R.drawable.button_bg_rounded_corners, null));
                                    offer.setTextColor(Color.WHITE);
                                    offer.setGravity(Gravity.CENTER);
                                    offer.setText("IN OFFERTA");
                                    price.setText("€ " + Double.toString(response.body().get(i).getPrice() - (response.body().get(i).getPrice() * 0.2)) + "0");
                                } else {
                                    price.setText("€ " + Double.toString(response.body().get(i).getPrice()) + "0");
                                }
                                //creo il nome
                                TextView name = new TextView(getApplicationContext());
                                LinearLayout.LayoutParams nameParams = new LinearLayout.LayoutParams(ViewGroup.LayoutParams.WRAP_CONTENT, ViewGroup.LayoutParams.WRAP_CONTENT);
                                nameParams.gravity = Gravity.CENTER;
                                name.setLayoutParams(nameParams);
                                name.setTextSize(16);
                                name.setMaxLines(1);
                                name.setTypeface(null, Typeface.BOLD);
                                name.setGravity(Gravity.CENTER);
                                name.setText(response.body().get(i).getName());
                                //creo il rating
                                RatingBar rating = new RatingBar(getApplicationContext(), null, android.R.attr.ratingBarStyleSmall);
                                Drawable progressDrawable = rating.getProgressDrawable();
                                if (progressDrawable != null) {
                                    DrawableCompat.setTint(progressDrawable, ContextCompat.getColor(getApplicationContext(), R.color.colorPrimary));
                                }
                                LinearLayout.LayoutParams ratingParams = new LinearLayout.LayoutParams(ViewGroup.LayoutParams.WRAP_CONTENT, ViewGroup.LayoutParams.WRAP_CONTENT);
                                ratingParams.gravity = Gravity.CENTER;
                                rating.setLayoutParams(ratingParams);
                                rating.setNumStars(5);
                                rating.setStepSize(1);
                                rating.setForegroundGravity(Gravity.CENTER);
                                rating.setRating(response.body().get(i).getRating());

                                //aggiungo ognuno di queste cose al linearlayout principale
                                product.addView(image);
                                if (response.body().get(i).getOnSale())
                                    product.addView(offer);
                                else
                                    product.addView(new TextView(getApplicationContext()));
                                product.addView(name);
                                product.addView(rating);
                                product.addView(price);
                                //aggiungo il linearlayout principale a sx o a dx a seconda dell'indice
                                if (i % 2 == 0) {
                                    left.addView(product);
                                } else {
                                    right.addView(product);
                                }
                            }
                            //aggiungo le colonne sx e dx al parent linearlayout
                            parent.addView(left);
                            parent.addView(right);
                            //aggiungo il parent linearlayout alla scrollview
                            scrollView.addView(parent);
                            //aggiungo la scrollview a ?
                            LinearLayout baseLayout = findViewById(R.id.baseLayout);
                            if (baseLayout != null)
                                baseLayout.addView(scrollView);

                            spinner.setVisibility(View.GONE);


                        }

                    }
                    @Override
                    public void onFailure(Call<List<Item>> call, Throwable t) {
                         Context context = getApplicationContext();
                        int duration = Toast.LENGTH_SHORT;

                        Toast toast = Toast.makeText(context, t.getLocalizedMessage(), duration);
                        toast.show();
                    }
                });

            }

    @Override
    public boolean onSupportNavigateUp(){
        finish();
        return true;
    }

    @Override
    public void onActivityResult(int requestCode, int resultCode, Intent data) {
        filterType="";
        super.onActivityResult(requestCode, resultCode, data);
        switch(requestCode) {
            case (0) : {
                if (resultCode == Activity.RESULT_OK) {

                    filterType =  data.getStringExtra("result");
                    TextView filtri = findViewById(R.id.filterType);
                    if(filterType==null){
                        filtri.setText("Tutti i prodotti");
                    }
                    else{
                        filtri.setText(filterType);
                    }
                    String [] temp = filterType.split(", ");
                    if(temp.length==2){
                        getFilters(temp[0],temp[1]);
                    }
                    else{
                        getFilters(temp[0],null);
                    }
                }
                break;
            }
        }
    }
    public void getFilters(String first, String second){
        Call<List<Item>> call = apiInterface.getFilters(first,second);
        call.enqueue(new Callback<List<Item>>() {
            @Override
            public void onResponse(Call<List<Item>> call, final Response<List<Item>> response) {
                if (response.body() != null) {

                    LinearLayout toDelete = findViewById(R.id.baseLayout);
                    toDelete.removeAllViews();
                    //creo la scrollview
                    ScrollView scrollView = new ScrollView(getApplicationContext());
                    LinearLayout.LayoutParams params = new LinearLayout.LayoutParams(ViewGroup.LayoutParams.MATCH_PARENT, ViewGroup.LayoutParams.WRAP_CONTENT);
                    scrollView.setLayoutParams(params);
                    scrollView.setScrollBarSize(0);
                    //creo il parent linearlayout
                    LinearLayout parent = new LinearLayout(getApplicationContext());
                    LinearLayout.LayoutParams parentParams = new LinearLayout.LayoutParams(ViewGroup.LayoutParams.MATCH_PARENT, ViewGroup.LayoutParams.MATCH_PARENT);
                    parent.setLayoutParams(parentParams);

                    //creo la colonna sinistra
                    LinearLayout left = new LinearLayout(getApplicationContext());
                    LinearLayout.LayoutParams leftParams = new LinearLayout.LayoutParams(0, ViewGroup.LayoutParams.WRAP_CONTENT, 1);
                    left.setOrientation(LinearLayout.VERTICAL);
                    left.setLayoutParams(leftParams);
                    //creo la colonna destra
                    LinearLayout right = new LinearLayout(getApplicationContext());
                    LinearLayout.LayoutParams rightParams = new LinearLayout.LayoutParams(0, ViewGroup.LayoutParams.WRAP_CONTENT, 1);
                    right.setOrientation(LinearLayout.VERTICAL);
                    right.setLayoutParams(rightParams);

                    if (response.body() != null) {
                        for (int i = 0; i < response.body().size(); i++) {
                            //creo il prodotto
                            //creo il linearlayout principale
                            LinearLayout product = new LinearLayout(getApplicationContext());
                            LinearLayout.LayoutParams productParams = new LinearLayout.LayoutParams(ViewGroup.LayoutParams.MATCH_PARENT, ViewGroup.LayoutParams.WRAP_CONTENT);
                            productParams.setMargins(20, 40, 20, 70);
                            product.setOrientation(LinearLayout.VERTICAL);
                            product.setLayoutParams(productParams);
                            //creo l'imgbutton
                            ImageButton image = new ImageButton(getApplicationContext());
                            image.setBackgroundColor(Color.WHITE);
                            LinearLayout.LayoutParams imageParams = new LinearLayout.LayoutParams(450, 450);
                            imageParams.gravity = Gravity.CENTER;
                            image.setLayoutParams(imageParams);
                            Picasso.get().load(response.body().get(i).getPath().get(0)).resize(450, 450).centerInside().into(image);
                            final int finalI = i;
                            image.setOnClickListener(new View.OnClickListener() {
                                @Override
                                public void onClick(View view) {
                                    Intent detail = new Intent(getApplicationContext(), ViewItemActivity.class);
                                    detail.putExtra("item", response.body().get(finalI));
                                    startActivity(detail);
                                }
                            });

                            //creo il prezzo
                            TextView price = new TextView(getApplicationContext());
                            LinearLayout.LayoutParams priceParams = new LinearLayout.LayoutParams(ViewGroup.LayoutParams.WRAP_CONTENT, ViewGroup.LayoutParams.WRAP_CONTENT);
                            priceParams.gravity = Gravity.CENTER;
                            price.setLayoutParams(priceParams);
                            price.setTextSize(16);
                            price.setTypeface(null, Typeface.BOLD);
                            price.setGravity(Gravity.CENTER);
                            //creo l'offer
                            TextView offer = new TextView(getApplicationContext());
                            if (response.body().get(i).getOnSale()) {
                                LinearLayout.LayoutParams offerParams = new LinearLayout.LayoutParams(300, ViewGroup.LayoutParams.WRAP_CONTENT);
                                offerParams.setMargins(10, 0, 10, 0);
                                offerParams.gravity = Gravity.CENTER;
                                offer.setLayoutParams(offerParams);
                                offer.setBackgroundColor(getResources().getColor(R.color.colorPrimary, null));
                                offer.setBackground(getResources().getDrawable(R.drawable.button_bg_rounded_corners, null));
                                offer.setTextColor(Color.WHITE);
                                offer.setGravity(Gravity.CENTER);
                                offer.setText("IN OFFERTA");
                                price.setText("€ " + Double.toString(response.body().get(i).getPrice() - (response.body().get(i).getPrice() * 0.2)) + "0");
                            } else {
                                price.setText("€ " + Double.toString(response.body().get(i).getPrice()) + "0");
                            }
                            //creo il nome
                            TextView name = new TextView(getApplicationContext());
                            LinearLayout.LayoutParams nameParams = new LinearLayout.LayoutParams(ViewGroup.LayoutParams.WRAP_CONTENT, ViewGroup.LayoutParams.WRAP_CONTENT);
                            nameParams.gravity = Gravity.CENTER;
                            name.setLayoutParams(nameParams);
                            name.setLayoutParams(nameParams);
                            name.setTextSize(16);
                            name.setMaxLines(1);
                            name.setTypeface(null, Typeface.BOLD);
                            name.setGravity(Gravity.CENTER);
                            name.setText(response.body().get(i).getName());
                            //creo il rating
                            RatingBar rating = new RatingBar(getApplicationContext(), null, android.R.attr.ratingBarStyleSmall);
                            Drawable progressDrawable = rating.getProgressDrawable();
                            if (progressDrawable != null) {
                                DrawableCompat.setTint(progressDrawable, ContextCompat.getColor(getApplicationContext(), R.color.colorPrimary));
                            }
                            LinearLayout.LayoutParams ratingParams = new LinearLayout.LayoutParams(ViewGroup.LayoutParams.WRAP_CONTENT, ViewGroup.LayoutParams.WRAP_CONTENT);
                            ratingParams.gravity = Gravity.CENTER;
                            rating.setLayoutParams(ratingParams);
                            rating.setNumStars(5);
                            rating.setStepSize(1);
                            rating.setForegroundGravity(Gravity.CENTER);
                            rating.setRating(response.body().get(i).getRating());

                            //aggiungo ognuno di queste cose al linearlayout principale
                            product.addView(image);
                            if (response.body().get(i).getOnSale())
                                product.addView(offer);
                            else
                                product.addView(new TextView(getApplicationContext()));
                            product.addView(name);
                            product.addView(rating);
                            product.addView(price);
                            //aggiungo il linearlayout principale a sx o a dx a seconda dell'indice
                            if (i % 2 == 0) {
                                left.addView(product);
                            } else {
                                right.addView(product);
                            }

                        }
                    }
                    //aggiungo le colonne sx e dx al parent linearlayout
                    parent.addView(left);
                    parent.addView(right);
                    //aggiungo il parent linearlayout alla scrollview
                    scrollView.addView(parent);
                    //aggiungo la scrollview a ?
                    LinearLayout baseLayout = findViewById(R.id.baseLayout);
                    if (baseLayout != null)
                        baseLayout.addView(scrollView);
                }
                spinner.setVisibility(View.GONE);

            }

            @Override
            public void onFailure(Call<List<Item>> call, Throwable t) {

            }
        });
    }

    public void getItemSearchResult(String itemSearch){
        Call<List<Item>> call = apiInterface.getItemSearchResult(itemSearch);
        call.enqueue(new Callback<List<Item>>() {
            @Override
            public void onResponse(Call<List<Item>> call, final Response<List<Item>> response) {
                if (response.body() != null) {

                    LinearLayout toDelete = findViewById(R.id.baseLayout);
                    toDelete.removeAllViews();
                    //creo la scrollview
                    ScrollView scrollView = new ScrollView(getApplicationContext());
                    LinearLayout.LayoutParams params = new LinearLayout.LayoutParams(ViewGroup.LayoutParams.MATCH_PARENT, ViewGroup.LayoutParams.WRAP_CONTENT);
                    scrollView.setLayoutParams(params);
                    scrollView.setScrollBarSize(0);
                    //creo il parent linearlayout
                    LinearLayout parent = new LinearLayout(getApplicationContext());
                    LinearLayout.LayoutParams parentParams = new LinearLayout.LayoutParams(ViewGroup.LayoutParams.MATCH_PARENT, ViewGroup.LayoutParams.MATCH_PARENT);
                    parent.setLayoutParams(parentParams);

                    //creo la colonna sinistra
                    LinearLayout left = new LinearLayout(getApplicationContext());
                    LinearLayout.LayoutParams leftParams = new LinearLayout.LayoutParams(0, ViewGroup.LayoutParams.WRAP_CONTENT, 1);
                    left.setOrientation(LinearLayout.VERTICAL);
                    left.setLayoutParams(leftParams);
                    //creo la colonna destra
                    LinearLayout right = new LinearLayout(getApplicationContext());
                    LinearLayout.LayoutParams rightParams = new LinearLayout.LayoutParams(0, ViewGroup.LayoutParams.WRAP_CONTENT, 1);
                    right.setOrientation(LinearLayout.VERTICAL);
                    right.setLayoutParams(rightParams);

                    if (response.body() != null) {
                        for (int i = 0; i < response.body().size(); i++) {
                            //creo il prodotto
                            //creo il linearlayout principale
                            LinearLayout product = new LinearLayout(getApplicationContext());
                            LinearLayout.LayoutParams productParams = new LinearLayout.LayoutParams(ViewGroup.LayoutParams.MATCH_PARENT, ViewGroup.LayoutParams.WRAP_CONTENT);
                            productParams.setMargins(20, 40, 20, 70);
                            product.setOrientation(LinearLayout.VERTICAL);
                            product.setLayoutParams(productParams);
                            //creo l'imgbutton
                            ImageButton image = new ImageButton(getApplicationContext());
                            image.setBackgroundColor(Color.WHITE);
                            LinearLayout.LayoutParams imageParams = new LinearLayout.LayoutParams(450, 450);
                            imageParams.gravity = Gravity.CENTER;
                            image.setLayoutParams(imageParams);
                            Picasso.get().load(response.body().get(i).getPath().get(0)).resize(450, 450).centerInside().into(image);
                            final int finalI = i;
                            image.setOnClickListener(new View.OnClickListener() {
                                @Override
                                public void onClick(View view) {
                                    Intent detail = new Intent(getApplicationContext(), ViewItemActivity.class);
                                    detail.putExtra("item", response.body().get(finalI));
                                    startActivity(detail);
                                }
                            });

                            //creo il prezzo
                            TextView price = new TextView(getApplicationContext());
                            LinearLayout.LayoutParams priceParams = new LinearLayout.LayoutParams(ViewGroup.LayoutParams.WRAP_CONTENT, ViewGroup.LayoutParams.WRAP_CONTENT);
                            priceParams.gravity = Gravity.CENTER;
                            price.setLayoutParams(priceParams);
                            price.setTextSize(16);
                            price.setTypeface(null, Typeface.BOLD);
                            price.setGravity(Gravity.CENTER);
                            //creo l'offer
                            TextView offer = new TextView(getApplicationContext());
                            if (response.body().get(i).getOnSale()) {
                                LinearLayout.LayoutParams offerParams = new LinearLayout.LayoutParams(300, ViewGroup.LayoutParams.WRAP_CONTENT);
                                offerParams.setMargins(10, 0, 10, 0);
                                offerParams.gravity = Gravity.CENTER;
                                offer.setLayoutParams(offerParams);
                                offer.setBackgroundColor(getResources().getColor(R.color.colorPrimary, null));
                                offer.setBackground(getResources().getDrawable(R.drawable.button_bg_rounded_corners, null));
                                offer.setTextColor(Color.WHITE);
                                offer.setGravity(Gravity.CENTER);
                                offer.setText("IN OFFERTA");
                                price.setText("€ " + Double.toString(response.body().get(i).getPrice() - (response.body().get(i).getPrice() * 0.2)) + "0");
                            } else {
                                price.setText("€ " + Double.toString(response.body().get(i).getPrice()) + "0");
                            }
                            //creo il nome
                            TextView name = new TextView(getApplicationContext());
                            LinearLayout.LayoutParams nameParams = new LinearLayout.LayoutParams(ViewGroup.LayoutParams.WRAP_CONTENT, ViewGroup.LayoutParams.WRAP_CONTENT);
                            nameParams.gravity = Gravity.CENTER;
                            name.setLayoutParams(nameParams);
                            name.setTextSize(16);
                            name.setMaxLines(1);
                            name.setTypeface(null, Typeface.BOLD);
                            name.setGravity(Gravity.CENTER);
                            name.setText(response.body().get(i).getName());
                            //creo il rating
                            RatingBar rating = new RatingBar(getApplicationContext(), null, android.R.attr.ratingBarStyleSmall);
                            Drawable progressDrawable = rating.getProgressDrawable();
                            if (progressDrawable != null) {
                                DrawableCompat.setTint(progressDrawable, ContextCompat.getColor(getApplicationContext(), R.color.colorPrimary));
                            }
                            LinearLayout.LayoutParams ratingParams = new LinearLayout.LayoutParams(ViewGroup.LayoutParams.WRAP_CONTENT, ViewGroup.LayoutParams.WRAP_CONTENT);
                            ratingParams.gravity = Gravity.CENTER;
                            rating.setLayoutParams(ratingParams);
                            rating.setNumStars(5);
                            rating.setStepSize(1);
                            rating.setForegroundGravity(Gravity.CENTER);
                            rating.setRating(response.body().get(i).getRating());

                            //aggiungo ognuno di queste cose al linearlayout principale
                            product.addView(image);
                            if (response.body().get(i).getOnSale())
                                product.addView(offer);
                            else
                                product.addView(new TextView(getApplicationContext()));
                            product.addView(name);
                            product.addView(rating);
                            product.addView(price);
                            //aggiungo il linearlayout principale a sx o a dx a seconda dell'indice
                            if (i % 2 == 0) {
                                left.addView(product);
                            } else {
                                right.addView(product);
                            }
                        }
                    }
                    //aggiungo le colonne sx e dx al parent linearlayout
                    parent.addView(left);
                    parent.addView(right);
                    //aggiungo il parent linearlayout alla scrollview
                    scrollView.addView(parent);
                    //aggiungo la scrollview a ?
                    LinearLayout baseLayout = findViewById(R.id.baseLayout);
                    if (baseLayout != null)
                        baseLayout.addView(scrollView);
                }else{
                    Context context = getApplicationContext();
                    CharSequence text = "Prodotto non trovato";
                    int duration = Toast.LENGTH_SHORT;

                    Toast toast = Toast.makeText(context, text, duration);
                    toast.show();
                }
                spinner.setVisibility(View.GONE);

            }


            @Override
            public void onFailure(Call<List<Item>> call, Throwable t) {

                Context context = getApplicationContext();
                int duration = Toast.LENGTH_SHORT;

                Toast toast = Toast.makeText(context, t.getLocalizedMessage() , duration);
                toast.show();



            }
        });

    }

    @Override
    public boolean onQueryTextSubmit(String query) {
            getItemSearchResult(query);
            return false;
    }

    @Override
    public boolean onQueryTextChange(String newText) {
        return false;
    }
}



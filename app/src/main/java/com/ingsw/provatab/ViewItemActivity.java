package com.ingsw.provatab;

import androidx.appcompat.app.AppCompatActivity;

import android.annotation.SuppressLint;
import android.content.Intent;
import android.content.SharedPreferences;
import android.graphics.Color;
import android.os.Bundle;
import android.text.Html;
import android.view.Menu;
import android.view.MenuItem;
import android.view.View;
import android.view.ViewGroup;
import android.view.animation.AlphaAnimation;
import android.view.animation.Animation;
import android.webkit.WebSettings;
import android.webkit.WebView;
import android.widget.AdapterView;
import android.widget.ArrayAdapter;
import android.widget.Button;
import android.widget.LinearLayout;
import android.widget.RatingBar;
import android.widget.Spinner;
import android.widget.TextView;
import android.widget.Toast;

import com.ingsw.provatab.com.ingsw.model.Cart;
import com.ingsw.provatab.com.ingsw.model.Item;
import com.ingsw.provatab.utils.ClickableViewPager;
import com.ingsw.provatab.utils.ImageAdapter;
import java.util.ArrayList;
import java.util.List;

import retrofit2.Call;
import retrofit2.Callback;
import retrofit2.Response;

public class ViewItemActivity extends AppCompatActivity implements
        AdapterView.OnItemSelectedListener {
    private Item item = null;
    private SharedPreferences sharedPreferences;
    private String colorSelected = "Colore";
    private String shippingSelected = "Spedizione";
    private Cart cart;
    private ApiInterface apiInterface;

    @SuppressLint("SetTextI18n")
    @Override
    protected void onCreate(Bundle savedInstanceState) {
        super.onCreate(savedInstanceState);
        assert getSupportActionBar() != null;   //null check
        getSupportActionBar().setDisplayHomeAsUpEnabled(true);   //show back button
        setContentView(R.layout.activity_view_item);

        sharedPreferences = getSharedPreferences("user_details", MODE_PRIVATE);
        cart = Cart.getCart();

        setTitle("Dettagli Prodotto");
        item = (Item) getIntent().getSerializableExtra("item");
        TextView name = findViewById(R.id.detailName);
        name.setText(item.getName());
        RatingBar ratingBar = findViewById(R.id.detailStars);
        ratingBar.setRating(item.getRating());
        ClickableViewPager mViewPager = findViewById(R.id.viewPage);

        apiInterface=ApiClient.getClient().create(ApiInterface.class);
        Call<Boolean> call=apiInterface.addClick(item.getID());
        call.enqueue(new Callback<Boolean>() {
            @Override
            public void onResponse(Call<Boolean> call, Response<Boolean> response) {

            }

            @Override
            public void onFailure(Call<Boolean> call, Throwable t) {

            }
        });


        ImageAdapter adapterView = new ImageAdapter(this, item);
        mViewPager.setAdapter(adapterView);
        TextView price = findViewById(R.id.detailPrice);
        if (!item.getOnSale()) {
            price.setText("€ " + item.getPrice() + "0");
        } else {
            TextView oldPrice = findViewById(R.id.offerPrice);
            oldPrice.setText(Html.fromHtml("<del>€ " + item.getPrice() + "0</del>", Html.FROM_HTML_MODE_COMPACT));
            price.setText("€" + (item.getPrice() - (item.getPrice() * 0.2)) + "0");
        }


        //ColorSpinner
        Spinner colors = findViewById(R.id.detailColors);
        colors.setOnItemSelectedListener(this);
        List<String> colorTemp = new ArrayList<>();
        colorTemp.add("Colore");
        colorTemp.addAll(item.getColors());
        ArrayAdapter<String> colorsArray = new ArrayAdapter<String>(this, R.layout.spinner_item, colorTemp) {
            @Override
            public boolean isEnabled(int position) {
                if (position == 0) {
                    return false;
                } else {
                    return true;
                }
            }

            @Override
            public View getDropDownView(int position, View convertView, ViewGroup parent) {
                View view = super.getDropDownView(position, convertView, parent);
                TextView tv = (TextView) view;
                if (position == 0) {
                    tv.setTextColor(Color.GRAY);
                } else {
                    tv.setTextColor(Color.BLACK);
                }
                return view;
            }
        };
        colorsArray.setDropDownViewResource(R.layout.spinner_item);
        colors.setAdapter(colorsArray);

        colors.setOnItemSelectedListener(new AdapterView.OnItemSelectedListener() {
            @Override
            public void onItemSelected(AdapterView<?> parent, View view, int position, long id) {
                String selectedItemText = (String) parent.getItemAtPosition(position);
                if (position > 0) {
                    colorSelected = selectedItemText;
                }
            }

            @Override
            public void onNothingSelected(AdapterView<?> parent) {

            }
        });
        //Shipping Spinner
        final Spinner shipping = findViewById(R.id.detailShipping);
        shipping.setOnItemSelectedListener(this);
        List<String> shippingTemp = new ArrayList<>();
        shippingTemp.add("Spedizione");
        shippingTemp.add("Standard (4/5 gg) Gratis");
        shippingTemp.add("Espressa (48/72 ore) €4.00");
        shippingTemp.add("Espressa (24 ore) €8.00");
        ArrayAdapter<String> shippingArray = new ArrayAdapter<String>(this, R.layout.spinner_item, shippingTemp) {
            @Override
            public boolean isEnabled(int position) {
                if (position == 0) {
                    return false;
                } else {
                    return true;
                }
            }

            @Override
            public View getDropDownView(int position, View convertView, ViewGroup parent) {
                View view = super.getDropDownView(position, convertView, parent);
                TextView tv = (TextView) view;
                if (position == 0) {
                    // Set the hint text color gray
                    tv.setTextColor(Color.GRAY);
                } else {
                    tv.setTextColor(Color.BLACK);
                }
                return view;
            }
        };
        shippingArray.setDropDownViewResource(R.layout.spinner_item);
        shipping.setAdapter(shippingArray);

        shipping.setOnItemSelectedListener(new AdapterView.OnItemSelectedListener() {
            @Override
            public void onItemSelected(AdapterView<?> parent, View view, int position, long id) {
                String selectedItemText = (String) parent.getItemAtPosition(position);
                if (position > 0) {
                    shippingSelected = selectedItemText;
                }
            }

            @Override
            public void onNothingSelected(AdapterView<?> parent) {

            }
        });

        WebView description = findViewById(R.id.WebDescription);
        WebSettings webSettings = description.getSettings();
        webSettings.setDefaultFontSize(13);
        description.loadData(item.getDescriptionHTML(), "text/html", "utf-8");

        TextView offer = findViewById((R.id.detailOffer));
        LinearLayout linearLayout = findViewById(R.id.layoutParent);
        if (item.getOnSale()) {
            offer.setText("IN OFFERTA");
            offer.setBackground(getResources().getDrawable(R.drawable.button_bg_rounded_corners, null));
            Animation anim = new AlphaAnimation(0.0f, 1.0f);
            anim.setDuration(1500); //You can manage the blinking time with this parameter
            anim.setStartOffset(20);
            anim.setRepeatMode(Animation.REVERSE);
            anim.setRepeatCount(Animation.INFINITE);
            offer.startAnimation(anim);
        } else {
            linearLayout.removeView(findViewById(R.id.layoutSale));
            linearLayout.removeView(findViewById(R.id.detailOffer));
        }


        /////buttonAddCart
        Button buttonAddCart = findViewById(R.id.buttonAddCart);

        buttonAddCart.setOnClickListener(new View.OnClickListener() {
            @Override
            public void onClick(View v) {
                if (!colorSelected.equals("Colore") && !shippingSelected.equals("Spedizione")) {

                    if (sharedPreferences.contains("username")) {

                        cart.addProductToCart(item, colorSelected, shippingSelected, sharedPreferences.getString("username", null));

                        Toast.makeText
                                (getApplicationContext(), "Prodotto aggiunto al carrello", Toast.LENGTH_SHORT)
                                .show();
                        Animation anim = new AlphaAnimation(0.0f, 1.0f);
                        anim.setDuration(1000); //You can manage the blinking time with this parameter
                        anim.setStartOffset(15);
                        anim.setRepeatMode(Animation.REVERSE);
                        anim.setRepeatCount(Animation.INFINITE);
                        findViewById(R.id.toCart).startAnimation(anim);

                    }else {
                        Intent i = new Intent(getApplicationContext(), LoginActivity.class);
                        startActivity(i);
                        Toast.makeText
                                (getApplicationContext(), "Effettua il login per aggiungere al carrello", Toast.LENGTH_SHORT)
                                .show();
                    }

                } else {
                    if (colorSelected.equals("Colore") && shippingSelected.equals("Spedizione")) {
                        Toast.makeText
                                (getApplicationContext(), "Selezionare colore e spedizione", Toast.LENGTH_SHORT)
                                .show();
                    } else if (colorSelected.equals("Colore")) {
                        Toast.makeText
                                (getApplicationContext(), "Colore non selezionato", Toast.LENGTH_SHORT)
                                .show();
                    } else {
                        Toast.makeText
                                (getApplicationContext(), "Spedizione non selezionata", Toast.LENGTH_SHORT)
                                .show();
                    }
                }
            }

        });


    }


    // create an action bar button
    @Override
    public boolean onCreateOptionsMenu(Menu menu) {
        getMenuInflater().inflate(R.menu.bottom_cart, menu);
        return super.onCreateOptionsMenu(menu);
    }

    // handle button activities
    @Override
    public boolean onOptionsItemSelected(MenuItem item) {
        if (item.getItemId() == R.id.toCart) {//from view item to profile fragment
            int position = 2;
            Intent intent = new Intent(getApplicationContext(), MainActivity.class);
            intent.putExtra("fragmentPosition", position);
            startActivity(intent);

            return true;
        }
        if(cart.getProducts().size()!=0){
            Animation anim = new AlphaAnimation(0.0f, 1.0f);
            anim.setDuration(1000); //You can manage the blinking time with this parameter
            anim.setStartOffset(15);
            anim.setRepeatMode(Animation.REVERSE);
            anim.setRepeatCount(Animation.INFINITE);
            findViewById(R.id.toCart).startAnimation(anim);
        }
        return super.onOptionsItemSelected(item);
    }

    @Override
    public void onItemSelected(AdapterView<?> arg0, View arg1, int position, long id) {
    }

    @Override
    public void onNothingSelected(AdapterView<?> arg0) {
    }

    @Override
    public boolean onSupportNavigateUp() {
        finish();
        return true;
    }
}


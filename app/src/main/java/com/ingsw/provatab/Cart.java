package com.ingsw.provatab;

import android.annotation.SuppressLint;
import android.content.Intent;
import android.graphics.Color;
import android.graphics.Typeface;
import android.net.Uri;
import android.os.Bundle;

import androidx.fragment.app.Fragment;

import android.util.Log;
import android.view.Gravity;
import android.view.LayoutInflater;
import android.view.View;
import android.view.ViewGroup;
import android.view.animation.AlphaAnimation;
import android.view.animation.Animation;
import android.widget.Button;
import android.widget.LinearLayout;
import android.widget.RelativeLayout;
import android.widget.TableLayout;
import android.widget.TextView;
import android.widget.Toast;

import androidx.annotation.Nullable;
import androidx.recyclerview.widget.LinearLayoutManager;
import androidx.recyclerview.widget.RecyclerView;

import com.braintreepayments.api.dropin.DropInActivity;
import com.braintreepayments.api.dropin.DropInRequest;
import com.braintreepayments.api.dropin.DropInResult;
import com.braintreepayments.api.models.PayPalRequest;
import com.ingsw.provatab.utils.CardAdapter;
import java.util.Date;

import retrofit2.Call;
import retrofit2.Callback;
import retrofit2.Response;

import static android.app.Activity.RESULT_CANCELED;
import static android.app.Activity.RESULT_OK;


public class Cart extends Fragment {


    private String clientToken;
    private ApiInterface apiInterface;
    public static final int PAYPAL_REQUEST_CODE = (int)Math.random()*100;

    private String paymentAmount;
    private com.ingsw.provatab.com.ingsw.model.Order order;
    private RecyclerView recyclerView;
    private CardAdapter adapter;
    final com.ingsw.provatab.com.ingsw.model.Cart cart= com.ingsw.provatab.com.ingsw.model.Cart.getCart();
    @SuppressLint("SetTextI18n")
    @Nullable
    @Override
    public View onCreateView(LayoutInflater inflater, @Nullable ViewGroup container, @Nullable Bundle savedInstanceState) {

        final View rootView = inflater.inflate(R.layout.fragment_cart, container, false);

        apiInterface = ApiClient.getClient().create(ApiInterface.class);



        RelativeLayout btt = new RelativeLayout(getActivity());
        RelativeLayout.LayoutParams parentParams = new RelativeLayout.LayoutParams(ViewGroup.LayoutParams.MATCH_PARENT, TableLayout.LayoutParams.MATCH_PARENT);
        btt.setLayoutParams(parentParams);
        parentParams.setMargins(0,100,0,100);
        parentParams.addRule(RelativeLayout.ALIGN_PARENT_BOTTOM);


        Button shopping = new Button(getActivity());
        shopping.setText("Torna allo shopping");
        shopping.setTextColor(Color.WHITE);
        shopping.setBackground(getResources().getDrawable(R.drawable.button_bg_rounded_corners, null));
        RelativeLayout.LayoutParams buttonShopping = new RelativeLayout.LayoutParams(450, 105);
        buttonShopping.setMargins(50, 50, 0, 0);
        shopping.setLayoutParams(buttonShopping);

        btt.addView(shopping);

        Button payment = new Button(getActivity());
        payment.setText("Vai al pagamento");
        payment.setTextColor(Color.WHITE);
        payment.setBackground(getResources().getDrawable(R.drawable.button_bg_rounded_corners, null));
        RelativeLayout.LayoutParams buttonPayment = new RelativeLayout.LayoutParams(450, 105);
        buttonPayment.setMargins(550, 50, 50, 0);

        payment.setLayoutParams(buttonPayment);
        btt.addView(payment);


        RelativeLayout relativeLayout=rootView.findViewById(R.id.bottom);


        if(cart.getProducts()!=null) {


            LinearLayout linear=new LinearLayout(getActivity());
            LinearLayout.LayoutParams layoutParams=new LinearLayout.LayoutParams(ViewGroup.LayoutParams.MATCH_PARENT, ViewGroup.LayoutParams.WRAP_CONTENT);
            linear.setLayoutParams(layoutParams);

            LinearLayout linearLayout1=new LinearLayout(getActivity());
            linearLayout1.setOrientation(LinearLayout.VERTICAL);
            LinearLayout.LayoutParams layoutParams11=new LinearLayout.LayoutParams(ViewGroup.LayoutParams.MATCH_PARENT, ViewGroup.LayoutParams.WRAP_CONTENT,1);
            linearLayout1.setLayoutParams(layoutParams11);

            LinearLayout linearLayout2=new LinearLayout(getActivity());
            linearLayout2.setOrientation(LinearLayout.VERTICAL);
            LinearLayout.LayoutParams layoutParams12=new LinearLayout.LayoutParams(ViewGroup.LayoutParams.MATCH_PARENT, ViewGroup.LayoutParams.WRAP_CONTENT,1);
            linearLayout2.setLayoutParams(layoutParams12);


            TextView textView1=new TextView(getActivity());

            LinearLayout.LayoutParams layoutParams1 = new LinearLayout.LayoutParams(ViewGroup.LayoutParams.MATCH_PARENT, ViewGroup.LayoutParams.WRAP_CONTENT);
            layoutParams1.setMargins(0, 20, 0, 20);
            textView1.setText("Spedizione");
            textView1.setGravity(Gravity.CENTER);
            textView1.setTypeface(null, Typeface.BOLD);
            textView1.setLayoutParams(layoutParams1);
            textView1.setTextSize(16);



            TextView totalPriceShipping=new TextView(getActivity());

            LinearLayout.LayoutParams layoutParams2 = new LinearLayout.LayoutParams(ViewGroup.LayoutParams.MATCH_PARENT, ViewGroup.LayoutParams.WRAP_CONTENT);
            layoutParams2.setMargins(0, 20, 0, 20);
            totalPriceShipping.setText("€ "+cart.getTotalShipping()+"0");
            totalPriceShipping.setGravity(Gravity.CENTER);
            totalPriceShipping.setLayoutParams(layoutParams2);
            totalPriceShipping.setTextSize(16);


            TextView textView=new TextView(getActivity());

            LinearLayout.LayoutParams layoutParams3 = new LinearLayout.LayoutParams(ViewGroup.LayoutParams.MATCH_PARENT, ViewGroup.LayoutParams.WRAP_CONTENT);
            layoutParams3.setMargins(0, 20, 0, 20);
            textView.setText("Prezzo");
            textView.setGravity(Gravity.CENTER);
            textView.setTypeface(null, Typeface.BOLD);
            textView.setLayoutParams(layoutParams3);
            textView.setTextSize(16);

            TextView totalPrice=new TextView(getActivity());

            LinearLayout.LayoutParams layoutParams4 = new LinearLayout.LayoutParams(ViewGroup.LayoutParams.MATCH_PARENT, ViewGroup.LayoutParams.WRAP_CONTENT);
            layoutParams3.setMargins(0,0,0,20);
            totalPrice.setText("€ "+cart.getTotalPrice()+"0");
            totalPrice.setGravity(Gravity.CENTER);
            totalPrice.setLayoutParams(layoutParams4);
            totalPrice.setTextSize(16);



            linearLayout1.addView(textView1);
            linearLayout2.addView(totalPriceShipping);
            linearLayout1.addView(textView);
            linearLayout2.addView(totalPrice);

            linear.addView(linearLayout1);
            linear.addView(linearLayout2);

            TextView mailForPayment=new TextView(getActivity());
            TextView passwordForPayment=new TextView(getActivity());

            LinearLayout linear2=new LinearLayout(getActivity());
            LinearLayout.LayoutParams layoutParams123=new LinearLayout.LayoutParams(ViewGroup.LayoutParams.MATCH_PARENT, ViewGroup.LayoutParams.WRAP_CONTENT);
            linear2.setLayoutParams(layoutParams123);
            linear2.setOrientation(LinearLayout.VERTICAL);
            layoutParams123.setMargins(100,200,100,150);
            linear2.setGravity(Gravity.CENTER);

            LinearLayout.LayoutParams layoutParams6 = new LinearLayout.LayoutParams(ViewGroup.LayoutParams.MATCH_PARENT, 100);
            mailForPayment.setText(" Mail per il pagamento: tester@ec18.com");
            mailForPayment.setGravity(Gravity.CENTER);
            mailForPayment.setTextColor(getResources().getColor(R.color.colorPrimary,null));
            mailForPayment.setLayoutParams(layoutParams6);
            mailForPayment.setTextSize(16);
            linear2.addView(mailForPayment);


            LinearLayout.LayoutParams layoutParams5 = new LinearLayout.LayoutParams(ViewGroup.LayoutParams.MATCH_PARENT, 100);
            passwordForPayment.setText(" Password per il pagamento: testec18");
            passwordForPayment.setGravity(Gravity.CENTER);
            passwordForPayment.setTextColor(getResources().getColor(R.color.colorPrimary,null));
            passwordForPayment.setLayoutParams(layoutParams5);
            passwordForPayment.setTextSize(16);
            linear2.addView(passwordForPayment);






            relativeLayout.addView(linear);
            relativeLayout.addView(linear2);

            recyclerView=rootView.findViewById(R.id.cart);
            recyclerView.setLayoutManager(new LinearLayoutManager(getActivity()));
            adapter=new CardAdapter(getActivity(), cart, totalPrice, totalPriceShipping);
            recyclerView.setAdapter(adapter);


            adapter.notifyDataSetChanged();

            if (cart.getProducts().size() == 0) {

                linear.removeAllViews();
                linear2.removeAllViews();
                payment.setEnabled(false);
                payment.setBackground(getResources().getDrawable(R.drawable.button_bg_off, null));
                TextView textView11=new TextView(getActivity());


                LinearLayout.LayoutParams layoutParams111 = new LinearLayout.LayoutParams(ViewGroup.LayoutParams.MATCH_PARENT, ViewGroup.LayoutParams.WRAP_CONTENT);
                layoutParams111.setMargins(0,100,0,0);
                textView11.setText("CARRELLO VUOTO");
                textView11.setGravity(Gravity.CENTER);
                textView11.setTextColor(getResources().getColor(R.color.colorPrimary,null));
                textView11.setTypeface(null, Typeface.BOLD);
                textView11.setLayoutParams(layoutParams111);
                textView11.setTextSize(20);
                Animation anim = new AlphaAnimation(0.0f, 1.0f);
                anim.setDuration(750); //You can manage the blinking time with this parameter
                anim.setStartOffset(10);
                anim.setRepeatMode(Animation.REVERSE);
                anim.setRepeatCount(Animation.INFINITE);
                textView11.startAnimation(anim);


                relativeLayout.addView(textView11);

            }

        }else{
            payment.setEnabled(true);
            payment.setBackground(getResources().getDrawable(R.drawable.button_bg_off, null));
            TextView textView=new TextView(getActivity());


            LinearLayout.LayoutParams layoutParams1 = new LinearLayout.LayoutParams(ViewGroup.LayoutParams.MATCH_PARENT, ViewGroup.LayoutParams.WRAP_CONTENT);
            layoutParams1.setMargins(0,100,0,0);
            textView.setText("CARRELLO VUOTO");
            textView.setGravity(Gravity.CENTER);
            textView.setTextColor(getResources().getColor(R.color.colorPrimary,null));
            textView.setTypeface(null, Typeface.BOLD);
            textView.setLayoutParams(layoutParams1);
            textView.setTextSize(20);
            Animation anim = new AlphaAnimation(0.0f, 1.0f);
            anim.setDuration(750); //You can manage the blinking time with this parameter
            anim.setStartOffset(10);
            anim.setRepeatMode(Animation.REVERSE);
            anim.setRepeatCount(Animation.INFINITE);
            textView.startAnimation(anim);


            relativeLayout.addView(textView);
        }

        relativeLayout.addView(btt);


        shopping.setOnClickListener(new View.OnClickListener() {
            @Override
            public void onClick(View v) {
                Intent i=new Intent(getActivity(), MainActivity.class);
                i.setFlags(Intent.FLAG_ACTIVITY_CLEAR_TOP | Intent.FLAG_ACTIVITY_NEW_TASK);
                startActivity(i);
            }
        });
        payment.setOnClickListener(new View.OnClickListener() {
            @Override
            public void onClick(View v) {
                if (cart.getProducts()!=null) {
                    order=new com.ingsw.provatab.com.ingsw.model.Order(cart);
                    order.setDate(getDecentDate(new Date().toString()));
                    paymentAmount=Double.toString(order.getTotalPrice());
                    Call<String> call=apiInterface.brainTreeGetToken();
                    call.enqueue(new Callback<String>() {
                        @Override
                        public void onResponse(Call<String> call, Response<String> response) {
                            if (response.body()!=null){
                                clientToken=response.body();

                                DropInRequest dropInRequest=new DropInRequest().clientToken(clientToken).disableCard()
                                        .paypalRequest(new PayPalRequest().billingAgreementDescription("Ordine n°"+
                                                order.getOrderN()+" - € "+order.getTotalPrice()+"0"));

                                startActivityForResult(dropInRequest.getIntent(getActivity()),PAYPAL_REQUEST_CODE);

                            } else {
                                Log.e("GUESS WHO'S NULL","Yeah,response.body()");
                            }
                        }

                        @Override
                        public void onFailure(Call<String> call, Throwable t) {

                        }
                    });

                }
            }
        });





        return rootView;
    }


    private String getDecentDate(String date){

        String day=date.substring(8,10);
        String month=date.substring(4,7);
        String year=date.substring(30,34);

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

    @Override
    public void onActivityResult(int requestCode, int resultCode, Intent data) {
        if (requestCode == PAYPAL_REQUEST_CODE) {
            final View progressOverlay;
            progressOverlay = getActivity().findViewById(R.id.progress_overlay);
            CardAdapter.animateView(progressOverlay, View.VISIBLE, 0.4f, 200);
            if (resultCode == RESULT_OK) {
                DropInResult result = data.getParcelableExtra(DropInResult.EXTRA_DROP_IN_RESULT);
                // use the result to update your UI and send the payment method nonce to your server
                Call<Boolean> call=apiInterface.brainTreePayment(result.getPaymentMethodNonce().getNonce(),paymentAmount);
                call.enqueue(new Callback<Boolean>() {
                    @Override
                    public void onResponse(Call<Boolean> call, Response<Boolean> response) {
                        if (response.body()!=null){
                            com.ingsw.provatab.com.ingsw.model.Cart.deleteCart();
                            Toast toast = Toast.makeText(getContext(), "Pagamento avvenuto con successo", Toast.LENGTH_SHORT);
                            toast.show();
                            //crea ordine
                            CardAdapter.animateView(progressOverlay, View.GONE, 0, 200);
                            Intent i=new Intent(getActivity(),MainActivity.class);
                            i.setFlags(Intent.FLAG_ACTIVITY_CLEAR_TOP | Intent.FLAG_ACTIVITY_NEW_TASK);
                            startActivity(i);
                            Call<Boolean> call2=apiInterface.payment(order);
                            call2.enqueue(new Callback<Boolean>() {
                                @Override
                                public void onResponse(Call<Boolean> call, Response<Boolean> response) {

                                }

                                @Override
                                public void onFailure(Call<Boolean> call, Throwable t) {

                                }
                            });
                        } else {
                            Toast toast = Toast.makeText(getContext(), "Errore del server", Toast.LENGTH_SHORT);
                            toast.show();
                            CardAdapter.animateView(progressOverlay, View.GONE, 0, 200);
                        }


                    }

                    @Override
                    public void onFailure(Call<Boolean> call, Throwable t) {
                        Toast toast = Toast.makeText(getContext(), "Errore del server", Toast.LENGTH_LONG);
                        toast.show();
                        CardAdapter.animateView(progressOverlay, View.GONE, 0, 200);
                    }
                });
            } else if (resultCode == RESULT_CANCELED) {
                Toast toast = Toast.makeText(getContext(), "Pagamento annullato", Toast.LENGTH_SHORT);
                toast.show();
                CardAdapter.animateView(progressOverlay, View.GONE, 0, 200);

            } else {
                // handle errors here, an exception may be available in
                Exception error = (Exception) data.getSerializableExtra(DropInActivity.EXTRA_ERROR);
                Toast toast = Toast.makeText(getContext(), "C'è stato un errore nel pagamento.", Toast.LENGTH_LONG);
                toast.show();
                CardAdapter.animateView(progressOverlay, View.GONE, 0, 200);
            }
        }
    }


}
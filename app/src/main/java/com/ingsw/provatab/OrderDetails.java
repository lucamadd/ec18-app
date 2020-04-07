package com.ingsw.provatab;

import android.annotation.SuppressLint;
import android.os.Bundle;
import android.view.View;
import android.widget.ProgressBar;

import androidx.appcompat.app.AppCompatActivity;
import androidx.recyclerview.widget.LinearLayoutManager;
import androidx.recyclerview.widget.RecyclerView;

import com.ingsw.provatab.com.ingsw.model.Order;
import com.ingsw.provatab.utils.OrderDetailsAdapter;

import retrofit2.Call;
import retrofit2.Callback;
import retrofit2.Response;


public class OrderDetails extends AppCompatActivity {
    private  ApiInterface apiInterface;
    private RecyclerView recyclerView;
    private OrderDetailsAdapter adapter;
    private ProgressBar spinner;

    @SuppressLint("SetTextI18n")
    @Override
    protected void onCreate(Bundle savedInstanceState) {
        super.onCreate(savedInstanceState);
        setContentView(R.layout.activity_details_order);
        setTitle("Dettagli ordine");
        assert getSupportActionBar() != null;   //null check
        getSupportActionBar().setDisplayHomeAsUpEnabled(true);

        apiInterface = ApiClient.getClient().create(ApiInterface.class);


        spinner=findViewById(R.id.progressBarDetailsOrder);



        int ordern = getIntent().getIntExtra("itemOrder",0);
        setTitle("Dettagli ordine n° " + ordern);
        Call<Order> call = apiInterface.getOrderByID(ordern);
        call.enqueue(new Callback<Order>() {
            @Override
            public void onResponse(Call<Order> call, Response<Order> response) {
                if (response.body() != null) {
                    recyclerView=findViewById(R.id.order_details);
                    recyclerView.setLayoutManager(new LinearLayoutManager(getApplicationContext()));
                    adapter=new OrderDetailsAdapter(getApplicationContext(), response.body());
                    recyclerView.setAdapter(adapter);


                    adapter.notifyDataSetChanged();
                    if(spinner!=null) {
                        spinner.setVisibility(View.GONE);
                    }





                }


            }

            @Override
            public void onFailure(Call<Order> call, Throwable t) {

            }
        });


    }




    public boolean onSupportNavigateUp(){
        finish();
        return true;
    }

}

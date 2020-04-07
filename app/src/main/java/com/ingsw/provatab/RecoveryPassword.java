package com.ingsw.provatab;

import android.content.Context;
import android.content.Intent;
import android.os.Bundle;
import android.util.Log;
import android.view.View;
import android.widget.Button;
import android.widget.EditText;
import android.widget.Toast;
import androidx.appcompat.app.AppCompatActivity;

import retrofit2.Call;
import retrofit2.Callback;
import retrofit2.Response;

public class RecoveryPassword extends AppCompatActivity {
    private ApiInterface apiInterface;
    private EditText rMail;
    private String mail;

    @Override
    protected void onCreate(Bundle savedInstanceState) {
        super.onCreate(savedInstanceState);
        setContentView(R.layout.activity_recovery_password);
        setTitle("Recupero password");
        assert getSupportActionBar() != null;   //null check
        getSupportActionBar().setDisplayHomeAsUpEnabled(true);

        apiInterface = ApiClient.getClient().create(ApiInterface.class);


        Button send=findViewById(R.id.sendMail);
        send.setOnClickListener(new View.OnClickListener() {
            @Override
            public void onClick(View v) {

                rMail=findViewById(R.id.mailrecoverypass);
                mail=rMail.getText().toString();
                Log.e("lkhflakf", mail);

                Call<Boolean> call = apiInterface.recoveryPassword(mail);
                call.enqueue(new Callback<Boolean>() {
                    @Override
                    public void onResponse(Call<Boolean> call, Response<Boolean> response) {
                        if (response.body() != null) {
                            if (response.body()) {
                                Context context = getApplicationContext();
                                CharSequence text = "Password temporanea inviata";
                                int duration = Toast.LENGTH_SHORT;

                                Toast toast = Toast.makeText(context, text, duration);
                                toast.show();
                                Intent intent = new Intent(getApplicationContext(), LoginActivity.class);
                                startActivity(intent);

                            } else {
                                Context context = getApplicationContext();
                                CharSequence text = "Mail non presente nel Database. Riprova!";
                                int duration = Toast.LENGTH_SHORT;

                                Toast toast = Toast.makeText(context, text, duration);
                                toast.show();


                            }
                        }else{
                            Log.e("ncaslkfhlas","jashla");
                        }
                    }

                    @Override
                    public void onFailure(Call<Boolean> call, Throwable t) {

                    }
                });

            }
        });








    }
    public boolean onSupportNavigateUp(){
        finish();
        return true;
    }
}

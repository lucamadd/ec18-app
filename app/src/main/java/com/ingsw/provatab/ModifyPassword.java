package com.ingsw.provatab;

import android.content.Context;
import android.content.Intent;
import android.content.SharedPreferences;
import android.os.Bundle;
import android.view.View;
import android.widget.Button;
import android.widget.EditText;
import android.widget.Toast;

import androidx.appcompat.app.AppCompatActivity;

import retrofit2.Call;
import retrofit2.Callback;
import retrofit2.Response;

public class ModifyPassword extends AppCompatActivity {

    private ApiInterface apiInterface;
    private EditText eCurrentPassword, eNewPassword, eConfirmNewPassword;
    private String currentPassword, newPassword, confirmNewPassword, sessionPass;

    private Button back,modify;
    private SharedPreferences sharedPreferences;
    private String sessionMail;

    @Override
    protected void onCreate(Bundle savedInstanceState) {
        super.onCreate(savedInstanceState);
        setContentView(R.layout.activity_modifiy_password);
        setTitle("Modifica password");
        assert getSupportActionBar() != null;   //null check
        getSupportActionBar().setDisplayHomeAsUpEnabled(true);

        apiInterface = ApiClient.getClient().create(ApiInterface.class);

        sharedPreferences=getSharedPreferences("user_details",MODE_PRIVATE);

        sessionMail=sharedPreferences.getString("username", null);

        sessionPass=sharedPreferences.getString("password", null);

        back=findViewById(R.id.returnBack);
        back.setOnClickListener(new View.OnClickListener() {
            @Override
            public void onClick(View v) {
                onBackPressed();
            }
        });

        modify=findViewById(R.id.modPassConfirm);
        modify.setOnClickListener(new View.OnClickListener() {
            @Override
            public void onClick(View v) {
                eCurrentPassword=findViewById(R.id.currentPass);
                currentPassword=eCurrentPassword.getText().toString();

                eNewPassword=findViewById(R.id.newPass);
                newPassword=eNewPassword.getText().toString();

                eConfirmNewPassword=findViewById(R.id.confiNewPass);
                confirmNewPassword=eConfirmNewPassword.getText().toString();

                if(!empty(currentPassword) && !empty(newPassword)&& !empty(confirmNewPassword)) {
                    if (!(sessionPass.equals(currentPassword))) {
                        Toast.makeText
                                (getApplicationContext(), "La password attuale è errata!", Toast.LENGTH_SHORT)
                                .show();
                        }
                    else  if (!newPassword.equals(confirmNewPassword)) {
                        Toast.makeText
                                (getApplicationContext(), "Le nuove password sono diverse", Toast.LENGTH_SHORT)
                                .show();
                    } else if(sessionPass.equals(newPassword)) {
                        Toast.makeText
                                (getApplicationContext(), "La password attuale e la nuova devono essere diverse", Toast.LENGTH_SHORT)
                                .show();

                    }
                        else{

                            Intent i=new Intent(getApplicationContext(),MainActivity.class);
                            i.setFlags(Intent.FLAG_ACTIVITY_CLEAR_TOP | Intent.FLAG_ACTIVITY_NEW_TASK);
                              startActivity(i);

                        Call<Boolean> call= apiInterface.modifyPassword(sessionMail,currentPassword,newPassword);
                        call.enqueue(new Callback<Boolean>() {
                            @Override
                            public void onResponse(Call<Boolean> call, Response<Boolean> response) {
                                if(response.body()!=null){
                                    if (response.body()) {
                                        Context context = getApplicationContext();
                                        CharSequence text = "Password modificata!";
                                        int duration = Toast.LENGTH_SHORT;

                                        Toast toast = Toast.makeText(context, text, duration);
                                        toast.show();
                                        Intent intent = new Intent(getApplicationContext(), LoginActivity.class);
                                        startActivity(intent);

                                    } else {
                                        Context context = getApplicationContext();
                                        CharSequence text = "Errore. Riprova!";
                                        int duration = Toast.LENGTH_SHORT;

                                        Toast toast = Toast.makeText(context, text, duration);
                                        toast.show();


                                    }
                                }
                            }

                            @Override
                            public void onFailure(Call<Boolean> call, Throwable t) {

                            }
                        });
                        Toast.makeText
                                    (getApplicationContext(), "La password è stata modificata!", Toast.LENGTH_SHORT)
                                    .show();
                        }
                }else{
                    Toast.makeText
                            (getApplicationContext(), "Uno o più campi sono vuoti", Toast.LENGTH_SHORT)
                            .show();
                }

            }
        });



    }


    public boolean onSupportNavigateUp(){
        finish();
        return true;
    }
    public static boolean empty( final String s ) {
        // Null-safe, short-circuit evaluation.
        return s == null || s.trim().isEmpty();
    }
}
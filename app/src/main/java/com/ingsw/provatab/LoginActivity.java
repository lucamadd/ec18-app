package com.ingsw.provatab;

import android.content.Context;
import android.content.Intent;
import android.content.SharedPreferences;
import android.os.Bundle;
import android.text.TextUtils;
import android.view.View;
import android.widget.Button;
import android.widget.EditText;
import android.widget.TextView;
import android.widget.Toast;

import androidx.appcompat.app.AppCompatActivity;

import retrofit2.Call;
import retrofit2.Callback;
import retrofit2.Response;

public class LoginActivity extends AppCompatActivity {

    private static final String TAG="LoginActivity";
    private ApiInterface apiInterface;
    private SharedPreferences sharedPreferences;

    @Override
    protected void onCreate(Bundle savedInstanceState) {
        super.onCreate(savedInstanceState);
        setContentView(R.layout.activity_login);
        setTitle("Login");
        assert getSupportActionBar() != null;   //null check
        getSupportActionBar().setDisplayHomeAsUpEnabled(true);   //show back button
        apiInterface=ApiClient.getClient().create(ApiInterface.class);

        sharedPreferences = getSharedPreferences("user_details", MODE_PRIVATE);
        if(sharedPreferences.contains("username") && sharedPreferences.contains("password")){
            Intent i = new Intent(getApplicationContext(),MainActivity.class);
            startActivity(i);
        }
        Button reg=findViewById(R.id.buttonRegister);
        reg.setOnClickListener(new View.OnClickListener() {
            @Override
            public void onClick(View v) {
                Intent i = new Intent(LoginActivity.this , RegisterActivity.class);
                startActivity(i);
            }
        });


        TextView recovery=findViewById(R.id.recoveryPassword);
        recovery.setClickable(true);
        recovery.setOnClickListener(new View.OnClickListener() {
            @Override
            public void onClick(View v) {
                Intent intent=new Intent(getApplicationContext(), RecoveryPassword.class);
                startActivity(intent);
            }
        });



    }

    @Override
    public void onBackPressed() {
        Intent i=new Intent(this,MainActivity.class);
        startActivity(i);
    }



    public void login(final View view) {




        final EditText eMail, ePassword;
        final String mail, password;

        eMail =findViewById(R.id.loginMail);
        ePassword =findViewById(R.id.loginPassword);

        mail = eMail.getText().toString();
        password = ePassword.getText().toString();

        if (!empty(mail) && !empty(password)) {

            if (isValidEmail(mail)) {
                Call<Boolean> call = apiInterface.login(mail, password);
                call.enqueue(new Callback<Boolean>() {
                    @Override
                    public void onResponse(Call<Boolean> call, Response<Boolean> response) {

                        if (response.body()) {
                            Context context = getApplicationContext();
                            CharSequence text = "Login effettuato con successo";
                            int duration = Toast.LENGTH_SHORT;

                            Toast toast = Toast.makeText(context, text, duration);
                            toast.show();

                            SharedPreferences.Editor editor=sharedPreferences.edit();
                            editor.putString("username",mail);
                            editor.putString("password",password) ;
                            editor.commit();

                            finish();

                        } else {

                            Context context = getApplicationContext();
                            CharSequence text = "Mail/Password errati";
                            int duration = Toast.LENGTH_SHORT;

                            Toast toast = Toast.makeText(context, text, duration);
                            toast.show();
                        }

                    }

                    @Override
                    public void onFailure(Call<Boolean> call, Throwable t) {
                        Context context = getApplicationContext();
                        int duration = Toast.LENGTH_SHORT;

                        Toast toast = Toast.makeText(context, t.getLocalizedMessage(), duration);
                        toast.show();


                    }
                });
            }
            else {
                Context context = getApplicationContext();
                CharSequence text = "Mail non valida";
                int duration = Toast.LENGTH_SHORT;

                Toast toast = Toast.makeText(context, text, duration);
                toast.show();
            }
        }
        else{
            Context context = getApplicationContext();
            CharSequence text = "Uno o più campi sono vuoti";
            int duration = Toast.LENGTH_SHORT;

            Toast toast = Toast.makeText(context, text, duration);
            toast.show();
        }


    }
    public static boolean empty( final String s ) {
        // Null-safe, short-circuit evaluation.
        return s == null || s.trim().isEmpty();
    }

    public final static boolean isValidEmail(CharSequence target) {
        return !TextUtils.isEmpty(target) && android.util.Patterns.EMAIL_ADDRESS.matcher(target).matches();
    }
    @Override
    public boolean onSupportNavigateUp(){
        finish();
        Intent intent=new Intent(this,MainActivity.class);
        startActivity(intent);
        return true;
    }
}

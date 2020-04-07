package com.ingsw.provatab;

import android.content.Context;
import android.content.Intent;
import android.graphics.Color;
import android.os.Bundle;
import android.text.TextUtils;
import android.view.View;
import android.view.ViewGroup;
import android.widget.AdapterView;
import android.widget.ArrayAdapter;
import android.widget.EditText;
import android.widget.Spinner;
import android.widget.TextView;
import android.widget.Toast;

import androidx.appcompat.app.AppCompatActivity;
import java.util.ArrayList;
import java.util.List;

import retrofit2.Call;
import retrofit2.Callback;
import retrofit2.Response;

public class RegisterActivity extends AppCompatActivity implements AdapterView.OnItemSelectedListener {

    private static final String TAG="RegisterActivity";
    private ApiInterface apiInterface;

    @Override
    protected void onCreate(Bundle savedInstanceState) {
        super.onCreate(savedInstanceState);
        setContentView(R.layout.activity_register);
        setTitle("Registrati");
        assert getSupportActionBar() != null;   //null check
        getSupportActionBar().setDisplayHomeAsUpEnabled(true);   //show back button
        apiInterface=ApiClient.getClient().create(ApiInterface.class);


        ////SPINNER PROVINCE
        Spinner provinceSpinner = findViewById(R.id.spinnerProvince);
        provinceSpinner.setOnItemSelectedListener(this);
        List<String> listProvince=new ArrayList<>();
        listProvince.add("Provincia");
        listProvince.add("AG");
        listProvince.add("AL");
        listProvince.add("AN");
        listProvince.add("AO");
        listProvince.add("AR");
        listProvince.add("AP");
        listProvince.add("AT");
        listProvince.add("AV");
        listProvince.add("BA");
        listProvince.add("BT");
        listProvince.add("BL");
        listProvince.add("BN");
        listProvince.add("BG");
        listProvince.add("BI");
        listProvince.add("BO");
        listProvince.add("BZ");
        listProvince.add("BS");
        listProvince.add("BR");
        listProvince.add("CA");
        listProvince.add("CL");
        listProvince.add("CB");
        listProvince.add("CI");
        listProvince.add("CE");
        listProvince.add("CT");
        listProvince.add("CZ");
        listProvince.add("CH");
        listProvince.add("CO");
        listProvince.add("CS");
        listProvince.add("CR");
        listProvince.add("KR");
        listProvince.add("CN");
        listProvince.add("EN");
        listProvince.add("FM");
        listProvince.add("FE");
        listProvince.add("FI");
        listProvince.add("FG");
        listProvince.add("FC");
        listProvince.add("FR");
        listProvince.add("GE");
        listProvince.add("GO");
        listProvince.add("GR");
        listProvince.add("IM");
        listProvince.add("IS");
        listProvince.add("AQ");
        listProvince.add("SP");
        listProvince.add("LT");
        listProvince.add("LE");
        listProvince.add("LC");
        listProvince.add("LI");
        listProvince.add("LO");
        listProvince.add("LU");
        listProvince.add("MC");
        listProvince.add("MN");
        listProvince.add("MS");
        listProvince.add("MT");
        listProvince.add("VS");
        listProvince.add("ME");
        listProvince.add("MI");
        listProvince.add("MO");
        listProvince.add("MB");
        listProvince.add("NA");
        listProvince.add("NO");
        listProvince.add("NU");
        listProvince.add("OG");
        listProvince.add("OT");
        listProvince.add("OR");
        listProvince.add("PD");
        listProvince.add("PA");
        listProvince.add("PR");
        listProvince.add("PV");
        listProvince.add("PG");
        listProvince.add("PS");
        listProvince.add("PE");
        listProvince.add("PC");
        listProvince.add("PI");
        listProvince.add("PT");
        listProvince.add("PN");
        listProvince.add("PZ");
        listProvince.add("PO");
        listProvince.add("RG");
        listProvince.add("RA");
        listProvince.add("RC");
        listProvince.add("RE");
        listProvince.add("RI");
        listProvince.add("RN");
        listProvince.add("RM");
        listProvince.add("RO");
        listProvince.add("SA");
        listProvince.add("SS");
        listProvince.add("SV");
        listProvince.add("SI");
        listProvince.add("SR");
        listProvince.add("SO");
        listProvince.add("TA");
        listProvince.add("TE");
        listProvince.add("TR");
        listProvince.add("TO");
        listProvince.add("TP");
        listProvince.add("TN");
        listProvince.add("TV");
        listProvince.add("TS");
        listProvince.add("UD");
        listProvince.add("VA");
        listProvince.add("VE");
        listProvince.add("VB");
        listProvince.add("VC");
        listProvince.add("VR");
        listProvince.add("VV");
        listProvince.add("VI");
        listProvince.add("VT");


        ArrayAdapter <String> filterArray = new ArrayAdapter<String>(this, R.layout.spinner_item,listProvince ){
            @Override
            public boolean isEnabled(int position){
                if(position == 0)
                {
                    // Disable the first item from Spinner
                    // First item will be use for hint
                    return false;
                }
                else
                {
                    return true;
                }
            }
            @Override
            public View getDropDownView(int position, View convertView, ViewGroup parent) {
                View view = super.getDropDownView(position, convertView, parent);
                TextView tv = (TextView) view;
                if(position == 0){
                    // Set the hint text color gray
                    tv.setTextColor(Color.GRAY);
                }
                else {
                    tv.setTextColor(Color.BLACK);
                }
                return view;
            }
        };





        filterArray.setDropDownViewResource(R.layout.spinner_item);
        provinceSpinner.setAdapter(filterArray);



        provinceSpinner.setOnItemSelectedListener(new AdapterView.OnItemSelectedListener() {
            public void onNothingSelected(AdapterView<?> arg0) {

            }
            @Override
            public void onItemSelected(AdapterView<?> parent, View view, int position, long id) {



                final String selectedItemText = (String) parent.getItemAtPosition(position);
                // If user change the default selection
                // First item is disable and it is used for hint
                if(position > 0) {

                }
            }
        });







    }

    public void registration(View view){
        EditText eName,eSurname, eAddress,eCity,eMail,ePassword,eCPassword;
        String name, surname, address, city, province, mail, password,cpassword;
        Spinner spinnerProvince;

        eName= findViewById(R.id.registerName);
        eSurname=findViewById(R.id.registerSurname);
        eAddress=findViewById(R.id.registerAddress);
        eCity=findViewById(R.id.registerCity);
        eMail=findViewById(R.id.registerEmail);
        ePassword=findViewById(R.id.registerPassword);
        eCPassword=findViewById(R.id.registerConfirmPassword);
        spinnerProvince=findViewById(R.id.spinnerProvince);




        name=eName.getText().toString();
        surname=eSurname.getText().toString();
        address=eAddress.getText().toString();
        city=eCity.getText().toString();
        mail=eMail.getText().toString();
        password=ePassword.getText().toString();
        cpassword=eCPassword.getText().toString();
        province=spinnerProvince.getSelectedItem().toString();


        //spinner province


        /////

        if( !empty(name) && !empty(surname) && !empty(address) && !empty(city) && !empty(province) && !empty(mail) && !empty(password) && !empty(cpassword)) {

            if (isValidEmail(mail)) {
                if (password.equals(cpassword)) {

                    Call<Boolean> call = apiInterface.registration(name, surname, address, city, province, mail, password);
                    call.enqueue(new Callback<Boolean>() {
                        @Override
                        public void onResponse(Call<Boolean> call, Response<Boolean> response) {
                            if (response.body()) {
                                Context context = getApplicationContext();
                                CharSequence text = "Utente con questa mail già presente";
                                int duration = Toast.LENGTH_SHORT;

                                Toast toast = Toast.makeText(context, text, duration);
                                toast.show();


                            } else {
                                Context context = getApplicationContext();
                                CharSequence text = "Registrazione effettuata con successo";
                                int duration = Toast.LENGTH_SHORT;

                                Toast toast = Toast.makeText(context, text, duration);
                                toast.show();

                                Intent i = new Intent(RegisterActivity.this, LoginActivity.class);
                                RegisterActivity.this.startActivity(i);

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
                } else {
                    Context context = getApplicationContext();
                    CharSequence text = "Le password sono diverse!";
                    int duration = Toast.LENGTH_SHORT;

                    Toast toast = Toast.makeText(context, text, duration);
                    toast.show();
                }
            }
            else {
                Context context = getApplicationContext();
                CharSequence text = "Mail non valida";
                int duration = Toast.LENGTH_SHORT;

                Toast toast = Toast.makeText(context, text, duration);
                toast.show();

            }
        }

        else {
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
        return true;
    }

    @Override
    public void onItemSelected(AdapterView<?> parent, View view, int position, long id) {

    }

    @Override
    public void onNothingSelected(AdapterView<?> parent) {

    }
}

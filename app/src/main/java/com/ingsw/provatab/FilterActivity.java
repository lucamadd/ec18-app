package com.ingsw.provatab;

import androidx.appcompat.app.AppCompatActivity;
import androidx.appcompat.widget.AppCompatSpinner;
import android.app.Activity;
import android.content.Intent;
import android.graphics.Color;
import android.os.Bundle;
import android.view.Gravity;
import android.view.View;
import android.view.ViewGroup;
import android.widget.AdapterView;
import android.widget.ArrayAdapter;
import android.widget.Button;
import android.widget.RelativeLayout;
import android.widget.Spinner;
import android.widget.TextView;
import java.util.ArrayList;
import java.util.List;

import retrofit2.Call;
import retrofit2.Callback;
import retrofit2.Response;

public class FilterActivity extends AppCompatActivity implements
        AdapterView.OnItemSelectedListener{
    private ApiInterface apiInterface;
    private String result;
    @Override
    protected void onCreate(Bundle savedInstanceState) {
        super.onCreate(savedInstanceState);
        setContentView(R.layout.activity_filter);

        apiInterface=ApiClient.getClient().create(ApiInterface.class);
        final RelativeLayout parent = new RelativeLayout(this);
        RelativeLayout.LayoutParams parentParams = new RelativeLayout.LayoutParams(ViewGroup.LayoutParams.MATCH_PARENT, ViewGroup.LayoutParams.MATCH_PARENT);
        parent.setLayoutParams(parentParams);

        setTitle("Seleziona filtri");
        assert getSupportActionBar() != null;   //null check
        getSupportActionBar().setDisplayHomeAsUpEnabled(true);   //show back button
        //Spinner 1
        Spinner filterTypeSpinner = findViewById(R.id.firstSpinner);
        filterTypeSpinner.setOnItemSelectedListener(this);

        final Button applyButton = new Button(this);
        applyButton.setEnabled(false);
        RelativeLayout.LayoutParams buttonApply = new RelativeLayout.LayoutParams(RelativeLayout.LayoutParams.MATCH_PARENT,130);
        buttonApply.addRule(RelativeLayout.ALIGN_PARENT_BOTTOM);
        buttonApply.setMargins(70, 0, 70, 70);
        applyButton.setGravity(Gravity.CENTER);
        applyButton.setLayoutParams(buttonApply);
        applyButton.setText("APPLICA");
        applyButton.setTextColor(Color.WHITE);
        applyButton.setBackground(getResources().getDrawable(R.drawable.button_bg_off,null));

        parent.addView(applyButton);


        RelativeLayout relativeLayout=findViewById(R.id.filterRelativeLayout);
        relativeLayout.addView(parent);

        List<String> filterTemp=new ArrayList<>();
        filterTemp.add("Scegli");
        filterTemp.add("Categoria");
        filterTemp.add("In offerta");
        filterTemp.add("Prezzo");
        filterTemp.add("Brand");
        filterTemp.add("Ultimi arrivi");
        filterTemp.add("Più venduti");



        ArrayAdapter <String> filterArray = new ArrayAdapter<String>(this, R.layout.spinner_item,filterTemp ){
            @Override
            public boolean isEnabled(int position){
                if(position == 0) {

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
        filterTypeSpinner.setAdapter(filterArray);
        if ((getIntent().getStringExtra("spinnerValue")!= null)){
            filterTypeSpinner.setSelection(filterArray.getPosition("Categoria"));
        }


        filterTypeSpinner.setOnItemSelectedListener(new AdapterView.OnItemSelectedListener() {
            public void onNothingSelected(AdapterView<?> arg0) {
                // TODO Auto-generated method stub
                result="Categoria";
            }
            @Override
            public void onItemSelected(AdapterView<?> parent, View view, int position, long id) {



                final String selectedItemText = (String) parent.getItemAtPosition(position);

                if(position > 0) {
                    // Notify the selected item text
                    AppCompatSpinner secondSpinner=findViewById(R.id.secondSpinner);
                    final List<String> selectedTemp = new ArrayList<>();
                    switch (selectedItemText) {
                        case "Categoria":
                            applyButton.setEnabled(false);
                            applyButton.setBackground(getResources().getDrawable(R.drawable.button_bg_off,null));
                            secondSpinner.setBackgroundColor(Color.LTGRAY);
                            secondSpinner.setForeground(getResources().getDrawable(R.drawable.spinner_bg));
                            selectedTemp.add("Scegli");
                            selectedTemp.add("Smartphone");
                            selectedTemp.add("Notebook");
                            selectedTemp.add("Accessori");
                            break;
                        case "Prezzo":
                            applyButton.setEnabled(false);
                            applyButton.setBackground(getResources().getDrawable(R.drawable.button_bg_off,null));
                            secondSpinner.setBackgroundColor(Color.LTGRAY);
                            secondSpinner.setForeground(getResources().getDrawable(R.drawable.spinner_bg));
                            selectedTemp.add("Scegli");
                            selectedTemp.add("€ 0-100");
                            selectedTemp.add("€ 100-200");
                            selectedTemp.add("€ 200-300");
                            selectedTemp.add("€ 300-400");
                            selectedTemp.add("€ 400-500");
                            selectedTemp.add("€ 500-600");
                            selectedTemp.add("€ 600-700");
                            selectedTemp.add("€ 700-800");
                            selectedTemp.add("€ 800-900");
                            selectedTemp.add("€ 900+");
                            break;
                        case "Brand":
                            applyButton.setEnabled(false);
                            applyButton.setBackground(getResources().getDrawable(R.drawable.button_bg_off,null));
                            secondSpinner.setBackgroundColor(Color.LTGRAY);
                            secondSpinner.setForeground(getResources().getDrawable(R.drawable.spinner_bg));
                            selectedTemp.add("Scegli");
                            Call<List<String>> call = apiInterface.getBrands();
                            call.enqueue(new Callback<List<String>>() {
                                @Override
                                public void onResponse(Call<List<String>> call, Response<List<String>> response) {
                                    for (String i:response.body()){
                                        selectedTemp.add(i);
                                    }

                                }

                                @Override
                                public void onFailure(Call<List<String>> call, Throwable t) {

                                }
                            });

                            break;

                            default:
                                secondSpinner.setBackgroundColor(Color.WHITE);
                                secondSpinner.setForeground(null);
                                break;

                    }


                    ArrayAdapter<String> selectedArray = new ArrayAdapter<String>(getApplicationContext(), R.layout.spinner_item, selectedTemp) {
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
                    selectedArray.setDropDownViewResource(R.layout.spinner_item);
                    secondSpinner.setAdapter(selectedArray);

                    secondSpinner.setOnItemSelectedListener(new AdapterView.OnItemSelectedListener() {
                        @Override
                        public void onItemSelected(AdapterView<?> parent, View view, int position, long id) {
                            String selectedItemText = (String) parent.getItemAtPosition(position);
                            if (position > 0) {
                                applyButton.setEnabled(true);
                                applyButton.setBackground(getResources().getDrawable(R.drawable.button_bg_rounded_corners,null));
                                result+=", "+selectedItemText;

                            }
                        }

                        @Override
                        public void onNothingSelected(AdapterView<?> parent) {

                        }
                    });

                    result = selectedItemText;
                    if(selectedItemText.equals("In offerta") || selectedItemText.equals("Ultimi arrivi") || selectedItemText.equals("Più venduti")){
                        applyButton.setEnabled(true);
                        applyButton.setBackground(getResources().getDrawable(R.drawable.button_bg_rounded_corners,null));
                    }

                }
            }
        });



        applyButton.setOnClickListener(new View.OnClickListener() {
            @Override
            public void onClick(View view) {
                Intent resultIntent = new Intent();
                resultIntent.putExtra("result",result);
                setResult(Activity.RESULT_OK, resultIntent);
                result="";
                finish();
            }
        });




    }


   


    @Override
    public void onItemSelected(AdapterView<?> arg0, View arg1, int position, long id) {
    }
    @Override
    public void onNothingSelected(AdapterView<?> arg0) {
        // TODO Auto-generated method stub
    }
    @Override
    public boolean onSupportNavigateUp(){
        finish();
        return true;
    }



    }


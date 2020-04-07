package com.ingsw.provatab;

import android.annotation.SuppressLint;
import android.content.Context;
import android.content.Intent;
import android.content.SharedPreferences;
import android.graphics.Color;
import android.graphics.Typeface;
import android.os.Bundle;
import android.view.Gravity;
import android.view.LayoutInflater;
import android.view.View;
import android.view.ViewGroup;
import android.view.animation.AlphaAnimation;
import android.view.animation.Animation;
import android.widget.AdapterView;
import android.widget.ArrayAdapter;
import android.widget.Button;
import android.widget.EditText;
import android.widget.LinearLayout;
import android.widget.ProgressBar;
import android.widget.RelativeLayout;
import android.widget.Spinner;
import android.widget.TextView;
import android.widget.Toast;

import androidx.annotation.Nullable;
import androidx.fragment.app.Fragment;
import androidx.recyclerview.widget.LinearLayoutManager;
import androidx.recyclerview.widget.RecyclerView;

import com.ingsw.provatab.com.ingsw.model.Customer;
import com.ingsw.provatab.com.ingsw.model.Order;
import com.ingsw.provatab.utils.OrderAdapter;

import java.util.ArrayList;
import java.util.List;

import retrofit2.Call;
import retrofit2.Callback;
import retrofit2.Response;

import static android.view.ViewGroup.LayoutParams;
import static android.view.ViewGroup.OnClickListener;
import static android.view.ViewGroup.VISIBLE;


public class Profile extends Fragment implements  AdapterView.OnItemSelectedListener {
    private SharedPreferences sharedPreferences;
    private ApiInterface apiInterface;
    private Button mod;
    private Button editData;
    private Button order;
    ///per i dati utente
    private EditText eName,eSurname, eAddress, eCity, eMail;
    private Spinner provinceSpinner;
    private String name, surname, address, city, province, mail;
    private ArrayAdapter<String> filterArray;
    //per gli ordini

    private RecyclerView recyclerView;
    private OrderAdapter adapter;
    private ProgressBar spinner;


    @Nullable
    @Override
    public View onCreateView(LayoutInflater inflater, @Nullable ViewGroup container, @Nullable Bundle savedInstanceState) {

        final View rootView = inflater.inflate(R.layout.fragment_profile, container, false);
        apiInterface=ApiClient.getClient().create(ApiInterface.class);

        spinner = (ProgressBar)rootView.findViewById(R.id.progressBarProfile);

        sharedPreferences=getActivity().getSharedPreferences("user_details", Context.MODE_PRIVATE);
        if(!sharedPreferences.contains("username") && !sharedPreferences.contains("password")){
            Intent i = new Intent(getActivity(),LoginActivity.class);
            startActivity(i);
            startActivity(i);
            Context context = getActivity();
            CharSequence text = "Effettua login!";
            int duration = Toast.LENGTH_SHORT;

            Toast toast = Toast.makeText(context, text, duration);
            toast.show();
        }


        mail=sharedPreferences.getString("username",null);
        getUserByMail(mail);


        modifyUser(rootView);


            ///LOGOUT
        Button logout = rootView.findViewById(R.id.buttonLogOut);
            logout.setOnClickListener(new View.OnClickListener() {
                @Override
                public void onClick(View v) {

                    SharedPreferences.Editor editor=sharedPreferences.edit();
                    editor.clear();
                    editor.commit();

                    Toast toast = Toast.makeText(getContext(), "Logout avvenuto", Toast.LENGTH_LONG);
                    toast.show();

                    Intent intent=new Intent(getActivity(),MainActivity.class);
                    startActivity(intent);
                    com.ingsw.provatab.com.ingsw.model.Cart.deleteCart();


                }
            });
            //////






            return  rootView;
        }

    private void getUserByMail(final String mail){
        Call<Customer> call=apiInterface.editUser(mail, name, surname, address, city,province);
        call.enqueue(new Callback<Customer>() {
            @Override
            public void onResponse(Call<Customer> call, final Response<Customer> response) {

                if (response.body() != null) {

                    eName.setText(response.body().getFirstName());
                    eSurname.setText(response.body().getLastName());
                    eAddress.setText(response.body().getAddress());
                    eCity.setText(response.body().getCity());
                    eMail.setText(response.body().getEmail());

                    if (response.body().getProvince() != null) {
                        int spinnerPosition = filterArray.getPosition(response.body().getProvince());
                        provinceSpinner.setSelection(spinnerPosition);
                    }


                }

            }

            @Override
            public void onFailure(Call<Customer> call, Throwable t) {

            }
        });


    }

    @SuppressLint("SetTextI18n")
    private void modifyUser(final View view){


        final RelativeLayout parent = new RelativeLayout(getActivity());
        RelativeLayout.LayoutParams parentParams = new RelativeLayout.LayoutParams(LayoutParams.MATCH_PARENT, LayoutParams.MATCH_PARENT);
        parent.setLayoutParams(parentParams);
        editData=new Button(getActivity());
        editData.setText("DATI UTENTE");
        editData.setEnabled(false);
        editData.setTextColor(Color.WHITE);
        editData.setBackground(getResources().getDrawable(R.drawable.button_bg_off, null));
        LinearLayout.LayoutParams buttonEdit = new LinearLayout.LayoutParams(450,105);
        buttonEdit.setMargins(50, 0, 0, 40);
        editData.setLayoutParams(buttonEdit);
        parent.addView(editData);

        order=new Button(getActivity());
        order.setText("ORDINI EFFETTUATI");
        order.setTextColor(Color.WHITE);
        order.setBackground(getResources().getDrawable(R.drawable.button_bg_rounded_corners, null));
        LinearLayout.LayoutParams buttonOrder = new LinearLayout.LayoutParams(450, 105);
        buttonOrder.setMargins(550, 0, 0, 40);
        order.setLayoutParams(buttonOrder);
        parent.addView(order);





        order.setOnClickListener(new OnClickListener() {
            @Override
            public void onClick(View v) {


                parent.removeAllViews();

                RelativeLayout toDelete=view.findViewById(R.id.profile);
                toDelete.removeAllViews();

                orderPage(view);

            }
        });


        TextView tName = new TextView(getActivity());
        TextView tSurname = new TextView(getActivity());
        TextView tAddress = new TextView(getActivity());
        TextView tCity = new TextView(getActivity());
        TextView tProvince = new TextView(getActivity());
        TextView tMail = new TextView(getActivity());


        eName=new EditText(getActivity());
        eSurname=new EditText(getActivity());
        eAddress=new EditText(getActivity());
        eCity=new EditText(getActivity());
        provinceSpinner = new Spinner(getActivity());
        eMail=new EditText(getActivity());
        mod= new Button(getActivity());


        Customer customer=new Customer();
        getUserByMail(mail);
        eName.setText(customer.getFirstName());
        eSurname.setText(customer.getLastName());
        eAddress.setText(customer.getAddress());
        eCity.setText(customer.getCity());
        if (customer.getProvince() != null) {
            int spinnerPosition = filterArray.getPosition(customer.getProvince());
            provinceSpinner.setSelection(spinnerPosition);
        }
        eMail.setText(customer.getEmail());



        tName.setText("Nome");
        LinearLayout.LayoutParams nameParams = new LinearLayout.LayoutParams(ViewGroup.LayoutParams.WRAP_CONTENT, ViewGroup.LayoutParams.WRAP_CONTENT);
        nameParams.gravity = Gravity.CENTER;
        nameParams.setMargins(60, 170, 20, 70);
        tName.setLayoutParams(nameParams);
        tName.setTextSize(16);
        tName.setTypeface(null, Typeface.BOLD);
        tName.setGravity(Gravity.CENTER);

        LinearLayout.LayoutParams eNameParams = new LinearLayout.LayoutParams(700, 100);
        eNameParams.gravity = Gravity.CENTER;
        eNameParams.setMargins(60, 240, 20, 70);
        eName.setLayoutParams(eNameParams);
        eName.setTextSize(14);
        eName.setBackgroundColor(Color.LTGRAY);
        eName.setSingleLine(true);
        eName.setEnabled(false);


        tSurname.setText("Cognome");
        LinearLayout.LayoutParams surnameParams = new LinearLayout.LayoutParams(ViewGroup.LayoutParams.WRAP_CONTENT, ViewGroup.LayoutParams.WRAP_CONTENT);
        surnameParams.gravity = Gravity.CENTER;
        surnameParams.setMargins(60, 350, 20, 70);
        tSurname.setLayoutParams(surnameParams);
        tSurname.setTextSize(16);
        tSurname.setTypeface(null, Typeface.BOLD);
        tSurname.setGravity(Gravity.CENTER);

        LinearLayout.LayoutParams eSurnameParams = new LinearLayout.LayoutParams(700, 100);
        eSurnameParams.gravity = Gravity.CENTER;
        eSurnameParams.setMargins(60, 410, 20, 70);
        eSurname.setLayoutParams(eSurnameParams);
        eSurname.setTextSize(14);
        eSurname.setBackgroundColor(Color.LTGRAY);
        eSurname.setSingleLine(true);
        eSurname.setEnabled(false);


        tAddress.setText("Indirizzo");
        LinearLayout.LayoutParams addressParams = new LinearLayout.LayoutParams(ViewGroup.LayoutParams.WRAP_CONTENT, ViewGroup.LayoutParams.WRAP_CONTENT);
        addressParams.gravity = Gravity.CENTER;
        addressParams.setMargins(60, 530, 20, 70);
        tAddress.setLayoutParams(addressParams);
        tAddress.setTextSize(16);
        tAddress.setTypeface(null, Typeface.BOLD);
        tAddress.setGravity(Gravity.CENTER);

        LinearLayout.LayoutParams eAddressParams = new LinearLayout.LayoutParams(700, 100);
        eAddressParams.gravity = Gravity.CENTER;
        eAddressParams.setMargins(60, 590, 20, 70);
        eAddress.setLayoutParams(eAddressParams);
        eAddress.setBackgroundColor(Color.LTGRAY);
        eAddress.setTextSize(14);
        eAddress.setSingleLine(true);
        eAddress.setEnabled(false);



        tCity.setText("Città");
        LinearLayout.LayoutParams cityParams = new LinearLayout.LayoutParams(ViewGroup.LayoutParams.WRAP_CONTENT, ViewGroup.LayoutParams.WRAP_CONTENT);
        cityParams.gravity = Gravity.CENTER;
        cityParams.setMargins(60, 700, 20, 70);
        tCity.setLayoutParams(cityParams);
        tCity.setTextSize(16);
        tCity.setTypeface(null, Typeface.BOLD);
        tCity.setGravity(Gravity.CENTER);

        LinearLayout.LayoutParams eCityParams = new LinearLayout.LayoutParams(700, 100);
        eCityParams.gravity = Gravity.CENTER;
        eCityParams.setMargins(60, 760, 20, 70);
        eCity.setLayoutParams(eCityParams);
        eCity.setTextSize(14);
        eCity.setBackgroundColor(Color.LTGRAY);
        eCity.setSingleLine(true);
        eCity.setEnabled(false);



        tProvince.setText("Provincia");
        LinearLayout.LayoutParams provinceParams = new LinearLayout.LayoutParams(ViewGroup.LayoutParams.WRAP_CONTENT, ViewGroup.LayoutParams.WRAP_CONTENT);
        provinceParams.gravity = Gravity.CENTER;
        provinceParams.setMargins(60, 870, 20, 70);
        tProvince.setLayoutParams(provinceParams);
        tProvince.setTextSize(16);
        tProvince.setTypeface(null, Typeface.BOLD);
        tProvince.setGravity(Gravity.CENTER);

        LinearLayout.LayoutParams eprovinceParams = new LinearLayout.LayoutParams(700, 100);
        eprovinceParams.gravity = Gravity.CENTER;
        eprovinceParams.setMargins(60, 930, 20, 70);
        provinceSpinner.setLayoutParams(eprovinceParams);
        provinceSpinner.setOnItemSelectedListener(this);
        List<String> listProvince=new ArrayList<>();
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
        provinceSpinner.setBackgroundColor(Color.LTGRAY);
        provinceSpinner.setForeground(getResources().getDrawable(R.drawable.spinner_bg));
        provinceSpinner.setEnabled(false);



        tMail.setText("Mail");
        LinearLayout.LayoutParams mailParams = new LinearLayout.LayoutParams(ViewGroup.LayoutParams.WRAP_CONTENT, ViewGroup.LayoutParams.WRAP_CONTENT);
        mailParams.gravity = Gravity.CENTER;
        mailParams.setMargins(60, 1040, 20, 70);
        tMail.setLayoutParams(mailParams);
        tMail.setTextSize(16);
        tMail.setTypeface(null, Typeface.BOLD);
        tMail.setGravity(Gravity.CENTER);

        LinearLayout.LayoutParams eMailParamas = new LinearLayout.LayoutParams(700, 100);
        eMailParamas.gravity = Gravity.CENTER;
        eMailParamas.setMargins(60, 1110, 20, 70);
        eMail.setLayoutParams(eMailParamas);
        eMail.setTextSize(14);
        eMail.setEnabled(false);
        eMail.setBackgroundColor(Color.LTGRAY);
        eMail.setSingleLine(true);
        eName.setEnabled(false);
        eSurname.setEnabled(false);
        eAddress.setEnabled(false);
        eCity.setEnabled(false);
        provinceSpinner.setEnabled(false);

        mod.setText("MODIFICA");
        mod.setTextColor(Color.WHITE);
        mod.setBackground(getResources().getDrawable(R.drawable.button_bg_rounded_corners, null));
        LinearLayout.LayoutParams buttonMod = new LinearLayout.LayoutParams(250, 105);
        buttonMod.setMargins(650, 1290, 0, 40);
        mod.setLayoutParams(buttonMod);



        parent.addView(tName);
        parent.addView(tSurname);
        parent.addView(tAddress);
        parent.addView(tCity);
        parent.addView(tProvince);
        parent.addView(tMail);

        parent.addView(eName);
        parent.addView(eSurname);
        parent.addView(eAddress);
        parent.addView(eCity);
        parent.addView(provinceSpinner);
        parent.addView(eMail);

        parent.addView(mod);


        RelativeLayout relativeLayout2=view.findViewById(R.id.profile);
        relativeLayout2.addView(parent);


        mod.setOnClickListener(new View.OnClickListener() {
            @Override
            public void onClick(View v) {
                mod.setVisibility(View.GONE); //INVISIBLE LO TOGLIE SOLO VISIVAMENTE
                eName.setEnabled(true);
                eSurname.setEnabled(true);
                eAddress.setEnabled(true);
                eCity.setEnabled(true);
                provinceSpinner.setEnabled(true);


                final LinearLayout parent = new LinearLayout(getActivity());
                RelativeLayout.LayoutParams parentParams = new RelativeLayout.LayoutParams(LayoutParams.MATCH_PARENT, LayoutParams.MATCH_PARENT);
                parent.setLayoutParams(parentParams);



                final Button back=new Button(getActivity());
                back.setText("ANNULLA");
                back.setTextColor(Color.WHITE);
                back.setBackground(getResources().getDrawable(R.drawable.button_bg_rounded_corners, null));
                LinearLayout.LayoutParams buttonback = new LinearLayout.LayoutParams(250, 105);
                buttonback.setMargins(150, 1290, 0, 40);
                back.setLayoutParams(buttonback);
                parent.addView(back);

                final Button save=new Button(getActivity());
                save.setText("SALVA");
                save.setTextColor(Color.WHITE);
                save.setBackground(getResources().getDrawable(R.drawable.button_bg_rounded_corners, null));
                LinearLayout.LayoutParams button = new LinearLayout.LayoutParams(250, 105);
                button.setMargins(290, 1290, 0, 40);
                save.setLayoutParams(button);
                parent.addView(save);

                final LinearLayout parent2 = new LinearLayout(getActivity());
                RelativeLayout.LayoutParams parentParams2 = new RelativeLayout.LayoutParams(LayoutParams.MATCH_PARENT, LayoutParams.MATCH_PARENT);
                parent2.setLayoutParams(parentParams2);


                final Button modPassword=new Button(getActivity());
                modPassword.setText("Modifica password");
                modPassword.setTextColor(Color.WHITE);
                modPassword.setBackground(getResources().getDrawable(R.drawable.button_bg_rounded_corners, null));
                LinearLayout.LayoutParams buttonModPass = new LinearLayout.LayoutParams(600,105);
                buttonModPass.setMargins(150, 1450, 0, 80);
                modPassword.setLayoutParams(buttonModPass);

                parent2.addView(modPassword);

                RelativeLayout r= getActivity().findViewById(R.id.profile);
                r.addView(parent);
                r.addView(parent2);

                modPassword.setOnClickListener(new OnClickListener() {
                    @Override
                    public void onClick(View v) {



                        Intent intent=new Intent(getActivity(), ModifyPassword.class);
                        startActivity(intent);

                    }
                });



                back.setOnClickListener(new OnClickListener() {
                    @Override
                    public void onClick(View v) {

                        save.setVisibility(View.GONE);
                        back.setVisibility(View.GONE);
                        modPassword.setVisibility(View.GONE);

                        eName.setEnabled(false);
                        eSurname.setEnabled(false);
                        eAddress.setEnabled(false);
                        eCity.setEnabled(false);
                        provinceSpinner.setEnabled(false);

                        mod.setVisibility(VISIBLE);
                        getActivity().getFragmentManager().popBackStack();


                    }

                });


                save.setOnClickListener(new OnClickListener() {
                    @Override
                    public void onClick(View v) {


                        name=eName.getText().toString();
                        surname=eSurname.getText().toString();
                        address=eAddress.getText().toString();
                        city= eCity.getText().toString();
                        province=provinceSpinner.getSelectedItem().toString();

                        save.setVisibility(View.GONE);
                        back.setVisibility(View.GONE);
                        mod.setVisibility(VISIBLE);

                        eName.setEnabled(false);
                        eSurname.setEnabled(false);
                        eAddress.setEnabled(false);
                        eCity.setEnabled(false);
                        provinceSpinner.setEnabled(false);

                        apiInterface.editUser(mail,name,surname,address,city,province);

                        Context context = getContext();
                        CharSequence text = "Dati aggiornati";
                        int duration = Toast.LENGTH_SHORT;

                        Toast toast = Toast.makeText(context, text, duration);
                        toast.show();

                        getActivity().getFragmentManager().popBackStack();





                        RelativeLayout toDelete=getActivity().findViewById(R.id.profile);
                        toDelete.removeAllViews();
                        parent.removeAllViews();
                        modifyUser(view);





                    }
                });


            }
        });










        filterArray = new ArrayAdapter<String>(getActivity(), R.layout.spinner_item,listProvince ){
            @Override
            public boolean isEnabled(int position){
                return true;
            }
            @Override
            public View getDropDownView(int position, View convertView, ViewGroup parent) {
                View view = super.getDropDownView(position, convertView, parent);

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

                if(position > 0) {

                }
            }
        });
    }

    @SuppressLint("SetTextI18n")
    private void orderPage(final View view) {



        spinner.setVisibility(View.VISIBLE);

        final RelativeLayout parent = new RelativeLayout(getActivity());
        RelativeLayout.LayoutParams parentParams = new RelativeLayout.LayoutParams(LayoutParams.MATCH_PARENT, LayoutParams.MATCH_PARENT);
        parent.setLayoutParams(parentParams);

        editData = new Button(getActivity());
        editData.setText("DATI UTENTE");
        editData.setTextColor(Color.WHITE);
        editData.setBackground(getResources().getDrawable(R.drawable.button_bg_rounded_corners, null));
        LinearLayout.LayoutParams buttonEdit = new LinearLayout.LayoutParams(450, 105);
        buttonEdit.setMargins(50, 0, 0, 40);
        editData.setLayoutParams(buttonEdit);
        editData.setEnabled(false);
        parent.addView(editData);

        order = new Button(getActivity());
        order.setText("ORDINI EFFETTUATI");
        order.setTextColor(Color.WHITE);
        order.setEnabled(false);
        order.setBackground(getResources().getDrawable(R.drawable.button_bg_off, null));
        LinearLayout.LayoutParams buttonOrder = new LinearLayout.LayoutParams(450, 105);
        buttonOrder.setMargins(550, 0, 0, 40);
        order.setLayoutParams(buttonOrder);
        parent.addView(order);


        final RelativeLayout orders=view.findViewById(R.id.profile);

        orders.addView(parent);



        Call<List<Order>> call= apiInterface.getOrderByUser(mail);
        call.enqueue(new Callback<List<Order>>() {
            @Override
            public void onResponse(Call<List<Order>> call, final Response<List<Order>> response) {

                if (response.body() != null) {


                    recyclerView= new RecyclerView(getContext());
                    RecyclerView.LayoutParams layoutParams=new RecyclerView.LayoutParams(LayoutParams.MATCH_PARENT,LayoutParams.WRAP_CONTENT);
                    layoutParams.setMargins(0,150,0,0);
                    recyclerView.setLayoutParams(layoutParams);
                    recyclerView.setLayoutManager(new LinearLayoutManager(getActivity()));
                    adapter=new OrderAdapter(getActivity(), response.body());
                    recyclerView.setAdapter(adapter);


                    adapter.notifyDataSetChanged();
                    orders.addView(recyclerView);
                    if(spinner!=null) {
                        spinner.setVisibility(View.GONE);
                    }
                    editData.setEnabled(true);


                }else{

                    editData.setEnabled(true);
                    spinner.setVisibility(View.GONE);
                    LinearLayout.LayoutParams layoutParams1 = new LinearLayout.LayoutParams(ViewGroup.LayoutParams.MATCH_PARENT, ViewGroup.LayoutParams.WRAP_CONTENT);
                    layoutParams1.setMargins(0,300,0,0);
                    TextView textView=new TextView(getActivity());
                    textView.setText("NON HAI ORDINI");
                    textView.setGravity(Gravity.CENTER);
                    textView.setTextColor(getResources().getColor(R.color.colorPrimary,null));
                    textView.setTypeface(null, Typeface.BOLD);
                    textView.setLayoutParams(layoutParams1);
                    textView.setTextSize(20);
                    Animation anim = new AlphaAnimation(0.0f, 1.0f);
                    anim.setDuration(750);
                    anim.setStartOffset(10);
                    anim.setRepeatMode(Animation.REVERSE);
                    anim.setRepeatCount(Animation.INFINITE);
                    textView.startAnimation(anim);

                    parent.addView(textView);
                }



            }

                @Override
                public void onFailure(Call<List<Order>> call, Throwable t) {

                }


            });
        editData.setOnClickListener(new OnClickListener() {
            @Override
            public void onClick(View v) {
                RelativeLayout toDelete = view.findViewById(R.id.profile);
                toDelete.removeAllViews();

                order.setEnabled(true);
                parent.removeAllViews();
                modifyUser(view);

            }
        });

    }











    @Override
    public void onItemSelected(AdapterView<?> parent, View view, int position, long id) {

    }

    @Override
    public void onNothingSelected(AdapterView<?> parent) {

    }



}

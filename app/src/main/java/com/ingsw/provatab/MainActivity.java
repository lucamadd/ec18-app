package com.ingsw.provatab;

import android.content.Intent;
import android.content.SharedPreferences;
import android.os.Bundle;
import com.google.android.material.tabs.TabLayout;
import androidx.viewpager.widget.ViewPager;
import androidx.appcompat.app.AppCompatActivity;

import com.ingsw.provatab.ui.main.SectionsPagerAdapter;

import java.util.Objects;

public class MainActivity extends AppCompatActivity {
    private SharedPreferences sharedPreferences;

    @Override
    protected void onCreate(Bundle savedInstanceState) {
        super.onCreate(savedInstanceState);
        setContentView(R.layout.activity_main);
        SectionsPagerAdapter sectionsPagerAdapter = new SectionsPagerAdapter(this, getSupportFragmentManager());
        ViewPager viewPager = findViewById(R.id.view_pager);
        viewPager.setAdapter(sectionsPagerAdapter);
        TabLayout tabs = findViewById(R.id.tabs);
        tabs.setupWithViewPager(viewPager);
        Objects.requireNonNull(tabs.getTabAt(0)).setIcon(R.drawable.ic_home_black_24dp);
        Objects.requireNonNull(tabs.getTabAt(1)).setIcon(R.drawable.ic_info_black_24dp);
        Objects.requireNonNull(tabs.getTabAt(2)).setIcon(R.drawable.ic_shopping_cart_black_24dp);
        Objects.requireNonNull(tabs.getTabAt(3)).setIcon(R.drawable.ic_account_circle_black_24dp);

        sharedPreferences=getSharedPreferences("user_details", MODE_PRIVATE);




        Intent intent = getIntent();
        int fragmentPosition = intent.getIntExtra("fragmentPosition",0);
        viewPager.setCurrentItem(fragmentPosition);


    }


}
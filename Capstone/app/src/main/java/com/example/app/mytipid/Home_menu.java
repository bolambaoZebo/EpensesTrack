package com.example.app.mytipid;

import android.content.Intent;
import android.support.v7.app.AppCompatActivity;
import android.os.Bundle;
import android.view.View;
import android.widget.Button;
import android.widget.ImageButton;

public class Home_menu extends AppCompatActivity {


    ImageButton imgbtn_addhome;
    ImageButton imgbtn_viewhome;
    ImageButton imgbtn_recom;

    @Override
    protected void onCreate(Bundle savedInstanceState) {
        super.onCreate(savedInstanceState);
        setContentView(R.layout.activity_home_menu);

        imgbtn_viewhome = findViewById(R.id.imgbtn_homeview);
        imgbtn_addhome = findViewById(R.id.imgbtn_homeadd);
        imgbtn_recom = findViewById(R.id.imgbtn_reco);

        imgbtn_addhome.setOnClickListener(new View.OnClickListener() {
            @Override
            public void onClick(View v) {
                Intent intent = new Intent(Home_menu.this, Add.class);
                startActivity(intent);
            }
        });


        imgbtn_viewhome.setOnClickListener(new View.OnClickListener() {
            @Override
            public void onClick(View v) {
                Intent intent = new Intent(Home_menu.this,Viewdata.class);
                startActivity(intent);
            }
        });

        imgbtn_recom.setOnClickListener(new View.OnClickListener() {
            @Override
            public void onClick(View v) {
                Intent intent = new Intent(Home_menu.this,Mytipid_Chart.class);
                startActivity(intent);
            }
        });


    }
}

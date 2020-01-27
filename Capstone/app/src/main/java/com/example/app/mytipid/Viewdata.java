package com.example.app.mytipid;

import android.content.Context;
import android.content.Intent;
import android.database.Cursor;
import android.support.v7.app.AppCompatActivity;
import android.os.Bundle;
import android.support.v7.widget.LinearLayoutManager;
import android.support.v7.widget.RecyclerView;
import android.view.View;
import android.widget.Button;
import android.widget.ImageButton;
import android.widget.TextView;
import android.widget.Toast;

import java.util.ArrayList;
import java.util.Calendar;
import java.util.List;

public class Viewdata extends AppCompatActivity {
    DB_Controller mytipid;
    ArrayList<String> val_item,val_price,val_date,val_type,val_id;
    Button tp;
    ImageButton backhome;
    TextView tmonth;
    Context context;

    static int month ;
    String[] monthlist = {"January","February","March","April","May","June"
            ,"July","August","September","October","November","December"};

    @Override
    protected void onCreate(Bundle savedInstanceState) {
        super.onCreate(savedInstanceState);
        setContentView(R.layout.viewdata);
        mytipid = new DB_Controller(this);
        final Calendar calendar = Calendar.getInstance();
        month = calendar.get(Calendar.MONTH);
        context = Viewdata.this;
        tp = findViewById(R.id.toPie);
        backhome = findViewById(R.id.imgbtn_backhome);
        tmonth = findViewById(R.id.txt_months);

        tmonth.setText(monthlist[month]);

        listData();

        List<MyListData> myListData1 = new ArrayList<MyListData>();

        for (int index = 0 ; index<val_item.size();index++){
            myListData1.add(new MyListData(val_id.get(index),val_item.get(index),val_type.get(index),val_price.get(index),val_date.get(index),android.R.drawable.ic_dialog_email));
        }


        backhome.setOnClickListener(new View.OnClickListener() {
            @Override
            public void onClick(View v) {
                Intent intent = new Intent(Viewdata.this, Home_menu.class);
                startActivity(intent);
            }
        });


        RecyclerView recyclerView = (RecyclerView) findViewById(R.id.recyclerView);
        MyListAdapter adapter = new MyListAdapter(myListData1,context);
        recyclerView.setHasFixedSize(true);
        recyclerView.setLayoutManager(new LinearLayoutManager(this));
        recyclerView.setAdapter(adapter);

        tp.setOnClickListener(new View.OnClickListener() {
            @Override
            public void onClick(View v) {
                Intent i = new Intent(Viewdata.this, Mytipid_Chart.class);
                startActivity(i);
            }
        });
    }

public void listData(){
    Cursor res = mytipid.getAllData();

    if(res.getCount() == 0) {
        // show message
        //showMessage("Error","Nothing found");

    }
    //---------------store database from val_id-----------------------//
    val_id = new ArrayList<String>();
    val_item = new ArrayList<String>();
    val_type = new ArrayList<String>();
    val_price = new ArrayList<String>();
    val_date = new ArrayList<String>();

    while (res.moveToNext()) {
        val_id.add(res.getString(0));
        val_item.add(res.getString(1));
        val_type.add(res.getString(2));
        val_price.add(res.getString(3));
        val_date.add(res.getString(4));
        //buffer.append("stud_id :"+ res.getString(0)+"\n");
        // buffer.append("date :"+ res.getString(2)+"\n");

        System.out.println("new size: " +val_item.size() );

    }}

}

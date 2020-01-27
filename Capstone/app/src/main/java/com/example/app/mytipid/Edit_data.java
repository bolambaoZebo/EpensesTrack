package com.example.app.mytipid;

import android.app.DatePickerDialog;
import android.app.Dialog;
import android.app.DialogFragment;
import android.content.Intent;
import android.support.v7.app.AppCompatActivity;
import android.os.Bundle;
import android.view.View;
import android.widget.ArrayAdapter;
import android.widget.Button;
import android.widget.DatePicker;
import android.widget.EditText;
import android.widget.ImageButton;
import android.widget.Spinner;
import android.widget.TextView;
import android.widget.Toast;

import java.util.Calendar;

import static java.lang.Integer.*;

public class Edit_data extends AppCompatActivity {
    EditText itemname_editdata, itemprice_editdata;
    Button btndelete_editdata,btnupdate_editdata;
    Spinner spn_type_editdata;
    TextView dateView_editdata;
    ImageButton backhome;

    public static String dates = "";
    DB_Controller mytipid;
    MyListData myListData;
    public String selectname;
    public String selectid;
    public String selecttype;
    public String selectprice;
    public String selectdate;
    public String selectImg;

    @Override
    protected void onCreate(Bundle savedInstanceState) {
        super.onCreate(savedInstanceState);
        setContentView(R.layout.activity_edit_data);

        myListData = new MyListData();
        mytipid = new DB_Controller(this);


        itemname_editdata = findViewById(R.id.et_itemname_editdata);
        itemprice_editdata = findViewById(R.id.et_itemprice_editdata);
        btndelete_editdata = findViewById(R.id.btnDelete_editdata);
        btnupdate_editdata = findViewById(R.id.btnUpdate);
        spn_type_editdata = findViewById(R.id.spn_pick_editdata);
        dateView_editdata = findViewById(R.id.et_dateofpurchase_editdata);
       backhome = findViewById(R.id.imgbtn_backhome);

        Intent rec = getIntent();

        selectid = rec.getStringExtra("id");
        selectname = rec.getStringExtra("name");
        selecttype = rec.getStringExtra("type");
        selectprice = rec.getStringExtra("price");
        selectdate = rec.getStringExtra("date");
        selectImg = rec.getStringExtra("image");

        itemname_editdata.setText(selectname);
        itemprice_editdata.setText(selectprice);


// setup the spinner adapter
        ArrayAdapter<String> spinAdapter = new ArrayAdapter<String>(Edit_data.this,android.R.layout.simple_list_item_1
                ,getResources().getStringArray(R.array.types));

        spinAdapter.setDropDownViewResource(android.R.layout.simple_spinner_dropdown_item);
        spn_type_editdata.setAdapter(spinAdapter);

        backhome.setOnClickListener(new View.OnClickListener() {
            @Override
            public void onClick(View v) {
                Intent intent = new Intent(Edit_data.this, Home_menu.class);
                startActivity(intent);
            }
        });



//setup button for delete
        btndelete_editdata.setOnClickListener(new View.OnClickListener() {
            @Override
            public void onClick(View v) {

                if (itemname_editdata.getText().toString().equals("") || itemprice_editdata.getText().toString().equals("")
                        || selectdate.equals("")) {
                    Toast.makeText(Edit_data.this, "Fill all fields", Toast.LENGTH_SHORT).show();

                } else {
                    Toast.makeText(v.getContext(), "Deleted: " + selectid + ":" + selectname, Toast.LENGTH_LONG).show();
                    mytipid.deleteName(valueOf(selectid), selectname);
                    intentRefresh();
                }
            }
        });
//setup button for update
        btnupdate_editdata.setOnClickListener(new View.OnClickListener() {
            @Override
            public void onClick(View v) {

                if (itemname_editdata.getText().toString().equals("") || itemprice_editdata.getText().toString().equals("")
                        || selectdate.equals("")||spn_type_editdata.getSelectedItem().equals("Type")) {
                    Toast.makeText(Edit_data.this, "Fill all fields", Toast.LENGTH_SHORT).show();

                } else{

                    boolean up = mytipid.updateData(selectid,itemname_editdata.getText().toString(),spn_type_editdata.getSelectedItem().toString()
                ,itemprice_editdata.getText().toString(),selectdate);

                if (up){
                    intentRefresh();
                }else
                    Toast.makeText(Edit_data.this, "Not successful!", Toast.LENGTH_SHORT).show();

                 }
            }
        });
// date picker setup.....


    }

    public void intentRefresh(){
       Intent intent = new Intent(this,Viewdata.class);
        startActivity(intent);
    }

}

package com.example.app.mytipid;

import android.content.Intent;
import android.database.Cursor;
import android.support.v7.app.AlertDialog;
import android.support.v7.app.AppCompatActivity;
import android.os.Bundle;
import android.view.LayoutInflater;
import android.view.View;
import android.view.ViewGroup;
import android.widget.Button;
import android.widget.EditText;
import android.widget.TextView;
import android.widget.Toast;
import android.support.v7.widget.RecyclerView;
import android.support.v7.widget.LinearLayoutManager;

import java.util.ArrayList;
import java.util.Calendar;

public class MainActivity extends AppCompatActivity {
    DB_Controller mytipid;
    Button btn_Enter;
    TextView txtregister;
    EditText student_id;
    EditText student_name;
    ArrayList<String> val_id;
    MyListData myListData;

    @Override
    protected void onCreate(Bundle savedInstanceState) {
        super.onCreate(savedInstanceState);
        setContentView(R.layout.activity_main);
        myListData = new MyListData();
        mytipid = new DB_Controller(this);
        btn_Enter = findViewById(R.id.btn_Entermain);
        txtregister = findViewById(R.id.txtregister);
        student_id = findViewById(R.id.et_studID);
        //student_name = findViewById(R.id.etstudname);




        btn_Enter.setOnClickListener(new View.OnClickListener() {
            @Override
            public void onClick(View v) {


            if(student_id.getText().toString().equals("")){
                    Toast.makeText(MainActivity.this, "Input Login!", Toast.LENGTH_SHORT).show();
                }else {

                   if ( validLogin(student_id.getText().toString())) {
                       // openAdd(); this part need to compare from the database if the id number is registered
                       Intent intent = new Intent(MainActivity.this, Home_menu.class);
                       startActivity(intent);
                   }else {Toast.makeText(MainActivity.this, "Not Valid Login!", Toast.LENGTH_SHORT).show();}
                }


               // Intent intent = new Intent(MainActivity.this, Home_menu.class);
               // startActivity(intent);


            }
        });



        //--------------------------------------------------------------------------------------------------------------//

        txtregister.setOnClickListener(new View.OnClickListener() {
            @Override
            public void onClick(View v) {
                regshowDialog();
            }
        });


    }

    public void addClickStudent(String i, String n) {
        if (i.equals("") || n.equals("") ) {
            Toast.makeText(MainActivity.this, "Fill all fields", Toast.LENGTH_SHORT).show();

        } else {
            boolean isInserted = mytipid.register_insertUser(i, n);
            if (isInserted == true) {
                Toast.makeText(MainActivity.this, "Account Created!", Toast.LENGTH_SHORT).show();

            } else {
                Toast.makeText(MainActivity.this, "Error inputs or Account already exists!", Toast.LENGTH_SHORT).show();
            }

        }
    }

    public void regshowDialog(){

        //before inflating the custom alert dialog layout, we will get the current activity viewgroup
        ViewGroup viewGroup = findViewById(android.R.id.content);
        EditText student_id ;
        EditText student_name ;

        //then we will inflate the custom alert dialog xml that we created
        View dialogView = LayoutInflater.from(this).inflate(R.layout.register_dialog, viewGroup, false);
        final  EditText s_id = dialogView.findViewById(R.id.edt_rstudID);
        final  EditText s_name = dialogView.findViewById(R.id.edt_rstudname);
        final Button s_btnadd = dialogView.findViewById(R.id.btn_registerADD);

        //convert to string


        s_btnadd.setOnClickListener(new View.OnClickListener() {
            @Override
            public void onClick(View v) {

                String tmp_id = s_id.getText().toString();
                String tmp_name = s_name.getText().toString();
                addClickStudent(tmp_id,tmp_name);//this is where dialog register input into the the database
            }
        });


        //Now we need an AlertDialog.Builder object
        AlertDialog.Builder builder = new AlertDialog.Builder(this);


        //setting the view of the builder to our custom view that we already inflated
        builder.setView(dialogView);

        //finally creating the alert dialog and displaying it
        AlertDialog alertDialog = builder.create();
        alertDialog.show();

        //addClickStudent();



    }

    public boolean validLogin(String s){
        //---------------------------------------//
        Cursor res = mytipid.getAllDataLogin();

        if(res.getCount() == 0) {
            // show message
            showMessage("Error","Nothing found");

        }
        //---------------store database from val_id-----------------------//
        val_id = new ArrayList<String>();

        while (res.moveToNext()) {

            val_id.add(res.getString(0));
            //buffer.append("stud_id :"+ res.getString(0)+"\n");
            // buffer.append("date :"+ res.getString(2)+"\n");

            System.out.println("new size: " +val_id.size() );

        }

       //-------------------compare user login to the registered user-----------------------//

       return val_id.contains(s);


    }

    private void showMessage(String title, String Message) {
        AlertDialog.Builder builder = new AlertDialog.Builder(this);
        builder.setCancelable(true);
        builder.setTitle(title);
        builder.setMessage(Message);
        builder.show();


    }


    }




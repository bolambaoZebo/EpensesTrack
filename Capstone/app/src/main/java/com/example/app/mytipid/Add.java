package com.example.app.mytipid;

import android.app.DatePickerDialog;
import android.app.Dialog;
import android.app.DialogFragment;
import android.content.DialogInterface;
import android.content.Intent;
import android.database.Cursor;
import android.os.Bundle;
import android.support.v7.app.AlertDialog;
import android.support.v7.app.AppCompatActivity;
import android.view.View;
import android.widget.ArrayAdapter;
import android.widget.Button;
import android.widget.DatePicker;
import android.widget.EditText;
import android.widget.ImageButton;
import android.widget.Spinner;
import android.widget.TextView;
import android.widget.Toast;
import android.app.Dialog;
import android.app.DialogFragment;
import android.os.Bundle;

import java.util.Calendar;


public class Add extends AppCompatActivity {
    EditText itemname, itemprice;
    TextView dateView;
    public static String date = "";
    Button btnadd;
    ImageButton backhome;
    DB_Controller mytipid;
    MyListData myListData;
    Spinner spn_type;

    static int year ;
    static int month ;
    static int day;

    String[] monthlist = {"January","February","March","April","May","June"
                            ,"July","August","September","October","November","December"};

    @Override
    protected void onCreate(Bundle savedInstanceState) {
        super.onCreate(savedInstanceState);
        setContentView(R.layout.addexpenses);
        myListData = new MyListData();
        mytipid = new DB_Controller(this);
        itemname = findViewById(R.id.et_itemname);
        itemprice = findViewById(R.id.et_itemprice);
        btnadd = findViewById(R.id.btnadd);
        spn_type = findViewById(R.id.spn_pick);
        dateView = findViewById(R.id.et_dateofpurchase);
        backhome = findViewById(R.id.imgbtn_backhome);

        ArrayAdapter<String> spinAdapter = new ArrayAdapter<String>(Add.this,android.R.layout.simple_list_item_1
                ,getResources().getStringArray(R.array.types));

        spinAdapter.setDropDownViewResource(android.R.layout.simple_spinner_dropdown_item);
        spn_type.setAdapter(spinAdapter);

       viewAll();


        backhome.setOnClickListener(new View.OnClickListener() {
            @Override
            public void onClick(View v) {
                Intent intent = new Intent(Add.this, Home_menu.class);
                startActivity(intent);
            }
        });


        btnadd.setOnClickListener(new View.OnClickListener() {
            @Override
            public void onClick(View v) {
               if (addClick()){
                   final AlertDialog.Builder builder = new AlertDialog.Builder(Add.this);
                   builder.setMessage("Insert More?");
                   builder.setCancelable(true);

                   builder.setPositiveButton("Add", new DialogInterface.OnClickListener() {
                       @Override
                       public void onClick(DialogInterface dialog, int which) {
                           itemname.setText("");
                           itemprice.setText("");
                           dialog.dismiss();
                       }
                   });
                   builder.setNegativeButton("Cancel", new DialogInterface.OnClickListener() {
                       @Override
                       public void onClick(DialogInterface dialog, int which) {
                           dialog.cancel();
                           Intent intent = new Intent(Add.this,Home_menu.class);
                           startActivity(intent);

                       }
                   });

                   AlertDialog alertDialog = builder.create();
                   alertDialog.show();
               }//addClick();


            }
        });
        dateView.setOnClickListener(new View.OnClickListener() {
            @Override
            public void onClick(View v) {
                DialogFragment dialogfragment = new DatePickerDialogClass();

                dialogfragment.show(getFragmentManager(), "Date Picker Dialog");
            }
        });


    }


    public boolean addClick() {

        int tempMon = month +1;
        if (itemname.getText().toString().equals("") || itemprice.getText().toString().equals("") || date.equals("")||
                spn_type.getSelectedItem().equals("Type")) {
            Toast.makeText(Add.this, "Fill all fields", Toast.LENGTH_SHORT).show();

            return false;

        }else if (!date.contains(tempMon+"/")){

            Toast.makeText(Add.this, "Valid Month should be:"+monthlist[tempMon-1], Toast.LENGTH_SHORT).show();

            return false;
        }else {

            boolean isInserted = mytipid.insertExp(
                     itemname.getText().toString()
                    ,spn_type.getSelectedItem().toString()
                    , itemprice.getText().toString()
                    , date);
            if (isInserted == true) {
                Toast.makeText(Add.this, "Account Creqated!", Toast.LENGTH_SHORT).show();

            } else {
                Toast.makeText(Add.this, "Invalid!", Toast.LENGTH_SHORT).show();
            }

            return true;

        }
    }


    public int viewAll() {

                        Cursor res = mytipid.getAllData();
                        if(res.getCount() == 0) {
                            // show message
                            //showMessage("Error","Nothing found");
                            return 0;
                        }

                        StringBuffer buffer = new StringBuffer();
                        while (res.moveToNext()) {
                            buffer.append("item :"+ res.getString(0)+"\n");
                            buffer.append("price :"+ res.getString(1)+"\n");
                            buffer.append("date :"+ res.getString(2)+"\n");

                        }

                        // Show all data
                        //showMessage("Expenses",buffer.toString());
                        return buffer.length();

    }

    private void showMessage(String title, String Message) {
        AlertDialog.Builder builder = new AlertDialog.Builder(this);
        builder.setCancelable(true);
        builder.setTitle(title);
        builder.setMessage(Message);
        builder.show();


    }

   public static class DatePickerDialogClass extends DialogFragment implements DatePickerDialog.OnDateSetListener{


       @Override
       public Dialog onCreateDialog(Bundle savedInstanceState) {
           final Calendar calendar = Calendar.getInstance();
           year = calendar.get(Calendar.YEAR);
           month = calendar.get(Calendar.MONTH);
           day = calendar.get(Calendar.DAY_OF_MONTH);

           DatePickerDialog datepickerdialog = new DatePickerDialog(getActivity(),
                   android.app.AlertDialog.THEME_DEVICE_DEFAULT_DARK,this,year,month,day);
                   //AlertDialog,Add.class,year,month,day);

           return datepickerdialog;
       }

       public void onDateSet(DatePicker view, int year, int month, int day){

            date = day + "/" + (month+1) + "/" + year;

       }
   }

}

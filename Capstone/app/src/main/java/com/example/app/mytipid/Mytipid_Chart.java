package com.example.app.mytipid;

import android.content.Intent;
import android.database.Cursor;
import android.support.v7.app.AppCompatActivity;
import android.os.Bundle;
import android.view.View;
import android.widget.Button;
import android.widget.EditText;
import android.widget.ImageButton;
import android.widget.TextView;
import android.widget.Toast;

import com.github.mikephil.charting.charts.PieChart;
import com.github.mikephil.charting.components.Legend;
import com.github.mikephil.charting.data.PieData;
import com.github.mikephil.charting.data.PieDataSet;
import com.github.mikephil.charting.data.PieEntry;
import com.github.mikephil.charting.utils.ColorTemplate;
import com.github.mikephil.charting.utils.MPPointF;

import java.util.ArrayList;
import java.util.Calendar;
import java.util.List;

public class Mytipid_Chart extends AppCompatActivity {
    float rainfall[] = {98.5f,76.8f,233.5f,100.8f};
    String typeData[] = {"Bills","Food","Entertainment","Transport"};
    String chart_category[] = {"Bills","Food","Entertainment","Transport"};
    float bills = 0.0f,food= 0.0f,entertain= 0.0f,transp= 0.0f;//this would be the storage of the data
    float chart_av[];

    ArrayList<String> categ_name;
    ArrayList<String> categ_price;
    ArrayList<String> rec_mon;
    ArrayList<String> rec_detail;

    DB_Controller mytipid;
    Button addRecom;
    EditText edt_recom;
    ImageButton backhome;
    TextView tit;
    TextView vRecon,vRecondetail;





    @Override
    protected void onCreate(Bundle savedInstanceState) {
        super.onCreate(savedInstanceState);
        setContentView(R.layout.activity_mytipid__chart);
        mytipid = new DB_Controller(this);
        addRecom = findViewById(R.id.btn_recom);
        edt_recom = findViewById(R.id.edt_recom);
        tit = findViewById(R.id.txtTitle);
        vRecon = findViewById(R.id.view_reconmon);
        backhome = findViewById(R.id.imgbtn_backhome);
        vRecondetail = findViewById(R.id.view_recondetail);
        final Calendar calendar = Calendar.getInstance();
        int year = calendar.get(Calendar.YEAR);
        int month = calendar.get(Calendar.MONTH);
        int day = calendar.get(Calendar.DAY_OF_MONTH);
        final String time = String.valueOf(month);

        rec_mon = new ArrayList<>();
        rec_detail = new ArrayList<>();



        listData();//getting all data from the database
        sumData();//calculate sum of all type
        chart_av = new float[]{bills, food, entertain, transp};//input float data to piechart

        backhome.setOnClickListener(new View.OnClickListener() {
            @Override
            public void onClick(View v) {
                Intent intent = new Intent(Mytipid_Chart.this, Home_menu.class);
                startActivity(intent);
            }
        });


        //checking for available recommendation..
        if (rec_mon.isEmpty()){
            Toast.makeText(Mytipid_Chart.this, "add Recommendation!", Toast.LENGTH_SHORT).show();
        }else{
            String samp = rec_detail.get(0);
           // String s = String.valueOf(rec_mon.get(tempS));
            //vRecon.setText(String.valueOf(rec_mon.get(tempS)));
            vRecondetail.setText(samp);
            Toast.makeText(Mytipid_Chart.this, String.valueOf(rec_mon.get(0)), Toast.LENGTH_SHORT).show();
        }


       setupPiechart();


       addRecom.setOnClickListener(new View.OnClickListener() {
           @Override
           public void onClick(View v) {
               if (edt_recom.equals("") ) {
                   Toast.makeText(Mytipid_Chart.this, "Fill all fields", Toast.LENGTH_SHORT).show();

               }else{
                   boolean insert = mytipid.insertRecom(edt_recom.getText().toString(),time);

                   if (insert == true) {
                       Toast.makeText(Mytipid_Chart.this, "Recommendation added!", Toast.LENGTH_SHORT).show();

                   } else {
                       Toast.makeText(Mytipid_Chart.this, "Error inputs or Account already exists!", Toast.LENGTH_SHORT).show();
                   }
               }

           }
       });

    }

    public void sumData(){
        for (int i=0;i<categ_name.size();i++){
            switch (categ_name.get(i)){
                case "Bills":
                    bills+= Float.valueOf(categ_price.get(i));
                    break;

                case "Food":
                    food += Float.valueOf(categ_price.get(i));
                    break;

                case "Entertainment":
                    entertain += Float.valueOf(categ_price.get(i));
                    break;

                case "Transportation":
                    transp += Float.valueOf(categ_price.get(i));
                    break;


            }
        }
    }

    public void listData(){
        Cursor res = mytipid.getAllData();
        Cursor res1 = mytipid.getAllRecom();

        if(res.getCount() == 0) {
            // show message
            //showMessage("Error","Nothing found");

        }

        categ_name = new ArrayList<>();
        categ_price = new ArrayList<>();

        while (res.moveToNext()) {

           categ_name.add(res.getString(2));
           categ_price.add(res.getString(3));
            //val_type.add(res.getString(2));
            //val_price.add(res.getString(3));

        }

        if(res1.getCount() == 0) {
            // show message
            //showMessage("Error","Nothing found");

        }

        while (res1.moveToNext()) {

            rec_mon.add(res1.getString(0));
            rec_detail.add(res1.getString(1));
            //val_type.add(res.getString(2));
            //val_price.add(res.getString(3));

        }



    }

    private void  setupPiechart(){

        //poppulation
        List<PieEntry> pieEntries = new ArrayList<>();

        for (int i = 0; i<chart_category.length ;i++){
            pieEntries.add(new PieEntry(chart_av[i],chart_category[i]));
        }

        PieDataSet dataSet = new PieDataSet(pieEntries,"Expenses Summary");
        dataSet.setColors(ColorTemplate.JOYFUL_COLORS);
        dataSet.setDrawValues(true);
        dataSet.setValueTextSize(10.f);
        dataSet.setSliceSpace(1f);
        dataSet.setSelectionShift(5f);

        // Outside values
        dataSet.setXValuePosition(PieDataSet.ValuePosition.OUTSIDE_SLICE);
        dataSet.setYValuePosition(PieDataSet.ValuePosition.OUTSIDE_SLICE);

        dataSet.setValueLinePart1OffsetPercentage(70f); /** When valuePosition is OutsideSlice, indicates offset as percentage out of the slice size */
        dataSet.setValueLinePart1Length(0.4f); /** When valuePosition is OutsideSlice, indicates length of first half of the line */
        dataSet.setValueLinePart2Length(0.5f); /** When valuePosition is OutsideSlice, indicates length of second half of the line */
        //setExtraOffsets(0.f, 5.f, 0.f, 5.f); // Ofsets of the view chart to prevent outside values being cropped /** Sets extra offsets (around the chart view) to be appended to the auto-calculated offsets.*/


        PieData data = new PieData(dataSet);

        // get the chart
        PieChart chart = (PieChart) findViewById(R.id.piechar);
        chart.setData(data);
        chart.setCenterTextColor(ColorTemplate.rgb(String.valueOf(505050)));
        chart.setEntryLabelColor(ColorTemplate.rgb(String.valueOf(505050)));
        chart.animateY(1000);
        chart.setDrawEntryLabels(false);
        chart.setExtraOffsets(0.f, 5.f, 0.f, 5.f);
        chart.invalidate();

        Legend l = chart.getLegend(); // get legend of pie
        l.setVerticalAlignment(Legend.LegendVerticalAlignment.CENTER); // set vertical alignment for legend
        l.setHorizontalAlignment(Legend.LegendHorizontalAlignment.RIGHT); // set horizontal alignment for legend
        l.setOrientation(Legend.LegendOrientation.VERTICAL); // set orientation for legend
        l.setDrawInside(false);
    }
}

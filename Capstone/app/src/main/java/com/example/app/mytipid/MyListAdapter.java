package com.example.app.mytipid;

import android.content.Context;
import android.content.Intent;
import android.database.Cursor;
import android.support.v7.widget.RecyclerView;
import android.view.LayoutInflater;
import android.view.MotionEvent;
import android.view.View;
import android.view.ViewGroup;
import android.widget.ImageButton;
import android.widget.ImageView;
import android.widget.RelativeLayout;
import android.widget.TextView;
import android.widget.Toast;

import java.util.ArrayList;
import java.util.Calendar;
import java.util.List;


public class MyListAdapter extends RecyclerView.Adapter<MyListAdapter.ViewHolder>{
    private MyListData[] listdata;
    private Context lcontext;
    Edit_data edit_datal;
     RecyclerView recyclerView;



    public MyListAdapter(List<MyListData> listdata, Context context) {
        this.listdata = listdata.toArray(new MyListData[0]);
        this.lcontext = context;

    }
    @Override
    public ViewHolder onCreateViewHolder(ViewGroup parent, int viewType) {
        LayoutInflater layoutInflater = LayoutInflater.from(parent.getContext());
        View listItem = layoutInflater.inflate(R.layout.list_item, parent, false);
        final ViewHolder viewHolder = new ViewHolder(listItem);

        return viewHolder;
    }

    @Override
    public void onBindViewHolder(final ViewHolder holder, final int position) {
        final MyListData myListData = listdata[position];

        holder.textView.setText(listdata[position].getDescription());
        holder.textprice.setText(listdata[position].getPrice());
        holder.textdate.setText(listdata[position].getDate());
        holder.imageView.setImageResource(listdata[position].getImgId());

        holder.relativeLayout.setOnClickListener(new View.OnClickListener() {
            @Override
            public void onClick(View view) {
                Toast.makeText(view.getContext(),"click on item: "+myListData.getDescription()+":"
                        +myListData.getId(),Toast.LENGTH_LONG).show();


                 }
        });

        holder.imgbt.setOnClickListener(new View.OnClickListener() {
            @Override
            public void onClick(View v) {
                Intent i = new Intent(v.getContext(), Edit_data.class);

                i.putExtra("id",myListData.getId());
                i.putExtra("name",myListData.getDescription());
                i.putExtra("type",myListData.getType());
                i.putExtra("price",myListData.getPrice());
                i.putExtra("date",myListData.getDate());
                i.putExtra("image",myListData.getImgId());
                v.getContext().startActivity(i);
            }
        });





    }

    @Override
    public int getItemCount() {
        return listdata.length;
    }

    public static class ViewHolder extends RecyclerView.ViewHolder {
        public ImageView imageView;
        public TextView textView;
        public TextView textprice;
        public TextView textdate;
        public ImageButton imgbt;
        public RelativeLayout relativeLayout;
        public ViewHolder(View itemView) {
            super(itemView);
            this.imageView = (ImageView) itemView.findViewById(R.id.imageView);
            this.textView = (TextView) itemView.findViewById(R.id.textView);
            this.textprice = (TextView) itemView.findViewById(R.id.txtprice);
            this.textdate = itemView.findViewById(R.id.txtdate);
            this.imgbt = itemView.findViewById(R.id.imgbtn_edit);
            relativeLayout = (RelativeLayout)itemView.findViewById(R.id.relativeLayout);
        }
    }


}

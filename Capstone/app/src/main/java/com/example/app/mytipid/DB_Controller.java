package com.example.app.mytipid;

import android.database.sqlite.SQLiteOpenHelper;
import android.support.v7.app.AppCompatActivity;
import android.os.Bundle;
import android.content.ContentValues;
import android.content.Context;
import android.database.Cursor;
import android.database.sqlite.SQLiteDatabase;
import android.util.Log;

public class DB_Controller extends SQLiteOpenHelper {
    //databasename
    public static final String DATABASE_NAMED = "mytipid.db";
    public static final String DATABASE_NAME = "mytipid_database05.db";
    private static final String TAG = "DatabaseHelper";
    //add attributes
    public static final String EXPENSES_TABLE = "expenses";
    public static final String COL_ID = "item_id";
    public static final String COL_INAME = "item_name";
    public static final String COL_ITYPE = "type";
    public static final String COL_IPRICE = "item_price";
    public static final String COL_DATE = "date";

    //log in attribute
    public static final String LOG_IN_TABLE =  "login";
    public static final String COL_IDstudent = "stud_id";
    public static final String COL_NAMEstudent = "stud_name";

    // Recommendation database
    public static final String TABLE_RECOM =  "recommendation";
    public static final String COL_RECOM_ID =  "id";
    public static final String COL_RECOM_DISCREP = "recom_discrep";
    public static final String COL_RECOM_MONTH = "recom_month";


    public DB_Controller(Context context) {
        super(context, DATABASE_NAME, null, 1);
        SQLiteDatabase db = this.getWritableDatabase();


    }

    @Override
    public void onCreate(SQLiteDatabase db) {
        db.execSQL("CREATE TABLE " + EXPENSES_TABLE + "(item_id INTEGER PRIMARY KEY AUTOINCREMENT,item_name TEXT,type TEXT,item_price INT, date TEXT)");
        db.execSQL("CREATE TABLE " + LOG_IN_TABLE + "(stud_id INT PRIMARY KEY, stud_name TEXT)");
        db.execSQL("CREATE TABLE " + TABLE_RECOM + "(id INTEGER PRIMARY KEY AUTOINCREMENT ,recom_discrep TEXT, recom_month TEXT)");


    }

    @Override
    public void onUpgrade(SQLiteDatabase db, int oldVersion, int newVersion) {
        db.execSQL("DROP TABLE IF EXISTS "+EXPENSES_TABLE);
        db.execSQL("DROP TABLE IF EXISTS "+LOG_IN_TABLE);
        db.execSQL("DROP TABLE IF EXISTS "+TABLE_RECOM);

        onCreate(db);

    }

    /**
     * Updates the name field
     String newName, int id, String oldName
     */

    public boolean updateData(String id,String name,String typ,String price,String date) {
        SQLiteDatabase db = this.getWritableDatabase();
        ContentValues contentValues = new ContentValues();
        contentValues.put(COL_INAME,name);
        contentValues.put(COL_ITYPE,typ);
        contentValues.put(COL_IPRICE,price);
        contentValues.put(COL_DATE,date);
        db.update(EXPENSES_TABLE, contentValues, "item_id = ?",new String[] { id });
        return true;
    }

    public boolean insertRecom(String dicp, String mon){
        SQLiteDatabase db = this.getWritableDatabase();
        ContentValues contentValues = new ContentValues();
        contentValues.put(COL_RECOM_DISCREP,dicp);
        contentValues.put(COL_RECOM_MONTH,mon);

        long result = db.insert(TABLE_RECOM, null,contentValues);
        db.close();
        if (result == -1){
            return false;

        }
        else
        {
            return true;
        }

    }

    public boolean insertExp(String inames,String type, String iprice, String date) {
        SQLiteDatabase db = this.getWritableDatabase();
        ContentValues contentValues = new ContentValues();
        contentValues.put(COL_INAME,inames);
        contentValues.put(COL_ITYPE,type);
        contentValues.put(COL_IPRICE,iprice);
        contentValues.put(COL_DATE,date);

        long result = db.insert(EXPENSES_TABLE, null,contentValues);
        db.close();
        if (result == -1){
            return false;

        }
        else
        {
            return true;
        }


    }

    public boolean register_insertUser(String id, String sname) {
        SQLiteDatabase db = this.getWritableDatabase();
        ContentValues contentValues = new ContentValues();
        contentValues.put(COL_IDstudent,id);
        contentValues.put(COL_NAMEstudent,sname);

        long result = db.insert(LOG_IN_TABLE, null,contentValues);
        db.close();
        if (result == -1){
            return false;

        }
        else
        {
            return true;
        }


    }



    public Cursor getAllData() {
        SQLiteDatabase db = this.getWritableDatabase();
        Cursor res = db.rawQuery("select * from "+EXPENSES_TABLE,null);
        return res;
    }

    public Cursor getAllRecom() {
        SQLiteDatabase db = this.getWritableDatabase();
        Cursor res = db.rawQuery("select * from "+TABLE_RECOM+" ORDER BY "+COL_RECOM_ID+" DESC",null);
        return res;
    }

    public Cursor getAllDataLogin() {
        SQLiteDatabase db = this.getWritableDatabase();
        Cursor res = db.rawQuery("select * from "+LOG_IN_TABLE,null);
        return res;
    }

    public void deleteexp(String id)
    {
        SQLiteDatabase db = this.getReadableDatabase();
        db.delete(EXPENSES_TABLE, COL_ID + "=?", new String[]{id});
        db.close();
    }

    /**
     * Returns only the ID that matches the name passed in
     * @param id
     * @return
     */


    public Cursor getItemID(String id){

        SQLiteDatabase db = this.getWritableDatabase();

        String query = "SELECT " +COL_ID + " FROM " + EXPENSES_TABLE +
                " WHERE " + COL_ID + " = '" + id + "'";

        Cursor data = db.rawQuery(query, null);

        return data;
    }




    /**
     * Delete from database
     * @param id
     */

    public void deleteName(int id, String name){

        SQLiteDatabase db = this.getWritableDatabase();

        String query = "DELETE FROM " + EXPENSES_TABLE+ " WHERE "
                + COL_ID + " = '" + id + "'" +
                " AND " + COL_INAME + " = '" + name + "'";

        Log.d(TAG, "deleteName: query: " + query);

        Log.d(TAG, "deleteName: Deleting " + name + " from database.");

        db.execSQL(query);
    }




}

package com.sihmaxhealth.drawerfragmeny;

import android.content.ContentValues;
import android.content.Context;
import android.database.Cursor;
import android.database.sqlite.SQLiteDatabase;
import android.database.sqlite.SQLiteOpenHelper;

public class databasefile extends SQLiteOpenHelper {
    public String DATABASE_NAME;
    public String TABLE_NAME;
    public databasefile(Context context,String name,String tabname) {
        super(context, name, null, 1);
        DATABASE_NAME=name;
        TABLE_NAME=tabname;
        SQLiteDatabase db=this.getWritableDatabase();

    }

    @Override
    public void onCreate(SQLiteDatabase sqLiteDatabase) {
        sqLiteDatabase.execSQL("create table "+TABLE_NAME+" (Username TEXT PRIMARY KEY,Password TEXT,FirstName Text,LastName Text,Email Text)");

    }

    @Override
    public void onUpgrade(SQLiteDatabase sqLiteDatabase, int i, int i1) {

    }
    public void adddData(ContentValues cntval)
    {
        SQLiteDatabase db=this.getWritableDatabase();
        db.insert(TABLE_NAME,null,cntval);


    }
    public Cursor verifyData(String Uname, String Pass)
    {
        SQLiteDatabase db=this.getWritableDatabase();
        Cursor res=db.rawQuery("select * from "+TABLE_NAME+" where Username='"+Uname+"' and Password='"+Pass+"'",null);
        return res;


    }

}

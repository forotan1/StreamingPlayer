package com.musa.iptv4.Iptv;

import android.content.ContentValues;
import android.content.Context;
import android.database.Cursor;
import android.database.sqlite.SQLiteDatabase;
import android.database.sqlite.SQLiteOpenHelper;
import android.widget.Toast;

import java.util.LinkedList;
import java.util.List;

public class IpTvDBHelper extends SQLiteOpenHelper {

    public static final String DATABASE_NAME = "people.db";
    private static final int DATABASE_VERSION = 3 ;
    public static final String TABLE_NAME = "People";
    public static final String COLUMN_ID = "_id";
    public static final String COLUMN_MODEL_TITLE = "Ititle";
    public static final String COLUMN_MODEL_URL = "iUrl";
    public static final String COLUMN_MODEL_ABOUT = "iAbout";
    public static final String COLUMN_MODEL_ICON = "image";


    public IpTvDBHelper(Context context) {
        super(context, DATABASE_NAME , null, DATABASE_VERSION);
    }

    @Override
    public void onCreate(SQLiteDatabase db) {
        db.execSQL(" CREATE TABLE " + TABLE_NAME + " (" +
                COLUMN_ID + " INTEGER PRIMARY KEY AUTOINCREMENT, " +
                COLUMN_MODEL_TITLE + " TEXT NOT NULL, " +
                COLUMN_MODEL_URL + " TEXT NOT NULL, " +
                COLUMN_MODEL_ABOUT + " TEXT NOT NULL, " +
                COLUMN_MODEL_ICON + " BLOB NOT NULL);"
        );

    }

    @Override
    public void onUpgrade(SQLiteDatabase db, int oldVersion, int newVersion) {
        // you can implement here migration process
        db.execSQL("DROP TABLE IF EXISTS " + TABLE_NAME);
        this.onCreate(db);
    }
    /**create record**/
    public void saveNewPerson(Imodel iModel) {

        SQLiteDatabase db = this.getWritableDatabase();
        ContentValues values = new ContentValues();
        values.put(COLUMN_MODEL_TITLE, iModel.getiTitle());
        values.put(COLUMN_MODEL_URL, iModel.getiUrl());
        values.put(COLUMN_MODEL_ABOUT, iModel.getiAbout());
        values.put(COLUMN_MODEL_ICON, iModel.getImage());

        // insert
        db.insert(TABLE_NAME,null, values);
        db.close();
    }

    /**Query records, give options to filter results**/
    public List<Imodel> peopleList(String filter) {
        String query;
        if(filter.equals("")){
            //regular query
            query = "SELECT  * FROM " + TABLE_NAME;
        }else{
            //filter results by filter option provided
            query = "SELECT  * FROM " + TABLE_NAME + " ORDER BY "+ filter;
        }

        List<Imodel> imodelLinkedList = new LinkedList<>();
        SQLiteDatabase db = this.getWritableDatabase();
        Cursor cursor = db.rawQuery(query, null);
        Imodel iModel;

        if (cursor.moveToFirst()) {
            do {
                iModel = new Imodel();

                iModel.setId(cursor.getInt(cursor.getColumnIndex(COLUMN_ID)));
                iModel.setiTitle(cursor.getString(cursor.getColumnIndex(COLUMN_MODEL_TITLE)));
                iModel.setiUrl(cursor.getString(cursor.getColumnIndex(COLUMN_MODEL_URL)));
                iModel.setiAbout(cursor.getString(cursor.getColumnIndex(COLUMN_MODEL_ABOUT)));
                iModel.setImage(cursor.getString(cursor.getColumnIndex(COLUMN_MODEL_ICON)));
                imodelLinkedList.add(iModel);
            } while (cursor.moveToNext());
        }


        return imodelLinkedList;
    }

    /**Query only 1 record**/
    public Imodel getPerson(long id){
        SQLiteDatabase db = this.getWritableDatabase();
        String query = "SELECT  * FROM " + TABLE_NAME + " WHERE _id="+ id;
        Cursor cursor = db.rawQuery(query, null);

        Imodel receivedImodel = new Imodel();
        if(cursor.getCount() > 0) {
            cursor.moveToFirst();

            receivedImodel.setiTitle(cursor.getString(cursor.getColumnIndex(COLUMN_MODEL_TITLE)));
            receivedImodel.setiUrl(cursor.getString(cursor.getColumnIndex(COLUMN_MODEL_URL)));
            receivedImodel.setiAbout(cursor.getString(cursor.getColumnIndex(COLUMN_MODEL_ABOUT)));
            receivedImodel.setImage(cursor.getString(cursor.getColumnIndex(COLUMN_MODEL_ICON)));
        }



        return receivedImodel;


    }


    /**delete record**/
    public void deletePersonRecord(long id, Context context) {
        SQLiteDatabase db = this.getWritableDatabase();

        db.execSQL("DELETE FROM "+TABLE_NAME+" WHERE _id='"+id+"'");
       // Toast.makeText(context, "Deleted successfully.", Toast.LENGTH_SHORT).show();

    }

    /**update record**/
    public void updatePersonRecord(long personId, Context context, Imodel updatedperson) {
        SQLiteDatabase db = this.getWritableDatabase();
        //you can use the constants above instead of typing the column names
        db.execSQL("UPDATE  "+TABLE_NAME+" SET iTitle ='"+ updatedperson.getiTitle() + "', iUrl ='" + updatedperson.getiUrl()+ "', iAbout ='"+ updatedperson.getiAbout() + "', image ='"+ updatedperson.getImage() + "'  WHERE _id='" + personId + "'");
        Toast.makeText(context, "Updated successfully.", Toast.LENGTH_SHORT).show();


    }




}

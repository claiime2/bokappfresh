package com.example.user.lvhnreadbookapp;

import android.content.ContentValues;
import android.content.Context;
import android.database.Cursor;
import android.database.sqlite.SQLiteDatabase;
import android.database.sqlite.SQLiteOpenHelper;
import android.util.Log;

import com.example.user.lvhnreadbookapp.Models.SachModel;

import java.util.ArrayList;

public class SQLiteHelper extends SQLiteOpenHelper {
    private static final String DB_NAME = "db_book";
    private static final int DB_VERSION = 1;
    private static final String TB_NAME = "tb_book";
    private static final String TB_COL_ID = "masach";
    private static final String TB_COL_NAME = "tensach";
    private static final String TB_COL_TACGIA = "tacgia";
    private static final String TB_COL_LINK_ANH= "linkanh";
    private static final String TB_COL_LINK_FILE="linkfile";
    private  static  SQLiteHelper helper;
    SQLiteDatabase db;
    public static synchronized SQLiteHelper getHeler(Context context){
        if(helper==null){
            helper=new SQLiteHelper(context);
        }
        return helper;
    }
    public SQLiteHelper(Context context) {
        super(context, DB_NAME, null, DB_VERSION);
    }

    @Override
    public void onCreate(SQLiteDatabase db) {
        db.execSQL("CREATE TABLE " + TB_NAME + "( " +
                TB_COL_ID + " TEXT PRIMARY KEY, " +
                TB_COL_NAME + " TEXT, " +
                TB_COL_TACGIA + " TEXT, " +
                TB_COL_LINK_ANH + " TEXT, " +
                TB_COL_LINK_FILE + " TEXT )"
        );
    }

    public void insert(SachModel data){
        db = this.getWritableDatabase();
        ContentValues value=new ContentValues();
        value.put(TB_COL_ID,data.getMasach());
        Log.d("id",data.getMasach());
        value.put(TB_COL_NAME,data.getTensach());
        value.put(TB_COL_TACGIA,data.getTacgia());
        value.put(TB_COL_LINK_ANH,data.getHinhanh());
        value.put(TB_COL_LINK_FILE,data.getFile());
        db.insert(TB_NAME,null,value);
    }

    public void update( SachModel data, String id){
        db = this.getWritableDatabase();
        ContentValues value=new ContentValues();
        value.put(TB_COL_ID,data.getMasach());
        value.put(TB_COL_NAME,data.getTensach());
        value.put(TB_COL_TACGIA,data.getTacgia());
        value.put(TB_COL_LINK_ANH,data.getHinhanh());
        value.put(TB_COL_LINK_FILE,data.getFile());
        db.update(TB_NAME,value, TB_COL_ID+"="+id,null);
    }
    public void deleteAll(){
        SQLiteDatabase db=getWritableDatabase();
        db.delete(TB_NAME,null,null);
    }
    public void loadData(ArrayList<SachModel> arrPersion){
        SQLiteDatabase db = getWritableDatabase();
        Cursor cursor = db.rawQuery("select * from " + TB_NAME,null);
        if(cursor.moveToFirst()){
            do{
                String id = cursor.getString(cursor.getColumnIndex(TB_COL_ID));
                String tensach = cursor.getString(cursor.getColumnIndex(TB_COL_NAME));
                String tacgia = cursor.getString(cursor.getColumnIndex(TB_COL_TACGIA));
                String linkanh = cursor.getString(cursor.getColumnIndex(TB_COL_LINK_ANH));
                String linkfile = cursor.getString(cursor.getColumnIndex(TB_COL_LINK_FILE));
                SachModel sachModel=new SachModel();
                sachModel.setMasach(id);
                sachModel.setTensach(tensach);
                sachModel.setTacgia(tacgia);
                sachModel.setHinhanh(linkanh);
                sachModel.setFile(linkfile);
                arrPersion.add(sachModel);
                Log.d("DB",id+"");
            }while (cursor.moveToNext());
        }
        db.close();
    }
    public ArrayList<SachModel> getAllData(){
        ArrayList<SachModel> data=new ArrayList<>();
        db = this.getWritableDatabase();
        Cursor cursor = db.rawQuery("select * from " + TB_NAME,null);
        if(cursor.moveToFirst()){
            do{
                SachModel sachModel1=new SachModel();
                sachModel1.setMasach(cursor.getString(cursor.getColumnIndex(TB_COL_ID)));
                sachModel1.setTensach(cursor.getString(cursor.getColumnIndex(TB_COL_NAME)));
                sachModel1.setTacgia(cursor.getString(cursor.getColumnIndex(TB_COL_TACGIA)));
                sachModel1.setHinhanh(cursor.getString(cursor.getColumnIndex(TB_COL_LINK_ANH)));
                sachModel1.setFile(cursor.getString(cursor.getColumnIndex(TB_COL_LINK_FILE)));
                data.add(sachModel1);
            }while (cursor.moveToNext());
        }
        db.close();
        return data;
    }
    public SachModel getData(String id){
        SQLiteDatabase db = this.getWritableDatabase();
        SachModel sachModel1=new SachModel();
        Cursor cursor=db.query(TB_NAME,new String[]{TB_COL_ID,TB_COL_NAME,TB_COL_TACGIA,TB_COL_LINK_ANH,TB_COL_LINK_FILE},TB_COL_ID+"="+id,null,null,null,null);
        if(cursor.moveToFirst()){
            do{
                sachModel1.setMasach(cursor.getString(cursor.getColumnIndex(TB_COL_ID)));
                sachModel1.setTensach(cursor.getString(cursor.getColumnIndex(TB_COL_NAME)));
                sachModel1.setTacgia(cursor.getString(cursor.getColumnIndex(TB_COL_TACGIA)));
                sachModel1.setHinhanh(cursor.getString(cursor.getColumnIndex(TB_COL_LINK_ANH)));
                sachModel1.setFile(cursor.getString(cursor.getColumnIndex(TB_COL_LINK_FILE)));
            }while (cursor.moveToNext());
        }
        db.close();
        return sachModel1;
    }
    public void saveData(ArrayList<SachModel> sachModelArrayList){
        SQLiteDatabase db = getWritableDatabase();
        db.execSQL("delete from " + TB_NAME);
        db.execSQL("UPDATE SQLITE_SEQUENCE SET SEQ=0 WHERE NAME = '" + TB_NAME + "'");
        for (SachModel data : sachModelArrayList){
            ContentValues value = new ContentValues();
            value.put(TB_COL_ID,data.getMasach());
            value.put(TB_COL_NAME,data.getTensach());
            value.put(TB_COL_TACGIA,data.getTacgia());
            value.put(TB_COL_LINK_ANH,data.getHinhanh());
            value.put(TB_COL_LINK_FILE,data.getFile());
            db.insert(TB_NAME,null,value);

        }
        db.close();
    }
    public void delete(int id){
        SQLiteDatabase db = getWritableDatabase();
        db.delete(TB_NAME,TB_COL_ID + "="+id,null);
        db.close();
    }


    @Override
    public void onUpgrade(SQLiteDatabase db, int i, int i1) {
        db.execSQL("DROP TABLE IF EXISTS " + TB_NAME);

        // Create tables again
        onCreate(db);
    }
}

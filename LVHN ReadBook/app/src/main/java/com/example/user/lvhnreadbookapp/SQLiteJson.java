package com.example.user.lvhnreadbookapp;

import android.content.ContentValues;
import android.content.Context;
import android.database.Cursor;
import android.database.sqlite.SQLiteDatabase;
import android.database.sqlite.SQLiteOpenHelper;

import java.util.ArrayList;

public class SQLiteJson extends SQLiteOpenHelper {
    Context context;
    private static SQLiteJson json;
    private static final String DB_NAME = "db_json";
    private static final int DB_VERSION = 3;
    private static final String TB_NAME = "tb_json";
    private static final String TB_COL_ID = "link";
    private static final String TB_COL_NAME = "bookID";
    private static final String TB_COL_CHAPID = "chapID";
    private static final String TB_COL_CHAPINDEX = "chapindex";
    private static final String TB_COL_CHAPHREF = "chapHREF";
    private static final String TB_COL_USINGID = "usingID";
    private static final String TB_COL_VALUE = "value";
    public void insertcolumn(){
        SQLiteDatabase db;
//        db.execSQL("ALTER TABLE " + TB_NAME +
//                " ADD COLUMN " + TB_COL_CHAPID + " text");
    }
    public void onConfigure(SQLiteDatabase db) {
        super.onConfigure(db);
        db.setForeignKeyConstraintsEnabled(true);
    }
    public static synchronized SQLiteJson getHelper(Context context){
        if (json==null)
            json=new SQLiteJson(context);
        return json;
    }
    public SQLiteJson(Context context) {
        super(context, DB_NAME, null, DB_VERSION);
        this.context=context;
    }

    @Override
    public void onCreate(SQLiteDatabase db) {
        db.execSQL("CREATE TABLE " + TB_NAME + "( " +
                TB_COL_ID + " TEXT PRIMARY KEY, " +
                TB_COL_NAME + " TEXT, " +
                TB_COL_CHAPID + " TEXT, " +
                TB_COL_CHAPINDEX + " INTEGER, " +
                TB_COL_CHAPHREF + " TEXT, " +
                TB_COL_USINGID + " BOOL, " +
                TB_COL_VALUE + " TEXT )"
        );
    }
    public void insert(String link, String s1, String s4, int i, String s2, boolean b, String s3){
        SQLiteDatabase db = getWritableDatabase();
        ContentValues value=new ContentValues();
        value.put(TB_COL_ID,link);
        value.put(TB_COL_NAME, s1);
        value.put(TB_COL_CHAPID,s4);
        value.put(TB_COL_CHAPINDEX,i);
        value.put(TB_COL_CHAPHREF,s2);
        value.put(TB_COL_USINGID,b);
        value.put(TB_COL_VALUE,s3);
        db.insert(TB_NAME,null,value);
    }
    public void updateBookID( String link, String bookID){
        SQLiteDatabase db = getWritableDatabase();
        ContentValues value=new ContentValues();
        value.put(TB_COL_NAME,bookID);
        db.update(TB_NAME,value, TB_COL_ID+"= '"+link+"'",null);
    }
    public void updateChapID( String link, String chapID){
        SQLiteDatabase db = getWritableDatabase();
        ContentValues value=new ContentValues();
        value.put(TB_COL_CHAPID,chapID);
        db.update(TB_NAME,value, TB_COL_ID+"= '"+link+"'",null);
    }
    public void updateUsingID( String link, boolean usingID){
        SQLiteDatabase db = getWritableDatabase();
        ContentValues value=new ContentValues();
        value.put(TB_COL_USINGID,usingID);
        db.update(TB_NAME,value, TB_COL_ID+"= '"+link+"'",null);
    }
    public void updateChapHref( String link, String chapHref){
        SQLiteDatabase db = getWritableDatabase();
        ContentValues value=new ContentValues();
        value.put(TB_COL_CHAPHREF,chapHref);
        db.update(TB_NAME,value, TB_COL_ID+"= '"+link+"'",null);
    }
    public void updateChapIndex( String link, int chapIndex){
        SQLiteDatabase db = getWritableDatabase();
        ContentValues value=new ContentValues();
        value.put(TB_COL_CHAPINDEX,chapIndex);
        db.update(TB_NAME,value, TB_COL_ID+"= '"+link+"'",null);
    }
    public void updateValue( String link, String s){
        SQLiteDatabase db = getWritableDatabase();
        ContentValues value=new ContentValues();
        value.put(TB_COL_VALUE,s);
        db.update(TB_NAME,value, TB_COL_ID+"= '"+link+"'",null);
    }
    public String getBookID(String link){
        String kq="";
        SQLiteDatabase db = this.getWritableDatabase();
        Cursor cursor=db.rawQuery("select * from "+TB_NAME+" where link = '"+link+"'",null);
        if(cursor.moveToFirst()){
            do{
                kq=(cursor.getString(cursor.getColumnIndex(TB_COL_NAME)));
            }while (cursor.moveToNext());
        }
        db.close();
        return kq;
    }
    public int getChapIndex(String link){
        int kq=0;
        SQLiteDatabase db = this.getWritableDatabase();
        Cursor cursor=db.rawQuery("select * from "+TB_NAME+" where link = '"+link+"'",null);
        if(cursor.moveToFirst()){
            do{
                kq=cursor.getInt(cursor.getColumnIndex(TB_COL_CHAPINDEX));
            }while (cursor.moveToNext());
        }
        db.close();
        return kq;
    }
    public String  getChapID(String link){
        String kq="";
        SQLiteDatabase db = this.getWritableDatabase();
        Cursor cursor=db.rawQuery("select * from "+TB_NAME+" where link = '"+link+"'",null);
        if(cursor.moveToFirst()){
            do{
                kq=cursor.getString(cursor.getColumnIndex(TB_COL_CHAPID));
            }while (cursor.moveToNext());
        }
        db.close();
        return kq;
    }
    public String getChapHref(String link){
        String kq="";
        SQLiteDatabase db = this.getWritableDatabase();
        Cursor cursor=db.rawQuery("select * from "+TB_NAME+" where link = '"+link+"'",null);
        if(cursor.moveToFirst()){
            do{
                kq=(cursor.getString(cursor.getColumnIndex(TB_COL_CHAPHREF)));
            }while (cursor.moveToNext());
        }
        db.close();
        return kq;
    }
    public boolean getUsingID(String link){
        boolean kq=true;
        SQLiteDatabase db = this.getWritableDatabase();
        Cursor cursor=db.rawQuery("select * from "+TB_NAME+" where link = '"+link+"'",null);
        if(cursor.moveToFirst()){
            do{
                kq=(cursor.getInt(cursor.getColumnIndex(TB_COL_NAME))>0);
            }while (cursor.moveToNext());
        }
        db.close();
        return kq;
    }
    public String getValue(String link){
        String kq="";
        SQLiteDatabase db = this.getWritableDatabase();
        Cursor cursor=db.rawQuery("select * from "+TB_NAME+" where link = '"+link+"'",null);
        if(cursor.moveToFirst()){
            do{
                kq=(cursor.getString(cursor.getColumnIndex(TB_COL_VALUE)));
            }while (cursor.moveToNext());
        }
        db.close();
        return kq;
    }
    public void deleteAll(){

        SQLiteDatabase db=getWritableDatabase();
        db.delete(TB_NAME,null,null);
    }
    public void loadData(ArrayList<String> arrPersion){
        SQLiteDatabase db = getWritableDatabase();
        Cursor cursor = db.rawQuery("select * from " + TB_NAME,null);
        if(cursor.moveToFirst()){
            do{
                String id = cursor.getString(cursor.getColumnIndex(TB_COL_ID));

                arrPersion.add(id);
            }while (cursor.moveToNext());
        }
        db.close();
    }
    public String getData(String id){
        String kq="";
        SQLiteDatabase db = this.getWritableDatabase();
        Cursor cursor=db.rawQuery("select * from "+TB_NAME+" where link = '"+id+"'",null);
        if(cursor.moveToFirst()){
            do{
                kq=(cursor.getString(cursor.getColumnIndex(TB_COL_NAME)));
            }while (cursor.moveToNext());
        }
        db.close();
        return kq;
    }
    public void onUpgrade(SQLiteDatabase db, int i, int i1) {
        db.execSQL("DROP TABLE IF EXISTS " + TB_NAME);

        // Create tables again
        onCreate(db);
    }
}

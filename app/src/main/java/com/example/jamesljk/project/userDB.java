package com.example.jamesljk.project;

import android.content.ContentValues;
import android.content.Context;
import android.database.Cursor;
import android.database.sqlite.SQLiteDatabase;
import android.database.sqlite.SQLiteOpenHelper;
import android.util.Log;

/**
 * Created by qinwei on 2016/11/16.
 */

public class userDB extends SQLiteOpenHelper {
    private static final String DB_NAME = "Memo.db";
    private static final String TABLE_NAME = "Memo";
    private static final int DB_VERSION = 1;

    public userDB(Context context) {
        super(context, DB_NAME, null, DB_VERSION);
    }

    @Override
    public void onCreate(SQLiteDatabase db) {  //oncreate函数在类创建时执行，在数据库中创建指定表
        try{
            String CREATE_TABLE = "create table if not exists " + TABLE_NAME
                    + "( _id TEXT primary key,content TEXT)";
            db.execSQL(CREATE_TABLE);
        }catch ( Exception e ){
            e.printStackTrace();
        }
    }

    @Override
    public void onUpgrade(SQLiteDatabase db, int oldVersion, int newVersion){ }

    public void insertDB(String id,String content){   //向数据库插入条目
        try{
            SQLiteDatabase db = getWritableDatabase();
            ContentValues cv = new ContentValues();
            cv.put("_id",id);
            cv.put("content",content);
            long n= db.insert(TABLE_NAME,null,cv);
            if(n<=0){
                Log.d("bug","数据库插入失败");
            }
            db.close();
        }catch ( Exception e ){
            e.printStackTrace();
        }
    }

    public void updateDB(String id,String content){//更新数据库
        SQLiteDatabase db = getWritableDatabase();
        String whereClause = "_id = ?";
        String[] whereArgs = { id };
        ContentValues cv = new ContentValues();
        cv.put("_id",id);
        cv.put("content",content);
        db.update(TABLE_NAME,cv, whereClause, whereArgs);
        db.close();
    }

    public void deleteDB(String id) {   //在数据库中给删除指定条目
        SQLiteDatabase db = getWritableDatabase();
        String whereClause = "_id = ?";
        String[] whereArgs = { id };

        db.delete(TABLE_NAME, whereClause, whereArgs);
        db.close();
    }

    public Cursor queryDB() {                                    //遍历数据库，返回指定表中所有内容
        SQLiteDatabase db = getReadableDatabase();
        return db.query(TABLE_NAME, null, null, null, null, null, null);
    }

    public Cursor queryDB2(String id) {                     //根据字段查询指定条目，用于判断联系人是否已经存在

            SQLiteDatabase db = getReadableDatabase();
            Cursor cur = db.rawQuery("select * from "+TABLE_NAME+" where _id='" + id + "'", null);
        return cur;
    }

    @Override
    public void onOpen(SQLiteDatabase db) {
        // TODO 每次成功打开数据库后首先被执行
        super.onOpen(db);
    }

}

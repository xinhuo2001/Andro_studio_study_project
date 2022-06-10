package com.example.ynote.Manager;

import android.annotation.SuppressLint;
import android.content.ContentValues;
import android.content.Context;
import android.database.Cursor;
import android.database.sqlite.SQLiteDatabase;
import android.database.sqlite.SQLiteOpenHelper;
import android.util.Log;

import com.example.ynote.Model.Date;
import com.example.ynote.Model.Note;

import java.util.ArrayList;
import java.util.List;

public class DBManager {
    private Context mContext;

    public DBManager(Context context){
        mContext=context;
    }

    //给定标题 内容插入数据
    public void addDataByString(String title, String content) {
        try {
            Note note = new Note(title, new Date(), content);
//            note.showNote();
            //制作数据集
            ContentValues values = getContentValues(note);
            //准备写入数据
            DBHelper dbHelper = DBHelper.getInstance(mContext);
            SQLiteDatabase db = dbHelper.getWritableDatabase();
            //向NotesTable表写入数据
            Long ret = db.insert(dbHelper.getNotesTableName(), null, values);
            dbHelper.close();
        } catch (Exception e) {
            e.printStackTrace();
        }
    }

    //根据Note类制作数据集
    public ContentValues getContentValues(Note note) {
        ContentValues values = new ContentValues();
        //必备信息
        values.put("title", note.getTitle());
        values.put("content", note.getContent());
        values.put("date", note.getDate().getDateString());
        //添加信息 (未完成 皆为默认值)
        values.put("folderName", note.getFolderName());
        values.put("level", note.getLevel());
        values.put("location", note.getLocation());
        values.put("deleteDate", note.getDeleteDate().getDateString());
        return values;
    }

    //展示数据库当前信息
    public void showDatabaseInfo(){
        Log.d("yzf", "展示数据库信息");
        DBHelper dbHelper = DBHelper.getInstance(mContext);
        SQLiteDatabase db = dbHelper.getReadableDatabase();
        //Note表
        Cursor cursor_note = db.query(dbHelper.getNotesTableName(), null, null, null, null, null, null);
        if(cursor_note.moveToFirst()) {
            Log.d("yzf", "有数据");
            do{
                @SuppressLint("Range") String title = cursor_note.getString(cursor_note.getColumnIndex("title"));
                @SuppressLint("Range") String content = cursor_note.getString(cursor_note.getColumnIndex("content"));
                @SuppressLint("Range") String date = cursor_note.getString(cursor_note.getColumnIndex("date"));
//                Log.d("yzf", "date str:" + date);
                Note note = new Note(title, new Date(date), content);
                note.showNote();
            }while(cursor_note.moveToNext());
        }
        dbHelper.close();
        cursor_note.close();
    }

    //清空数据库信息
    public void deleteDatabase() {
        DBHelper dbHelper = DBHelper.getInstance(mContext);
        SQLiteDatabase db = dbHelper.getWritableDatabase();
        //清空notes表
        String delete_notes_sql = "delete from " + dbHelper.getNotesTableName();
        db.execSQL(delete_notes_sql);
    }

    //将数据库信息以list形式返回
    public List<Note> tableDataToList(String tableName) {
        //待返还数据
        List<Note> retList = new ArrayList<>();
        DBHelper dbHelper = DBHelper.getInstance(mContext);
        SQLiteDatabase db = dbHelper.getReadableDatabase();
        //Note表
        Cursor cursor_note = db.query(tableName, null, null, null, null, null, null);
        if(cursor_note.moveToFirst()) {
            Log.d("yzf", "有数据");
            do{
                @SuppressLint("Range") String title = cursor_note.getString(cursor_note.getColumnIndex("title"));
                @SuppressLint("Range") String content = cursor_note.getString(cursor_note.getColumnIndex("content"));
                @SuppressLint("Range") String date = cursor_note.getString(cursor_note.getColumnIndex("date"));
//                Log.d("yzf", "date str:" + date);
                Note note = new Note(title, new Date(date), content);
                retList.add(note);
//                note.showNote();
            }while(cursor_note.moveToNext());
        }
        dbHelper.close();
        cursor_note.close();
        return retList;
    }

    //返回notes表字符串
    public String getNotesTableName() {
        DBHelper dbHelper = DBHelper.getInstance(mContext);
        String ret = dbHelper.getNotesTableName();
        dbHelper.close();
        return ret;
    }
}

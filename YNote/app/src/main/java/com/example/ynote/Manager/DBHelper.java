package com.example.ynote.Manager;

import android.content.Context;
import android.database.sqlite.SQLiteDatabase;
import android.database.sqlite.SQLiteOpenHelper;
import android.net.http.SslCertificate;
import android.util.Log;
import android.widget.Toast;

import androidx.annotation.Nullable;

public class DBHelper extends SQLiteOpenHelper {

    Context mContext;
    static final String DBName = "Notes.db";
    private static DBHelper dbHelper = null;

    static final String NotesTable = "notes";
    static final String RecycleTable = "recycle";

    //1.数据表
    public static final String CREATE_NOTE="create table " + NotesTable + " ("
            + "id integer primary key autoincrement,"
            + "title text,"
            + "content text,"
            + "date text,"
            + "folderName text,"
            + "level integer,"
            + "location text,"
            + "deleteDate text)";
    //2.回收站表
    public static final String CREATE_RECYCLE="create table " + RecycleTable + " ("
            + "id integer primary key autoincrement,"
            + "title text,"
            + "content text,"
            + "date text,"
            + "folderName text,"
            + "level integer,"
            + "location text,"
            + "deleteDate text)";

    public DBHelper(@Nullable Context context, @Nullable String name, @Nullable SQLiteDatabase.CursorFactory factory, int version) {
        super(context, name, factory, version);
        this.mContext = context;
    }

    @Override
    public void onCreate(SQLiteDatabase sqLiteDatabase) {
        sqLiteDatabase.execSQL(CREATE_NOTE);
        sqLiteDatabase.execSQL(CREATE_RECYCLE);
        Log.d("yzf", "创建数据库成功");
    }

    @Override
    public void onUpgrade(SQLiteDatabase sqLiteDatabase, int i, int i1) {

    }

    /**
     * 单例模式
     * @param context
     * @return
     */
    public static DBHelper getInstance(Context context) {
        if (dbHelper == null) {
            dbHelper = new DBHelper(context, DBName, null, 1);
        }
        return dbHelper;
    }

    public String getNotesTableName(){
        return NotesTable;
    }

    public String getRecycleTableName(){
        return RecycleTable;
    }


}

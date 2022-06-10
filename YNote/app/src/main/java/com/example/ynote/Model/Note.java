package com.example.ynote.Model;

import android.util.Log;

public class Note extends Item{
    /**
     * 必备信息
     * */
    //文本内容
    private String content;

    /**
     * 附加信息 后期完善
     * */
    //Note重要级别
    public static final  int RED_LEVEL = 2;
    public static final  int ORA_LEVEL = 1;
    public static final  int GRE_LEVEL = 0;
    //Item:title, date, folderName
    //使用上面三个级别标识事件重要性
    private int level;
    //位置
    private String location;
    //删除日期
    private Date deleteDate;


    public Note(String title, Date date, String content) {
        super(title, date, "未分类");
        this.content = content;
        this.location="";
        //默认紧急级别最低(绿色)
        this.level  = GRE_LEVEL;
        deleteDate = new Date();
    }

    public void showNote() {
        String s = "yzf";
        Log.d(s, "标题:" + super.getTitle());
        Log.d(s, "内容:" + this.getContent());
        Log.d(s, "日期:" + super.getDate().getDateString());
    }

    public int getLevel() {
        return level;
    }

    public void setLevel(int level) {
        this.level = level;
    }

    public String getContent() {
        return content;
    }

    public void setContent(String content) {
        this.content = content;
    }

    public String getLocation() {
        return location;
    }

    public void setLocation(String location) {
        this.location = location;
    }

    public Date getDeleteDate() {
        return deleteDate;
    }

    public void setDeleteDate(Date deleteDate) {
        this.deleteDate = deleteDate;
    }
}

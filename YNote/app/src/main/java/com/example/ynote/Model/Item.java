package com.example.ynote.Model;

public class Item {
    //必备信息
    //标题
    private String title;
    //日期
    private Date date;

    //条目文件夹(分类) 后期添加 不必要
    private String folderName;

    public String getFolderName() {
        return folderName;
    }

    public void setFolderName(String folderName) {
        this.folderName = folderName;
    }

    public String getTitle() {
        return title;
    }

    public void setTitle(String title) {
        this.title = title;
    }

    public Date getDate() {
        return date;
    }

    public void setDate(Date date) {
        this.date = date;
    }

    public Item(String title, Date date, String folderName) {
        this.folderName = folderName;
        this.date = date;
        this.title = title;
    }
}

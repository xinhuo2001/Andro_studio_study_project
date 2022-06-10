package com.example.ynote.Manager;

import android.app.Activity;
import android.content.Context;
import android.content.SharedPreferences;
import android.content.res.Resources;
import android.graphics.Bitmap;
import android.graphics.BitmapFactory;
import android.graphics.drawable.BitmapDrawable;
import android.graphics.drawable.Drawable;
import android.net.Uri;
import android.os.Environment;
import android.provider.MediaStore;
import android.util.Log;

import com.example.ynote.Util.AppUtil;

import java.io.File;
import java.io.FileNotFoundException;
import java.io.FileOutputStream;

//侧滑栏界面用户信息设置
public class PersonInfoManager {
    private Context mContext;
    static String path;

    public PersonInfoManager(Context mContext) {
        this.mContext = mContext;
        path = Environment.getExternalStorageDirectory().getPath();
        path += "/images/";
        Log.d("yzf1", path);
    }

    //获取图片
    public Drawable getHeadImg(){

        //第一次启动程序 不获取
        if(AppUtil.isFirstStart((Activity) mContext)){
            Log.d("yzf", "第一次启动");
            return  null;
        }
        Log.d("yzf", "第N次启动");
        //不是第一次启动 获取头像图片
        Bitmap bt = getBitmap(path + "head.jpg");
        if(bt==null) {
            Log.d("yzf", "获取图片失败");
            return null;
        }
        Log.d("yzf", "获取图片成功");
        //将头像图片转换为Drawable
        Resources resources = mContext.getResources();
        Drawable drawable = new BitmapDrawable(resources,bt);
        return drawable;
    }

    //保存头像图片
    public void setHeadImg(Uri uri){
        Bitmap head;
        try {
            //从uri 获取 图片
            head = MediaStore.Images.Media.getBitmap
                    (mContext.getContentResolver(), uri);

            if (head != null) {
                saveIgmToSD(head);// 保存在SD卡中
                //释放位图空间
                if (head != null && head.isRecycled()) {
                    head.recycle();
                }
            }
        }
        catch (Exception e){
        }
    }

    //存储位图到指定位置
    private void saveIgmToSD(Bitmap mBitmap) {
        String sdStatus = Environment.getExternalStorageState();
        if (!sdStatus.equals(Environment.MEDIA_MOUNTED)) { // 检测sd是否可用
            return;
        }
        FileOutputStream b = null;
        File file = new File(path);
        file.mkdirs();// 创建文件夹
        String fileName = path + "head.jpg";// 图片名字
        try {
            b = new FileOutputStream(fileName);
            mBitmap.compress(Bitmap.CompressFormat.JPEG, 100, b);// 把数据写入文件
        } catch (FileNotFoundException e) {
            e.printStackTrace();
        } finally {
            try {
                // 关闭流
                b.flush();
                b.close();
            } catch (Exception e) {
                e.printStackTrace();
            }
        }
    }

    // 从本地的文件中以保存的图片中 获取图片的方法
    private Bitmap getBitmap(String pathString) {
        Bitmap bitmap = null;
        try {
            File file = new File(pathString);
            if (file.exists()) {
                bitmap = BitmapFactory.decodeFile(pathString);
            }
        } catch (Exception e) {
            e.printStackTrace();
        }
        return bitmap;
    }

    //存储名字
    public void savePersonName(String name) {
        SharedPreferences.Editor editor = mContext.getSharedPreferences("personal_Name", Context.MODE_PRIVATE).edit();
        editor.putString("name", name);
        editor.apply();
    }

    //读入名字
    public String getPersonName() {
        SharedPreferences reader = mContext.getSharedPreferences("personal_Name", Context.MODE_PRIVATE);
        String name = reader.getString("name", "YNote");
        return name;
    }
}

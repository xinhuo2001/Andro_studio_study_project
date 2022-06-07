package com.example.ynote.Activity;

import android.content.Intent;
import android.os.Bundle;
import android.util.Log;
import android.view.MenuItem;
import android.view.View;
import android.widget.TextView;
import android.widget.Toast;

import androidx.annotation.Nullable;
import androidx.appcompat.app.AppCompatActivity;
import androidx.appcompat.widget.Toolbar;

import com.example.ynote.R;
import com.getbase.floatingactionbutton.FloatingActionButton;
import com.getbase.floatingactionbutton.FloatingActionsMenu;

public class MainActivity extends AppCompatActivity {


    //备忘录标题 初始默认Notes
    private String currentFolderName ="Notes";

    @Override
    protected void onCreate(@Nullable Bundle savedInstanceState) {
        super.onCreate(savedInstanceState);
        setContentView(R.layout.activity_main);
        init();
    }
    //初始化界面
    public void init() {
        actionbarReset();
        fab_setting();
    }
    //设置主界面导航栏
    public void actionbarReset() {
        //导航栏
        Toolbar toolbar = (Toolbar) findViewById(R.id.toolbar);
        //点击头像打开侧栏
        findViewById(R.id.head_icon_main).setOnClickListener(new View.OnClickListener() {
            @Override
            public void onClick(View view) {
                Toast.makeText(MainActivity.this, "展开侧边栏", Toast.LENGTH_SHORT).show();
            }
        });

        //设置标题
        TextView toolbar_title = findViewById(R.id.toolbar_title);
        toolbar_title.setText(currentFolderName);

        //导航栏右边设置一个搜索标志
        toolbar.inflateMenu(R.menu.memu_search);
        //监听搜索点击事件
        toolbar.setOnMenuItemClickListener(new Toolbar.OnMenuItemClickListener() {
            @Override
            public boolean onMenuItemClick(MenuItem item) {
                switch (item.getItemId()) {
                    case R.id.search_item:
                        Toast.makeText(MainActivity.this, "打开搜索界面", Toast.LENGTH_SHORT).show();
                        break;
                }
                return false;
            }
        });

    }
    //设置主界面创建标志块(右下角)
    private void fab_setting(){
        FloatingActionButton fab = (FloatingActionButton)findViewById(R.id.action_note);

        fab.setOnClickListener(new View.OnClickListener() {
            @Override
            public void onClick(View v) {
                Toast.makeText(MainActivity.this, "创建新条目", Toast.LENGTH_SHORT).show();
            }
        });

        FloatingActionButton fab_quick = (FloatingActionButton)findViewById(R.id.action_quick);

        fab_quick.setOnClickListener(new View.OnClickListener() {
            @Override
            public void onClick(View v) {
                Toast.makeText(MainActivity.this, "快速创建新条目", Toast.LENGTH_SHORT).show();
            }
        });
    }
}

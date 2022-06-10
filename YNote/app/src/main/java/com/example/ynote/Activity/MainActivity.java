package com.example.ynote.Activity;

import android.content.Intent;
import android.content.res.ColorStateList;
import android.content.res.Resources;
import android.graphics.drawable.Drawable;
import android.os.Bundle;
import android.util.Log;
import android.view.MenuItem;
import android.view.View;
import android.widget.TextView;
import android.widget.Toast;

import androidx.annotation.NonNull;
import androidx.annotation.Nullable;
import androidx.appcompat.app.AppCompatActivity;
import androidx.appcompat.widget.Toolbar;
import androidx.drawerlayout.widget.DrawerLayout;
import androidx.recyclerview.widget.LinearLayoutManager;
import androidx.recyclerview.widget.RecyclerView;

import com.example.ynote.Adapter.RecyclerViewAdapter;
import com.example.ynote.Manager.DBManager;
import com.example.ynote.Manager.PersonInfoManager;
import com.example.ynote.Model.Note;
import com.example.ynote.R;
import com.getbase.floatingactionbutton.FloatingActionButton;
import com.getbase.floatingactionbutton.FloatingActionsMenu;
import com.google.android.material.navigation.NavigationView;

import java.util.ArrayList;
import java.util.List;

import de.hdodenhof.circleimageview.CircleImageView;

public class MainActivity extends AppCompatActivity {

    //主界面整体
    private DrawerLayout mDrawer;
    //备忘录标题 初始默认Notes
    private String currentFolderName ="Notes";
    //当前列表数据
    private List<Note> curNoteList = new ArrayList<>();

    @Override
    protected void onCreate(@Nullable Bundle savedInstanceState) {
        super.onCreate(savedInstanceState);
        setContentView(R.layout.activity_main);
        init();
    }

    //测试函数 数据库
    public void testDatabase() {
        DBManager dbManager = new DBManager(MainActivity.this);
        dbManager.deleteDatabase();
        dbManager.addDataByString("党课", "下午上党课别忘了");
        dbManager.addDataByString("作业", "课设作业ddl");
        dbManager.showDatabaseInfo();
    }
    //初始化界面
    public void init() {
        actionbarReset();
        fab_setting();
        navigation_setting();
        getNotesListData();
        setRecyclerView();
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
    //设置侧滑栏点击事件
    private void navigation_setting() {
        mDrawer = (DrawerLayout) findViewById(R.id.drawer_layout);
        //侧滑栏
        NavigationView navigation = (NavigationView)findViewById(R.id.navigation);
        //监听点击事件
        navigation.setNavigationItemSelectedListener(new NavigationView.OnNavigationItemSelectedListener() {
            @Override
            public boolean onNavigationItemSelected(@NonNull MenuItem item) {
                switch ( item.getTitle().toString() ) {
                    case "所有分类":
                        Toast.makeText(MainActivity.this, "所有分类", Toast.LENGTH_SHORT).show();
                        break;
                    case "最近删除":
                        Toast.makeText(MainActivity.this, "最近删除", Toast.LENGTH_SHORT).show();
                        break;
                    case "隐私安全":
                        Toast.makeText(MainActivity.this, "隐私安全", Toast.LENGTH_SHORT).show();
                        break;
                    case "关于":
                        Toast.makeText(MainActivity.this, "关于", Toast.LENGTH_SHORT).show();
                        break;
                    case "退出 YNote":
                        System.exit(0);
                    default:
                        break;
                }
                //执行点击事件后关闭侧边栏
                mDrawer.closeDrawers();
                return false;
            }
        });
        //可有可无 设置navigation字体颜色
        Resources resource=getBaseContext().getResources();
        ColorStateList csl=resource.getColorStateList(R.color.item_color_navgtin);
        navigation.setItemTextColor(csl);

        //设置侧边栏头
        //1.将头部组件加到侧边栏
        View view = navigation.inflateHeaderView(R.layout.nav_head);
        //2.设置头部组件用户信息
        personalInfoSet(view);
    }
    //侧边栏头像和名字
    public void personalInfoSet(final View view) {
        PersonInfoManager personal = new PersonInfoManager(MainActivity.this);
        //从存储中读取用户信息
        final Drawable headImg = personal.getHeadImg();
        final String name = personal.getPersonName();

        //获取用户信息展示组件
        final TextView userName = (TextView) view.findViewById(R.id.useName_nav);
        final CircleImageView userImg = (CircleImageView) view.findViewById(R.id.useImg_nav);

        //设置头像和名字
        if(headImg!=null) {
            //获取到图像就设置 否则就用xml里面的数据
            Log.d("yzf", "设置用户头像");
            userImg.setImageDrawable(headImg);
        }
        userName.setText(name);

        View.OnClickListener listener = new View.OnClickListener() {
            @Override
            public void onClick(View v) {
                Toast.makeText(MainActivity.this,
                        "编辑信息",
                        Toast.LENGTH_SHORT).show();
                mDrawer.closeDrawers();
            }
        };



        userImg.setOnClickListener(listener);
        (view.findViewById(R.id.useEdit_nav)).setOnClickListener(listener);
    }
    //从数据库获取notes表数据放到curNoteList
    public void getNotesListData() {
        DBManager dbManager = new DBManager(MainActivity.this);
        curNoteList = dbManager.tableDataToList(dbManager.getNotesTableName());
    }
    //设置列表界面
    public void setRecyclerView() {
        //当前有数据 则隐藏Item
        if(curNoteList.isEmpty() == true) {
            Log.d("yzf", "列表是空的");
            findViewById(R.id.empty).setVisibility(View.VISIBLE);
        } else {
            Log.d("yzf", "列表不是空的");
            findViewById(R.id.empty).setVisibility(View.GONE);
        }
        RecyclerView recyclerView = findViewById(R.id.recyclerview);
        //控制格式
        LinearLayoutManager linearLayoutManager = new LinearLayoutManager(this);
        //控制方向
        linearLayoutManager.setOrientation(RecyclerView.VERTICAL);
        //将上面格式和方向的设置应用到当前recyclerView
        recyclerView.setLayoutManager(linearLayoutManager);

        //适配器
        RecyclerViewAdapter recyclerViewAdapter = new RecyclerViewAdapter(this, curNoteList);
        recyclerView.setAdapter(recyclerViewAdapter);
    }
}

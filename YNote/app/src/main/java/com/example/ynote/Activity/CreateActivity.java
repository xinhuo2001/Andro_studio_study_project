package com.example.ynote.Activity;

import android.content.Intent;
import android.os.Bundle;
import android.util.Log;
import android.view.View;
import android.widget.Button;
import android.widget.EditText;

import androidx.annotation.Nullable;
import androidx.appcompat.app.AppCompatActivity;

import com.example.ynote.Info.CodeInfo;
import com.example.ynote.R;

public class CreateActivity extends AppCompatActivity {

    private EditText edit_title;
    private EditText edit_content;
    private Button btn_save;
    private Button btn_cancel;

    //返回的数据
    Intent data = new Intent();


    @Override
    protected void onCreate(@Nullable Bundle savedInstanceState) {
        super.onCreate(savedInstanceState);
        setContentView(R.layout.activity_create);
        init();
        getMainData();
    }

    @Override
    protected void onDestroy() {
        super.onDestroy();
    }

    public void init() {
        edit_title = findViewById(R.id.edit_title);
        edit_content = findViewById(R.id.edit_content);
        btn_save = findViewById(R.id.btn_save);
        btn_cancel = findViewById(R.id.btn_cancel);

        btn_save.setOnClickListener(new View.OnClickListener() {
            @Override
            public void onClick(View view) {
                String title_str = edit_title.getText().toString();
                String content_str = edit_content.getText().toString();
                //空处理
                if(title_str == "" ) {
                    title_str = "未命名";
                }
                if(content_str == "") {
                    title_str = "空内容";
                }
                //将数据填入data
                data.putExtra("title", title_str);
                data.putExtra("content", content_str);
                data.putExtra("is_save", true);

                //打印语句
                {
                    Log.d("yzf", "请求保存");
                    Log.d("yzf", "title:" + title_str);
                    Log.d("yzf", "content:" + content_str);
                }
                setResult(CodeInfo.CREATE_CODE, data);
                finish();
            }
        });

        btn_cancel.setOnClickListener(new View.OnClickListener() {
            @Override
            public void onClick(View view) {
                data.putExtra("is_save", false);
                Log.d("yzf", "取消退出");
                setResult(CodeInfo.CREATE_CODE, data);
                finish();
            }
        });
    }


    public void getMainData() {
        Intent intent = getIntent();
        Bundle bundle = intent.getExtras();

        int send_code = bundle.getInt("SEND_CODE", -1);
        Log.d("yzf", "收到发送码:" + send_code);
    }

    public void sendInfoToMain() {
        setResult(CodeInfo.CREATE_CODE, data);
    }

}

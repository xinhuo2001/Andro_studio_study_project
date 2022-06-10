# 介绍

```sh
实现activity双向通信
```

# 代码

## Activity1.java

```java
//接口
RecyclerViewAdapter recyclerViewAdapter;

//在onCreate定义接口
@Override
    protected void onCreate(@Nullable Bundle savedInstanceState) {
        super.onCreate(savedInstanceState);
        setContentView(R.layout.activity_main);
        intentActivityResultLauncher = registerForActivityResult(new ActivityResultContracts.StartActivityForResult(), new ActivityResultCallback<ActivityResult>() {
            @Override
            public void onActivityResult(ActivityResult result) {
                Log.d("yzf", "获取到回调数据");
                Intent data = result.getData();
                int resultCode = result.getResultCode();
                Log.d("yzf", "回调code:" + resultCode);
                switch (resultCode){
                    case CodeInfo.CREATE_CODE:
                        //回调方法
                        updateDatabase(data);
                        break;
                    default:
                        break;
                }

            }
        });
    }

//调用接口
Intent intent = new Intent(MainActivity.this, CreateActivity.class);
Bundle bundle = new Bundle();
bundle.putInt("SEND_CODE", CodeInfo.MAIN_CODE);
intent.putExtras(bundle);
intentActivityResultLauncher.launch(intent);
```

## Activity2.java

```java
// 接收1发来的数据
Intent intent = getIntent();
Bundle bundle = intent.getExtras();
int send_code = bundle.getInt("SEND_CODE", -1);
Log.d("yzf", "收到发送码:" + send_code);

//向1回送数据
//返回的数据
Intent data = new Intent();
//将数据填入data
data.putExtra("title", title_str);
data.putExtra("content", content_str);
data.putExtra("is_save", true);
//设置data为回送数据 并设置码
setResult(CodeInfo.CREATE_CODE, data);
```


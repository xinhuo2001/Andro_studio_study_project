### date

```sh
2022 0529 编辑
```

### 简介

```sh
类似recyclerview
```

### 导包

```groovy
implementation "androidx.viewpager2:viewpager2:1.0.0"
```

### 文件结构

```sh
两个layout文件
	- 放置ViewPager2 #activity_main
	- 放置ViewPager2里面的内容 #item_viewpager
一个activity文件 用来做context 锁定ViewPager2组件后设置其Adapter进行展示数据 #MainActivity
ViewHolder类(文件)  用来锁定item_viewpager里面的组件 #这里内部类实现了
Adapter类(文件) 内部存放要展示的数据 并负责将数据填充入ViewHolder里面 #这里使用内部类实现

#结构如下
- layout
	- activity_main.xml
	- item_viewPager.xml
- java
	- MainActivity.java
		- MyViewHolder.class
		- MyAdapter.class
```

### 源代码

```sh
https://github.com/xinhuo2001/Andro_studio_study_project/tree/main/media_lesson
```

### 代码记录

#### MainActivity.java

```java
public class MainActivity extends AppCompatActivity {

    private ViewPager2 viewPager2;
    private MyAdapter myAdapter;

    @Override
    protected void onCreate(@Nullable Bundle savedInstanceState) {
        super.onCreate(savedInstanceState);
        setContentView(R.layout.activity_main);
		//锁定ViewPager2组件
        viewPager2 = findViewById(R.id.viewpager2);
        myAdapter = new MyAdapter();
        viewPager2.setAdapter(myAdapter);
    }
    
    public class MyViewHolder extends RecyclerView.ViewHolder {

        TextView textView;
        ImageView imageView;
		
        //锁定item组件
        public MyViewHolder(@NonNull View itemView) {
            super(itemView);
            textView = itemView.findViewById(R.id.item_text);
            imageView = itemView.findViewById(R.id.item_image);
        }
    }

    public class MyAdapter extends RecyclerView.Adapter<MyViewHolder> {

        List<Integer> numList = new ArrayList<>();
        //存放imageUrl
        List<String> imageList = new ArrayList<>();

        public MyAdapter() {
            numList.add(0);
            numList.add(1);
            numList.add(2);
        }
		
        //套路代码
        @NonNull
        @Override
        public MyViewHolder onCreateViewHolder(@NonNull ViewGroup parent, int viewType) {
            return new MyViewHolder(LayoutInflater.from(parent.getContext()).inflate(R.layout.item_viewpager2, parent, false));
        }
		
        //填充数据
        @Override
        public void onBindViewHolder(@NonNull MyViewHolder holder, int position) {
            holder.textView.setText(numList.get(position).toString());
        }

        @Override
        public int getItemCount() {
            return numList.size();
        }
    }
}
```

#### activity_main.xml

```xml
<?xml version="1.0" encoding="utf-8"?>
<androidx.constraintlayout.widget.ConstraintLayout xmlns:android="http://schemas.android.com/apk/res/android"
    android:layout_width="match_parent"
    android:layout_height="match_parent">

    <androidx.viewpager2.widget.ViewPager2
        android:id="@+id/viewpager2"
        android:layout_width="match_parent"
        android:layout_height="match_parent"/>

</androidx.constraintlayout.widget.ConstraintLayout>
```

#### item_viewPager.xml

```xml
<?xml version="1.0" encoding="utf-8"?>
<androidx.constraintlayout.widget.ConstraintLayout xmlns:android="http://schemas.android.com/apk/res/android"
    xmlns:tools="http://schemas.android.com/tools"
    android:layout_width="match_parent"
    android:layout_height="match_parent">

    <androidx.appcompat.widget.AppCompatTextView
        android:id="@+id/item_text"
        android:layout_width="match_parent"
        android:layout_height="wrap_content"
        tools:ignore="MissingConstraints" />

    <androidx.appcompat.widget.AppCompatImageView
        android:id="@+id/item_image"
        android:layout_width="match_parent"
        android:layout_height="match_parent"/>

</androidx.constraintlayout.widget.ConstraintLayout>
```


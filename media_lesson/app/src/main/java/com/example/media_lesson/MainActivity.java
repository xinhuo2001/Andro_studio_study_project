package com.example.media_lesson;

import android.os.Bundle;
import android.util.Log;
import android.view.LayoutInflater;
import android.view.View;
import android.view.ViewGroup;
import android.widget.ImageView;
import android.widget.TextView;
import android.widget.Toast;

import androidx.annotation.NonNull;
import androidx.annotation.Nullable;
import androidx.appcompat.app.AppCompatActivity;
import androidx.recyclerview.widget.RecyclerView;
import androidx.viewpager2.widget.ViewPager2;

import com.bumptech.glide.Glide;

import java.util.ArrayList;
import java.util.Arrays;
import java.util.List;

import retrofit2.Call;
import retrofit2.Callback;
import retrofit2.Response;


//网络接口
//https://unsplash.com/documentation#get-a-random-photo
//https://api.unsplash.com/photos/random
//3lypSQe2gg6xmYuUgdvJTjMIcGRg___DHpfc4A4_yuA
//all:https://api.unsplash.com/photos/random?client_id=3lypSQe2gg6xmYuUgdvJTjMIcGRg___DHpfc4A4_yuA&count=5

public class MainActivity extends AppCompatActivity {

    private ViewPager2 viewPager2;
    private MyAdapter myAdapter;

    @Override
    protected void onCreate(@Nullable Bundle savedInstanceState) {
        super.onCreate(savedInstanceState);
        setContentView(R.layout.activity_main);

        viewPager2 = findViewById(R.id.viewpager2);
        myAdapter = new MyAdapter();
        viewPager2.setAdapter(myAdapter);
        addImgUrlToAdapter(5);
        //确保能一直右移
        viewPager2.registerOnPageChangeCallback(new ViewPager2.OnPageChangeCallback() {
            @Override
            public void onPageScrollStateChanged(int state) {
                addImgUrlToAdapter(1);
                //当前处于第一页 不执行该操作
                if(viewPager2.getCurrentItem() != 0) {
                    //当界面静止下来
                    if(state == ViewPager2.SCROLL_STATE_IDLE) {
                        int first = myAdapter.numList.remove(0);
                        myAdapter.numList.add(first+3);
                        myAdapter.notifyDataSetChanged();
                        //设置false防止页面跳动
                        viewPager2.setCurrentItem(0, false);
                    }
                }
            }
        });
    }

    //发起网络请求 获取图片url 并把url加载到MyAdapter
    public void addImgUrlToAdapter(int count) {
        //网络请求设置图片
        UnsplashService.get().create(UnsplashRetrofitApi.class)
                .randomPics("aTDd1xsdCGMDF8Ew6rdqXnRczGvFmiWhpI-ZyQBbSgQ", count)
                .enqueue(new Callback<List<ImageModel>>() {
                    @Override
                    public void onResponse(Call<List<ImageModel>> call, Response<List<ImageModel>> response) {
                        Log.d("yzf", "net ok");
                        List<ImageModel> list = response.body();

                        if(list == null) {
                            Log.d("yzf", "list null");
                        }

                        if(list != null && list.isEmpty()) {
                            Log.d("yzf", "list empty");
                        }

                        if(list != null && !list.isEmpty()) {
                            //直接将imgUrl加载到myAdapter中
                            for (ImageModel imageModel : list) {
                                String regularUrl = imageModel.getRegular();
                                //预先下载图片
                                Glide.with(MainActivity.this).downloadOnly().load(regularUrl).preload();
                                myAdapter.imageList.add(regularUrl);
                            }


                            //这里需要notify 否则网络延迟会导致图片不显示
                            myAdapter.notifyDataSetChanged();
                        }
                    }

                    @Override
                    public void onFailure(Call<List<ImageModel>> call, Throwable t) {
                        Log.d("yzf", "net error");
                        Toast.makeText(MainActivity.this, "client_id被拉黑", Toast.LENGTH_SHORT);
                    }
                });
    }

    public class MyViewHolder extends RecyclerView.ViewHolder {

        TextView textView;
        ImageView imageView;

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

        @NonNull
        @Override
        public MyViewHolder onCreateViewHolder(@NonNull ViewGroup parent, int viewType) {
            return new MyViewHolder(LayoutInflater.from(parent.getContext()).inflate(R.layout.item_viewpager2, parent, false));
        }

        @Override
        public void onBindViewHolder(@NonNull MyViewHolder holder, int position) {
            holder.textView.setText(numList.get(position).toString());
            //本例中这里position永远只加载0
            if(imageList.size() > numList.get(position)) {
                Log.d("yzf", "find url position:" + position);
                Log.d("yzf", "bind img size:" + myAdapter.imageList.size());
                Log.d("yzf", "get url:" + imageList.get(numList.get(position)));
                String url = imageList.get(numList.get(position));
                Glide.with(holder.imageView.getContext())
                        .load(url)
                        .centerCrop()
                        .into(holder.imageView);
            } else {
                //防止闪屏
                Glide.with(holder.imageView.getContext()).clear(holder.imageView);
            }

        }

        @Override
        public int getItemCount() {
            return numList.size();
        }
    }
}

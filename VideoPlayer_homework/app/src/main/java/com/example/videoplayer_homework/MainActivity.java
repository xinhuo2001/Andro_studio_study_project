package com.example.videoplayer_homework;

import android.os.Bundle;
import android.os.Handler;
import android.os.Looper;
import android.os.Message;
import android.util.Log;
import android.view.View;
import android.widget.Button;
import android.widget.SeekBar;
import android.widget.TextView;
import android.widget.VideoView;

import androidx.annotation.NonNull;
import androidx.annotation.Nullable;
import androidx.appcompat.app.AppCompatActivity;

import java.util.Timer;
import java.util.TimerTask;

public class MainActivity extends AppCompatActivity {

    //控制按钮
    private Button buttonPlay;
    private Button buttonPause;
    private Button buttonContinue;
    private Button buttonExit;

    //进度条组件 需要static 供子线程修改
    private static SeekBar seekBar;
    private static TextView tv_progress; //当前播放进度
    private static TextView tv_total; //视频总时长

    //视频播放组件
    private VideoView videoView;

    //计时器
    private Timer timer; //时钟


    @Override
    protected void onCreate(@Nullable Bundle savedInstanceState) {
        super.onCreate(savedInstanceState);
        setContentView(R.layout.activity_main);
        setTitle("视频播放器");

        init();
        initSeekBar();

    }

    //初始化各种组件
    private void init() {
        //暂停播放
        buttonPause = findViewById(R.id.buttonPause);
        buttonPause.setOnClickListener(new View.OnClickListener() {
            @Override
            public void onClick(View v) {
                myPause();
            }
        });

        //开始播放
        buttonPlay = findViewById(R.id.buttonPlay);
        buttonPlay.setOnClickListener(new View.OnClickListener() {
            @Override
            public void onClick(View v) {
                myPlay();
            }
        });

        //继续播放
        buttonContinue = findViewById(R.id.buttonContinue);
        buttonContinue.setOnClickListener(new View.OnClickListener() {
            @Override
            public void onClick(View v) {
                myContinue();
            }
        });

        //退出
        buttonExit = findViewById(R.id.buttonExit);
        buttonExit.setOnClickListener(new View.OnClickListener() {
            @Override
            public void onClick(View v) {
                myExit();
            }
        });


        //视频组件
        videoView = findViewById(R.id.videoView);
        videoView.setVideoPath(getVideoPath(R.raw.ada));

        //进度条组件
        seekBar = findViewById(R.id.seekBar);
        tv_progress = findViewById(R.id.tv_progress);
        tv_total = findViewById(R.id.tv_total);


    }

    //处理进度条
    private void initSeekBar() {
        seekBar.setOnSeekBarChangeListener(new SeekBar.OnSeekBarChangeListener() {
            @Override
            public void onProgressChanged(SeekBar seekBar, int i, boolean b) {
                //进度条达到最大 视频停止
                if(i == seekBar.getMax()) {
                    myPause();
                }

                //用户拖动进度条 2
                if(b) {
                    Log.d("yzf", "onProgressChanged 2" + " 进度条时间:" + i);
                    mySeek(i);
                }
            }

            @Override
            public void onStartTrackingTouch(SeekBar seekBar) {
                //拖动 视频暂停 1
                Log.d("yzf", "onStartTrackingTouch 1");
                myPause();
            }

            @Override
            public void onStopTrackingTouch(SeekBar seekBar) {
                //放开 视频继续 3
                try {
                    Log.d("yzf", "onStopTrackingTouch 3");
                    myContinue();
                } catch (Exception e) {
                    e.printStackTrace();
                }
            }
        });
    }

    //ms 转化 函数
    private static String msToTime(int ms) {
        //这里不考虑小时了
//        int minute = ms / 60000;
//        int second = (ms % 60000) / 1000;
//        String ret = minute + ":" + second;
//        return ret;

        int sec = ms / 1000;
        int min = sec / 60;
        sec -= min*60;
        return String.format("%02d:%02d", min, sec);
    }

    //初始化进度文本
    private void initSeekBarText() {
        int progress = videoView.getCurrentPosition();
        int total = videoView.getDuration();
        seekBar.setMax(total);

        Log.d("yzf", "播放到:" + progress + " 共计:" + total);
        tv_progress.setText(msToTime(progress));
        tv_total.setText(msToTime(total));
    }

    //获取视频路径
    private String getVideoPath(int resId) {
        return "android.resource://" + this.getPackageName() + "/" + resId;
    }

    /**
     * 设计计时器让进度条动起来
     * */
    //子线程 进度条相关
    public static Handler handler = new Handler(Looper.myLooper()) {
        @Override
        public void handleMessage(@NonNull Message msg) {
            super.handleMessage(msg);
            //获取传送过来的相关信息
            Bundle bundle = msg.getData();
            int duration = bundle.getInt("duration");
            int currentPosition = bundle.getInt("currentPosition");
            //设置进度条参数
            seekBar.setMax(duration);
            seekBar.setProgress(currentPosition);
            //设置进度条下面文本
            tv_progress.setText(msToTime(currentPosition));
            tv_total.setText(msToTime(duration));
        }
    };

    //添加计时器 呼唤子线程
    private void addTimer() {
        if(timer == null) {
            timer = new Timer();
            //循环执行的任务
            TimerTask task = new TimerTask() {
                @Override
                public void run() {
                    Log.d("yzf", "run");
                    int duration = videoView.getDuration();
                    int currentPos = videoView.getCurrentPosition();
                    //创建消息对象
                    Message msg = MainActivity.handler.obtainMessage();
                    //封装视频数据
                    Bundle bundle = new Bundle();
                    bundle.putInt("duration", duration);
                    bundle.putInt("currentPosition", currentPos);
                    msg.setData(bundle);

                    //发送消息到子线程
                    MainActivity.handler.sendMessage(msg);
                }
            };
            //5ms后每500ms执行一次
            timer.schedule(task, 5, 500);
        }
    }

    //播放
    private void myPlay() { ;
        videoView.seekTo(0);
        videoView.start();
        addTimer();
    }

    //暂停
    private void myPause() {
        videoView.pause();
    }

    //继续
    private void myContinue() {
        videoView.start();
    }

    //退出
    private void myExit() {
        finish();
    }

    //进度条播放
    private void mySeek(int ms) {
        videoView.seekTo(ms);
    }
}

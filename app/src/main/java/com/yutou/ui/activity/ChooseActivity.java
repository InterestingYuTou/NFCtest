package com.yutou.ui.activity;

import android.animation.Keyframe;
import android.animation.ObjectAnimator;
import android.animation.PropertyValuesHolder;
import android.app.Activity;
import android.content.Intent;
import android.os.Bundle;
import android.os.Environment;
import android.support.v4.view.animation.FastOutSlowInInterpolator;
import android.view.View;
import android.widget.Button;
import android.widget.Toast;

//import com.mabeijianxi.smallvideorecord2.DeviceUtils;
//import com.mabeijianxi.smallvideorecord2.JianXiCamera;
//import com.mabeijianxi.smallvideorecord2.MediaRecorderActivity;
//import com.mabeijianxi.smallvideorecord2.model.AutoVBRMode;
//import com.mabeijianxi.smallvideorecord2.model.BaseMediaBitrateConfig;
//import com.mabeijianxi.smallvideorecord2.model.MediaRecorderConfig;
import com.yutou.R;
import com.yutou.SampleApplication;
import com.yutou.ui.smallvideo.SelectSmallVideoActivity;
import com.yutou.ui.smallvideo.SendSmallVideoActivity;
import com.yutou.ui.widget.MyView;

import java.io.File;

import butterknife.Bind;
import butterknife.ButterKnife;
import butterknife.OnClick;

public class ChooseActivity extends Activity {


    @Bind(R.id.btn1)
    Button btn1;
    @Bind(R.id.btn2)
    Button btn2;
    @Bind(R.id.myview)
    MyView myview;


    @Override
    protected void onCreate(Bundle savedInstanceState) {
        super.onCreate(savedInstanceState);
        setContentView(R.layout.activity_choose);
        ButterKnife.bind(this);
        Keyframe keyframe1 = Keyframe.ofFloat(0, 0); // 开始：progress 为 0
        Keyframe keyframe2 = Keyframe.ofFloat(0.5f, 100); // 进行到一半是，progres 为 100
        Keyframe keyframe3 = Keyframe.ofFloat(1, 80); // 结束时倒回到 80
        PropertyValuesHolder holder = PropertyValuesHolder.ofKeyframe("progress", keyframe1, keyframe2, keyframe3);

        ObjectAnimator animator = ObjectAnimator.ofPropertyValuesHolder(myview, holder);
        animator.setDuration(4000);
        animator.setInterpolator(new FastOutSlowInInterpolator());
        animator.start();
//        initSmallVideo();
        btn1 = (Button) findViewById(R.id.btn1);
        btn1.setOnClickListener(new View.OnClickListener() {
            @Override
            public void onClick(View v) {
                Toast.makeText(ChooseActivity.this,"点击了",Toast.LENGTH_LONG).show();
            }
        });
    }

//    @OnClick({R.id.btn1, R.id.btn2})
//    public void onViewClicked(View view) {
//        switch (view.getId()) {
//            case R.id.btn1:
//                BaseMediaBitrateConfig recordMode = new AutoVBRMode();//用于设置转码的速度
//                recordMode.setVelocity("ultrafast");
//                MediaRecorderConfig config = new MediaRecorderConfig.Buidler()
//                        .smallVideoWidth(480)
//                        .smallVideoHeight(480)
//                        .maxFrameRate(20)
//                        .captureThumbnailsTime(1)
//                        .recordTimeMax(10 * 1000)
//                        .recordTimeMin(0 * 1000)
//                        .build();
//                MediaRecorderActivity.goSmallVideoRecorder(this, SendSmallVideoActivity.class.getName(), config);
//                break;
//            case R.id.btn2:
//                Intent intent = new Intent(this, SelectSmallVideoActivity.class);
//                startActivity(intent);
//                break;
//        }
//    }
//
//    public static void initSmallVideo() {
//        // 设置拍摄视频缓存路径
//        File dcim = Environment
//                .getExternalStoragePublicDirectory(Environment.DIRECTORY_DCIM);
//        if (DeviceUtils.isZte()) {
//            if (dcim.exists()) {
//                JianXiCamera.setVideoCachePath(dcim + "/" + SampleApplication.getInstance().getPackageName() + "/");
//            } else {
//                JianXiCamera.setVideoCachePath(dcim.getPath().replace("/sdcard/",
//                        "/sdcard-ext/")
//                        + "/" + SampleApplication.getInstance().getPackageName() + "/");
//            }
//        } else {
//            JianXiCamera.setVideoCachePath(dcim + "/" + SampleApplication.getInstance().getPackageName() + "/");
//        }
//        // 初始化拍摄
//        JianXiCamera.initialize(false, null);
//    }
}

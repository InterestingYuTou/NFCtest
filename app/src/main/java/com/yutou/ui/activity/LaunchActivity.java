package com.yutou.ui.activity;

import android.content.Intent;
import android.databinding.DataBindingUtil;
import android.os.Bundle;
import android.view.View;

import com.yutou.R;
import com.yutou.databinding.ActivityLaunchBinding;
import com.yutou.ui.MainActivity;
import com.yutou.ui.base.BaseActivity;

/**
 * @Description:启动页
 * @Author: zhoujianyu
 * @Time: 2018/7/25 14:23
 */
public class LaunchActivity extends BaseActivity {
    private ActivityLaunchBinding mBinding = null;

    @Override
    protected void onCreate(Bundle savedInstanceState) {
        super.onCreate(savedInstanceState);
        mBinding = DataBindingUtil.setContentView(this, R.layout.activity_launch);
        initClick();
    }

    private void initClick() {
        mBinding.btnNfc.setOnClickListener(new View.OnClickListener() {
            @Override
            public void onClick(View view) {
                Intent intent_nfc = new Intent(LaunchActivity.this, NFCTestActivity.class);
                startActivity(intent_nfc);
            }
        });
        mBinding.btnMain.setOnClickListener(new View.OnClickListener() {
            @Override
            public void onClick(View view) {
                Intent intent_main = new Intent(LaunchActivity.this, MainActivity.class);
                startActivity(intent_main);
            }
        });
    }
}

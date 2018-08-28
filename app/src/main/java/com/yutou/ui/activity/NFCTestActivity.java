package com.yutou.ui.activity;

import android.Manifest;
import android.content.Context;
import android.databinding.DataBindingUtil;
import android.os.Bundle;
import android.support.annotation.NonNull;
import android.support.design.widget.FloatingActionButton;
import android.support.design.widget.Snackbar;
import android.support.design.widget.TabLayout;
import android.support.v4.app.Fragment;
import android.support.v7.app.AppCompatActivity;
import android.support.v7.widget.Toolbar;
import android.util.Log;
import android.view.View;
import android.widget.FrameLayout;
import android.widget.Toast;

import com.google.gson.Gson;
import com.yutou.R;
import com.yutou.data.APPURL;
import com.yutou.databinding.ActivityNfctestBinding;
import com.yutou.net.utils.RetrofitUtil;
import com.yutou.ui.adapter.TabLayoutImageAdapter;
import com.yutou.ui.base.BaseActivity;
import com.yutou.ui.bean.NFCModelBean;
import com.yutou.ui.fragment.FragmentNFCList;
import com.yutou.ui.utils.AppUtils;
import com.yutou.ui.utils.PhoneAction;

import java.io.Serializable;
import java.util.ArrayList;
import java.util.HashMap;
import java.util.List;

import io.reactivex.Observer;
import io.reactivex.android.schedulers.AndroidSchedulers;
import io.reactivex.disposables.Disposable;
import io.reactivex.schedulers.Schedulers;
import okhttp3.RequestBody;
import permissions.dispatcher.NeedsPermission;
import permissions.dispatcher.OnNeverAskAgain;
import permissions.dispatcher.OnPermissionDenied;
import permissions.dispatcher.RuntimePermissions;

/**
 * @Description:NFC检测页面
 * @Author: zhoujianyu
 * @Time: 2018/7/12 9:18
 */
@RuntimePermissions
public class NFCTestActivity extends BaseActivity implements FragmentNFCList.OnListFragmentInteractionListener {
    private ActivityNfctestBinding mBinding = null;
    private String iccid = "";
    private String mobile_model = "";
    private Context context;
    private List<NFCModelBean.DatasBean> mDataList = new ArrayList<>();
    private List<FragmentNFCList> mFragmentList = new ArrayList<>();

    @Override
    protected void onCreate(Bundle savedInstanceState) {
        super.onCreate(savedInstanceState);
        mBinding = DataBindingUtil.setContentView(this, R.layout.activity_nfctest);
        context = this;
    }

    @Override
    protected void onStart() {
        super.onStart();
        NFCTestActivityPermissionsDispatcher.getPhoneStateWithPermissionCheck(this);
    }

    private void initView() {
        iccid = PhoneAction.getICCID(NFCTestActivity.this); // sim卡iccid
        mBinding.tvIccid.setText("");
        if (iccid == null || iccid.equals("")) {
            mBinding.tvIccid.setText("获取ICCID失败");
        } else {
            mBinding.tvIccid.setText(iccid);
        }
        mobile_model = android.os.Build.MODEL.toLowerCase().trim();
        mBinding.tvVersion.setText("");
        mBinding.tvVersion.setText(mobile_model);
    }

    private void initClick() {
        mBinding.btnCopyIccid.setOnClickListener(new View.OnClickListener() {
            @Override
            public void onClick(View view) {
                if (mBinding.tvIccid.getText().toString().equals("获取ICCID失败")) {
                    Toast(context, mBinding.tvIccid.getText().toString());
                } else {
                    if (AppUtils.copy(NFCTestActivity.this, mBinding.tvIccid.getText().toString())) {
                        Toast(context, "复制成功");
                    }
                }
            }
        });
        mBinding.btnCopyVersion.setOnClickListener(new View.OnClickListener() {
            @Override
            public void onClick(View view) {
                if (AppUtils.copy(NFCTestActivity.this, mBinding.tvVersion.getText().toString())) {
                    Toast(context, "复制成功");
                }
            }
        });

    }

    @NeedsPermission(Manifest.permission.READ_PHONE_STATE)
    void getPhoneState() {
        initView();
        initClick();
        getNFCModel();
    }

    /**
     * 获取NFC全机型
     */
    private void getNFCModel() {
        Gson gson = new Gson();
        HashMap<String, String> paramsMap = new HashMap<>();
        paramsMap.put("funCode", "6317");
        String strEntity = gson.toJson(paramsMap);
        RequestBody requestBody = RequestBody.create(okhttp3.MediaType.parse("application/json;charset=UTF-8"), strEntity);
        RetrofitUtil.getInstance()
                .getApiService()
                .getNFCModelBean(requestBody)
                .subscribeOn(Schedulers.io())
                .observeOn(AndroidSchedulers.mainThread())
                .subscribe(new Observer<NFCModelBean>() {
                    @Override
                    public void onSubscribe(Disposable d) {

                    }

                    @Override
                    public void onNext(NFCModelBean nfcModelBean) {
                        if (nfcModelBean.getRetCode().equals("9000")) {
                            for (int i = 0; i < nfcModelBean.getDatas().size(); i++) {
                                mDataList.add(nfcModelBean.getDatas().get(i));
                            }
                            initFragment();
                        } else {
                            Toast(context, nfcModelBean.getRetMsg());
                        }
                    }

                    @Override
                    public void onError(Throwable e) {

                    }

                    @Override
                    public void onComplete() {

                    }
                });

    }

    private void initFragment() {
        for (int i = 0; i < mDataList.size(); i++) {
            FragmentNFCList fragment = new FragmentNFCList();
            Bundle bundle = new Bundle();
            bundle.putSerializable("data", (Serializable) mDataList.get(i).getModels());
            fragment.setArguments(bundle);
            mFragmentList.add(fragment);
        }
        //设置关联的ViewPager
        TabLayoutImageAdapter tabAdapter = new TabLayoutImageAdapter(NFCTestActivity.this, getSupportFragmentManager(), mFragmentList, mDataList);
        mBinding.mViewPage.setAdapter(tabAdapter);
        mBinding.mTabLayout.setupWithViewPager(mBinding.mViewPage);
        mBinding.mTabLayout.setTabMode(TabLayout.MODE_SCROLLABLE);
        //tablayout 添加自己定义的view 上面图片 底下文字
        for (int i = 0; i < mDataList.size(); i++) {
            TabLayout.Tab tab = mBinding.mTabLayout.getTabAt(i);
            tab.setCustomView(tabAdapter.getTabView(i));
        }
        getResult();
    }

    private void getResult() {
        if (iccid == null || iccid.equals("")) {
            mBinding.tvResult.setText("获取ICCID失败,无法检测");
            return;
        }
        Boolean isUse = false;
        int position1 = 0;
        int position2 = 0;
        for (int i = 0; i < mDataList.size(); i++) {
            for (int j = 0; j < mDataList.get(i).getModels().size(); j++) {
                if (mDataList.get(i).getModels().get(j).getBrandName().toLowerCase().trim().equals(mobile_model)) { // 本机型号是否在名单内
                    position1 = i;
                    position2 = j;
                    isUse = true;
                }
            }
        }
        mBinding.mViewPage.setOffscreenPageLimit(position1);
        mBinding.mViewPage.setCurrentItem(position1);
        mBinding.mTabLayout.getTabAt(position1).select();
        mFragmentList.get(position1).onNFCFounded(position2);
        if (isUse) {
            mBinding.tvResult.setText("通过");
        } else {
            mBinding.tvResult.setText("手机型号未添加到白名单，请联系开发人员添加");
        }

    }


    @Override
    public void onRequestPermissionsResult(int requestCode, @NonNull String[] permissions, @NonNull int[] grantResults) {
        super.onRequestPermissionsResult(requestCode, permissions, grantResults);
        NFCTestActivityPermissionsDispatcher.onRequestPermissionsResult(this, requestCode, grantResults);
    }

    @OnPermissionDenied(Manifest.permission.READ_PHONE_STATE)
    void getPhoneStateDenied() {
        finish();
    }

    @OnNeverAskAgain(Manifest.permission.READ_PHONE_STATE)
    void getPhoneStateNeverAskAgain() {
        finish();
    }

    @Override
    public void onListFragmentInteraction(NFCModelBean.DatasBean.ModelsBean item) {
        //点击item的时候
        Toast(context, item.getBrandName());
    }

}

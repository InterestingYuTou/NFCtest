package com.yutou.ui;

import android.Manifest;
import android.content.Context;
import android.content.Intent;
import android.os.Bundle;
import android.support.annotation.NonNull;
import android.support.v7.widget.RecyclerView;
import android.support.v7.widget.helper.ItemTouchHelper;
import android.util.Log;
import android.view.View;
import android.widget.Button;

import com.chad.library.adapter.base.BaseQuickAdapter;
import com.yutou.R;
import com.yutou.bean.ProductBean;
import com.yutou.bean.User;
import com.yutou.db.DBManager;
import com.yutou.net.http.base.BaseObserver;
import com.yutou.net.http.bean.BaseEntity;
import com.yutou.net.utils.RetrofitUtil;
import com.yutou.ui.activity.ChooseActivity;
import com.yutou.ui.adapter.FullyGridLayoutManager;
import com.yutou.ui.adapter.ProductAdapter;
import com.yutou.ui.base.BaseActivity;
import com.yutou.ui.callback.SimpleItemTouchCallback;


import java.util.List;
import java.util.concurrent.TimeUnit;

import in.srain.cube.views.ptr.PtrClassicFrameLayout;
import in.srain.cube.views.ptr.PtrDefaultHandler;
import in.srain.cube.views.ptr.PtrFrameLayout;
import io.reactivex.Observable;
import io.reactivex.Observer;
import io.reactivex.android.schedulers.AndroidSchedulers;
import io.reactivex.disposables.Disposable;
import io.reactivex.schedulers.Schedulers;
import permissions.dispatcher.NeedsPermission;
import permissions.dispatcher.OnNeverAskAgain;
import permissions.dispatcher.OnPermissionDenied;
import permissions.dispatcher.RuntimePermissions;


@RuntimePermissions
public class MainActivity extends BaseActivity implements BaseQuickAdapter.RequestLoadMoreListener {
    private int page = 1;
    private RecyclerView rv;
    private PtrClassicFrameLayout ptr;
    private Button btn, btn_database, btn_permission;
    private ProductAdapter productAdapter;
    private Context context;


    @Override
    protected void onCreate(Bundle savedInstanceState) {
        super.onCreate(savedInstanceState);
        setContentView(R.layout.activity_main);
        context = this;
        ptr = (PtrClassicFrameLayout) findViewById(R.id.ptr);
        rv = (RecyclerView) findViewById(R.id.recyclerview);
        btn = (Button) findViewById(R.id.btn);
        btn_database = (Button) findViewById(R.id.btn_database);
        initPtr();
        //请求实体类数据
        initData();
        //请求json数据
//        initJsonData();
        //模拟内存泄露
//        testRxJava();
//        finish();
        initClick();
    }

    @Override
    protected void onStart() {
        super.onStart();
        MainActivityPermissionsDispatcher.checkCameraWithPermissionCheck(this);
    }

    @Override
    protected void onResume() {
        super.onResume();
    }

    @Override
    protected void onDestroy() {
        super.onDestroy();
//        SampleApplication.getRefWatcher(this).watch(this);
    }

    private void initData() {
        RetrofitUtil.getInstance().getApiService()
                .getProductList(page + "")
                .compose(this.<BaseEntity<ProductBean>>setThread())
                .compose(this.<BaseEntity<ProductBean>>bindToLifecycle())
                .subscribe(new BaseObserver<ProductBean>() {
                    @Override
                    protected void onSuccees(BaseEntity<ProductBean> t) throws Exception {
                        if (page == 1) {
                            productAdapter = new ProductAdapter(context, null);
                            productAdapter.setOnLoadMoreListener(MainActivity.this);
                            productAdapter.setNewData(t.getList().getProduct_list());
                            FullyGridLayoutManager fullyGridLayoutManager = new FullyGridLayoutManager(context, 2);
                            rv.setLayoutManager(fullyGridLayoutManager);
                            rv.setAdapter(productAdapter);
                            ItemTouchHelper helper = new ItemTouchHelper(new SimpleItemTouchCallback(productAdapter, productAdapter.getData()));
                            helper.attachToRecyclerView(rv);
                            if (ptr.isShown()) {
                                ptr.refreshComplete();//下拉刷新完成关闭
                            }
                        } else {
                            if (t.getList().getProduct_list().size() <= 0) {
                                //上拉加载没有数据了
                            } else {
//                        Log.e("添加的数量", "" + s.getList().getProduct_list().size());
                                productAdapter.addData(t.getList().getProduct_list());
                                productAdapter.notifyDataSetChanged();
                            }
                        }
                    }

                    @Override
                    protected void onFailure(Throwable e, boolean isNetWorkError) throws Exception {

                    }

                });
    }


    private void initPtr() {
        ptr.setPtrHandler(new PtrDefaultHandler() {
            @Override
            public void onRefreshBegin(PtrFrameLayout frame) {
                page = 1;
                initData();
            }
        });
    }

    @Override
    public void onLoadMoreRequested() {
        page++;
        initData();
    }

    private void initClick() {
        btn.setOnClickListener(new View.OnClickListener() {
            @Override
            public void onClick(View view) {
                Intent intent = new Intent(context, ChooseActivity.class);
                startActivity(intent);
            }
        });
        btn_database.setOnClickListener(new View.OnClickListener() {
            @Override
            public void onClick(View v) {
                initDatabase();
            }
        });
    }

    /**
     * 数据库测试逻辑
     */
    private void initDatabase() {
        DBManager dbManager = DBManager.getInstance(this);
        for (int i = 0; i < 5; i++) {
            User user = new User();
            user.setId(i + "");
            user.setAge(i * 3 + "");
            user.setName("第" + i + "人");
            dbManager.insertUser(user);
        }
        List<User> userList = dbManager.queryUserList();
        for (User user : userList) {
            Log.e("TAG", "queryUserList--before-->" + user.getId() + "--" + user.getName() + "--" + user.getAge());
            if (user.getId().equals("0")) {
                dbManager.deleteUser(user);
            }
            if (user.getId().equals("3")) {
                user.setAge(10 + "");
                dbManager.updateUser(user);
            }
        }
        userList = dbManager.queryUserList();
        for (User user : userList) {
            Log.e("TAG", "queryUserList--after--->" + user.getId() + "---" + user.getName() + "--" + user.getAge());
        }
    }

    private void testRxJava() {
        Observable.interval(1, TimeUnit.SECONDS)
                .subscribeOn(Schedulers.io())
                .observeOn(AndroidSchedulers.mainThread())
                .compose(this.<Long>bindToLifecycle())
                .subscribe(new Observer<Long>() {
                    @Override
                    public void onSubscribe(Disposable d) {

                    }

                    @Override
                    public void onNext(Long aLong) {
                        Log.i("接受数据", String.valueOf(aLong));
                    }

                    @Override
                    public void onError(Throwable e) {

                    }

                    @Override
                    public void onComplete() {

                    }
                });
    }

    @NeedsPermission(Manifest.permission.CAMERA)
    void checkCamera() {}

    @Override
    public void onRequestPermissionsResult(int requestCode, @NonNull String[] permissions, @NonNull int[] grantResults) {
        super.onRequestPermissionsResult(requestCode, permissions, grantResults);
        MainActivityPermissionsDispatcher.onRequestPermissionsResult(this, requestCode, grantResults);
    }

    @OnPermissionDenied(Manifest.permission.CAMERA)
    void checkCameraDenied() {}

    @OnNeverAskAgain(Manifest.permission.CAMERA)
    void checkCameraNeverAskAgain() {}
}

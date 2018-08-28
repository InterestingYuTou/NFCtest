package com.yutou.net.utils;


import com.yutou.data.APPURL;
import com.yutou.net.ApiService;
import com.yutou.net.InterceptorUtil;
import com.yutou.net.http.config.HttpConfig;
import java.util.concurrent.TimeUnit;
import okhttp3.OkHttpClient;
import retrofit2.Retrofit;
import retrofit2.adapter.rxjava2.RxJava2CallAdapterFactory;
import retrofit2.converter.gson.GsonConverterFactory;


/**
 * 描    述：请求管理类
 * 创 建 人：ZJY
 * 创建日期：2017/4/6 16:13
 * 修订历史：
 * 修 改 人：
 */

public class RetrofitUtil {
    private static RetrofitUtil mInstance;
    private ApiService mApiService;

    private RetrofitUtil() {
        OkHttpClient mOkHttpClient = new OkHttpClient.Builder()
                .connectTimeout(HttpConfig.HTTP_TIME, TimeUnit.SECONDS)
                .readTimeout(HttpConfig.HTTP_TIME, TimeUnit.SECONDS)
                .writeTimeout(HttpConfig.HTTP_TIME, TimeUnit.SECONDS)
//                .addInterceptor(InterceptorUtil.tokenInterceptor())
                .addInterceptor(InterceptorUtil.LogInterceptor())//添加日志拦截器
                .build();
        Retrofit mRetrofit = new Retrofit.Builder()
                .baseUrl(APPURL.APP_BASE_NFC_URL)
                .addConverterFactory(GsonConverterFactory.create())//添加gson转换器
                .addCallAdapterFactory(RxJava2CallAdapterFactory.create())//添加rxjava转换器
                .client(mOkHttpClient)
                .build();
        mApiService = mRetrofit.create(ApiService.class);
    }

    public static RetrofitUtil getInstance() {
        if (mInstance == null) {
            synchronized (RetrofitUtil.class) {
                mInstance = new RetrofitUtil();
            }
        }
        return mInstance;
    }

    /**
     * 绑定接口文档
     *
     * @return
     */
    public ApiService getApiService() {
        return mApiService;
    }
}

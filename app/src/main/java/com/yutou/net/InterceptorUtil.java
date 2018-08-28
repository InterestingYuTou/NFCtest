package com.yutou.net;

import android.util.Log;

import com.google.gson.JsonObject;
import com.yutou.net.http.bean.BaseEntity;
import com.yutou.net.utils.RetrofitUtil;

import org.json.JSONObject;

import java.io.IOException;
import java.nio.charset.Charset;
import java.util.List;

import okhttp3.Interceptor;
import okhttp3.MediaType;
import okhttp3.Request;
import okhttp3.Response;
import okhttp3.ResponseBody;
import okhttp3.internal.http.HttpHeaders;
import okhttp3.logging.HttpLoggingInterceptor;
import okio.Buffer;
import okio.BufferedSource;
import retrofit2.Call;

/**
 * 描    述：拦截器工具类
 * 创 建 人：ZJY
 * 创建日期：2017/4/6 16:57
 * 修订历史：
 * 修 改 人：
 */

public class InterceptorUtil {
    public static String TAG = "----";
    private static String Token = "";
    public final static Charset UTF8 = Charset.forName("UTF-8");

    //可以添加统一发送请求的头部信息
    public static Interceptor HeaderInterceptor() {
        return new Interceptor() {
            @Override
            public Response intercept(Chain chain) throws IOException {
                Request.Builder builder = chain.request().newBuilder();
//                builder.addHeader("token", "123");
                return chain.proceed(builder.build());
            }
        };
    }

    //日志拦截器
    public static HttpLoggingInterceptor LogInterceptor() {
        return new HttpLoggingInterceptor(new HttpLoggingInterceptor.Logger() {
            @Override
            public void log(String message) {
                Log.w(TAG, "InterceptorUtil:--- " + message);
            }
        }).setLevel(HttpLoggingInterceptor.Level.BODY);//设置打印数据的级别
    }
//
//    /**
//     * token验证的拦截器1
//     *
//     * @return
//     */
//    public static void tokenInterceptor1() {
//        new Interceptor() {
//            @Override
//            public Response intercept(Chain chain) throws IOException {
//                //拿到请求体,并添加header携带上token
//                Request mRequest = chain.request().newBuilder()
//                        .addHeader("Token", Token)
//                        .build();
//                //拿到响应体
//                Response mResponse = chain.proceed(mRequest);
//
//                if (mResponse.code() == 201) {
//                    //重新获取新token
//                    //这用了一个特殊接口来获取新的Token
//                    Call<String> call = (Call<String>) RetrofitUtil.getInstance().getApiService().getProductListJSON("123456");
//                    //拿到请求体
//                    Request tokenRequest = call.request();
//                    //获取响应体
//                    Response tokenResponse = chain.proceed(tokenRequest);
//                    //我这假设新的token是在header里返回的
//                    //我们拿到新的token头
//                    List<String> listHeader = tokenResponse.headers().values("Token");
//                    if (listHeader != null) {
//                        //重新赋值到新的token
//                        Token = listHeader.get(0);
//                    }
//
//                    //这是只需要替换掉之前的token重新请求接口就行了
//                    Request newRequest = mRequest.newBuilder()
//                            .header("Token", Token)
//                            .build();
//                    return chain.proceed(newRequest);
//                }
//                return mResponse;
//            }
//        };
//    }
//
//    /**
//     * token验证的拦截器
//     *
//     * @return
//     */
//    public static Interceptor tokenInterceptor() {
//        return new Interceptor() {
//            @Override
//            public Response intercept(Chain chain) throws IOException {
//                /**
//                 * 1.拦截到返回的数据
//                 * 2.判断token是否失效
//                 * 3.失效获取新的token
//                 * 4.重新请求接口
//                 */
//
//                //拿到请求体,并添加header携带上token
//                Request mRequest = chain.request().newBuilder()
//                        .addHeader("Token", Token)
//                        .build();
//                //拿到响应体
//                Response mResponse = chain.proceed(mRequest);
//                ResponseBody responseBody = mResponse.body();
//
//                //得到缓冲源
//                BufferedSource source = responseBody.source();
//
//                //请求全部
//                source.request(Long.MAX_VALUE); // Buffer the entire body.
//                Buffer buffer = source.buffer();
//                Charset charset = UTF8;
//
//                MediaType contentType = responseBody.contentType();
//
//                if (contentType != null) {
//                    charset = contentType.charset(UTF8);
//                }
//                //读取返回数据
//                String bodyString = buffer.clone().readString(charset);
//                if (bodyString != null) {
//                    //处理返回的数据我这创建了一个BaseEntity来将数据转化为对象
//
//                    BaseEntity bean = new JsonObject(bodyString, BaseEntity.class);
////                    假设当返回的code为42444时token失效
//                    if (bean.getCode().equals("42444")) {
////                        重新获取新token
////                    这用了一个特殊接口来获取新的Token
//                        Call<String> call = RetrofitUtil.getInstance().API().loginByToken("123456", Token);
////                    拿到请求体
//                        Request tokenRequest = call.request();
////                    获取响应体
//                        Response tokenResponse = chain.proceed(tokenRequest);
////                    我这假设新的token是在header里返回的
////                    我们拿到新的token头
//                        List<String> listHeader = tokenResponse.headers().values("Token");
//                        if (listHeader != null) {
////                    重新赋值到新的token
//                            Token = listHeader.get(0);
//                        }
//                        //这是只需要替换掉之前的token重新请求接口就行了
//                        Request newRequest = mRequest.newBuilder()
//                                .header("Token", Token)
//                                .build();
//                        return chain.proceed(newRequest);
//                    }
//                }
//
//                return chain.proceed(mRequest);
//            }
//        };
//    }
}

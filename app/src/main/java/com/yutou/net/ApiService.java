package com.yutou.net;


import com.yutou.bean.ProductBean;
import com.yutou.data.APPURL;
import com.yutou.net.http.bean.BaseEntity;
import com.yutou.ui.bean.NFCModelBean;

import io.reactivex.Observable;
import okhttp3.RequestBody;
import retrofit2.http.Body;
import retrofit2.http.GET;
import retrofit2.http.POST;
import retrofit2.http.Query;

/**
 * Created by samsung on 2017/4/6.
 */

public interface ApiService {
    //获取商品列表数据(实体类)
    @GET(APPURL.PRODUCT_INDEX)
    Observable<BaseEntity<ProductBean>> getProductList(@Query("page") String page);

    //获取商品列表数据(Json)
    @GET(APPURL.PRODUCT_INDEX)
    Observable<BaseEntity<ProductBean>> getProductListJSON(@Query("page") String page);

    //获取nfc机型列表
    @POST(APPURL.NFC_MODEL)
    Observable<NFCModelBean> getNFCModelBean(@Body RequestBody requestBody);


}

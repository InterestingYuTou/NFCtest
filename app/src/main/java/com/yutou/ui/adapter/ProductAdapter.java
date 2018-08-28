package com.yutou.ui.adapter;

import android.content.Context;
import android.widget.ImageView;
import android.widget.TextView;

import com.bumptech.glide.Glide;
import com.chad.library.adapter.base.BaseQuickAdapter;
import com.chad.library.adapter.base.BaseViewHolder;
import com.yutou.R;
import com.yutou.bean.ProductBean;

import java.util.List;

/**
 * 描    述：产品适配器
 * 创 建 人：ZJY
 * 创建日期：2017/4/7 17:27
 * 修订历史：
 * 修 改 人：
 */
public class ProductAdapter extends BaseQuickAdapter<ProductBean.ProductListBean> {
    private Context mContext;


    public ProductAdapter(Context context, List<ProductBean.ProductListBean> mDatas) {
        super(R.layout.recyclerview_product_item, mDatas);
        mContext = context;
    }

    @Override
    protected void convert(BaseViewHolder baseViewHolder, ProductBean.ProductListBean productListBean) {
        TextView tvSaleNum = baseViewHolder.getView(R.id.tv_sale_num);
        TextView mPrice = baseViewHolder.getView(R.id.tvmPrice);
        TextView mTvTitle = baseViewHolder.getView(R.id.tvTitle);
        TextView mTvContent = baseViewHolder.getView(R.id.tvContent);
        ImageView mImageView = baseViewHolder.getView(R.id.ivImage);
        tvSaleNum.setText(productListBean.getSale_num());
        mPrice.setText(productListBean.getPrice());
        mTvTitle.setText(productListBean.getTitle());
        mTvContent.setText(productListBean.getShort_title());
        Glide.with(mContext).load(productListBean.getImg()).into(mImageView);
    }


}

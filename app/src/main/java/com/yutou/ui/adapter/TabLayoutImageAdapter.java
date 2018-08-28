package com.yutou.ui.adapter;

import android.app.Activity;
import android.support.v4.app.Fragment;
import android.support.v4.app.FragmentManager;
import android.support.v4.app.FragmentPagerAdapter;
import android.support.v4.app.FragmentStatePagerAdapter;
import android.view.View;
import android.widget.ImageView;
import android.widget.TextView;

import com.bumptech.glide.Glide;
import com.yutou.R;
import com.yutou.ui.bean.NFCModelBean;
import com.yutou.ui.fragment.FragmentNFCList;

import java.util.List;

/**
 * @Description:
 * @Author: zhoujianyu
 * @Time: 2018/7/12 16:09
 */

public class TabLayoutImageAdapter extends FragmentPagerAdapter {
    private Activity context;
    private List<FragmentNFCList> fragmentList;
    private List<NFCModelBean.DatasBean> datasBeanList;

    public TabLayoutImageAdapter(Activity context, FragmentManager fm, List<FragmentNFCList> fragmentList, List<NFCModelBean.DatasBean> datasBeanList) {
        super(fm);
        this.context = context;
        this.fragmentList = fragmentList;
        this.datasBeanList = datasBeanList;
    }

    @Override
    public Fragment getItem(int position) {
        return fragmentList.get(position);
    }

    @Override
    public int getCount() {
        return datasBeanList.size();
    }

    @Override
    public CharSequence getPageTitle(int position) {
        return datasBeanList.get(position).getBrand();
    }

    public View getTabView(int position) {
        View view = context.getLayoutInflater().inflate(R.layout.table_icon_layout, null);
        TextView textView = (TextView) view.findViewById(R.id.title_tv);
        ImageView img = (ImageView) view.findViewById(R.id.img);
        Glide.with(context).load(datasBeanList.get(position).getImageUrl()).into(img);
        textView.setText(datasBeanList.get(position).getBrand());
        return view;
    }

}

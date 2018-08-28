package com.yutou.ui.fragment;

import android.content.Context;
import android.support.v7.widget.RecyclerView;
import android.view.LayoutInflater;
import android.view.View;
import android.view.ViewGroup;
import android.widget.TextView;

import com.yutou.R;
import com.yutou.ui.bean.NFCModelBean;
import com.yutou.ui.fragment.FragmentNFCList.OnListFragmentInteractionListener;

import java.util.List;

/**
 * @Description:机型列表适配器
 * @Author: zhoujianyu
 * @Time: 2018/7/12 17:03
 */
public class NFCItemRecyclerViewAdapter extends RecyclerView.Adapter<NFCItemRecyclerViewAdapter.ViewHolder> {

    private final List<NFCModelBean.DatasBean.ModelsBean> mValues;
    private final OnListFragmentInteractionListener mListener;
    private Context context;

    public NFCItemRecyclerViewAdapter(Context context, List<NFCModelBean.DatasBean.ModelsBean> items, OnListFragmentInteractionListener listener) {
        this.context = context;
        this.mValues = items;
        this.mListener = listener;
    }

    @Override
    public ViewHolder onCreateViewHolder(ViewGroup parent, int viewType) {
        View view = LayoutInflater.from(parent.getContext())
                .inflate(R.layout.fragment_item_nfc, parent, false);
        return new ViewHolder(view);
    }

    @Override
    public void onBindViewHolder(final ViewHolder holder, int position) {
        holder.mItem = mValues.get(position);
        holder.mIdView.setText(mValues.get(position).getDeviceName());
        holder.mContentView.setText(mValues.get(position).getBrandName().toLowerCase().trim());
        if (mValues.get(position).isCheck() == true) {
            holder.mView.setBackgroundColor(context.getResources().getColor(R.color.color_status));
        } else {
            holder.mView.setBackgroundColor(context.getResources().getColor(R.color.white));
        }

        holder.mView.setOnClickListener(new View.OnClickListener() {
            @Override
            public void onClick(View v) {
                if (null != mListener) {
                    // Notify the active callbacks interface (the activity, if the
                    // fragment is attached to one) that an item has been selected.
                    mListener.onListFragmentInteraction(holder.mItem);
                }
            }
        });
    }

    @Override
    public int getItemCount() {
        return mValues.size();
    }

    public class ViewHolder extends RecyclerView.ViewHolder {
        public final View mView;
        public final TextView mIdView;
        public final TextView mContentView;
        public NFCModelBean.DatasBean.ModelsBean mItem;

        public ViewHolder(View view) {
            super(view);
            mView = view;
            mIdView = (TextView) view.findViewById(R.id.id);
            mContentView = (TextView) view.findViewById(R.id.content);
        }

        @Override
        public String toString() {
            return super.toString() + " '" + mContentView.getText() + "'";
        }
    }
}

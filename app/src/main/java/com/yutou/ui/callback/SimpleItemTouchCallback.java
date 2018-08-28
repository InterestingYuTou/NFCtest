package com.yutou.ui.callback;

import android.support.v7.widget.RecyclerView;
import android.support.v7.widget.helper.ItemTouchHelper;

import com.yutou.bean.ProductBean;
import com.yutou.ui.adapter.ProductAdapter;

import java.util.Collections;
import java.util.List;

/**
 * 描    述：Recyclevie拖拽 侧滑删除
 * 创 建 人：ZJY
 * 创建日期：2017/6/26 14:50
 * 修订历史：
 * 修 改 人：
 */

public class SimpleItemTouchCallback extends ItemTouchHelper.Callback {
    private ProductAdapter mAdapter;
    private List<ProductBean.ProductListBean> mData;

    public SimpleItemTouchCallback(ProductAdapter adapter, List<ProductBean.ProductListBean> data) {
        mAdapter = adapter;
        mData = data;
    }

    @Override
    public int getMovementFlags(RecyclerView recyclerView, RecyclerView.ViewHolder viewHolder) {
        int dragFlag = ItemTouchHelper.UP | ItemTouchHelper.DOWN;//上下拖拽
        int swipeFlag = ItemTouchHelper.START | ItemTouchHelper.END;//左右滑动
        return makeMovementFlags(dragFlag, swipeFlag);
    }

    @Override
    public boolean onMove(RecyclerView recyclerView, RecyclerView.ViewHolder viewHolder, RecyclerView.ViewHolder target) {
        int from = viewHolder.getAdapterPosition();
        int to = target.getAdapterPosition();
        Collections.swap(mData,from,to);
        mAdapter.notifyItemMoved(from,to);
        return true;
    }

    @Override
    public void onSwiped(RecyclerView.ViewHolder viewHolder, int direction) {
        int pos = viewHolder.getAdapterPosition();
        mData.remove(pos);
        mAdapter.notifyItemRemoved(pos);
    }

    @Override
    public void onSelectedChanged(RecyclerView.ViewHolder viewHolder, int actionState) {
        super.onSelectedChanged(viewHolder, actionState);
        if (actionState != ItemTouchHelper.ACTION_STATE_IDLE) {
            viewHolder.itemView.setBackgroundColor(0xffbcbcbc);
        }
    }

    @Override
    public void clearView(RecyclerView recyclerView, RecyclerView.ViewHolder viewHolder) {
        super.clearView(recyclerView, viewHolder);
        viewHolder.itemView.setBackgroundColor(0xffeeeeee);
    }
}

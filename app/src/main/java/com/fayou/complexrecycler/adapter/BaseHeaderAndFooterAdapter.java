package com.fayou.complexrecycler.adapter;

import android.support.v7.widget.RecyclerView;
import android.support.v7.widget.StaggeredGridLayoutManager;
import android.view.View;
import android.view.ViewGroup;

/**
 * Created by Administrator on 2016/11/3.
 */

public abstract class BaseHeaderAndFooterAdapter<VH extends RecyclerView.ViewHolder> extends RecyclerView.Adapter<VH> {
    protected static final int ITEM_TYPE_HEADER = 0;
    protected static final int ITEM_TYPE_CONTENT = 1;
    protected static final int ITEM_TYPE_BOTTOM = 2;

    private int mHeaderCount;
    private int mFooterCount;

    protected View mHeaderView;
    protected View mFooterView;

    @Override
    public int getItemViewType(int position) {
        if (isHeaderView(position)) {
            //头部View
            return ITEM_TYPE_HEADER;
        } else if (isFooterView(position)) {
            //底部View
            return ITEM_TYPE_BOTTOM;
        } else {
            //内容View
            return ITEM_TYPE_CONTENT;
        }
    }

    @Override
    public int getItemCount() {
        return getHeaderViewsCount() + getContentItemCount() + getFooterViewsCount();
    }

    public abstract int getContentItemCount();

    public void addHeaderView(View header) {
        mHeaderView = header;
        if (getHeaderViewsCount() == 0) {
            //顺序不能改变
            notifyItemInserted(0);
            mHeaderCount++;
        }
    }

    public void addFooterView(View footer) {
        mFooterView = footer;
        if (getFooterViewsCount() == 0) {
            notifyItemInserted(getItemCount());
            mFooterCount++;
        }
    }

    public void removeHeaderView() {
        if (mHeaderCount <= 0) return;
        notifyItemRemoved(0);
        mHeaderCount--;
        mHeaderView = null;
    }

    public void removeFooterView() {
        if (mFooterCount <= 0) return;
        notifyItemRemoved(getItemCount() - 1);
        mFooterCount--;
        mFooterView = null;
    }

    public int getHeaderViewsCount() {
        return mHeaderCount;
    }


    public int getFooterViewsCount() {
        return mFooterCount;
    }
//
//    @Override
//    public void onAttachedToRecyclerView(RecyclerView recyclerView) {
//        super.onAttachedToRecyclerView(recyclerView);
//        RecyclerView.LayoutManager mLayoutManager = recyclerView.getLayoutManager();
//        if (mLayoutManager instanceof GridLayoutManager) {
//            final GridLayoutManager gridLayoutManager = (GridLayoutManager) mLayoutManager;
//            gridLayoutManager.setSpanSizeLookup(new GridLayoutManager.SpanSizeLookup() {
//                //position上的item占据的单元格个数
//                @Override
//                public int getSpanSize(int position) {
//                    if (getItemViewType(position) == ITEM_TYPE_CONTENT) {
//                        return 1;
//                    } else {
//                        return gridLayoutManager.getSpanCount();
//                    }
//                }
//            });
//        }
//    }
//
    @Override
    public void onViewAttachedToWindow(VH holder) {
        super.onViewAttachedToWindow(holder);
        ViewGroup.LayoutParams lp = holder.itemView.getLayoutParams();
        if (lp != null
                && lp instanceof StaggeredGridLayoutManager.LayoutParams) {
            StaggeredGridLayoutManager.LayoutParams p = (StaggeredGridLayoutManager.LayoutParams) lp;
            if (getItemViewType(holder.getLayoutPosition()) != ITEM_TYPE_CONTENT) {
                p.setFullSpan(true);
            }
        }
    }

    public boolean isHeaderView(int position) {
        return mHeaderCount != 0 && position < mHeaderCount;
    }

    //判断当前item是否是FooterView
    public boolean isFooterView(int position) {
        return mFooterCount != 0 && position >= (mHeaderCount + getContentItemCount());
    }
}

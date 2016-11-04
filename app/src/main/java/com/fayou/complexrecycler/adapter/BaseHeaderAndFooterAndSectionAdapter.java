package com.fayou.complexrecycler.adapter;

import android.support.v7.widget.RecyclerView;
import android.util.Log;
import android.view.View;
import android.view.ViewGroup;

import java.util.HashMap;

/**
 * Created by Administrator on 2016/11/3.
 */

public abstract class BaseHeaderAndFooterAndSectionAdapter<VH extends RecyclerView.ViewHolder> extends RecyclerView.Adapter<VH> {
    private String TAG = "BaseHeaderAndFooterAndSectionAdapter";
    private static final int TYPE_SECTION_HEADER = -1;
    private static final int TYPE_SECTION_FOOTER = -2;
    private static final int TYEP_ITEM = -3;


    protected static final int RECYCLER_TYPE_HEADER = 0;
    protected static final int RECYCLER_TYPE_FOOTER = 1;

    private int mRecyclerHeaderCount;
    private int mRecyclerFooterCount;

    protected View mRecyclerHeaderView;
    protected View mRecyclerFooterView;

    private HashMap<Integer, Integer> sectionForPosition;
    private HashMap<Integer, Integer> positionWithInSection;//在组中的位置数

    private HashMap<Integer, Boolean> isSectionHeader;
    private HashMap<Integer, Boolean> isSectionFooter;

    public BaseHeaderAndFooterAndSectionAdapter() {
        super();
        registerAdapterDataObserver(new BaseHeaderAndFooterAndSectionAdapter.SectionDataObserver());
    }

    @Override
    public void onAttachedToRecyclerView(RecyclerView recyclerView) {
        super.onAttachedToRecyclerView(recyclerView);
        init();

    }

    @Override
    public int getItemViewType(int position) {
        if (sectionForPosition == null) {
            init();
        }
        if (isRecyclerHeaderView(position)) {
            //Recycler头部View
            return RECYCLER_TYPE_HEADER;
        } else if (isRecyclerFooterView(position)) {
            //Recycler底部View
            return RECYCLER_TYPE_FOOTER;
        } else {
            //内容View
            if (isSectionHeader(position)) {
                return TYPE_SECTION_HEADER;
            } else if (isSectionFooter(position)) {
                return TYPE_SECTION_FOOTER;
            }
            return TYEP_ITEM;
        }


    }

    @Override
    public VH onCreateViewHolder(ViewGroup parent, int viewType) {
        VH mViewHolder;
        if (viewType == RECYCLER_TYPE_HEADER) {
            mViewHolder = onCreateRecyclerHeaderViewHolder(parent, viewType);
        } else if (viewType == RECYCLER_TYPE_FOOTER) {
            mViewHolder = onCreateRecyclerFooterViewHolder(parent, viewType);
        } else {
            if (viewType == TYPE_SECTION_HEADER) {
                mViewHolder = onCreateSectionHeaderViewHolder(parent, viewType);
            } else if (viewType == TYPE_SECTION_FOOTER) {
                mViewHolder = onCreateSectionFooterViewHolder(parent, viewType);
            } else {
                mViewHolder = onCreateItemViewHolder(parent, viewType);
            }
        }

        return mViewHolder;
    }

    @Override
    public void onBindViewHolder(VH holder, int position) {
        if (isRecyclerHeaderView(position)) {
            onBindRecyclerHeaderViewHodler(holder, position);
        } else if (isRecyclerFooterView(position)) {
            onBindRecyclerFooterViewHolder(holder, position);
        } else {
            int section = sectionForPosition.get(position);
            int index = positionWithInSection.get(position);
            if (isSectionHeader(position)) {
                onBindSectionHeaderViewHodler(holder, section);
            } else if (isSectionFooter(position)) {
                onBindSectionFooterViewHolder(holder, section);
            } else {
                onBindItemViewHolder(holder, section, index);
            }
        }
    }

    @Override
    public int getItemCount() {
        return getRecyclerHeaderViewsCount() + getContentItemCount() + getRecyclerFooterViewsCount();
    }

    private void init() {
        initAllArrays();
        setupItemViewType();
    }

    private void reset() {
        resetAllArrays();
        setupItemViewType();
    }

    //设置item的类型
    private void setupItemViewType() {
        int sections = getSectionCount();
        int position = 0;
        if (mRecyclerHeaderCount > 0) {
            position++;
        }
        for (int i = 0; i < sections; i++) {
            setPrecomputedItem(position, true, false, i, 0);
            position++;

            for (int j = 0; j < getItemCountForSection(i); j++) {
                setPrecomputedItem(position, false, false, i, j);
                position++;
            }

            if (hasFooterInSection(i)) {
                setPrecomputedItem(position, false, true, i, 0);
                position++;
            }
        }
    }

    //为整个count设置是否是头节点，尾节点，节点
    private void setPrecomputedItem(int position, boolean isSecHeader, boolean isSecFooter, int section, int positionInSection) {
        isSectionHeader.put(position, isSecHeader);
        isSectionFooter.put(position, isSecFooter);
        sectionForPosition.put(position, section);
        positionWithInSection.put(position, positionInSection);
    }

    private void initAllArrays() {
        sectionForPosition = new HashMap<>();
        positionWithInSection = new HashMap<>();
        isSectionHeader = new HashMap<>();
        isSectionFooter = new HashMap<>();
    }

    private void resetAllArrays() {
        sectionForPosition.clear();
        positionWithInSection.clear();
        isSectionHeader.clear();
        isSectionFooter.clear();
    }

    /**
     * 判断该position是否是Header
     */
    public boolean isSectionHeader(int position) {
        if (isSectionHeader == null) {
            init();
        }

        return isSectionHeader.get(position);
    }

    /**
     * 判断该position是否是Footer
     */
    public boolean isSectionFooter(int position) {
        if (isSectionFooter == null) {
            init();
        }
        return isSectionFooter.get(position);
    }


    public void addRecyclerHeaderView(View header) {
        mRecyclerHeaderView = header;
        if (getRecyclerHeaderViewsCount() == 0) {
            //顺序不能改变
//            notifyItemInserted(0);
            mRecyclerHeaderCount++;
            notifyDataSetChanged();
        }
    }

    public void addRecyclerFooterView(View footer) {
        mRecyclerFooterView = footer;
        if (getRecyclerFooterViewsCount() == 0) {
            mRecyclerFooterCount++;
            notifyDataSetChanged();
        }
    }

    public void removeRecyclerHeaderView() {
        if (mRecyclerHeaderCount <= 0) return;
        mRecyclerHeaderCount--;
        mRecyclerHeaderView = null;
        notifyDataSetChanged();
    }

    public void removeRecyclerFooterView() {
        if (mRecyclerFooterCount <= 0) return;
        mRecyclerFooterCount--;
        mRecyclerFooterView = null;
        notifyDataSetChanged();
    }

    public boolean isRecyclerHeaderView(int position) {
        return mRecyclerHeaderCount != 0 && position < mRecyclerHeaderCount;
    }

    //判断当前item是否是FooterView
    public boolean isRecyclerFooterView(int position) {
        return mRecyclerFooterCount != 0 && position >= (mRecyclerHeaderCount + getContentItemCount());
    }

    public int getRecyclerHeaderViewsCount() {
        return mRecyclerHeaderCount;
    }


    public int getRecyclerFooterViewsCount() {
        return mRecyclerFooterCount;
    }

    /**
     * 获取组数
     */
    protected abstract int getSectionCount();

    /**
     * 获取该组的item数
     */
    protected abstract int getItemCountForSection(int section);

    /**
     * 判断该组中是否有footer视图
     */
    protected abstract boolean hasFooterInSection(int section);

    /**
     * 创建recycler header视图
     */
    protected abstract VH onCreateRecyclerHeaderViewHolder(ViewGroup parent, int viewType);

    /**
     * 创建recycler footer视图
     */
    protected abstract VH onCreateRecyclerFooterViewHolder(ViewGroup parent, int viewType);

    /**
     * 创建section header视图
     */
    protected abstract VH onCreateSectionHeaderViewHolder(ViewGroup parent, int viewType);

    /**
     * 创建section item视图
     */
    protected abstract VH onCreateItemViewHolder(ViewGroup parent, int viewType);

    /**
     * 创建section Footer视图
     */
    protected abstract VH onCreateSectionFooterViewHolder(ViewGroup parent, int viewType);


    /**
     * 绑定section header视图
     */
    protected abstract void onBindSectionHeaderViewHodler(VH holder, int section);

    /**
     * 绑定section Footer视图
     */
    protected abstract void onBindSectionFooterViewHolder(VH holder, int section);

    /**
     * 绑定section item视图
     */
    protected abstract void onBindItemViewHolder(VH holder, int section, int position);


    /**
     * 绑定recycler header视图
     */
    protected abstract void onBindRecyclerHeaderViewHodler(VH holder, int section);

    /**
     * 绑定recycler Footer视图
     */
    protected abstract void onBindRecyclerFooterViewHolder(VH holder, int section);


    public abstract int getContentItemCount();

    private class SectionDataObserver extends RecyclerView.AdapterDataObserver {
        @Override
        public void onChanged() {
            super.onChanged();
            Log.e(TAG, "onChanged");
            reset();
        }
    }
}

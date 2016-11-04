package com.fayou.complexrecycler.adapter;

import android.support.v7.widget.RecyclerView;
import android.view.ViewGroup;

import java.util.HashMap;

/**
 * Created by Administrator on 2016/11/3.
 */

public abstract class BaseSectionedRecyclerViewAdapter<VH extends RecyclerView.ViewHolder> extends RecyclerView.Adapter<VH> {


    private static final int TYPE_SECTION_HEADER = -1;
    private static final int TYPE_SECTION_FOOTER = -2;
    private static final int TYEP_ITEM = -3;

    private HashMap<Integer, Integer> sectionForPosition;
    private HashMap<Integer, Integer> positionWithInSection;//在组中的位置数

    private HashMap<Integer, Boolean> isSectionHeader;
    private HashMap<Integer, Boolean> isSectionFooter;

    private int itemCount = 0;//总数量

    public BaseSectionedRecyclerViewAdapter() {
        super();
        registerAdapterDataObserver(new SectionDataObserver());
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
        if (isSectionHeader(position)) {
            return TYPE_SECTION_HEADER;
        } else if (isSectionFooter(position)) {
            return TYPE_SECTION_FOOTER;
        }
        return TYEP_ITEM;
    }

    @Override
    public VH onCreateViewHolder(ViewGroup parent, int viewType) {
        VH mViewHolder;
        if (viewType == TYPE_SECTION_HEADER) {
            mViewHolder = onCreateSectionHeaderViewHolder(parent, viewType);
        } else if (viewType == TYPE_SECTION_FOOTER) {
            mViewHolder = onCreateSectionFooterViewHolder(parent, viewType);
        } else {
            mViewHolder = onCreateItemViewHolder(parent, viewType);
        }
        return mViewHolder;
    }

    @Override
    public void onBindViewHolder(VH holder, int position) {
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

    @Override
    public int getItemCount() {
        return itemCount;
    }

    private void init() {
        itemCount = countItems();
        initAllArrays();
        setupItemViewType();
    }
    private void reset(){
        itemCount = countItems();
        resetAllArrays();
        setupItemViewType();
    }
    //获取count的数量
    private int countItems() {
        int count = 0;
        int sections = getSectionCount();

        for (int i = 0; i < sections; i++) {
            count += 1 + getItemCountForSection(i) + (hasFooterInSection(i) ? 1 : 0);
        }

        return count;
    }

    //设置item的类型
    private void setupItemViewType() {
        int sections = getSectionCount();
        int index = 0;

        for (int i = 0; i < sections; i++) {
            setPrecomputedItem(index, true, false, i, 0);
            index++;

            for (int j = 0; j < getItemCountForSection(i); j++) {
                setPrecomputedItem(index, false, false, i, j);
                index++;
            }

            if (hasFooterInSection(i)) {
                setPrecomputedItem(index, false, true, i, 0);
                index++;
            }
        }
    }

    //为整个count设置是否是头节点，尾节点，节点
    private void setPrecomputedItem(int index, boolean isHeader, boolean isFooter, int section, int position) {
        isSectionHeader.put(index, isHeader);
        isSectionFooter.put(index, isFooter);
        sectionForPosition.put(index, section);
        positionWithInSection.put(index, position);
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
     * 创建header视图
     */
    protected abstract VH onCreateSectionHeaderViewHolder(ViewGroup parent, int viewType);

    /**
     * 用类VH创建item视图
     */
    protected abstract VH onCreateItemViewHolder(ViewGroup parent, int viewType);

    /**
     * 创建Footer视图
     */
    protected abstract VH onCreateSectionFooterViewHolder(ViewGroup parent, int viewType);


    /**
     * 绑定header视图
     */
    protected abstract void onBindSectionHeaderViewHodler(VH holder, int section);

    /**
     * 绑定Footer视图
     */
    protected abstract void onBindSectionFooterViewHolder(VH holder, int section);

    /**
     * 绑定item视图
     */
    protected abstract void onBindItemViewHolder(VH holder, int section, int position);

    private class SectionDataObserver extends RecyclerView.AdapterDataObserver {
        @Override
        public void onChanged() {
            super.onChanged();
            reset();
        }
    }
}

package com.fayou.complexrecycler.adapter;

import android.content.Context;
import android.support.v7.widget.RecyclerView;
import android.util.SparseArray;
import android.view.LayoutInflater;
import android.view.View;
import android.view.ViewGroup;
import android.widget.TextView;

import com.fayou.complexrecycler.R;

import java.util.List;

/**
 * Created by Administrator on 2016/11/3.
 */

public class HeaderAndFooterAndSectionAdapter extends BaseHeaderAndFooterAndSectionAdapter<RecyclerView.ViewHolder> {
    private String TAG = "HeaderAndFooterAndSectionAdapter";
    private SparseArray<List<String>> mSparseLive;
    private LayoutInflater mLayoutInflater;

    public HeaderAndFooterAndSectionAdapter(SparseArray<List<String>> mSparseLive, Context context) {
        this.mSparseLive = mSparseLive;
        mLayoutInflater = LayoutInflater.from(context);
    }


    @Override
    protected int getSectionCount() {
        return mSparseLive.size();
    }

    @Override
    protected int getItemCountForSection(int section) {
        return mSparseLive.get(section).size();
    }

    @Override
    protected boolean hasFooterInSection(int section) {
        return section == 1 ? true : false;
    }

    @Override
    protected RecyclerView.ViewHolder onCreateRecyclerHeaderViewHolder(ViewGroup parent, int viewType) {
        return new RecyclerHeaderViewHolder(mLayoutInflater.inflate(R.layout.rv_header, parent, false));
    }

    @Override
    protected RecyclerView.ViewHolder onCreateRecyclerFooterViewHolder(ViewGroup parent, int viewType) {
        return new RecyclerFooterViewHolder(mLayoutInflater.inflate(R.layout.rv_footer, parent, false));
    }

    @Override
    protected RecyclerView.ViewHolder onCreateSectionHeaderViewHolder(ViewGroup parent, int viewType) {
        View view = mLayoutInflater.inflate(R.layout.view_count_header, parent, false);
        return new HeaderAndFooterAndSectionAdapter.SectionHeaderViewHolder(view);
    }

    @Override
    protected RecyclerView.ViewHolder onCreateItemViewHolder(ViewGroup parent, int viewType) {

        View view = mLayoutInflater.inflate(R.layout.view_count_item, parent, false);

        return new HeaderAndFooterAndSectionAdapter.CountItemViewHolder(view);
    }

    @Override
    protected RecyclerView.ViewHolder onCreateSectionFooterViewHolder(ViewGroup parent, int viewType) {
        View view = mLayoutInflater.inflate(R.layout.view_count_footer, parent, false);
        return new HeaderAndFooterAndSectionAdapter.SectionFooterViewHolder(view);
    }

    @Override
    protected void onBindSectionHeaderViewHodler(RecyclerView.ViewHolder holder, int section) {
        ((HeaderAndFooterAndSectionAdapter.SectionHeaderViewHolder) holder).title.setText("Section " + (section + 1));
    }

    @Override
    protected void onBindSectionFooterViewHolder(RecyclerView.ViewHolder holder, int section) {
        ((HeaderAndFooterAndSectionAdapter.SectionFooterViewHolder) holder).title.setText("Footer " + (section + 1));
    }

    @Override
    protected void onBindItemViewHolder(RecyclerView.ViewHolder holder, int section, int position) {
        ((HeaderAndFooterAndSectionAdapter.CountItemViewHolder) holder).title.setText(mSparseLive.get(section).get(position));
    }

    @Override
    protected void onBindRecyclerHeaderViewHodler(RecyclerView.ViewHolder holder, int section) {

    }

    @Override
    protected void onBindRecyclerFooterViewHolder(RecyclerView.ViewHolder holder, int section) {

    }

    @Override
    public int getContentItemCount() {
        int count = 0;
        int sections = getSectionCount();

        for (int i = 0; i < sections; i++) {
            count += 1 + getItemCountForSection(i) + (hasFooterInSection(i) ? 1 : 0);
        }

        return count;
    }


    public static class CountItemViewHolder extends RecyclerView.ViewHolder {
        private TextView title;

        public CountItemViewHolder(View itemView) {
            super(itemView);
            title = (TextView) itemView.findViewById(R.id.title);
        }
    }

    public static class SectionHeaderViewHolder extends RecyclerView.ViewHolder {
        TextView title;

        public SectionHeaderViewHolder(View itemView) {
            super(itemView);
            title = (TextView) itemView.findViewById(R.id.title);

        }
    }

    public static class SectionFooterViewHolder extends RecyclerView.ViewHolder {
        TextView title;

        public SectionFooterViewHolder(View itemView) {
            super(itemView);
            title = (TextView) itemView.findViewById(R.id.title);
        }
    }


    //头部 ViewHolder
    public static class RecyclerHeaderViewHolder extends RecyclerView.ViewHolder {

        public RecyclerHeaderViewHolder(View itemView) {
            super(itemView);
        }
    }

    //底部 ViewHolder
    public static class RecyclerFooterViewHolder extends RecyclerView.ViewHolder {

        public RecyclerFooterViewHolder(View itemView) {
            super(itemView);
        }
    }
}

package com.fayou.complexrecycler.adapter;

import android.content.Context;
import android.support.v7.widget.RecyclerView;
import android.util.Log;
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

public class SectionedRecyclerViewAdapter extends BaseSectionedRecyclerViewAdapter<RecyclerView.ViewHolder> {
    private String TAG = "SectionedRecyclerViewAdapter";
    private SparseArray<List<String>> mSparseLive;
    private LayoutInflater mLayoutInflater;

    public SectionedRecyclerViewAdapter(SparseArray<List<String>> mSparseLive, Context context) {
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
    protected RecyclerView.ViewHolder onCreateSectionHeaderViewHolder(ViewGroup parent, int viewType) {
        View view = mLayoutInflater.inflate(R.layout.view_count_header, parent, false);
        return new SectionHeaderViewHolder(view);
    }

    @Override
    protected RecyclerView.ViewHolder onCreateItemViewHolder(ViewGroup parent, int viewType) {

        View view = mLayoutInflater.inflate(R.layout.view_count_item, parent, false);

        return new CountItemViewHolder(view);
    }

    @Override
    protected RecyclerView.ViewHolder onCreateSectionFooterViewHolder(ViewGroup parent, int viewType) {
        View view = mLayoutInflater.inflate(R.layout.view_count_footer, parent, false);
        return new SectionFooterViewHolder(view);
    }

    @Override
    protected void onBindSectionHeaderViewHodler(RecyclerView.ViewHolder holder, int section) {
        ((SectionHeaderViewHolder)holder).title.setText("Section " + (section + 1));
    }

    @Override
    protected void onBindSectionFooterViewHolder(RecyclerView.ViewHolder holder, int section) {
        ((SectionFooterViewHolder)holder).title.setText("Footer " + (section + 1));
    }

    @Override
    protected void onBindItemViewHolder(RecyclerView.ViewHolder holder, int section, int position) {
        ((CountItemViewHolder)holder).title.setText(mSparseLive.get(section).get(position));
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
}

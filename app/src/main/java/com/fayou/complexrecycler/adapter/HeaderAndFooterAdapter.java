package com.fayou.complexrecycler.adapter;

import android.content.Context;
import android.support.v7.widget.RecyclerView;
import android.view.LayoutInflater;
import android.view.View;
import android.view.ViewGroup;
import android.widget.TextView;

import com.fayou.complexrecycler.R;

/**
 * Created by Administrator on 2016/11/3.
 */

public class HeaderAndFooterAdapter extends BaseHeaderAndFooterAdapter<RecyclerView.ViewHolder> {
    private String TAG = "HeaderAndFooterAdapter";

    //模拟数据
    private String[] texts;
    private LayoutInflater mLayoutInflater;

    public HeaderAndFooterAdapter(Context mContext, String[] texts) {
        this.texts = texts;
        mLayoutInflater = LayoutInflater.from(mContext);
    }

    @Override
    public int getItemViewType(int position) {
        return super.getItemViewType(position);
    }

    @Override
    public RecyclerView.ViewHolder onCreateViewHolder(ViewGroup parent, int viewType) {
        if (viewType == ITEM_TYPE_HEADER) {
            if (mHeaderView == null) {
                return new HeaderViewHolder(mLayoutInflater.inflate(R.layout.rv_header, parent, false));
            } else {
                return new HeaderViewHolder(mHeaderView);
            }

        } else if (viewType == ITEM_TYPE_CONTENT) {
            return new ContentViewHolder(mLayoutInflater.inflate(R.layout.rv_item, parent, false));
        } else if (viewType == ITEM_TYPE_BOTTOM) {
            if (mFooterView == null) {
                return new BottomViewHolder(mLayoutInflater.inflate(R.layout.rv_footer, parent, false));
            } else {
                return new BottomViewHolder(mFooterView);
            }
        }
        return null;
    }

    @Override
    public void onBindViewHolder(RecyclerView.ViewHolder holder, int position) {
        if (holder instanceof HeaderViewHolder) {

        } else if (holder instanceof ContentViewHolder) {
            ((ContentViewHolder) holder).textView.setText(texts[position - getHeaderViewsCount()]);

        } else if (holder instanceof BottomViewHolder) {

        }
    }

    @Override
    public int getItemCount() {
        return super.getItemCount();
    }

    @Override
    public int getContentItemCount() {
        return texts == null ? 0 : texts.length;
    }


    //内容 ViewHolder
    public static class ContentViewHolder extends RecyclerView.ViewHolder {
        private TextView textView;

        public ContentViewHolder(View itemView) {
            super(itemView);
            textView = (TextView) itemView.findViewById(R.id.tv_item_text);
        }
    }

    //头部 ViewHolder
    public static class HeaderViewHolder extends RecyclerView.ViewHolder {

        public HeaderViewHolder(View itemView) {
            super(itemView);
        }
    }

    //底部 ViewHolder
    public static class BottomViewHolder extends RecyclerView.ViewHolder {

        public BottomViewHolder(View itemView) {
            super(itemView);
        }
    }

}

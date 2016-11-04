package com.fayou.complexrecycler.weight;

import android.content.Context;
import android.util.AttributeSet;
import android.view.LayoutInflater;
import android.view.View;
import android.widget.RelativeLayout;

import com.fayou.complexrecycler.R;

/**
 * Created by Administrator on 2016/11/3.
 */

public class SampleHeader extends RelativeLayout {
    public SampleHeader(Context context) {
        this(context, null);
    }

    public SampleHeader(Context context, AttributeSet attrs) {
        this(context, attrs, 0);
    }

    public SampleHeader(Context context, AttributeSet attrs, int defStyleAttr) {
        super(context, attrs, defStyleAttr);
        init(context);
    }

    public void init(Context context) {
        inflate(context, R.layout.rv_header, this);
    }
}

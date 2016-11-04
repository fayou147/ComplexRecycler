package com.fayou.complexrecycler.weight;

import android.content.Context;
import android.util.AttributeSet;
import android.widget.RelativeLayout;

import com.fayou.complexrecycler.R;

/**
 * Created by Administrator on 2016/11/3.
 */

public class SampleFooter extends RelativeLayout {
    public SampleFooter(Context context) {
        this(context, null);
    }

    public SampleFooter(Context context, AttributeSet attrs) {
        this(context, attrs, 0);
    }

    public SampleFooter(Context context, AttributeSet attrs, int defStyleAttr) {
        super(context, attrs, defStyleAttr);
        init(context);
    }

    public void init(Context context) {
        inflate(context, R.layout.rv_footer, this);
    }
}

package com.fayou.complexrecycler;

import android.support.v7.app.AppCompatActivity;
import android.os.Bundle;
import android.support.v7.widget.GridLayoutManager;
import android.support.v7.widget.LinearLayoutManager;
import android.support.v7.widget.RecyclerView;
import android.support.v7.widget.StaggeredGridLayoutManager;
import android.util.SparseArray;
import android.view.View;
import android.widget.Button;

import com.fayou.complexrecycler.adapter.HeaderAndFooterAdapter;
import com.fayou.complexrecycler.adapter.HeaderAndFooterAndSectionAdapter;
import com.fayou.complexrecycler.adapter.SectionedRecyclerViewAdapter;
import com.fayou.complexrecycler.weight.SampleFooter;
import com.fayou.complexrecycler.weight.SampleHeader;

import java.util.ArrayList;
import java.util.Arrays;
import java.util.List;

public class MainActivity extends AppCompatActivity implements View.OnClickListener {
    private StaggeredGridLayoutManager staggeredGridLayoutManager;
    private LinearLayoutManager layoutManager;
    private GridLayoutManager gridLayoutManager;

    private RecyclerView recyclerView;
    private Button btnAddHeader;
    private Button btnAddFooter;
    private Button btnRmHeader;
    private Button btnRmFooter;
    private SampleHeader mSampleHeader;
    private SampleFooter mSampleFooter;

    public String[] texts = {"a", "b", "c", "d", ".e", "f", "g", "h", "i", "j", "a", "b", "c", "d", ".e", "f", "g", "h", "i", "j","a", "b", "c", "d", ".e", "f", "g", "h", "i", "j", "a", "b", "c", "d", ".e", "f", "g", "h", "i", "j"};
    private String[] texts2 = {"a", "b", "c", "d", ".e", "f", "g", "h", "i", "j", "a", "b", "c", "d", ".e", "f", "g", "h", "i", "j"};
    private SparseArray<List<String>> mSparseLive;


    private HeaderAndFooterAndSectionAdapter mAdapter;

    private HeaderAndFooterAdapter adapter;
    private boolean noSection;

    @Override
    protected void onCreate(Bundle savedInstanceState) {
        super.onCreate(savedInstanceState);
        setContentView(R.layout.activity_main);
        initView();
        /**
         * 1 包括header , footer 的linear
         */
        //linearHeaderAndFooter();


        /**
         * 2 包括header , footer 的taggeredGrid
         */
        //staggeredGridHeaderAndFooter();



        /**
         * 3 包括header , footer 的Grid
         */
        //gridHeaderAndFooter();


        /**
         * 4 包括header , footer , section的linear
         */
        //linearHeaderAndFooterAndSection();



        /**
         * 5 包括header , footer , section的grid
         */
        gridHeaderAndFooterAndSection();

    }

    private void gridHeaderAndFooter() {
        noSection = true;
        gridLayoutManager = new GridLayoutManager(this, 2);
        recyclerView.setLayoutManager(gridLayoutManager);//这里用线性宫格显示 类似于grid view
        recyclerView.setAdapter(adapter = new HeaderAndFooterAdapter(this, texts));
        gridLayoutManager.setSpanSizeLookup(new GridLayoutManager.SpanSizeLookup() {
            @Override
            public int getSpanSize(int position) {
                return (adapter.isHeaderView(position) || adapter.isFooterView(position)) ? gridLayoutManager.getSpanCount() : 1;
            }
        });
    }

    private void staggeredGridHeaderAndFooter() {
        noSection = true;
        staggeredGridLayoutManager = new StaggeredGridLayoutManager(3, StaggeredGridLayoutManager.VERTICAL);
        recyclerView.setLayoutManager(staggeredGridLayoutManager);//这里用线性宫格显示 类似于grid view
        recyclerView.setAdapter(adapter = new HeaderAndFooterAdapter(this,texts));
    }

    private void linearHeaderAndFooter() {
        noSection = true;

        layoutManager = new LinearLayoutManager(this);
        layoutManager.setOrientation(LinearLayoutManager.VERTICAL);
        recyclerView.setLayoutManager(layoutManager);
        recyclerView.setAdapter(adapter = new HeaderAndFooterAdapter(this, texts));
    }


    private void gridHeaderAndFooterAndSection() {
        noSection = false;

        mSparseLive = new SparseArray<>();
        mSparseLive.put(0, Arrays.asList(texts));
        mSparseLive.put(1, Arrays.asList(texts2));
        mAdapter = new HeaderAndFooterAndSectionAdapter(mSparseLive, this);
        recyclerView.setAdapter(mAdapter);

        gridLayoutManager = new GridLayoutManager(this, 3);
        recyclerView.setLayoutManager(gridLayoutManager);//这里用线性宫格显示 类似于grid view
        gridLayoutManager.setSpanSizeLookup(new GridLayoutManager.SpanSizeLookup() {
            @Override
            public int getSpanSize(int position) {
                return (mAdapter.isRecyclerHeaderView(position) || mAdapter.isRecyclerFooterView(position) || mAdapter.isSectionHeader(position) || mAdapter.isSectionFooter(position)) ? gridLayoutManager.getSpanCount() : 1;
            }
        });

    }

    private void linearHeaderAndFooterAndSection() {
        noSection = false;

        mSparseLive = new SparseArray<>();
        mSparseLive.put(0, Arrays.asList(texts));
        mSparseLive.put(1, Arrays.asList(texts2));
        mAdapter = new HeaderAndFooterAndSectionAdapter(mSparseLive, this);

        recyclerView.setAdapter(mAdapter);
        layoutManager = new LinearLayoutManager(this);
        layoutManager.setOrientation(LinearLayoutManager.VERTICAL);
        recyclerView.setLayoutManager(layoutManager);
    }

    private void initView() {
        mSampleHeader = new SampleHeader(this);
        mSampleFooter = new SampleFooter(this);
        recyclerView = (RecyclerView) findViewById(R.id.recycler_view);
        btnAddHeader = (Button) findViewById(R.id.btn_add_header);
        btnAddFooter = (Button) findViewById(R.id.btn_add_footer);
        btnRmHeader = (Button) findViewById(R.id.btn_rm_header);
        btnRmFooter = (Button) findViewById(R.id.btn_rm_footer);

        btnAddHeader.setOnClickListener(this);
        btnAddFooter.setOnClickListener(this);
        btnRmHeader.setOnClickListener(this);
        btnRmFooter.setOnClickListener(this);
    }

    @Override
    public void onClick(View v) {
        switch (v.getId()) {
            case R.id.btn_add_header:
                if (noSection) {
                    adapter.addHeaderView(mSampleHeader);
                } else {
                    mAdapter.addRecyclerHeaderView(mSampleHeader);
                }
                recyclerView.smoothScrollToPosition(0);
                break;
            case R.id.btn_add_footer:
                if (noSection) {
                    adapter.addFooterView(mSampleFooter);
                    recyclerView.smoothScrollToPosition(adapter.getItemCount());
                } else {
                    mAdapter.addRecyclerFooterView(mSampleFooter);
                    recyclerView.smoothScrollToPosition(mAdapter.getItemCount());
                }
                break;
            case R.id.btn_rm_header:
                if (noSection) {
                    adapter.removeHeaderView();
                } else {
                    mAdapter.removeRecyclerHeaderView();
                }
                break;
            case R.id.btn_rm_footer:
                if (noSection) {
                    adapter.removeFooterView();
                } else {
                    mAdapter.removeRecyclerFooterView();
                }
                break;
        }
    }
}

package com.zhangsiqi.dragboarddemo;

import android.os.Bundle;
import android.support.design.widget.FloatingActionButton;
import android.support.design.widget.Snackbar;
import android.support.v7.app.AppCompatActivity;
import android.support.v7.widget.LinearLayoutManager;
import android.support.v7.widget.RecyclerView;
import android.support.v7.widget.Toolbar;
import android.support.v7.widget.helper.ItemTouchHelper;
import android.view.View;
import android.view.Menu;
import android.view.MenuItem;

import java.util.ArrayList;
import java.util.List;

public class MainActivity extends AppCompatActivity {

    private PagerRecyclerView mRecyclerView;
    private RecyclerViewHorizontalDataAdapter mAdapter;
    private DragLayout mLayoutMain;
    private DragHelper mDragHelper;

    private MainActivity mActivity;

    private List<Entry> mData = new ArrayList<>();

    @Override
    protected void onCreate(Bundle savedInstanceState) {
        super.onCreate(savedInstanceState);
        mActivity = this;
        setContentView(R.layout.activity_main);
        Toolbar toolbar = (Toolbar) findViewById(R.id.toolbar);
        setSupportActionBar(toolbar);

        mLayoutMain = (DragLayout) findViewById(R.id.layout_main);
        mRecyclerView = (PagerRecyclerView) findViewById(R.id.rv_lists);
        LinearLayoutManager layoutManager = new LinearLayoutManager(mActivity, LinearLayoutManager.HORIZONTAL, false);
        mRecyclerView.setLayoutManager(layoutManager);
        mRecyclerView.setHasFixedSize(true);
        mRecyclerView.setFlingFactor(0.1f);

        mAdapter = new RecyclerViewHorizontalDataAdapter(mActivity, mData);
        View footer = getLayoutInflater().inflate(R.layout.recyclerview_footer_addlist, null, false);
        mAdapter.setFooterView(footer);
        mRecyclerView.setAdapter(mAdapter);

//        ItemTouchHelper.Callback callback = new ListItemTouchHelperCallback(mAdapter);
//        mItemTouchHelper = new ItemTouchHelper(callback);
//        mItemTouchHelper.attachToRecyclerView(mRecyclerView);

        mRecyclerView.addOnPageChangedListener(mOnPagerChangedListener);
        mRecyclerView.addOnLayoutChangeListener(mOnLayoutChangedListener);
        mRecyclerView.addOnScrollListener(mOnScrollListener);

        mDragHelper = new DragHelper(mActivity);
        mDragHelper.bindHorizontalRecyclerView(mRecyclerView);
        mLayoutMain.setDragHelper(mDragHelper);
        getDataAndRefreshView();
    }

    @Override
    public void onWindowFocusChanged(boolean hasFocus) {
        AttrAboutPhone.saveAttr(this);
        AttrAboutPhone.initScreen(this);
        super.onWindowFocusChanged(hasFocus);
    }

    private void getDataAndRefreshView() {
        for (int i = 0; i < 5; i ++) {
            List<Item> itemList = new ArrayList<>();
            for (int j = 0; j < 10; j ++) {
                itemList.add(new Item("entry " + i + " item id " + j, "item name " + j, "info " + j));
            }
            mData.add(new Entry("entry id " + i, "name " + i, itemList));
        }
        mAdapter.notifyDataSetChanged();
    }

    private RecyclerView.OnScrollListener mOnScrollListener = new RecyclerView.OnScrollListener() {
        @Override
        public void onScrollStateChanged(RecyclerView recyclerView, int newState) {
            super.onScrollStateChanged(recyclerView, newState);
        }

        @Override
        public void onScrolled(RecyclerView recyclerView, int dx, int dy) {
            int childCount = mRecyclerView.getChildCount();
            int width = mRecyclerView.getChildAt(0).getWidth();
            int padding = (mRecyclerView.getWidth() - width) / 2;

            for (int j = 0; j < childCount; j++) {
                View v = recyclerView.getChildAt(j);
                //往左 从 padding 到 -(v.getWidth()-padding) 的过程中，由大到小
                float rate = 0;
                if (v.getLeft() <= padding) {
                    if (v.getLeft() >= padding - v.getWidth()) {
                        rate = (padding - v.getLeft()) * 1f / v.getWidth();
                    } else {
                        rate = 1;
                    }
//                    v.setScaleY(1 - rate * 0.1f);
                    v.setScaleX(1 - rate * 0.1f);

                } else {
                    //往右 从 padding 到 recyclerView.getWidth()-padding 的过程中，由大到小
                    if (v.getLeft() <= recyclerView.getWidth() - padding) {
                        rate = (recyclerView.getWidth() - padding - v.getLeft()) * 1f / v.getWidth();
                    }
//                    v.setScaleY(0.9f + rate * 0.1f);
                    v.setScaleX(0.9f + rate * 0.1f);
                }
            }
        }
    };

    private View.OnLayoutChangeListener mOnLayoutChangedListener = new View.OnLayoutChangeListener() {
        @Override
        public void onLayoutChange(View v, int left, int top, int right, int bottom, int oldLeft, int oldTop, int oldRight, int oldBottom) {
        }
    };

    private PagerRecyclerView.OnPageChangedListener mOnPagerChangedListener = new PagerRecyclerView.OnPageChangedListener() {
        @Override
        public void OnPageChanged(int oldPosition, int newPosition) {

        }
    };

    public DragHelper getDragHelper() {
        return mDragHelper;
    }

}

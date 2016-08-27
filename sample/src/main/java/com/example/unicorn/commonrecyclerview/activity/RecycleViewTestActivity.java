package com.example.unicorn.commonrecyclerview.activity;

import android.app.Activity;
import android.content.Context;
import android.content.Intent;
import android.net.Uri;
import android.os.Bundle;
import android.os.Handler;
import android.support.v7.widget.DefaultItemAnimator;
import android.support.v7.widget.LinearLayoutManager;
import android.support.v7.widget.RecyclerView;
import android.view.View;
import android.widget.TextView;
import android.widget.Toast;

import com.example.unicorn.commonrecyclerview.DividerItemDecoration;
import com.example.unicorn.commonrecyclerview.R;
import com.example.unicorn.commonrecyclerview_library.CommonRecyclerView;
import com.example.unicorn.commonrecyclerview_library.adapter.CommonAdapter;
import com.example.unicorn.commonrecyclerview_library.base.ViewHolder;
import com.example.unicorn.commonrecyclerview_library.wrapper.HeaderAndFooterWrapper;
import com.example.unicorn.commonrecyclerview_library.wrapper.LoadMoreWrapper;
import com.google.android.gms.appindexing.Action;
import com.google.android.gms.appindexing.AppIndex;
import com.google.android.gms.appindexing.Thing;
import com.google.android.gms.common.api.GoogleApiClient;

import java.util.ArrayList;
import java.util.List;


/**
 * Description: <文件描述>
 * Author: yuchengchen
 * Date: 2016/7/5
 */

public class RecycleViewTestActivity extends Activity {

    private CommonRecyclerView commonRecyclerView;

    private List<String> mDatas = new ArrayList<>();
    private CommonAdapter<String> mAdapter;

    private LoadMoreWrapper mLoadMoreWrapper;
    private RecyclerView mRecyclerView;
    private Context mContext;
    /**
     * ATTENTION: This was auto-generated to implement the App Indexing API.
     * See https://g.co/AppIndexing/AndroidStudio for more information.
     */
    private GoogleApiClient client;


    @Override
    protected void onCreate(Bundle savedInstanceState) {
        super.onCreate(savedInstanceState);
        mContext = this;
        //可在布局文件中指定加载中，加载错误，数据为空时显示的布局
        setContentView(R.layout.activity_recycleview_test);
        initDatas();
        initAdapter();
        //先初始化adapterWrapper,再设置给RecyclerView
        initWarpper();
        initView();
        onNewIntent(getIntent());

        // ATTENTION: This was auto-generated to implement the App Indexing API.
        // See https://g.co/AppIndexing/AndroidStudio for more information.
        client = new GoogleApiClient.Builder(this).addApi(AppIndex.API).build();
    }

    protected void onNewIntent(Intent intent) {
        String action = intent.getAction();
        Uri data = intent.getData();
        if (Intent.ACTION_VIEW.equals(action) && data != null) {
            Toast.makeText(mContext, "Open from App Indexing......" + data.toString(), Toast
                    .LENGTH_LONG).show();
        }
    }


    private void initDatas() {
        for (int i = 0; i < 3; i++) {
            mDatas.add(i + "");
        }
    }


    /**
     * 初始化adapter
     */
    private void initAdapter() {
        mAdapter = new CommonAdapter<String>(this, R.layout.item_list, mDatas) {
            @Override
            protected void convert(ViewHolder holder, String s, int position) {
                holder.setText(R.id.id_item_list_title, s + " : " + holder.getAdapterPosition() +
                        " , " + holder.getLayoutPosition());
            }
        };


        /**
         * 设置item点击和长按事件
         */
        mAdapter.setOnItemClickListener(new CommonAdapter.OnItemClickListener<String>() {
            @Override
            public void onItemClick(View view, RecyclerView.ViewHolder holder, String o, int
                    position) {
                Toast.makeText(RecycleViewTestActivity.this, "pos = " + position, Toast
                        .LENGTH_SHORT).show();
                mAdapter.notifyItemRemoved(position);
            }

            @Override
            public boolean onItemLongClick(View view, RecyclerView.ViewHolder holder, String o,
                                           int position) {
                return false;
            }
        });
    }


    /**
     * 通过mAdapter构造Warpper
     */
    private void initWarpper() {

        HeaderAndFooterWrapper mHeaderAndFooterWrapper = new HeaderAndFooterWrapper(mAdapter);
        TextView t1 = new TextView(this);
        t1.setText("Header 1");
        mHeaderAndFooterWrapper.addHeaderView(t1);

        mLoadMoreWrapper = new LoadMoreWrapper(mHeaderAndFooterWrapper);
        mLoadMoreWrapper.setLoadMoreLayout(R.layout.default_loading);

        TextView t2 = new TextView(RecycleViewTestActivity.this);
        t2.setText("no more data");
        mLoadMoreWrapper.setNoMoreView(t2);

        //设置上拉加载监听
        mLoadMoreWrapper.setOnLoadMoreListener(new LoadMoreWrapper.OnLoadMoreListener() {
            @Override
            public void onLoadMoreRequested() {
                new Handler().postDelayed(new Runnable() {
                    @Override
                    public void run() {
                        for (int i = 0; i < 25; i++) {
                            mDatas.add("Add:" + i);
                        }
                        mLoadMoreWrapper.notifyDataSetChanged();
                        /**
                         * 调用setStateNoMore，setStateLoadMore，setStateIdle来控制状态
                         * 若第一次返回数据未满屏，可调用mLoadMoreWrapper.setStateNoMore()显示没有更多数据的ItemView，
                         * 也可调用setStateIdle()不显示没有更多数据的ItemView
                         *
                         */
                        if (mLoadMoreWrapper.getItemCount() > 30) {
                            mLoadMoreWrapper.setStateNoMore();
                        }
                    }
                }, 1000);
            }

            @Override
            public void onNoMore() {
                Toast.makeText(RecycleViewTestActivity.this, "no more data", Toast.LENGTH_SHORT)
                        .show();
            }
        });
    }

    private void initView() {
        initCommonRecyclerView();
        initRecycleView();
    }

    private void initCommonRecyclerView() {
        commonRecyclerView = (CommonRecyclerView) findViewById(R.id.common_recycleview);
        commonRecyclerView.getSwipeToRefresh().setColorSchemeResources(R.color.colorAccent, R
                .color.colorPrimary);

        //设置显示加载中
        commonRecyclerView.showProgress();

        //设置刷新监听
        commonRecyclerView.setOnRefreshListener(new CommonRecyclerView.onRefreshListener() {
            @Override
            public void onRefresh() {
                new Handler().postDelayed(new Runnable() {
                    @Override
                    public void run() {
                        mDatas.clear();
                        initDatas();
                        //设置刷新状态
                        commonRecyclerView.setRefreshing(false);
                        mLoadMoreWrapper.notifyDataSetChanged();
                    }
                }, 2000);
            }
        });
    }

    private void initRecycleView() {
        mRecyclerView = commonRecyclerView.getRecyclerView();
        mRecyclerView.setHasFixedSize(false);
        mRecyclerView.setItemAnimator(new DefaultItemAnimator());
        mRecyclerView.setLayoutManager(new LinearLayoutManager(this));
        mRecyclerView.addItemDecoration(new DividerItemDecoration(this, R.drawable
                .recycle_view_item_divider));


        new Handler().postDelayed(new Runnable() {
            @Override
            public void run() {
                commonRecyclerView.setAdapter(mLoadMoreWrapper);

                //设置显示RecyclerView
                commonRecyclerView.showRecycler();
            }
        }, 2000);

    }

    /**
     * ATTENTION: This was auto-generated to implement the App Indexing API.
     * See https://g.co/AppIndexing/AndroidStudio for more information.
     */
    public Action getIndexApiAction() {
        Thing object = new Thing.Builder()
                .setName("RecycleViewTest Page") // TODO: Define a title for the content shown.
                // TODO: Make sure this auto-generated URL is correct.
                .setUrl(Uri.parse("http://[ENTER-YOUR-URL-HERE]"))
                .build();
        return new Action.Builder(Action.TYPE_VIEW)
                .setObject(object)
                .setActionStatus(Action.STATUS_TYPE_COMPLETED)
                .build();
    }

    @Override
    public void onStart() {
        super.onStart();

        // ATTENTION: This was auto-generated to implement the App Indexing API.
        // See https://g.co/AppIndexing/AndroidStudio for more information.
        client.connect();
        AppIndex.AppIndexApi.start(client, getIndexApiAction());
    }

    @Override
    public void onStop() {
        super.onStop();

        // ATTENTION: This was auto-generated to implement the App Indexing API.
        // See https://g.co/AppIndexing/AndroidStudio for more information.
        AppIndex.AppIndexApi.end(client, getIndexApiAction());
        client.disconnect();
    }
}

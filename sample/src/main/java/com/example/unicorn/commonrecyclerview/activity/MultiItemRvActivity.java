package com.example.unicorn.commonrecyclerview.activity;

import android.content.Context;
import android.os.Bundle;
import android.os.Handler;
import android.support.v7.app.AppCompatActivity;
import android.support.v7.widget.LinearLayoutManager;
import android.support.v7.widget.RecyclerView;
import android.view.LayoutInflater;
import android.view.View;
import android.widget.TextView;
import android.widget.Toast;

import com.example.unicorn.commonrecyclerview.R;
import com.example.unicorn.commonrecyclerview.bean.ChatMessage;
import com.example.unicorn.commonrecyclerview.rv.ChatAdapterForRv;
import com.example.unicorn.commonrecyclerview.rv.MsgComingItemDelagate;
import com.example.unicorn.commonrecyclerview_library.CommonRecyclerView;
import com.example.unicorn.commonrecyclerview_library.adapter.CommonAdapter;
import com.example.unicorn.commonrecyclerview_library.wrapper.LoadMoreWrapper;

import java.util.ArrayList;
import java.util.List;

public class MultiItemRvActivity extends AppCompatActivity {
    private CommonRecyclerView commonRecyclerView;
    private RecyclerView mRecyclerView;
    private LoadMoreWrapper mLoadMoreWrapper;
    private List<ChatMessage> mDatas = new ArrayList<>();
    private Context mContext;


    @Override
    protected void onCreate(Bundle savedInstanceState) {
        super.onCreate(savedInstanceState);
        setContentView(R.layout.activity_recyclerview);
        mContext = this;



        //初始化mRecyclerView
        commonRecyclerView = (CommonRecyclerView) findViewById(R.id.id_recyclerview);
        mRecyclerView = commonRecyclerView.getRecyclerView();
        mRecyclerView.setLayoutManager(new LinearLayoutManager(this));



        //模拟数据
        mDatas.addAll(ChatMessage.MOCK_DATAS);

        //初始化Item种类
        ChatAdapterForRv adapter = new ChatAdapterForRv(this, mDatas, new MsgComingItemDelagate
                .ComingHeaderClickListener() {

            //设置item中的view点击事件
            @Override
            public void onHeaderClick(int position, ChatMessage chatMessage) {
                Toast.makeText(mContext, "header clicked,pos = " + position + "chatMessage = " +
                        chatMessage.toString(), Toast.LENGTH_SHORT)
                        .show();
            }
        });

        //上拉加载更多 通过adapter构造出mLoadMoreWrapper
        mLoadMoreWrapper = new LoadMoreWrapper(adapter);
        mLoadMoreWrapper.setLoadMoreView(LayoutInflater.from(this).inflate(R.layout
                .default_loading, mRecyclerView, false));
        TextView t2 = new TextView(mContext);
        t2.setText("no more data");
        mLoadMoreWrapper.setNoMoreView(t2);
        mLoadMoreWrapper.setOnLoadMoreListener(new LoadMoreWrapper.OnLoadMoreListener() {
            @Override
            public void onLoadMoreRequested() {
                new Handler().postDelayed(new Runnable() {
                    @Override
                    public void run() {
                        int MsgType = (int)(Math.random()*3+1);
                        ChatMessage msg = null;
                        switch (MsgType){
                            case 1:
                                msg = new ChatMessage(R.mipmap.ic_launcher,
                                        "aaa", "where are you " + mDatas.size(),
                                        null, 1);
                                break;
                            case 2:
                                msg = new ChatMessage(R.mipmap.ic_launcher,
                                        "bbb", "where are you " + mDatas.size(),
                                        null, 2);
                                break;
                            case 3:
                                msg = new ChatMessage(R.mipmap.ic_launcher,
                                         "system", "where are you " + mDatas.size(),
                                        null, 3);
                                break;
                        }
                        mDatas.add(msg);
                        mLoadMoreWrapper.notifyDataSetChanged();

                        //根据特定条件，设置不再加载更多，会回调onNoMore()方法
                        if (mLoadMoreWrapper.getItemCount() > 25) {
                            mLoadMoreWrapper.setStateNoMore();
                        }

                    }
                }, 500);
            }

            @Override
            public void onNoMore() {
                Toast.makeText(MultiItemRvActivity.this, "no more data", Toast.LENGTH_SHORT)
                        .show();
            }
        });

        //设置下拉刷新
        commonRecyclerView.setOnRefreshListener(new CommonRecyclerView.onRefreshListener() {
            @Override
            public void onRefresh() {
                new Handler().postDelayed(new Runnable() {
                    @Override
                    public void run() {
                        mDatas.clear();
                        mDatas.addAll(ChatMessage.MOCK_DATAS);
                        mLoadMoreWrapper.notifyDataSetChanged();
                        commonRecyclerView.setRefreshing(false);
                    }
                }, 2000);
            }
        } );

        //item点击和长按
        adapter.setOnItemClickListener(new CommonAdapter.OnItemClickListener<ChatMessage>() {
            @Override
            public void onItemClick(View view, RecyclerView.ViewHolder holder, ChatMessage o, int
                    position) {
                Toast.makeText(MultiItemRvActivity.this, "Click:" + position + " => " + o
                        .getContent(), Toast.LENGTH_SHORT).show();
            }

            @Override
            public boolean onItemLongClick(View view, RecyclerView.ViewHolder holder, ChatMessage
                    o, int position) {
                Toast.makeText(MultiItemRvActivity.this, "LongClick:" + position + " => " + o
                        .getContent(), Toast.LENGTH_SHORT).show();
                return false;
            }
        });
        commonRecyclerView.setAdapter(mLoadMoreWrapper);
    }

}

package com.example.unicorn.commonrecyclerview_library.wrapper;


import android.support.v7.widget.GridLayoutManager;
import android.support.v7.widget.RecyclerView;
import android.support.v7.widget.StaggeredGridLayoutManager;
import android.view.View;
import android.view.ViewGroup;

import com.example.unicorn.commonrecyclerview_library.base.ViewHolder;
import com.example.unicorn.commonrecyclerview_library.utils.WrapperUtils;

/**
 * 通过调用setStateLoadMore(),setStateNoMore(),setStateIdle() 来设置RecyclerView当前状态
 * 控制是否回调加载更多，显示没有更多等
 * @param <T>
 */
public class LoadMoreWrapper<T> extends RecyclerView.Adapter<RecyclerView.ViewHolder> {

    private static final int ITEM_TYPE_LOAD_MORE = Integer.MAX_VALUE - 2;
    private static final int ITEM_TYPE_NO_MORE = Integer.MAX_VALUE - 3;

    private static final int STATE_LOAD_MORE = 1;
    private static final int STATE_NO_MORE = 2;
    private static final int STATE_IDLE = 3;
    private int currentState = STATE_LOAD_MORE;

    public RecyclerView.Adapter getInnerAdapter() {
        return mInnerAdapter;
    }

    private RecyclerView.Adapter mInnerAdapter;
    private View mLoadMoreView;
    private int mLoadMoreLayoutId;
    private View mNoMoreView;
    private int mNoMoreLayoutId;


    public LoadMoreWrapper(RecyclerView.Adapter adapter) {
        mInnerAdapter = adapter;
    }

    private boolean hasLoadMore() {
        return mLoadMoreView != null || mLoadMoreLayoutId != 0;
    }

    private boolean hasNoMore() {
        return mNoMoreView != null || mNoMoreLayoutId != 0;
    }

    //如果设置了加载更多时显示的View,并且当前position大于列表中的item个数时，则返回加载更多中状态
    private boolean isShowLoadMore(int position) {
        return hasLoadMore() && (position >= mInnerAdapter.getItemCount() && currentState ==
                STATE_LOAD_MORE);
    }

    //在实现OnLoadMoreListener时，根据返回数据情况，设置isShowLoadMore标志位为false，则返回没有更多状态
    private boolean isShowNoMore(int position) {
        return hasNoMore() && (position >= mInnerAdapter.getItemCount() && currentState ==
                STATE_NO_MORE);
    }

    /**
     * 设置当前列表状态为加载中状态，RecyclerView底部显示加载中ItemView
     */
    public void setStateLoadMore(){
        currentState = STATE_LOAD_MORE;
    }

    /**
     * 设置当前列表状态为没有更多数据状态，RecyclerView底部显示没有更多数据时的ItemView
     */
    public void setStateNoMore(){
        currentState = STATE_NO_MORE;
    }

    /**
     * 设置当前列表状态为普通状态，RecyclerView底部不显示别的ItemView
     */
    public void setStateIdle(){
        currentState = STATE_IDLE;
    }

    private boolean isShowEmpty(int position) {
        return position >= mInnerAdapter.getItemCount() &&currentState == STATE_IDLE;
    }

    //在RecyclerView.Adapter回调该方法时，根据当前加载状态，返回相应的Item类型
    @Override
    public int getItemViewType(int position) {
        if (isShowLoadMore(position)) {
            return ITEM_TYPE_LOAD_MORE;
        } else if (isShowNoMore(position)) {
            return ITEM_TYPE_NO_MORE;
        }
        return mInnerAdapter.getItemViewType(position);
    }

    //在RecyclerView.Adapter回调该方法时，根据getItemViewType返回的item的类型，创建不同的ViewHolder
    @Override
    public RecyclerView.ViewHolder onCreateViewHolder(ViewGroup parent, int viewType) {
        ViewHolder holder;
        if (viewType == ITEM_TYPE_LOAD_MORE) {
            if (mLoadMoreView != null) {
                holder = ViewHolder.createViewHolder(parent.getContext(), mLoadMoreView);
            } else {
                holder = ViewHolder.createViewHolder(parent.getContext(), parent,
                        mLoadMoreLayoutId);
            }
            return holder;
        } else if (viewType == ITEM_TYPE_NO_MORE) {
            if (mNoMoreView != null) {
                holder = ViewHolder.createViewHolder(parent.getContext(), mNoMoreView);
            } else {
                holder = ViewHolder.createViewHolder(parent.getContext(), parent,
                        mNoMoreLayoutId);
            }
            return holder;
        }
        return mInnerAdapter.onCreateViewHolder(parent, viewType);
    }

    //在RecyclerView.Adapter回调该方法时，回调OnLoadMoreListener中的回调方法
    @Override
    public void onBindViewHolder(RecyclerView.ViewHolder holder, int position) {
        if (isShowLoadMore(position)) {
            if (mOnLoadMoreListener != null) {
                mOnLoadMoreListener.onLoadMoreRequested();
            }
            return;
        } else if (isShowNoMore(position)) {
            if (mOnLoadMoreListener != null) {
                mOnLoadMoreListener.onNoMore();
            }
            return;
        }
        mInnerAdapter.onBindViewHolder(holder, position);
    }

    @Override
    public void onAttachedToRecyclerView(RecyclerView recyclerView) {
        WrapperUtils.onAttachedToRecyclerView(mInnerAdapter, recyclerView, new WrapperUtils
                .SpanSizeCallback() {
            @Override
            public int getSpanSize(GridLayoutManager layoutManager, GridLayoutManager
                    .SpanSizeLookup oldLookup, int position) {
                if (isShowLoadMore(position) || isShowNoMore(position)) {
                    return layoutManager.getSpanCount();
                }
                if (oldLookup != null) {
                    return oldLookup.getSpanSize(position);
                }
                return 1;
            }
        });
    }

    @Override
    public void onViewAttachedToWindow(RecyclerView.ViewHolder holder) {
        mInnerAdapter.onViewAttachedToWindow(holder);
        if (isShowLoadMore(holder.getLayoutPosition()) || isShowNoMore(holder.getLayoutPosition()
        )) {
            setFullSpan(holder);
        }
    }

    private void setFullSpan(RecyclerView.ViewHolder holder) {
        ViewGroup.LayoutParams lp = holder.itemView.getLayoutParams();
        if (lp != null && lp instanceof StaggeredGridLayoutManager.LayoutParams) {
            StaggeredGridLayoutManager.LayoutParams p = (StaggeredGridLayoutManager.LayoutParams)
                    lp;
            p.setFullSpan(true);
        }
    }

    //该方法返回的数量是否加1，决定底部是否显示加载更多或者没有更多的View
    @Override
    public int getItemCount() {
        if (currentState == STATE_IDLE) {
            return mInnerAdapter.getItemCount();
        } else {
            return mInnerAdapter.getItemCount() + 1;
        }
    }

    public interface OnLoadMoreListener {
        void onLoadMoreRequested();
        void onNoMore();
    }

    private OnLoadMoreListener mOnLoadMoreListener;

    public LoadMoreWrapper setOnLoadMoreListener(OnLoadMoreListener loadMoreListener) {
        if (loadMoreListener != null) {
            mOnLoadMoreListener = loadMoreListener;
        }
        return this;
    }

    public LoadMoreWrapper setLoadMoreView(View loadMoreView) {
        mLoadMoreView = loadMoreView;
        return this;
    }

    public LoadMoreWrapper setNoMoreView(View noMoreView) {
        mNoMoreView = noMoreView;
        return this;
    }

    public LoadMoreWrapper setNoMoreLayout(int layoutId) {
        mNoMoreLayoutId = layoutId;
        return this;
    }

    public LoadMoreWrapper setLoadMoreLayout(int layoutId) {
        mLoadMoreLayoutId = layoutId;
        return this;
    }
}

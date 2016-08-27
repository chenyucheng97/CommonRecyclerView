package com.example.unicorn.commonrecyclerview_library.base;


public interface ItemViewDelegate<T>
{

    /**
     * 获取当前item的布局
     * @return
     */
    int getItemViewLayoutId();

    /**
     * ItemViewDelegate的实现类，即多种item类型通过该方法，
     * 在ItemViewDelegateManager的getItemViewType方法中返回当前item的类型时，
     * 实际是通过调用ItemViewDelegate的实现类的该方法，与Manager管理的ItemViewDelegate类型对比确定
     * @param item
     * @param position
     * @return
     */
    boolean isForViewType(T item, int position);

    /**
     * 对当前的item进行数据绑定
     * @param holder
     * @param t
     * @param position
     */
    void convert(ViewHolder holder, T t, int position);

}

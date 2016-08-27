# CommonRecyclerView

此项目参考了鸿洋的baseAdapter和judy的EasyRecyclerView

baseAdapter:https://github.com/hongyangAndroid/baseAdapter

EasyRecyclerView:https://github.com/Jude95/EasyRecyclerView

简要介绍：

1.CommonRecyclerView封装了下拉刷新，上拉加载的RecyclerView，方便设置加载中，加载失败，或者没有数据时的布局

2.CommonRecyclerView的adapter采用装饰者模式，通过原Adapter构造出AdapterWrapper，方便设置上拉加载更多和没有更多数据时的布局，方便设置Header和Footer

3.多种item类型时，采用委派模式，原Adapter中相关操作都交给了ItemViewDelegateManager处理，方便增删item类型处理

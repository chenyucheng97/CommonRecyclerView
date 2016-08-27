package com.example.unicorn.commonrecyclerview.rv;

import android.content.Context;

import com.example.unicorn.commonrecyclerview.bean.ChatMessage;
import com.example.unicorn.commonrecyclerview_library.adapter.MultiItemTypeAdapter;

import java.util.List;


public class ChatAdapterForRv extends MultiItemTypeAdapter<ChatMessage>
{

    public ChatAdapterForRv(Context context, List<ChatMessage> datas,MsgComingItemDelagate.ComingHeaderClickListener listener)
    {
        super(context, datas);

        //添加item种类，由ItemViewDelegateManager管理item种类
        addItemViewDelegate(new MsgSendItemDelagate());
        addItemViewDelegate(new MsgComingItemDelagate(listener));
        addItemViewDelegate(new MsgSystemItemDelagate());
    }
}

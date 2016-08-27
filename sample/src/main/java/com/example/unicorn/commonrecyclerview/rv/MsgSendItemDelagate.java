package com.example.unicorn.commonrecyclerview.rv;


import com.example.unicorn.commonrecyclerview.R;
import com.example.unicorn.commonrecyclerview.bean.ChatMessage;
import com.example.unicorn.commonrecyclerview_library.base.ItemViewDelegate;
import com.example.unicorn.commonrecyclerview_library.base.ViewHolder;


public class MsgSendItemDelagate implements ItemViewDelegate<ChatMessage> {

    @Override
    public int getItemViewLayoutId() {
        return R.layout.main_chat_send_msg;
    }

    @Override
    public boolean isForViewType(ChatMessage item, int position) {
        return item.getMsgType() == 1 ? true : false;
    }

    @Override
    public void convert(ViewHolder holder, ChatMessage chatMessage, int position) {
        holder.setText(R.id.chat_send_content, chatMessage.getContent());
        holder.setText(R.id.chat_send_name, chatMessage.getName());
        holder.setImageResource(R.id.chat_send_icon, chatMessage.getIcon());
    }
}

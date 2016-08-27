package com.example.unicorn.commonrecyclerview.rv;

import android.view.View;

import com.example.unicorn.commonrecyclerview.R;
import com.example.unicorn.commonrecyclerview.bean.ChatMessage;
import com.example.unicorn.commonrecyclerview_library.base.ItemViewDelegate;
import com.example.unicorn.commonrecyclerview_library.base.ViewHolder;


public class MsgComingItemDelagate implements ItemViewDelegate<ChatMessage>
{
    public MsgComingItemDelagate(ComingHeaderClickListener mListener) {
        this.mListener = mListener;
    }

    private ComingHeaderClickListener mListener;

    public interface ComingHeaderClickListener{
          void onHeaderClick(int position,ChatMessage chatMessage);
    }


    @Override
    public int getItemViewLayoutId()
    {
        return R.layout.main_chat_from_msg;
    }

    @Override
    public boolean isForViewType(ChatMessage item, int position)
    {
        return item.getMsgType() == 2 ? true : false;
    }

    @Override
    public void convert(ViewHolder holder, final ChatMessage chatMessage, final int position)
    {
        holder.setText(R.id.chat_from_content, chatMessage.getContent());
        holder.setText(R.id.chat_from_name, chatMessage.getName());
        holder.setImageResource(R.id.chat_from_icon, chatMessage.getIcon());
        holder.getView(R.id.chat_from_icon).setOnClickListener(new View.OnClickListener() {
            @Override
            public void onClick(View view) {
                if (mListener!=null){
                    mListener.onHeaderClick(position,chatMessage);
                }
            }
        });
    }
}

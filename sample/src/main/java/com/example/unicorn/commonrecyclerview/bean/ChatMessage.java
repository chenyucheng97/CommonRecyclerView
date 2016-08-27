package com.example.unicorn.commonrecyclerview.bean;


import com.example.unicorn.commonrecyclerview.R;

import java.util.ArrayList;
import java.util.List;

public class ChatMessage
{
	private int icon;
	private String name;
	private String content;
	private String createDate;
	private int MsgType; // 1 = coming  2 = send  3 = system

	public final static int RECIEVE_MSG = 0;
	public final static int SEND_MSG = 1;

	public ChatMessage(int icon, String name, String content,
					   String createDate, int MsgType)
	{
		this.icon = icon;
		this.name = name;
		this.content = content;
		this.createDate = createDate;
		this.MsgType = MsgType;
	}

	public int getMsgType()
	{
		return MsgType;
	}

	public void setMsgType(int isComMeg)
	{
		this.MsgType = isComMeg;
	}

	public String getContent()
	{
		return content;
	}

	public void setContent(String content)
	{
		this.content = content;
	}

	public int getIcon()
	{
		return icon;
	}

	public void setIcon(int icon)
	{
		this.icon = icon;
	}

	public String getName()
	{
		return name;
	}

	public void setName(String name)
	{
		this.name = name;
	}

	public String getCreateDate()
	{
		return createDate;
	}

	public void setCreateDate(String createDate)
	{
		this.createDate = createDate;
	}

	@Override
	public String toString()
	{
		return "ChatMessage [icon=" + icon + ", name=" + name + ", content="
				+ content + ", createDate=" + createDate +", MsgType = "+ getMsgType()+ "]";
	}

	public static List<ChatMessage> MOCK_DATAS = new ArrayList<>();

	static {
		ChatMessage msg = null;
		msg = new ChatMessage(R.mipmap.ic_launcher, "system", "where are you ",
				null, 3);
		MOCK_DATAS.add(msg);
		msg = new ChatMessage(R.mipmap.ic_launcher, "bbb", "where are you ",
				null, 2);
		MOCK_DATAS.add(msg);
		msg = new ChatMessage(R.mipmap.ic_launcher, "aaa", "where are you ",
				null, 1);
		MOCK_DATAS.add(msg);
		msg = new ChatMessage(R.mipmap.ic_launcher, "bbb", "where are you ",
				null, 2);
		MOCK_DATAS.add(msg);
		msg = new ChatMessage(R.mipmap.ic_launcher, "system", "where are you ",
				null, 3);
		MOCK_DATAS.add(msg);
		msg = new ChatMessage(R.mipmap.ic_launcher, "aaa", "where are you ",
				null, 1);
		MOCK_DATAS.add(msg);
		msg = new ChatMessage(R.mipmap.ic_launcher, "system", "where are you ",
				null, 3);
		MOCK_DATAS.add(msg);
		msg = new ChatMessage(R.mipmap.ic_launcher, "aaa", "where are you ",
				null, 1);
		MOCK_DATAS.add(msg);
		msg = new ChatMessage(R.mipmap.ic_launcher, "bbb", "where are you ",
				null, 2);
		MOCK_DATAS.add(msg);
		msg = new ChatMessage(R.mipmap.ic_launcher, "system", "where are you ",
				null, 3);
		MOCK_DATAS.add(msg);
		msg = new ChatMessage(R.mipmap.ic_launcher, "aaa", "where are you ",
				null, 1);
		MOCK_DATAS.add(msg);
		msg = new ChatMessage(R.mipmap.ic_launcher, "bbb", "where are you ",
				null, 2);
		MOCK_DATAS.add(msg);
		msg = new ChatMessage(R.mipmap.ic_launcher, "aaa", "where are you ",
				null, 1);
		MOCK_DATAS.add(msg);
		msg = new ChatMessage(R.mipmap.ic_launcher, "bbb", "where are you ",
				null, 2);
		MOCK_DATAS.add(msg);
		msg = new ChatMessage(R.mipmap.ic_launcher, "system", "where are you ",
				null, 3);
		MOCK_DATAS.add(msg);
		msg = new ChatMessage(R.mipmap.ic_launcher, "aaa", "where are you ",
				null, 1);
		MOCK_DATAS.add(msg);
		msg = new ChatMessage(R.mipmap.ic_launcher, "bbb", "where are you ",
				null, 2);
		MOCK_DATAS.add(msg);
		msg = new ChatMessage(R.mipmap.ic_launcher, "system", "where are you ",
				null, 3);
		MOCK_DATAS.add(msg);
		msg = new ChatMessage(R.mipmap.ic_launcher, "bbb", "where are you ",
				null, 2);
		MOCK_DATAS.add(msg);
		msg = new ChatMessage(R.mipmap.ic_launcher, "aaa", "where are you ",
				null, 1);
		MOCK_DATAS.add(msg);
	}


}

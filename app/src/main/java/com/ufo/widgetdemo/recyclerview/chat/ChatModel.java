package com.ufo.widgetdemo.recyclerview.chat;

import android.os.Parcel;
import android.os.Parcelable;

import java.util.Date;

/**
 * Created by tjpld on 2016/9/26.
 */

public class ChatModel implements Parcelable {

    private String content;
    private String headPortrait;
    private int chatType;
    private long date;
    private int dataType;
    private int sendStatusType = SendStatusType.Sended;


    public String getHeadPortrait() {
        return headPortrait;
    }

    public void setHeadPortrait(String headPortrait) {
        this.headPortrait = headPortrait;
    }

    public int getChatType() {
        return chatType;
    }

    public void setChatType(int chatType) {
        this.chatType = chatType;
    }

    public String getContent() {
        return content;
    }

    public void setContent(String content) {
        this.content = content;
    }

    public int getDataType() {
        return dataType;
    }

    public void setDataType(int dataType) {
        this.dataType = dataType;
    }

    public Date getDate() {
        return new Date(date);
    }

    public void setDate(Date date) {
        this.date = date.getTime();
    }

    public int getSendStatusType() {
        return sendStatusType;
    }

    public void setSendStatusType(int sendStatusType) {
        this.sendStatusType = sendStatusType;
    }


    @Override
    public int describeContents() {
        return 0;
    }

    @Override
    public void writeToParcel(Parcel dest, int flags) {
        dest.writeString(content);
        dest.writeString(headPortrait);
        dest.writeInt(chatType);
        dest.writeLong(date);
        dest.writeInt(dataType);
    }

    public static final Creator<ChatModel> CREATOR = new Creator<ChatModel>() {
        @Override
        public ChatModel createFromParcel(Parcel source) {
            ChatModel chatModel = new ChatModel();
            chatModel.setContent(source.readString());
            chatModel.setHeadPortrait(source.readString());
            chatModel.setChatType(source.readInt());
            chatModel.setDataType(source.readInt());
            chatModel.setDate(new Date(source.readLong()));
            return chatModel;
        }

        @Override
        public ChatModel[] newArray(int size) {
            return new ChatModel[size];
        }
    };

}

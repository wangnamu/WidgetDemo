package com.ufo.widgetdemo.recyclerview.chat;


import android.content.Context;
import android.support.v7.widget.RecyclerView;
import android.view.LayoutInflater;
import android.view.View;
import android.view.ViewGroup;

import com.ufo.widgetdemo.R;

import java.util.ArrayList;
import java.util.List;

/**
 * Created by tjpld on 2016/9/26.
 */

public class RecyclerViewWithChatAdapter extends RecyclerView.Adapter<RecyclerView.ViewHolder> {
    private List<ChatModel> mData;
    private Context mContext;
    private Tooltips mTooltips;

    private ChatTooltipsItemClickListener mChatTooltipsItemClickListener;

    private Tooltips.TooltipsItemClickListener mTooltipsItemClickListener = new Tooltips.TooltipsItemClickListener() {
        @Override
        public void onTooltipsItemClickListener(String name, Object object) {
            if (mChatTooltipsItemClickListener != null) {
                switch (name) {
                    case "复制":
                        mChatTooltipsItemClickListener.copyItem((ChatModel) object);
                        break;
                    case "转发":
                        mChatTooltipsItemClickListener.forwardItem((ChatModel) object);
                        break;
                    case "删除":
                        mChatTooltipsItemClickListener.delItem((ChatModel) object);
                        break;
                    default:
                        break;
                }
            }
        }
    };

    public RecyclerViewWithChatAdapter(List<ChatModel> data, Context context) {
        mData = data;
        mContext = context;

        mTooltips = new Tooltips(context);

    }

    @Override
    public RecyclerView.ViewHolder onCreateViewHolder(ViewGroup parent, int viewType) {

        if (viewType == ChatType.Host) {
            View view = LayoutInflater.from(mContext).inflate(R.layout.recycler_view_chat_host_item, parent, false);
            HostViewHolder viewHolder = new HostViewHolder(view);
            return viewHolder;
        } else {
            View view = LayoutInflater.from(mContext).inflate(R.layout.recycler_view_chat_guest_item, parent, false);
            GuestViewHolder viewHolder = new GuestViewHolder(view);
            return viewHolder;
        }

    }

    @Override
    public void onBindViewHolder(RecyclerView.ViewHolder holder, final int position) {
        if (holder instanceof HostViewHolder) {
            HostViewHolder viewHolder = (HostViewHolder) holder;

            viewHolder.setTopTime(position, mData.get(position).getDate(), position - 1 >= 0 ? mData.get(position - 1).getDate() : null);
            viewHolder.setHolder(mContext, mData.get(position));

            viewHolder.mContent.setOnLongClickListener(new View.OnLongClickListener() {
                @Override
                public boolean onLongClick(View v) {
                    Utils.vibrator(mContext);

                    List<String> list = new ArrayList<>();
                    list.add("复制");
                    list.add("转发");
                    list.add("删除");

                    mTooltips.setDataSource(list);
                    mTooltips.setObj(mData.get(position));

                    mTooltips.setTooltipsItemClickListener(mTooltipsItemClickListener);
                    mTooltips.show(v);

                    return false;
                }
            });

            viewHolder.mImage.setOnLongClickListener(new View.OnLongClickListener() {
                @Override
                public boolean onLongClick(View v) {
                    Utils.vibrator(mContext);

                    List<String> list = new ArrayList<>();
                    list.add("转发");
                    list.add("删除");

                    mTooltips.setDataSource(list);
                    mTooltips.setObj(mData.get(position));

                    mTooltips.setTooltipsItemClickListener(mTooltipsItemClickListener);
                    mTooltips.show(v);

                    return false;
                }
            });


        } else if (holder instanceof GuestViewHolder) {
            GuestViewHolder viewHolder = (GuestViewHolder) holder;

            viewHolder.setTopTime(position, mData.get(position).getDate(), position - 1 >= 0 ? mData.get(position - 1).getDate() : null);
            viewHolder.setHolder(mContext, mData.get(position));


            viewHolder.mContent.setOnLongClickListener(new View.OnLongClickListener() {
                @Override
                public boolean onLongClick(View v) {
                    Utils.vibrator(mContext);

                    List<String> list = new ArrayList<>();
                    list.add("复制");
                    list.add("转发");
                    list.add("删除");

                    mTooltips.setDataSource(list);
                    mTooltips.setObj(mData.get(position));

                    mTooltips.setTooltipsItemClickListener(mTooltipsItemClickListener);
                    mTooltips.show(v);

                    return false;
                }
            });

            viewHolder.mImage.setOnLongClickListener(new View.OnLongClickListener() {
                @Override
                public boolean onLongClick(View v) {
                    Utils.vibrator(mContext);

                    List<String> list = new ArrayList<>();
                    list.add("转发");
                    list.add("删除");

                    mTooltips.setDataSource(list);
                    mTooltips.setObj(mData.get(position));

                    mTooltips.setTooltipsItemClickListener(mTooltipsItemClickListener);
                    mTooltips.show(v);

                    return false;
                }
            });

        }

    }

    @Override
    public void onBindViewHolder(RecyclerView.ViewHolder holder, int position, List<Object> payloads) {
        if (payloads.isEmpty()) {
            onBindViewHolder(holder, position);
        } else {
            if (holder instanceof HostViewHolder) {
                HostViewHolder viewHolder = (HostViewHolder) holder;
                viewHolder.refresh((ChatModel) payloads.get(0));
            }
        }

    }

    @Override
    public int getItemCount() {
        return mData.size();
    }


    @Override
    public int getItemViewType(int position) {
        return mData.get(position).getChatType();
    }


    public void setChatTooltipsItemClickListener(ChatTooltipsItemClickListener chatTooltipsItemClickListener) {
        this.mChatTooltipsItemClickListener = chatTooltipsItemClickListener;
    }


    public interface ChatTooltipsItemClickListener {
        void copyItem(ChatModel chatModel);

        void forwardItem(ChatModel chatModel);

        void delItem(ChatModel chatModel);
    }
}

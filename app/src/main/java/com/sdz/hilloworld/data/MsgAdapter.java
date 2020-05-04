package com.sdz.hilloworld.data;

import android.view.LayoutInflater;
import android.view.View;
import android.view.ViewGroup;
import android.widget.ImageView;
import android.widget.TextView;

import androidx.annotation.NonNull;
import androidx.recyclerview.widget.RecyclerView;

import com.sdz.hilloworld.R;
import com.sdz.hilloworld.data.MessageOnline;

import java.util.ArrayList;

public class MsgAdapter extends RecyclerView.Adapter<MsgAdapter.ViewHolder> {
    private ArrayList<MessageOnline> list;

    public MsgAdapter(ArrayList<MessageOnline> list) {
        this.list = list;
    }

    class ViewHolder extends RecyclerView.ViewHolder{
        public TextView userName;
        public TextView mName;
        public View view;
        public TextView msgLeft;
        public TextView msgRight;
        public ImageView userHeadPortrait;
        public ImageView myHeadPortrait;
        public ViewHolder(View itemView){
            super(itemView);
            view = itemView;
            userName = view.findViewById(R.id.chat_user_name);
            mName = view.findViewById(R.id.chat_my_name);
            msgLeft = view.findViewById(R.id.chat_msg_left);
            msgRight = view.findViewById(R.id.chat_msg_right);
            userHeadPortrait = view.findViewById(R.id.chat_user_head_portrait);
            myHeadPortrait = view.findViewById(R.id.chat_me_head_portrait);
        }

    }

    @NonNull
    @Override
    public ViewHolder onCreateViewHolder(@NonNull ViewGroup parent, int viewType) {
        View view = LayoutInflater.from(parent.getContext()).inflate(R.layout.chat_msg_content,parent,false);
        ViewHolder viewHolder = new ViewHolder(view);
        return viewHolder;
    }

    @Override
    public void onBindViewHolder(@NonNull ViewHolder holder, int position) {
        MessageOnline message = list.get(position);
        if(message.getType()==0){
            holder.userName.setVisibility(View.VISIBLE);
            holder.mName.setVisibility(View.GONE);
            holder.msgRight.setVisibility(View.GONE);
            holder.msgLeft.setVisibility(View.VISIBLE);
            holder.msgLeft.setText(message.getContent().toString());
            holder.userHeadPortrait.setVisibility(View.VISIBLE);
            holder.myHeadPortrait.setVisibility(View.INVISIBLE);
        }else if(message.getType()==1){
            holder.userName.setVisibility(View.GONE);
            holder.mName.setVisibility(View.VISIBLE);
            holder.msgLeft.setVisibility(View.GONE);
            holder.msgRight.setVisibility(View.VISIBLE);
            holder.msgRight.setText(message.getContent().toString());
            holder.userHeadPortrait.setVisibility(View.INVISIBLE);
            holder.myHeadPortrait.setVisibility(View.VISIBLE);

        }
    }

    @Override
    public int getItemCount() {
        return list.size();
    }


}

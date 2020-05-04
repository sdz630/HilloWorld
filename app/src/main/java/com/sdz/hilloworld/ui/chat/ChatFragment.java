package com.sdz.hilloworld.ui.chat;

import android.content.Intent;
import android.os.Bundle;
import android.os.Handler;
import android.os.Looper;
import android.os.Message;
import android.util.Log;
import android.view.KeyEvent;
import android.view.LayoutInflater;
import android.view.View;
import android.view.ViewGroup;
import android.view.inputmethod.EditorInfo;
import android.widget.EditText;
import android.widget.TextView;
import android.widget.Toast;

import androidx.annotation.Nullable;
import androidx.annotation.NonNull;
import androidx.core.widget.NestedScrollView;
import androidx.fragment.app.Fragment;
import androidx.lifecycle.ViewModelProviders;
import androidx.recyclerview.widget.LinearLayoutManager;
import androidx.recyclerview.widget.RecyclerView;

import com.sdz.hilloworld.MainActivity;
import com.sdz.hilloworld.R;
import com.sdz.hilloworld.common.AsyncTask;
import com.sdz.hilloworld.data.MessageOnline;
import com.sdz.hilloworld.data.MessageType;
import com.sdz.hilloworld.data.MsgAdapter;

import java.util.ArrayList;

public class ChatFragment extends Fragment implements View.OnClickListener, EditText.OnEditorActionListener {

    private static final String TAG = "ChatFragment";

    private NestedScrollView scrollView;
    private RecyclerView recyclerView;
    private EditText msg;
    private TextView send;
    private TextView more;

    private ArrayList<MessageOnline> list;
    private MsgAdapter adapter;

    private Handler mHandler = new Handler(Looper.getMainLooper()) {
        @Override
        public void handleMessage(@NonNull Message msg) {
            super.handleMessage(msg);
            MessageOnline messageOnline = (MessageOnline) msg.getData().getSerializable("Message");
            switch (messageOnline.getType()) {
                case MessageType.CHAT_MESSAGE: {
                    String str = messageOnline.getContent().toString();
                    MessageOnline input = new MessageOnline(0, "不吃棉花糖", str);
                    list.add(input);
                    adapter.notifyDataSetChanged();
                    break;
                }
            }
        }
    };

    private ChatViewModel notificationsViewModel;

    public View onCreateView(@NonNull LayoutInflater inflater,
                             ViewGroup container, Bundle savedInstanceState) {
        notificationsViewModel =
                ViewModelProviders.of(this).get(ChatViewModel.class);
        View root = inflater.inflate(R.layout.fragment_chat, container, false);
        return root;
    }


    @Override
    public void onActivityCreated(@Nullable Bundle savedInstanceState) {
        super.onActivityCreated(savedInstanceState);

    }


    @Override
    public void onStart() {
        super.onStart();

        list = new ArrayList<>();
        msg = getActivity().findViewById(R.id.input_text);
//        msg.setOnEditorActionListener(this);
        send = getActivity().findViewById(R.id.send);
        send.setOnClickListener(this);
//        more = getActivity().findViewById(R.id.toolbar_right_tv);
//        more.setOnClickListener(this);
//        more.setVisibility(View.VISIBLE);

        scrollView = getActivity().findViewById(R.id.scrollView_content);
        recyclerView = getActivity().findViewById(R.id.recyclerView);
        recyclerView.setNestedScrollingEnabled(false);
        scrollView.addOnLayoutChangeListener((View v, int left, int top, int right, int bottom, int oldLeft, int oldTop, int oldRight, int oldBottom) -> {
            if (mHandler != null) {
                mHandler.post(() -> {
                    scrollView.fullScroll(View.FOCUS_DOWN);
                    msg.setFocusable(true);
                    msg.setFocusableInTouchMode(true);
                    msg.requestFocus();
                    msg.findFocus();
                });
            }
        });


        LinearLayoutManager linearLayoutManager = new LinearLayoutManager(getActivity());
        linearLayoutManager.setStackFromEnd(true);
        recyclerView.setLayoutManager(linearLayoutManager);
        adapter = new MsgAdapter(list);
        recyclerView.setAdapter(adapter);
    }

    @Override
    public void onClick(View v) {
        switch (v.getId()) {
            case R.id.send:
                generateMsg(1);
                //发送到服务器

                break;
            case R.id.toolbar_right_tv:
                Toast.makeText(getActivity(), "about me", Toast.LENGTH_SHORT).show();
//                Intent intent = new Intent(getActivity(),AboutMeActivity.class);
//                startActivity(intent);

        }
    }

    private void test(){
        String message = "hi";
        MessageOnline input = new MessageOnline(1, "不吃棉花糖", message);
        list.add(input);
        adapter.notifyDataSetChanged();
        msg.setText("");
        message = "hillo";
        input = new MessageOnline(1, "不吃棉花糖", message);
        list.add(input);
        adapter.notifyDataSetChanged();
        msg.setText("");
    }

    private void generateMsg(int type) {
        String message = msg.getText().toString().trim();
        if ("".equals(message)) {
            Toast.makeText(getActivity(), "Can't input space.", Toast.LENGTH_SHORT).show();
            return;
        }
        MessageOnline input = new MessageOnline(type, "不吃棉花糖", message);
        list.add(input);
        adapter.notifyDataSetChanged();
//        AsyncTask.getInstance().postNormalTask(new Runnable() {
//            @Override
//            public void run() {
//                messageService.senMessage(new MessageOnline(MessageType.CHAT_MESSAGE,"19823319514",message));
//            }
//        });
//        messageService.senMessage(new MessageOnline(MessageType.CHAT_MESSAGE,"19823319514",message));
        msg.setText("");

    }


    @Override
    public boolean onEditorAction(TextView v, int actionId, KeyEvent event) {
        if (actionId == EditorInfo.IME_ACTION_SEND) {
            generateMsg(1);
        }
        return false;
    }

    @Override
    public void onDestroy() {
        super.onDestroy();
        Log.i(TAG, "onDestroy: ");
    }
}
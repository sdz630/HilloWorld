package com.sdz.hilloworld.service;


import android.os.Bundle;
import android.os.Message;
import android.os.Messenger;

import com.sdz.hilloworld.common.AsyncTask;
import com.sdz.hilloworld.data.MessageOnline;
import com.sdz.hilloworld.utils.Log;
import com.sdz.hilloworld.utils.Utils;


import java.io.IOException;
import java.io.InputStream;
import java.io.ObjectInputStream;
import java.io.ObjectOutputStream;
import java.io.OutputStream;
import java.net.Socket;


public class ClientConnect {
    private static final String TAG = "ClientConnect";
    private static final int TIMEOUT = 3000;
    private Socket socket;
    private final String serverIP;
    private final int port;
    private ReadHandler readHandler;
    private ObjectOutputStream socketPrintStream;
    private Messenger messenger;

    public ClientConnect(String serverIP, int port, Messenger messenger) {
        this.messenger = messenger;
        this.serverIP = serverIP;
        this.port = port;
    }

    public void connect() throws IOException {
        socket = new Socket(serverIP, port);
//        socket.setSoTimeout(TIMEOUT);
//        Log.i(TAG, "客户端初始化等待连接");
//        socket.connect(new InetSocketAddress(serverIP, port));

        Log.i(TAG, "客户端{IP:%s, port:%s}发起连接", socket.getLocalAddress(), socket.getLocalPort());
        Log.i(TAG, "服务器--{IP:%s, port:%s}", socket.getInetAddress(), socket.getPort());
        readHandler = new ReadHandler(socket.getInputStream());
        AsyncTask.getInstance().postNormalTask(readHandler);

        OutputStream outputStream = socket.getOutputStream();

        socketPrintStream = new ObjectOutputStream(outputStream);
    }

    public void sendMessage(MessageOnline msg) {
        try {
            socketPrintStream.writeObject(msg);
        } catch (IOException e) {
            e.printStackTrace();
        }

    }

    public void exit() {
        readHandler.exit();
        Utils.close(socketPrintStream);
    }

    class ReadHandler implements Runnable {

        private boolean done = false;
        private final InputStream inputStream;

        ReadHandler(InputStream inputStream) {
            this.inputStream = inputStream;
        }

        //从服务器转发的消息
        @Override
        public void run() {
            try {
                //登录返回码
                ObjectInputStream socketInput = new ObjectInputStream(inputStream);

                do {
                    try {
                        MessageOnline message = (MessageOnline) socketInput.readObject();
                        Bundle bundle = new Bundle();
                        Message msg = new Message();
                        bundle.putSerializable("Message", message);
                        msg.setData(bundle);
                        messenger.send(msg);
                    } catch (IOException e) {
                        continue;
                    }

                } while (!done);
            } catch (Exception e) {
                if (!done) {
                    Log.i(TAG, "连接异常断开：" + e.getMessage());
                }
            } finally {
                Utils.close(inputStream);
            }
        }

        void exit() {
            done = true;
            Utils.close(inputStream);
        }
    }
}

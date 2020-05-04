package com.sdz.hilloworld.data;

import java.io.Serializable;

public class MessageOnline implements Serializable {

    private static final long serialVersionUID = 3591349571489574L;

    private int type;
    private String phoneNumber;
    private Object content;

    public int getType() {
        return type;
    }

    public void setType(int type) {
        this.type = type;
    }

    public String getPhoneNumber() {
        return phoneNumber;
    }

    public void setPhoneNumber(String phoneNumber) {
        this.phoneNumber = phoneNumber;
    }

    public Object getContent() {
        return content;
    }

    public void setContent(Object content) {
        this.content = content;
    }

    public MessageOnline(int type, String phoneNumber, Object content) {
        this.type = type;
        this.phoneNumber = phoneNumber;
        this.content = content;
    }
}

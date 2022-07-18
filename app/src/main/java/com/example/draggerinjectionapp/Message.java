package com.example.draggerinjectionapp;

import androidx.core.app.Person;

import java.util.ArrayList;
import java.util.List;

public class Message {
    private CharSequence text;
    private long timestamp;
    private Person sender;



    public Message(CharSequence text, Person sender) {
        this.text = text;
        this.sender = sender;
        this.timestamp =System.currentTimeMillis();
    }


    public CharSequence getText() {
        return text;
    }

    public long getTimestamp() {
        return timestamp;
    }

    public Person getSender() {
        return sender;
    }
}

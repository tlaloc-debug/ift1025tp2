package com.example.demo;

import java.io.Serializable;

public class Command implements Serializable{

    public String cmd;
    public String args;

    public Command(String cmd, String args) {
        this.cmd = cmd;
        this.args = args;
    }

    @Override
    public String toString() {
        return cmd + " " + args;
    }
    
}

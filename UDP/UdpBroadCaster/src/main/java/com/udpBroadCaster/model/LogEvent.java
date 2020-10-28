package com.udpBroadCaster.model;

import java.net.InetSocketAddress;

// POJO
public class LogEvent {
    public static final byte SEPARATOR = (byte) ':';

    private final InetSocketAddress source;
    private final String logFile;
    private final String msg;
    private final long recieved;

    public LogEvent(String logFile, String msg) {
        this(null, logFile, msg, -1);
    }

    public LogEvent(InetSocketAddress source, String logFile, String msg, long recieved) {
        this.source = source;
        this.logFile = logFile;
        this.msg = msg;
        this.recieved = recieved;
    }

    public InetSocketAddress getSource() {
        return source;
    }

    public String getLogFile() {
        return logFile;
    }

    public String getMsg() {
        return msg;
    }

    public long getRecieved() {
        return recieved;
    }
}

package com.example.calllog;

public class PhoneCall {

    public enum CallDirection {
        INCOMING, OUTGOING, MISSED, REJECTED, BLOCKED
    }

    private final String phoneNumber;
    private final String callTime;
    private final CallDirection callDirection;

    public PhoneCall(String phoneNumber, String callTime, CallDirection callDirection) {
        this.phoneNumber = phoneNumber;
        this.callTime = callTime;
        this.callDirection = callDirection;
    }

    public PhoneCall(String phoneNumber, String callTime, String callDirection) {
        this(phoneNumber, callTime, CallDirection.valueOf(callDirection));
    }

    public String getPhoneNumber() {
        return phoneNumber;
    }

    public String getCallTime() {
        return callTime;
    }

    public CallDirection getCallDirection() {
        return callDirection;
    }
}

package com.example.calllog;

import android.provider.CallLog;

import org.junit.Test;

import static org.junit.Assert.*;

public class MainActivityTest {

    @Test
    public void getCallDirectionFromCode() {
        assertEquals(MainActivity.getCallDirectionFromCode(CallLog.Calls.OUTGOING_TYPE), PhoneCall.CallDirection.OUTGOING);
        assertEquals(MainActivity.getCallDirectionFromCode(CallLog.Calls.INCOMING_TYPE), PhoneCall.CallDirection.INCOMING);
        assertEquals(MainActivity.getCallDirectionFromCode(CallLog.Calls.MISSED_TYPE), PhoneCall.CallDirection.MISSED);
        assertEquals(MainActivity.getCallDirectionFromCode(CallLog.Calls.BLOCKED_TYPE), PhoneCall.CallDirection.BLOCKED);
    }
}

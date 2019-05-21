package com.example.calllog;

import android.database.Cursor;
import android.os.Bundle;
import android.provider.CallLog;

import androidx.annotation.VisibleForTesting;
import androidx.appcompat.app.AppCompatActivity;
import androidx.recyclerview.widget.LinearLayoutManager;
import androidx.recyclerview.widget.RecyclerView;
import androidx.swiperefreshlayout.widget.SwipeRefreshLayout;

import java.text.DateFormat;
import java.util.Date;
import java.util.LinkedList;
import java.util.List;

public class MainActivity extends AppCompatActivity {

    private static int MAX_CALLS = 50;

    private CallListAdapter callListAdapter;

    @Override
    protected void onCreate(Bundle savedInstanceState) {
        super.onCreate(savedInstanceState);
        setContentView(R.layout.activity_main);

        RecyclerView recyclerView = findViewById(R.id.call_list);

        LinearLayoutManager layoutManager = new LinearLayoutManager(this);
        recyclerView.setLayoutManager(layoutManager);

        callListAdapter = new CallListAdapter(this, getPhoneCalls());
        recyclerView.setAdapter(callListAdapter);
    }

    private List<PhoneCall> getPhoneCalls() {
        List<PhoneCall> latestPhoneCalls = new LinkedList<>();

        final DateFormat dateFormatter = DateFormat.getDateTimeInstance(DateFormat.SHORT, DateFormat.SHORT);

        Cursor managedCursor = managedQuery(CallLog.Calls.CONTENT_URI, null,
                null, null, null);
        final int numberColumn = managedCursor.getColumnIndex(CallLog.Calls.NUMBER);
        final int typeColumn = managedCursor.getColumnIndex(CallLog.Calls.TYPE);
        final int dateColumn = managedCursor.getColumnIndex(CallLog.Calls.DATE);

        while (managedCursor.moveToNext() && latestPhoneCalls.size() < MAX_CALLS) {
            String phoneNumber = managedCursor.getString(numberColumn);
            Date callDayTime = new Date(Long.valueOf(managedCursor.getString(dateColumn)));
            String callDate = dateFormatter.format(callDayTime);

            int callDirectionCode = Integer.parseInt(managedCursor.getString(typeColumn));
            PhoneCall.CallDirection callDirection = getCallDirectionFromCode(callDirectionCode);

            latestPhoneCalls.add(new PhoneCall(phoneNumber, callDate, callDirection));
        }
        managedCursor.close();

        return latestPhoneCalls;
    }

    @VisibleForTesting
    protected static PhoneCall.CallDirection getCallDirectionFromCode(final int callDirectionCode) {
        switch (callDirectionCode) {
            case CallLog.Calls.OUTGOING_TYPE:
                return PhoneCall.CallDirection.OUTGOING;
            case CallLog.Calls.INCOMING_TYPE:
                return PhoneCall.CallDirection.INCOMING;
            case CallLog.Calls.MISSED_TYPE:
                return PhoneCall.CallDirection.MISSED;
            case CallLog.Calls.REJECTED_TYPE:
                return PhoneCall.CallDirection.REJECTED;
            case CallLog.Calls.BLOCKED_TYPE:
                return PhoneCall.CallDirection.BLOCKED;
            default:
                return null;
        }
    }
}

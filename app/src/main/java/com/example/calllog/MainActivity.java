package com.example.calllog;

import android.Manifest;
import android.content.pm.PackageManager;
import android.database.Cursor;
import android.os.Bundle;
import android.provider.CallLog;
import android.view.View;

import androidx.annotation.NonNull;
import androidx.annotation.VisibleForTesting;
import androidx.appcompat.app.AppCompatActivity;
import androidx.core.app.ActivityCompat;
import androidx.core.content.ContextCompat;
import androidx.recyclerview.widget.LinearLayoutManager;
import androidx.recyclerview.widget.RecyclerView;
import androidx.swiperefreshlayout.widget.SwipeRefreshLayout;

import java.text.DateFormat;
import java.util.Date;
import java.util.LinkedList;
import java.util.List;

public class MainActivity extends AppCompatActivity {

    private static int MAX_CALLS = 50;

    private final static String [] NECESSARY_PERMISSIONS = new String[]{Manifest.permission.READ_CALL_LOG};
    private final static int PERMISSIONS_REQUEST = 42;

    private CallListAdapter callListAdapter;

    @Override
    protected void onCreate(Bundle savedInstanceState) {
        super.onCreate(savedInstanceState);
        setContentView(R.layout.activity_main);

        if (ContextCompat.checkSelfPermission(this,
                Manifest.permission.READ_CALL_LOG) == PackageManager.PERMISSION_GRANTED) {
            generateCallList();
        } else {
            ActivityCompat.requestPermissions(this, NECESSARY_PERMISSIONS, PERMISSIONS_REQUEST);
        }
    }

    @Override
    public void onRequestPermissionsResult(int requestCode,
                                           @NonNull String[] permissions, @NonNull int[] grantResults) {
        if (requestCode == PERMISSIONS_REQUEST) {
            if (grantResults.length > 0
                    && grantResults[0] == PackageManager.PERMISSION_GRANTED) {
                generateCallList();
            } else {
                findViewById(R.id.permissions_required_message).setVisibility(View.VISIBLE);
            }
        }
    }

    private void generateCallList() {
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

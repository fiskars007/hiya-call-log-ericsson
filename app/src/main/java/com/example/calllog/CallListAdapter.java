package com.example.calllog;

import android.content.Context;
import android.view.LayoutInflater;
import android.view.View;
import android.view.ViewGroup;
import android.widget.TextView;

import androidx.annotation.NonNull;
import androidx.recyclerview.widget.RecyclerView;

import java.util.HashMap;
import java.util.List;
import java.util.Map;

public class CallListAdapter extends RecyclerView.Adapter<CallListAdapterViewHolder> {

    private Context context;
    private List<PhoneCall> phoneCallList;
    private final Map<PhoneCall.CallDirection, String> callDirectionLookup = new HashMap<>();

    public CallListAdapter(Context context, List<PhoneCall> phoneCallList) {
        super();
        this.context = context;
        this.phoneCallList = phoneCallList;

        callDirectionLookup.put(PhoneCall.CallDirection.OUTGOING, context.getString(R.string.call_direction_outgoing));
        callDirectionLookup.put(PhoneCall.CallDirection.INCOMING, context.getString(R.string.call_direction_incoming));
        callDirectionLookup.put(PhoneCall.CallDirection.MISSED, context.getString(R.string.call_direction_missed));
        callDirectionLookup.put(PhoneCall.CallDirection.BLOCKED, context.getString(R.string.call_direction_blocked));
        callDirectionLookup.put(PhoneCall.CallDirection.REJECTED, context.getString(R.string.call_direction_rejected));
    }

    @NonNull
    @Override
    public CallListAdapterViewHolder onCreateViewHolder(@NonNull ViewGroup parent, int viewType) {
        // create a new view
        View callListItem = LayoutInflater.from(this.context)
                .inflate(R.layout.call_list_item, parent, false);
        return new CallListAdapterViewHolder(callListItem);
    }

    @Override
    public void onBindViewHolder(@NonNull CallListAdapterViewHolder holder, int position) {
        final PhoneCall phoneCall = phoneCallList.get(position);
        holder.getPhoneNumber().setText(phoneCall.getPhoneNumber());
        holder.getCallTime().setText(phoneCall.getCallTime());
        holder.getCallDirection().setText(callDirectionLookup.get(phoneCall.getCallDirection()));
    }

    @Override
    public int getItemCount() {
        return this.phoneCallList.size();
    }
}

class CallListAdapterViewHolder extends RecyclerView.ViewHolder {

    private TextView phoneNumber;
    private TextView callTime;
    private TextView callDirection;

    public CallListAdapterViewHolder(@NonNull View itemView) {
        super(itemView);
        phoneNumber = itemView.findViewById(R.id.phone_number);
        callTime = itemView.findViewById(R.id.call_time);
        callDirection = itemView.findViewById(R.id.call_direction);
    }

    public TextView getPhoneNumber() {
        return phoneNumber;
    }

    public TextView getCallTime() {
        return callTime;
    }

    public TextView getCallDirection() {
        return callDirection;
    }
}


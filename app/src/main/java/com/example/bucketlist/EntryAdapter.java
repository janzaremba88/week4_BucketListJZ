package com.example.bucketlist;

import android.content.Context;
import android.graphics.Paint;
import android.support.annotation.NonNull;
import android.support.v7.widget.RecyclerView;
import android.view.LayoutInflater;
import android.view.View;
import android.view.ViewGroup;
import android.widget.TextView;

import java.util.List;

public class EntryAdapter extends RecyclerView.Adapter<EntryAdapter.ViewHolder> {

    private List<Entry> mEntries;
    public EntryAdapter(List<Entry> mEntries) {
        this.mEntries = mEntries;
    }

    @NonNull
    @Override
    public EntryAdapter.ViewHolder onCreateViewHolder(@NonNull ViewGroup viewGroup, int viewType) {
        Context context = viewGroup.getContext();
        LayoutInflater inflater = LayoutInflater.from(context);
        View view = inflater.inflate(R.layout.entry, null);
        EntryAdapter.ViewHolder viewHolder = new EntryAdapter.ViewHolder(view);
        return viewHolder;
    }
    @Override
    public void onBindViewHolder(@NonNull EntryAdapter.ViewHolder viewHolder, int i) {
        Entry entry = mEntries.get(i);
        viewHolder.textView.setText(entry.getEntryTitle());
        viewHolder.textView2.setText(entry.getEntryDescription());
        //remove the strikethru
        viewHolder.textView.setPaintFlags(viewHolder.textView.getPaintFlags() & (~Paint.STRIKE_THRU_TEXT_FLAG));
        viewHolder.textView2.setPaintFlags(viewHolder.textView2.getPaintFlags() & (~Paint.STRIKE_THRU_TEXT_FLAG));
        if (entry.getEntryStrikeThru() == 1) {
            //add the strikethru
            viewHolder.textView.setPaintFlags(viewHolder.textView.getPaintFlags() | Paint.STRIKE_THRU_TEXT_FLAG);
            viewHolder.textView2.setPaintFlags(viewHolder.textView2.getPaintFlags() | Paint.STRIKE_THRU_TEXT_FLAG);
        }
    }

    @Override
    public int getItemCount() {
        return mEntries.size();
    }

    public class ViewHolder extends RecyclerView.ViewHolder {
        TextView textView;
        TextView textView2;
        public ViewHolder(View itemView) {
            super(itemView);
            textView = itemView.findViewById(R.id.checkedTextView);
            textView2 = itemView.findViewById(R.id.textView3);
        }
    }

    public void swapList (List<Entry> newList) {
        mEntries = newList;
        if (newList != null) {
            this.notifyDataSetChanged();
        }
    }
}

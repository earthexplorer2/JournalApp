package androidsamples.java.journalapp;

import android.content.Context;
import android.view.LayoutInflater;
import android.view.View;
import android.view.ViewGroup;
import android.widget.TextView;

import androidx.annotation.NonNull;
import androidx.recyclerview.widget.RecyclerView;

import java.util.List;

public class JournalEntryListAdapter extends RecyclerView.Adapter<JournalEntryListAdapter.EntryViewHolder> {
    private final LayoutInflater mInflater;
    private List<JournalEntry> mEntries;
    private final OnItemClickListener mListener;

    // Define an interface for click events
    public interface OnItemClickListener {
        void onItemClick(JournalEntry entry); // For opening details
    }

    public JournalEntryListAdapter(Context context, OnItemClickListener listener) {
        mInflater = LayoutInflater.from(context);
        mListener = listener; // Set the listener
    }

    @NonNull
    @Override
    public EntryViewHolder onCreateViewHolder(@NonNull ViewGroup parent, int viewType) {
        View itemView = mInflater.inflate(R.layout.fragment_entry, parent, false);
        return new EntryViewHolder(itemView);
    }

    @Override
    public void onBindViewHolder(@NonNull EntryViewHolder holder, int position) {
        if (mEntries != null) {
            JournalEntry current = mEntries.get(position);
            holder.mTxtTitle.setText(current.getmTitle());
            holder.mTxtDate.setText(current.getmDate().toString());
            holder.mTxtStart.setText(current.getmStartTime().toString().substring(0,5));
            holder.mTxtEnd.setText(current.getmEndTime().toString().substring(0,5));
            // Set click listeners
            holder.bind(current, mListener);
        }
    }

    @Override
    public int getItemCount() {
        return (mEntries == null) ? 0 : mEntries.size();
    }

    public void setEntries(List<JournalEntry> entries) {
        mEntries = entries;
        notifyDataSetChanged();
    }

    class EntryViewHolder extends RecyclerView.ViewHolder {
        private final TextView mTxtTitle;
        private final TextView mTxtDate;
        private final TextView mTxtStart;
        private final TextView mTxtEnd;

        public EntryViewHolder(@NonNull View itemView) {
            super(itemView);
            mTxtTitle = itemView.findViewById(R.id.txt_item_title);
            mTxtDate = itemView.findViewById(R.id.txt_item_date);
            mTxtStart = itemView.findViewById(R.id.txt_item_start_time);
            mTxtEnd = itemView.findViewById(R.id.txt_item_end_time);
        }

        public void bind(final JournalEntry entry, final OnItemClickListener listener) {
            itemView.setOnClickListener(v -> listener.onItemClick(entry)); // Handle item click
        }
    }
}


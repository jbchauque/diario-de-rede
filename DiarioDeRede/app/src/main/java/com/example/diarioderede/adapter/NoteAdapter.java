package com.example.diarioderede.adapter;

import android.view.LayoutInflater;
import android.view.View;
import android.view.ViewGroup;
import android.widget.TextView;

import androidx.annotation.NonNull;
import androidx.recyclerview.widget.RecyclerView;

import com.example.diarioderede.R;
import com.example.diarioderede.model.NetworkNote;

import java.util.List;

public class NoteAdapter extends RecyclerView.Adapter<NoteAdapter.NoteViewHolder> {

    private final List<NetworkNote> notes;

    public NoteAdapter(List<NetworkNote> notes) {
        this.notes = notes;
    }

    @NonNull
    @Override
    public NoteViewHolder onCreateViewHolder(@NonNull ViewGroup parent, int viewType) {
        View view = LayoutInflater.from(parent.getContext())
                .inflate(R.layout.item_note, parent, false);
        return new NoteViewHolder(view);
    }

    @Override
    public void onBindViewHolder(@NonNull NoteViewHolder holder, int position) {
        NetworkNote note = notes.get(position);
        holder.tvNoteText.setText(note.getText());
        holder.tvNoteDate.setText(note.getDateTime() + " • " + note.getNetworkType());
    }

    @Override
    public int getItemCount() {
        return notes.size();
    }

    public static class NoteViewHolder extends RecyclerView.ViewHolder {
        final TextView tvNoteText;
        final TextView tvNoteDate;

        public NoteViewHolder(@NonNull View itemView) {
            super(itemView);
            tvNoteText = itemView.findViewById(R.id.tvNoteText);
            tvNoteDate = itemView.findViewById(R.id.tvNoteDate);
        }
    }
}

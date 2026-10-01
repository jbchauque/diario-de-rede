package com.example.diarioderede;

import android.os.Bundle;
import android.text.TextUtils;
import android.widget.Button;
import android.widget.TextView;
import android.widget.Toast;

import androidx.appcompat.app.AppCompatActivity;
import androidx.recyclerview.widget.LinearLayoutManager;
import androidx.recyclerview.widget.RecyclerView;

import com.example.diarioderede.adapter.NoteAdapter;
import com.example.diarioderede.model.NetworkNote;
import com.example.diarioderede.util.NetworkUtils;
import com.example.diarioderede.util.NoteStorage;
import com.google.android.material.textfield.TextInputEditText;

import java.text.SimpleDateFormat;
import java.util.ArrayList;
import java.util.Date;
import java.util.List;
import java.util.Locale;

/**
 * Activity do diário: permite criar e visualizar notas persistidas localmente.
 */
public class DiaryActivity extends AppCompatActivity {

    private TextInputEditText etNote;
    private TextView tvEmpty;
    private RecyclerView recyclerNotes;
    private NoteAdapter adapter;
    private List<NetworkNote> notes;
    private NoteStorage noteStorage;

    @Override
    protected void onCreate(Bundle savedInstanceState) {
        super.onCreate(savedInstanceState);
        setContentView(R.layout.activity_diary);

        etNote = findViewById(R.id.etNote);
        Button btnSaveNote = findViewById(R.id.btnSaveNote);
        tvEmpty = findViewById(R.id.tvEmpty);
        recyclerNotes = findViewById(R.id.recyclerNotes);

        noteStorage = new NoteStorage(this);
        notes = new ArrayList<>();

        adapter = new NoteAdapter(notes);
        recyclerNotes.setLayoutManager(new LinearLayoutManager(this));
        recyclerNotes.setAdapter(adapter);

        btnSaveNote.setOnClickListener(v -> saveNote());
        loadNotes();
    }

    private void saveNote() {
        String text = etNote.getText() == null ? "" : etNote.getText().toString().trim();

        if (TextUtils.isEmpty(text)) {
            etNote.setError(getString(R.string.note_required));
            etNote.requestFocus();
            return;
        }

        String dateTime = new SimpleDateFormat("dd/MM/yyyy HH:mm:ss", Locale.getDefault())
                .format(new Date());
        String networkType = NetworkUtils.getNetworkTypeName(this);

        NetworkNote note = new NetworkNote(text, dateTime, networkType);
        noteStorage.saveNote(note);

        etNote.setText("");
        Toast.makeText(this, R.string.note_saved, Toast.LENGTH_SHORT).show();
        loadNotes();
    }

    private void loadNotes() {
        notes.clear();
        notes.addAll(noteStorage.getNotes());
        adapter.notifyDataSetChanged();

        boolean empty = notes.isEmpty();
        tvEmpty.setVisibility(empty ? TextView.VISIBLE : TextView.GONE);
        recyclerNotes.setVisibility(empty ? RecyclerView.GONE : RecyclerView.VISIBLE);
    }
}

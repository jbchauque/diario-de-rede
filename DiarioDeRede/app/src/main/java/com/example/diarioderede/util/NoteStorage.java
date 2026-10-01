package com.example.diarioderede.util;

import android.content.Context;
import android.content.SharedPreferences;

import com.example.diarioderede.model.NetworkNote;

import org.json.JSONArray;
import org.json.JSONException;
import org.json.JSONObject;

import java.util.ArrayList;
import java.util.List;

/**
 * Persistência local simples para o diário.
 * As notas ficam guardadas no armazenamento privado da aplicação através de SharedPreferences.
 */
public class NoteStorage {

    private static final String PREFS_NAME = "diarioderede_preferences";
    private static final String KEY_NOTES = "notes";

    private final SharedPreferences preferences;

    public NoteStorage(Context context) {
        preferences = context.getSharedPreferences(PREFS_NAME, Context.MODE_PRIVATE);
    }

    public void saveNote(NetworkNote note) {
        List<NetworkNote> notes = getNotes();
        // Inserir a nota no início para mostrar primeiro a mais recente.
        notes.add(0, note);

        JSONArray jsonArray = new JSONArray();
        for (NetworkNote item : notes) {
            JSONObject object = new JSONObject();
            try {
                object.put("text", item.getText());
                object.put("dateTime", item.getDateTime());
                object.put("networkType", item.getNetworkType());
                jsonArray.put(object);
            } catch (JSONException ignored) {
                // Os campos utilizados são strings simples; esta exceção não deve ocorrer.
            }
        }

        preferences.edit().putString(KEY_NOTES, jsonArray.toString()).apply();
    }

    public List<NetworkNote> getNotes() {
        List<NetworkNote> notes = new ArrayList<>();
        String json = preferences.getString(KEY_NOTES, "[]");

        try {
            JSONArray jsonArray = new JSONArray(json);
            for (int i = 0; i < jsonArray.length(); i++) {
                JSONObject object = jsonArray.getJSONObject(i);
                notes.add(new NetworkNote(
                        object.optString("text"),
                        object.optString("dateTime"),
                        object.optString("networkType")
                ));
            }
        } catch (JSONException ignored) {
            // Se os dados estiverem corrompidos, o diário começa vazio em vez de fechar a aplicação.
        }

        return notes;
    }
}

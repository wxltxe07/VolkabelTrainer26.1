package com.example.vokabeltrainer261;

import android.content.Intent;
import android.database.Cursor;
import android.os.Bundle;
import android.widget.Button;

import androidx.appcompat.app.AppCompatActivity;
import androidx.core.view.ViewCompat;
import androidx.core.view.WindowInsetsCompat;
import androidx.core.graphics.Insets;
import androidx.recyclerview.widget.LinearLayoutManager;
import androidx.recyclerview.widget.RecyclerView;

import java.util.ArrayList;
import java.util.List;

public class EditList extends AppCompatActivity {
    private Button returnBtn2;
    private Button updateBtn;
    private List<Vocab> vocabList = new ArrayList<>();
    private DbHelper db;

    @Override
    protected void onCreate(Bundle savedInstanceState) {
        super.onCreate(savedInstanceState);
        setContentView(R.layout.activity_edit_list);

        db = new DbHelper(this);

        returnBtn2 = findViewById(R.id.button5);
        updateBtn = findViewById(R.id.button6);


        loadVocabFromDB();

        RecyclerView recyclerView = findViewById(R.id.rV1);
        recyclerView.setLayoutManager(new LinearLayoutManager(this));
        VocabAdapter adapter = new VocabAdapter(this, vocabList);
        recyclerView.setAdapter(adapter);


        returnBtn2.setOnClickListener(v -> {
            finish();
        });


        updateBtn.setOnClickListener(v -> {
            for (Vocab vocab : vocabList) {
                db.updateVocab(vocab.getGerman(), vocab.getOther(), vocab.getId());
            }
        });


        ViewCompat.setOnApplyWindowInsetsListener(findViewById(R.id.main3), (v, insets) -> {
            Insets systemBars = insets.getInsets(WindowInsetsCompat.Type.systemBars());
            v.setPadding(systemBars.left, systemBars.top, systemBars.right, systemBars.bottom);
            return insets;
        });
    }

    private void loadVocabFromDB() {
        vocabList.clear();

        Cursor cursor = db.getReadableDatabase().rawQuery(
                "SELECT * FROM Vocabs WHERE lektion = ?",
                new String[]{String.valueOf(MainActivity.language)}
        );

        if (cursor.moveToFirst()) {
            do {
                int id = cursor.getInt(cursor.getColumnIndexOrThrow("id"));
                String german = cursor.getString(cursor.getColumnIndexOrThrow("german"));
                String other = cursor.getString(cursor.getColumnIndexOrThrow("other"));

                vocabList.add(new Vocab(id, german, other, EditList.this));
            } while (cursor.moveToNext());
        }

        cursor.close();
    }
}
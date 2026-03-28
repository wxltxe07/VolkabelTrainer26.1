package com.example.vokabeltrainer261;

import android.database.Cursor;
import android.os.Bundle;
import android.widget.Button;
import android.widget.TextView;

import androidx.activity.EdgeToEdge;
import androidx.appcompat.app.AppCompatActivity;
import androidx.core.graphics.Insets;
import androidx.core.view.ViewCompat;
import androidx.core.view.WindowInsetsCompat;

import java.util.ArrayList;

public class LearnVocabActivity extends AppCompatActivity {

    private Button flipBtn, backBtn, rightBtn, wrongBtn;
    private TextView vokabelText, spracheText, scoreText;

    private DbHelper db;
    private String mode;
    private String filter;

    private ArrayList<Vocab> vocabList = new ArrayList<>();
    private int currentIndex = 0;
    private boolean deutsch = false;

    @Override
    protected void onCreate(Bundle savedInstanceState) {
        super.onCreate(savedInstanceState);
        EdgeToEdge.enable(this);
        setContentView(R.layout.activity_learn_vocab);

        db = new DbHelper(this);

        flipBtn = findViewById(R.id.buttonT6);
        backBtn = findViewById(R.id.buttonT4);
        rightBtn = findViewById(R.id.buttonT5);
        wrongBtn = findViewById(R.id.buttonT7);

        vokabelText = findViewById(R.id.textViewT5);
        spracheText = findViewById(R.id.textViewT4);
        scoreText = findViewById(R.id.textViewT6);

        mode = getIntent().getStringExtra("mode");
        filter = getIntent().getStringExtra("filter");

        if (mode == null) {
            mode = "zufall";
        }

        if (filter == null) {
            filter = "alle";
        }

        if (MainActivity.language != null) {
            spracheText.setText(MainActivity.language);
        }

        loadAllVocabs();
        showVocab();

        flipBtn.setOnClickListener(v -> {
            Vocab vocab = getCurrentVocab();
            if (vocab == null) {
                return;
            }
            if (!deutsch) {
                vokabelText.setText(vocab.getGerman());
                deutsch = true;
            } else {
                vokabelText.setText(vocab.getOther());
                deutsch = false;
            }
        });

        rightBtn.setOnClickListener(v -> {
            Vocab vocab = getCurrentVocab();
            if (vocab == null) {
                return;
            }
            db.updateScore(vocab.getScore() + 1, vocab.getId());
            nextVocab();
        });

        wrongBtn.setOnClickListener(v -> {
            Vocab vocab = getCurrentVocab();
            if (vocab == null) {
                return;
            }
            db.updateScore(0, vocab.getId());
            nextVocab();
        });

        backBtn.setOnClickListener(v -> {
            finish();
        });

        ViewCompat.setOnApplyWindowInsetsListener(findViewById(R.id.mainT4), (v, insets) -> {
            Insets systemBars = insets.getInsets(WindowInsetsCompat.Type.systemBars());
            v.setPadding(systemBars.left, systemBars.top, systemBars.right, systemBars.bottom);
            return insets;
        });
    }




    private void loadAllVocabs() {
        vocabList.clear();
        Cursor cursor = db.getAllVocabs();

        while (cursor.moveToNext()) {
            int id = cursor.getInt(cursor.getColumnIndexOrThrow("id"));
            String german = cursor.getString(cursor.getColumnIndexOrThrow("german"));
            String other = cursor.getString(cursor.getColumnIndexOrThrow("other"));
            int score = cursor.getInt(cursor.getColumnIndexOrThrow("score"));

            if (isValidVocab(score)) {
                vocabList.add(new Vocab(id, german, other, LearnVocabActivity.this));
            }
        }
        cursor.close();
        currentIndex = 0;
    }

    private Vocab getCurrentVocab() {
        if (vocabList.isEmpty() || currentIndex >= vocabList.size()) {
            return null;
        }
        return vocabList.get(currentIndex);
    }

    private void showVocab() {
        Vocab vocab = getCurrentVocab();
        if (vocab == null) {
            finish();
            return;
        }

        scoreText.setText(String.valueOf(vocab.getScore()));

        if ("deutsch".equals(mode)) {
            vokabelText.setText(vocab.getGerman());
            deutsch = true;
        } else if ("fremd".equals(mode)) {
            vokabelText.setText(vocab.getOther());
            deutsch = false;
        } else {
            if (Math.random() < 0.5) {
                vokabelText.setText(vocab.getGerman());
                deutsch = true;
            } else {
                vokabelText.setText(vocab.getOther());
                deutsch = false;
            }
        }
    }

    private void nextVocab() {
        currentIndex++;
        if (currentIndex >= vocabList.size()) {
            finish();
        } else {
            showVocab();
        }
    }

    private boolean isValidVocab(int score) {
        if ("hard".equals(filter)) {
            if (score > 3) {
                return false;
            }
        }

        if ("mid".equals(filter)) {
            if (score < 4 || score > 7) {
                return false;
            }
        }

        return true;
    }
}
package com.example.vokabeltrainer261;

import android.content.Intent;
import android.os.Bundle;
import android.view.View;
import android.widget.Button;
import android.widget.TextView;

import androidx.activity.EdgeToEdge;
import androidx.appcompat.app.AppCompatActivity;
import androidx.core.graphics.Insets;
import androidx.core.view.ViewCompat;
import androidx.core.view.WindowInsetsCompat;

public class LearnVocabActivity extends AppCompatActivity {

    private Button flipBtn, backBtn, rightBtn, wrongBtn;
    private TextView vokabelText, spracheText, scoreText;

    private int a = 0;
    private boolean deutsch = false;
    private DbHelper db;

    private String mode;
    private String filter;

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

        if (mode == null) mode = "zufall";
        if (filter == null) filter = "alle";


        if (MainActivity.language != null) {
            spracheText.setText(MainActivity.language);
        }


        loadFirstVocab();


        flipBtn.setOnClickListener(v -> {
            if (!deutsch) {
                vokabelText.setText(db.readGerman(a));
                deutsch = true;
            } else {
                vokabelText.setText(db.readOther(a));
                deutsch = false;
            }
        });


        rightBtn.setOnClickListener(v -> {
            int b = db.readScore(a);
            b++;
            db.updateScore(b, a);
            nextVocab();
        });


        wrongBtn.setOnClickListener(v -> {
            int b = db.readScore(a);
            if (b > 0) b--;
            db.updateScore(b, a);
            nextVocab();
        });


        backBtn.setOnClickListener(v -> {
            Intent intent = new Intent(LearnVocabActivity.this, MainActivity.class);
            startActivity(intent);
        });

        ViewCompat.setOnApplyWindowInsetsListener(findViewById(R.id.mainT4), (v, insets) -> {
            Insets systemBars = insets.getInsets(WindowInsetsCompat.Type.systemBars());
            v.setPadding(systemBars.left, systemBars.top, systemBars.right, systemBars.bottom);
            return insets;
        });
    }


    private void loadFirstVocab() {
        while (!isValidVocab(a) && a < 1000) {
            a++;
        }

        if (a >= 1000) {
            finish();
            return;
        }

        showVocab();
    }


    private void nextVocab() {
        a++;

        while (!isValidVocab(a) && a < 1000) {
            a++;
        }

        if (a >= 1000) {
            finish();
            return;
        }

        showVocab();
    }


    private void showVocab() {
        scoreText.setText(String.valueOf(db.readScore(a)));

        if ("deutsch".equals(mode)) {
            vokabelText.setText(db.readGerman(a));
            deutsch = true;

        } else if ("fremd".equals(mode)) {
            vokabelText.setText(db.readOther(a));
            deutsch = false;

        } else {
            if (Math.random() < 0.5) {
                vokabelText.setText(db.readGerman(a));
                deutsch = true;
            } else {
                vokabelText.setText(db.readOther(a));
                deutsch = false;
            }
        }
    }


    private boolean isValidVocab(int index) {
        String vokabel = db.readOther(index);
        if (vokabel == null) return false;

        if ("schwer".equals(filter)) {
            int score = db.readScore(index);
            if (score > 5) return false;
        }

        return true;
    }
}
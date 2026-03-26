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

    private Button flipBtn;
    private Button backBtn;
    private Button rightBtn;
    private Button wrongBtn;
    private TextView vokabelText;
    private TextView spracheText;
    private TextView scoreText;


    private int a = 0;
    private boolean deutsch = false;
    private DbHelper db;

    private String mode;

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

        spracheText.setText(MainActivity.language);


        String ersteVokabel;

        do {
            ersteVokabel = db.readOther(a);
            if (ersteVokabel == null) {
                a++;
            }
        } while (ersteVokabel == null && a < 1000);

        if (ersteVokabel == null) {
            finish();
            return;
        }
        scoreText.setText(db.readScore(a));

        if (mode.equals("deutsch")) {
            vokabelText.setText(db.readGerman(a));
            deutsch = true;
        } else if (mode.equals("fremd")) {
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


        flipBtn.setOnClickListener(new View.OnClickListener() {
            @Override
            public void onClick(View v) {
                if (!deutsch){
                    vokabelText.setText(db.readGerman(a));
                    deutsch = true;
                } else {
                    vokabelText.setText(db.readOther(a));
                    deutsch = false;
                }
            }
        });


        rightBtn.setOnClickListener(new View.OnClickListener() {
            @Override
            public void onClick(View v) {

                int b;

                b = db.readScore(a);
                b ++;
                db.updateScore(b,a);

                a++;

                String nextVokabel;

                do {
                    nextVokabel = db.readOther(a);
                    if (nextVokabel == null) {
                        a++;
                    }
                } while (nextVokabel == null && a < 1000);

                if (nextVokabel == null) {
                    finish();
                } else {

                    if (mode.equals("deutsch")) {
                        vokabelText.setText(db.readGerman(a));
                        deutsch = true;
                        scoreText.setText(db.readScore(a));
                    } else if (mode.equals("fremd")) {
                        vokabelText.setText(db.readOther(a));
                        deutsch = false;
                        scoreText.setText(db.readScore(a));
                    } else {
                        if (Math.random() < 0.5) {
                            vokabelText.setText(db.readGerman(a));
                            deutsch = true;
                            scoreText.setText(db.readScore(a));
                        } else {
                            vokabelText.setText(db.readOther(a));
                            deutsch = false;
                            scoreText.setText(db.readScore(a));
                        }
                    }
                }
            }
        });



        wrongBtn.setOnClickListener(new View.OnClickListener() {
            @Override
            public void onClick(View v) {

                int b;

                b = db.readScore(a);
                if (b > 0){
                b --;
                }

                db.updateScore(b,a);

                a++;

                String nextVokabel;

                do {
                    nextVokabel = db.readOther(a);
                    if (nextVokabel == null) {
                        a++;
                    }
                } while (nextVokabel == null && a < 1000);

                if (nextVokabel == null) {
                    finish();
                } else {

                    if (mode.equals("deutsch")) {
                        vokabelText.setText(db.readGerman(a));
                        deutsch = true;
                        scoreText.setText(db.readScore(a));
                    } else if (mode.equals("fremd")) {
                        vokabelText.setText(db.readOther(a));
                        deutsch = false;
                        scoreText.setText(db.readScore(a));
                    } else {
                        if (Math.random() < 0.5) {
                            vokabelText.setText(db.readGerman(a));
                            deutsch = true;
                            scoreText.setText(db.readScore(a));
                        } else {
                            vokabelText.setText(db.readOther(a));
                            deutsch = false;
                            scoreText.setText(db.readScore(a));
                        }
                    }
                }
            }
        });





        backBtn.setOnClickListener(new View.OnClickListener() {
            @Override
            public void onClick(View v) {
                Intent intent = new Intent(LearnVocabActivity.this, MainActivity.class);
                startActivity(intent);
            }
        });



        ViewCompat.setOnApplyWindowInsetsListener(findViewById(R.id.mainT4), (v, insets) -> {
            Insets systemBars = insets.getInsets(WindowInsetsCompat.Type.systemBars());
            v.setPadding(systemBars.left, systemBars.top, systemBars.right, systemBars.bottom);
            return insets;
        });
    }
}
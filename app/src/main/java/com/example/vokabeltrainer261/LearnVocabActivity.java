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
    private Button nextBtn;
    private TextView vokabelText;


    private int a = 0;
    private boolean deutsch = false;
    private DbHelper db;

    @Override
    protected void onCreate(Bundle savedInstanceState) {
        super.onCreate(savedInstanceState);
        EdgeToEdge.enable(this);
        setContentView(R.layout.activity_learn_vocab);
        db = new DbHelper(this);

        flipBtn = findViewById(R.id.buttonT6);
        nextBtn = findViewById(R.id.buttonT5);
        backBtn = findViewById(R.id.buttonT4);

        vokabelText = findViewById(R.id.textViewT5);


        vokabelText.setText(db.readOther(a));


        flipBtn.setOnClickListener(new View.OnClickListener() {
            @Override
            public void onClick(View v) {
               if (deutsch == false){
                   vokabelText.setText(db.readGerman(a));
                   deutsch = true;
               } else {
                   vokabelText.setText(db.readOther(a));
                   deutsch = false;

               }

            }

        });

        nextBtn.setOnClickListener(new View.OnClickListener() {
            @Override
            public void onClick(View v) {
                a ++;
                vokabelText.setText(db.readOther(a));
                deutsch = false;


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
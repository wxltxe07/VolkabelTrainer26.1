package com.example.vokabeltrainer261;

import android.content.Intent;
import android.os.Bundle;
import android.view.View;
import android.widget.Button;

import androidx.activity.EdgeToEdge;
import androidx.appcompat.app.AppCompatActivity;
import androidx.core.graphics.Insets;
import androidx.core.view.ViewCompat;
import androidx.core.view.WindowInsetsCompat;
import androidx.recyclerview.widget.LinearLayoutManager;
import androidx.recyclerview.widget.RecyclerView;

import java.util.ArrayList;
import java.util.List;



public class EditList extends AppCompatActivity {
    private Button returnBtn2;

    private Button updateBtn;

    private List<Vocab> vocabList = new ArrayList<>();

    @Override
    protected void onCreate(Bundle savedInstanceState) {
        super.onCreate(savedInstanceState);
        EdgeToEdge.enable(this);
        setContentView(R.layout.activity_edit_list);


        DbHelper db = new DbHelper(this);

        returnBtn2 = findViewById(R.id.button5);

        updateBtn = findViewById(R.id.button6);

        updateBtn.setOnClickListener(new View.OnClickListener() {
            @Override
            public void onClick(View v) {
                for (int i = 0; i < db.readAmount(); i++) {

                    db.updateVocab(vocabList.get(i).getGerman(), vocabList.get(i).getOther(), i);
                }
            }
        });

        returnBtn2.setOnClickListener(new View.OnClickListener() {
            @Override
            public void onClick(View v) {
                Intent intent = new Intent(EditList.this, MainActivity.class);
                startActivity(intent);
            }
        });



        for (int i = 0; i < db.readAmount()+1; i++){
            vocabList.add(new Vocab(this, i));
        }


        RecyclerView recyclerView = findViewById(R.id.rV1);

        recyclerView.setLayoutManager(new LinearLayoutManager(this));

        VocabAdapter adapter = new VocabAdapter(vocabList);

        recyclerView.setAdapter(adapter);

        ViewCompat.setOnApplyWindowInsetsListener(findViewById(R.id.main3), (v, insets) -> {
            Insets systemBars = insets.getInsets(WindowInsetsCompat.Type.systemBars());
            v.setPadding(systemBars.left, systemBars.top, systemBars.right, systemBars.bottom);
            return insets;
        });
    }
}
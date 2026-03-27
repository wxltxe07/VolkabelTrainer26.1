package com.example.vokabeltrainer261;

import android.content.Intent;
import android.os.Bundle;
import android.view.View;
import android.widget.AdapterView;
import android.widget.ArrayAdapter;
import android.widget.Button;
import android.widget.Spinner;
import android.widget.TextView;
import android.widget.RadioGroup;
import android.widget.RadioButton;

import androidx.activity.EdgeToEdge;
import androidx.appcompat.app.AppCompatActivity;
import androidx.core.graphics.Insets;
import androidx.core.view.ViewCompat;
import androidx.core.view.WindowInsetsCompat;

import com.google.android.material.floatingactionbutton.FloatingActionButton;

import java.util.ArrayList;
import java.util.Arrays;

public class MainActivity extends AppCompatActivity {

    private Button lerneBtn;
    static ArrayList<String> languagesList = new ArrayList<>(Arrays.asList("Englisch", "Spanisch", "Französisch"));

    private TextView vokabelText;
    private TextView trainerText;
    private FloatingActionButton plusBtn;
    private FloatingActionButton editListBtn;
    private Button addLektion;
    private Spinner spinner;
    private DbHelper db = new DbHelper(this);
    static String language;
    private RadioGroup radioGroup;
    private RadioButton deutschBtn, fremdBtn, zufallBtn;

    @Override
    protected void onCreate(Bundle savedInstanceState) {
        super.onCreate(savedInstanceState);
        EdgeToEdge.enable(this);
        setContentView(R.layout.activity_main);

        vokabelText = findViewById(R.id.textView);
        trainerText = findViewById(R.id.textView1);
        lerneBtn = findViewById(R.id.button);
        plusBtn = findViewById(R.id.floatingActionButton);
        editListBtn = findViewById(R.id.floatingActionButton2);
        addLektion = findViewById(R.id.button4);
        radioGroup = findViewById(R.id.radioGroup);
        deutschBtn = findViewById(R.id.radioButton);
        fremdBtn = findViewById(R.id.radioButton2);
        zufallBtn = findViewById(R.id.radioButton3);
        spinner = findViewById(R.id.spinnerLanguage);


        ArrayAdapter<String> adapter = new ArrayAdapter<>(
                this,
                android.R.layout.simple_spinner_dropdown_item,
                languagesList
        );

        spinner.setAdapter(adapter);

        spinner.setOnItemSelectedListener(new AdapterView.OnItemSelectedListener() {
            @Override
            public void onItemSelected(AdapterView<?> parent, View view, int position, long id) {
                language = languagesList.get(position);
            }

            @Override
            public void onNothingSelected(AdapterView<?> parent) {
                language = "Englisch";
            }
        });

        fremdBtn.setChecked(true);

        editListBtn.setOnClickListener(new View.OnClickListener() {
            @Override
            public void onClick(View v) {
                Intent intent = new Intent(MainActivity.this, EditList.class);
                startActivity(intent);
            }
        });

        lerneBtn.setOnClickListener(new View.OnClickListener() {
            @Override
            public void onClick(View v) {
                Intent intent = new Intent(MainActivity.this, LearnVocabActivity.class);
                if (deutschBtn.isChecked()) {
                    intent.putExtra("mode", "deutsch");
                } else if (fremdBtn.isChecked()) {
                    intent.putExtra("mode", "fremd");
                } else {
                    intent.putExtra("mode", "zufall");
                }
                startActivity(intent);
            }
        });

        addLektion.setOnClickListener(new View.OnClickListener() {
            @Override
            public void onClick(View v) {
                Intent intent = new Intent(MainActivity.this, addLektionActivity.class);
                startActivity(intent);
            }
        });

        plusBtn.setOnClickListener(new View.OnClickListener() {
            @Override
            public void onClick(View v) {
                Intent intent = new Intent(MainActivity.this, NewVocabMenu1Activity.class);
                startActivity(intent);
            }
        });

        ViewCompat.setOnApplyWindowInsetsListener(findViewById(R.id.main), (v, insets) -> {
            Insets systemBars = insets.getInsets(WindowInsetsCompat.Type.systemBars());
            v.setPadding(systemBars.left, systemBars.top, systemBars.right, systemBars.bottom);
            return insets;
        });
    }
}
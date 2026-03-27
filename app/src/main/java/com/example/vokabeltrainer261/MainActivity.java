package com.example.vokabeltrainer261;

import android.content.Intent;
import android.os.Bundle;
import android.view.View;
import android.widget.AdapterView;
import android.widget.ArrayAdapter;
import android.widget.Button;
import android.widget.Spinner;
import android.widget.TextView;

import androidx.activity.EdgeToEdge;
import androidx.appcompat.app.AppCompatActivity;
import androidx.core.graphics.Insets;
import androidx.core.view.ViewCompat;
import androidx.core.view.WindowInsetsCompat;

import com.google.android.material.floatingactionbutton.FloatingActionButton;
import android.widget.RadioGroup;
import android.widget.RadioButton;

public class MainActivity extends AppCompatActivity {

    private Button lerneBtn;
    private Button addLektion;
    private TextView vokabelText;
    private TextView trainerText;
    private FloatingActionButton plusBtn;
    private FloatingActionButton editListBtn;

    private Spinner spinner;

    private DbHelper db;

    static String language;

    private RadioGroup radioGroup;
    private RadioButton deutschBtn, fremdBtn, zufallBtn;

    private RadioGroup radioGroupT1;
    private RadioButton alleBtn, schwereBtn;

    @Override
    protected void onCreate(Bundle savedInstanceState) {
        super.onCreate(savedInstanceState);
        EdgeToEdge.enable(this);
        setContentView(R.layout.activity_main);

        // DB initialisieren (wichtig: erst hier!)
        db = new DbHelper(this);

        // Views
        vokabelText = findViewById(R.id.textView);
        trainerText = findViewById(R.id.textView1);
        lerneBtn = findViewById(R.id.button);
        plusBtn = findViewById(R.id.floatingActionButton);
        editListBtn = findViewById(R.id.floatingActionButton2);
        addLektion = findViewById(R.id.button4);

        // Radio Gruppen
        radioGroup = findViewById(R.id.radioGroup);
        deutschBtn = findViewById(R.id.radioButton);
        fremdBtn = findViewById(R.id.radioButton2);
        zufallBtn = findViewById(R.id.radioButton3);

        radioGroupT1 = findViewById(R.id.radioGroupT1);
        alleBtn = findViewById(R.id.radioButtonT2);
        schwereBtn = findViewById(R.id.radioButtonT3);

        // Defaults
        fremdBtn.setChecked(true);
        alleBtn.setChecked(true);

        // Spinner
        spinner = findViewById(R.id.spinnerLanguage);

        String[] languages = {"Englisch", "Spanisch", "Französisch"};

        ArrayAdapter<String> adapter = new ArrayAdapter<>(
                this,
                android.R.layout.simple_spinner_dropdown_item,
                languages
        );

        spinner.setAdapter(adapter);

        spinner.setOnItemSelectedListener(new AdapterView.OnItemSelectedListener() {
            @Override
            public void onItemSelected(AdapterView<?> parent, View view, int position, long id) {
                language = parent.getItemAtPosition(position).toString();
            }

            @Override
            public void onNothingSelected(AdapterView<?> parent) {
                language = "Englisch";
            }
        });

        // Edit Liste
        editListBtn.setOnClickListener(v -> {
            Intent intent = new Intent(MainActivity.this, EditList.class);
            startActivity(intent);
        });

        // Lernen starten
        lerneBtn.setOnClickListener(v -> {
            Intent intent = new Intent(MainActivity.this, LearnVocabActivity.class);

            // Modus
            if (deutschBtn.isChecked()) {
                intent.putExtra("mode", "deutsch");
            } else if (fremdBtn.isChecked()) {
                intent.putExtra("mode", "fremd");
            } else {
                intent.putExtra("mode", "zufall");
            }

            // Filter
            if (schwereBtn.isChecked()) {
                intent.putExtra("filter", "schwer");
            } else {
                intent.putExtra("filter", "alle");
            }

            startActivity(intent);
        });

        // Neue Lektion
        addLektion.setOnClickListener(v -> {
            Intent intent = new Intent(MainActivity.this, addLektionActivity.class);
            startActivity(intent);
        });

        // Neue Vokabel
        plusBtn.setOnClickListener(v -> {
            Intent intent = new Intent(MainActivity.this, NewVocabMenu1Activity.class);
            startActivity(intent);
        });

        // Insets
        ViewCompat.setOnApplyWindowInsetsListener(findViewById(R.id.main), (v, insets) -> {
            Insets systemBars = insets.getInsets(WindowInsetsCompat.Type.systemBars());
            v.setPadding(systemBars.left, systemBars.top, systemBars.right, systemBars.bottom);
            return insets;
        });
    }
}
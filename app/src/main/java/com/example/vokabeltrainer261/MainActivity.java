package com.example.vokabeltrainer261;

import android.os.Bundle;
import android.view.View;
import android.widget.Button;
import android.widget.TextView;

import androidx.activity.EdgeToEdge;
import androidx.appcompat.app.AppCompatActivity;
import androidx.core.graphics.Insets;
import androidx.core.view.ViewCompat;
import androidx.core.view.WindowInsetsCompat;

import com.google.android.material.floatingactionbutton.FloatingActionButton;

public class MainActivity extends AppCompatActivity {

    private Button lerneBtn;
    private TextView vokabelText;
    private FloatingActionButton plusBtn;
    private DbHelper db = new DbHelper(this);

    @Override
    protected void onCreate(Bundle savedInstanceState) {
        super.onCreate(savedInstanceState);
        EdgeToEdge.enable(this);
        setContentView(R.layout.activity_main);

        vokabelText = findViewById(R.id.textView);
        lerneBtn = findViewById(R.id.button);
        plusBtn = findViewById(R.id.floatingActionButton);

        plusBtn.setOnClickListener(new View.OnClickListener() {
            @Override
            public void onClick(View v) {
                db.addVocab("Hallo","Hello");
            }
        });

        lerneBtn.setOnClickListener(new View.OnClickListener() {
            @Override
            public void onClick(View v) {
                vokabelText.setText(db.readGerman(1));

            }
        });

        ViewCompat.setOnApplyWindowInsetsListener(findViewById(R.id.main), (v, insets) -> {
            Insets systemBars = insets.getInsets(WindowInsetsCompat.Type.systemBars());
            v.setPadding(systemBars.left, systemBars.top, systemBars.right, systemBars.bottom);
            return insets;
        });
    }
}
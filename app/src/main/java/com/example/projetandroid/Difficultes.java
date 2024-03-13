package com.example.projetandroid;

import android.content.Intent;
import android.os.Bundle;
import android.view.View;
import android.widget.Button;

import androidx.activity.EdgeToEdge;
import androidx.appcompat.app.AppCompatActivity;
import androidx.core.graphics.Insets;
import androidx.core.view.ViewCompat;
import androidx.core.view.WindowInsetsCompat;

public class Difficultes extends AppCompatActivity {

    public static final String DIFFICULTE = "difficulte";
    public static String NIVEAU = "";

    @Override
    protected void onCreate(Bundle savedInstanceState) {
        super.onCreate(savedInstanceState);
        EdgeToEdge.enable(this);
        setContentView(R.layout.activity_difficultes);
        ViewCompat.setOnApplyWindowInsetsListener(findViewById(R.id.main), (v, insets) -> {
            Insets systemBars = insets.getInsets(WindowInsetsCompat.Type.systemBars());
            v.setPadding(systemBars.left, systemBars.top, systemBars.right, systemBars.bottom);
            return insets;
        });
    }

    public void facile(View view) {
        Intent intent = new Intent(this, Niveau.class);
        Button boutonFacile = (Button) view;
        String difficulte = boutonFacile.getText().toString();

        intent.putExtra(DIFFICULTE,difficulte);
        intent.putExtra(NIVEAU,"1");
        startActivity(intent);
    }

    public void moyen(View view) {
        Intent intent = new Intent(this, Niveau.class);
        Button boutonMoyen = (Button) view;
        String difficulte = boutonMoyen.getText().toString();

        intent.putExtra(DIFFICULTE,difficulte);
        intent.putExtra(NIVEAU,"1");
        startActivity(intent);
    }

    public void difficile(View view) {
        Intent intent = new Intent(this, Niveau.class);
        Button boutonDifficile = (Button) view;
        String difficulte = boutonDifficile.getText().toString();

        intent.putExtra(DIFFICULTE,difficulte);
        intent.putExtra(NIVEAU,"1");
        startActivity(intent);
    }

    public void retour(View view){
        Intent intent = new Intent(this, MainActivity.class);

        startActivity(intent);
    }
}
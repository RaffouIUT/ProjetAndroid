package com.example.projetandroid;

import android.content.Intent;
import android.os.Bundle;
import android.view.View;
import android.widget.Button;
import android.widget.ImageView;

import androidx.activity.EdgeToEdge;
import androidx.appcompat.app.AppCompatActivity;
import androidx.core.graphics.Insets;
import androidx.core.view.ViewCompat;
import androidx.core.view.WindowInsetsCompat;

public class Scoreboard extends AppCompatActivity {

    public static final String DIFFICULTE = "difficulte";

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
        Intent intent = new Intent(this, ScoreboardFacile.class);
        ImageView boutonFacile = (ImageView) view;
        String difficulte = boutonFacile.getContentDescription().toString();


        intent.putExtra(DIFFICULTE,difficulte);
        startActivity(intent);
    }

    public void moyen(View view) {
        Intent intent = new Intent(this, ScoreboardMoyen.class);
        ImageView boutonMoyen = (ImageView) view;
        String difficulte = boutonMoyen.getContentDescription().toString();
        intent.putExtra(DIFFICULTE,difficulte);
        startActivity(intent);
    }

    public void difficile(View view) {
        Intent intent = new Intent(this, ScoreboardDifficile.class);
        ImageView boutonDifficile = (ImageView) view;
        String difficulte = boutonDifficile.getContentDescription().toString();

        intent.putExtra(DIFFICULTE,difficulte);
        startActivity(intent);
    }

    public void retour(View view){
        Intent intent = new Intent(this, MainActivity.class);

        startActivity(intent);
    }
}
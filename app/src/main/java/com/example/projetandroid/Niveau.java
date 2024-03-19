package com.example.projetandroid;

import static com.example.projetandroid.Difficultes.DIFFICULTE;
import static com.example.projetandroid.Difficultes.NIVEAU;

import android.content.Intent;
import android.os.Bundle;
import android.util.Log;
import android.view.View;
import android.widget.Button;
import android.widget.ImageView;

import androidx.activity.EdgeToEdge;
import androidx.appcompat.app.AppCompatActivity;
import androidx.core.graphics.Insets;
import androidx.core.view.ViewCompat;
import androidx.core.view.WindowInsetsCompat;

public class Niveau extends AppCompatActivity {

    private String difficulte;
    @Override
    protected void onCreate(Bundle savedInstanceState) {
        super.onCreate(savedInstanceState);
        EdgeToEdge.enable(this);
        setContentView(R.layout.activity_niveau);

        ImageView niv1 = findViewById(R.id.niv1);
        ImageView niv2 = findViewById(R.id.niv2);
        ImageView niv3 = findViewById(R.id.niv3);
        ImageView niv4 = findViewById(R.id.niv4);

        Intent intent = getIntent();

        difficulte = intent.getStringExtra(DIFFICULTE);
        NIVEAU = intent.getStringExtra(NIVEAU);

        Button diffChoisie = findViewById(R.id.diffChoisie);
        diffChoisie.setText(difficulte);

        switch (NIVEAU){
            case "2":
                niv1.setVisibility(View.VISIBLE);
                niv2.setVisibility(View.VISIBLE);
                niv3.setVisibility(View.INVISIBLE);
                niv4.setVisibility(View.INVISIBLE);
                break;
            case "3":
                niv1.setVisibility(View.VISIBLE);
                niv2.setVisibility(View.VISIBLE);
                niv3.setVisibility(View.VISIBLE);
                niv4.setVisibility(View.INVISIBLE);
                break;
            case "4":
                niv1.setVisibility(View.VISIBLE);
                niv2.setVisibility(View.VISIBLE);
                niv3.setVisibility(View.VISIBLE);
                niv4.setVisibility(View.VISIBLE);
                break;
            default:
                niv1.setVisibility(View.VISIBLE);
                niv2.setVisibility(View.INVISIBLE);
                niv3.setVisibility(View.INVISIBLE);
                niv4.setVisibility(View.INVISIBLE);
                break;
        }

        ViewCompat.setOnApplyWindowInsetsListener(findViewById(R.id.main), (v, insets) -> {
            Insets systemBars = insets.getInsets(WindowInsetsCompat.Type.systemBars());
            v.setPadding(systemBars.left, systemBars.top, systemBars.right, systemBars.bottom);
            return insets;
        });
    }

    public void retour(View view){
        Intent intent = new Intent(this, Difficultes.class);

        intent.putExtra(DIFFICULTE,difficulte);
        intent.putExtra(NIVEAU,NIVEAU);

        startActivity(intent);
    }

    public void lancerNiveau1(View view){
        Intent intent = new Intent(this, Niveau1.class);

        ImageView niveau = (ImageView) view;
        String niv = niveau.getContentDescription().toString();

        intent.putExtra(DIFFICULTE,difficulte);
        intent.putExtra(NIVEAU,niv);

        startActivity(intent);
    }

    public void lancerNiveau2(View view){
        Intent intent = new Intent(this, Niveau2.class);

        ImageView niveau = (ImageView) view;
        String niv = niveau.getContentDescription().toString();

        intent.putExtra(DIFFICULTE,difficulte);
        intent.putExtra(NIVEAU,niv);

        startActivity(intent);
    }


    public void lancerNiveau3(View view){
        Intent intent = new Intent(this, Niveau3.class);


        ImageView niveau = (ImageView) view;
        String niv = niveau.getContentDescription().toString();
        Log.d("niveau",  "Niv : " + niv);

        intent.putExtra(DIFFICULTE,difficulte);
        intent.putExtra(NIVEAU,niv);

        startActivity(intent);
    }
}
package com.example.projetandroid;

import static com.example.projetandroid.Difficultes.DIFFICULTE;

import android.content.Intent;
import android.os.Bundle;
import android.view.View;
import android.widget.TextView;

import androidx.activity.EdgeToEdge;
import androidx.appcompat.app.AppCompatActivity;
import androidx.core.graphics.Insets;
import androidx.core.view.ViewCompat;
import androidx.core.view.WindowInsetsCompat;

public class ScoreboardDifficile extends AppCompatActivity {

    int scoreLvl1, scoreLvl2, scoreLvl3;
    private String difficulte;

    @Override
    protected void onCreate(Bundle savedInstanceState) {
        super.onCreate(savedInstanceState);
        EdgeToEdge.enable(this);
        setContentView(R.layout.activity_scoreboard_difficile);

        TextView scoreView1 = findViewById(R.id.score1);
        TextView scoreView2 = findViewById(R.id.score2);
        TextView scoreView3 = findViewById(R.id.score3);

        TextView niveauView1 = findViewById(R.id.niveau1);
        TextView niveauView2 = findViewById(R.id.niveau2);
        TextView niveauView3 = findViewById(R.id.niveau3);

        Intent intent = getIntent();
        difficulte = intent.getStringExtra(DIFFICULTE);
        TextView levelNameView = (TextView) findViewById(R.id.difficulty);
        levelNameView.setText(difficulte);

        String keyNiveau1 = "Niveau 1 " + difficulte;
        String keyNiveau2 = "Niveau 2 " + difficulte;
        String keyNiveau3 = "Niveau 3 " + difficulte;

        ScoreInfo scoreInfoLvl1 = ScoreSingleton.getInstance().getScoreInfo(keyNiveau1);
        if(scoreInfoLvl1 != null) {
            scoreLvl1 = scoreInfoLvl1.getScore();
        }

        ScoreInfo scoreInfoLvl2 = ScoreSingleton.getInstance().getScoreInfo(keyNiveau2);
        if(scoreInfoLvl2 != null) {
            scoreLvl2 = scoreInfoLvl2.getScore();
        }

        ScoreInfo scoreInfoLvl3 = ScoreSingleton.getInstance().getScoreInfo(keyNiveau3);
        if(scoreInfoLvl3 != null) {
            scoreLvl3 = scoreInfoLvl3.getScore();
        }

        scoreView1.setText("" + scoreLvl1);
        scoreView2.setText("" + scoreLvl2);
        scoreView3.setText("" + scoreLvl3);

        if(scoreLvl1 == 0){
            scoreView1.setVisibility(TextView.INVISIBLE);
        }
        if(scoreLvl2 == 0){
            scoreView2.setVisibility(TextView.INVISIBLE);
            niveauView2.setVisibility(TextView.INVISIBLE);
        }
        if (scoreLvl3 == 0) {
            scoreView3.setVisibility(TextView.INVISIBLE);
            niveauView3.setVisibility(TextView.INVISIBLE);
        }

        ViewCompat.setOnApplyWindowInsetsListener(findViewById(R.id.main), (v, insets) -> {
            Insets systemBars = insets.getInsets(WindowInsetsCompat.Type.systemBars());
            v.setPadding(systemBars.left, systemBars.top, systemBars.right, systemBars.bottom);
            return insets;
        });
    }

    public void retour(View view){
        Intent intent = new Intent(this, Scoreboard.class);

        startActivity(intent);
    }
}
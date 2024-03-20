package com.example.projetandroid;

import static com.example.projetandroid.Difficultes.DIFFICULTE;
import static com.example.projetandroid.Difficultes.NIVEAU;

import android.app.AlertDialog;
import android.content.DialogInterface;
import android.content.Intent;
import android.os.Bundle;
import android.os.CountDownTimer;
import android.os.Handler;
import android.view.MotionEvent;
import android.view.View;
import android.widget.Button;
import android.widget.ImageView;
import android.widget.TextView;

import androidx.appcompat.app.AppCompatActivity;

public class Niveau2 extends AppCompatActivity implements View.OnTouchListener {

    Button btn;
    ImageView[] imageViews;
    Handler handler;
    CountDownTimer timer;
    long secondsRemaining = 0;
    private String niveau = "";
    private String difficulte = "";
    int points = 1000;

    @Override
    protected void onCreate(Bundle savedInstanceState) {
        super.onCreate(savedInstanceState);
        setContentView(R.layout.activity_niveau2);

        btn = findViewById(R.id.bouton);
        btn.setOnTouchListener(this);

        Intent intent = getIntent();

        difficulte = intent.getStringExtra(DIFFICULTE);
        niveau = intent.getStringExtra(NIVEAU);
        TextView levelNameView = (TextView) findViewById(R.id.levelName);
        levelNameView.setText("Niveau "+niveau);
        ImageView aide = findViewById(R.id.boutonAide);

        if(difficulte.equals("DIFFICILE")){
            aide.setVisibility(View.INVISIBLE);
        }

        imageViews = new ImageView[]{
                findViewById(R.id.imageView),
                findViewById(R.id.imageView2),
                findViewById(R.id.imageView3),
                findViewById(R.id.imageView4),
                findViewById(R.id.imageView5),
                findViewById(R.id.imageView6)
        };

        startTimer();
        handler = new Handler();
    }

    @Override
    public boolean onTouch(View v, MotionEvent event) {
        switch (event.getAction()) {
            case MotionEvent.ACTION_DOWN:
                startDisplayingImages();
                break;
            case MotionEvent.ACTION_UP:
                stopDisplayingImages();
                break;
        }
        return true;
    }

    private void startDisplayingImages() {
        resetImageViewsVisibility();

        handler.postDelayed(new Runnable() {
            int counter = 0;
            @Override
            public void run() {
                if (counter < imageViews.length) {
                    imageViews[counter].setVisibility(View.VISIBLE);
                    counter++;
                    if(counter == imageViews.length) {
                        showSuccessDialog();
                    } else {
                        handler.postDelayed(this, 1000);
                    }
                }
            }
        }, 1000);
    }

    private void stopDisplayingImages() {
        handler.removeCallbacksAndMessages(null);
        resetImageViewsVisibility();
    }

    private void resetImageViewsVisibility() {
        for (ImageView imageView : imageViews) {
            imageView.setVisibility(View.INVISIBLE);
        }
    }

    public void aide(View view){
        AlertDialog.Builder builder = new AlertDialog.Builder(this);
        if(!difficulte.equals("DIFFICILE")){
            // Bouton "Afficher l'aide"
            builder.setPositiveButton("Afficher l'aide", new DialogInterface.OnClickListener() {
                @Override
                public void onClick(DialogInterface dialog, int which) {
                    AlertDialog.Builder builder2 = new AlertDialog.Builder(Niveau2.this);
                    builder2.setTitle("Aide");
                    builder2.setMessage("Ce bouton semble bizarre");
                    secondsRemaining += 15;
                    builder2.setPositiveButton("OK", new DialogInterface.OnClickListener() {
                        @Override
                        public void onClick(DialogInterface dialog, int which) {
                            dialog.dismiss(); // Ferme la boîte de dialogue
                        }
                    });
                    builder2.setCancelable(false);
                    builder2.show();
                    dialog.dismiss(); // Ferme la boîte de dialogue
                }
            });

            // Bouton "Skip le niveau"
            if(difficulte.equals("FACILE")) {
                builder.setNegativeButton("Skip le niveau", new DialogInterface.OnClickListener() {
                    @Override
                    public void onClick(DialogInterface dialog, int which) {
                        Intent intent = new Intent(Niveau2.this, Niveau.class);

                        niveau = String.valueOf(Integer.parseInt(niveau) + 1);

                        intent.putExtra(DIFFICULTE, difficulte);
                        intent.putExtra(NIVEAU, niveau);

                        startActivity(intent);
                        dialog.dismiss(); // Ferme la boîte de dialogue
                    }
                });
            }

            builder.setCancelable(false);
            builder.show();
        }

    }

    private void startTimer() {
        timer = new CountDownTimer(Long.MAX_VALUE, 1000) {
            @Override
            public void onTick(long millisUntilFinished) {
                secondsRemaining++;
            }

            @Override
            public void onFinish() {
                showSuccessDialog();
            }
        };
        timer.start();
    }

    private void showSuccessDialog() {
        AlertDialog.Builder builder = new AlertDialog.Builder(this);
        if(secondsRemaining > 15){
            points = points - ((int) (secondsRemaining)-15) * 10;
            if(points < 100){
                points = 100;
            }
        }
        builder.setTitle("Félicitations !");
        builder.setMessage("Vous avez réussi en " + secondsRemaining + " secondes !\nVous avez " + points + " points !");
        builder.setPositiveButton("OK", new DialogInterface.OnClickListener() {
            @Override
            public void onClick(DialogInterface dialog, int which) {
                dialog.dismiss();
                String key = "Niveau " + niveau + " " + difficulte;
                ScoreSingleton.getInstance().setScore(key, points, difficulte);
                Intent intent = new Intent(Niveau2.this, Niveau.class);

                niveau = String.valueOf(Integer.parseInt(niveau) + 1);

                intent.putExtra(DIFFICULTE,difficulte);
                intent.putExtra(NIVEAU,niveau);

                startActivity(intent);
            }
        });
        builder.setCancelable(false);
        builder.show();
    }

    public void goBack(View view){
        Intent intent = new Intent(this, Niveau.class);

        intent.putExtra(DIFFICULTE,difficulte);
        intent.putExtra(NIVEAU,niveau);

        startActivity(intent);
    }

    @Override
    protected void onDestroy() {
        super.onDestroy();
        if (timer != null) {
            timer.cancel();
        }
    }
}

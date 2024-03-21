package com.example.projetandroid;

import static com.example.projetandroid.Difficultes.DIFFICULTE;
import static com.example.projetandroid.Difficultes.NIVEAU;

import android.annotation.SuppressLint;
import android.app.AlertDialog;
import android.content.ContentResolver;
import android.content.DialogInterface;
import android.content.Intent;
import android.os.Bundle;
import android.os.CountDownTimer;
import android.util.Log;
import android.view.View;
import android.widget.Button;

import androidx.activity.EdgeToEdge;
import androidx.appcompat.app.AppCompatActivity;
import androidx.core.graphics.Insets;
import androidx.core.view.ViewCompat;
import androidx.core.view.WindowInsetsCompat;

import android.content.Context;
import android.provider.Settings;
import android.widget.ImageView;
import android.widget.TextView;

import java.util.Objects;


public class Niveau3 extends AppCompatActivity implements BrightnessObserver.BrightnessChangeListener {

    private String niveau = "";
    private String difficulte = "";
    private Button winButton;
    private boolean win = false;
    private ImageView gelView;

    private BrightnessObserver brightnessObserver;


    CountDownTimer timer;
    long secondsRemaining = 0;

    int points = 1000;




    @SuppressLint("ClickableViewAccessibility")
    @Override
    protected void onCreate(Bundle savedInstanceState) {
        super.onCreate(savedInstanceState);
        EdgeToEdge.enable(this);
        setContentView(R.layout.activity_niveau3);

        winButton = findViewById(R.id.button_win);
        winButton.setEnabled(false);
        gelView = findViewById(R.id.view_gel);

        Intent intent = getIntent();

        difficulte = intent.getStringExtra(DIFFICULTE);
        niveau = intent.getStringExtra(NIVEAU);
        TextView levelNameView = (TextView) findViewById(R.id.levelName);
        levelNameView.setText("Niveau "+niveau);
        ImageView aide = findViewById(R.id.boutonAide);


        ImageView diffChoisie = findViewById(R.id.color_text);


        if (Objects.equals(difficulte, "FACILE")) {
            diffChoisie.setImageResource(R.drawable.easy_button);

        } else if (Objects.equals(difficulte, "MOYEN")) {
            diffChoisie.setImageResource(R.drawable.medium_button);
        } else {
            diffChoisie.setImageResource(R.drawable.hard_button);
        }

        if(difficulte.equals("DIFFICILE")){
            aide.setVisibility(View.INVISIBLE);
        }

       /* ContentResolver contentResolver = getContentResolver();
        int brightnessValue = 150;
        Settings.System.putInt(contentResolver, Settings.System.SCREEN_BRIGHTNESS, brightnessValue);
        */

        brightnessObserver = new BrightnessObserver(this, this);
        getContentResolver().registerContentObserver(Settings.System.getUriFor(Settings.System.SCREEN_BRIGHTNESS), true, brightnessObserver);

        ViewCompat.setOnApplyWindowInsetsListener(findViewById(R.id.main), (v, insets) -> {
            Insets systemBars = insets.getInsets(WindowInsetsCompat.Type.systemBars());
            v.setPadding(systemBars.left, systemBars.top, systemBars.right, systemBars.bottom);
            return insets;
        });
        startTimer();
    }

    @Override
    public void onBrightnessChanged(int brightness) {

        if (brightness >= 200) {
            winButton.setEnabled(true);
            gelView.setVisibility(View.INVISIBLE);


        }
    }

    public void aide(View view){
        AlertDialog.Builder builder = new AlertDialog.Builder(this);
        if(!difficulte.equals("DIFFICILE")){
            // Bouton "Afficher l'aide"
            builder.setPositiveButton("Afficher l'aide", new DialogInterface.OnClickListener() {
                @Override
                public void onClick(DialogInterface dialog, int which) {
                    AlertDialog.Builder builder2 = new AlertDialog.Builder(Niveau3.this);
                    builder2.setTitle("Aide");
                    builder2.setMessage("Il fait un peu sombre ici");
                    secondsRemaining += 15;
                    builder2.setPositiveButton("OK", new DialogInterface.OnClickListener() {
                        @Override
                        public void onClick(DialogInterface dialog, int which) {
                            dialog.dismiss();
                        }
                    });
                    builder2.setCancelable(false);
                    builder2.show();
                    dialog.dismiss();
                }
            });


            if(difficulte.equals("FACILE")) {
                builder.setNegativeButton("Skip le niveau", new DialogInterface.OnClickListener() {
                    @Override
                    public void onClick(DialogInterface dialog, int which) {
                        Intent intent = new Intent(Niveau3.this, Niveau.class);

                        niveau = String.valueOf(Integer.parseInt(niveau));

                        intent.putExtra(DIFFICULTE, difficulte);
                        intent.putExtra(NIVEAU, niveau);

                        startActivity(intent);
                        dialog.dismiss();
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

            }
        };
        timer.start();
    }


    public void showSuccessDialog(View view) {
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
                Intent intent = new Intent(Niveau3.this, Niveau.class);

                niveau = String.valueOf(Integer.parseInt(niveau));

                intent.putExtra(DIFFICULTE,difficulte);
                intent.putExtra(NIVEAU,niveau);

                startActivity(intent);
            }
        });
        builder.setCancelable(false);
        builder.show();
    }

    @Override
    protected void onDestroy() {
        super.onDestroy();
        if (timer != null) {
            timer.cancel();
        }
    }

    public void goBack(View view) {
        Intent intent = new Intent(this, Difficultes.class);

        intent.putExtra(DIFFICULTE,difficulte);
        intent.putExtra(NIVEAU,niveau);

        startActivity(intent);
    }

}

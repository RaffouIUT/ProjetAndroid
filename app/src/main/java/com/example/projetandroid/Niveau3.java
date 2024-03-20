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


    //winButton = findViewById(R.id.button_win);
    //gelView = findViewById(R.id.view_gel);
    @SuppressLint("ClickableViewAccessibility")
    @Override
    protected void onCreate(Bundle savedInstanceState) {
        super.onCreate(savedInstanceState);
        EdgeToEdge.enable(this);
        setContentView(R.layout.activity_niveau3);

        winButton = findViewById(R.id.button_win);
        winButton.setEnabled(false);
        gelView = findViewById(R.id.view_gel);

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
        // Votre logique pour réagir au changement de luminosité ici
        if (brightness >= 200) {
            winButton.setEnabled(true);
            gelView.setVisibility(View.INVISIBLE);


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
                Intent intent = new Intent(Niveau3.this, Niveau.class);

                niveau = String.valueOf(Integer.parseInt(niveau) + 1);

                intent.putExtra(DIFFICULTE,difficulte);
                intent.putExtra(NIVEAU,niveau);

                startActivity(intent);
            }
        });
        builder.setCancelable(false);
        builder.show();
    }



    public void goBack(View view) {
        Intent intent = new Intent(this, Difficultes.class);

        startActivity(intent);
    }

    public void nextLevel(View view) {
        Intent intent = new Intent(this, Niveau.class);

        int niv = Integer.parseInt(niveau) + 1;
        niveau = String.valueOf(niv);

        intent.putExtra(DIFFICULTE, difficulte);
        intent.putExtra(NIVEAU, niveau);

        startActivity(intent);
    }
}






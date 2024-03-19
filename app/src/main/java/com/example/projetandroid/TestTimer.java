package com.example.projetandroid;

import android.os.Bundle;
import android.os.Handler;
import android.view.View;
import android.widget.Button;
import android.widget.TextView;

import androidx.activity.EdgeToEdge;
import androidx.appcompat.app.AppCompatActivity;
import androidx.core.graphics.Insets;
import androidx.core.view.ViewCompat;
import androidx.core.view.WindowInsetsCompat;

public class TestTimer extends AppCompatActivity {

    TextView text;
    Button lancer, pause;
    Runnable runnable;
    int seconde = 0;
    boolean clique, stop, annuler;

    @Override
    protected void onCreate(Bundle savedInstanceState) {
        super.onCreate(savedInstanceState);
        EdgeToEdge.enable(this);
        setContentView(R.layout.activity_test_timer);

        text = findViewById(R.id.texte);
        lancer = findViewById(R.id.lancer);
        pause = findViewById(R.id.pause);
        pause.setVisibility(View.INVISIBLE);

        runnable = new Runnable() {
            @Override
            public void run() {
                seconde += 1;

                if(!annuler){
                    if(!stop){
                        new Handler().postDelayed(runnable, 1000);
                    }
                } else {
                    seconde = 0;
                }

                text.setText(seconde + "s");
            }
        };

        lancer.setOnClickListener(new View.OnClickListener() {
            @Override
            public void onClick(View v) {
                if(!clique){
                    seconde = 0;
                    new Handler().postDelayed(runnable, 1000);
                    stop = false;
                    annuler = false;
                    clique = true;
                    pause.setVisibility(View.VISIBLE);
                    lancer.setText("Annuler");
                } else {
                    annuler = true;
                    clique = false;
                    pause.setVisibility(View.INVISIBLE);
                    lancer.setText("Lancer");
                }
            }
        });

        pause.setOnClickListener(new View.OnClickListener() {
            @Override
            public void onClick(View v) {
                if(!stop){
                    stop = true;
                    pause.setText("Reprendre");
                } else {
                    stop = false;
                    pause.setText("Pause");
                    new Handler().postDelayed(runnable, 1000);
                }
            }
        });

        ViewCompat.setOnApplyWindowInsetsListener(findViewById(R.id.main), (v, insets) -> {
            Insets systemBars = insets.getInsets(WindowInsetsCompat.Type.systemBars());
            v.setPadding(systemBars.left, systemBars.top, systemBars.right, systemBars.bottom);
            return insets;
        });
    }

}
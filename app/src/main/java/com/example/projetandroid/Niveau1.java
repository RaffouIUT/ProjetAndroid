package com.example.projetandroid;

import static com.example.projetandroid.Difficultes.DIFFICULTE;
import static com.example.projetandroid.Difficultes.NIVEAU;

import android.content.Intent;
import android.os.Bundle;
import android.view.View;
import android.widget.ImageView;
import android.widget.TextView;



import androidx.activity.EdgeToEdge;
import androidx.appcompat.app.AppCompatActivity;
import androidx.core.graphics.Insets;
import androidx.core.view.ViewCompat;
import androidx.core.view.WindowInsetsCompat;

import android.view.MotionEvent;




public class Niveau1 extends AppCompatActivity {

    private String niveau = "";
    private String difficulte = "";

    private float x, y; // Variables pour stocker les coordonnées de la balle
    private ImageView ball; // Renommer l'imageView en "ball"

    private ImageView bucket;




    @Override
    protected void onCreate(Bundle savedInstanceState) {
        super.onCreate(savedInstanceState);
        EdgeToEdge.enable(this);
        setContentView(R.layout.activity_niveau1);

        Intent intent = getIntent();

        difficulte = intent.getStringExtra(DIFFICULTE);
        niveau = intent.getStringExtra(NIVEAU);
        TextView levelNameView = (TextView) findViewById(R.id.levelName);
        bucket = findViewById(R.id.bucket);

        String levelName = "Level 1";
        levelNameView.setText(levelName);

        // Ball
        ball = findViewById(R.id.ball);
        ball.setVisibility(View.INVISIBLE);

        // Ajouter un écouteur de toucher à l'imageView
        ball.setOnTouchListener(new View.OnTouchListener() {
            @Override
            public boolean onTouch(View v, MotionEvent event) {
                switch (event.getAction()) {
                    case MotionEvent.ACTION_DOWN:
                        // Lorsque l'utilisateur appuie sur l'écran
                        x = event.getX();
                        y = event.getY();
                        break;
                    case MotionEvent.ACTION_MOVE:
                        // Lorsque l'utilisateur fait glisser son doigt
                        float dx = event.getX() - x;
                        float dy = event.getY() - y;

                        // Déplacer l'imageView en fonction du déplacement du doigt
                        ball.setX(ball.getX() + dx);
                        ball.setY(ball.getY() + dy);

                        // Mettre à jour les nouvelles coordonnées de la balle
                        x = event.getX();
                        y = event.getY();



                        // Vérifier si la balle quitte le seau
                        if (!isViewOverlapping(ball, bucket)) {
                            // Si la balle quitte le seau, la rendre visible
                            ball.setVisibility(View.VISIBLE);
                            //ball.setVisibility(View.INVISIBLE);
                        }

                        break;
                }
                return true; // Retourner true pour indiquer que l'événement a été consommé
            }
        });



        ViewCompat.setOnApplyWindowInsetsListener(findViewById(R.id.main), (v, insets) -> {
            Insets systemBars = insets.getInsets(WindowInsetsCompat.Type.systemBars());
            v.setPadding(systemBars.left, systemBars.top, systemBars.right, systemBars.bottom);
            return insets;
        });
    }



    private boolean isViewOverlapping(View firstView, View secondView) {
        int[] firstPosition = new int[2];
        int[] secondPosition = new int[2];

        firstView.getLocationOnScreen(firstPosition);
        secondView.getLocationOnScreen(secondPosition);

        int firstViewLeft = firstPosition[0];
        int firstViewTop = firstPosition[1];
        int firstViewRight = firstViewLeft + firstView.getWidth();
        int firstViewBottom = firstViewTop + firstView.getHeight();

        int secondViewLeft = secondPosition[0];
        int secondViewTop = secondPosition[1];
        int secondViewRight = secondViewLeft + secondView.getWidth();
        int secondViewBottom = secondViewTop + secondView.getHeight();

        return !(firstViewLeft > secondViewRight || firstViewRight < secondViewLeft || firstViewTop > secondViewBottom || firstViewBottom < secondViewTop);
    }

    public void goBack(View view){
        Intent intent = new Intent(this, Difficultes.class);

        startActivity(intent);
    }

    public void nextLevel(View view){
        Intent intent = new Intent(this, Niveau.class);

        int niv = Integer.parseInt(niveau) + 1;
        niveau = String.valueOf(niv);

        intent.putExtra(DIFFICULTE,difficulte);
        intent.putExtra(NIVEAU,niveau);

        startActivity(intent);
    }
}
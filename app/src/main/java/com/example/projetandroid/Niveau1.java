package com.example.projetandroid;

import static com.example.projetandroid.Difficultes.DIFFICULTE;
import static com.example.projetandroid.Difficultes.NIVEAU;

import android.annotation.SuppressLint;
import android.content.Intent;
import android.graphics.Point;
import android.graphics.PointF;
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
import android.util.Log;





public class Niveau1 extends AppCompatActivity {

    private String niveau = "";
    private String difficulte = "";

    private float x, y; // Variables pour stocker les coordonnées de la balle
    private ImageView ball; // Renommer l'imageView en "ball"
    private ImageView hoopfront;
    private View bucket;



    private PointF hoop_l;
    private PointF hoop_r;

    private PointF ball_t;
    private PointF ball_b;

    private boolean top_check = false;
    private boolean bottom_check = false ;

    private float hoop_y;

    private float ball_radius;






    @SuppressLint("ClickableViewAccessibility")
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
        ball_radius = (float) ball.getHeight() / 2;
        ball.setVisibility(View.VISIBLE);

        ball_t = new PointF(ball.getX(), ball.getY() - ball_radius);
        ball_b = new PointF(ball.getX(), ball.getY() + ball_radius);





        hoopfront = findViewById(R.id.hoopfront);

        hoop_y = Math.round(hoopfront.getY()) - 62;

        hoop_l = new PointF(Math.round(hoopfront.getX()) - 63, hoop_y);
        hoop_r = new PointF(Math.round(hoopfront.getX()) + 63, hoop_y);





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

                        // point sur balle
                        ball_t.y = ball.getY() - ball_radius;
                        ball_b.y = ball.getY() + ball_radius;



                        // Mettre à jour les nouvelles coordonnées de la balle
                        x = event.getX();
                        y = event.getY();


                        //Log.d("Y",  "Y : " + ball.getY() );
                        //Log.d("Y",  "X : " + ball.getX() );

                        Log.d("LEFT",  "X : " + hoop_l.x );
                        Log.d("LEFT",  "Y : " + hoop_l.y );

                        Log.d("RIGHT",  "X : " + hoop_r.x );
                        Log.d("RIGHT",  "Y : " + hoop_r.y);



                        if ( hoop_l.y == Math.round(ball_b.y) && Math.round(ball_b.x) < hoop_r.x && Math.round(ball_b.x) > hoop_l.x ){
                            Log.d("test1",  "1 est validé : " );
                            bottom_check = true;

                        }

                        if ( hoop_l.y == Math.round(ball_t.y) && Math.round(ball_t.x) < hoop_r.x && Math.round(ball_t.x) > hoop_l.x ){
                            Log.d("test2",  "2 est validé : " );
                            top_check = true;
                        }


                        if (top_check && bottom_check){
                            Log.d("TAG", "Validéeeeeee");

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
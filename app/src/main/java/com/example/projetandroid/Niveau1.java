package com.example.projetandroid;

import static com.example.projetandroid.Difficultes.DIFFICULTE;
import static com.example.projetandroid.Difficultes.NIVEAU;

import android.app.AlertDialog;
import android.content.DialogInterface;
import android.annotation.SuppressLint;
import android.content.Intent;
import android.graphics.PointF;
import android.os.Bundle;
import android.os.CountDownTimer;
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

import java.util.Objects;


public class Niveau1 extends AppCompatActivity{

    private String niveau = "";
    private String difficulte = "";

    private float x, y;
    private ImageView ball;
    private ImageView hoopfront;
    private View bucket;

    private float offset = 50;



    private PointF hoop_l;
    private PointF hoop_r;

    private PointF ball_t;
    private PointF ball_b;

    private boolean top_check = false;
    private boolean bottom_check = false ;

    private boolean ball_bottom_entered = false;
    private boolean ball_top_entered = false;

    private float hoop_y;

    private float ball_radius;



    CountDownTimer timer;
    long secondsRemaining = 0;

    int points = 1000;




    @Override
    protected void onCreate(Bundle savedInstanceState) {


        super.onCreate(savedInstanceState);
        EdgeToEdge.enable(this);
        setContentView(R.layout.activity_niveau1);


        ViewCompat.setOnApplyWindowInsetsListener(findViewById(R.id.main), (v, insets) -> {
            Insets systemBars = insets.getInsets(WindowInsetsCompat.Type.systemBars());
            v.setPadding(systemBars.left, systemBars.top, systemBars.right, systemBars.bottom);
            return insets;
        });
        startTimer();
    }

    @SuppressLint("ClickableViewAccessibility")
    @Override
    protected void onStart() {
        super.onStart();


        Intent intent = getIntent();

        difficulte = intent.getStringExtra(DIFFICULTE);
        niveau = intent.getStringExtra(NIVEAU);
        TextView levelNameView = (TextView) findViewById(R.id.levelName);
        levelNameView.setText("Niveau "+niveau);
        ImageView aide = findViewById(R.id.boutonAide);

        if(difficulte.equals("DIFFICILE")){
            aide.setVisibility(View.INVISIBLE);
        }

        bucket = findViewById(R.id.bucket);

        // Ball
        ball = findViewById(R.id.ball);

        ball.post(new Runnable() {
            @Override
            public void run() {
                ball_radius = (float) ball.getHeight() / 2;
                ball.setVisibility(View.VISIBLE);

                int[] location = new int[2];
                ball.getLocationOnScreen(location);

                int ball_x = location[0];
                int ball_y = location[1];

                ball_t = new PointF(ball.getX(), ball.getY() - ball_radius);
                ball_b = new PointF(ball.getX(), ball.getY() + ball_radius);
            }
        });








        hoopfront = findViewById(R.id.hoopfront);

        hoopfront.post(new Runnable() {
            @Override
            public void run() {
                hoop_y = Math.round(hoopfront.getY()) - 62;

                hoop_l = new PointF(Math.round(hoopfront.getX()) - 63, hoop_y);
                hoop_r = new PointF(Math.round(hoopfront.getX()) + 63, hoop_y);

            }
        });






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

                        //Log.d("LEFT",  "X : " + hoop_l.x );
                        //Log.d("LEFT",  "Y : " + hoop_l.y );

                        //Log.d("RIGHT",  "X : " + hoop_r.x );
                        //Log.d("RIGHT",  "Y : " + hoop_r.y);

                        //Log.d("BALL",  "Y : " + Math.round(ball_b.y));







                        if (!ball_bottom_entered && checkPassThroughRim(ball.getX() - 63, ball_b.y)) {
                            ball_bottom_entered = true;
                            bottom_check = !bottom_check;
                            Log.d("BALL",  "bottom check: " + bottom_check);
                        }
                        if (ball_bottom_entered && !checkPassThroughRim(ball.getX() - 63, ball_b.y)) {
                            ball_bottom_entered = false;
                        }

                        if (!ball_top_entered && checkPassThroughRim(ball.getX() - 63, ball_t.y)) {
                            ball_top_entered = true;
                            top_check = !top_check;
                            Log.d("BALL",  "top check: " + top_check);

                        }
                        if (ball_top_entered && !checkPassThroughRim(ball.getX() - 63, ball_t.y)) {
                            ball_top_entered = false;
                            if (!bottom_check) {
                                top_check = false;
                            }
                        }


                        if (top_check && bottom_check){
                            showSuccessDialog();

                        }




                        break;
                }
                return true; // Retourner true pour indiquer que l'événement a été consommé
            }

        // Code à exécuter lorsque l'activité devient visible à l'utilisateur
        // Par exemple, vous pouvez effectuer des opérations comme charger des données, démarrer des services, etc.
        // Assurez-vous de ne pas exécuter de code intensif en ressources ici pour ne pas ralentir l'ouverture de l'activité.
        });
    }






    private boolean checkPassThroughRim(float x, float y) {

        //Log.d("BALL",  "Verifclass: ");

        //Log.d("Raw X",  "X : " + x );
        //Log.d("Raw Y",  "Y : " + y );


        //Log.d("LEFT",  "X : " + hoop_l.x );
        //Log.d("LEFT",  "Y : " + hoop_l.y );

        //Log.d("RIGHT",  "X : " + hoop_r.x );
        //Log.d("RIGHT",  "Y : " + hoop_r.y);

        if (y > hoop_l.y && y < hoop_l.y + offset && x > hoop_l.x && x < hoop_r.x){
            //Log.d("BALoiuklthrhthgtrhgtrhtrhtrhtrhtrhrtL",  "Ver if: ");


            return true;

        }
        return false;
    }

    public void aide(View view){
        AlertDialog.Builder builder = new AlertDialog.Builder(this);
        if(!difficulte.equals("DIFFICILE")){
            // Bouton "Afficher l'aide"
            builder.setPositiveButton("Afficher l'aide", new DialogInterface.OnClickListener() {
                @Override
                public void onClick(DialogInterface dialog, int which) {
                    AlertDialog.Builder builder2 = new AlertDialog.Builder(Niveau1.this);
                    builder2.setTitle("Aide");
                    builder2.setMessage("Le seau ne semble pas vide");
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
                        Intent intent = new Intent(Niveau1.this, Niveau.class);

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
                Intent intent = new Intent(Niveau1.this, Niveau.class);

                niveau = String.valueOf(Integer.parseInt(niveau) + 1);

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

    public void goBack(View view){
        Intent intent = new Intent(this, Niveau.class);

        intent.putExtra(DIFFICULTE,difficulte);
        intent.putExtra(NIVEAU,niveau);

        startActivity(intent);
    }

}
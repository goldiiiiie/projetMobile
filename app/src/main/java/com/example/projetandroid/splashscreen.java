package com.example.projetandroid;

import androidx.appcompat.app.AppCompatActivity;

import android.content.Intent;
import android.media.Image;
import android.os.Bundle;
import android.os.Handler;
import android.view.animation.Animation;
import android.view.animation.AnimationUtils;
import android.widget.ImageView;
import android.widget.ProgressBar;

public class splashscreen extends AppCompatActivity {
    ImageView logoimage;
    ProgressBar progress;
    Animation logoanim;

    @Override
    protected void onCreate(Bundle savedInstanceState) {
        super.onCreate(savedInstanceState);
        setContentView(R.layout.activity_splashscreen);

        //1-get view by id
            //get the image view du fichier splash screen.xml
        logoimage=findViewById(R.id.logo);
            //get the progress bar
        progress=findViewById(R.id.progressBar);
        //2-appliquer l animation sur le logo
        logoanim=AnimationUtils.loadAnimation(splashscreen.this,R.anim.logo_animation);
        //3-appliquer animation sut l image
        //logoimage.animate(logoanim);
        //logoimage.setAnimation(logoanim);
        logoimage.startAnimation(logoanim);
        //4-appliquer le delai avant la navigation au l activite suivante
        new Handler().postDelayed(new Runnable() {
            @Override
            public void run() { //creation d un objet intent
                Intent i=new Intent(splashscreen.this, Login.class);
                //Start activity
                startActivity(i);
                //destruction de l premiere activite
                finish();


            }
        },5000);

    }
}
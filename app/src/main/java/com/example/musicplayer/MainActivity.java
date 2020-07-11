package com.example.musicplayer;

import androidx.appcompat.app.AppCompatActivity;

import android.content.Intent;
import android.media.MediaPlayer;
import android.os.Bundle;
import android.os.Handler;
import android.view.View;
import android.widget.Button;
import android.widget.ImageButton;
import android.widget.ImageView;
import android.widget.SeekBar;
import android.widget.TextView;
import android.widget.Toast;

import java.util.concurrent.TimeUnit;

public class MainActivity extends AppCompatActivity {
    private ImageView imageView;
   private ImageButton playbutton,pausebutton,forwardbutton,Backwardbutton;
   private TextView songstarttime,songendtime;
   private SeekBar seekbarprogress;
   private  static  int starttime=0,endtime=0,ontimeonly=0,forwardtime=5000,backwardtime=5000;
   private Handler handler=new Handler();
   private MediaPlayer mediaPlayer;


    @Override
    protected void onCreate(Bundle savedInstanceState) {
        super.onCreate(savedInstanceState);
        setContentView(R.layout.activity_main);

        playbutton = findViewById(R.id.play);
        pausebutton = findViewById(R.id.pause);
        forwardbutton = findViewById(R.id.forward);
        Backwardbutton = findViewById(R.id.backward);
        seekbarprogress = findViewById(R.id.seek);
        songstarttime = findViewById(R.id.songstarttime);
        songendtime = findViewById(R.id.songendtime);
        mediaPlayer = MediaPlayer.create(this, R.raw.sariyagi);
        pausebutton.setEnabled(false);
       seekbarprogress.setClickable(true);





        playbutton.setOnClickListener(new View.OnClickListener() {
            @Override
            public void onClick(View v) {
                mediaPlayer.start();
                starttime = mediaPlayer.getCurrentPosition();
                endtime = mediaPlayer.getDuration();
                if (ontimeonly == 0) {
                    seekbarprogress.setMax(endtime);
                    ontimeonly = 1;
                }
                // set the songstarttime and songendtime
                // set the songstarttime
                songstarttime.setText(String.format("%d min, %d sec", TimeUnit.MILLISECONDS.toMinutes(endtime),
                        TimeUnit.MILLISECONDS.toSeconds(endtime) - TimeUnit.MINUTES.toSeconds(TimeUnit.MILLISECONDS. toMinutes(endtime))) );
                songendtime.setText(String.format("%d min, %d sec", TimeUnit.MILLISECONDS.toMinutes(starttime),
                        TimeUnit.MILLISECONDS.toSeconds(starttime) - TimeUnit.MINUTES.toSeconds(TimeUnit.MILLISECONDS. toMinutes(starttime))) );
                //update the seekbar
                seekbarprogress.setProgress(starttime);
                handler.postDelayed(Updatesong, 100);
                pausebutton.setEnabled(true);
                playbutton.setEnabled(false);


            }


        });
        forwardbutton.setOnClickListener(new View.OnClickListener() {
            @Override
            public void onClick(View v) {
                if ((starttime + forwardtime) <= endtime) {
                    starttime = starttime + forwardtime;
                    mediaPlayer.seekTo(starttime);
                } else {
                    Toast.makeText(getApplicationContext(), "sorry maximum song limit reached", Toast.LENGTH_SHORT).show();
                }
                if (!playbutton.isEnabled()) {
                    playbutton.setEnabled(true);
                }

            }
        });

       pausebutton.setOnClickListener(new View.OnClickListener() {
           @Override
           public void onClick(View v) {
               mediaPlayer.pause();;
               pausebutton.setEnabled(false);
               playbutton.setEnabled(true);
               Toast.makeText(getApplicationContext(),"music paused",Toast.LENGTH_LONG).show();
           }
       });

       Backwardbutton.setOnClickListener(new View.OnClickListener() {
           @Override
           public void onClick(View v) {
               if((starttime-backwardtime)>0)
               {
                   starttime=starttime-backwardtime;
                   mediaPlayer.seekTo(starttime);
               }
               else
               {
                   Toast.makeText(getApplicationContext(),"sorry no song ",Toast.LENGTH_SHORT).show();
               }
               if(!playbutton.isEnabled())
               {
                   playbutton.setEnabled(true);
               }

           }
       });



    }

    private  Runnable Updatesong=new Runnable() {
        @Override
        public void run()
        {
            //set the start time
            starttime=mediaPlayer.getCurrentPosition();
            songstarttime.setText(String.format("%d min, %d sec", TimeUnit.MILLISECONDS.toMinutes(starttime),
                    TimeUnit.MILLISECONDS.toSeconds(starttime) - TimeUnit.MINUTES.toSeconds(TimeUnit.MILLISECONDS.toMinutes(starttime))) );
            // set the songendtime

            seekbarprogress.setProgress(starttime);
            handler.postDelayed(this,100);

        }
    };
}


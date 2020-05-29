package cn.loberty.voicemikedemo;

import androidx.appcompat.app.AppCompatActivity;

import android.os.Bundle;

import java.util.Random;
import java.util.Timer;
import java.util.TimerTask;

public class MainActivity extends AppCompatActivity {

    private VoiceMikeView voiceMikeView;

    @Override
    protected void onCreate(Bundle savedInstanceState) {
        super.onCreate(savedInstanceState);
        setContentView(R.layout.activity_main);
        voiceMikeView = findViewById(R.id.voice_mike_view);
        downTime();
    }

    private void downTime(){
        Timer timer = new Timer();
        timer.schedule(new TimerTask() {
            @Override
            public void run() {
                runOnUiThread(new Runnable() {
                    @Override
                    public void run() {
                        int x = new Random().nextInt(30);
                        voiceMikeView.setLevel(x);
                    }
                });
            }
        },1000,200);
    }
}

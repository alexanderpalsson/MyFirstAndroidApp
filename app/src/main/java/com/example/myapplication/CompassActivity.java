package com.example.myapplication;
import android.graphics.Color;
import android.support.v7.app.AppCompatActivity;
import android.os.Bundle;
import android.view.View;
import android.widget.RelativeLayout;
import android.hardware.Sensor;
import android.hardware.SensorManager;
import android.hardware.SensorEvent;
import android.hardware.SensorEventListener;
import android.widget.TextView;
import android.content.Intent;
import android.view.animation.Animation;
import android.view.animation.RotateAnimation;
import android.widget.ImageView;
import android.content.Context;
import android.os.Vibrator;
import android.media.MediaPlayer;

public class CompassActivity extends AppCompatActivity implements SensorEventListener, View.OnClickListener {
    // define the display assembly compass picture
    private ImageView image;

    // record the compass picture angle turned
    private float currentDegree = 0f;

    // device sensor manager
    private SensorManager mSensorManager;

    TextView tvHeading;
    boolean hasLeft = true;
    MediaPlayer m;
    Vibrator vib;

    @Override
    protected void onCreate(Bundle savedInstanceState) {
        super.onCreate(savedInstanceState);
        setContentView(R.layout.activity_comp);
        View v = findViewById(R.id.button1337);
        v.setOnClickListener(this);
        m = MediaPlayer.create(this, R.raw.kingofnorth);
        vib = (Vibrator) getSystemService(Context.VIBRATOR_SERVICE);




        // our compass image
        image = (ImageView) findViewById(R.id.imageViewCompass);

        // TextView that will tell the user what degree is he heading
        tvHeading = (TextView) findViewById(R.id.tvHeading);

        // initialize your android device sensor capabilities
        mSensorManager = (SensorManager) getSystemService(SENSOR_SERVICE);
    }

    @Override
    protected void onResume() {
        super.onResume();

        // for the system's orientation sensor registered listeners
        mSensorManager.registerListener(this, mSensorManager.getDefaultSensor(Sensor.TYPE_ORIENTATION),
                SensorManager.SENSOR_DELAY_GAME);
    }

    @Override
    protected void onPause() {
        super.onPause();

        // to stop the listener and save battery
        mSensorManager.unregisterListener(this);
    }

    @Override
    public void onSensorChanged(SensorEvent event) {
        // get the angle around the z-axis rotated
        float degree = Math.round(event.values[0]);
        RelativeLayout ll = findViewById(R.id.comLL);
        tvHeading.setText("Heading: " + Float.toString(degree) + " degrees");
        if((degree < 15 || degree > 345) && hasLeft){
            hasLeft = false;

            // Get instance of Vibrator from current Context

             vib.vibrate(100000);
            ll.setBackgroundColor(Color.RED);
            m.start();
        }
        if(degree > 15 && degree < 345 && !hasLeft){
            ll.setBackgroundColor(Color.WHITE);
            m.stop();
            vib.cancel();
            m = MediaPlayer.create(this, R.raw.kingofnorth);
            hasLeft = true;
        }
        RotateAnimation ra = new RotateAnimation(
                currentDegree,
                -degree,
                Animation.RELATIVE_TO_SELF, 0.5f,
                Animation.RELATIVE_TO_SELF,
                0.5f);

        // how long the animation will take place
        ra.setDuration(100);

        // set the animation after the end of the reservation status
        ra.setFillAfter(true);

        // Start the animation
        image.startAnimation(ra);
        currentDegree = -degree;

    }
    @Override
    public void onClick(View arg0) {
        if (arg0.getId() == R.id.button1337) {
            //define a new Intent for the second Activity
            Intent intent = new Intent(this, MainActivity.class);
            vib.cancel();
            //start the second Activity
            this.startActivity(intent);
        }
    }

    @Override
    public void onAccuracyChanged(Sensor sensor, int accuracy) {
        // not in use
    }


}


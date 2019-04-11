package com.example.myapplication;
import android.app.Activity;
import android.support.v7.app.AppCompatActivity;
import android.os.Bundle;
import android.view.View;
import android.widget.Button;
import android.widget.EditText;
import android.widget.Toast;
import android.hardware.Sensor;
import android.hardware.SensorManager;
import android.hardware.SensorEvent;
import android.hardware.SensorEventListener;
import android.widget.TextView;
import android.content.Intent;
import android.support.v7.app.AppCompatActivity;
import android.os.Bundle;
import android.view.View;
import android.widget.Button;
import android.hardware.SensorEventListener;

public class AccActivity extends AppCompatActivity implements View.OnClickListener, SensorEventListener{
    private TextView xText, yText, zText, rText;
    private Sensor mySensor;
    private SensorManager SM;


    @Override
    public void onCreate(Bundle savedInstanceState) {
        super.onCreate(savedInstanceState);
        setContentView(R.layout.activity_acc);
        View v = findViewById(R.id.button2);
        v.setOnClickListener(this);

        SM = (SensorManager)getSystemService(SENSOR_SERVICE);

        // Accelerometer Sensor
        mySensor = SM.getDefaultSensor(Sensor.TYPE_ACCELEROMETER);

        // Register sensor Listener
        SM.registerListener(this, mySensor, SensorManager.SENSOR_DELAY_NORMAL);

        // Assign TextView
        xText = (TextView)findViewById(R.id.xText);
        yText = (TextView)findViewById(R.id.yText);
        zText = (TextView)findViewById(R.id.zText);
        rText = (TextView)findViewById(R.id.rText);
    }

    @Override
    public void onClick(View arg0) {
        if (arg0.getId() == R.id.button2) {
            //define a new Intent for the second Activity
            Intent intent = new Intent(this, MainActivity.class);

            //start the second Activity
            this.startActivity(intent);
        }
    }

    @Override
    public void onAccuracyChanged(Sensor sensor, int accuracy) {
        // Not in use
    }

    @Override
    public void onSensorChanged(SensorEvent event) {

        xText.setText("X: " + Math.round(event.values[0]));
        yText.setText("Y: " + Math.round(event.values[1]));
        zText.setText("Z: " + Math.round(event.values[2]));
        if (event.values[0] > 0) {
            rText.setText("Riktning: Vänster");
        } else {
            rText.setText("Riktning: Höger");
        }
    }
}


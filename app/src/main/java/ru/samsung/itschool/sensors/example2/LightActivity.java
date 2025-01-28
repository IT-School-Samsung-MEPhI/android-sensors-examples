package ru.samsung.itschool.sensors.example2;

import android.annotation.SuppressLint;
import android.content.Context;
import android.hardware.Sensor;
import android.hardware.SensorEvent;
import android.hardware.SensorEventListener;
import android.hardware.SensorManager;
import android.os.Bundle;

import android.view.WindowManager;
import android.widget.TextView;
import androidx.appcompat.app.AppCompatActivity;
import ru.samsung.itschool.sensors.R;

public class LightActivity extends AppCompatActivity implements SensorEventListener {

    private SensorManager sensorManager;
    private Sensor lightSensor;
    private WindowManager.LayoutParams layoutParams;
    private TextView lightTextView;

    @Override
    protected void onCreate(Bundle savedInstanceState) {
        super.onCreate(savedInstanceState);
        setContentView(R.layout.activity_light);

        sensorManager = (SensorManager) getSystemService(Context.SENSOR_SERVICE);
        if (sensorManager != null) {
            lightSensor = sensorManager.getDefaultSensor(Sensor.TYPE_LIGHT);
        }

        layoutParams = getWindow().getAttributes();

        lightTextView = findViewById(R.id.light_text_view);
    }

    @Override
    protected void onResume() {
        super.onResume();
        if (lightSensor != null) {
            sensorManager.registerListener(this, lightSensor, SensorManager.SENSOR_DELAY_NORMAL);
        }
    }

    @Override
    protected void onPause() {
        super.onPause();
        sensorManager.unregisterListener(this);
    }

    @SuppressLint("SetTextI18n")
    @Override
    public void onSensorChanged(SensorEvent event) {
        if (event.sensor.getType() == Sensor.TYPE_LIGHT) {
            float lightLevel = event.values[0];
            adjustBrightness(lightLevel);
            lightTextView.setText(Float.toString(lightLevel));
        }
    }

    private void adjustBrightness(float lightLevel) {
        layoutParams.screenBrightness = mapLightLevelToBrightness(lightLevel);
        getWindow().setAttributes(layoutParams);
    }

    private float mapLightLevelToBrightness(float lightLevel) {
        float minLight = 10;
        float maxLight = 1000;

        float minBrightness = 0.1f;
        float maxBrightness = 1.0f;

        if (lightLevel < minLight) {
            return minBrightness;
        }  else if (lightLevel > maxLight) {
            return maxBrightness;
        } else {
            float expected = (lightLevel - minLight) / (maxLight - minLight);
            return Math.max(expected, minBrightness);
        }
    }

    @Override
    public void onAccuracyChanged(Sensor sensor, int accuracy) { }
}
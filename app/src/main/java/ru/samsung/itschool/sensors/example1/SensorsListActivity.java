package ru.samsung.itschool.sensors.example1;

import android.content.Context;
import android.hardware.Sensor;
import android.hardware.SensorManager;
import android.os.Bundle;

import android.widget.TextView;
import androidx.appcompat.app.AppCompatActivity;
import java.util.List;
import ru.samsung.itschool.sensors.R;

public class SensorsListActivity extends AppCompatActivity {

    @Override
    protected void onCreate(Bundle savedInstanceState) {
        super.onCreate(savedInstanceState);
        setContentView(R.layout.activity_sensors_list);

        SensorManager sensorManager = (SensorManager) getSystemService(Context.SENSOR_SERVICE);
        List<Sensor> sensors = sensorManager.getSensorList(Sensor.TYPE_ALL);

        StringBuilder sb = new StringBuilder();
        for (Sensor sensor : sensors) {
            sb.append("name = ").append(sensor.getName()).append('\n')
                    .append("type = ").append(sensor.getStringType()).append('\n')
                    .append("vendor = ").append(sensor.getVendor()).append('\n')
                    .append("resolution = ").append(sensor.getResolution()).append('\n')
                    .append('\n');
        }

        ((TextView) findViewById(R.id.text_view)).setText(sb);
    }
}
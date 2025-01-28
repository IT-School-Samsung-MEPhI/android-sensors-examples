package ru.samsung.itschool.sensors;

import android.content.Intent;
import android.os.Bundle;

import android.view.View;
import androidx.appcompat.app.AppCompatActivity;
import ru.samsung.itschool.sensors.example1.SensorsListActivity;
import ru.samsung.itschool.sensors.example2.LightActivity;
import ru.samsung.itschool.sensors.example3.AccelerometerActivity;

public class MainActivity extends AppCompatActivity {

    @Override
    protected void onCreate(Bundle savedInstanceState) {
        super.onCreate(savedInstanceState);
        setContentView(R.layout.activity_main);
    }

    public void openExample1(View view) { openActivity(SensorsListActivity.class); }

    public void openExample2(View view) { openActivity(LightActivity.class); }

    public void openExample3(View view) { openActivity(AccelerometerActivity.class); }

    private void openActivity(Class<?> cls) {
        Intent intent = new Intent(this, cls);
        startActivity(intent);
    }
}
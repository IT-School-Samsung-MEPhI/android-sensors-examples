package ru.samsung.itschool.sensors.example3;

import android.content.Context;
import android.graphics.Canvas;
import android.graphics.Color;
import android.graphics.Paint;
import android.hardware.Sensor;
import android.hardware.SensorEvent;
import android.hardware.SensorEventListener;
import android.hardware.SensorManager;
import android.os.Bundle;

import android.view.View;
import androidx.annotation.NonNull;
import androidx.appcompat.app.AppCompatActivity;

public class AccelerometerActivity extends AppCompatActivity {

    private SensorManager sensorManager;
    private Sensor accelerometer;
    private FieldView fieldView;

    @Override
    protected void onCreate(Bundle savedInstanceState) {
        super.onCreate(savedInstanceState);

        fieldView = new FieldView(this);
        setContentView(fieldView);

        sensorManager = (SensorManager) getSystemService(Context.SENSOR_SERVICE);
        if (sensorManager != null) {
            accelerometer = sensorManager.getDefaultSensor(Sensor.TYPE_ACCELEROMETER);
        }
    }

    @Override
    protected void onResume() {
        super.onResume();
        if (accelerometer != null) {
            sensorManager.registerListener(fieldView, accelerometer, SensorManager.SENSOR_DELAY_GAME);
        }
    }

    @Override
    protected void onPause() {
        super.onPause();
        sensorManager.unregisterListener(fieldView);
    }

    static class FieldView extends View implements SensorEventListener {

        private final Paint ballPaint, goalPaint, textPaint;
        private float ballX, ballY;
        private float goalX1, goalX2, goalY;
        private float screenWidth, screenHeight;
        private boolean goalScored;

        public FieldView(Context context) {
            super(context);

            ballPaint = new Paint();
            ballPaint.setColor(Color.RED);
            ballPaint.setAntiAlias(true);

            goalPaint = new Paint();
            goalPaint.setColor(Color.GREEN);
            goalPaint.setStyle(Paint.Style.STROKE);
            goalPaint.setStrokeWidth(50);

            textPaint = new Paint();
            textPaint.setColor(Color.BLACK);
            textPaint.setTextSize(128);
            textPaint.setTextAlign(Paint.Align.CENTER);
        }

        @Override
        protected void onSizeChanged(int w, int h, int oldw, int oldh) {
            super.onSizeChanged(w, h, oldw, oldh);

            screenWidth = w;
            screenHeight = h;

            ballX = screenWidth / 2;
            ballY = screenHeight / 2;


            float goalWidth = screenWidth / 2;
            goalX1 = (screenWidth - goalWidth) / 2;
            goalX2 = goalX1 + goalWidth;
            goalY = screenHeight - 25;
        }

        @Override
        protected void onDraw(@NonNull Canvas canvas) {
            super.onDraw(canvas);

            canvas.drawColor(Color.WHITE);

            canvas.drawLine(goalX1, goalY, goalX2, goalY, goalPaint);

            float ballRadius = 50f;
            if (ballX - ballRadius < 0)
                ballX = ballRadius;
            if (ballX + ballRadius > screenWidth)
                ballX = screenWidth - ballRadius;
            if (ballY - ballRadius < 0)
                ballY = ballRadius;
            if (ballY + ballRadius > screenHeight)
                ballY = screenHeight - ballRadius;

            canvas.drawCircle(ballX, ballY, ballRadius, ballPaint);

            if (ballY + ballRadius >= goalY && ballX >= goalX1 && ballX <= goalX2) {
                goalScored = true;
            }

            if (goalScored) {
                canvas.drawText("ГОООЛ!", screenWidth / 2, screenHeight / 2, textPaint);
            }

            invalidate();
        }

        @Override
        public void onSensorChanged(SensorEvent event) {
            float multiplier = 4.75f;
            if (event.sensor.getType() == Sensor.TYPE_ACCELEROMETER) {
                ballX -= event.values[0] * multiplier;
                ballY += event.values[1] * multiplier;

                invalidate();
            }
        }

        @Override
        public void onAccuracyChanged(Sensor sensor, int accuracy) { }
    }
}
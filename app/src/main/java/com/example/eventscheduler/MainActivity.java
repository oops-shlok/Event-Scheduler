package com.example.eventscheduler;

import android.app.AlarmManager;
import android.app.PendingIntent;
import android.content.Intent;
import android.os.Bundle;
import android.view.View;
import android.widget.Button;
import android.widget.EditText;
import android.widget.TextView;
import android.widget.TimePicker;
import android.widget.Toast;

import java.util.Calendar;


import androidx.appcompat.app.AppCompatActivity;

public class MainActivity extends AppCompatActivity implements View.OnClickListener {

    private int notificationId = 1;
    private Button setBtn,cancelBtn;
    private TextView textView;

    @Override
    protected void onCreate(Bundle savedInstanceState) {
        super.onCreate(savedInstanceState);
        setContentView(R.layout.activity_main);
        setBtn=findViewById(R.id.set);
        cancelBtn=findViewById(R.id.cancel);
        setBtn.setOnClickListener(this);
        cancelBtn.setOnClickListener(this);
        textView=findViewById(R.id.textView);
    }

    @Override
    public void onClick(View view) {

        EditText editText = findViewById(R.id.edit);
        TimePicker timePicker = findViewById(R.id.time);
        Intent intent = new Intent(MainActivity.this, Time.class);
        intent.putExtra("notificationId", notificationId);
        intent.putExtra("event", editText.getText().toString());
        PendingIntent pendingIntent = PendingIntent.getBroadcast(
                MainActivity.this, 0, intent, PendingIntent.FLAG_CANCEL_CURRENT
        );

        AlarmManager alarmManager = (AlarmManager)getSystemService(ALARM_SERVICE);

        switch (view.getId()) {
            case R.id.set:
                int hour = timePicker.getCurrentHour();
                int minute = timePicker.getCurrentMinute();

                Calendar startTime = Calendar.getInstance();
                startTime.set(Calendar.HOUR_OF_DAY, hour);
                startTime.set(Calendar.MINUTE, minute);
                startTime.set(Calendar.SECOND, 0);
                long alarmStartTime = startTime.getTimeInMillis();

                alarmManager.set(AlarmManager.RTC_WAKEUP, alarmStartTime, pendingIntent);
                Toast.makeText(this, "Event Set!", Toast.LENGTH_SHORT).show();
                textView.setText("Event Pending: " + editText.getText());
                break;

            case R.id.cancel:
                alarmManager.cancel(pendingIntent);
                textView.setText("");
                break;
        }

    }
}
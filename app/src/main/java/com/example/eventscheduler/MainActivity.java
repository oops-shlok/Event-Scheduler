package com.example.eventscheduler;

import androidx.appcompat.app.AppCompatActivity;

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

import java.sql.Time;
import java.util.Calendar;

public class MainActivity extends AppCompatActivity implements View.OnClickListener{

    private int notificationid=1;
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
    public void onClick(View v) {
        EditText editText = findViewById(R.id.edit);
        TimePicker timePicker = findViewById(R.id.time);
        Intent intent = new Intent(MainActivity.this, Time.class);
        intent.putExtra("Notificationid",notificationid);
        intent.putExtra("event",editText.getText().toString());
        PendingIntent remIntent = PendingIntent.getBroadcast(MainActivity.this,0,intent,PendingIntent.FLAG_CANCEL_CURRENT);
        AlarmManager alarmManager = (AlarmManager) getSystemService(ALARM_SERVICE);

        switch(v.getId()){
            case R.id.set:
                int hour = timePicker.getCurrentHour();
                int min = timePicker.getCurrentMinute();

                Calendar start = Calendar.getInstance();
                start.set(Calendar.HOUR_OF_DAY,hour);
                start.set(Calendar.MINUTE,min);
                start.set(Calendar.SECOND,0);
                long settime = start.getTimeInMillis();

                alarmManager.set(AlarmManager.RTC,settime,remIntent);

                Toast.makeText(this, "Event Set", Toast.LENGTH_SHORT).show();
                textView.setText("Event Pending: " + editText.getText());
                break;
            case R.id.cancel:
                alarmManager.cancel(remIntent);
                textView.setText("");
                break;
        }
    }
}
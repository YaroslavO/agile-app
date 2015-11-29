package com.agile.gdpdp.agile;

import android.support.v7.app.AppCompatActivity;
import android.os.Bundle;
import android.view.MotionEvent;
import android.view.View;
import android.widget.EditText;

import com.android.datetimepicker.date.DatePickerDialog;
import com.android.datetimepicker.time.RadialPickerLayout;
import com.android.datetimepicker.time.TimePickerDialog;

import java.text.DateFormat;
import java.text.SimpleDateFormat;
import java.util.Calendar;
import java.util.Locale;

public class CreateSprintActivity extends AppCompatActivity
        implements DatePickerDialog.OnDateSetListener, TimePickerDialog.OnTimeSetListener {

    private static final String TIME_PATTERN = "HH:mm";

    private EditText startSprintDate;
    private EditText startSprintTime;
    private EditText endSprintDate;
    private EditText endSprintTime;
    private Calendar calendar;
    private DateFormat dateFormat;
    private SimpleDateFormat timeFormat;
    private boolean startDate;

    @Override
    protected void onCreate(Bundle savedInstanceState) {
        super.onCreate(savedInstanceState);
        setContentView(R.layout.activity_create_sprint);

        startDate = true;

        calendar = Calendar.getInstance();
        dateFormat = DateFormat.getDateInstance(DateFormat.LONG, Locale.getDefault());
        timeFormat = new SimpleDateFormat(TIME_PATTERN, Locale.getDefault());

        setDateListener();

        update();
    }

    private void setDateListener() {
        startSprintDate = (EditText) findViewById(R.id.startSprintDate);
        setDateTouchListener(startSprintDate, true);

        endSprintDate = (EditText) findViewById(R.id.endSprintDate);
        setDateTouchListener(endSprintDate, false);

        startSprintTime = (EditText) findViewById(R.id.startSprintTime);
        setTimeTouchListener(startSprintTime, true);

        endSprintTime = (EditText) findViewById(R.id.endSprintTime);
        setTimeTouchListener(endSprintTime, false);
    }

    private void setDateTouchListener(EditText dateSprint, final boolean start) {
        dateSprint.setKeyListener(null);

        dateSprint.setOnTouchListener(new View.OnTouchListener() {
            @Override
            public boolean onTouch(View v, MotionEvent event) {
                if (MotionEvent.ACTION_DOWN == event.getAction()) {
                    DatePickerDialog.newInstance(CreateSprintActivity.this, calendar.get(Calendar.YEAR), calendar.get(Calendar.MONTH), calendar.get(Calendar.DAY_OF_MONTH)).show(getFragmentManager(), "datePicker");
                    startDate = start;
                }

                return true;
            }
        });
    }

    private void setTimeTouchListener(EditText timeSprint, final boolean start) {
        timeSprint.setKeyListener(null);

        timeSprint.setOnTouchListener(new View.OnTouchListener() {
            @Override
            public boolean onTouch(View v, MotionEvent event) {
                if (MotionEvent.ACTION_DOWN == event.getAction()) {
                    TimePickerDialog.newInstance(CreateSprintActivity.this, calendar.get(Calendar.HOUR_OF_DAY), calendar.get(Calendar.MINUTE), true).show(getFragmentManager(), "timePicker");
                    startDate = start;
                }

                return true;
            }
        });
    }

    private void update() {
        if (isStartDate()) {
            startSprintDate.setText(dateFormat.format(calendar.getTime()));
            startSprintTime.setText(timeFormat.format(calendar.getTime()));
        } else {
            endSprintDate.setText(dateFormat.format(calendar.getTime()));
            endSprintTime.setText(timeFormat.format(calendar.getTime()));
        }
    }

    private boolean isStartDate() {
        return startDate;
    }

    @Override
    public void onDateSet(DatePickerDialog dialog, int year, int monthOfYear, int dayOfMonth) {
        calendar.set(year, monthOfYear, dayOfMonth);
        update();
    }

    @Override
    public void onTimeSet(RadialPickerLayout view, int hourOfDay, int minute) {
        calendar.set(Calendar.HOUR_OF_DAY, hourOfDay);
        calendar.set(Calendar.MINUTE, minute);
        update();
    }
}

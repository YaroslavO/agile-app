package com.agile.gdpdp.agile;

import android.support.v7.app.AppCompatActivity;
import android.os.Bundle;
import android.view.MotionEvent;
import android.view.View;
import android.widget.EditText;
import android.widget.ImageButton;
import android.widget.Toast;

import com.agile.gdpdp.agile.database.DatabaseHelper;
import com.agile.gdpdp.agile.database.model.Sprint;
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

    private EditText nameSprint;
    private EditText startSprintDate;
    private EditText startSprintTime;
    private EditText endSprintDate;
    private EditText endSprintTime;
    private ImageButton saveSprintButton;
    private Calendar calendar;
    private DateFormat dateFormat;
    private SimpleDateFormat timeFormat;
    private boolean startDate;
    private Calendar startSprintDateTime = Calendar.getInstance();
    private Calendar endSprintDateTime = Calendar.getInstance();

    @Override
    protected void onCreate(Bundle savedInstanceState) {
        super.onCreate(savedInstanceState);
        setContentView(R.layout.activity_create_sprint);

        startDate = true;

        calendar = Calendar.getInstance();
        dateFormat = DateFormat.getDateInstance(DateFormat.LONG, Locale.getDefault());
        timeFormat = new SimpleDateFormat(TIME_PATTERN, Locale.getDefault());

        nameSprint = (EditText) findViewById(R.id.nameSprint);

        setDateListener();

        setSaveSprintButtonListener();

        initialDateTime();
    }

    private void initialDateTime() {
        startSprintDate.setText(dateFormat.format(calendar.getTime()));
        startSprintTime.setText(timeFormat.format(calendar.getTime()));
        startSprintDateTime.setTime(calendar.getTime());

        endSprintDate.setText(dateFormat.format(calendar.getTime()));
        endSprintTime.setText(timeFormat.format(calendar.getTime()));
        endSprintDateTime.setTime(calendar.getTime());
    }

    private void setSaveSprintButtonListener() {
        saveSprintButton = (ImageButton) findViewById(R.id.saveSprintButton);

        saveSprintButton.setOnClickListener(new View.OnClickListener() {
            @Override
            public void onClick(View v) {
                if (!isValidateAllValue()) {
                    return;
                }

                String name = nameSprint.getText().toString();

                Sprint sprint = new Sprint(name, startSprintDateTime, endSprintDateTime);
                DatabaseHelper db = new DatabaseHelper(CreateSprintActivity.this);
                db.addSprint(sprint);

                Toast.makeText(getApplicationContext(), "You save sprint " + name, Toast.LENGTH_LONG).show();
                CreateSprintActivity.this.finish();
            }
        });
    }

    private boolean isValidateAllValue() {
        String name = nameSprint.getText().toString();

        if (name.isEmpty()) {
            Toast.makeText(getApplicationContext(), "Name sprint not may be empty!!!", Toast.LENGTH_LONG).show();
            nameSprint.setFocusable(true);

            return false;
        }

        if (startSprintDateTime.compareTo(endSprintDateTime) != -1) {
            Toast.makeText(getApplicationContext(), "Time start sprint must less as time end sprint", Toast.LENGTH_LONG).show();

            return false;
        }

        return true;
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
            startSprintDateTime.setTime(calendar.getTime());
        } else {
            endSprintDate.setText(dateFormat.format(calendar.getTime()));
            endSprintTime.setText(timeFormat.format(calendar.getTime()));
            endSprintDateTime.setTime(calendar.getTime());
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

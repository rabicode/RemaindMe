package com.myAndroidApp.RemaindMyTask;

import android.app.AlarmManager;
import android.app.DatePickerDialog;
import android.app.Dialog;
import android.app.PendingIntent;
import android.app.TimePickerDialog;
import android.content.Context;
import android.content.Intent;
import android.content.SharedPreferences;
import android.os.Bundle;
import android.support.v4.app.DialogFragment;
import android.support.v7.app.ActionBarActivity;
import android.support.v7.widget.Toolbar;
import android.text.format.DateFormat;
import android.view.Menu;
import android.view.MenuItem;
import android.view.View;
import android.widget.AdapterView;
import android.widget.ArrayAdapter;
import android.widget.DatePicker;
import android.widget.EditText;
import android.widget.Spinner;
import android.widget.TextView;
import android.widget.TimePicker;

import java.util.Calendar;

import static android.widget.Toast.LENGTH_LONG;
import static android.widget.Toast.makeText;

public class MainActivity extends ActionBarActivity {

    EditText nameinput;

    TextView viewtext;
    public static int alarmHour, alarmMin, alarmDay, alarmYear, alarmMonth;
    //final static int RQS_1 = 1;
    private int RQS_1=1;


    Spinner spinner;



    @Override
    protected void onCreate(Bundle savedInstanceState) {
        super.onCreate(savedInstanceState);
        setContentView(R.layout.activity_main);
        Toolbar toolbar = (Toolbar) findViewById(R.id.toolbar);
        setSupportActionBar(toolbar);

        nameinput = (EditText) findViewById(R.id.nameinput);
        // purposeinput = (EditText) findViewById(R.id.purposeinput);
        viewtext = (TextView) findViewById(R.id.txt);
        spinner = (Spinner) findViewById(R.id.spinner);
        ArrayAdapter adapter =  ArrayAdapter.createFromResource(this,R.array.purpose_name,android.R.layout.simple_spinner_item);
        spinner.setAdapter(adapter);
        spinner.setOnItemSelectedListener(new AdapterView.OnItemSelectedListener() {
            @Override
            public void onItemSelected(AdapterView<?> parent, View view, int i, long l) {

                //TextView myText = (TextView) view;
                String item = parent.getItemAtPosition(i).toString();
                //  Toast.makeText(getApplicationContext(),item+"is selected",Toast.LENGTH_LONG).show();


            }

            @Override
            public void onNothingSelected(AdapterView<?> adapterView) {

            }
        });

    }

    @Override
    public boolean onCreateOptionsMenu(Menu menu) {

        getMenuInflater().inflate(R.menu.menu_main, menu);
        return true;
    }

    @Override
    public boolean onOptionsItemSelected(MenuItem item) {

        int id = item.getItemId();


        if (id == R.id.action_settings) {
            return true;
        }

        return super.onOptionsItemSelected(item);
    }

    public void setDate(View view) {
        DialogFragment newFragment = new DatePickerFragment();
        newFragment.show(getSupportFragmentManager(), "datePicker");
    }

    public void setTime(View view) {

        DialogFragment newFragment = new TimePickerFragment();
        newFragment.show(getSupportFragmentManager(), "timePicker");
    }


    public void setAlarm(View view) {
        Calendar calNow = Calendar.getInstance();
        Calendar calSet = (Calendar) calNow.clone();
        calSet.set(Calendar.HOUR_OF_DAY, alarmHour);
        calSet.set(Calendar.MINUTE, alarmMin);
        calSet.set(Calendar.DAY_OF_MONTH, alarmDay);
        calSet.set(Calendar.YEAR, alarmYear);
        calSet.set(Calendar.MONTH, alarmMonth);

        setAlarmN(calSet);
    }

    private void setAlarmN(Calendar targetCal) {

        makeText(this, "Alarm is set at" + targetCal.getTime(),
                LENGTH_LONG).show();
        Intent intent = new Intent(getBaseContext(), AlarmReceiver.class);

       /* PendingIntent pendingIntent = PendingIntent.getBroadcast(
                getBaseContext(), RQS_1+=1, intent, 0);*/
         RQS_1 += (int) System.currentTimeMillis();
        PendingIntent pendingIntent = PendingIntent.getBroadcast(
                getBaseContext(), RQS_1, intent, 0);

        AlarmManager alarmManager = (AlarmManager) getSystemService(Context.ALARM_SERVICE);
        alarmManager.set(AlarmManager.RTC_WAKEUP, targetCal.getTimeInMillis(),
                pendingIntent);


    }

    //date picker fragment
    public static class DatePickerFragment extends DialogFragment
            implements DatePickerDialog.OnDateSetListener {

        @Override
        public Dialog onCreateDialog(Bundle savedInstanceState) {
            // Use the current date as the default date in the picker
            final Calendar c = Calendar.getInstance();
            int year = c.get(Calendar.YEAR);
            int month = c.get(Calendar.MONTH);
            int day = c.get(Calendar.DAY_OF_MONTH);

            // Create a new instance of DatePickerDialog and return it
            return new DatePickerDialog(getActivity(), this, year, month, day);
        }

        public void onDateSet(DatePicker view, int year, int month, int day) {

            alarmDay = day;
            alarmYear = year;
            alarmMonth = month;
        }
    }

    //Time picker fragment
    public static class TimePickerFragment extends DialogFragment
            implements TimePickerDialog.OnTimeSetListener {
        @Override
        public Dialog onCreateDialog(Bundle savedInstanceState) {
            // Use the current time as the default values for the picker
            final Calendar c = Calendar.getInstance();
            int hour = c.get(Calendar.HOUR_OF_DAY);
            int minute = c.get(Calendar.MINUTE);

            // Create a new instance of TimePickerDialog and return it
            return new TimePickerDialog(getActivity(), this, hour, minute,
                    DateFormat.is24HourFormat(getActivity()));
        }

        public void onTimeSet(TimePicker view, int hourOfDay, int minute) {

            alarmHour = hourOfDay;
            alarmMin = minute;
        }
    }

    //saving data in sharedpreferences
    public void saveinfo(View view) {
        SharedPreferences sp = getSharedPreferences("RemaindMyTask", Context.MODE_PRIVATE);
        SharedPreferences.Editor editor = sp.edit();
        editor.putString("name", nameinput.getText().toString());
        editor.putString("purpose",spinner.getSelectedItem().toString());
        editor.apply();
        makeText(this, "saved", LENGTH_LONG).show();

    }


    //showing data
    public void displayinfo(View view) {
        SharedPreferences sp = getSharedPreferences("RemaindMyTask", Context.MODE_PRIVATE);
        String name = sp.getString("name", "");
        String purpose = sp.getString("purpose", "");

        viewtext.setText(name + " "+ purpose + " ");
    }
    protected void onResume() {
        super.onResume();
    }

    protected void onDestroy() {
        super.onDestroy();
    }


}

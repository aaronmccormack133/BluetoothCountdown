package com.example.bluetoothcountdown;

import android.bluetooth.BluetoothAdapter;
import android.os.Bundle;
import android.os.CountDownTimer;
import android.support.v7.app.AppCompatActivity;
import android.view.View;
import android.widget.Button;
import android.widget.EditText;
import android.widget.TextView;
import android.widget.Toast;

import java.util.concurrent.TimeUnit;

public class MainActivity extends AppCompatActivity {

    private final BluetoothAdapter bAdapter = BluetoothAdapter.getDefaultAdapter();

    private TextView mTextField;
    private Button turnOff;
    private CountDownTimer countDownTimer;
    private EditText editHour;
    private EditText editMin;


    @Override
    protected void onCreate(Bundle savedInstanceState) {
        super.onCreate(savedInstanceState);
        setContentView(R.layout.activity_main);

        turnOff = (Button) findViewById(R.id.toggle);
        mTextField = (TextView) findViewById(R.id.displayTimer);
        editHour = (EditText) findViewById(R.id.timePickerHour);
        editMin = (EditText) findViewById(R.id.timePickerMin);

        turnOff.setOnClickListener(new View.OnClickListener() {
            @Override
            public void onClick(View v) {
                if(bAdapter == null){
                    Toast.makeText(getApplicationContext(), "Bluetooth not supported", Toast.LENGTH_SHORT).show();
                }
                else{
                    countDown();
                }
            }
        });

    }

    public void countDown(){
        String hourTime = editHour.getText().toString();
        String minTime = editMin.getText().toString();

        if(hourTime.isEmpty()){
            hourTime = "0";
        }
        else if(minTime.isEmpty()){
            minTime = "0";
        }
        int hour = Integer.parseInt(hourTime);
        int min = Integer.parseInt(minTime);
        long hourInMillis = TimeUnit.HOURS.toMillis(hour);
        long minInMillis = TimeUnit.MINUTES.toMillis(min);
        long finalTime = hourInMillis + minInMillis;

        countDownTimer = new CountDownTimer(finalTime, 1000) {
            @Override
            public void onTick(long millisUntilFinished) {
                mTextField.setText(""  + millisUntilFinished / 1000);
            }

            @Override
            public void onFinish() {
                bAdapter.disable();
                Toast.makeText(getApplicationContext(), "Bluetooth off", Toast.LENGTH_SHORT).show();
            }
        };
        countDownTimer.start();
    }
}

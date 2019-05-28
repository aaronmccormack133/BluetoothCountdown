package com.example.bluetoothcountdown;

import android.bluetooth.BluetoothAdapter;
import android.os.Bundle;
import android.app.Activity;
import android.os.CountDownTimer;
import android.view.View;
import android.widget.Button;
import android.widget.TextView;
import android.widget.Toast;

import java.util.Calendar;

public class MainActivity extends Activity {

    private final BluetoothAdapter bAdapter = BluetoothAdapter.getDefaultAdapter();

    private TextView mTextField;
    private Button turnOff;
    private CountDownTimer countDownTimer;


    @Override
    protected void onCreate(Bundle savedInstanceState) {
        super.onCreate(savedInstanceState);
        setContentView(R.layout.activity_main);

        turnOff = (Button) findViewById(R.id.toggle);
        mTextField = (TextView) findViewById(R.id.displayTimer);

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
        countDownTimer = new CountDownTimer(30000, 1000) {
            @Override
            public void onTick(long millisUntilFinished) {
                mTextField.setText("Time remaining: " + millisUntilFinished / 1000);
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

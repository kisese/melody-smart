package com.yhtomit.brayo.bleserial.activity;

import android.app.Activity;
import android.app.AlertDialog;
import android.content.DialogInterface;
import android.os.Bundle;
import android.util.Log;
import android.view.View;
import android.widget.Button;
import android.widget.EditText;
import android.widget.Toast;

import com.bluecreation.melodysmart.I2CService;
import com.bluecreation.melodysmart.MelodySmartDevice;
import com.bluecreation.melodysmart.MelodySmartListener;
import com.yhtomit.brayo.bleserial.R;

/**
 * Created by genis on 23/01/2015.
 */
public class I2CActivity extends BleActivity implements I2CService.Listener {

    private static final String TAG = I2CActivity.class.getSimpleName();
    private EditText incomingData;
    private EditText outgoingData;
    private EditText deviceAddress;
    private EditText registerAddress;
    private Button readButton;
    private Button writeButton;
    private I2CService i2CService;


    @Override
    public void onCreate(Bundle savedInstanceState) {
        super.onCreate(savedInstanceState);
        setContentView(R.layout.i2c_activity);

        i2CService = MelodySmartDevice.getInstance().getI2CService();
        i2CService.enableNotifications(true);
        i2CService.registerListener(this);

        incomingData = (EditText) findViewById(R.id.incomingDataTextEdit);
        outgoingData = (EditText) findViewById(R.id.outgoingDataTextEdit);
        writeButton = (Button) findViewById(R.id.writeButton);
        readButton = (Button) findViewById(R.id.readButton);

        deviceAddress = (EditText) findViewById(R.id.deviceEditText);
        registerAddress = (EditText) findViewById(R.id.registerEditText);

        writeButton.setOnClickListener(new View.OnClickListener() {
            @Override
            public void onClick(View view) {
                byte[] register = toBytes(registerAddress.getText().toString());
                byte[] data = toBytes(outgoingData.getText().toString());
                byte[] fullData = new byte[register.length + data.length];
                System.arraycopy(register, 0, fullData, 0, register.length);
                System.arraycopy(data, 0, fullData, register.length, data.length);
                i2CService.writeData(fullData, toByte(deviceAddress.getText().toString()));
            }
        });

        readButton.setOnClickListener(new View.OnClickListener() {
            @Override
            public void onClick(View view) {
                byte[] writePortion = toBytes(registerAddress.getText().toString());
                i2CService.readData(writePortion, toByte(deviceAddress.getText().toString()), (byte)16);
            }
        });
    }

    @Override public void onDestroy() {
        i2CService.enableNotifications(false);
        i2CService.unregisterListener(this);

        super.onDestroy();
    }


    @Override
    public void handleReply(boolean success, byte[] data) {

        String str = "";
        for (byte b : data) {
            str += String.format("0x%02x ", b);
            Log.d(TAG, "Got I2C data : " + str);
        }
        final String finalStr = str;
        if (success) {
            runOnUiThread(new Runnable() {
                @Override
                public void run() {
                    incomingData.setText(finalStr);
                }
            });
        } else {
            runOnUiThread(new Runnable() {
                @Override
                public void run() {
                    Toast.makeText(I2CActivity.this, "Error happened", Toast.LENGTH_SHORT);
                }
            });
        }
    }

    @Override
    public void onConnected(boolean found) {
        /* Nothing to do here, we get to the I2CActivity so the I2C service is already connected */
    }

    private static byte toByte(String s) {
        return (byte) (0xff & Integer.parseInt(s,16));
    }

    private static byte[] toBytes(String s) {
        if (s.length() % 2 != 0) {
            s = "0" + s;
        }
        byte[] bytes = new byte[s.length() / 2];
        for (int i = 0; i < bytes.length; i++) {
            bytes[i] = (byte)(Integer.parseInt(s.substring(2*i,2*(i+1)), 16) & 0xff);
        }
        return bytes;
    }
}

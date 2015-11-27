package com.yhtomit.brayo.bleserial.activity;

import android.app.AlertDialog;
import android.app.ProgressDialog;
import android.bluetooth.BluetoothAdapter;
import android.content.DialogInterface;
import android.content.Intent;
import android.os.Bundle;
import android.os.Handler;
import android.support.v7.widget.Toolbar;
import android.util.Log;
import android.view.KeyEvent;
import android.view.Menu;
import android.view.View;
import android.view.WindowManager;
import android.widget.Button;
import android.widget.EditText;
import android.widget.TextView;
import android.widget.Toast;

import com.bluecreation.melodysmart.BLEError;
import com.bluecreation.melodysmart.DataService;
import com.bluecreation.melodysmart.DeviceDatabase;
import com.bluecreation.melodysmart.DeviceInfoService;
import com.bluecreation.melodysmart.I2CService;
import com.bluecreation.melodysmart.MelodySmartDevice;
import com.bluecreation.melodysmart.MelodySmartListener;
import com.bluecreation.melodysmart.RemoteCommandsService;
import com.gc.materialdesign.views.ButtonRectangle;
import com.yhtomit.brayo.bleserial.R;
import com.yhtomit.brayo.bleserial.magic.MagicBox;

import java.util.ArrayList;
import java.util.List;

/**
 * Created by genis on 15/10/2014.
 */
public class MelodySmartTestActivity extends BleActivity implements MelodySmartListener {

    private String TAG = "TestActivity";
    private static final int OTAU_ACTIVITY_CODE = 1;
    private MelodySmartDevice device;
    private EditText dataToSend;
    private EditText dataReceived;
    private Button otaButton;
    private Button i2cButton;
    private Button batteryButton;
    private Button remoteCommandsButton;
    private AlertDialog alertDialog;
    private int nextInfoType = 0;

    private static final boolean BATTERY_SERVICE = false;
    private static final boolean DEVICE_INFO_SERVICE = false;
    private String send_string;
    private ButtonRectangle send;
    private ButtonRectangle send_n_save;
    private MagicBox magicBox;
    private ButtonRectangle btn_home;
    private ProgressDialog pDialog;

    private enum ChildActivity {
        OTAU,
        REMOTE_COMMANDS,
        I2C
    };



    private I2CService.Listener i2cListener = new I2CService.Listener() {

        @Override
        public void onConnected(final boolean found) {
            Log.d(TAG, ((found) ? "Connected " : "Not connected  ") + "to I2C service");
            runOnUiThread(new Runnable() {
                @Override
                public void run() {
//                    i2cButton.setEnabled(found);
                }
            });
        }

        @Override
        public void handleReply(boolean success, byte[] data) {
            /* do nothing */
        }
    };

    private DataService.Listener dataServiceListener = new DataService.Listener() {

        @Override
        public void onConnected(final boolean found) {
            Log.d(TAG, ((found) ? "Connected " : "Not connected  ") + "to MelodySmart data service");
            if (found) {
                device.getDataService().enableNotifications(true);
            }
            runOnUiThread(new Runnable() {
                @Override
                public void run() {

                    dataToSend.setEnabled(found);
                    dataToSend.setText(send_string);
                    pDialog.dismiss();
                    pDialog.dismiss();
                    if (!found) {
                        Toast.makeText(MelodySmartTestActivity.this, "MelodySmart service not found on the remote device.", Toast.LENGTH_LONG).show();
                    }
                }
            });
        }

        @Override
        public void onReceived(final byte[] data) {
            runOnUiThread(new Runnable() {
                @Override
                public void run() {
                    dataToSend.setText("Sending... " + new String(data));
                }
            });
        }
    };

    private RemoteCommandsService.Listener remoteCommandsListener = new RemoteCommandsService.Listener() {

        @Override
        public void handleReply(byte[] reply) {
            /* Do nothing */
        }

        @Override
        public void onConnected(final boolean found) {
            Log.d(TAG, ((found) ? "Connected " : "Not connected  ") + "to remote commands service");
            runOnUiThread(new Runnable() {
                @Override
                public void run() {
                    send.setEnabled(true);
                    dataToSend.setText(send_string);
                }
            });
        }
    };


    @Override
    public void onCreate(Bundle savedInstanceState) {
        super.onCreate(savedInstanceState);
        setContentView(R.layout.test_activity);
        Log.d(TAG, "Starting");

        send = (ButtonRectangle)findViewById(R.id.send);
        send_n_save = (ButtonRectangle)findViewById(R.id.send_save);
        btn_home = (ButtonRectangle)findViewById(R.id.btn_home);

        Intent i = getIntent();
        send_string = i.getStringExtra("send_string").replace(";null", "");
        send_string = send_string + "|#";

        /* Get the instance of the Melody Smart Android library and initialize it */
        device = MelodySmartDevice.getInstance();
        device.registerListener((MelodySmartListener) this);
        device.getDataService().registerListener(dataServiceListener);
        device.getI2CService().registerListener(i2cListener);

        device.getRemoteCommandsService().registerListener(remoteCommandsListener);

        final String deviceAddress = getIntent().getStringExtra("deviceAddress");
        String deviceName = getIntent().getStringExtra("deviceAddress");

        //Toast.makeText(MelodySmartTestActivity.this, "Connecting", Toast.LENGTH_LONG).show();


        try {
            device.connect(deviceAddress);
        } catch (Exception e) {
            Toast.makeText(this, e.getMessage(), Toast.LENGTH_LONG).show();
        }

        dataToSend = (EditText) findViewById(R.id.etDataToSend);
        dataToSend.setVisibility(View.GONE);
        pDialog = new ProgressDialog(this);
        pDialog.setMessage("Preparing to send ...");
        pDialog.setIndeterminate(false);
        pDialog.setCancelable(true);
        pDialog.show();
        //dataToSend.setVisibility(View.GONE);
//        dataToSend.setOnEditorActionListener(new TextView.OnEditorActionListener() {
//            @Override
//            public boolean onEditorAction(TextView textView, int i, KeyEvent keyEvent) {
//                //device.getDataService().send(textView.getText().toString().getBytes());
//                sendValues(send_string);
//                textView.setText("");
//                return true;
//            }
//        });
        magicBox = new MagicBox(this);
        dataToSend.setEnabled(false);

        send_n_save.setOnClickListener(new View.OnClickListener() {
            @Override
            public void onClick(View v) {
                send_string = "M," +send_string;
                sendValues(send_string);
                //magicBox.addPresetDialog(send_string);
            }
        });

        btn_home.setOnClickListener(new View.OnClickListener() {
            @Override
            public void onClick(View v) {
                device.getDataService().unregisterListener(dataServiceListener);
                device.getI2CService().unregisterListener(i2cListener);
                device.getRemoteCommandsService().unregisterListener(remoteCommandsListener);
                //device.unregisterListener((MelodySmartListener) this);
                device.disconnect();
                disableBT();
                Intent i =new Intent(MelodySmartTestActivity.this, MainActivity.class);
                startActivity(i);
                finish();
            }
        });

        send.setOnClickListener(new View.OnClickListener() {
            @Override
            public void onClick(View v) {
                sendValues(send_string);
            }
        });

        getWindow().setSoftInputMode(WindowManager.LayoutParams.SOFT_INPUT_STATE_HIDDEN);
    }

    public void sendValues(String send_string){

        final String[] data = splitterB(send_string.replace(";null", ""));

        final Handler h = new Handler();

        final Runnable r16 = new Runnable() {
            @Override
            public void run() {
                try{
                    String send = data[15];
                    
                    device.getDataService().send(send.getBytes());
                    device.getDataService().unregisterListener(dataServiceListener);
                    device.getI2CService().unregisterListener(i2cListener);
                    device.getRemoteCommandsService().unregisterListener(remoteCommandsListener);
                    //device.unregisterListener((MelodySmartListener) this);
                    device.disconnect();
                    disableBT();
                    Intent i =new Intent(MelodySmartTestActivity.this, MainActivity.class);
                    startActivity(i);
                    finish();
                }catch(Exception e){
                    e.printStackTrace();
                    return;
                }
            }
        };

        final Runnable r15 = new Runnable() {
            @Override
            public void run() {
                try{
                    String send = data[14];
                    
                    device.getDataService().send(send.getBytes());
                    h.postDelayed(r16, 10);
                }catch(Exception e){
                    e.printStackTrace();
                    return;
                }
            }
        };

        final Runnable r14 = new Runnable() {
            @Override
            public void run() {
                try{
                    String send = data[13];
                    
                    device.getDataService().send(send.getBytes());
                    h.postDelayed(r15, 10);
                }catch(Exception e){
                    e.printStackTrace();
                    return;
                }
            }
        };

        final Runnable r13 = new Runnable() {
            @Override
            public void run() {
                try{
                    String send = data[12];
                    
                    device.getDataService().send(send.getBytes());
                    h.postDelayed(r14, 10);
                }catch(Exception e){
                    e.printStackTrace();
                    return;
                }
            }
        };

        final Runnable r12 = new Runnable() {
            @Override
            public void run() {
                try{
                    String send = data[11];
                    
                    device.getDataService().send(send.getBytes());
                    h.postDelayed(r13, 10);
                }catch(Exception e){
                    e.printStackTrace();
                    return;
                }
            }
        };

        final Runnable r11 = new Runnable() {
            @Override
            public void run() {
                try{
                    String send = data[10];
                    
                    device.getDataService().send(send.getBytes());
                    h.postDelayed(r12, 10);
                }catch(Exception e){
                    e.printStackTrace();
                    return;
                }
            }
        };

        final Runnable r10 = new Runnable() {
            @Override
            public void run() {
                try{
                    String send = data[9];
                    
                    device.getDataService().send(send.getBytes());
                    h.postDelayed(r11, 10);
                }catch(Exception e){
                    e.printStackTrace();
                    return;
                }
            }
        };

        final Runnable r9 = new Runnable() {
            @Override
            public void run() {
                try{
                    String send = data[8];
                    
                    device.getDataService().send(send.getBytes());
                    h.postDelayed(r10, 10);
                }catch(Exception e){
                    e.printStackTrace();
                    return;
                }
            }
        };

        final Runnable r8 = new Runnable() {
            @Override
            public void run() {
                try{
                    String send = data[7];
                    
                    device.getDataService().send(send.getBytes());
                    h.postDelayed(r9, 10);
                }catch(Exception e){
                    e.printStackTrace();
                    return;
                }
            }
        };

        final Runnable r7 = new Runnable() {
            @Override
            public void run() {
                try{
                    String send = data[6];
                    
                    device.getDataService().send(send.getBytes());
                    h.postDelayed(r8, 10);
                }catch(Exception e){
                    e.printStackTrace();
                    return;
                }
            }
        };

        final Runnable r6 = new Runnable() {
            @Override
            public void run() {
                try{
                    String send = data[5];
                    
                    device.getDataService().send(send.getBytes());
                    h.postDelayed(r7, 10);
                }catch(Exception e){
                    e.printStackTrace();
                    return;
                }
            }
        };

        final Runnable r5 = new Runnable() {
            @Override
            public void run() {
                try{
                    String send = data[4];
                    
                    device.getDataService().send(send.getBytes());
                    h.postDelayed(r6, 10);
                }catch(Exception e){
                    e.printStackTrace();
                    return;
                }
            }
        };

        final Runnable r4 = new Runnable() {
            @Override
            public void run() {
                try{
                    String send = data[3];
                    
                    device.getDataService().send(send.getBytes());
                    h.postDelayed(r5, 10);
                }catch(Exception e){
                    e.printStackTrace();
                    return;
                }
            }
        };

        final Runnable r3 = new Runnable() {
            @Override
            public void run() {
                try {
                    String send = data[2];
                    
                    device.getDataService().send(send.getBytes());
                    h.postDelayed(r4, 10);
                }catch(Exception e){
                    e.printStackTrace();
                    return;
                }
            }
        };
        final Runnable r2 = new Runnable() {
            @Override
            public void run() {
                try {
                    String send = data[1];
                    
                    device.getDataService().send(send.getBytes());
                    h.postDelayed(r3, 10);
                }catch(Exception e){
                    e.printStackTrace();
                    return;
                }
            }
        };

        Runnable r1 = new Runnable() {
            @Override
            public void run() {
                try {
                    String send = data[0];
                    
                    device.getDataService().send(send.getBytes());
                    h.postDelayed(r2, 10);
                }catch(Exception e){
                    e.printStackTrace();
                    return;
                }
            }
        };
        h.postDelayed(r1, 10);
    }


    public String[] splitterB(String value){
        List<String> list= new ArrayList<String>();
        for(int i=0; i<value.length(); i=i+18){
            int endIndex = Math.min(i+18, value.length());
            list.add(value.substring(i, endIndex));
        }

        return list.toArray(new String[16]);
    }

    @Override
    protected void onActivityResult(int requestCode, int resultCode, Intent data) {

        if (requestCode == ChildActivity.OTAU.ordinal() && resultCode == RESULT_OK) {
            finish();
        }
    }


    @Override
    public void onDestroy() {
        super.onDestroy();
        device.getDataService().unregisterListener(dataServiceListener);
        device.getI2CService().unregisterListener(i2cListener);
        device.getRemoteCommandsService().unregisterListener(remoteCommandsListener);
        device.unregisterListener((MelodySmartListener) this);
        device.disconnect();
    }


    @Override
    protected void onPause() {
        super.onPause();
        device.getDataService().unregisterListener(dataServiceListener);
        device.getI2CService().unregisterListener(i2cListener);
        device.getRemoteCommandsService().unregisterListener(remoteCommandsListener);
        device.unregisterListener((MelodySmartListener) this);
        device.disconnect();
    }

    @Override
    public boolean onCreateOptionsMenu(Menu menu) {
        //getMenuInflater().inflate(R.menu.main, menu);
        return true;
    }

    @Override
    public void onDeviceConnected() {
        runOnUiThread(new Runnable() {
            @Override
            public void run() {
                //sendValues(send_string);
                Toast.makeText(MelodySmartTestActivity.this,"Connected", Toast.LENGTH_LONG).show();
                //alertDialog.setMessage("Discovering MelodySmart service...");
            }
        });
    }

    @Override
    public void onDeviceDisconnected(final BLEError error) {

        for (ChildActivity child : ChildActivity.values()) {
            finishActivity(child.ordinal());
        }

        if (error.getType() == BLEError.Type.NO_ERROR) {
            runOnUiThread(new Runnable() {
                @Override
                public void run() {
                    dataToSend.setEnabled(false);
                    Toast.makeText(MelodySmartTestActivity.this, "Disconnected from the device.", Toast.LENGTH_LONG).show();
                    finish();
                }
            });
        } else {
            runOnUiThread(new Runnable() {
                @Override
                public void run() {
                    Toast.makeText(MelodySmartTestActivity.this, "Disconnected", Toast.LENGTH_LONG).show();

                }
            });
        }
    }

    @Override
    public void onOtauAvailable() {
        runOnUiThread(new Runnable() {
            @Override
            public void run() {
               // otaButton.setEnabled(true);
            }
        });
    }

    @Override
    public void onOtauRecovery(DeviceDatabase.DeviceData deviceData) {
        // Automatically go to OTAU
        startOtauActivity(true, deviceData);
    }

    private void readNextInfo() {
        DeviceInfoService.INFO_TYPE[] types = DeviceInfoService.INFO_TYPE.values();
        if (nextInfoType < types.length) {
            device.getDeviceInfoService().read(types[nextInfoType]);
            nextInfoType++;
        }
    }

    private void startOtauActivity(boolean isRecovery, DeviceDatabase.DeviceData deviceData) {

        device.getDataService().unregisterListener(dataServiceListener);
        //Uncomment to enable battery or device info services
        device.unregisterListener((MelodySmartListener) this);


    }


    @Override
    public void onBackPressed() {
        super.onBackPressed();
        Intent i =new Intent(MelodySmartTestActivity.this, MainActivity.class);

        try {
            device.disconnect();
        } catch (Exception e) {
            Toast.makeText(MelodySmartTestActivity.this, e.getMessage(), Toast.LENGTH_LONG).show();
        }
        startActivity(i);
        onDestroy();
        finish();
    }

    private String getDisconnectionMessage(BLEError error) {
        String message = "";
        switch (error.getType()) {
            case AUTHENTICATION_ERROR:
                message = "Authentication error: ";
                if (device.isBonded()) {
                    if (device.removeBond()) {
                        message += " bonding information has been removed on your Android phone. Please remove it on your MelodySmart device if necessary and reconnect.";
                    } else {
                        message += " could not remove bonding information on your Android phone. Please remove it manually on the Bluetooth settings screen, " +
                                "remove it on your MelodySmart device if necessary and reconnect.";
                    }
                } else {
                    message += " please remove bonding information on your MelodySmart device and reconnect.";

                }
                break;

            case REMOTE_DISCONNECTION:
                message = error.getMessage();
                break;

            default:
                message = String.format("Disconnected: %s\n\n[error code: %d]", error.getMessage(), error.getCode());
                break;
        }

        return message;
    }

    public void disableBT(){
        BluetoothAdapter mBluetoothAdapter = BluetoothAdapter.getDefaultAdapter();
        if (mBluetoothAdapter.isEnabled()){
            mBluetoothAdapter.disable();
        }
    }
}

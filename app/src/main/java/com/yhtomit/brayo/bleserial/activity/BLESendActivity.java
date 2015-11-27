package com.yhtomit.brayo.bleserial.activity;

import android.bluetooth.BluetoothDevice;
import android.content.BroadcastReceiver;
import android.content.ComponentName;
import android.content.Context;
import android.content.Intent;
import android.content.IntentFilter;
import android.content.ServiceConnection;
import android.os.Bundle;
import android.os.Handler;
import android.os.IBinder;
import android.support.v7.app.ActionBarActivity;
import android.util.Log;
import android.view.Menu;
import android.view.MenuItem;
import android.view.View;
import android.view.inputmethod.InputMethodManager;
import android.widget.Button;
import android.widget.EditText;
import android.widget.ScrollView;
import android.widget.TextView;
import android.widget.Toast;

import com.bluecreation.melodysmart.BLEError;
import com.bluecreation.melodysmart.DataService;
import com.bluecreation.melodysmart.DeviceDatabase;
import com.bluecreation.melodysmart.MelodySmartDevice;
import com.bluecreation.melodysmart.MelodySmartListener;
import com.yhtomit.brayo.bleserial.R;

import java.util.ArrayList;
import java.util.List;

/**
 * Created by Brayo on 8/10/2015.
 */
public class BLESendActivity extends BleActivity implements MelodySmartListener {
    private final static String TAG = DeviceControlActivity.class.getSimpleName();

    public static final String EXTRAS_DEVICE_NAME = "DEVICE_NAME";
    public static final String EXTRAS_DEVICE_ADDRESS = "DEVICE_ADDRESS";

    private String mDeviceName;
    private String mDeviceAddress;
    private BluetoothLeService mBluetoothLeService;
    private boolean mConnected = false;
    private MelodySmartDevice device;

    // Code to manage Service lifecycle.
    private final ServiceConnection mServiceConnection = new ServiceConnection() {

        @Override
        public void onServiceConnected(ComponentName componentName, IBinder service) {
            mBluetoothLeService = ((BluetoothLeService.LocalBinder) service).getService();
            if (!mBluetoothLeService.initialize()) {
                Log.e(TAG, "Unable to initialize Bluetooth");
                finish();
            }

            Log.e(TAG, "mBluetoothLeService is okay");
            // Automatically connects to the device upon successful start-up initialization.
            //mBluetoothLeService.connect(mDeviceAddress);
        }

        @Override
        public void onServiceDisconnected(ComponentName componentName) {
            mBluetoothLeService = null;
        }
    };

    // Handles various events fired by the Service.
    // ACTION_GATT_CONNECTED: connected to a GATT server.
    // ACTION_GATT_DISCONNECTED: disconnected from a GATT server.
    // ACTION_GATT_SERVICES_DISCOVERED: discovered GATT services.
    // ACTION_DATA_AVAILABLE: received data from the device.  This can be a result of read
    //                        or notification operations.
    private final BroadcastReceiver mGattUpdateReceiver = new BroadcastReceiver() {
        @Override
        public void onReceive(Context context, Intent intent) {
            final String action = intent.getAction();
            if (BluetoothLeService.ACTION_GATT_CONNECTED.equals(action)) {
                Log.e(TAG, "Only gatt, just wait");
            } else if (BluetoothLeService.ACTION_GATT_DISCONNECTED.equals(action)) {
                mConnected = false;
                invalidateOptionsMenu();
                Toast.makeText(BLESendActivity.this, "Disconnected", Toast.LENGTH_LONG).show();
                clearUI();
            }else if(BluetoothLeService.ACTION_GATT_SERVICES_DISCOVERED.equals(action))
            {
                mConnected = true;
                ShowDialog();
                Toast.makeText(BLESendActivity.this, "DIscovered", Toast.LENGTH_LONG).show();
                Log.e(TAG, "In what we need");
                invalidateOptionsMenu();
            }else if (BluetoothLeService.ACTION_DATA_AVAILABLE.equals(action)) {
                Log.e(TAG, "RECV DATA");
                String data = intent.getStringExtra(BluetoothLeService.EXTRA_DATA);
                if (data != null) {
                    Toast.makeText(BLESendActivity.this, "Available", Toast.LENGTH_LONG).show();
                }
            }
        }
    };
    private TextView mDataField;
    private String send_string;

    private void clearUI() {
        mDataField.setText("No Data");
    }

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

                    if (!found) {
                        Toast.makeText(BLESendActivity.this, "MelodySmart service not found on the remote device.", Toast.LENGTH_LONG).show();
                    }
                }
            });
        }

        @Override
        public void onReceived(final byte[] data) {
            runOnUiThread(new Runnable() {
                @Override
                public void run() {
                    mDataField.setText(new String(data));
                }
            });
        }
    };

    @Override
    public void onCreate(Bundle savedInstanceState) {
        super.onCreate(savedInstanceState);
        setContentView(R.layout.ble_sender);

        final Intent intent = getIntent();
        mDeviceName = intent.getStringExtra(EXTRAS_DEVICE_NAME);
        mDeviceAddress = intent.getStringExtra(EXTRAS_DEVICE_ADDRESS);
        send_string = intent.getStringExtra("send_string");
        //device.getRemoteCommandsService().registerListener(remoteCommandsListener);

        device = MelodySmartDevice.getInstance();
        device.registerListener((MelodySmartListener) this);
        device.getDataService().registerListener(dataServiceListener);


        try {
            device.connect(mDeviceAddress);
        } catch (Exception e) {
            e.printStackTrace();
            Toast.makeText(this, e.getMessage(), Toast.LENGTH_LONG).show();
        }


        // Sets up UI references.
        mDataField = (TextView) findViewById(R.id.send_string_status);
        mDataField.setText(send_string);


        Intent gattServiceIntent = new Intent(this, BluetoothLeService.class);
        Log.d(TAG, "Try to bindService=" + bindService(gattServiceIntent, mServiceConnection, BIND_AUTO_CREATE));

        registerReceiver(mGattUpdateReceiver, makeGattUpdateIntentFilter());
       // sendValues(send_string + "#");
        device.getDataService().send("T#".getBytes());
        Toast.makeText(BLESendActivity.this, "T#", Toast.LENGTH_LONG).show();
    }

    public void sendValues(String send_string){
       
        final String[] data = splitterB(send_string);
 
        final Handler h = new Handler();
        
        final Runnable r16 = new Runnable() {
            @Override
            public void run() {
                try{
                    mBluetoothLeService.WriteValue(data[15]);
                }catch(Exception e){e.printStackTrace();}
            }
        };

        final Runnable r15 = new Runnable() {
            @Override
            public void run() {
                try{
                    mBluetoothLeService.WriteValue(data[14]);
                    h.postDelayed(r16, 10);
                }catch(Exception e){e.printStackTrace();}
            }
        };

        final Runnable r14 = new Runnable() {
            @Override
            public void run() {
                try{
                    mBluetoothLeService.WriteValue(data[13]);
                    h.postDelayed(r15, 10);
                }catch(Exception e){e.printStackTrace();}
            }
        };

        final Runnable r13 = new Runnable() {
            @Override
            public void run() {
                try{
                    mBluetoothLeService.WriteValue(data[12]);
                    h.postDelayed(r14, 10);
                }catch(Exception e){e.printStackTrace();}
            }
        };

        final Runnable r12 = new Runnable() {
            @Override
            public void run() {
                try{
                    mBluetoothLeService.WriteValue(data[11]);
                    h.postDelayed(r13, 10);
                }catch(Exception e){e.printStackTrace();}
            }
        };

        final Runnable r11 = new Runnable() {
            @Override
            public void run() {
                try{
                    mBluetoothLeService.WriteValue(data[10]);
                    h.postDelayed(r12, 10);
                }catch(Exception e){e.printStackTrace();}
            }
        };

        final Runnable r10 = new Runnable() {
            @Override
            public void run() {
                try{
                    mBluetoothLeService.WriteValue(data[9]);
                    h.postDelayed(r11, 10);
                }catch(Exception e){e.printStackTrace();}
            }
        };

        final Runnable r9 = new Runnable() {
            @Override
            public void run() {
                try{
                    mBluetoothLeService.WriteValue(data[8]);
                    h.postDelayed(r10, 10);
                }catch(Exception e){e.printStackTrace();}
            }
        };

        final Runnable r8 = new Runnable() {
            @Override
            public void run() {
                try{
                    mBluetoothLeService.WriteValue(data[7]);
                    h.postDelayed(r9, 10);
                }catch(Exception e){e.printStackTrace();}
            }
        };

        final Runnable r7 = new Runnable() {
            @Override
            public void run() {
                try{
                    mBluetoothLeService.WriteValue(data[6]);
                    h.postDelayed(r8, 10);
                }catch(Exception e){e.printStackTrace();}
            }
        };

        final Runnable r6 = new Runnable() {
            @Override
            public void run() {
                try{
                    mBluetoothLeService.WriteValue(data[5]);
                    h.postDelayed(r7, 10);
                }catch(Exception e){e.printStackTrace();}
            }
        };

        final Runnable r5 = new Runnable() {
            @Override
            public void run() {
                try{
                    mBluetoothLeService.WriteValue(data[4]);
                    h.postDelayed(r6, 10);
                }catch(Exception e){e.printStackTrace();}
            }
        };

        final Runnable r4 = new Runnable() {
            @Override
            public void run() {
                try{
                    mBluetoothLeService.WriteValue(data[3]);
                    h.postDelayed(r5, 10);
                }catch(Exception e){e.printStackTrace();}
            }
        };

        final Runnable r3 = new Runnable() {
            @Override
            public void run() {
                try {
                    mBluetoothLeService.WriteValue(data[2]);
                    h.postDelayed(r4, 10);
                }catch(Exception e){e.printStackTrace();}
            }
        };
        final Runnable r2 = new Runnable() {
            @Override
            public void run() {
                try {
                    mBluetoothLeService.WriteValue(data[1]);
                    h.postDelayed(r3, 10);
                }catch(Exception e){e.printStackTrace();}
            }
        };

        Runnable r1 = new Runnable() {
            @Override
            public void run() {
                try {
                    mBluetoothLeService.WriteValue(data[1]);
                    h.postDelayed(r2, 10);
                }catch(Exception e){e.printStackTrace();}
            }
        };
        h.postDelayed(r1, 10);
    }


    public String[] splitterB(String value){
        List<String> list= new ArrayList<String>();
        for(int i=0; i<value.length(); i=i+20){
            int endIndex = Math.min(i+20, value.length());
            list.add(value.substring(i, endIndex));
        }

        return list.toArray(new String[16]);
    }

    @Override
    protected void onResume() {
        super.onResume();
        /*registerReceiver(mGattUpdateReceiver, makeGattUpdateIntentFilter());
        if (mBluetoothLeService != null) {
            final boolean result = mBluetoothLeService.connect(mDeviceAddress);
            Log.d(TAG, "Connect request result=" + result);
        }*/
    }

    @Override
    protected void onPause() {
        super.onPause();
        unregisterReceiver(mGattUpdateReceiver);
        unbindService(mServiceConnection);
    }

    @Override
    public void onDestroy() {
        super.onDestroy();
        //this.unregisterReceiver(mGattUpdateReceiver);
        //unbindService(mServiceConnection);
        if(mBluetoothLeService != null)
        {
            mBluetoothLeService.close();
            mBluetoothLeService = null;
        }
        Log.d(TAG, "We are in destroy");
    }

    @Override
    public boolean onCreateOptionsMenu(Menu menu) {
        getMenuInflater().inflate(R.menu.gatt_services, menu);
        if (mConnected) {
            menu.findItem(R.id.menu_connect).setVisible(false);
            menu.findItem(R.id.menu_disconnect).setVisible(true);
        } else {
            menu.findItem(R.id.menu_connect).setVisible(true);
            menu.findItem(R.id.menu_disconnect).setVisible(false);
        }
        return true;
    }

    @Override
    public boolean onOptionsItemSelected(MenuItem item) {
        switch(item.getItemId()) {
            case R.id.menu_connect:
                mBluetoothLeService.connect(mDeviceAddress);
                return true;
            case R.id.menu_disconnect:
                mBluetoothLeService.disconnect();
                return true;
            case android.R.id.home:
                if(mConnected)
                {
                    mBluetoothLeService.disconnect();
                    mConnected = false;
                }
                onBackPressed();
                return true;
        }
        return super.onOptionsItemSelected(item);
    }

    private void ShowDialog()
    {
        Toast.makeText(this, "info 2", Toast.LENGTH_SHORT).show();
    }

    private static IntentFilter makeGattUpdateIntentFilter() {
        final IntentFilter intentFilter = new IntentFilter();
        intentFilter.addAction(BluetoothLeService.ACTION_GATT_CONNECTED);
        intentFilter.addAction(BluetoothLeService.ACTION_GATT_DISCONNECTED);
        intentFilter.addAction(BluetoothLeService.ACTION_GATT_SERVICES_DISCOVERED);
        intentFilter.addAction(BluetoothLeService.ACTION_DATA_AVAILABLE);
        intentFilter.addAction(BluetoothDevice.ACTION_UUID);
        return intentFilter;
    }

    @Override
    public void onDeviceConnected() {
        Toast.makeText(BLESendActivity.this, "Connected", Toast.LENGTH_LONG).show();
    }

    @Override
    public void onDeviceDisconnected(BLEError bleError) {
        Toast.makeText(BLESendActivity.this, "Disconnected", Toast.LENGTH_LONG).show();
    }

    @Override
    public void onOtauAvailable() {

    }

    @Override
    public void onOtauRecovery(DeviceDatabase.DeviceData deviceData) {

    }
}

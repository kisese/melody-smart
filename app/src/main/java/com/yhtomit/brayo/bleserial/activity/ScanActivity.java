package com.yhtomit.brayo.bleserial.activity;

import android.app.AlertDialog;
import android.app.ListActivity;
import android.bluetooth.BluetoothAdapter;
import android.bluetooth.BluetoothDevice;
import android.content.Intent;
import android.os.Bundle;
import android.util.Log;
import android.view.LayoutInflater;
import android.view.Menu;
import android.view.MenuItem;
import android.view.View;
import android.view.ViewGroup;
import android.widget.BaseAdapter;
import android.widget.Button;
import android.widget.LinearLayout;
import android.widget.ListView;
import android.widget.TextView;
import android.widget.Toast;
import android.widget.Toolbar;

import com.bluecreation.melodysmart.MelodySmartDevice;

import com.gc.materialdesign.views.ButtonRectangle;
import com.yhtomit.brayo.bleserial.R;

import java.util.ArrayList;

/**
 * Created by genis on 20/10/2014.
 */
/**
 * Activity for scanning and displaying available Bluetooth LE devices.
 */
public class ScanActivity extends ListActivity {
    private LeDeviceListAdapter mLeDeviceListAdapter;
    private boolean mScanning;

    private static final int REQUEST_ENABLE_BT      = 1;
    private static final int REQUEST_OPEN_DEVICE    = 2;

    protected static final String TAG = ScanActivity.class.getSimpleName();

    protected String mDeviceAddress;
    protected String mDeviceName;
    private AlertDialog connectingDialog;

    private MelodySmartDevice melodySmartDevice;
    private String send_string;
    private ButtonRectangle scan_device;

    @Override
    public void onCreate(Bundle savedInstanceState) {
        super.onCreate(savedInstanceState);
        setContentView(R.layout.ble_sender);
        setTitle(String.format("%s v%s", getString(R.string.app_name), "BlE"));
        getListView().setPadding(4, 4, 4, 4);

        Intent i= getIntent();
        send_string = i.getStringExtra("send_string");
       // Toast.makeText(ScanActivity.this, send_string, Toast.LENGTH_LONG).show();
        melodySmartDevice = MelodySmartDevice.getInstance();
        melodySmartDevice.init(this);

        scan_device = (ButtonRectangle)findViewById(R.id.btn_scan);
        scan_device.setOnClickListener(new View.OnClickListener() {
            @Override
            public void onClick(View v) {
                scanLeDevice(true);
            }
        });

        enableBT();
        scanLeDevice(true);
        // Initializes list view adapter.
        mLeDeviceListAdapter = new LeDeviceListAdapter(getLayoutInflater());
        setListAdapter(mLeDeviceListAdapter);
    }

    @Override
    protected void onDestroy() {
        Log.d(TAG, "onDestroy()");
        melodySmartDevice.disconnect();
        melodySmartDevice.close(this);
        super.onDestroy();
    }

    @Override
    public boolean onCreateOptionsMenu(Menu menu) {
        getMenuInflater().inflate(R.menu.main, menu);
        if (!mScanning) {
            menu.findItem(R.id.menu_stop).setVisible(false);
            menu.findItem(R.id.menu_scan).setVisible(true);
            menu.findItem(R.id.menu_refresh).setActionView(null);
        } else {
            menu.findItem(R.id.menu_stop).setVisible(true);
            menu.findItem(R.id.menu_scan).setVisible(false);
            menu.findItem(R.id.menu_refresh).setActionView(R.layout.actionbar_indeterminate_progress);
        }
        return true;
    }

    @Override
    public boolean onOptionsItemSelected(MenuItem item) {
        switch (item.getItemId()) {
            case R.id.menu_scan:
                mLeDeviceListAdapter.clear();
                scanLeDevice(true);
                break;

            case R.id.menu_stop:
                scanLeDevice(false);
                break;
        }

        return true;
    }

    @Override
    protected void onListItemClick(ListView l, View v, int position, long id) {
        LeDeviceListAdapter.ViewHolder viewHolder = (LeDeviceListAdapter.ViewHolder) v.getTag();

        scanLeDevice(false);

        mDeviceAddress = viewHolder.deviceAddress.getText().toString();
        mDeviceName = viewHolder.deviceName.getText().toString();

        Intent i = new Intent(getApplicationContext(), MelodySmartTestActivity.class);
        i.putExtra("deviceAddress", mDeviceAddress);
        i.putExtra("deviceName", mDeviceName);
        i.putExtra("send_string", send_string.replace("#", ""));
        startActivity(i);
        //finish();
    }

    private synchronized void scanLeDevice(final boolean enable) {
        mScanning = enable;
        if (enable) {
            melodySmartDevice.startLeScan(mLeScanCallback);
        } else {
            melodySmartDevice.stopLeScan(mLeScanCallback);
        }
        invalidateOptionsMenu();
    }

    // Adapter for holding devices found through scanning.
    private static class LeDeviceListAdapter extends BaseAdapter {
        private final ArrayList<BluetoothDevice> mLeDevices;
        private final LayoutInflater mInflater;

        static class ViewHolder {
            TextView deviceName;
            TextView deviceAddress;
            Button button;
        }

        public LeDeviceListAdapter(LayoutInflater layoutInflater) {
            super();
            mLeDevices = new ArrayList<>();
            mInflater = layoutInflater;
        }

        public void addDevice(BluetoothDevice device) {
            if (!mLeDevices.contains(device)) {
                mLeDevices.add(device);
                notifyDataSetChanged();
            }
        }

        public void clear() {
            mLeDevices.clear();
            notifyDataSetChanged();
        }

        @Override
        public int getCount() {
            return mLeDevices.size();
        }

        @Override
        public Object getItem(int i) {
            return mLeDevices.get(i);
        }

        @Override
        public long getItemId(int i) {
            return i;
        }

        @Override
        public View getView(int i, View view, ViewGroup viewGroup) {
            ViewHolder viewHolder;

            // General ListView optimization code.
            if (view == null) {
                view = mInflater.inflate(R.layout.listitem_device, viewGroup, false);
                viewHolder = new ViewHolder();
                viewHolder.deviceAddress = (TextView) view.findViewById(R.id.device_address);
                viewHolder.deviceName = (TextView) view.findViewById(R.id.device_name);
                view.setTag(viewHolder);
            } else {
                viewHolder = (ViewHolder) view.getTag();
            }

            BluetoothDevice device = mLeDevices.get(i);
            final String deviceName = device.getName();
            if (deviceName != null && deviceName.length() > 0) {
                viewHolder.deviceName.setText(deviceName);
            } else {
                viewHolder.deviceName.setText("Unknown device");
            }
            viewHolder.deviceAddress.setText(device.getAddress());

            return view;
        }
    }

    // Device scan callback.
    private final BluetoothAdapter.LeScanCallback mLeScanCallback = new BluetoothAdapter.LeScanCallback() {
        @Override
        public void onLeScan(final BluetoothDevice device, int rssi, byte[] scanRecord) {
            runOnUiThread(new Runnable() {
                @Override
                public void run() {
                    Log.d(TAG, "Found device " + device.getAddress());
                    mLeDeviceListAdapter.addDevice(device);
                }
            });
        }
    };

    public void enableBT(){
        BluetoothAdapter mBluetoothAdapter = BluetoothAdapter.getDefaultAdapter();
        if (!mBluetoothAdapter.isEnabled()){
            Intent intentBtEnabled = new Intent(BluetoothAdapter.ACTION_REQUEST_ENABLE);
            // The REQUEST_ENABLE_BT constant passed to startActivityForResult() is a locally defined integer (which must be greater than 0), that the system passes back to you in your onActivityResult()
            // implementation as the requestCode parameter.
            int REQUEST_ENABLE_BT = 1;
            startActivityForResult(intentBtEnabled, REQUEST_ENABLE_BT);
        }
    }
}
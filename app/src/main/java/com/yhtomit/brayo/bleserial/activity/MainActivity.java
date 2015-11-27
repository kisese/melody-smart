package com.yhtomit.brayo.bleserial.activity;

import android.bluetooth.BluetoothDevice;
import android.content.BroadcastReceiver;
import android.content.ComponentName;
import android.content.Context;
import android.content.Intent;
import android.content.IntentFilter;
import android.content.ServiceConnection;
import android.database.Cursor;
import android.os.Bundle;
import android.os.IBinder;
import android.support.v7.app.ActionBarActivity;
import android.support.v7.widget.Toolbar;
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

import com.yhtomit.brayo.bleserial.R;
import com.yhtomit.brayo.bleserial.magic.MagicBox;
import com.yhtomit.brayo.bleserial.storagesqlite.FingerColorsDBAdapter;

public class MainActivity extends ActionBarActivity {

    private Toolbar mToolbar;
    private MagicBox magicBox;
    private Cursor cursor;
    private FingerColorsDBAdapter fingerColorsDBAdapter;
    private String finger_1_color;
    private final static String TAG = MainActivity.class.getSimpleName();
    public static final String EXTRAS_DEVICE_NAME = "DEVICE_NAME";
    public static final String EXTRAS_DEVICE_ADDRESS = "DEVICE_ADDRESS";

    @Override
    protected void onCreate(Bundle savedInstanceState) {
        super.onCreate(savedInstanceState);
        overridePendingTransition(R.anim.slide_in_left, R.anim.slide_out_right);
        setContentView(R.layout.menu_selection);
        fingerColorsDBAdapter = new FingerColorsDBAdapter(this);
        magicBox = new MagicBox(this);

        cursor = fingerColorsDBAdapter.queryFilter("finger 1");
        if (cursor.moveToFirst()){
            do{
                finger_1_color = cursor.getString(cursor.getColumnIndex("color_1"));
            }while(cursor.moveToNext());
        }
        mToolbar = (Toolbar) findViewById(R.id.toolbar);
        setSupportActionBar(mToolbar);
    }

    public void createGloveset(View view){
        final Intent intent = new Intent(MainActivity.this, MotionRegularActivity.class);
        startActivity(intent);
    }

    public void savedGlovesets(View view){
        final Intent intent = new Intent(MainActivity.this, AllPresetsActivity.class);
        startActivity(intent);
    }
}

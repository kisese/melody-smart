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

import com.gc.materialdesign.views.ButtonRectangle;
import com.yhtomit.brayo.bleserial.R;
import com.yhtomit.brayo.bleserial.magic.MagicBox;
import com.yhtomit.brayo.bleserial.storagesqlite.FingerColorsDBAdapter;

/**
 * Created by Brayo on 7/24/2015.
 */
public class SendSaveActivity extends ActionBarActivity {

    private String hand = "";
    private String effect = "";
    private String finger_1_pattern = "";
    private String finger_1_color = "";
    private String finger_2_color = "";
    private String finger_2_pattern = "";
    private String finger_3_color = "";
    private String finger_3_pattern = "";
    private String finger_4_color = "";
    private String finger_4_pattern = "";
    private TextView string;
    private FingerColorsDBAdapter fingerColorsDBAdapter;
    private Cursor cursor;
    private String[] finger_1_colors = new String[6];
    private String[] finger_2_colors = new String[6];
    private String[] finger_3_colors = new String[6];
    private String[] finger_4_colors = new String[6];
    private String send_string = "";
    private String finger_1_string = "";
    private String finger_2_string = "";
    private String finger_3_string = "";
    private String finger_4_string = "";
    private String mode = "mode_1";  //all fingers
    private ButtonRectangle btn_save;
    private ButtonRectangle btn_send;
    private MagicBox magicBox;
    private Toolbar mToolbar;

    EditText edtSend;
    private String all_fingers;

    @Override
    protected void onCreate(Bundle savedInstanceState) {
        super.onCreate(savedInstanceState);
        setContentView(R.layout.send_activity);


        string = (TextView) findViewById(R.id.string);
        btn_save = (ButtonRectangle) findViewById(R.id.btn_save);
        btn_send = (ButtonRectangle) findViewById(R.id.btn_send);
        fingerColorsDBAdapter = new FingerColorsDBAdapter(this);
        magicBox = new MagicBox(this);

        Intent intent = getIntent();
        hand = intent.getStringExtra("hand");
        effect = intent.getStringExtra("effect");
        finger_1_color = intent.getStringExtra("finger_1_color");
        finger_1_pattern = intent.getStringExtra("finger_1_pattern");
        finger_2_color = intent.getStringExtra("finger_2_color");
        finger_2_pattern = intent.getStringExtra("finger_2_pattern");
        finger_3_color = intent.getStringExtra("finger_3_color");
        finger_3_pattern = intent.getStringExtra("finger_3_pattern");
        finger_4_color = intent.getStringExtra("finger_4_color");
        finger_4_pattern = intent.getStringExtra("finger_4_pattern");

        all_fingers = intent.getStringExtra("all_fingers");

        getColors();

        send_string = hand + "," +
                effect;

        if (all_fingers==null){
            finger_1_string = "F1" + "," +
                    finger_1_pattern + ";" +
                    finger_1_colors[0] + ";" +
                    finger_1_colors[1] + ";" +
                    finger_1_colors[2] + ";" +
                    finger_1_colors[3] + ";" +
                    finger_1_colors[4] + ";" +
                    finger_1_colors[5]
                    ;
    }else{
            finger_1_string = "F0" + "," +
                    finger_1_pattern + ";" +
                    finger_1_colors[0] + ";" +
                    finger_1_colors[1] + ";" +
                    finger_1_colors[2] + ";" +
                    finger_1_colors[3] + ";" +
                    finger_1_colors[4] + ";" +
                    finger_1_colors[5]
            ;
        }

    if (finger_2_color==null) {
        mode = "mode_2";
        send_string = send_string + "," + finger_1_string;
        string.setText(send_string);
        string.setVisibility(View.GONE);
       // Toast.makeText(SendSaveActivity.this, "mode 2" + mode, Toast.LENGTH_LONG).show();
    }

    if(mode.equals("mode_1")){
       // Toast.makeText(SendSaveActivity.this, mode, Toast.LENGTH_LONG).show();

        finger_2_string = "F2" + "," +
                finger_2_pattern + ";" +
                finger_2_colors[0] + ";" +
                finger_2_colors[1] + ";" +
                finger_2_colors[2] + ";" +
                finger_2_colors[3] + ";" +
                finger_2_colors[4] + ";" +
                finger_2_colors[5]
        ;

        finger_3_string = "F3" + "," +
                finger_3_pattern + ";" +
                finger_3_colors[0] + ";" +
                finger_3_colors[1] + ";" +
                finger_3_colors[2] + ";" +
                finger_3_colors[3] + ";" +
                finger_3_colors[4] + ";" +
                finger_3_colors[5]
        ;

        finger_4_string = "F4" + "," +
                finger_4_pattern + ";" +
                finger_4_colors[0] + ";" +
                finger_4_colors[1] + ";" +
                finger_4_colors[2] + ";" +
                finger_4_colors[3] + ";" +
                finger_4_colors[4] + ";" +
                finger_4_colors[5]
        ;


                string.setText(
                hand + " -- " +
                effect+ " -- " +
                finger_1_color+ " -- " +
                finger_1_pattern+ " -- " +
                finger_2_color+ " -- " +
                finger_2_pattern+ " -- " +
                finger_3_color+ " -- " +
                finger_3_pattern+ " -- " +
                finger_4_color+ " -- " +
                finger_4_pattern + " "+ finger_3_colors[2]);

            send_string = send_string + "," + finger_1_string + "|" +
                    finger_2_string + "|" + finger_3_string + "|" +
                    finger_4_string;
            string.setText(send_string);
        }

//        Toast.makeText(this,
//                hand + " -- " +
//                        hand + " -- " +
//                        effect+ " -- " +
//                        finger_1_color+ " -- " +
//                        finger_1_pattern+ " -- " +
//                        finger_2_color+ " -- " +
//                        finger_2_pattern+ " -- " +
//                        finger_3_color+ " -- " +
//                        finger_3_pattern+ " -- " +
//                        finger_4_color+ " -- " +
//                        finger_4_pattern
//                ,
//                Toast.LENGTH_LONG).show();


//        mBluetoothLeService.connect(mDeviceAddress);

        btn_save.setOnClickListener(new View.OnClickListener() {
            @Override
            public void onClick(View v) {
                magicBox.addPresetDialog(send_string);
            }
        });


        mToolbar = (Toolbar) findViewById(R.id.toolbar);
        mToolbar.setOnClickListener(new View.OnClickListener() {
            @Override
            public void onClick(View v) {
                Intent i3 = new Intent(SendSaveActivity.this, MainActivity.class);
                startActivity(i3);
            }
        });
        setSupportActionBar(mToolbar);
       
        btn_send.setOnClickListener(new View.OnClickListener() {
            @Override
            public void onClick(View v) {

                if (send_string.length() < 1) {
                   // Toast.makeText(SendSaveActivity.this, "info", Toast.LENGTH_SHORT).show();
                    return;
                }else {
                    Intent i = new Intent(SendSaveActivity.this, ScanActivity.class);
                    i.putExtra("send_string", send_string);
                    startActivity(i);
                }
            }

        });

    }

    public void getColors(){
        cursor = fingerColorsDBAdapter.queryFilter(finger_1_color);
        if (cursor.moveToFirst()){
            do{
                finger_1_colors[0] = cursor.getString(cursor.getColumnIndex("color_1"));
                finger_1_colors[1] = cursor.getString(cursor.getColumnIndex("color_2"));
                finger_1_colors[2] = cursor.getString(cursor.getColumnIndex("color_3"));
                finger_1_colors[3] = cursor.getString(cursor.getColumnIndex("color_4"));
                finger_1_colors[4] = cursor.getString(cursor.getColumnIndex("color_5"));
                finger_1_colors[5] = cursor.getString(cursor.getColumnIndex("color_6"));
            }while(cursor.moveToNext());
        }

     //   Toast.makeText(SendSaveActivity.this, finger_1_colors[1], Toast.LENGTH_SHORT).show();
        
        cursor = fingerColorsDBAdapter.queryFilter(finger_2_color);
        if (cursor.moveToFirst()){
            do{
                finger_2_colors[0] = cursor.getString(cursor.getColumnIndex("color_1"));
                finger_2_colors[1] = cursor.getString(cursor.getColumnIndex("color_2"));
                finger_2_colors[2] = cursor.getString(cursor.getColumnIndex("color_3"));
                finger_2_colors[3] = cursor.getString(cursor.getColumnIndex("color_4"));
                finger_2_colors[4] = cursor.getString(cursor.getColumnIndex("color_5"));
                finger_2_colors[5] = cursor.getString(cursor.getColumnIndex("color_6"));
            }while(cursor.moveToNext());
        }

        cursor = fingerColorsDBAdapter.queryFilter(finger_3_color);
        if (cursor.moveToFirst()){
            do{
                finger_3_colors[0] = cursor.getString(cursor.getColumnIndex("color_1"));
                finger_3_colors[1] = cursor.getString(cursor.getColumnIndex("color_2"));
                finger_3_colors[2] = cursor.getString(cursor.getColumnIndex("color_3"));
                finger_3_colors[3] = cursor.getString(cursor.getColumnIndex("color_4"));
                finger_3_colors[4] = cursor.getString(cursor.getColumnIndex("color_5"));
                finger_3_colors[5] = cursor.getString(cursor.getColumnIndex("color_6"));
            }while(cursor.moveToNext());
        }

        cursor = fingerColorsDBAdapter.queryFilter(finger_4_color);
        if (cursor.moveToFirst()){
            do{
                finger_4_colors[0] = cursor.getString(cursor.getColumnIndex("color_1"));
                finger_4_colors[1] = cursor.getString(cursor.getColumnIndex("color_2"));
                finger_4_colors[2] = cursor.getString(cursor.getColumnIndex("color_3"));
                finger_4_colors[3] = cursor.getString(cursor.getColumnIndex("color_4"));
                finger_4_colors[4] = cursor.getString(cursor.getColumnIndex("color_5"));
                finger_4_colors[5] = cursor.getString(cursor.getColumnIndex("color_6"));
            }while(cursor.moveToNext());
        }
    }
}

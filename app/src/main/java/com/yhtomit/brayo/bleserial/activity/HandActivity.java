package com.yhtomit.brayo.bleserial.activity;

import android.bluetooth.BluetoothDevice;
import android.content.Intent;
import android.content.IntentFilter;
import android.graphics.drawable.ColorDrawable;
import android.os.Bundle;
import android.support.v4.app.Fragment;
import android.support.v7.app.ActionBarActivity;
import android.support.v7.widget.Toolbar;
import android.text.Html;
import android.util.Log;
import android.view.LayoutInflater;
import android.view.View;
import android.view.ViewGroup;
import android.widget.TextView;
import android.widget.Toast;

import com.gc.materialdesign.views.ButtonRectangle;
import com.yhtomit.brayo.bleserial.R;
import com.yhtomit.brayo.bleserial.magic.MagicBox;

/**
 * Created by Brayo on 7/7/2015.
 */
public class HandActivity extends ActionBarActivity {
    private ButtonRectangle left;
    private MagicBox magicBox;
    private Toolbar mToolbar;
    private ButtonRectangle right;
    private ButtonRectangle both;
    private Intent i = null;
    ConnectionManager connectionManager = new ConnectionManager();
    private TextView mDataField;

    @Override
    public void onCreate(Bundle savedInstanceState) {
        super.onCreate(savedInstanceState);
        setContentView(R.layout.hand_selection);

        i = new Intent(HandActivity.this, EffectsActivity.class);
        left = (ButtonRectangle)findViewById(R.id.btn_left_hand);
        right = (ButtonRectangle)findViewById(R.id.btn_right_hand);
        both = (ButtonRectangle)findViewById(R.id.btn_both_hands);


        clickListeners();

        mToolbar = (Toolbar) findViewById(R.id.toolbar);
        mToolbar.setTitle("Hand Selection");
        setSupportActionBar(mToolbar);
    }

    public void clickListeners(){

        left.setOnClickListener(new View.OnClickListener() {
            @Override
            public void onClick(View v) {
                i.putExtra("hand", "0");
                startActivity(i);
            }
        });

        right.setOnClickListener(new View.OnClickListener() {
            @Override
            public void onClick(View v) {
                i.putExtra("hand", "1");
                startActivity(i);
            }
        });

        both.setOnClickListener(new View.OnClickListener() {
            @Override
            public void onClick(View v) {
                i.putExtra("hand", "2");
                startActivity(i);
            }
        });
    }
}

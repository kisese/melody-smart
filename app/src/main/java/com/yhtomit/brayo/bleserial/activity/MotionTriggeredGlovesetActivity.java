package com.yhtomit.brayo.bleserial.activity;

import android.bluetooth.BluetoothDevice;
import android.content.Intent;
import android.content.IntentFilter;
import android.graphics.drawable.ColorDrawable;
import android.os.Bundle;
import android.support.v7.app.ActionBarActivity;
import android.support.v7.widget.Toolbar;
import android.text.Html;
import android.util.Log;
import android.view.View;
import android.widget.AdapterView;
import android.widget.ListView;
import android.widget.SeekBar;
import android.widget.TextView;
import android.widget.Toast;

import com.yhtomit.brayo.bleserial.R;
import com.yhtomit.brayo.bleserial.model.PresetsListAdapter;
import com.yhtomit.brayo.bleserial.storagepreferences.AllColorPresets;
import com.yhtomit.brayo.bleserial.storagepreferences.ColorPresets;

import java.util.ArrayList;

/**
 * Created by Brayo on 7/26/2015.
 */
public class MotionTriggeredGlovesetActivity extends ActionBarActivity {

    private Toolbar mToolbar;
    private String[] presets;
    private String[] filtered;
    private ListView listview;
    private PresetsListAdapter list_adapter;
    private ColorPresets color_presets;
    ConnectionManager connectionManager = new ConnectionManager();
    private String TAG;
    private ArrayList<String> send_string = new ArrayList<>();
    private String hand;
    private TextView feedback;
    private SeekBar sensitivity;
    private TextView sensitivity_feedback;
    int step = 1;
    int max = 5;
    int min = 255;
    private int value = 0;

    @Override
    protected void onCreate(Bundle savedInstanceState) {
        super.onCreate(savedInstanceState);
        overridePendingTransition(R.anim.slide_in_left, R.anim.slide_out_right);
        setContentView(R.layout.motion_triggered_gloveset);

        color_presets = new ColorPresets(this);
        listview = (ListView) findViewById(R.id.preset_list);
        feedback = (TextView)findViewById(R.id.feedback);
        sensitivity = (SeekBar)findViewById(R.id.sensitivity_bar);
        sensitivity_feedback = (TextView)findViewById(R.id.sensitivity_feedback);
        sensitivity.setMax(250);
        sensitivity.setOnSeekBarChangeListener(new SeekBar.OnSeekBarChangeListener() {
            @Override
            public void onProgressChanged(SeekBar seekBar, int i, boolean b) {
                value = min - (i * step);
                sensitivity_feedback.setText(String.valueOf(value));
            }

            @Override
            public void onStartTrackingTouch(SeekBar seekBar) {

            }

            @Override
            public void onStopTrackingTouch(SeekBar seekBar) {

            }
        });

        Intent intent = getIntent();
        hand = intent.getStringExtra("hand");

        presets = color_presets.getPresets();
        list_adapter = new PresetsListAdapter(this, presets);
        listview.setAdapter(list_adapter);
        clickListeners();

        mToolbar = (Toolbar) findViewById(R.id.toolbar);
        mToolbar = (Toolbar) findViewById(R.id.toolbar);
        mToolbar.setOnClickListener(new View.OnClickListener() {
            @Override
            public void onClick(View v) {
                Intent i3 = new Intent(MotionTriggeredGlovesetActivity.this, MainActivity.class);
                startActivity(i3);
            }
        });
        setSupportActionBar(mToolbar);
    }

    //y,s23,h1:():()
    public void clickListeners() {

        listview.setOnItemClickListener(new AdapterView.OnItemClickListener() {
            @Override
            public void onItemClick(AdapterView<?> parent, View view, int position, long id) {
                //  Toast.makeText(MotionTriggeredGlovesetActivity.this,
                //   color_presets.getPreset(presets[position]), Toast.LENGTH_LONG).show();
                if (value != 0) {
                    if (send_string.size() <= 2) {
                        send_string.add(color_presets.getPreset(presets[position]));
                        feedback.setText("With motion: " + presets[position]);
                    }

                    if (send_string.size() == 2) {
                        try {
                            feedback.setText("With motion: " + presets[0] + "\n" +
                                    "Without motion: " + presets[1]);
                            Intent i = new Intent(MotionTriggeredGlovesetActivity.this, ScanActivity.class);
                            i.putExtra("send_string", "Y,S" + value + "," + hand + "|(" + send_string.get(0) + "):(" + send_string.get(1) + ")");
                            startActivity(i);
                            send_string.clear();
                        }catch (ArrayIndexOutOfBoundsException e){
                            e.printStackTrace();
                        }
                    }
                }else{
                    Toast.makeText(MotionTriggeredGlovesetActivity.this,
                            "Please set the sensitivity first", Toast.LENGTH_LONG).show();
                }
            }
        });
    }
}

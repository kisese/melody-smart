package com.yhtomit.brayo.bleserial.activity;

import android.content.Intent;
import android.graphics.Color;
import android.os.Bundle;
import android.support.v7.app.ActionBarActivity;
import android.support.v7.widget.Toolbar;
import android.text.Spannable;
import android.text.SpannableString;
import android.text.style.AbsoluteSizeSpan;
import android.view.Menu;
import android.view.MenuItem;
import android.view.View;
import android.widget.Button;
import android.widget.TextView;

import com.yhtomit.brayo.bleserial.R;
import com.yhtomit.brayo.bleserial.magic.MagicBox;

/**
 * Created by Brayo on 7/20/2015.
 */
public class MotionRegularActivity  extends ActionBarActivity {

    private Toolbar mToolbar;
    private MagicBox magicBox;
    private Button motion_triggered_gloveset;
    private Button regular_gloveset;
    ConnectionManager connectionManager = new ConnectionManager();
    private TextView mDataField;
    private Button btn_motion_triggered,btn_sound_rective, btn_sync_lights,
                   btn_regular, btn_time_controlled;
    private String TAG;

    @Override
    protected void onCreate(Bundle savedInstanceState) {
        super.onCreate(savedInstanceState);
        overridePendingTransition(R.anim.slide_in_left, R.anim.slide_out_right);
        setContentView(R.layout.motion_regular_selection);


        magicBox = new MagicBox(this);
        motion_triggered_gloveset = (Button)findViewById(R.id.btn_motion_triggered);
        regular_gloveset = (Button)findViewById(R.id.btn_regular);
        mToolbar = (Toolbar) findViewById(R.id.toolbar);
        btn_motion_triggered = (Button)findViewById(R.id.btn_motion_triggered);
        btn_sound_rective = (Button)findViewById(R.id.btn_sound_reactive);
        btn_sync_lights = (Button)findViewById(R.id.btn_sync_lights);
        btn_regular = (Button)findViewById(R.id.btn_regular);
        btn_time_controlled = (Button)findViewById(R.id.btn_motion_triggered);

        mToolbar = (Toolbar) findViewById(R.id.toolbar);
        mToolbar.setOnClickListener(new View.OnClickListener() {
            @Override
            public void onClick(View v) {
                Intent i3 = new Intent(MotionRegularActivity.this, MainActivity.class);
                startActivity(i3);
            }
        });
        setSupportActionBar(mToolbar);
        clickListeners();
    }

    private void setCustomText(Button btn, String text, String underText) {

        Spannable span = new SpannableString(text + "\n" +  underText);
        span.setSpan(new AbsoluteSizeSpan(32), 10, 12, Spannable.SPAN_EXCLUSIVE_EXCLUSIVE);
        btn.setText(span);
    }

    public void clickListeners(){
        motion_triggered_gloveset.setOnClickListener(new View.OnClickListener() {
            @Override
            public void onClick(View v) {
                Intent intent = new Intent(MotionRegularActivity.this, MotionTriggeredGlovesetActivity.class);
                intent.putExtra("hand", "H2");
                startActivity(intent);
            }
        });

        regular_gloveset.setOnClickListener(new View.OnClickListener() {
            @Override
            public void onClick(View v) {
                Intent intent = new Intent(MotionRegularActivity.this, EffectsActivity.class);
                startActivity(intent);
            }
        });
    }


    @Override
    public boolean onCreateOptionsMenu(Menu menu) {
        getMenuInflater().inflate(R.menu.menu_main, menu);
        return true;
    }

    @Override
    public boolean onOptionsItemSelected(MenuItem item) {
        int id = item.getItemId();
        if (id == R.id.action_settings) {
            return true;
        }

        return super.onOptionsItemSelected(item);
    }
}

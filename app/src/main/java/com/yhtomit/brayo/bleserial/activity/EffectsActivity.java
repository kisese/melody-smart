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
import android.widget.RadioButton;
import android.widget.RadioGroup;
import android.widget.TextView;

import com.felipecsl.gifimageview.library.GifImageView;
import com.gc.materialdesign.views.ButtonRectangle;
import com.yhtomit.brayo.bleserial.R;
import com.yhtomit.brayo.bleserial.magic.MagicBox;
import com.yhtomit.brayo.bleserial.magic.ToastMessage;

/**
 * Created by Brayo on 7/9/2015.
 */
public class EffectsActivity extends ActionBarActivity {
    private ButtonRectangle next_btn;
    private MagicBox magicBox;
    private Toolbar mToolbar;
    private Intent i = null;
    private String hand;
    private RadioGroup effects_group;
    private RadioButton knight_rider_1;
    private RadioButton knight_rider_2;
    private RadioButton knight_rider_3;
    private RadioButton knight_rider_4;
    private RadioButton ranbow;
    private RadioButton color_wheel;
    private RadioButton no_effect;
    private ToastMessage warningMessage;
    private GifImageView knight_rider_1_gif;
    private GifImageView knight_rider_2_gif;
    private GifImageView knight_rider_3_gif;
    private GifImageView knight_rider_4_gif;
    private GifImageView rainbow_gif;
    private GifImageView rainbow_cycle_gif;
    private RadioButton rainbow_cycle;
    ConnectionManager connectionManager = new ConnectionManager();
    private TextView mDataField;

    @Override
    public void onCreate(Bundle savedInstanceState) {
        super.onCreate(savedInstanceState);
        setContentView(R.layout.effect_selection);
        next_btn = (ButtonRectangle) findViewById(R.id.effects_next);
        warningMessage = new ToastMessage(this, ToastMessage.TYPE_ERROR);
        i = new Intent(EffectsActivity.this, ColorsActivity.class);


        magicBox = new MagicBox(EffectsActivity.this);
        effects_group = (RadioGroup) findViewById(R.id.effects_group);
        knight_rider_1 = (RadioButton) findViewById(R.id.effect_knight_rider_1);
        knight_rider_2 = (RadioButton) findViewById(R.id.effect_knight_rider_2);
        knight_rider_3 = (RadioButton) findViewById(R.id.effect_knight_rider_3);
        knight_rider_4 = (RadioButton) findViewById(R.id.effect_knight_rider_4);
        ranbow = (RadioButton) findViewById(R.id.effect_rainbow);
        rainbow_cycle = (RadioButton) findViewById(R.id.effect_cycle);
        no_effect = (RadioButton) findViewById(R.id.effect_no_effect);

        knight_rider_1_gif = (GifImageView) findViewById(R.id.knight_rider_1);
        knight_rider_2_gif = (GifImageView) findViewById(R.id.knight_rider_2);
        knight_rider_3_gif = (GifImageView) findViewById(R.id.knight_rider_3);
        knight_rider_4_gif = (GifImageView) findViewById(R.id.knight_rider_4);
        rainbow_gif = (GifImageView) findViewById(R.id.rainbow);
        rainbow_cycle_gif = (GifImageView) findViewById(R.id.rainbow_cycle);

        knight_rider_1_gif.startAnimation();
        knight_rider_2_gif.startAnimation();
        knight_rider_3_gif.startAnimation();
        knight_rider_4_gif.startAnimation();
        rainbow_gif.startAnimation();
        rainbow_cycle_gif.startAnimation();

        hideAllGifs(knight_rider_1_gif);
        hideAllGifs(knight_rider_2_gif);
        hideAllGifs(knight_rider_3_gif);
        hideAllGifs(knight_rider_4_gif);
        hideAllGifs(rainbow_gif);
        hideAllGifs(rainbow_cycle_gif);

        Intent intent2 = getIntent();
        hand = "2";

        clickListeners();

        mToolbar = (Toolbar) findViewById(R.id.toolbar);
        mToolbar.setTitle("Synchronization");
        setSupportActionBar(mToolbar);

    }

    public void clickListeners() {

        next_btn.setOnClickListener(new View.OnClickListener() {
            @Override
            public void onClick(View v) {
                if (knight_rider_1.isChecked() ||
                        knight_rider_2.isChecked() ||
                        knight_rider_3.isChecked() ||
                        knight_rider_4.isChecked() ||
                        ranbow.isChecked() ||
                        rainbow_cycle.isChecked() ||
                        no_effect.isChecked())
                    startActivity(i);
                else
                    warningMessage.showToastMessage("Please select an effect");
            }
        });

        effects_group.setOnCheckedChangeListener(new RadioGroup.OnCheckedChangeListener() {
            @Override
            public void onCheckedChanged(RadioGroup group, int checkedId) {
                i.putExtra("hand", hand);
                if (effects_group.getCheckedRadioButtonId() == R.id.effect_knight_rider_1) {
                    i.putExtra("effect", "E1");
                    hideAllGifsExcept(knight_rider_1_gif);
                } else if (effects_group.getCheckedRadioButtonId() == R.id.effect_knight_rider_2) {
                    i.putExtra("effect", "E2");
                    hideAllGifsExcept(knight_rider_2_gif);
                } else if (effects_group.getCheckedRadioButtonId() == R.id.effect_knight_rider_3) {
                    i.putExtra("effect", "E3");
                    hideAllGifsExcept(knight_rider_3_gif);
                } else if (effects_group.getCheckedRadioButtonId() == R.id.effect_knight_rider_4) {
                    i.putExtra("effect", "E4");
                    hideAllGifsExcept(knight_rider_4_gif);
                } else if (effects_group.getCheckedRadioButtonId() == R.id.effect_rainbow) {
                    i.putExtra("effect", "E5");
                    hideAllGifsExcept(rainbow_gif);
                } else if (effects_group.getCheckedRadioButtonId() == R.id.effect_cycle) {
                    i.putExtra("effect", "E6");
                    hideAllGifsExcept(rainbow_cycle_gif);
                } else if (effects_group.getCheckedRadioButtonId() == R.id.effect_no_effect) {
                    i.putExtra("effect", "E0");
                    hideAll();
                }
            }
        });
    }

    public void hideAllGifs(View view) {
        view.setVisibility(View.INVISIBLE);
    }

    public void hideAllGifsExcept(View view) {
        hideAllGifs(knight_rider_1_gif);
        hideAllGifs(knight_rider_2_gif);
        hideAllGifs(knight_rider_3_gif);
        hideAllGifs(knight_rider_4_gif);
        hideAllGifs(rainbow_gif);
        hideAllGifs(rainbow_cycle_gif);
        view.setVisibility(View.VISIBLE);
    }

    public void hideAll() {
        hideAllGifs(knight_rider_1_gif);
        hideAllGifs(knight_rider_2_gif);
        hideAllGifs(knight_rider_3_gif);
        hideAllGifs(knight_rider_4_gif);
        hideAllGifs(rainbow_gif);
        hideAllGifs(rainbow_cycle_gif);
    }
}


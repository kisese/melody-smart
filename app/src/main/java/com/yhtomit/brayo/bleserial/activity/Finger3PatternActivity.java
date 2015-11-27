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

import com.felipecsl.gifimageview.library.GifImageView;
import com.gc.materialdesign.views.ButtonRectangle;
import android.widget.CheckBox;
import android.widget.RadioButton;
import android.widget.RadioGroup;
import android.widget.TextView;
import android.widget.Toast;

import com.yhtomit.brayo.bleserial.R;
import com.yhtomit.brayo.bleserial.magic.MagicBox;
import com.yhtomit.brayo.bleserial.magic.ToastMessage;


/**
 * Created by Brayo on 7/20/2015.
 */
public class Finger3PatternActivity  extends ActionBarActivity {

    private MagicBox magicBox;
    private ButtonRectangle next_btn;
    private CheckBox no_pattern;
    private Toolbar mToolbar;
    private RadioGroup pallete_radiogroup;
    private RadioButton finger_1;
    private RadioButton finger_2;
    private RadioButton finger_3;
    private RadioButton finger_4;
    private RadioGroup patterns_group;
    private RadioButton pattern_strobe;
    private RadioButton pattern_hyperstrobe;
    private RadioButton pattern_dops;
    private RadioButton pattern_strobie;
    private RadioButton pattern_chroma;
    private ToastMessage warningMessage;
    private String hand;
    private String effect;
    private String finger_1_pattern;
    private String finger_1_color;
    private Intent i;
    private GifImageView strobe_gif;
    private GifImageView hyperstrobe_gif;
    private GifImageView dops_gif;
    private GifImageView strobie_gif;
    private GifImageView chroma_gif;
    private String finger_2_color;
    private String finger_2_pattern;
    ConnectionManager connectionManager = new ConnectionManager();
    private PatternViewer patternViewer;

    @Override
    public void onCreate(Bundle savedInstanceState) {
        super.onCreate(savedInstanceState);
        overridePendingTransition(R.anim.slide_in_left, R.anim.slide_out_right);
        setContentView(R.layout.pattern_selection);
        i = new Intent(Finger3PatternActivity.this, Finger4PatternActivity.class);
        warningMessage = new ToastMessage(this, ToastMessage.TYPE_ERROR);

        patternViewer = new PatternViewer(this);

        magicBox = new MagicBox(this);
        magicBox = new MagicBox(this);
        Intent intent2 = getIntent();
        hand = intent2.getStringExtra("hand");
        effect = intent2.getStringExtra("effect");
        finger_1_color = intent2.getStringExtra("finger_1_color");
        finger_1_pattern = intent2.getStringExtra("finger_1_pattern");
        finger_2_color = intent2.getStringExtra("finger_2_color");
        finger_2_pattern = intent2.getStringExtra("finger_2_pattern");

        next_btn = (ButtonRectangle)findViewById(R.id.patterns_next);
        no_pattern = (CheckBox) findViewById(R.id.no_pattern);
        pallete_radiogroup = (RadioGroup) findViewById(R.id.patterns_colors);
        patterns_group = (RadioGroup)findViewById(R.id.patterns_group);
        finger_1 = (RadioButton) findViewById(R.id.finger_1_radio);
        finger_2 = (RadioButton) findViewById(R.id.finger_2_radio);
        finger_3 = (RadioButton) findViewById(R.id.finger_3_radio);
        finger_4 = (RadioButton) findViewById(R.id.finger_4_radio);

        patterns_group = (RadioGroup)findViewById(R.id.patterns_group);
        pattern_strobe = (RadioButton)findViewById(R.id.pattern_strobe);
        pattern_hyperstrobe = (RadioButton)findViewById(R.id.pattern_hyperstrobe);
        pattern_dops = (RadioButton)findViewById(R.id.pattern_dops);
        pattern_strobie = (RadioButton)findViewById(R.id.pattern_strobie);
        pattern_chroma = (RadioButton)findViewById(R.id.pattern_chroma);

        strobe_gif = (GifImageView)findViewById(R.id.strobe);
        hyperstrobe_gif = (GifImageView)findViewById(R.id.hyperstrobe);
        dops_gif = (GifImageView)findViewById(R.id.dops);
        strobie_gif = (GifImageView)findViewById(R.id.strobie);
        chroma_gif = (GifImageView)findViewById(R.id.chroma);

        patternViewer.hideViews(strobe_gif);
        patternViewer.hideViews(hyperstrobe_gif);
        patternViewer.hideViews(dops_gif);
        patternViewer.hideViews(strobie_gif);
        patternViewer.hideViews(chroma_gif);

        clickListeners();
        no_pattern.setVisibility(View.GONE);
        
        next_btn.setOnClickListener(new View.OnClickListener() {
            @Override
            public void onClick(View v) {
                if (pattern_strobe.isChecked() ||
                        pattern_hyperstrobe.isChecked() ||
                        pattern_dops.isChecked() ||
                        pattern_strobie.isChecked() ||
                        pattern_chroma.isChecked()
                        ) {
                    if (finger_1.isChecked() ||
                            finger_2.isChecked() ||
                            finger_3.isChecked() ||
                            finger_4.isChecked()
                            ) {
                        i.putExtra("hand", hand);
                        i.putExtra("effect", effect);
                        i.putExtra("finger_1_color", finger_1_color);
                        i.putExtra("finger_1_pattern", finger_1_pattern);
                        i.putExtra("finger_2_color", finger_2_color);
                        i.putExtra("finger_2_pattern", finger_2_pattern);
                        startActivity(i);
                    } else
                        warningMessage.showToastMessage("Please select a finger");
                    } else
                        warningMessage.showToastMessage("Please select a pattern");
                }
        });
        mToolbar = (Toolbar) findViewById(R.id.toolbar);
        mToolbar.setTitle("Pattern 3");
        setSupportActionBar(mToolbar);
    }

    public void clickListeners() {
        pallete_radiogroup.setOnCheckedChangeListener(new RadioGroup.OnCheckedChangeListener() {
            @Override
            public void onCheckedChanged(RadioGroup group, int checkedId) {
                if (pallete_radiogroup.getCheckedRadioButtonId() == R.id.finger_1_radio) {
                    i.putExtra("finger_3_color", "finger 1");
                } else if (pallete_radiogroup.getCheckedRadioButtonId() == R.id.finger_2_radio) {
                    i.putExtra("finger_3_color", "finger 2");
                } else if (pallete_radiogroup.getCheckedRadioButtonId() == R.id.finger_3_radio) {
                    i.putExtra("finger_3_color", "finger 3");
                } else if (pallete_radiogroup.getCheckedRadioButtonId() == R.id.finger_4_radio) {
                    i.putExtra("finger_3_color", "finger 4");
                }
            }
        });

        patterns_group.setOnCheckedChangeListener(new RadioGroup.OnCheckedChangeListener() {
            @Override
            public void onCheckedChanged(RadioGroup group, int checkedId) {
                if (patterns_group.getCheckedRadioButtonId() == R.id.pattern_strobe) {
                    i.putExtra("finger_3_pattern", "P1");
                    patternViewer.hideViewExcept(strobe_gif, hyperstrobe_gif, dops_gif, strobie_gif, chroma_gif);
                } else if (patterns_group.getCheckedRadioButtonId() == R.id.pattern_hyperstrobe) {
                    i.putExtra("finger_3_pattern", "P2");
                    patternViewer.hideViewExcept(hyperstrobe_gif, strobe_gif, dops_gif, strobie_gif, chroma_gif);
                }else if (patterns_group.getCheckedRadioButtonId() == R.id.pattern_dops) {
                    i.putExtra("finger_3_pattern", "P3");
                    patternViewer.hideViewExcept(dops_gif, strobe_gif, hyperstrobe_gif, strobie_gif, chroma_gif);
                }else if (patterns_group.getCheckedRadioButtonId() == R.id.pattern_strobie) {
                    i.putExtra("finger_3_pattern", "P4");
                    patternViewer.hideViewExcept(strobie_gif, strobe_gif, hyperstrobe_gif, dops_gif, chroma_gif);
                } else if (patterns_group.getCheckedRadioButtonId() == R.id.pattern_chroma) {
                    i.putExtra("finger_3_pattern", "P5");
                    patternViewer.hideViewExcept(chroma_gif, hyperstrobe_gif, strobe_gif, dops_gif, strobie_gif);
                }
            }
        });
    }
}

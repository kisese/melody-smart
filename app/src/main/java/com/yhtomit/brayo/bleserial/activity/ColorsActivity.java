package com.yhtomit.brayo.bleserial.activity;


import android.content.Intent;
import android.graphics.Color;
import android.graphics.drawable.ColorDrawable;
import android.os.Bundle;
import android.support.v7.app.ActionBarActivity;
import android.support.v7.widget.Toolbar;
import android.text.Html;
import android.view.View;
import android.widget.RadioButton;
import android.widget.RadioGroup;
import android.widget.Toast;

import android.widget.Button;

import com.afollestad.materialdialogs.MaterialDialog;
import com.gc.materialdesign.views.ButtonFlat;
import com.gc.materialdesign.views.ButtonRectangle;
import com.yhtomit.brayo.bleserial.R;
import com.yhtomit.brayo.bleserial.magic.GetDate;
import com.yhtomit.brayo.bleserial.magic.MagicBox;
import com.yhtomit.brayo.bleserial.magic.ToastMessage;
import com.yhtomit.brayo.bleserial.storagepreferences.ColorPresets;
import com.yhtomit.brayo.bleserial.storagesqlite.DBOpenHelper;
import com.yhtomit.brayo.bleserial.storagesqlite.FingerColorsDBAdapter;

import java.util.ArrayList;
import java.util.HashMap;
import java.util.List;

import yuku.ambilwarna.AmbilWarnaDialog;

/**
 * Created by Brayo on 7/9/2015.
 */
public class ColorsActivity extends ActionBarActivity {
    private DBOpenHelper helper;
    private String hexColor = "119,047,047";
    private String rgbColor;
    List<String> color_list = new ArrayList<String>();
    private String colorSelection;
    private MagicBox magicBox;
    private ButtonFlat next_btn;
    private ButtonRectangle select_color;
    private FingerColorsDBAdapter fingerColorsDBAdapter;
    private int color = Color.RED;
    private HashMap<String, String> finger_1_colors = new HashMap<String, String>();
    private HashMap<String, String> finger_2_colors = new HashMap<String, String>();
    private HashMap<String, String> finger_3_colors = new HashMap<String, String>();
    private HashMap<String, String> finger_4_colors = new HashMap<String, String>();

    private Button finger_1_color1, finger_1_color2, finger_1_color3, finger_1_color4,
            finger_1_color5, finger_1_color6;
    private Button finger_2_color1, finger_2_color2, finger_2_color3, finger_2_color4,
            finger_2_color5, finger_2_color6;
    private Button finger_3_color1, finger_3_color2, finger_3_color3, finger_3_color4,
            finger_3_color5, finger_3_color6;
    private Button finger_4_color1, finger_4_color2, finger_4_color3, finger_4_color4,
            finger_4_color5, finger_4_color6;
    private ToastMessage warningMessage;
    private ToastMessage infoMessage;
    private Toolbar mToolbar;
    private GetDate getDate;
    private ColorPresets colorPreferences;
    private Intent i = null;
    private String hand;
    private String effect;
    private ButtonRectangle clear_finger_1;
    private ButtonRectangle clear_finger_2;
    private ButtonRectangle clear_finger_3;
    private ButtonRectangle clear_finger_4;
    private ButtonRectangle reset_clear;
    private Button color_1, color_2, color_3, color_4, color_5, color_6, color_7, color_8, color_9,
            color_10, color_11, color_12, color_13, color_14, color_15, color_16, color_17,
            color_18, color_19, color_20;
    private RadioGroup fingers_group;
    private RadioButton finger_1_radio;
    private RadioButton finger_2_radio;
    private RadioButton finger_3_radio;
    private RadioButton finger_4_radio;
    private HashMap<String, String> presets = new HashMap<>();
    private ArrayList<String> colors = new ArrayList<>();

    @Override
    public void onCreate(final Bundle savedInstanceState) {
        super.onCreate(savedInstanceState);
        overridePendingTransition(R.anim.slide_in_left, R.anim.slide_out_right);
        setContentView(R.layout.color_selection);

        magicBox = new MagicBox(this);
        next_btn = (ButtonFlat) findViewById(R.id.colors_next);
        warningMessage = new ToastMessage(this, ToastMessage.TYPE_ERROR);
        infoMessage = new ToastMessage(this, ToastMessage.TYPE_MESSAGE);
        fingerColorsDBAdapter = new FingerColorsDBAdapter(this);
        colorPreferences = new ColorPresets(this);
        i = new Intent(ColorsActivity.this, Finger1PatternActivity.class);
        getDate = new GetDate();

        Intent intent = getIntent();
        hand = intent.getStringExtra("hand");
        effect = intent.getStringExtra("effect");

        //lots of buttons
        finger_1_color1 = (Button) findViewById(R.id.finger_1_color1);
        finger_1_color2 = (Button) findViewById(R.id.finger_1_color2);
        finger_1_color3 = (Button) findViewById(R.id.finger_1_color3);
        finger_1_color4 = (Button) findViewById(R.id.finger_1_color4);
        finger_1_color5 = (Button) findViewById(R.id.finger_1_color5);
        finger_1_color6 = (Button) findViewById(R.id.finger_1_color6);

        finger_2_color1 = (Button) findViewById(R.id.finger_2_color1);
        finger_2_color2 = (Button) findViewById(R.id.finger_2_color2);
        finger_2_color3 = (Button) findViewById(R.id.finger_2_color3);
        finger_2_color4 = (Button) findViewById(R.id.finger_2_color4);
        finger_2_color5 = (Button) findViewById(R.id.finger_2_color5);
        finger_2_color6 = (Button) findViewById(R.id.finger_2_color6);

        finger_3_color1 = (Button) findViewById(R.id.finger_3_color1);
        finger_3_color2 = (Button) findViewById(R.id.finger_3_color2);
        finger_3_color3 = (Button) findViewById(R.id.finger_3_color3);
        finger_3_color4 = (Button) findViewById(R.id.finger_3_color4);
        finger_3_color5 = (Button) findViewById(R.id.finger_3_color5);
        finger_3_color6 = (Button) findViewById(R.id.finger_3_color6);

        finger_4_color1 = (Button) findViewById(R.id.finger_4_color1);
        finger_4_color2 = (Button) findViewById(R.id.finger_4_color2);
        finger_4_color3 = (Button) findViewById(R.id.finger_4_color3);
        finger_4_color4 = (Button) findViewById(R.id.finger_4_color4);
        finger_4_color5 = (Button) findViewById(R.id.finger_4_color5);
        finger_4_color6 = (Button) findViewById(R.id.finger_4_color6);

        color_1 = (Button) findViewById(R.id.color1);
        color_2 = (Button) findViewById(R.id.color2);
        color_3 = (Button) findViewById(R.id.color3);
        color_4 = (Button) findViewById(R.id.color4);
        color_5 = (Button) findViewById(R.id.color5);
        color_6 = (Button) findViewById(R.id.color6);
        color_7 = (Button) findViewById(R.id.color7);
        color_8 = (Button) findViewById(R.id.color8);
        color_9 = (Button) findViewById(R.id.color9);
        color_10 = (Button) findViewById(R.id.color10);

        color_11 = (Button) findViewById(R.id.color11);
        color_12 = (Button) findViewById(R.id.color12);
        color_13 = (Button) findViewById(R.id.color13);
        color_14 = (Button) findViewById(R.id.color14);
        color_15 = (Button) findViewById(R.id.color15);
        color_16 = (Button) findViewById(R.id.color16);
        color_17 = (Button) findViewById(R.id.color17);
        color_18 = (Button) findViewById(R.id.color18);
        color_19 = (Button) findViewById(R.id.color19);
        color_20 = (Button) findViewById(R.id.color20);

        fingers_group = (RadioGroup) findViewById(R.id.fingers_group);
        finger_1_radio = (RadioButton) findViewById(R.id.finger_1_radio);
        finger_2_radio = (RadioButton) findViewById(R.id.finger_2_radio);
        finger_3_radio = (RadioButton) findViewById(R.id.finger_3_radio);
        finger_4_radio = (RadioButton) findViewById(R.id.finger_4_radio);

        //set presets
        color_1.setBackgroundColor(Color.parseColor("#FF0000"));
        color_2.setBackgroundColor(Color.parseColor("#00FF00"));
        color_3.setBackgroundColor(Color.parseColor("#0000FF"));
        color_4.setBackgroundColor(Color.parseColor("#FFFF00"));
        color_5.setBackgroundColor(Color.parseColor("#F0EB6E"));
        color_6.setBackgroundColor(Color.parseColor("#FF00FF"));
        color_7.setBackgroundColor(Color.parseColor("#800000"));
        color_8.setBackgroundColor(Color.parseColor("#A6142D"));
        color_9.setBackgroundColor(Color.parseColor("#008000"));
        color_10.setBackgroundColor(Color.parseColor("#800080"));
        color_11.setBackgroundColor(Color.parseColor("#008080"));
        color_12.setBackgroundColor(Color.parseColor("#000080"));
        color_13.setBackgroundColor(Color.parseColor("#32CE55"));
        color_14.setBackgroundColor(Color.parseColor("#1B8A39"));
        color_15.setBackgroundColor(Color.parseColor("#8F8AF6"));
        color_16.setBackgroundColor(Color.parseColor("#F3551B"));
        color_17.setBackgroundColor(Color.parseColor("#CC641F"));
        color_18.setBackgroundColor(Color.parseColor("#5AAAAA"));
        color_19.setBackgroundColor(Color.parseColor("#F1D8BC"));
        color_20.setBackgroundColor(Color.parseColor("#C00A5F"));

        presets.put("1", "#FF0000");
        presets.put("2", "#00FF00");
        presets.put("3", "#0000FF");
        presets.put("4", "#FFFF00");
        presets.put("5", "#F0EB6E");
        presets.put("6", "#FF00FF");
        presets.put("7", "#800000");
        presets.put("8", "#A6142D");
        presets.put("9", "#008000");
        presets.put("10", "#800080");
        presets.put("11", "#008080");
        presets.put("12", "#000080");
        presets.put("13", "#32CE55");
        presets.put("14", "#1B8A39");
        presets.put("15", "#8F8AF6");
        presets.put("16", "#F3551B");
        presets.put("17", "#CC641F");
        presets.put("18", "#5AAAAA");
        presets.put("19", "#F1D8BC");
        presets.put("20", "#C00A5F");

        setColor(color_1, "1");
        setColor(color_2, "2");
        setColor(color_3, "3");
        setColor(color_4, "4");
        setColor(color_5, "5");
        setColor(color_6, "6");
        setColor(color_7, "7");
        setColor(color_8, "8");
        setColor(color_9, "9");
        setColor(color_10, "10");
        setColor(color_11, "11");
        setColor(color_12, "12");
        setColor(color_13, "13");
        setColor(color_14, "14");
        setColor(color_15, "15");
        setColor(color_16, "16");
        setColor(color_17, "17");
        setColor(color_18, "18");
        setColor(color_19, "19");
        setColor(color_20, "20");

//        finger_1_colors.put("finger_1_color_1", "#FF0000");
//        finger_1_colors.put("finger_1_color_2", "#00FF00");
//        finger_1_colors.put("finger_1_color_3", "#0000FF");
//        finger_1_colors.put("finger_1_color_4", "#FFFF00");
//        finger_1_colors.put("finger_1_color_5", "#00FFFF");
//        finger_1_colors.put("finger_1_color_6", "#FF00FF");
//
//        finger_2_colors.put("finger_2_color_1", "#800000");
//        finger_2_colors.put("finger_2_color_2", "#808000");
//        finger_2_colors.put("finger_2_color_3", "#008000");
//        finger_2_colors.put("finger_2_color_4", "#800080");
//        finger_2_colors.put("finger_2_color_5", "#008080");
//        finger_2_colors.put("finger_2_color_6", "#000080");
//
//        finger_3_colors.put("finger_3_color_1", "#32CE55");
//        finger_3_colors.put("finger_3_color_2", "#1B8A39");
//        finger_3_colors.put("finger_3_color_3", "#8F8AF6");
//        finger_3_colors.put("finger_3_color_4", "#F3551B");
//        finger_3_colors.put("finger_3_color_5", "#CC641F");
//        finger_3_colors.put("finger_3_color_6", "#5AAAAA");
//
//        finger_4_colors.put("finger_4_color_1", "#F1D8BC");
//        finger_4_colors.put("finger_4_color_2", "#C00A5F");
//        finger_4_colors.put("finger_4_color_3", "#A6142D");
//        finger_4_colors.put("finger_4_color_4", "#F0EB6E");
//        finger_4_colors.put("finger_4_color_5", "#70DBAD");
//        finger_4_colors.put("finger_4_color_6", "#FDFFBC");

        next_btn.setOnClickListener(new View.OnClickListener() {
            @Override
            public void onClick(View v) {
                if (finger_1_colors.size() < 1)
                    warningMessage.showToastMessage("Please select at least 1 color for finger 1");
                else
                    saveNProceed();
            }
        });

        fingers_group.setOnCheckedChangeListener(new RadioGroup.OnCheckedChangeListener() {

            @Override
            public void onCheckedChanged(RadioGroup group, int checkedId) {

                if (fingers_group.getCheckedRadioButtonId() == R.id.finger_1_radio) {
                    colorRow1();
                    colors.clear();
                } else if (fingers_group.getCheckedRadioButtonId() == R.id.finger_2_radio) {
                    colorRow2();
                    colors.clear();
                } else if (fingers_group.getCheckedRadioButtonId() == R.id.finger_3_radio) {
                    colorRow3();
                    colors.clear();
                } else if (fingers_group.getCheckedRadioButtonId() == R.id.finger_4_radio) {
                    colorRow4();
                    colors.clear();
                }

            }

        });

        mToolbar = (Toolbar) findViewById(R.id.toolbar);
        mToolbar.setOnClickListener(new View.OnClickListener() {
            @Override
            public void onClick(View v) {
                Intent i3 = new Intent(ColorsActivity.this, MainActivity.class);
                startActivity(i3);
            }
        });
        setSupportActionBar(mToolbar);
    }

    public void setColor(View view, final String key) {
        view.setOnClickListener(new View.OnClickListener() {
            @Override
            public void onClick(View v) {
                colors.add(presets.get(key));
            }
        });
    }

    public void colorRow1() {
        try {
            finger_1_colors.put("finger_1_color_1", getRgb(colors.get(0)));
            finger_1_colors.put("finger_1_color_2", getRgb(colors.get(1)));
            finger_1_colors.put("finger_1_color_3", getRgb(colors.get(2)));
            finger_1_colors.put("finger_1_color_4", getRgb(colors.get(3)));
            finger_1_colors.put("finger_1_color_5", getRgb(colors.get(4)));
            finger_1_colors.put("finger_1_color_6", getRgb(colors.get(5)));
        } catch (IndexOutOfBoundsException e) {
            e.printStackTrace();
        }
        row1();

        Toast.makeText(ColorsActivity.this, finger_1_colors.get("finger_1_color_1") + " " +
                finger_1_colors.get("finger_1_color_2"), Toast.LENGTH_LONG).show();
    }

    public void row1() {
        try {
            finger_1_color1.setBackgroundColor(Color.parseColor(colors.get(0)));
            finger_1_color2.setBackgroundColor(Color.parseColor(colors.get(1)));
            finger_1_color3.setBackgroundColor(Color.parseColor(colors.get(2)));
            finger_1_color4.setBackgroundColor(Color.parseColor(colors.get(3)));
            finger_1_color5.setBackgroundColor(Color.parseColor(colors.get(4)));
            finger_1_color6.setBackgroundColor(Color.parseColor(colors.get(5)));
        } catch (IndexOutOfBoundsException e) {
            e.printStackTrace();
        }
    }

    public void colorRow2() {
        try {
            finger_2_colors.put("finger_2_color_1", getRgb(colors.get(0)));
            finger_2_colors.put("finger_2_color_2", getRgb(colors.get(1)));
            finger_2_colors.put("finger_2_color_3", getRgb(colors.get(2)));
            finger_2_colors.put("finger_2_color_4", getRgb(colors.get(3)));
            finger_2_colors.put("finger_2_color_5", getRgb(colors.get(4)));
            finger_2_colors.put("finger_2_color_6", getRgb(colors.get(5)));
        } catch (IndexOutOfBoundsException e) {
            e.printStackTrace();
        }
        row2();
    }

    public void row2() {
        try {
            finger_2_color1.setBackgroundColor(Color.parseColor(colors.get(0)));
            finger_2_color2.setBackgroundColor(Color.parseColor(colors.get(1)));
            finger_2_color3.setBackgroundColor(Color.parseColor(colors.get(2)));
            finger_2_color4.setBackgroundColor(Color.parseColor(colors.get(3)));
            finger_2_color5.setBackgroundColor(Color.parseColor(colors.get(4)));
            finger_2_color6.setBackgroundColor(Color.parseColor(colors.get(5)));
        } catch (IndexOutOfBoundsException e) {
            e.printStackTrace();
        }
    }

    public void colorRow3() {
        try {
            finger_3_colors.put("finger_3_color_1", getRgb(colors.get(0)));
            finger_3_colors.put("finger_3_color_2", getRgb(colors.get(1)));
            finger_3_colors.put("finger_3_color_3", getRgb(colors.get(2)));
            finger_3_colors.put("finger_3_color_4", getRgb(colors.get(3)));
            finger_3_colors.put("finger_3_color_5", getRgb(colors.get(4)));
            finger_3_colors.put("finger_3_color_6", getRgb(colors.get(5)));
        } catch (IndexOutOfBoundsException e) {
            e.printStackTrace();
        }
        row3();
    }

    public void row3() {
        try {
            finger_3_color1.setBackgroundColor(Color.parseColor(colors.get(0)));
            finger_3_color2.setBackgroundColor(Color.parseColor(colors.get(1)));
            finger_3_color3.setBackgroundColor(Color.parseColor(colors.get(2)));
            finger_3_color4.setBackgroundColor(Color.parseColor(colors.get(3)));
            finger_3_color5.setBackgroundColor(Color.parseColor(colors.get(4)));
            finger_3_color6.setBackgroundColor(Color.parseColor(colors.get(5)));
        } catch (IndexOutOfBoundsException e) {
            e.printStackTrace();
        }
    }

    public void colorRow4() {
        try {
            finger_4_colors.put("finger_4_color_1", getRgb(colors.get(0)));
            finger_4_colors.put("finger_4_color_2", getRgb(colors.get(1)));
            finger_4_colors.put("finger_4_color_3", getRgb(colors.get(2)));
            finger_4_colors.put("finger_4_color_4", getRgb(colors.get(3)));
            finger_4_colors.put("finger_4_color_5", getRgb(colors.get(4)));
            finger_4_colors.put("finger_4_color_6", getRgb(colors.get(5)));
        } catch (IndexOutOfBoundsException e) {
            e.printStackTrace();
        }
        row4();
    }

    public void row4() {
        try {
            finger_4_color1.setBackgroundColor(Color.parseColor(colors.get(0)));
            finger_4_color2.setBackgroundColor(Color.parseColor(colors.get(1)));
            finger_4_color3.setBackgroundColor(Color.parseColor(colors.get(2)));
            finger_4_color4.setBackgroundColor(Color.parseColor(colors.get(3)));
            finger_4_color5.setBackgroundColor(Color.parseColor(colors.get(4)));
            finger_4_color6.setBackgroundColor(Color.parseColor(colors.get(5)));
        } catch (IndexOutOfBoundsException e) {
            e.printStackTrace();
        }
    }

//    public void clearFinger1() {
//        finger_1_color1.setBackgroundColor(Color.parseColor("#E1E1E1"));
//        finger_1_color2.setBackgroundColor(Color.parseColor("#E1E1E1"));
//        finger_1_color3.setBackgroundColor(Color.parseColor("#E1E1E1"));
//        finger_1_color4.setBackgroundColor(Color.parseColor("#E1E1E1"));
//        finger_1_color5.setBackgroundColor(Color.parseColor("#E1E1E1"));
//        finger_1_color6.setBackgroundColor(Color.parseColor("#E1E1E1"));
//        finger_1_colors.clear();
//    }
//
//    public void clearFinger2() {
//        finger_2_color1.setBackgroundColor(Color.parseColor("#E1E1E1"));
//        finger_2_color2.setBackgroundColor(Color.parseColor("#E1E1E1"));
//        finger_2_color3.setBackgroundColor(Color.parseColor("#E1E1E1"));
//        finger_2_color4.setBackgroundColor(Color.parseColor("#E1E1E1"));
//        finger_2_color5.setBackgroundColor(Color.parseColor("#E1E1E1"));
//        finger_2_color6.setBackgroundColor(Color.parseColor("#E1E1E1"));
//        finger_2_colors.clear();
//    }
//
//    public void clearFinger3() {
//        finger_3_color1.setBackgroundColor(Color.parseColor("#E1E1E1"));
//        finger_3_color2.setBackgroundColor(Color.parseColor("#E1E1E1"));
//        finger_3_color3.setBackgroundColor(Color.parseColor("#E1E1E1"));
//        finger_3_color4.setBackgroundColor(Color.parseColor("#E1E1E1"));
//        finger_3_color5.setBackgroundColor(Color.parseColor("#E1E1E1"));
//        finger_3_color6.setBackgroundColor(Color.parseColor("#E1E1E1"));
//        finger_3_colors.clear();
//    }
//
//    public void clearFinger4() {
//        finger_4_color1.setBackgroundColor(Color.parseColor("#E1E1E1"));
//        finger_4_color2.setBackgroundColor(Color.parseColor("#E1E1E1"));
//        finger_4_color3.setBackgroundColor(Color.parseColor("#E1E1E1"));
//        finger_4_color4.setBackgroundColor(Color.parseColor("#E1E1E1"));
//        finger_4_color5.setBackgroundColor(Color.parseColor("#E1E1E1"));
//        finger_4_color6.setBackgroundColor(Color.parseColor("#E1E1E1"));
//        finger_4_colors.clear();
//    }
//
//    public void resetClear() {
//        Intent i = new Intent(ColorsActivity.this, ColorsActivity.class);
//        i.putExtra("hand", hand);
//        i.putExtra("effect", effect);
//        startActivity(i);
//    }

    public String getRgb(String hex) {
        //int color = Integer.parseInt(hex.replace("#", ""), 16);
        int color = Color.parseColor(hex);
        int red = Color.red(color);
        int green = Color.green(color);
        int blue = Color.blue(color);
        String rgbColor = String.format("%03d", red) + "," + String.format("%03d", green)
                + "," + String.format("%03d", blue);
        return rgbColor;
    }

    void openDialog(boolean supportsAlpha, final View view) {
        AmbilWarnaDialog dialog = new AmbilWarnaDialog(ColorsActivity.this, color,
                new AmbilWarnaDialog.OnAmbilWarnaListener() {
                    @Override
                    public void onOk(AmbilWarnaDialog dialog, int color) {
                        ColorsActivity.this.color = color;
                        view.setBackgroundColor(color);
                        int red = Color.red(color);
                        int green = Color.green(color);
                        int blue = Color.blue(color);
                        hexColor = String.format("%03d", red) + "," + String.format("%03d", green)
                                + "," + String.format("%03d", blue);
                    }

                    @Override
                    public void onCancel(AmbilWarnaDialog dialog) {
                        //   Toast.makeText(getApplicationContext(), "cancel", Toast.LENGTH_SHORT).show();
                    }
                });
        dialog.show();
    }


    public void showDialog() {
        new MaterialDialog.Builder(this)
                .title("Color selection")
                .content("Please complete your color selection")
                .positiveText("Ok")
                .negativeText("Proceed Anyway")
                .callback(new MaterialDialog.ButtonCallback() {
                    @Override
                    public void onPositive(MaterialDialog dialog) {
                        dialog.dismiss();
                    }
                })
                .callback(new MaterialDialog.ButtonCallback() {
                    @Override
                    public void onNegative(MaterialDialog dialog) {
                        saveNProceed();
                    }
                })
                .show();
    }

    public void saveNProceed() {

        if (fingerColorsDBAdapter.getRowCount() == 0) {
            fingerColorsDBAdapter.insertMessage("Finger 1",
                    finger_1_colors.get("finger_1_color_1"),
                    finger_1_colors.get("finger_1_color_2"),
                    finger_1_colors.get("finger_1_color_3"),
                    finger_1_colors.get("finger_1_color_4"),
                    finger_1_colors.get("finger_1_color_5"),
                    finger_1_colors.get("finger_1_color_6"),
                    getDate.getDate(),
                    getDate.getDate()
            );

            fingerColorsDBAdapter.insertMessage("Finger 2",
                    finger_2_colors.get("finger_2_color_1"),
                    finger_2_colors.get("finger_2_color_2"),
                    finger_2_colors.get("finger_2_color_3"),
                    finger_2_colors.get("finger_2_color_4"),
                    finger_2_colors.get("finger_2_color_5"),
                    finger_2_colors.get("finger_2_color_6"),
                    getDate.getDate(),
                    getDate.getDate()
            );

            fingerColorsDBAdapter.insertMessage("Finger 3",
                    finger_3_colors.get("finger_3_color_1"),
                    finger_3_colors.get("finger_3_color_2"),
                    finger_3_colors.get("finger_3_color_3"),
                    finger_3_colors.get("finger_3_color_4"),
                    finger_3_colors.get("finger_3_color_5"),
                    finger_3_colors.get("finger_3_color_6"),
                    getDate.getDate(),
                    getDate.getDate()
            );

            fingerColorsDBAdapter.insertMessage("Finger 4",
                    finger_4_colors.get("finger_4_color_1"),
                    finger_4_colors.get("finger_4_color_2"),
                    finger_4_colors.get("finger_4_color_3"),
                    finger_4_colors.get("finger_4_color_4"),
                    finger_4_colors.get("finger_4_color_5"),
                    finger_4_colors.get("finger_4_color_6"),
                    getDate.getDate(),
                    getDate.getDate()
            );
        }

        if (fingerColorsDBAdapter.getRowCount() >= 4) {
            fingerColorsDBAdapter.updateMessage("Finger 1",
                    finger_1_colors.get("finger_1_color_1"),
                    finger_1_colors.get("finger_1_color_2"),
                    finger_1_colors.get("finger_1_color_3"),
                    finger_1_colors.get("finger_1_color_4"),
                    finger_1_colors.get("finger_1_color_5"),
                    finger_1_colors.get("finger_1_color_6"),
                    getDate.getDate(),
                    getDate.getDate()
            );

            fingerColorsDBAdapter.updateMessage("Finger 2",
                    finger_2_colors.get("finger_2_color_1"),
                    finger_2_colors.get("finger_2_color_2"),
                    finger_2_colors.get("finger_2_color_3"),
                    finger_2_colors.get("finger_2_color_4"),
                    finger_2_colors.get("finger_2_color_5"),
                    finger_2_colors.get("finger_2_color_6"),
                    getDate.getDate(),
                    getDate.getDate()
            );

            fingerColorsDBAdapter.updateMessage("Finger 3",
                    finger_3_colors.get("finger_3_color_1"),
                    finger_3_colors.get("finger_3_color_2"),
                    finger_3_colors.get("finger_3_color_3"),
                    finger_3_colors.get("finger_3_color_4"),
                    finger_3_colors.get("finger_3_color_5"),
                    finger_3_colors.get("finger_3_color_6"),
                    getDate.getDate(),
                    getDate.getDate()
            );

            fingerColorsDBAdapter.updateMessage("Finger 4",
                    finger_4_colors.get("finger_4_color_1"),
                    finger_4_colors.get("finger_4_color_2"),
                    finger_4_colors.get("finger_4_color_3"),
                    finger_4_colors.get("finger_4_color_4"),
                    finger_4_colors.get("finger_4_color_5"),
                    finger_4_colors.get("finger_4_color_6"),
                    getDate.getDate(),
                    getDate.getDate()
            );
        }
        i.putExtra("hand", hand);
        i.putExtra("effect", effect);
        startActivity(i);
    }
}

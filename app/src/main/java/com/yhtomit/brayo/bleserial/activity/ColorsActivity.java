package com.yhtomit.brayo.bleserial.activity;


import android.content.Intent;
import android.graphics.Color;
import android.graphics.drawable.ColorDrawable;
import android.os.Bundle;
import android.support.v7.app.ActionBarActivity;
import android.support.v7.widget.Toolbar;
import android.text.Html;
import android.view.View;
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
    private int color;
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

        //  Toast.makeText(this, hand+ " " +effect, Toast.LENGTH_LONG).show();

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

        clickListeners(finger_1_color1);
        clickListeners(finger_1_color2);
        clickListeners(finger_1_color3);
        clickListeners(finger_1_color4);
        clickListeners(finger_1_color5);
        clickListeners(finger_1_color6);

        clickListeners(finger_2_color1);
        clickListeners(finger_2_color2);
        clickListeners(finger_2_color3);
        clickListeners(finger_2_color4);
        clickListeners(finger_2_color5);
        clickListeners(finger_2_color6);

        clickListeners(finger_3_color1);
        clickListeners(finger_3_color2);
        clickListeners(finger_3_color3);
        clickListeners(finger_3_color4);
        clickListeners(finger_3_color5);
        clickListeners(finger_3_color6);

        clickListeners(finger_4_color1);
        clickListeners(finger_4_color2);
        clickListeners(finger_4_color3);
        clickListeners(finger_4_color4);
        clickListeners(finger_4_color5);
        clickListeners(finger_4_color6);


        next_btn.setOnClickListener(new View.OnClickListener() {
            @Override
            public void onClick(View v) {
                if (finger_1_colors.size() < 1)
                    warningMessage.showToastMessage("Please select at least 1 color for finger 1");
                else
                    saveNProceed();
            }
        });


        mToolbar = (Toolbar) findViewById(R.id.toolbar);
        setSupportActionBar(mToolbar);
        getSupportActionBar().setTitle(Html.fromHtml("<font color=\"#FFFFFF\">" + "Finger 1" + "</font"));
        getSupportActionBar().getThemedContext();
        getSupportActionBar().setIcon(new ColorDrawable(getResources().getColor(android.R.color.transparent)));
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

    public void clickListeners(View viewButton) {
        final View view = viewButton;
        viewButton.setOnClickListener(new View.OnClickListener() {
            @Override
            public void onClick(View v) {

                openDialog(false, view);

                switch (view.getId()) {
                    case R.id.finger_1_color1:
                        finger_1_colors.put("finger_1_color_1", hexColor);
                        break;
                    case R.id.finger_1_color2:
                        finger_1_colors.put("finger_1_color_2", hexColor);
                        break;
                    case R.id.finger_1_color3:
                        finger_1_colors.put("finger_1_color_3", hexColor);
                        break;
                    case R.id.finger_1_color4:
                        finger_1_colors.put("finger_1_color_4", hexColor);
                        break;
                    case R.id.finger_1_color5:
                        finger_1_colors.put("finger_1_color_5", hexColor);
                        break;
                    case R.id.finger_1_color6:
                        finger_1_colors.put("finger_1_color_6", hexColor);
                        break;

                    case R.id.finger_2_color1:
                        finger_2_colors.put("finger_2_color_1", hexColor);
                        break;
                    case R.id.finger_2_color2:
                        finger_2_colors.put("finger_2_color_2", hexColor);
                        break;
                    case R.id.finger_2_color3:
                        finger_2_colors.put("finger_2_color_3", hexColor);
                        break;
                    case R.id.finger_2_color4:
                        finger_2_colors.put("finger_2_color_4", hexColor);
                        break;
                    case R.id.finger_2_color5:
                        finger_2_colors.put("finger_2_color_5", hexColor);
                        break;
                    case R.id.finger_2_color6:
                        finger_2_colors.put("finger_2_color_6", hexColor);
                        break;

                    case R.id.finger_3_color1:
                        finger_3_colors.put("finger_3_color_1", hexColor);
                        break;
                    case R.id.finger_3_color2:
                        finger_3_colors.put("finger_3_color_2", hexColor);
                        break;
                    case R.id.finger_3_color3:
                        finger_3_colors.put("finger_3_color_3", hexColor);
                        break;
                    case R.id.finger_3_color4:
                        finger_3_colors.put("finger_3_color_4", hexColor);
                        break;
                    case R.id.finger_3_color5:
                        finger_3_colors.put("finger_3_color_5", hexColor);
                        break;
                    case R.id.finger_3_color6:
                        finger_3_colors.put("finger_3_color_6", hexColor);
                        break;

                    case R.id.finger_4_color1:
                        finger_4_colors.put("finger_4_color_1", hexColor);
                        break;
                    case R.id.finger_4_color2:
                        finger_4_colors.put("finger_4_color_2", hexColor);
                        break;
                    case R.id.finger_4_color3:
                        finger_4_colors.put("finger_4_color_3", hexColor);
                        break;
                    case R.id.finger_4_color4:
                        finger_4_colors.put("finger_4_color_4", hexColor);
                        break;
                    case R.id.finger_4_color5:
                        finger_4_colors.put("finger_4_color_5", hexColor);
                        break;
                    case R.id.finger_4_color6:
                        finger_4_colors.put("finger_4_color_6", hexColor);
                        break;
                }
            }
        });
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

        if (fingerColorsDBAdapter.getRowCount() <= 4) {
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

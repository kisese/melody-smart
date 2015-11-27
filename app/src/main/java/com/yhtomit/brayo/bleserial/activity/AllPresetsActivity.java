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
import android.widget.TextView;
import android.widget.Toast;

import com.afollestad.materialdialogs.MaterialDialog;
import com.yhtomit.brayo.bleserial.R;
import com.yhtomit.brayo.bleserial.model.PresetsListAdapter;
import com.yhtomit.brayo.bleserial.storagepreferences.AllColorPresets;
import com.yhtomit.brayo.bleserial.storagepreferences.ColorPresets;

/**
 * Created by Brayo on 7/27/2015.
 */
public class AllPresetsActivity extends ActionBarActivity {

    private Toolbar mToolbar;
    private String[] presets;
    private ListView listview;
    private PresetsListAdapter list_adapter;
    private AllColorPresets color_presets;
    private ColorPresets preset;
    ConnectionManager connectionManager = new ConnectionManager();
    private TextView mDataField;
    private String mDeviceName;
    private String mDeviceAddress;
    private String TAG;
    private String[] ap;
    private String[] arr;

    @Override
    protected void onCreate(Bundle savedInstanceState) {
        super.onCreate(savedInstanceState);
        overridePendingTransition(R.anim.slide_in_left, R.anim.slide_out_right);
        setContentView(R.layout.activity_all_presets);


        color_presets = new AllColorPresets(this);
        preset = new ColorPresets(this);

        listview = (ListView) findViewById(R.id.preset_list);

        presets = color_presets.getPresets();
        ap = preset.getPresets();

        list_adapter = new PresetsListAdapter(this, concat(presets, ap));
        listview.setAdapter(list_adapter);

        arr = concat(presets, ap);

        clickListeners();

        mToolbar = (Toolbar) findViewById(R.id.toolbar);
        mToolbar.setTitle("All Presets");
        setSupportActionBar(mToolbar);
    }

    private String[] concat(String[] A, String[] B) {
        int aLen = A.length;
        int bLen = B.length;
        String[] C = new String[aLen + bLen];
        System.arraycopy(A, 0, C, 0, aLen);
        System.arraycopy(B, 0, C, aLen, bLen);
        return C;
    }

    public void clickListeners() {

        listview.setOnItemClickListener(new AdapterView.OnItemClickListener() {
            @Override
            public void onItemClick(AdapterView<?> parent, View view, int position, long id) {
//                Toast.makeText(AllPresetsActivity.this,
//                        color_presets.getPreset(presets[position]), Toast.LENGTH_LONG).show();
                final Intent i = new Intent(AllPresetsActivity.this, ScanActivity.class);
                i.putExtra("send_string", find(arr[position]));
                Toast.makeText(AllPresetsActivity.this,  find(arr[position]), Toast.LENGTH_LONG).show();
                startActivity(i);
            }
        });
    }

    public String find(String key) {
        String msg = null;
        try {
            msg = color_presets.getPreset(key);
            if (msg == null)
                msg = preset.getPreset(key);
        } catch (NullPointerException e) {

        }
        return msg;
    }

    public void showDialog(final String send_string) {
        final Intent i = new Intent(AllPresetsActivity.this, ScanActivity.class);
        new MaterialDialog.Builder(this)
                .title("Send")
                .content("Proceed to send")
                .positiveText("Send and Save")
                .negativeText("Proceed Without Saving")
                .callback(new MaterialDialog.ButtonCallback() {
                    @Override
                    public void onPositive(MaterialDialog dialog) {
                        i.putExtra("send_string", "M," + send_string);
                        startActivity(i);
                        dialog.dismiss();
                    }
                })
                .callback(new MaterialDialog.ButtonCallback() {
                    @Override
                    public void onNegative(MaterialDialog dialog) {
                        i.putExtra("send_string", send_string);
                        startActivity(i);
                        dialog.dismiss();
                    }
                })
                .show();
    }
}

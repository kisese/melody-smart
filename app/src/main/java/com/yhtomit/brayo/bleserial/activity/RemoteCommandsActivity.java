package com.yhtomit.brayo.bleserial.activity;

import android.app.Activity;
import android.content.Context;
import android.os.Bundle;
import android.util.Log;
import android.view.KeyEvent;
import android.view.View;
import android.view.inputmethod.InputMethodManager;
import android.widget.EditText;
import android.widget.TextView;

import com.bluecreation.melodysmart.MelodySmartDevice;
import com.bluecreation.melodysmart.RemoteCommandsService;
import com.yhtomit.brayo.bleserial.R;

/**
 * Created by genis on 26/01/2015.
 */
public class RemoteCommandsActivity extends BleActivity implements RemoteCommandsService.Listener {

    private static final String TAG = RemoteCommandsActivity.class.getSimpleName();
    private RemoteCommandsService remoteCommandsService;
    private TextView responseTextView;
    private EditText commandEditText;

    @Override
    public void onCreate(Bundle savedInstanceState) {
        super.onCreate(savedInstanceState);

        setContentView(R.layout.remote_commands_layout);
        commandEditText = (EditText) findViewById(R.id.remoteCommandSendEditText);
        responseTextView = (TextView) findViewById(R.id.remoteCommandResponseEditText);



        commandEditText.setOnEditorActionListener(new TextView.OnEditorActionListener() {
            @Override
            public boolean onEditorAction(TextView v, int actionId, KeyEvent event) {
                remoteCommandsService.send(commandEditText.getText().toString());
                hideKeyboard(commandEditText.getWindowToken());
                commandEditText.setText("");
                return true;
            }
        });

        remoteCommandsService = MelodySmartDevice.getInstance().getRemoteCommandsService();
        remoteCommandsService.registerListener(this);
        remoteCommandsService.enableNotifications(true);
    }

    @Override
    public void onDestroy() {
        remoteCommandsService.unregisterListener(this);
        remoteCommandsService.enableNotifications(false);
        super.onDestroy();
    }



    @Override
    public void handleReply(final byte[] reply) {
        Log.d(TAG, "Got command response : " + new String(reply));
        runOnUiThread(new Runnable() {
            @Override
            public void run() {
                responseTextView.append(new String(reply));
            }
        });
    }

    @Override
    public void onConnected(boolean found) {
        /* Do nothing. If we got there, the service exists and has been connected. */
    }
}

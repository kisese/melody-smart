package com.yhtomit.brayo.bleserial.magic;

import android.content.Context;
import android.view.Gravity;
import android.view.LayoutInflater;
import android.view.View;
import android.view.ViewGroup;
import android.widget.TextView;
import android.widget.Toast;

import com.yhtomit.brayo.bleserial.R;


/**
 * Created by Brayo on 6/26/2015.
 */
public class ToastMessage extends View {

    private TextView text;
    private View layout;
    private Context context;
    LayoutInflater inflater;

    public static String TYPE_ERROR = "error";
    public static String TYPE_SUCCESS = "success";
    public static String TYPE_MESSAGE = "message";

    private String type;

    public ToastMessage(Context context, String type) {
        super(context);
        this.context = context;
        inflater = (LayoutInflater) context.getSystemService(Context.LAYOUT_INFLATER_SERVICE);
        this.type = type;
    }

    public void showToastMessage(String message) {
        if (type.equals("error"))
            layout = inflater.inflate(R.layout.warning_toast_layout, (ViewGroup) findViewById(R.id.toast_layout_root));
        else if (type.equals("message"))
            layout = inflater.inflate(R.layout.warning_toast_layout, (ViewGroup) findViewById(R.id.toast_layout_root));
        else
            layout = inflater.inflate(R.layout.warning_toast_layout, (ViewGroup) findViewById(R.id.toast_layout_root));

        text = (TextView) layout.findViewById(R.id.text);
        text.setText(message);
        Toast toast = new Toast(context);
        toast.setGravity(Gravity.CENTER_VERTICAL, 0, -100);
        toast.setDuration(Toast.LENGTH_LONG);
        toast.setView(layout);
        toast.show();
    }
}

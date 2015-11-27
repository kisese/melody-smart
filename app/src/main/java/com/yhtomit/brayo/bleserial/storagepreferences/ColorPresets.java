package com.yhtomit.brayo.bleserial.storagepreferences;

import android.content.Context;
import android.content.SharedPreferences;

import com.yhtomit.brayo.bleserial.magic.ToastMessage;

import java.util.Arrays;

/**
 * Created by Brayo on 6/26/2015.
 */
public class ColorPresets {
    SharedPreferences pref;
    SharedPreferences.Editor editor;
    Context context;
    int PRIVATE_MODE = 0;
    private ToastMessage toastMessage;

    private static final String SHARED_PREFER_FILE_NAME = "presets";

    public ColorPresets(Context context) {
        this.context = context;
        pref = context.getSharedPreferences(SHARED_PREFER_FILE_NAME, PRIVATE_MODE);
        editor = pref.edit();
        toastMessage = new ToastMessage(context, ToastMessage.TYPE_MESSAGE);
    }


    public void addPreset(String key, String name) {
        editor.putString(key, name);
        editor.commit();
    }

    public String[] getPresets(){
        String[] tags = pref.getAll().keySet().toArray(new String[0]);
        Arrays.sort(tags);
        return tags;
    }

    public String getPreset(String key){
        String color_name = pref.getString(key, null);
        return color_name;
    }

    public void clearPresets(){
        editor.clear();
        editor.commit();
        toastMessage.showToastMessage("Presets Cleared");
    }
}

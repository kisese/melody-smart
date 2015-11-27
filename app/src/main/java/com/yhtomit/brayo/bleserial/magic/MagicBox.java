package com.yhtomit.brayo.bleserial.magic;

import android.content.Context;
import android.content.Intent;
import android.text.InputType;
import android.view.View;

import com.afollestad.materialdialogs.MaterialDialog;
import com.yhtomit.brayo.bleserial.storagepreferences.AllColorPresets;
import com.yhtomit.brayo.bleserial.storagepreferences.ColorPresets;

/**
 * Created by Brayo on 7/9/2015.
 */
public class MagicBox extends View {

    private Context context;
    private ToastMessage toastMessage;
    private ColorPresets presets;
    private AllColorPresets allColorPresets;

    public MagicBox(Context context) {
        super(context);
        this.context = context;
        toastMessage = new ToastMessage(context, ToastMessage.TYPE_MESSAGE);
    }

    public void doIntent(Context context, Class classname) {
        Intent i = new Intent(context, classname);
        context.startActivity(i);
    }

    public void addPresetDialog(final String color) {
        new MaterialDialog.Builder(context)
                .title("Preset Name")
                .content("Add a new Preset")
                .inputType(InputType.TYPE_CLASS_TEXT)
                .input("Name of Preset", "", new MaterialDialog.InputCallback() {
                    @Override
                    public void onInput(MaterialDialog dialog, CharSequence input) {
                        if (input.toString().isEmpty())
                            return;
                        presets = new ColorPresets(context);
                        allColorPresets = new AllColorPresets(context);

                        if (color.contains(",F0,"))
                            presets.addPreset(input.toString(), color);
                        else
                            allColorPresets.addPreset(input.toString(), color);

                        toastMessage.showToastMessage("Preset Saved");
                    }
                }).show();
    }

}

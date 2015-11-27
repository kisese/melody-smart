package com.yhtomit.brayo.bleserial.model;

import android.content.Context;
import android.view.LayoutInflater;
import android.view.View;
import android.view.ViewGroup;
import android.widget.BaseAdapter;
import android.widget.TextView;

import com.yhtomit.brayo.bleserial.R;

/**
 * Created by Brayo on 7/26/2015.
 */
public class PresetsListAdapter extends BaseAdapter {

    private final LayoutInflater inflater;
    private Context context;
    private String[] presets;

    public PresetsListAdapter(Context context, String[] presets) {
        this.context = context;
        this.presets = presets;
        inflater = (LayoutInflater)context.getSystemService(Context.LAYOUT_INFLATER_SERVICE);
    }

    @Override
    public int getCount() {
        return presets.length;
    }

    @Override
    public Object getItem(int position) {
        return position;
    }

    @Override
    public long getItemId(int position) {
        return position;
    }

    public class Holder
    {
        TextView preset_name;
    }

    @Override
    public View getView(int position, View convertView, ViewGroup parent) {
        Holder holder;
        holder=new Holder();

        convertView = inflater.inflate(R.layout.preset_list_item, null);
        holder.preset_name = (TextView) convertView.findViewById(R.id.preset_name);
        holder.preset_name.setText(presets[position]);

        return convertView;
    }
}

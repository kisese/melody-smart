package com.yhtomit.brayo.bleserial.activity;

import android.content.Context;
import android.view.View;

/**
 * Created by Brayo on 8/1/2015.
 */
public class PatternViewer extends View {
    private Context context;

    public PatternViewer(Context context) {
        super(context);
        this.context = context;
    }

    public void hideViews(View view){
        view.setVisibility(View.INVISIBLE);
    }

    public void hideViewExcept(View view,
                               View view1,
                               View view2,
                               View view3,
                               View view4
                               ){
        view.setVisibility(View.VISIBLE);
        view1.setVisibility(View.INVISIBLE);
        view2.setVisibility(View.INVISIBLE);
        view3.setVisibility(View.INVISIBLE);
        view4.setVisibility(View.INVISIBLE);
    }
}

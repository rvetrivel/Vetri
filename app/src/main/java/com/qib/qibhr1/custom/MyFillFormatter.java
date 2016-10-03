package com.qib.qibhr1.custom;

import com.qib.qibhr1.charting.data.LineDataSet;
import com.qib.qibhr1.charting.formatter.FillFormatter;
import com.qib.qibhr1.charting.interfaces.LineDataProvider;

/**
 * Created by Philipp Jahoda on 12/09/15.
 */
public class MyFillFormatter implements FillFormatter {

    private float mFillPos = 0f;

    public MyFillFormatter(float fillpos) {
        this.mFillPos = fillpos;
    }

    @Override
    public float getFillLinePosition(LineDataSet dataSet, LineDataProvider dataProvider) {
        // your logic could be here
        return mFillPos;
    }
}

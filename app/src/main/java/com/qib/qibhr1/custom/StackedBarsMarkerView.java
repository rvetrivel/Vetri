
package com.qib.qibhr1.custom;

import android.content.Context;
import android.widget.TextView;

import com.qib.qibhr1.R;
import com.qib.qibhr1.charting.components.MarkerView;
import com.qib.qibhr1.charting.data.BarEntry;
import com.qib.qibhr1.charting.data.Entry;
import com.qib.qibhr1.charting.highlight.Highlight;
import com.qib.qibhr1.charting.utils.Utils;

/**
 * Custom implementation of the MarkerView.
 * 
 * @author Philipp Jahoda
 */
public class StackedBarsMarkerView extends MarkerView {

    private TextView tvContent;

    public StackedBarsMarkerView(Context context, int layoutResource) {
        super(context, layoutResource);

        tvContent = (TextView) findViewById(R.id.tvContent);
    }

    // callbacks everytime the MarkerView is redrawn, can be used to update the
    // content (user-interface)
    @Override
    public void refreshContent(Entry e, Highlight highlight) {

        if (e instanceof BarEntry) {

            BarEntry be = (BarEntry) e;

            if(be.getVals() != null) {

                // draw the stack value
                tvContent.setText("" + Utils.formatNumber(be.getVals()[highlight.getStackIndex()], 0, true));
            } else {
                tvContent.setText("" + Utils.formatNumber(be.getVal(), 0, true));
            }
        } else {

            tvContent.setText("" + Utils.formatNumber(e.getVal(), 0, true));
        }
    }

    @Override
    public int getXOffset(float xpos) {
        // this will center the marker-view horizontally
        return -(getWidth() / 2);
    }

    @Override
    public int getYOffset(float ypos) {
        // this will cause the marker-view to be above the selected value
        return -getHeight();
    }
}

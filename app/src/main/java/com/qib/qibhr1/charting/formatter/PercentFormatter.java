
package com.qib.qibhr1.charting.formatter;

import com.qib.qibhr1.charting.components.YAxis;
import com.qib.qibhr1.charting.data.Entry;
import com.qib.qibhr1.charting.utils.ViewPortHandler;

import java.text.DecimalFormat;

/**
 * This ValueFormatter is just for convenience and simply puts a "%" sign after
 * each value. (Recommeded for PieChart)
 *
 * @author Philipp Jahoda
 */
public class PercentFormatter implements ValueFormatter, YAxisValueFormatter {

    protected DecimalFormat mFormat;

    public PercentFormatter() {
        mFormat = new DecimalFormat("###,###,##0.0");
    }

    /**
     * Allow a custom decimalformat
     *
     * @param format
     */
    public PercentFormatter(DecimalFormat format) {
        this.mFormat = format;
    }

    // ValueFormatter
    @Override
    public String getFormattedValue(float value, Entry entry, int dataSetIndex, ViewPortHandler viewPortHandler) {
        return mFormat.format(value) + " %";
    }

    // YAxisValueFormatter
    @Override
    public String getFormattedValue(float value, YAxis yAxis) {
        return mFormat.format(value) + " %";
    }
}

package com.qib.qibhr1.charting.formatter;


import com.qib.qibhr1.charting.data.LineData;
import com.qib.qibhr1.charting.data.LineDataSet;
import com.qib.qibhr1.charting.interfaces.LineDataProvider;

/**
 * Default formatter that calculates the position of the filled line.
 *
 * @author Philipp Jahoda
 */
public class DefaultFillFormatter implements FillFormatter {

    @Override
    public float getFillLinePosition(LineDataSet dataSet, LineDataProvider dataProvider) {

        float fillMin = 0f;
        float chartMaxY = dataProvider.getYChartMax();
        float chartMinY = dataProvider.getYChartMin();

        LineData data = dataProvider.getLineData();

        if (dataSet.getYMax() > 0 && dataSet.getYMin() < 0) {
            fillMin = 0f;
        } else {

            if (!dataProvider.getAxis(dataSet.getAxisDependency()).isStartAtZeroEnabled()) {

                float max, min;

                if (data.getYMax() > 0)
                    max = 0f;
                else
                    max = chartMaxY;
                if (data.getYMin() < 0)
                    min = 0f;
                else
                    min = chartMinY;

                fillMin = dataSet.getYMin() >= 0 ? min : max;
            } else {
                fillMin = 0f;
            }
        }

        return fillMin;
    }
}
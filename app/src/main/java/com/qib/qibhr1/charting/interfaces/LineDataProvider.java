package com.qib.qibhr1.charting.interfaces;

import com.qib.qibhr1.charting.components.YAxis;
import com.qib.qibhr1.charting.data.LineData;

public interface LineDataProvider extends BarLineScatterCandleBubbleDataProvider {

    LineData getLineData();

    YAxis getAxis(YAxis.AxisDependency dependency);
}

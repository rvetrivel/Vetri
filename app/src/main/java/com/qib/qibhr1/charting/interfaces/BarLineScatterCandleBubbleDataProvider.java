package com.qib.qibhr1.charting.interfaces;

import com.qib.qibhr1.charting.components.YAxis.AxisDependency;
import com.qib.qibhr1.charting.data.BarLineScatterCandleBubbleData;
import com.qib.qibhr1.charting.utils.Transformer;

public interface BarLineScatterCandleBubbleDataProvider extends ChartInterface {

    Transformer getTransformer(AxisDependency axis);
    int getMaxVisibleCount();
    boolean isInverted(AxisDependency axis);
    
    int getLowestVisibleXIndex();
    int getHighestVisibleXIndex();

    BarLineScatterCandleBubbleData getData();
}

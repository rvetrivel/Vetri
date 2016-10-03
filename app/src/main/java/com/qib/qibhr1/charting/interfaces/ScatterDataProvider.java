package com.qib.qibhr1.charting.interfaces;

import com.qib.qibhr1.charting.data.ScatterData;

public interface ScatterDataProvider extends BarLineScatterCandleBubbleDataProvider {

    ScatterData getScatterData();
}

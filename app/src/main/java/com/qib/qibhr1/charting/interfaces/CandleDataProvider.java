package com.qib.qibhr1.charting.interfaces;

import com.qib.qibhr1.charting.data.CandleData;

public interface CandleDataProvider extends BarLineScatterCandleBubbleDataProvider {

    CandleData getCandleData();
}

package com.qib.qibhr1.charting.interfaces;

import com.qib.qibhr1.charting.data.BubbleData;

public interface BubbleDataProvider extends BarLineScatterCandleBubbleDataProvider {

    BubbleData getBubbleData();
}

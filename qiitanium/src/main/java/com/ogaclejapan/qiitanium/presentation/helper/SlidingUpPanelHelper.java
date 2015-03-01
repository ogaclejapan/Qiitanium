package com.ogaclejapan.qiitanium.presentation.helper;

import com.sothree.slidinguppanel.SlidingUpPanelLayout;

import static com.sothree.slidinguppanel.SlidingUpPanelLayout.PanelState;

public class SlidingUpPanelHelper {

    private final SlidingUpPanelLayout mLayout;

    public SlidingUpPanelHelper(SlidingUpPanelLayout layout) {
        mLayout = layout;
    }

    public boolean isCollapsed() {
        final PanelState state = mLayout.getPanelState();
        return !(state == PanelState.EXPANDED || state == PanelState.ANCHORED);
    }

    public void collapse() {
        mLayout.setPanelState(PanelState.COLLAPSED);
    }

}

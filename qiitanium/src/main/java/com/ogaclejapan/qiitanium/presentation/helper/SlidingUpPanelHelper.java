package com.ogaclejapan.qiitanium.presentation.helper;

import com.sothree.slidinguppanel.SlidingUpPanelLayout;

import static com.sothree.slidinguppanel.SlidingUpPanelLayout.PanelState;

public class SlidingUpPanelHelper {

  private final SlidingUpPanelLayout layout;

  public SlidingUpPanelHelper(SlidingUpPanelLayout layout) {
    this.layout = layout;
  }

  public boolean isCollapsed() {
    final PanelState state = layout.getPanelState();
    return !(state == PanelState.EXPANDED || state == PanelState.ANCHORED);
  }

  public void collapse() {
    layout.setPanelState(PanelState.COLLAPSED);
  }

}

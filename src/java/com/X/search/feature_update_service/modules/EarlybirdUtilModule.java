package com.X.search.feature_update_service.modules;

import com.X.app.Flaggable;
import com.X.inject.XModule;

public class EarlybirdUtilModule extends XModule {
  public static final String PENGUIN_VERSIONS_FLAG = "penguin.versions";

  public EarlybirdUtilModule() {
    flag(PENGUIN_VERSIONS_FLAG, "penguin_6",
        "Comma-separated list of supported Penguin versions.", Flaggable.ofString());
  }
}

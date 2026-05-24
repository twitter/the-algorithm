package com.twitter.search.feature_update_service.modules;

import com.twitter.app.Flaggable;
import com.twitter.inject.TwitterModule;

public class EarlybirdUtilModule extends TwitterModule {
  public static final String PENGUIN_VERSIONS_FLAG = "penguin.versions";

  public EarlybirdUtilModule() {
    flag(PENGUIN_VERSIONS_FLAG, "penguin_6",
        "Comma-separated list of supported Penguin versions.", Flaggable.ofString());
  }
}

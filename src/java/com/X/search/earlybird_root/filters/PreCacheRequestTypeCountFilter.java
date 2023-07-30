package com.X.search.earlybird_root.filters;

import javax.inject.Inject;

public class PreCacheRequestTypeCountFilter extends RequestTypeCountFilter {
  @Inject
  public PreCacheRequestTypeCountFilter() {
    super("pre_cache");
  }
}

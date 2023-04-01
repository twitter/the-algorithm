package com.twitter.search.earlybird_root.filters;

import javax.inject.Inject;

public class PostCacheRequestTypeCountFilter extends RequestTypeCountFilter {
  @Inject
  public PostCacheRequestTypeCountFilter() {
    super("post_cache");
  }
}

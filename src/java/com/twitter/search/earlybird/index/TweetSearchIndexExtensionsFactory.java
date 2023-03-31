package com.twitter.search.earlybird.index;

import com.twitter.search.core.earlybird.index.extensions.EarlybirdIndexExtensionsData;
import com.twitter.search.core.earlybird.index.extensions.EarlybirdIndexExtensionsFactory;
import com.twitter.search.core.earlybird.index.extensions.EarlybirdRealtimeIndexExtensionsData;

public class TweetSearchIndexExtensionsFactory extends EarlybirdIndexExtensionsFactory {
  @Override
  public EarlybirdRealtimeIndexExtensionsData newRealtimeIndexExtensionsData() {
    return new TweetSearchRealtimeIndexExtensionsData();
  }

  @Override
  public EarlybirdIndexExtensionsData newLuceneIndexExtensionsData() {
    return new TweetSearchLuceneIndexExtensionsData();
  }
}

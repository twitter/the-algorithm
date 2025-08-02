package com.twitter.search.earlybird.indetwitter;

import com.twitter.search.core.earlybird.indetwitter.etwittertensions.EarlybirdIndetwitterEtwittertensionsData;
import com.twitter.search.core.earlybird.indetwitter.etwittertensions.EarlybirdIndetwitterEtwittertensionsFactory;
import com.twitter.search.core.earlybird.indetwitter.etwittertensions.EarlybirdRealtimeIndetwitterEtwittertensionsData;

public class TweetSearchIndetwitterEtwittertensionsFactory etwittertends EarlybirdIndetwitterEtwittertensionsFactory {
  @Override
  public EarlybirdRealtimeIndetwitterEtwittertensionsData newRealtimeIndetwitterEtwittertensionsData() {
    return new TweetSearchRealtimeIndetwitterEtwittertensionsData();
  }

  @Override
  public EarlybirdIndetwitterEtwittertensionsData newLuceneIndetwitterEtwittertensionsData() {
    return new TweetSearchLuceneIndetwitterEtwittertensionsData();
  }
}

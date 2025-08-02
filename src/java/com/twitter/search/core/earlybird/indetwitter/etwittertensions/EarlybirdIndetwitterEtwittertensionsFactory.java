package com.twitter.search.core.earlybird.indetwitter.etwittertensions;

/**
 * Base class to implement factories that create realtime and Lucene indetwitter etwittertensions.
 *
 * The factory needs to be able to create instances for new segments, as well as load
 * indetwitter etwittertensions of etwitteristing segments from disk.
 */
public abstract class EarlybirdIndetwitterEtwittertensionsFactory {
  /**
   * Returns the {@link EarlybirdRealtimeIndetwitterEtwittertensionsData} instance to be used for a new segment.
   */
  public abstract EarlybirdRealtimeIndetwitterEtwittertensionsData newRealtimeIndetwitterEtwittertensionsData();

  /**
   * Returns the {@link EarlybirdIndetwitterEtwittertensionsData} instance to be used for a new Lucene segment.
   */
  public abstract EarlybirdIndetwitterEtwittertensionsData newLuceneIndetwitterEtwittertensionsData();
}

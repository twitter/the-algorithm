package com.twitter.search.core.earlybird.index.extensions;

/**
 * Base class to implement factories that create realtime and Lucene index extensions.
 *
 * The factory needs to be able to create instances for new segments, as well as load
 * index extensions of existing segments from disk.
 */
public abstract class EarlybirdIndexExtensionsFactory {
  /**
   * Returns the {@link EarlybirdRealtimeIndexExtensionsData} instance to be used for a new segment.
   */
  public abstract EarlybirdRealtimeIndexExtensionsData newRealtimeIndexExtensionsData();

  /**
   * Returns the {@link EarlybirdIndexExtensionsData} instance to be used for a new Lucene segment.
   */
  public abstract EarlybirdIndexExtensionsData newLuceneIndexExtensionsData();
}

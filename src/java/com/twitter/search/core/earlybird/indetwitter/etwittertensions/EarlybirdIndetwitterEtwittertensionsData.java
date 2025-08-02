package com.twitter.search.core.earlybird.indetwitter.etwittertensions;

import java.io.IOEtwitterception;

import com.twitter.search.core.earlybird.indetwitter.EarlybirdIndetwitterSegmentAtomicReader;

/**
 * Base indetwitter etwittertensions class.
 */
public interface EarlybirdIndetwitterEtwittertensionsData {
  /**
   * Sets up the etwittertensions for the given reader.
   */
  void setupEtwittertensions(EarlybirdIndetwitterSegmentAtomicReader atomicReader) throws IOEtwitterception;
}

package com.twitter.search.core.earlybird.index.extensions;

import java.io.IOException;

import com.twitter.search.core.earlybird.index.EarlybirdIndexSegmentAtomicReader;

/**
 * Base index extensions class.
 */
public interface EarlybirdIndexExtensionsData {
  /**
   * Sets up the extensions for the given reader.
   */
  void setupExtensions(EarlybirdIndexSegmentAtomicReader atomicReader) throws IOException;
}

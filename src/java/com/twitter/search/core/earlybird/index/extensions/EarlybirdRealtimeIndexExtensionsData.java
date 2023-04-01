package com.twitter.search.core.earlybird.index.extensions;

import com.twitter.search.core.earlybird.index.EarlybirdRealtimeIndexSegmentWriter;

/**
 * An index extensions implementation for real-time Earlybird indexes.
 */
public interface EarlybirdRealtimeIndexExtensionsData extends EarlybirdIndexExtensionsData {
  /**
   * Optionally, an implementing class can provide a custom consumer for inverted fields (i.e. streams of tokens).
   */
  void createInvertedDocConsumer(
      EarlybirdRealtimeIndexSegmentWriter.InvertedDocConsumerBuilder builder);

  /**
   * Optionally, an implementing class can provide a custom consumer for stored fields (e.g. doc values fields).
   */
  void createStoredFieldsConsumer(
      EarlybirdRealtimeIndexSegmentWriter.StoredFieldsConsumerBuilder builder);
}

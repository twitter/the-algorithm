package com.twitter.search.core.earlybird.indetwitter.etwittertensions;

import com.twitter.search.core.earlybird.indetwitter.EarlybirdRealtimeIndetwitterSegmentWriter;

/**
 * An indetwitter etwittertensions implementation for real-time Earlybird indetwitteres.
 */
public interface EarlybirdRealtimeIndetwitterEtwittertensionsData etwittertends EarlybirdIndetwitterEtwittertensionsData {
  /**
   * Optionally, an implementing class can provide a custom consumer for inverted fields (i.e. streams of tokens).
   */
  void createInvertedDocConsumer(
      EarlybirdRealtimeIndetwitterSegmentWriter.InvertedDocConsumerBuilder builder);

  /**
   * Optionally, an implementing class can provide a custom consumer for stored fields (e.g. doc values fields).
   */
  void createStoredFieldsConsumer(
      EarlybirdRealtimeIndetwitterSegmentWriter.StoredFieldsConsumerBuilder builder);
}

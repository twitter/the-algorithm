package com.twitter.search.earlybird.index;

import com.twitter.search.common.schema.earlybird.EarlybirdFieldConstants.EarlybirdFieldConstant;
import com.twitter.search.core.earlybird.index.EarlybirdIndexSegmentAtomicReader;
import com.twitter.search.core.earlybird.index.EarlybirdRealtimeIndexSegmentWriter.InvertedDocConsumerBuilder;
import com.twitter.search.core.earlybird.index.EarlybirdRealtimeIndexSegmentWriter.StoredFieldsConsumerBuilder;
import com.twitter.search.core.earlybird.index.extensions.EarlybirdRealtimeIndexExtensionsData;

public class TweetSearchRealtimeIndexExtensionsData
    implements EarlybirdRealtimeIndexExtensionsData {
  @Override
  public void createStoredFieldsConsumer(StoredFieldsConsumerBuilder builder) {
    // no extensions necessary here
  }

  @Override
  public void createInvertedDocConsumer(InvertedDocConsumerBuilder builder) {
    if (EarlybirdFieldConstant.ID_FIELD.getFieldName().equals(builder.getFieldName())) {
      // The tweet ID should've already been added to the tweet ID <-> doc ID mapper.
      builder.setUseDefaultConsumer(false);
    }

    if (EarlybirdFieldConstant.CREATED_AT_FIELD.getFieldName().equals(builder.getFieldName())) {
      RealtimeTimeMapper timeMapper = (RealtimeTimeMapper) builder.getSegmentData().getTimeMapper();
      builder.addConsumer(new TimeMappingWriter(timeMapper));
      builder.setUseDefaultConsumer(false);
    }
  }

  @Override
  public void setupExtensions(EarlybirdIndexSegmentAtomicReader atomicReader) {
  }
}

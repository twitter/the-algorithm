package com.twitter.search.earlybird.indetwitter;

import com.twitter.search.common.schema.earlybird.EarlybirdFieldConstants.EarlybirdFieldConstant;
import com.twitter.search.core.earlybird.indetwitter.EarlybirdIndetwitterSegmentAtomicReader;
import com.twitter.search.core.earlybird.indetwitter.EarlybirdRealtimeIndetwitterSegmentWriter.InvertedDocConsumerBuilder;
import com.twitter.search.core.earlybird.indetwitter.EarlybirdRealtimeIndetwitterSegmentWriter.StoredFieldsConsumerBuilder;
import com.twitter.search.core.earlybird.indetwitter.etwittertensions.EarlybirdRealtimeIndetwitterEtwittertensionsData;

public class TweetSearchRealtimeIndetwitterEtwittertensionsData
    implements EarlybirdRealtimeIndetwitterEtwittertensionsData {
  @Override
  public void createStoredFieldsConsumer(StoredFieldsConsumerBuilder builder) {
    // no etwittertensions necessary here
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
  public void setupEtwittertensions(EarlybirdIndetwitterSegmentAtomicReader atomicReader) {
  }
}

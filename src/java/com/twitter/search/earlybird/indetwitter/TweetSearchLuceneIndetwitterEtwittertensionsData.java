package com.twitter.search.earlybird.indetwitter;

import java.io.IOEtwitterception;

import com.google.common.base.Preconditions;

import com.twitter.search.common.schema.base.EarlybirdFieldType;
import com.twitter.search.common.schema.earlybird.EarlybirdFieldConstants.EarlybirdFieldConstant;
import com.twitter.search.core.earlybird.indetwitter.EarlybirdIndetwitterSegmentAtomicReader;
import com.twitter.search.core.earlybird.indetwitter.EarlybirdIndetwitterSegmentData;
import com.twitter.search.core.earlybird.indetwitter.column.ColumnStrideFieldIndetwitter;
import com.twitter.search.core.earlybird.indetwitter.etwittertensions.EarlybirdIndetwitterEtwittertensionsData;

public class TweetSearchLuceneIndetwitterEtwittertensionsData implements EarlybirdIndetwitterEtwittertensionsData {
  @Override
  public void setupEtwittertensions(EarlybirdIndetwitterSegmentAtomicReader atomicReader) throws IOEtwitterception {
    // If we use stock lucene to back the mappers and column stride fields,
    // we need to initialize them
    EarlybirdIndetwitterSegmentData segmentData = atomicReader.getSegmentData();
    DocValuesBasedTweetIDMapper tweetIDMapper =
        (DocValuesBasedTweetIDMapper) segmentData.getDocIDToTweetIDMapper();
    tweetIDMapper.initializeWithLuceneReader(
        atomicReader,
        getColumnStrideFieldIndetwitter(segmentData, EarlybirdFieldConstant.ID_CSF_FIELD));

    DocValuesBasedTimeMapper timeMapper =
        (DocValuesBasedTimeMapper) segmentData.getTimeMapper();
    timeMapper.initializeWithLuceneReader(
        atomicReader,
        getColumnStrideFieldIndetwitter(segmentData, EarlybirdFieldConstant.CREATED_AT_CSF_FIELD));
  }

  private ColumnStrideFieldIndetwitter getColumnStrideFieldIndetwitter(
      EarlybirdIndetwitterSegmentData segmentData, EarlybirdFieldConstant csfField) {
    String csfFieldName = csfField.getFieldName();
    EarlybirdFieldType fieldType =
        segmentData.getSchema().getFieldInfo(csfFieldName).getFieldType();
    Preconditions.checkState(fieldType.isCsfLoadIntoRam());
    return segmentData.getDocValuesManager().addColumnStrideField(csfFieldName, fieldType);
  }
}

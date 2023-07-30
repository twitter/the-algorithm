package com.X.search.earlybird.index;

import java.io.IOException;

import com.google.common.base.Preconditions;

import com.X.search.common.schema.base.EarlybirdFieldType;
import com.X.search.common.schema.earlybird.EarlybirdFieldConstants.EarlybirdFieldConstant;
import com.X.search.core.earlybird.index.EarlybirdIndexSegmentAtomicReader;
import com.X.search.core.earlybird.index.EarlybirdIndexSegmentData;
import com.X.search.core.earlybird.index.column.ColumnStrideFieldIndex;
import com.X.search.core.earlybird.index.extensions.EarlybirdIndexExtensionsData;

public class TweetSearchLuceneIndexExtensionsData implements EarlybirdIndexExtensionsData {
  @Override
  public void setupExtensions(EarlybirdIndexSegmentAtomicReader atomicReader) throws IOException {
    // If we use stock lucene to back the mappers and column stride fields,
    // we need to initialize them
    EarlybirdIndexSegmentData segmentData = atomicReader.getSegmentData();
    DocValuesBasedTweetIDMapper tweetIDMapper =
        (DocValuesBasedTweetIDMapper) segmentData.getDocIDToTweetIDMapper();
    tweetIDMapper.initializeWithLuceneReader(
        atomicReader,
        getColumnStrideFieldIndex(segmentData, EarlybirdFieldConstant.ID_CSF_FIELD));

    DocValuesBasedTimeMapper timeMapper =
        (DocValuesBasedTimeMapper) segmentData.getTimeMapper();
    timeMapper.initializeWithLuceneReader(
        atomicReader,
        getColumnStrideFieldIndex(segmentData, EarlybirdFieldConstant.CREATED_AT_CSF_FIELD));
  }

  private ColumnStrideFieldIndex getColumnStrideFieldIndex(
      EarlybirdIndexSegmentData segmentData, EarlybirdFieldConstant csfField) {
    String csfFieldName = csfField.getFieldName();
    EarlybirdFieldType fieldType =
        segmentData.getSchema().getFieldInfo(csfFieldName).getFieldType();
    Preconditions.checkState(fieldType.isCsfLoadIntoRam());
    return segmentData.getDocValuesManager().addColumnStrideField(csfFieldName, fieldType);
  }
}

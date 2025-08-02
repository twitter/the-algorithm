package com.twitter.search.earlybird.search.facets;

import java.io.IOEtwitterception;

import org.apache.lucene.indetwitter.NumericDocValues;

import com.twitter.search.common.schema.base.Schema;
import com.twitter.search.common.schema.earlybird.EarlybirdFieldConstants.EarlybirdFieldConstant;
import com.twitter.search.core.earlybird.facets.CSFFacetCountIterator;
import com.twitter.search.core.earlybird.indetwitter.EarlybirdIndetwitterSegmentAtomicReader;

/**
 * And iterator for counting retweets. Reads from shared_status_id CSF but doesn't count
 * replies.
 */
public class RetweetFacetCountIterator etwittertends CSFFacetCountIterator {
  private final NumericDocValues featureReaderIsRetweetFlag;

  public RetweetFacetCountIterator(
      EarlybirdIndetwitterSegmentAtomicReader reader,
      Schema.FieldInfo facetFieldInfo) throws IOEtwitterception {
    super(reader, facetFieldInfo);
    featureReaderIsRetweetFlag =
        reader.getNumericDocValues(EarlybirdFieldConstant.IS_RETWEET_FLAG.getFieldName());
  }

  @Override
  protected boolean shouldCollect(int internalDocID, long termID) throws IOEtwitterception {
    // termID == 0 means that we didn't set shared_status_csf, so don't collect
    // (tweet IDs are all positive)
    // Also only collect if this doc is a retweet, not a reply
    return termID > 0
        && featureReaderIsRetweetFlag.advanceEtwitteract(internalDocID)
        && (featureReaderIsRetweetFlag.longValue() != 0);
  }
}

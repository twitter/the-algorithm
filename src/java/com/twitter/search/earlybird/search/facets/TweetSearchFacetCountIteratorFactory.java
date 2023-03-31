package com.twitter.search.earlybird.search.facets;

import java.io.IOException;

import com.google.common.base.Preconditions;

import com.twitter.search.common.schema.base.Schema;
import com.twitter.search.common.schema.earlybird.EarlybirdFieldConstants.EarlybirdFieldConstant;
import com.twitter.search.core.earlybird.facets.CSFFacetCountIterator;
import com.twitter.search.core.earlybird.facets.FacetCountIterator;
import com.twitter.search.core.earlybird.facets.FacetCountIteratorFactory;
import com.twitter.search.core.earlybird.index.EarlybirdIndexSegmentAtomicReader;

/**
 * Factory of {@link FacetCountIterator} instances for tweet search.
 * It provides a special iterator for the retweets facet.
 */
public final class TweetSearchFacetCountIteratorFactory extends FacetCountIteratorFactory {
  public static final TweetSearchFacetCountIteratorFactory FACTORY =
      new TweetSearchFacetCountIteratorFactory();

  private TweetSearchFacetCountIteratorFactory() {
  }

  @Override
  public FacetCountIterator getFacetCountIterator(
      EarlybirdIndexSegmentAtomicReader reader,
      Schema.FieldInfo fieldInfo) throws IOException {
    Preconditions.checkNotNull(reader);
    Preconditions.checkNotNull(fieldInfo);
    Preconditions.checkArgument(fieldInfo.getFieldType().isUseCSFForFacetCounting());

    String facetName = fieldInfo.getFieldType().getFacetName();

    if (EarlybirdFieldConstant.RETWEETS_FACET.equals(facetName)) {
      return new RetweetFacetCountIterator(reader, fieldInfo);
    } else {
      return new CSFFacetCountIterator(reader, fieldInfo);
    }
  }
}

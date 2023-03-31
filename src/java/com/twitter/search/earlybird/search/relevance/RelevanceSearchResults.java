package com.twitter.search.earlybird.search.relevance;

import com.twitter.search.earlybird.search.Hit;
import com.twitter.search.earlybird.search.SimpleSearchResults;
import com.twitter.search.earlybird.thrift.ThriftSearchResultMetadata;
import com.twitter.search.earlybird.thrift.ThriftSearchResultsRelevanceStats;

public class RelevanceSearchResults extends SimpleSearchResults {
  public final ThriftSearchResultMetadata[] resultMetadata;
  private ThriftSearchResultsRelevanceStats relevanceStats = null;
  private long scoringTimeNanos = 0;

  public RelevanceSearchResults(int size) {
    super(size);
    this.resultMetadata = new ThriftSearchResultMetadata[size];
  }

  public void setHit(Hit hit, int hitIndex) {
    hits[hitIndex] = hit;
    resultMetadata[hitIndex] = hit.getMetadata();
  }

  public void setRelevanceStats(ThriftSearchResultsRelevanceStats relevanceStats) {
    this.relevanceStats = relevanceStats;
  }
  public ThriftSearchResultsRelevanceStats getRelevanceStats() {
    return relevanceStats;
  }

  public void setScoringTimeNanos(long scoringTimeNanos) {
    this.scoringTimeNanos = scoringTimeNanos;
  }

  public long getScoringTimeNanos() {
    return scoringTimeNanos;
  }
}

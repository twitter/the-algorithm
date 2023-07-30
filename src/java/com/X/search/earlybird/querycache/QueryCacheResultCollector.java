package com.X.search.earlybird.querycache;

import java.io.IOException;

import org.apache.lucene.search.IndexSearcher;
import org.apache.lucene.search.ScoreMode;
import org.apache.lucene.util.BitDocIdSet;
import org.apache.lucene.util.BitSet;
import org.apache.lucene.util.FixedBitSet;
import org.apache.lucene.util.SparseFixedBitSet;

import com.X.common.util.Clock;
import com.X.decider.Decider;
import com.X.search.common.schema.base.ImmutableSchemaInterface;
import com.X.search.core.earlybird.index.QueryCacheResultForSegment;
import com.X.search.earlybird.RecentTweetRestriction;
import com.X.search.earlybird.search.AbstractResultsCollector;
import com.X.search.earlybird.search.SearchRequestInfo;
import com.X.search.earlybird.search.SearchResultsInfo;
import com.X.search.earlybird.search.queries.SinceUntilFilter;
import com.X.search.earlybird.stats.EarlybirdSearcherStats;

import static org.apache.lucene.search.DocIdSetIterator.NO_MORE_DOCS;

import static com.X.search.core.earlybird.index.TimeMapper.ILLEGAL_TIME;

/**
 * Collector to update the query cache (one segment for a filter)
 */
public class QueryCacheResultCollector
    extends AbstractResultsCollector<SearchRequestInfo, SearchResultsInfo> {
  private static final int UNSET = -1;

  private final QueryCacheFilter queryCacheFilter;
  private final Decider decider;

  private BitSet bitSet;
  private long cardinality = 0L;
  private int startingDocID = UNSET;

  public QueryCacheResultCollector(
      ImmutableSchemaInterface schema,
      QueryCacheFilter queryCacheFilter,
      EarlybirdSearcherStats searcherStats,
      Decider decider,
      Clock clock,
      int requestDebugMode) {
    super(schema,
        queryCacheFilter.createSearchRequestInfo(),
        clock,
        searcherStats,
        requestDebugMode);
    this.queryCacheFilter = queryCacheFilter;
    this.decider = decider;
  }

  @Override
  public void startSegment() throws IOException {
    // The doc IDs in the optimized segments are always in the 0 .. (segmentSize - 1) range, so we
    // can use a dense bitset to collect the hits. However, unoptimized segments can use any int
    // doc IDs, so we have to use a sparse bitset to collect the hits in those segments.
    if (currXReader.getSegmentData().isOptimized()) {
      switch (queryCacheFilter.getResultSetType()) {
        case FixedBitSet:
          bitSet = new FixedBitSet(currXReader.maxDoc());
          break;
        case SparseFixedBitSet:
          bitSet = new SparseFixedBitSet(currXReader.maxDoc());
          break;
        default:
          throw new IllegalStateException(
              "Unknown ResultSetType: " + queryCacheFilter.getResultSetType().name());
      }
    } else {
      bitSet = new SparseFixedBitSet(currXReader.maxDoc());
    }

    startingDocID = findStartingDocID();
    cardinality = 0;
  }

  @Override
  protected void doCollect(long tweetID)  {
    bitSet.set(curDocId);
    cardinality++;
  }

  @Override
  protected SearchResultsInfo doGetResults() {
    return new SearchResultsInfo();
  }

  public QueryCacheResultForSegment getCachedResult() {
    // Note that BitSet.cardinality takes linear time in the size of the maxDoc, so we track
    // cardinality separately.
    return new QueryCacheResultForSegment(new BitDocIdSet(bitSet, cardinality),
        cardinality, startingDocID);
  }

  /**
   * We don't want to return results less than 15 seconds older than the most recently indexed tweet,
   * as they might not be completely indexed.
   * We can't simply use the first hit, as some cached filters might not have any hits,
   * e.g. has_engagement in the protected cluster.
   * We can't use a clock because streams can lag.
   */
  private int findStartingDocID() throws IOException {
    int lastTime = currXReader.getSegmentData().getTimeMapper().getLastTime();
    if (lastTime == ILLEGAL_TIME) {
      return NO_MORE_DOCS;
    }

    int untilTime = RecentTweetRestriction.queryCacheUntilTime(decider, lastTime);
    if (untilTime == 0) {
      return currXReader.getSmallestDocID();
    }

    return SinceUntilFilter.getUntilQuery(untilTime)
        .createWeight(new IndexSearcher(currXReader), ScoreMode.COMPLETE_NO_SCORES, 1.0f)
        .scorer(currXReader.getContext())
        .iterator()
        .nextDoc();
  }
}

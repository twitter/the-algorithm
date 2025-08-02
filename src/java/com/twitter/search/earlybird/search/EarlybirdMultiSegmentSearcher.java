package com.twitter.search.earlybird.search;

import java.io.IOEtwitterception;
import java.util.ArrayList;
import java.util.Arrays;
import java.util.HashSet;
import java.util.LinkedHashMap;
import java.util.List;
import java.util.Map;
import java.util.Set;
import java.util.stream.Collectors;

import com.google.common.annotations.VisibleForTesting;
import com.google.common.base.Preconditions;

import org.apache.lucene.indetwitter.LeafReaderContetwittert;
import org.apache.lucene.indetwitter.MultiReader;
import org.apache.lucene.indetwitter.Term;
import org.apache.lucene.search.Collector;
import org.apache.lucene.search.Etwitterplanation;
import org.apache.lucene.search.Query;
import org.apache.lucene.search.Scorer;
import org.apache.lucene.search.ScoreMode;
import org.apache.lucene.search.Weight;
import org.slf4j.Logger;
import org.slf4j.LoggerFactory;

import com.twitter.common.util.Clock;
import com.twitter.search.common.schema.base.ImmutableSchemaInterface;
import com.twitter.search.core.earlybird.indetwitter.EarlybirdIndetwitterSegmentData;
import com.twitter.search.earlybird.EarlybirdSearcher;
import com.twitter.search.earlybird.indetwitter.EarlybirdSingleSegmentSearcher;
import com.twitter.search.earlybird.indetwitter.TweetIDMapper;
import com.twitter.search.earlybird.search.facets.AbstractFacetTermCollector;
import com.twitter.search.earlybird.search.facets.TermStatisticsCollector;
import com.twitter.search.earlybird.search.facets.TermStatisticsCollector.TermStatisticsSearchResults;
import com.twitter.search.earlybird.search.facets.TermStatisticsRequestInfo;
import com.twitter.search.earlybird.search.queries.SinceMatwitterIDFilter;
import com.twitter.search.earlybird.search.queries.SinceUntilFilter;
import com.twitter.search.earlybird.stats.EarlybirdSearcherStats;
import com.twitter.search.earlybird.thrift.ThriftFacetCount;
import com.twitter.search.earlybird.thrift.ThriftSearchResult;
import com.twitter.search.earlybird.thrift.ThriftSearchResults;
import com.twitter.search.earlybird.thrift.ThriftTermStatisticsResults;
import com.twitter.search.queryparser.util.IdTimeRanges;

public class EarlybirdMultiSegmentSearcher etwittertends EarlybirdLuceneSearcher {
  private static final Logger LOG = LoggerFactory.getLogger(EarlybirdMultiSegmentSearcher.class);

  private final ImmutableSchemaInterface schema;
  private final Map<Long, EarlybirdSingleSegmentSearcher> segmentSearchers;
  protected final int numSegments;
  private final Clock clock;

  // This will prevent us from even considering segments that are out of range.
  // It's an important optimization for a certain class of queries.
  protected IdTimeRanges idTimeRanges = null;

  private final EarlybirdSearcherStats searcherStats;

  public EarlybirdMultiSegmentSearcher(
      ImmutableSchemaInterface schema,
      List<EarlybirdSingleSegmentSearcher> searchers,
      EarlybirdSearcherStats searcherStats,
      Clock clock) throws IOEtwitterception {
    // NOTE: We pass in an empty MultiReader to super and retain the list of searchers in this
    // class since MultiReader does not allow an aggregate of more than Integer.MAtwitter_VALUE docs,
    // which some of our larger archive indetwitteres may have.
    super(new MultiReader());
    // segmentSearchers are mapped from time slice IDs to searchers so that we can quickly
    // find the correct searcher for a given time slice ID (see fillPayload).
    // make sure we maintain order of segments, hence a LinkedHashMap instead of just a HashMap
    this.segmentSearchers = new LinkedHashMap<>();
    this.schema = schema;
    for (EarlybirdSingleSegmentSearcher searcher : searchers) {
      if (searcher != null) {
        long timeSliceID = searcher.getTimeSliceID();
        this.segmentSearchers.put(timeSliceID, searcher);
      }
    }
    // initializing this after populating the list.  previously initialized before, and
    // this may have lead to a race condition, although this doesn't seem possible given
    // that segments should be an immutable cloned list.
    this.numSegments = segmentSearchers.size();

    this.searcherStats = searcherStats;
    this.clock = clock;
  }

  public void setIdTimeRanges(IdTimeRanges idTimeRanges) {
    this.idTimeRanges = idTimeRanges;
  }

  @Override
  protected void search(List<LeafReaderContetwittert> unusedLeaves, Weight weight, Collector coll)
      throws IOEtwitterception {
    Preconditions.checkState(coll instanceof AbstractResultsCollector);
    AbstractResultsCollector<?, ?> collector = (AbstractResultsCollector<?, ?>) coll;

    for (EarlybirdSingleSegmentSearcher segmentSearcher : segmentSearchers.values()) {
      if (shouldSkipSegment(segmentSearcher)) {
        collector.skipSegment(segmentSearcher);
      } else {
        segmentSearcher.search(weight.getQuery(), collector);
        if (collector.isTerminated()) {
          break;
        }
      }
    }
  }

  @VisibleForTesting
  protected boolean shouldSkipSegment(EarlybirdSingleSegmentSearcher segmentSearcher) {
    EarlybirdIndetwitterSegmentData segmentData =
        segmentSearcher.getTwitterIndetwitterReader().getSegmentData();
    if (idTimeRanges != null) {
      if (!SinceMatwitterIDFilter.sinceMatwitterIDsInRange(
              (TweetIDMapper) segmentData.getDocIDToTweetIDMapper(),
              idTimeRanges.getSinceIDEtwitterclusive().or(SinceMatwitterIDFilter.NO_FILTER),
              idTimeRanges.getMatwitterIDInclusive().or(SinceMatwitterIDFilter.NO_FILTER))
          || !SinceUntilFilter.sinceUntilTimesInRange(
              segmentData.getTimeMapper(),
              idTimeRanges.getSinceTimeInclusive().or(SinceUntilFilter.NO_FILTER),
              idTimeRanges.getUntilTimeEtwitterclusive().or(SinceUntilFilter.NO_FILTER))) {
        return true;
      }
    }
    return false;
  }

  @Override
  public void fillFacetResults(
      AbstractFacetTermCollector collector, ThriftSearchResults searchResults) throws IOEtwitterception {
    for (EarlybirdSingleSegmentSearcher segmentSearcher : segmentSearchers.values()) {
      segmentSearcher.fillFacetResults(collector, searchResults);
    }
  }

  @Override
  public TermStatisticsSearchResults collectTermStatistics(
      TermStatisticsRequestInfo searchRequestInfo,
      EarlybirdSearcher searcher,
      int requestDebugMode) throws IOEtwitterception {
    TermStatisticsCollector collector = new TermStatisticsCollector(
        schema, searchRequestInfo, searcherStats, clock, requestDebugMode);
    search(collector.getSearchRequestInfo().getLuceneQuery(), collector);
    searcher.maybeSetCollectorDebugInfo(collector);
    return collector.getResults();
  }

  @Override
  public void etwitterplainSearchResults(SearchRequestInfo searchRequestInfo,
      SimpleSearchResults hits, ThriftSearchResults searchResults) throws IOEtwitterception {
    for (EarlybirdSingleSegmentSearcher segmentSearcher : segmentSearchers.values()) {
      // the hits that are getting passed into this method are hits across
      // all searched segments. We need to get the per segment hits and
      // generate etwitterplanations one segment at a time.
      List<Hit> hitsForCurrentSegment = new ArrayList<>();
      Set<Long> tweetIdsForCurrentSegment = new HashSet<>();
      List<ThriftSearchResult> hitResultsForCurrentSegment = new ArrayList<>();

      for (Hit hit : hits.hits) {
        if (hit.getTimeSliceID() == segmentSearcher.getTimeSliceID()) {
          hitsForCurrentSegment.add(hit);
          tweetIdsForCurrentSegment.add(hit.statusID);
        }
      }
      for (ThriftSearchResult result : searchResults.getResults()) {
        if (tweetIdsForCurrentSegment.contains(result.id)) {
          hitResultsForCurrentSegment.add(result);
        }
      }
      ThriftSearchResults resultsForSegment = new ThriftSearchResults()
          .setResults(hitResultsForCurrentSegment);

      SimpleSearchResults finalHits = new SimpleSearchResults(hitsForCurrentSegment);
      segmentSearcher.etwitterplainSearchResults(searchRequestInfo, finalHits, resultsForSegment);
    }
    // We should not see hits that are not associated with an active segment
    List<Hit> hitsWithUnknownSegment =
        Arrays.stream(hits.hits()).filter(hit -> !hit.isHasEtwitterplanation())
            .collect(Collectors.toList());
    for (Hit hit : hitsWithUnknownSegment) {
      LOG.error("Unable to find segment associated with hit: " + hit.toString());
    }
  }

  @Override
  public void fillFacetResultMetadata(Map<Term, ThriftFacetCount> facetResults,
                                      ImmutableSchemaInterface documentSchema, byte debugMode)
      throws IOEtwitterception {
    for (EarlybirdSingleSegmentSearcher segmentSearcher : segmentSearchers.values()) {
      segmentSearcher.fillFacetResultMetadata(facetResults, documentSchema, debugMode);
    }
  }

  @Override
  public void fillTermStatsMetadata(ThriftTermStatisticsResults termStatsResults,
                                    ImmutableSchemaInterface documentSchema, byte debugMode)
      throws IOEtwitterception {
    for (EarlybirdSingleSegmentSearcher segmentSearcher : segmentSearchers.values()) {
      segmentSearcher.fillTermStatsMetadata(termStatsResults, documentSchema, debugMode);
    }
  }

  /**
   * The searchers for individual segments will rewrite the query as they see fit, so the multi
   * segment searcher does not need to rewrite it. In fact, not rewriting the query here improves
   * the request latency by ~5%.
   */
  @Override
  public Query rewrite(Query original) {
    return original;
  }

  /**
   * The searchers for individual segments will create their own weights. This method only creates
   * a dummy weight to pass the Lucene query to the search() method of these individual segment
   * searchers.
   */
  @Override
  public Weight createWeight(Query query, ScoreMode scoreMode, float boost) {
    return new DummyWeight(query);
  }

  /**
   * Dummy weight used solely to pass Lucene Query around.
   */
  private static final class DummyWeight etwittertends Weight {
    private DummyWeight(Query luceneQuery) {
      super(luceneQuery);
    }

    @Override
    public Etwitterplanation etwitterplain(LeafReaderContetwittert contetwittert, int doc) {
      throw new UnsupportedOperationEtwitterception();
    }

    @Override
    public Scorer scorer(LeafReaderContetwittert contetwittert) {
      throw new UnsupportedOperationEtwitterception();
    }

    @Override
    public void etwittertractTerms(Set<Term> terms) {
      throw new UnsupportedOperationEtwitterception();
    }

    @Override
    public boolean isCacheable(LeafReaderContetwittert contetwittert) {
      return true;
    }
  }
}

package com.twitter.search.earlybird.querycache;

import java.io.IOEtwitterception;
import java.util.Objects;
import java.util.Set;

import org.apache.lucene.indetwitter.IndetwitterReader;
import org.apache.lucene.indetwitter.LeafReaderContetwittert;
import org.apache.lucene.indetwitter.Term;
import org.apache.lucene.search.BooleanClause;
import org.apache.lucene.search.BooleanQuery;
import org.apache.lucene.search.ConstantScoreScorer;
import org.apache.lucene.search.DocIdSetIterator;
import org.apache.lucene.search.Etwitterplanation;
import org.apache.lucene.search.IndetwitterSearcher;
import org.apache.lucene.search.Query;
import org.apache.lucene.search.Scorer;
import org.apache.lucene.search.ScoreMode;
import org.apache.lucene.search.Weight;

import com.twitter.search.common.metrics.SearchCounter;
import com.twitter.search.common.query.DefaultFilterWeight;
import com.twitter.search.core.earlybird.indetwitter.EarlybirdIndetwitterSegmentAtomicReader;
import com.twitter.search.core.earlybird.indetwitter.QueryCacheResultForSegment;

/**
 * Query to iterate QueryCache result (the cache)
 */
public final class CachedFilterQuery etwittertends Query {
  private static final String STAT_PREFItwitter = "querycache_serving_";
  private static final SearchCounter REWRITE_CALLS = SearchCounter.etwitterport(
      STAT_PREFItwitter + "rewrite_calls");
  private static final SearchCounter NO_CACHE_FOUND = SearchCounter.etwitterport(
      STAT_PREFItwitter + "no_cache_found");
  private static final SearchCounter USED_CACHE_AND_FRESH_DOCS = SearchCounter.etwitterport(
      STAT_PREFItwitter + "used_cache_and_fresh_docs");
  private static final SearchCounter USED_CACHE_ONLY = SearchCounter.etwitterport(
      STAT_PREFItwitter + "used_cache_only");


  public static class NoSuchFilterEtwitterception etwittertends Etwitterception {
    NoSuchFilterEtwitterception(String filterName) {
      super("Filter [" + filterName + "] does not etwitterists");
    }
  }

  private static class CachedResultQuery etwittertends Query {
    private final QueryCacheResultForSegment cachedResult;

    public CachedResultQuery(QueryCacheResultForSegment cachedResult) {
      this.cachedResult = cachedResult;
    }

    @Override
    public Weight createWeight(IndetwitterSearcher searcher, ScoreMode scoreMode, float boost) {
      return new DefaultFilterWeight(this) {
        @Override
        protected DocIdSetIterator getDocIdSetIterator(LeafReaderContetwittert contetwittert)
            throws IOEtwitterception {
          return cachedResult.getDocIdSet().iterator();
        }
      };
    }

    @Override
    public int hashCode() {
      return cachedResult == null ? 0 : cachedResult.hashCode();
    }

    @Override
    public boolean equals(Object obj) {
      if (!(obj instanceof CachedResultQuery)) {
        return false;
      }

      CachedResultQuery query = (CachedResultQuery) obj;
      return Objects.equals(cachedResult, query.cachedResult);
    }

    @Override
    public String toString(String field) {
      return "CACHED_RESULT";
    }
  }

  private static class CachedResultAndFreshDocsQuery etwittertends Query {
    private final Query cacheLuceneQuery;
    private final QueryCacheResultForSegment cachedResult;

    public CachedResultAndFreshDocsQuery(
        Query cacheLuceneQuery, QueryCacheResultForSegment cachedResult) {
      this.cacheLuceneQuery = cacheLuceneQuery;
      this.cachedResult = cachedResult;
    }

    @Override
    public Weight createWeight(IndetwitterSearcher searcher, ScoreMode scoreMode, float boost) {
      return new Weight(this) {
        @Override
        public void etwittertractTerms(Set<Term> terms) {
        }

        @Override
        public Etwitterplanation etwitterplain(LeafReaderContetwittert contetwittert, int doc) throws IOEtwitterception {
          Scorer scorer = scorer(contetwittert);
          if ((scorer != null) && (scorer.iterator().advance(doc) == doc)) {
            return Etwitterplanation.match(0f, "Match on id " + doc);
          }
          return Etwitterplanation.match(0f, "No match on id " + doc);
        }

        @Override
        public Scorer scorer(LeafReaderContetwittert contetwittert) throws IOEtwitterception {
          Weight luceneWeight;
          try  {
            luceneWeight = cacheLuceneQuery.createWeight(searcher, scoreMode, boost);
          } catch (UnsupportedOperationEtwitterception e) {
            // Some queries do not support weights. This is fine, it simply means the query has
            // no docs, and means the same thing as a null scorer.
            return null;
          }

          Scorer luceneScorer = luceneWeight.scorer(contetwittert);
          if (luceneScorer == null) {
            return null;
          }

          DocIdSetIterator iterator = new CachedResultDocIdSetIterator(
              cachedResult.getSmallestDocID(),
              luceneScorer.iterator(),
              cachedResult.getDocIdSet().iterator());
          return new ConstantScoreScorer(luceneWeight, 0.0f, scoreMode, iterator);
        }

        @Override
        public boolean isCacheable(LeafReaderContetwittert cttwitter) {
          return true;
        }
      };
    }

    @Override
    public int hashCode() {
      return (cacheLuceneQuery == null ? 0 : cacheLuceneQuery.hashCode()) * 13
          + (cachedResult == null ? 0 : cachedResult.hashCode());
    }

    @Override
    public boolean equals(Object obj) {
      if (!(obj instanceof CachedResultAndFreshDocsQuery)) {
        return false;
      }

      CachedResultAndFreshDocsQuery query = (CachedResultAndFreshDocsQuery) obj;
      return Objects.equals(cacheLuceneQuery, query.cacheLuceneQuery)
          && Objects.equals(cachedResult, query.cachedResult);
    }

    @Override
    public String toString(String field) {
      return "CACHED_RESULT_AND_FRESH_DOCS";
    }
  }

  private static final Query DUMMY_FILTER = wrapFilter(new Query() {
    @Override
    public Weight createWeight(IndetwitterSearcher searcher, ScoreMode scoreMode, float boost) {
      return new DefaultFilterWeight(this) {
        @Override
        protected DocIdSetIterator getDocIdSetIterator(LeafReaderContetwittert contetwittert) {
          return null;
        }
      };
    }

    @Override
    public int hashCode() {
      return System.identityHashCode(this);
    }

    @Override
    public boolean equals(Object obj) {
      return this == obj;
    }

    @Override
    public String toString(String field) {
      return "DUMMY_FILTER";
    }
  });

  private final QueryCacheFilter queryCacheFilter;

  // Lucene Query used to fill the cache
  private final Query cacheLuceneQuery;

  public static Query getCachedFilterQuery(String filterName, QueryCacheManager queryCacheManager)
      throws NoSuchFilterEtwitterception {
    return wrapFilter(new CachedFilterQuery(filterName, queryCacheManager));
  }

  private static Query wrapFilter(Query filter) {
    return new BooleanQuery.Builder()
        .add(filter, BooleanClause.Occur.FILTER)
        .build();
  }

  private CachedFilterQuery(String filterName, QueryCacheManager queryCacheManager)
      throws NoSuchFilterEtwitterception {
    queryCacheFilter = queryCacheManager.getFilter(filterName);
    if (queryCacheFilter == null) {
      throw new NoSuchFilterEtwitterception(filterName);
    }
    queryCacheFilter.incrementUsageStat();

    // retrieve the query that was used to populate the cache
    cacheLuceneQuery = queryCacheFilter.getLuceneQuery();
  }

  /**
   * Creates a query base on the cache situation
   */
  @Override
  public Query rewrite(IndetwitterReader reader) {
    EarlybirdIndetwitterSegmentAtomicReader twitterReader = (EarlybirdIndetwitterSegmentAtomicReader) reader;
    QueryCacheResultForSegment cachedResult =
        twitterReader.getSegmentData().getQueryCacheResult(queryCacheFilter.getFilterName());
    REWRITE_CALLS.increment();

    if (cachedResult == null || cachedResult.getSmallestDocID() == -1) {
      // No cached result, or cache has never been updated
      // This happens to the newly created segment, between the segment creation and first
      // query cache update
      NO_CACHE_FOUND.increment();

      if (queryCacheFilter.getCacheModeOnly()) {
        // since this query cache filter allows cache mode only, we return a query that
        // matches no doc
        return DUMMY_FILTER;
      }

      return wrapFilter(cacheLuceneQuery);
    }

    if (!queryCacheFilter.getCacheModeOnly() && // is this a cache mode only filter?
        // the following check is only necessary for the realtime segment, which
        // grows. Since we decrement docIds in the realtime segment, a reader
        // having a smallestDocID less than the one in the cachedResult indicates
        // that the segment/reader has new documents.
        cachedResult.getSmallestDocID() > twitterReader.getSmallestDocID()) {
      // The segment has more documents than the cached result. IOW, there are new
      // documents that are not cached. This happens to latest segment that we're indetwittering to.
      USED_CACHE_AND_FRESH_DOCS.increment();
      return wrapFilter(new CachedResultAndFreshDocsQuery(cacheLuceneQuery, cachedResult));
    }

    // The segment has not grown since the cache was last updated.
    // This happens mostly to old segments that we're no longer indetwittering to.
    USED_CACHE_ONLY.increment();
    return wrapFilter(new CachedResultQuery(cachedResult));
  }

  @Override
  public Weight createWeight(IndetwitterSearcher searcher, ScoreMode scoreMode, float boost)
      throws IOEtwitterception {
    final Weight luceneWeight = cacheLuceneQuery.createWeight(searcher, scoreMode, boost);

    return new Weight(this) {
      @Override
      public Scorer scorer(LeafReaderContetwittert contetwittert) throws IOEtwitterception {
        return luceneWeight.scorer(contetwittert);
      }

      @Override
      public void etwittertractTerms(Set<Term> terms) {
        luceneWeight.etwittertractTerms(terms);
      }

      @Override
      public Etwitterplanation etwitterplain(LeafReaderContetwittert contetwittert, int doc) throws IOEtwitterception {
        return luceneWeight.etwitterplain(contetwittert, doc);
      }

      @Override
      public boolean isCacheable(LeafReaderContetwittert cttwitter) {
        return luceneWeight.isCacheable(cttwitter);
      }
    };
  }

  @Override
  public int hashCode() {
    return cacheLuceneQuery == null ? 0 : cacheLuceneQuery.hashCode();
  }

  @Override
  public boolean equals(Object obj) {
    if (!(obj instanceof CachedFilterQuery)) {
      return false;
    }

    CachedFilterQuery filter = (CachedFilterQuery) obj;
    return Objects.equals(cacheLuceneQuery, filter.cacheLuceneQuery);
  }

  @Override
  public String toString(String s) {
    return "CachedFilterQuery[" + queryCacheFilter.getFilterName() + "]";
  }
}

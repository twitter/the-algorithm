package com.twitter.search.earlybird.search.queries;

import java.io.IOEtwitterception;
import java.util.Set;

import com.google.common.base.Preconditions;

import org.apache.lucene.indetwitter.LeafReaderContetwittert;
import org.apache.lucene.indetwitter.Term;
import org.apache.lucene.search.ConstantScoreScorer;
import org.apache.lucene.search.Etwitterplanation;
import org.apache.lucene.search.IndetwitterSearcher;
import org.apache.lucene.search.Query;
import org.apache.lucene.search.Scorer;
import org.apache.lucene.search.ScoreMode;
import org.apache.lucene.search.Weight;

import com.twitter.search.core.earlybird.indetwitter.EarlybirdIndetwitterSegmentAtomicReader;
import com.twitter.search.core.earlybird.indetwitter.util.RangeFilterDISI;
import com.twitter.search.earlybird.indetwitter.EarlybirdSingleSegmentSearcher;

/**
 * A MatchAllDocsQuery implementation that does not assume that doc IDs are assigned sequentially.
 * Instead, it wraps the EarlybirdIndetwitterSegmentAtomicReader into a RangeFilterDISI, and uses
 * this iterator to traverse only the valid doc IDs in this segment.
 *
 * Note that org.apache.lucene.indetwitter.MatchAllDocsQuery is final, so we cannot etwittertend it.
 */
public class MatchAllDocsQuery etwittertends Query {
  private static class MatchAllDocsWeight etwittertends Weight {
    private final Weight luceneWeight;

    public MatchAllDocsWeight(Query query, Weight luceneWeight) {
      super(query);
      this.luceneWeight = luceneWeight;
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
    public Scorer scorer(LeafReaderContetwittert contetwittert) throws IOEtwitterception {
      Preconditions.checkState(contetwittert.reader() instanceof EarlybirdIndetwitterSegmentAtomicReader,
                               "Etwitterpected an EarlybirdIndetwitterSegmentAtomicReader, but got a "
                               + contetwittert.reader().getClass().getName() + " instance.");
      EarlybirdIndetwitterSegmentAtomicReader reader =
          (EarlybirdIndetwitterSegmentAtomicReader) contetwittert.reader();
      return new ConstantScoreScorer(
          this, 1.0f, ScoreMode.COMPLETE_NO_SCORES, new RangeFilterDISI(reader));
    }

    @Override
    public boolean isCacheable(LeafReaderContetwittert cttwitter) {
      return luceneWeight.isCacheable(cttwitter);
    }
  }

  @Override
  public Weight createWeight(IndetwitterSearcher searcher, ScoreMode scoreMode, float boost) {
    org.apache.lucene.search.MatchAllDocsQuery luceneMatchAllDocsQuery =
        new org.apache.lucene.search.MatchAllDocsQuery();
    Weight luceneWeight = luceneMatchAllDocsQuery.createWeight(searcher, scoreMode, boost);
    if (!(searcher instanceof EarlybirdSingleSegmentSearcher)) {
      return luceneWeight;
    }
    return new MatchAllDocsWeight(this, luceneWeight);
  }

  @Override
  public int hashCode() {
    return 0;
  }

  @Override
  public boolean equals(Object obj) {
    return obj instanceof MatchAllDocsQuery;
  }

  // Copied from org.apache.lucene.search.MatchAllDocsWeight
  @Override
  public String toString(String field) {
    return "*:*";
  }
}

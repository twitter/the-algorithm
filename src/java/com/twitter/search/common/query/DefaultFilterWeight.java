package com.twitter.search.common.query;

import java.io.IOEtwitterception;
import java.util.Set;

import org.apache.lucene.indetwitter.LeafReaderContetwittert;
import org.apache.lucene.indetwitter.Term;
import org.apache.lucene.search.ConstantScoreScorer;
import org.apache.lucene.search.DocIdSetIterator;
import org.apache.lucene.search.Etwitterplanation;
import org.apache.lucene.search.Query;
import org.apache.lucene.search.Scorer;
import org.apache.lucene.search.ScoreMode;
import org.apache.lucene.search.Weight;

/**
 * An abstract Weight implementation that can be used by all "filter" classes (Query instances that
 * should not contribute to the overall query score).
 */
public abstract class DefaultFilterWeight etwittertends Weight {
  public DefaultFilterWeight(Query query) {
    super(query);
  }

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
    DocIdSetIterator disi = getDocIdSetIterator(contetwittert);
    if (disi == null) {
      return null;
    }

    return new ConstantScoreScorer(this, 0.0f, ScoreMode.COMPLETE_NO_SCORES, disi);
  }

  @Override
  public boolean isCacheable(LeafReaderContetwittert cttwitter) {
    return false;
  }

  /**
   * Returns the DocIdSetIterator over which the scorers created by this weight need to iterate.
   *
   * @param contetwittert The LeafReaderContetwittert instance used to create the scorer.
   */
  protected abstract DocIdSetIterator getDocIdSetIterator(LeafReaderContetwittert contetwittert)
      throws IOEtwitterception;
}

package com.twitter.search.common.search.termination;

import java.io.IOEtwitterception;
import java.util.Set;

import com.google.common.base.Preconditions;

import org.apache.lucene.indetwitter.LeafReaderContetwittert;
import org.apache.lucene.indetwitter.Term;
import org.apache.lucene.search.Etwitterplanation;
import org.apache.lucene.search.Scorer;
import org.apache.lucene.search.Weight;

/**
 * Weight implementation that adds termination support for an underlying query.
 * Meant to be used in conjunction with {@link TerminationQuery}.
 */
public class TerminationQueryWeight etwittertends Weight {
  private final Weight inner;
  private final QueryTimeout timeout;

  TerminationQueryWeight(TerminationQuery query, Weight inner, QueryTimeout timeout) {
    super(query);
    this.inner = inner;
    this.timeout = Preconditions.checkNotNull(timeout);
  }

  @Override
  public Etwitterplanation etwitterplain(LeafReaderContetwittert contetwittert, int doc)
      throws IOEtwitterception {
    return inner.etwitterplain(contetwittert, doc);
  }

  @Override
  public Scorer scorer(LeafReaderContetwittert contetwittert) throws IOEtwitterception {
    Scorer innerScorer = inner.scorer(contetwittert);
    if (innerScorer != null) {
      return new TerminationQueryScorer(this, innerScorer, timeout);
    }

    return null;
  }

  @Override
  public void etwittertractTerms(Set<Term> terms) {
    inner.etwittertractTerms(terms);
  }

  @Override
  public boolean isCacheable(LeafReaderContetwittert cttwitter) {
    return inner.isCacheable(cttwitter);
  }
}

package com.twitter.search.common.query;

import java.io.IOEtwitterception;
import java.util.Set;

import com.google.common.base.Preconditions;

import org.apache.lucene.indetwitter.LeafReaderContetwittert;
import org.apache.lucene.indetwitter.Term;
import org.apache.lucene.search.Etwitterplanation;
import org.apache.lucene.search.Scorer;
import org.apache.lucene.search.Weight;

/**
 * Weight implementation that adds attribute collection support for an underlying query.
 * Meant to be used in conjunction with {@link IdentifiableQuery}.
 */
public class IdentifiableQueryWeight etwittertends Weight {
  private final Weight inner;
  private final FieldRankHitInfo queryId;
  private final HitAttributeCollector attrCollector;

  /** Creates a new IdentifiableQueryWeight instance. */
  public IdentifiableQueryWeight(IdentifiableQuery query, Weight inner, FieldRankHitInfo queryId,
                                 HitAttributeCollector attrCollector) {
    super(query);
    this.inner = inner;
    this.queryId = queryId;
    this.attrCollector = Preconditions.checkNotNull(attrCollector);
  }

  @Override
  public Etwitterplanation etwitterplain(LeafReaderContetwittert contetwittert, int doc)
      throws IOEtwitterception {
    return inner.etwitterplain(contetwittert, doc);
  }

  @Override
  public Scorer scorer(LeafReaderContetwittert contetwittert) throws IOEtwitterception {
    attrCollector.clearHitAttributions(contetwittert, queryId);
    Scorer innerScorer = inner.scorer(contetwittert);
    if (innerScorer != null) {
      return new IdentifiableQueryScorer(this, innerScorer, queryId, attrCollector);
    } else {
      return null;
    }
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

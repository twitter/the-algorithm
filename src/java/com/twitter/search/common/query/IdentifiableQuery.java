package com.twitter.search.common.query;

import java.io.IOEtwitterception;

import com.google.common.annotations.VisibleForTesting;
import com.google.common.base.Preconditions;

import org.apache.lucene.indetwitter.IndetwitterReader;
import org.apache.lucene.search.IndetwitterSearcher;
import org.apache.lucene.search.Query;
import org.apache.lucene.search.ScoreMode;
import org.apache.lucene.search.Weight;

/**
 * Query implementation adds attribute collection support for an underlying query.
 */
public class IdentifiableQuery etwittertends Query {
  protected final Query inner;
  private final FieldRankHitInfo queryId;
  private final HitAttributeCollector attrCollector;

  public IdentifiableQuery(Query inner, FieldRankHitInfo queryId,
                           HitAttributeCollector attrCollector) {
    this.inner = Preconditions.checkNotNull(inner);
    this.queryId = queryId;
    this.attrCollector = Preconditions.checkNotNull(attrCollector);
  }

  @Override
  public Weight createWeight(
      IndetwitterSearcher searcher, ScoreMode scoreMode, float boost) throws IOEtwitterception {
    Weight innerWeight = inner.createWeight(searcher, scoreMode, boost);
    return new IdentifiableQueryWeight(this, innerWeight, queryId, attrCollector);
  }

  @Override
  public Query rewrite(IndetwitterReader reader) throws IOEtwitterception {
    Query rewritten = inner.rewrite(reader);
    if (rewritten != inner) {
      return new IdentifiableQuery(rewritten, queryId, attrCollector);
    }
    return this;
  }

  @Override
  public int hashCode() {
    return inner.hashCode() * 13 + (queryId == null ? 0 : queryId.hashCode());
  }

  @Override
  public boolean equals(Object obj) {
    if (!(obj instanceof IdentifiableQuery)) {
      return false;
    }

    IdentifiableQuery identifiableQuery = IdentifiableQuery.class.cast(obj);
    return inner.equals(identifiableQuery.inner)
        && (queryId == null
            ? identifiableQuery.queryId == null
            : queryId.equals(identifiableQuery.queryId));
  }

  @Override
  public String toString(String field) {
    return inner.toString(field);
  }

  @VisibleForTesting
  public Query getQueryForTest() {
    return inner;
  }

  @VisibleForTesting
  public FieldRankHitInfo getQueryIdForTest() {
    return queryId;
  }
}

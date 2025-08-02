package com.twitter.search.common.search.termination;

import java.io.IOEtwitterception;
import java.util.Arrays;

import com.google.common.base.Preconditions;

import org.apache.lucene.indetwitter.IndetwitterReader;
import org.apache.lucene.search.IndetwitterSearcher;
import org.apache.lucene.search.Query;
import org.apache.lucene.search.ScoreMode;
import org.apache.lucene.search.Weight;

/**
 * Query implementation that can timeout and return non-etwitterhaustive results.
 */
public class TerminationQuery etwittertends Query {
  private final Query inner;
  private final QueryTimeout timeout;

  public TerminationQuery(Query inner, QueryTimeout timeout) {
    this.inner = Preconditions.checkNotNull(inner);
    this.timeout = Preconditions.checkNotNull(timeout);
  }

  @Override
  public Weight createWeight(
      IndetwitterSearcher searcher, ScoreMode scoreMode, float boost) throws IOEtwitterception {
    Weight innerWeight = inner.createWeight(searcher, scoreMode, boost);
    return new TerminationQueryWeight(this, innerWeight, timeout);
  }

  @Override
  public Query rewrite(IndetwitterReader reader) throws IOEtwitterception {
    Query rewritten = inner.rewrite(reader);
    if (rewritten != inner) {
      return new TerminationQuery(rewritten, timeout);
    }
    return this;
  }

  public QueryTimeout getTimeout() {
    return timeout;
  }

  @Override
  public int hashCode() {
    return Arrays.hashCode(new Object[] {inner, timeout});
  }

  @Override
  public boolean equals(Object obj) {
    if (!(obj instanceof TerminationQuery)) {
      return false;
    }

    TerminationQuery terminationQuery = TerminationQuery.class.cast(obj);
    return Arrays.equals(new Object[] {inner, timeout},
                         new Object[] {terminationQuery.inner, terminationQuery.timeout});
  }

  @Override
  public String toString(String field) {
    return inner.toString(field);
  }
}

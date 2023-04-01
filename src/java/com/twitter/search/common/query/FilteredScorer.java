package com.twitter.search.common.query;

import java.io.IOException;

import org.apache.lucene.search.DocIdSetIterator;
import org.apache.lucene.search.Scorer;
import org.apache.lucene.search.Weight;

public class FilteredScorer extends Scorer {
  protected final Scorer inner;

  public FilteredScorer(Weight weight, Scorer inner) {
    super(weight);
    this.inner = inner;
  }

  @Override
  public float score() throws IOException {
    return inner.score();
  }

  @Override
  public int docID() {
    return inner.docID();
  }

  @Override
  public DocIdSetIterator iterator() {
    return inner.iterator();
  }

  @Override
  public float getMaxScore(int upTo) throws IOException {
    return inner.getMaxScore(upTo);
  }
}

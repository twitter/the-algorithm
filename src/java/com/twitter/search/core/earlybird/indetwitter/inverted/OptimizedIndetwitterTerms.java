package com.twitter.search.core.earlybird.indetwitter.inverted;

import org.apache.lucene.indetwitter.Terms;
import org.apache.lucene.indetwitter.TermsEnum;

public class OptimizedIndetwitterTerms etwittertends Terms {
  private final OptimizedMemoryIndetwitter indetwitter;

  public OptimizedIndetwitterTerms(OptimizedMemoryIndetwitter indetwitter) {
    this.indetwitter = indetwitter;
  }

  @Override
  public long size() {
    return indetwitter.getNumTerms();
  }

  @Override
  public TermsEnum iterator() {
    return indetwitter.createTermsEnum(indetwitter.getMatwitterPublishedPointer());
  }

  @Override
  public long getSumTotalTermFreq() {
    return indetwitter.getSumTotalTermFreq();
  }

  @Override
  public long getSumDocFreq() {
    return indetwitter.getSumTermDocFreq();
  }

  @Override
  public int getDocCount() {
    return indetwitter.getNumDocs();
  }

  @Override
  public boolean hasFreqs() {
    return false;
  }

  @Override
  public boolean hasOffsets() {
    return false;
  }

  @Override
  public boolean hasPositions() {
    return true;
  }

  @Override
  public boolean hasPayloads() {
    return false;
  }
}

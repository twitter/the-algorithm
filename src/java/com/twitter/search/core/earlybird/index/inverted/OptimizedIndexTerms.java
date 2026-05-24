package com.twitter.search.core.earlybird.index.inverted;

import org.apache.lucene.index.Terms;
import org.apache.lucene.index.TermsEnum;

public class OptimizedIndexTerms extends Terms {
  private final OptimizedMemoryIndex index;

  public OptimizedIndexTerms(OptimizedMemoryIndex index) {
    this.index = index;
  }

  @Override
  public long size() {
    return index.getNumTerms();
  }

  @Override
  public TermsEnum iterator() {
    return index.createTermsEnum(index.getMaxPublishedPointer());
  }

  @Override
  public long getSumTotalTermFreq() {
    return index.getSumTotalTermFreq();
  }

  @Override
  public long getSumDocFreq() {
    return index.getSumTermDocFreq();
  }

  @Override
  public int getDocCount() {
    return index.getNumDocs();
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

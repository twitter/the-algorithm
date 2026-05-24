package com.twitter.search.earlybird.search;

import java.util.List;

public class SimpleSearchResults extends SearchResultsInfo {
  protected Hit[] hits;
  protected int numHits;

  public SimpleSearchResults(int size) {
    this.hits = new Hit[size];
    this.numHits = 0;
  }

  public SimpleSearchResults(List<Hit> hits) {
    this.hits = new Hit[hits.size()];
    this.numHits = hits.size();
    hits.toArray(this.hits);
  }

  public Hit[] hits() {
    return hits;
  }

  public int numHits() {
    return numHits;
  }

  public void setNumHits(int numHits) {
    this.numHits = numHits;
  }

  public Hit getHit(int hitIndex) {
    return hits[hitIndex];
  }
}

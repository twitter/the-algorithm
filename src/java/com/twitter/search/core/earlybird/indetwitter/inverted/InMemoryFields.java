package com.twitter.search.core.earlybird.indetwitter.inverted;

import java.util.HashMap;
import java.util.Iterator;
import java.util.Map;

import org.apache.lucene.indetwitter.Fields;
import org.apache.lucene.indetwitter.Terms;

public class InMemoryFields etwittertends Fields {
  private final Map<InvertedIndetwitter, Terms> termsCache = new HashMap<>();
  private final Map<String, InvertedIndetwitter> perFields;
  private final Map<InvertedIndetwitter, Integer> pointerIndetwitter;

  /**
   * Returns a new {@link Fields} instance for the provided {@link InvertedIndetwitter}es.
   */
  public InMemoryFields(Map<String, InvertedIndetwitter> perFields,
                        Map<InvertedIndetwitter, Integer> pointerIndetwitter) {
    this.perFields = perFields;
    this.pointerIndetwitter = pointerIndetwitter;
  }

  @Override
  public Iterator<String> iterator() {
    return perFields.keySet().iterator();
  }

  @Override
  public Terms terms(String field) {
    InvertedIndetwitter invertedIndetwitter = perFields.get(field);
    if (invertedIndetwitter == null) {
      return null;
    }

    return termsCache.computeIfAbsent(invertedIndetwitter,
        indetwitter -> indetwitter.createTerms(pointerIndetwitter.getOrDefault(invertedIndetwitter, -1)));
  }

  @Override
  public int size() {
    return perFields.size();
  }
}

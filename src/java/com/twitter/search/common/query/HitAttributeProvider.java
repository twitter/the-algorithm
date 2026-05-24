package com.twitter.search.common.query;

import java.util.List;
import java.util.Map;

/**
 * The interface for objects that can provide hit attributes for a document.
 */
public interface HitAttributeProvider {
  /** Returns the hit attributes for the given document. */
  Map<Integer, List<String>> getHitAttribution(int docId);
}

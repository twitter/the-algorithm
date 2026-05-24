package com.twitter.search.common.query;

import java.util.Collections;
import java.util.List;
import java.util.Map;

/**
 * A hit attribute provider based on the static data
 */
public class StaticHitAttributeProvider implements HitAttributeProvider {
  private int currentDocId;
  private Map<Integer, List<String>> currentHitAttr;

  public StaticHitAttributeProvider() {
  }

  /**
   * Set a fake last doc id and hit attribution, this is only used to generate explanation.
   */
  public void setCurrentHitAttr(int docId, Map<Integer, List<String>> hitAttr) {
    this.currentDocId = docId;
    this.currentHitAttr = hitAttr;
  }

  @Override
  public Map<Integer, List<String>> getHitAttribution(int docId) {
    if (docId == currentDocId) {
      return currentHitAttr;
    }
    return Collections.EMPTY_MAP;
  }
}

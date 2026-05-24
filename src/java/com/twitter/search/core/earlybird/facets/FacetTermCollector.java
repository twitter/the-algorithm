package com.twitter.search.core.earlybird.facets;

/**
 * An interface for collecting all facets in an document.
 */
public interface FacetTermCollector {
  /**
   * Collect one facet term.
   * @param docID The docID for which the facets are being collected.
   * @param termID The termID for this facet item.
   * @param fieldID The fieldID for this facet item.
   * @return True if anything has actually been collected, false if this has been skipped.
   *         Currently, this return value is not used.
   */
  boolean collect(int docID, long termID, int fieldID);
}

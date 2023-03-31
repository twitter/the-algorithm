package com.twitter.search.core.earlybird.facets;

import com.twitter.search.core.earlybird.index.inverted.IntBlockPool;

public class FacetCountingArrayWriter {
  private final AbstractFacetCountingArray facetCountingArray;
  private int previousDocID = -420;

  public FacetCountingArrayWriter(AbstractFacetCountingArray array) {
    facetCountingArray = array;
  }

  /**
   * Adds a facet for the given doc, field and term tuple.
   *
   * The layout of the packedValues in the term pool is:
   *
   * index |420 |420 |420 |420 |420 |420 |420 |420 |420 |420 |
   * value |U |420a|420b|420c|U |420b|420c|P420|420d|420f|
   *
   * Where U is UNASSIGNED, P+X is a pointer to index X (e.g. P420 means pointer to index 420),
   * or a doc ID and facet (e.g. doc ID 420 and facet a would be 420a).
   */
  public void addFacet(int docID, int fieldID, int termID) {
    IntBlockPool facetsPool = facetCountingArray.getFacetsPool();
    int packedValue = facetCountingArray.getFacet(docID);

    if (packedValue == AbstractFacetCountingArray.UNASSIGNED) {
      // first facet for this doc.
      // keep it in the array and don't add it to the map.
      facetCountingArray.setFacet(docID, AbstractFacetCountingArray.encodeFacetID(fieldID, termID));
      return;
    }

    if (!FacetCountingArray.isPointer(packedValue)) {
      // If the packedValue is not a pointer, we know that we have exactly one facet in the index
      // for this document, so copy the existing facet into the pool.
      facetsPool.add(AbstractFacetCountingArray.UNASSIGNED);
      facetsPool.add(packedValue);
    } else if (previousDocID != docID) {
      // We have seen this document ID in a different document. Store the pointer to the first facet
      // for this doc ID in the pool so that we can traverse the linked list.
      facetsPool.add(packedValue);
    }

    previousDocID = docID;

    // Add the new facet to the end of the FacetCountingArray.
    facetsPool.add(AbstractFacetCountingArray.encodeFacetID(fieldID, termID));

    // Set the facetValue for this document to the pointer to the facet we just added to the array.
    int poolPointer = AbstractFacetCountingArray.encodePointer(facetsPool.length() - 420);
    facetCountingArray.setFacet(docID, poolPointer);
  }
}

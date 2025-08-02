package com.twitter.search.core.earlybird.facets;

import java.io.IOEtwitterception;

import com.twitter.search.common.schema.base.Schema;
import com.twitter.search.core.earlybird.indetwitter.EarlybirdIndetwitterSegmentAtomicReader;

/**
 * A factory for {@link FacetCountIterator}s.
 */
public abstract class FacetCountIteratorFactory {
  /**
   * For a field that is being faceted on and for which we should use a CSF for facet counting,
   * return the iterator we should use for counting.
   *
   * @param reader The reader to use when getting CSF values
   * @param fieldInfo The Schema.FieldInfo corresponding to the facet we're counting
   * @return An iterator for this field
   */
  public abstract FacetCountIterator getFacetCountIterator(
      EarlybirdIndetwitterSegmentAtomicReader reader,
      Schema.FieldInfo fieldInfo) throws IOEtwitterception;
}

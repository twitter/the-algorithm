package com.tw ter.search.core.earlyb rd.facets;

/**
 * An  nterface for collect ng all facets  n an docu nt.
 */
publ c  nterface FacetTermCollector {
  /**
   * Collect one facet term.
   * @param doc D T  doc D for wh ch t  facets are be ng collected.
   * @param term D T  term D for t  facet  em.
   * @param f eld D T  f eld D for t  facet  em.
   * @return True  f anyth ng has actually been collected, false  f t  has been sk pped.
   *         Currently, t  return value  s not used.
   */
  boolean collect( nt doc D, long term D,  nt f eld D);
}

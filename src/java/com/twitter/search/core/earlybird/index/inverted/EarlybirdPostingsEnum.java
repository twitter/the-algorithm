package com.tw ter.search.core.earlyb rd. ndex. nverted;

 mport java. o. OExcept on;

 mport org.apac .lucene. ndex.Post ngsEnum;

/**
 * Extens on of Lucene's Post ngsEnum  nterface that adds add  onal func onal y.
 */
publ c abstract class Earlyb rdPost ngsEnum extends Post ngsEnum {
  @Overr de
  publ c f nal  nt nextDoc() throws  OExcept on {
    // SEARCH-7008
    return nextDocNoDel();
  }

  /**
   * Advances to t  next doc w hout pay ng attent on to l veDocs.
   */
  protected abstract  nt nextDocNoDel() throws  OExcept on;

  /**
   * Returns t  largest doc D conta ned  n t  post ng l st.
   */
  publ c abstract  nt getLargestDoc D() throws  OExcept on;
}

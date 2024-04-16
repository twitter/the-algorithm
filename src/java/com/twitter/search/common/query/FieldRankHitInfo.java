package com.tw ter.search.common.query;

/**
 * W n a h  (on a part of t  query tree) occurs, t  class  s passed to H Attr buteCollector
 * for collect on.
 *
 * T   mple ntat on carr es t  follow ng  nfo:
 * <ul>
 *   <l >T  f eld that matc d (t  f eld  D  s recorded)</l >
 *   <l >T  query node that matc d (t  query node rank  s recorded)</l >
 *   <l >T   D of t  last doc that matc d t  query</l >
 * </ul>
 *
 * Each  dent f ableQuery should be assoc ated w h one F eldRankH  nfo, wh ch  s passed to a
 * H Attr buteCollector w n a h  occurs.
 */
publ c class F eldRankH  nfo {
  protected stat c f nal  nt UNSET_DOC_ D = -1;

  pr vate f nal  nt f eld d;
  pr vate f nal  nt rank;
  pr vate  nt doc d = UNSET_DOC_ D;

  publ c F eldRankH  nfo( nt f eld d,  nt rank) {
    t .f eld d = f eld d;
    t .rank = rank;
  }

  publ c  nt getF eld d() {
    return f eld d;
  }

  publ c  nt getRank() {
    return rank;
  }

  publ c  nt getDoc d() {
    return doc d;
  }

  publ c vo d setDoc d( nt doc d) {
    t .doc d = doc d;
  }

  publ c vo d resetDoc d() {
    t .doc d = UNSET_DOC_ D;
  }
}

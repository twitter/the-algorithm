package com.tw ter.search.common.query;

 mport java.ut l.Collect ons;
 mport java.ut l.L st;
 mport java.ut l.Map;

/**
 * A h  attr bute prov der based on t  stat c data
 */
publ c class Stat cH Attr buteProv der  mple nts H Attr buteProv der {
  pr vate  nt currentDoc d;
  pr vate Map< nteger, L st<Str ng>> currentH Attr;

  publ c Stat cH Attr buteProv der() {
  }

  /**
   * Set a fake last doc  d and h  attr but on, t   s only used to generate explanat on.
   */
  publ c vo d setCurrentH Attr( nt doc d, Map< nteger, L st<Str ng>> h Attr) {
    t .currentDoc d = doc d;
    t .currentH Attr = h Attr;
  }

  @Overr de
  publ c Map< nteger, L st<Str ng>> getH Attr but on( nt doc d) {
     f (doc d == currentDoc d) {
      return currentH Attr;
    }
    return Collect ons.EMPTY_MAP;
  }
}

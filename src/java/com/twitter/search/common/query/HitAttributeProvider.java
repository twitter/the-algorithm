package com.tw ter.search.common.query;

 mport java.ut l.L st;
 mport java.ut l.Map;

/**
 * T   nterface for objects that can prov de h  attr butes for a docu nt.
 */
publ c  nterface H Attr buteProv der {
  /** Returns t  h  attr butes for t  g ven docu nt. */
  Map< nteger, L st<Str ng>> getH Attr but on( nt doc d);
}

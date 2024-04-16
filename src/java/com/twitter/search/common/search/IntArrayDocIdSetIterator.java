package com.tw ter.search.common.search;

 mport java.ut l.Arrays;

 mport org.apac .lucene.search.Doc dSet erator;

/**
 * Doc dSet erator  mple ntat on from a sorted l st of non-negat ve  ntegers.  f t  g ven l st of
 * doc  Ds  s not sorted or conta ns negat ve doc  Ds, t  results are undef ned.
 */
publ c class  ntArrayDoc dSet erator extends Doc dSet erator {
  pr vate f nal  nt[] doc ds;
  pr vate  nt doc d;
  pr vate  nt cursor;

  publ c  ntArrayDoc dSet erator( nt[]  ds) {
    doc ds =  ds;
    reset();
  }

  /** Used for test ng. */
  publ c vo d reset() {
    doc d = -1;
    cursor = -1;
  }

  @Overr de
  publ c  nt doc D() {
    return doc d;
  }

  @Overr de
  publ c  nt nextDoc() {
    return advance(doc d);
  }

  @Overr de
  publ c  nt advance( nt target) {
     f (doc d == NO_MORE_DOCS) {
      return doc d;
    }

     f (target < doc d) {
      return doc d;
    }

     f (cursor == doc ds.length - 1) {
      doc d = NO_MORE_DOCS;
      return doc d;
    }

     f (target == doc d) {
      doc d = doc ds[++cursor];
      return doc d;
    }

     nt to ndex = Math.m n(cursor + (target - doc d) + 1, doc ds.length);
     nt target ndex = Arrays.b narySearch(doc ds, cursor + 1, to ndex, target);
     f (target ndex < 0) {
      target ndex = -target ndex - 1;
    }

     f (target ndex == doc ds.length) {
      doc d = NO_MORE_DOCS;
    } else {
      cursor = target ndex;
      doc d = doc ds[cursor];
    }
    return doc d;
  }

  @Overr de
  publ c long cost() {
    return doc ds == null ? 0 : doc ds.length;
  }
}

package com.tw ter.search.common.query;

 mport java. o. OExcept on;

 mport org.apac .lucene.search.Doc dSet erator;

publ c class S ngleDocDoc dSet erator extends Doc dSet erator {

  // t  only doc d  n t  l st
  pr vate f nal  nt doc;

  pr vate  nt doc d = -1;

  publ c S ngleDocDoc dSet erator( nt doc) {
    t .doc = doc;
  }

  @Overr de
  publ c  nt doc D() {
    return doc d;
  }

  @Overr de
  publ c  nt nextDoc() throws  OExcept on {
     f (doc d == -1) {
      doc d = doc;
    } else {
      doc d = NO_MORE_DOCS;
    }
    return doc d;
  }

  @Overr de
  publ c  nt advance( nt target) throws  OExcept on {
     f (doc d == NO_MORE_DOCS) {
      return doc d;
    } else  f (doc < target) {
      doc d = NO_MORE_DOCS;
      return doc d;
    } else {
      doc d = doc;
    }
    return doc d;
  }

  @Overr de
  publ c long cost() {
    return 1;
  }

}

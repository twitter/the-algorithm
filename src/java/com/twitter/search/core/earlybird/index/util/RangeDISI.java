package com.tw ter.search.core.earlyb rd. ndex.ut l;

 mport java. o. OExcept on;

 mport org.apac .lucene. ndex.LeafReader;
 mport org.apac .lucene.search.Doc dSet erator;

 mport com.tw ter.search.core.earlyb rd. ndex.Doc DToT et DMapper;

publ c class RangeD S  extends Doc dSet erator {
  pr vate f nal  nt start;
  pr vate f nal  nt end;
  pr vate f nal AllDocs erator delegate;

  pr vate  nt currentDoc d = -1;

  publ c RangeD S (LeafReader reader,  nt start,  nt end) throws  OExcept on {
    t .delegate = new AllDocs erator(reader);
    t .start = start;
     f (end == Doc DToT et DMapper. D_NOT_FOUND) {
      t .end =  nteger.MAX_VALUE;
    } else {
      t .end = end;
    }
  }

  @Overr de
  publ c  nt doc D() {
    return currentDoc d;
  }

  @Overr de
  publ c  nt nextDoc() throws  OExcept on {
    return advance(currentDoc d + 1);
  }

  @Overr de
  publ c  nt advance( nt target) throws  OExcept on {
    currentDoc d = delegate.advance(Math.max(target, start));
     f (currentDoc d > end) {
      currentDoc d = NO_MORE_DOCS;
    }
    return currentDoc d;
  }

  @Overr de
  publ c long cost() {
    return delegate.cost();
  }
}

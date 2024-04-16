package com.tw ter.search.core.earlyb rd. ndex.ut l;

 mport java. o. OExcept on;

 mport org.apac .lucene. ndex.LeafReader;
 mport org.apac .lucene.search.Doc dSet erator;

/**
 * A doc  d set  erator that  erates over a f ltered set of  ds from f rst d  nclus ve to last d
 *  nclus ve.
 */
publ c class RangeF lterD S  extends Doc dSet erator {
  pr vate f nal RangeD S  delegate;

  publ c RangeF lterD S (LeafReader reader) throws  OExcept on {
    t (reader, 0, reader.maxDoc() - 1);
  }

  publ c RangeF lterD S (LeafReader reader,  nt smallestDoc D,  nt largestDoc D)
      throws  OExcept on {
    t .delegate = new RangeD S (reader, smallestDoc D, largestDoc D);
  }

  @Overr de
  publ c  nt doc D() {
    return delegate.doc D();
  }

  @Overr de
  publ c  nt nextDoc() throws  OExcept on {
    delegate.nextDoc();
    return nextVal dDoc();
  }

  @Overr de
  publ c  nt advance( nt target) throws  OExcept on {
    delegate.advance(target);
    return nextVal dDoc();
  }

  pr vate  nt nextVal dDoc() throws  OExcept on {
     nt doc = delegate.doc D();
    wh le (doc != NO_MORE_DOCS && !shouldReturnDoc()) {
      doc = delegate.nextDoc();
    }
    return doc;
  }

  @Overr de
  publ c long cost() {
    return delegate.cost();
  }

  // Overr de t   thod to add add  onal f lters. Should return true  f t  current doc  s OK.
  protected boolean shouldReturnDoc() throws  OExcept on {
    return true;
  }
}

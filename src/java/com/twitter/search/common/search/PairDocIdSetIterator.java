package com.tw ter.search.common.search;

 mport java. o. OExcept on;

 mport com.google.common.base.Precond  ons;

 mport org.apac .lucene.search.Doc dSet erator;
/**
 * D sjunct on over 2 Doc dSet erators. T  should be faster than a d sjunct on over N s nce t re
 * would be no need to adjust t   ap.
 */
publ c class Pa rDoc dSet erator extends Doc dSet erator {

  pr vate f nal Doc dSet erator d1;
  pr vate f nal Doc dSet erator d2;

  pr vate  nt doc = -1;

  /** Creates a new Pa rDoc dSet erator  nstance. */
  publ c Pa rDoc dSet erator(Doc dSet erator d1, Doc dSet erator d2) throws  OExcept on {
    Precond  ons.c ckNotNull(d1);
    Precond  ons.c ckNotNull(d2);
    t .d1 = d1;
    t .d2 = d2;
    // pos  on t   erators
    t .d1.nextDoc();
    t .d2.nextDoc();
  }

  @Overr de
  publ c  nt doc D() {
    return doc;
  }

  @Overr de
  publ c  nt nextDoc() throws  OExcept on {
     nt doc1 = d1.doc D();
     nt doc2 = d2.doc D();
    Doc dSet erator  er = null;
     f (doc1 < doc2) {
      doc = doc1;
      //d1.nextDoc();
       er = d1;
    } else  f (doc1 > doc2) {
      doc = doc2;
      //d2.nextDoc();
       er = d2;
    } else {
      doc = doc1;
      //d1.nextDoc();
      //d2.nextDoc();
    }

     f (doc != NO_MORE_DOCS) {
       f ( er != null) {
         er.nextDoc();
      } else {
        d1.nextDoc();
        d2.nextDoc();
      }
    }
    return doc;
  }

  @Overr de
  publ c  nt advance( nt target) throws  OExcept on {
     f (d1.doc D() < target) {
      d1.advance(target);
    }
     f (d2.doc D() < target) {
      d2.advance(target);
    }
    return (doc != NO_MORE_DOCS) ? nextDoc() : doc;
  }

  @Overr de
  publ c long cost() {
    // very coarse est mate
    return d1.cost() + d2.cost();
  }

}

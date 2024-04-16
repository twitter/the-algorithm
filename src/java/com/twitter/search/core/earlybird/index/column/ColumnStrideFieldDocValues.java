package com.tw ter.search.core.earlyb rd. ndex.column;

 mport java. o. OExcept on;

 mport com.google.common.base.Precond  ons;

 mport org.apac .lucene. ndex.LeafReader;
 mport org.apac .lucene. ndex.Nu r cDocValues;

 mport com.tw ter.search.core.earlyb rd. ndex.ut l.AllDocs erator;

/**
 * A Nu r cDocValues  mple ntat on that uses an AllDocs erator to  erate through all docs, and
 * gets  s values from a ColumnStr deF eld ndex  nstance.
 */
publ c class ColumnStr deF eldDocValues extends Nu r cDocValues {
  pr vate f nal ColumnStr deF eld ndex csf;
  pr vate f nal AllDocs erator  erator;

  publ c ColumnStr deF eldDocValues(ColumnStr deF eld ndex csf, LeafReader reader)
      throws  OExcept on {
    t .csf = Precond  ons.c ckNotNull(csf);
    t . erator = new AllDocs erator(Precond  ons.c ckNotNull(reader));
  }

  @Overr de
  publ c long longValue() {
    return csf.get(doc D());
  }

  @Overr de
  publ c  nt doc D() {
    return  erator.doc D();
  }

  @Overr de
  publ c  nt nextDoc() throws  OExcept on {
    return  erator.nextDoc();
  }

  @Overr de
  publ c  nt advance( nt target) throws  OExcept on {
    return  erator.advance(target);
  }

  @Overr de
  publ c boolean advanceExact( nt target) throws  OExcept on {
    // T  javadocs for advance() and advanceExact() are  ncons stent. advance() allows t  target
    // to be smaller than t  current doc  D, and requ res t   erator to advance t  current doc
    //  D past t  target, and past t  current doc  D. So essent ally, advance(target) returns
    // max(target, currentDoc d + 1). At t  sa  t  , advanceExact()  s undef ned  f t  target  s
    // smaller than t  current do  D (or  f  's an  nval d doc  D), and always returns t  target.
    // So essent ally, advanceExact(target) should always set t  current doc  D to t  g ven target
    // and  f target == currentDoc d, t n currentDoc d should not be advanced. T   s why   have
    // t se extra c cks  re  nstead of mov ng t m to advance().
    Precond  ons.c ckState(
        target >= doc D(),
        "ColumnStr deF eldDocValues.advance() for f eld %s called w h target %s, "
        + "but t  current doc  D  s %s.",
        csf.getNa (),
        target,
        doc D());
     f (target == doc D()) {
      return true;
    }

    //   don't need to c ck  f   have a value for 'target', because a ColumnStr deF eld ndex
    //  nstance has a value for every doc  D (though that value m ght be 0).
    return advance(target) == target;
  }

  @Overr de
  publ c long cost() {
    return  erator.cost();
  }
}

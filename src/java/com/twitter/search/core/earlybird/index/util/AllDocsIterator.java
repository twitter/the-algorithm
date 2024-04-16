package com.tw ter.search.core.earlyb rd. ndex.ut l;

 mport java. o. OExcept on;

 mport org.apac .lucene. ndex.LeafReader;
 mport org.apac .lucene. ndex.Terms;
 mport org.apac .lucene. ndex.TermsEnum;
 mport org.apac .lucene.search.Doc dSet erator;
 mport org.apac .lucene.ut l.BytesRef;

 mport com.tw ter.search.common.sc ma.earlyb rd.Earlyb rdF eldConstants;
 mport com.tw ter.search.core.earlyb rd. ndex.Earlyb rdRealt   ndexSeg ntAtom cReader;

/**
 * Used to  erate through all of t  docu nts  n an Earlyb rd seg nt. T   s necessary so that
 *   can ensure all of t  docu nts   are read ng have been publ s d to t  readers.  f   used
 * t  doc  D mapper to  erate through docu nts,   would return docu nts that have been only
 * part ally added to t   ndex, and could return bogus search results (SEARCH-27711).
 */
publ c class AllDocs erator extends Doc dSet erator {
  publ c stat c f nal Str ng ALL_DOCS_TERM = "__all_docs";

  pr vate f nal Doc dSet erator delegate;

  publ c AllDocs erator(LeafReader reader) throws  OExcept on {
    delegate = bu ldD S (reader);
  }

  pr vate stat c Doc dSet erator bu ldD S (LeafReader reader) throws  OExcept on {
     f (! sRealt  Unopt m zedSeg nt(reader)) {
      return all(reader.maxDoc());
    }

    Terms terms =
        reader.terms(Earlyb rdF eldConstants.Earlyb rdF eldConstant. NTERNAL_F ELD.getF eldNa ());
     f (terms == null) {
      return all(reader.maxDoc());
    }

    TermsEnum termsEnum = terms. erator();
    boolean hasTerm = termsEnum.seekExact(new BytesRef(ALL_DOCS_TERM));
     f (hasTerm) {
      return termsEnum.post ngs(null);
    }

    return empty();
  }

  @Overr de
  publ c  nt doc D() {
    return delegate.doc D();
  }

  @Overr de
  publ c  nt nextDoc() throws  OExcept on {
    return delegate.nextDoc();
  }

  @Overr de
  publ c  nt advance( nt target) throws  OExcept on {
    return delegate.advance(target);
  }

  @Overr de
  publ c long cost() {
    return delegate.cost();
  }

  /**
   * Returns w t r t   s a realt   seg nt  n t  realt    ndex that  s st ll unopt m zed and
   * mutable.
   */
  pr vate stat c boolean  sRealt  Unopt m zedSeg nt(LeafReader reader) {
     f (reader  nstanceof Earlyb rdRealt   ndexSeg ntAtom cReader) {
      Earlyb rdRealt   ndexSeg ntAtom cReader realt  Reader =
          (Earlyb rdRealt   ndexSeg ntAtom cReader) reader;
      return !realt  Reader.getSeg ntData(). sOpt m zed();
    }

    return false;
  }
}

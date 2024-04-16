package com.tw ter.search.core.earlyb rd. ndex. nverted;

 mport java. o. OExcept on;

 mport org.apac .lucene. ndex.Terms;
 mport org.apac .lucene. ndex.TermsEnum;
 mport org.apac .lucene.ut l.BytesRef;

 mport com.tw ter.search.common.sc ma.base.Earlyb rdF eldType;
 mport com.tw ter.search.common.ut l. o.flushable.Flushable;
 mport com.tw ter.search.core.earlyb rd.facets.FacetLabelProv der;
 mport com.tw ter.search.core.earlyb rd. ndex.Earlyb rd ndexSeg ntAtom cReader;

/**
 *  nverted  ndex for a s ngle f eld.
 *
 * Example: T  f eld  s "hashtags", t   ndex conta ns a mapp ng from all t  hashtags
 * that  've seen to a l st of post ngs.
 */
publ c abstract class  nverted ndex  mple nts FacetLabelProv der, Flushable {
  protected f nal Earlyb rdF eldType f eldType;

  publ c  nverted ndex(Earlyb rdF eldType f eldType) {
    t .f eldType = f eldType;
  }

  publ c Earlyb rdF eldType getF eldType() {
    return f eldType;
  }

  /**
   * Get t   nternal doc  d of t  oldest doc that  ncludes term.
   * @param term  t  term to look for.
   * @return  T   nternal doc d, or TERM_NOT_FOUND.
   */
  publ c f nal  nt getLargestDoc DForTerm(BytesRef term) throws  OExcept on {
    f nal  nt term D = lookupTerm(term);
    return getLargestDoc DForTerm(term D);
  }

  /**
   * Get t  docu nt frequency for t  term.
   * @param term  t  term to look for.
   * @return  T  docu nt frequency of t  term  n t   ndex.
   */
  publ c f nal  nt getDF(BytesRef term) throws  OExcept on {
    f nal  nt term D = lookupTerm(term);
     f (term D == Earlyb rd ndexSeg ntAtom cReader.TERM_NOT_FOUND) {
      return 0;
    }
    return getDF(term D);
  }

  publ c boolean hasMaxPubl s dPo nter() {
    return false;
  }

  publ c  nt getMaxPubl s dPo nter() {
    return -1;
  }

  /**
   * Create t  Lucene mag c Terms accessor.
   * @param maxPubl s dPo nter used by t  sk p l st to enable atom c docu nt updates.
   * @return  a new Terms object.
   */
  publ c abstract Terms createTerms( nt maxPubl s dPo nter);

  /**
   * Create t  Lucene mag c TermsEnum accessor.
   * @param maxPubl s dPo nter used by t  sk p l st to enable atom c docu nt updates.
   * @return  a new TermsEnum object.
   */
  publ c abstract TermsEnum createTermsEnum( nt maxPubl s dPo nter);

  /**
   * Returns t  number of d st nct terms  n t   nverted  ndex.
   * For example,  f t   ndexed docu nts are:
   *   "  love chocolate and   love cakes"
   *   "  love cook es"
   *
   * t n t   thod w ll return 6, because t re are 6 d st nct terms:
   *    , love, chocolate, and, cakes, cook es
   */
  publ c abstract  nt getNumTerms();

  /**
   * Returns t  number of d st nct docu nts  n t   ndex.
   */
  publ c abstract  nt getNumDocs();

  /**
   * Returns t  total number of post ngs  n t   nverted  ndex.
   *
   * For example,  f t   ndexed docu nts are:
   *   "  love chocolate and   love cakes"
   *   "  love cook es"
   *
   * t n t   thod w ll return 10, because t re's a total of 10 words  n t se 2 docu nts.
   */
  publ c abstract  nt getSumTotalTermFreq();

  /**
   * Returns t  sum of t  number of docu nts for each term  n t   ndex.
   *
   * For example,  f t   ndexed docu nts are:
   *   "  love chocolate and   love cakes"
   *   "  love cook es"
   *
   * t n t   thod w ll return 8, because t re are:
   *   2 docu nts for term " " (  doesn't matter that t  f rst docu nt has t  term " " tw ce)
   *   2 docu nts for term "love" (sa  reason)
   *   1 docu nt for terms "chocolate", "and", "cakes", "cook es"
   */
  publ c abstract  nt getSumTermDocFreq();

  /**
   * Lookup a term.
   * @param term  t  term to lookup.
   * @return  t  term  D for t  term.
   */
  publ c abstract  nt lookupTerm(BytesRef term) throws  OExcept on;

  /**
   * Get t  text for a g ven term D.
   * @param term D  t  term  d
   * @param text  a BytesRef that w ll be mod f ed to conta n t  text of t  term d.
   */
  publ c abstract vo d getTerm( nt term D, BytesRef text);

  /**
   * Get t   nternal doc  d of t  oldest doc that  ncludes t  term.
   * @param term D  T  term D of t  term.
   * @return  T   nternal doc d, or TERM_NOT_FOUND.
   */
  publ c abstract  nt getLargestDoc DForTerm( nt term D) throws  OExcept on;

  /**
   * Get t  docu nt frequency for a g ven term D
   * @param term D  t  term  d
   * @return  t  docu nt frequency of t  term  n t   ndex.
   */
  publ c abstract  nt getDF( nt term D);
}

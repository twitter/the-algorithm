package com.tw ter.search.core.earlyb rd. ndex. nverted;

 mport com.google.common.collect. mmutableL st;

 mport org.apac .lucene.ut l.BytesRef;

 mport com.tw ter.search.core.earlyb rd. ndex.Earlyb rd ndexSeg ntAtom cReader;

/**
 * A term d ct onary that's backed by mult ple underly ng seg nts/ ndexes. For a g ven term, w ll
 * be able to return t  term d for each of t  underly ng  ndexes.
 */
publ c  nterface Mult Seg ntTermD ct onary {

  /**
   * Lookup a term  n t  mult  seg nt term d ct onary, and return t  term  ds for that term on
   * all of t  managed seg nts.
   *
   * @return An array conta n ng a term d for each seg nt that t  term d ct onary  s backed by.
   * T  order of seg nts w ll match t  order returned by {@l nk #getSeg nt ndexes()}.
   *
   * For each seg nt, t  term  d w ll be returned, or
   * {@l nk Earlyb rd ndexSeg ntAtom cReader#TERM_NOT_FOUND}  f that seg nt does not have t 
   * g ven term.
   */
   nt[] lookupTerm ds(BytesRef term);

  /**
   * A conven ence  thod for c ck ng w t r a spec f c  ndex/seg nt  s backed by t  term
   * d ct onary. Return ng true  re  s equ valent to return ng:
   * <pre>
   * getSeg nt ndexes().conta ns( nverted ndex);
   * </pre>
   */
  default boolean supportSeg nt ndex( nverted ndex  nverted ndex) {
    return getSeg nt ndexes().conta ns( nverted ndex);
  }

  /**
   * T  l st of  ndexes that t  term d ct onary  s backed by. T  order of  ndexes  re w ll
   * be cons stent w h t  order of term ds returned by {@l nk #lookupTerm ds(BytesRef)}.
   */
   mmutableL st<? extends  nverted ndex> getSeg nt ndexes();

  /**
   * Returns t  number of terms  n t  term d ct onary.
   *
   *  f t  term "foo" appears  n seg nt A and  n seg nt B,   w ll be counted once. To get t 
   * total number of terms across all managed seg nts, see {@l nk #getNumTermEntr es()}.
   */
   nt getNumTerms();

  /**
   * Returns t  total number of terms  n t  term d ct onary across all managed seg nts.
   *
   *  f t  term "foo" appears  n seg nt A and  n seg nt B,   w ll have 2 entr es  n t  term
   * d ct onary.
   */
   nt getNumTermEntr es();
}

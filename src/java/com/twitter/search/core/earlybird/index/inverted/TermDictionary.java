package com.tw ter.search.core.earlyb rd. ndex. nverted;

 mport java. o. OExcept on;

 mport org.apac .lucene. ndex.TermsEnum;
 mport org.apac .lucene.ut l.BytesRef;

 mport com.tw ter.search.common.ut l. o.flushable.Flushable;
 mport com.tw ter.search.core.earlyb rd. ndex.Earlyb rd ndexSeg ntAtom cReader;

/**
 * A two-way mapp ng bet en terms and t  r  nterned value (term D).
 *
 *  mple ntat on of t   nterface must guarantee that term Ds are dense, start ng at 0;
 * so t y are good to be used as  nd ces  n arrays.
 */
publ c  nterface TermD ct onary extends Flushable {
   nt TERM_NOT_FOUND = Earlyb rd ndexSeg ntAtom cReader.TERM_NOT_FOUND;

  /**
   * Returns t  number of terms  n t  d ct onary.
   */
   nt getNumTerms();

  /**
   * Create a TermsEnum object over t  TermD ct onary for a g ven  ndex.
   * @param  ndex
   */
  TermsEnum createTermsEnum(Opt m zed mory ndex  ndex);

  /**
   * Lookup a term  n t  d ct onary.
   * @param term  t  term to lookup.
   * @return  t  term  d for t  term, or TERM_NOT_FOUND
   * @throws  OExcept on
   */
   nt lookupTerm(BytesRef term) throws  OExcept on;

  /**
   * Get t  term for g ven  d and poss bly  s payload.
   * @param term D  t  term that   want to get.
   * @param text  MUST be non-null.   w ll be f lled w h t  term.
   * @param termPayload   f non-null,   w ll be f lled w h t  payload  f t  term has any.
   * @return  Returns true,  ff t  term has a term payload.
   */
  boolean getTerm( nt term D, BytesRef text, BytesRef termPayload);
}

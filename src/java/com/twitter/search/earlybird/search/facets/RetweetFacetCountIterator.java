package com.tw ter.search.earlyb rd.search.facets;

 mport java. o. OExcept on;

 mport org.apac .lucene. ndex.Nu r cDocValues;

 mport com.tw ter.search.common.sc ma.base.Sc ma;
 mport com.tw ter.search.common.sc ma.earlyb rd.Earlyb rdF eldConstants.Earlyb rdF eldConstant;
 mport com.tw ter.search.core.earlyb rd.facets.CSFFacetCount erator;
 mport com.tw ter.search.core.earlyb rd. ndex.Earlyb rd ndexSeg ntAtom cReader;

/**
 * And  erator for count ng ret ets. Reads from shared_status_ d CSF but doesn't count
 * repl es.
 */
publ c class Ret etFacetCount erator extends CSFFacetCount erator {
  pr vate f nal Nu r cDocValues featureReader sRet etFlag;

  publ c Ret etFacetCount erator(
      Earlyb rd ndexSeg ntAtom cReader reader,
      Sc ma.F eld nfo facetF eld nfo) throws  OExcept on {
    super(reader, facetF eld nfo);
    featureReader sRet etFlag =
        reader.getNu r cDocValues(Earlyb rdF eldConstant. S_RETWEET_FLAG.getF eldNa ());
  }

  @Overr de
  protected boolean shouldCollect( nt  nternalDoc D, long term D) throws  OExcept on {
    // term D == 0  ans that   d dn't set shared_status_csf, so don't collect
    // (t et  Ds are all pos  ve)
    // Also only collect  f t  doc  s a ret et, not a reply
    return term D > 0
        && featureReader sRet etFlag.advanceExact( nternalDoc D)
        && (featureReader sRet etFlag.longValue() != 0);
  }
}

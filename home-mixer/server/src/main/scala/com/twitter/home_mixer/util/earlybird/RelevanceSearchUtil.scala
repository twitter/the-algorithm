package com.tw ter.ho _m xer.ut l.earlyb rd

 mport com.tw ter.search.common.sc ma.earlyb rd.Earlyb rdF eldConstants.Earlyb rdF eldConstant
 mport com.tw ter.search.earlyb rd.{thr ftscala => eb}

object RelevanceSearchUt l {

  val  nt ons: Str ng = Earlyb rdF eldConstant.MENT ONS_FACET
  val Hashtags: Str ng = Earlyb rdF eldConstant.HASHTAGS_FACET
  val FacetsToFetch: Seq[Str ng] = Seq( nt ons, Hashtags)

  val  tadataOpt ons: eb.Thr ftSearchResult tadataOpt ons = {
    eb.Thr ftSearchResult tadataOpt ons(
      getT etUrls = true,
      getResultLocat on = false,
      getLuceneScore = false,
      get nReplyToStatus d = true,
      getReferencedT etAuthor d = true,
      get d aB s = true,
      getAllFeatures = true,
      returnSearchResultFeatures = true,
      // Set getExclus veConversat onAuthor d  n order to retr eve Exclus ve / SuperFollow t ets.
      getExclus veConversat onAuthor d = true
    )
  }

  val RelevanceOpt ons: eb.Thr ftSearchRelevanceOpt ons = {
    eb.Thr ftSearchRelevanceOpt ons(
      prox m yScor ng = true,
      maxConsecut veSa User = So (2),
      rank ngParams = None,
      maxH sToProcess = So (500),
      maxUserBlendCount = So (3),
      prox m yPhrase  ght = 9.0,
      returnAllResults = So (true)
    )
  }
}

package com.tw ter.ho _m xer.ut l.earlyb rd

 mport com.tw ter.convers ons.Durat onOps._
 mport com.tw ter.search.common.query.thr ftjava.{thr ftscala => scq}
 mport com.tw ter.search.common.rank ng.{thr ftscala => scr}
 mport com.tw ter.search.earlyb rd.{thr ftscala => eb}
 mport com.tw ter.t  l nes.cl ents.relevance_search.SearchCl ent.T etFeatures
 mport com.tw ter.t  l nes.cl ents.relevance_search.SearchCl ent.T etTypes
 mport com.tw ter.t  l nes.cl ents.relevance_search.SearchQueryBu lder
 mport com.tw ter.t  l nes.cl ents.relevance_search.SearchQueryBu lder.QueryW hNa dD sjunct ons
 mport com.tw ter.t  l nes.earlyb rd.common.opt ons.Earlyb rdScor ngModelConf g
 mport com.tw ter.t  l nes.earlyb rd.common.ut ls.SearchOperator
 mport com.tw ter.ut l.Durat on

object Earlyb rdRequestUt l {

  val DefaultMaxH sToProcess = 1000
  val DefaultSearchProcess ngT  out: Durat on = 200.m ll seconds
  val DefaultHydrat onMaxNumResultsPerShard = 1000
  val DefaultQueryMaxNumResultsPerShard = 300
  val DefaultHydrat onCollectorParams = mkCollectorParams(DefaultHydrat onMaxNumResultsPerShard)

  pr vate val queryBu lder = new SearchQueryBu lder

  object Earlyb rdScor ngModels {
    val Un f edEngage ntProd: Seq[Earlyb rdScor ngModelConf g] = Seq(
      Earlyb rdScor ngModelConf g("t  l nes_un f ed_engage nt_prod.sc ma_based", 1.0)
    )

    val Un f edEngage ntRect et: Seq[Earlyb rdScor ngModelConf g] = Seq(
      Earlyb rdScor ngModelConf g("t  l nes_un f ed_engage nt_rect et.sc ma_based", 1.0)
    )
  }

  pr vate[earlyb rd] def mkCollectorParams(numResultsToReturn:  nt): scq.CollectorParams = {
    scq.CollectorParams(
      // numResultsToReturn def nes how many results each EB shard w ll return to search root
      numResultsToReturn = numResultsToReturn,
      // term nat onParams.maxH sToProcess  s used for early term nat ng per shard results fetch ng.
      term nat onParams = So (
        scq.CollectorTerm nat onParams(
          maxH sToProcess = So (DefaultMaxH sToProcess),
          t  outMs = DefaultSearchProcess ngT  out. nM ll seconds.to nt
        ))
    )
  }

  pr vate def getRank ngParams(
    authorScoreMap: Opt on[Map[Long, Double]],
    tensorflowModel: Opt on[Str ng],
    ebModels: Seq[Earlyb rdScor ngModelConf g]
  ): Opt on[scr.Thr ftRank ngParams] = {
     f (tensorflowModel.nonEmpty) {
      So (
        scr.Thr ftRank ngParams(
          `type` = So (scr.Thr ftScor ngFunct onType.TensorflowBased),
          selectedTensorflowModel = tensorflowModel,
          m nScore = -1.0e100,
          applyBoosts = false,
          authorSpec f cScoreAdjust nts = authorScoreMap
        )
      )
    } else  f (ebModels.nonEmpty) {
      So (
        scr.Thr ftRank ngParams(
          `type` = So (scr.Thr ftScor ngFunct onType.ModelBased),
          selectedModels = So (ebModels.map(m => m.na  -> m.  ght).toMap),
          applyBoosts = false,
          m nScore = -1.0e100,
          authorSpec f cScoreAdjust nts = authorScoreMap
        )
      )
    } else None
  }

  def getT etsRequest(
    user d: Opt on[Long],
    cl ent d: Opt on[Str ng],
    sk pVeryRecentT ets: Boolean,
    follo dUser ds: Set[Long],
    ret etsMutedUser ds: Set[Long],
    beforeT et dExclus ve: Opt on[Long],
    afterT et dExclus ve: Opt on[Long],
    excludedT et ds: Opt on[Set[Long]] = None,
    maxCount:  nt,
    t etTypes: T etTypes.ValueSet,
    authorScoreMap: Opt on[Map[Long, Double]] = None,
    tensorflowModel: Opt on[Str ng] = None,
    ebModels: Seq[Earlyb rdScor ngModelConf g] = Seq.empty,
    queryMaxNumResultsPerShard:  nt = DefaultQueryMaxNumResultsPerShard
  ): eb.Earlyb rdRequest = {

    val QueryW hNa dD sjunct ons(query, na dD sjunct onMap) = queryBu lder.create(
      follo dUser ds,
      ret etsMutedUser ds,
      beforeT et dExclus ve,
      afterT et dExclus ve,
      semant cCore ds = None,
      languages = None,
      t etTypes = t etTypes,
      searchOperator = SearchOperator.Exclude,
      t etFeatures = T etFeatures.All,
      excludedT et ds = excludedT et ds.getOrElse(Set.empty),
      enableExcludeS ceT et dsQuery = false
    )
    val ebRank ngParams = getRank ngParams(authorScoreMap, tensorflowModel, ebModels)
    val relOpt ons = RelevanceSearchUt l.RelevanceOpt ons.copy(
      rank ngParams = ebRank ngParams
    )

    val follo dUser dsSeq = follo dUser ds.toSeq
    val na dD sjunct onMapOpt =
       f (na dD sjunct onMap. sEmpty) None
      else So (na dD sjunct onMap.mapValues(_.toSeq))

    val thr ftQuery = eb.Thr ftSearchQuery(
      ser al zedQuery = So (query.ser al ze),
      fromUser DF lter64 = So (follo dUser dsSeq),
      numResults = maxCount,
      collectConversat on d = true,
      rank ngMode = eb.Thr ftSearchRank ngMode.Relevance,
      relevanceOpt ons = So (relOpt ons),
      collectorParams = So (mkCollectorParams(queryMaxNumResultsPerShard)),
      facetF eldNa s = So (RelevanceSearchUt l.FacetsToFetch),
      result tadataOpt ons = So (RelevanceSearchUt l. tadataOpt ons),
      searc r d = user d,
      searchStatus ds = None,
      na dD sjunct onMap = na dD sjunct onMapOpt
    )

    eb.Earlyb rdRequest(
      searchQuery = thr ftQuery,
      cl ent d = cl ent d,
      getOlderResults = So (false),
      follo dUser ds = So (follo dUser dsSeq),
      getProtectedT etsOnly = So (false),
      t  outMs = DefaultSearchProcess ngT  out. nM ll seconds.to nt,
      sk pVeryRecentT ets = sk pVeryRecentT ets,
      numResultsToReturnAtRoot = So (maxCount)
    )
  }

  def getT etsFeaturesRequest(
    user d: Opt on[Long],
    t et ds: Opt on[Seq[Long]],
    cl ent d: Opt on[Str ng],
    getOnlyProtectedT ets: Boolean = false,
    authorScoreMap: Opt on[Map[Long, Double]] = None,
    tensorflowModel: Opt on[Str ng] = None,
    ebModels: Seq[Earlyb rdScor ngModelConf g] = Seq.empty
  ): eb.Earlyb rdRequest = {

    val cand dateS ze = t et ds.getOrElse(Seq.empty).s ze
    val ebRank ngParams = getRank ngParams(authorScoreMap, tensorflowModel, ebModels)
    val relOpt ons = RelevanceSearchUt l.RelevanceOpt ons.copy(
      rank ngParams = ebRank ngParams
    )
    val thr ftQuery = eb.Thr ftSearchQuery(
      numResults = cand dateS ze,
      collectConversat on d = true,
      rank ngMode = eb.Thr ftSearchRank ngMode.Relevance,
      relevanceOpt ons = So (relOpt ons),
      collectorParams = So (DefaultHydrat onCollectorParams),
      facetF eldNa s = So (RelevanceSearchUt l.FacetsToFetch),
      result tadataOpt ons = So (RelevanceSearchUt l. tadataOpt ons),
      searc r d = user d,
      searchStatus ds = t et ds.map(_.toSet),
    )

    eb.Earlyb rdRequest(
      searchQuery = thr ftQuery,
      cl ent d = cl ent d,
      getOlderResults = So (false),
      getProtectedT etsOnly = So (getOnlyProtectedT ets),
      t  outMs = DefaultSearchProcess ngT  out. nM ll seconds.to nt,
      sk pVeryRecentT ets = true,
      // T  param dec des # of t ets to return from search superRoot and realt  /protected/Arch ve roots.
      //   takes h g r precedence than Thr ftSearchQuery.numResults
      numResultsToReturnAtRoot = So (cand dateS ze)
    )
  }
}

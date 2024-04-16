package com.tw ter.ho _m xer.product.scored_t ets.query_transfor r

 mport com.tw ter.ho _m xer.model.Ho Features.RealGraph nNetworkScoresFeature
 mport com.tw ter.ho _m xer.model.request.HasDev ceContext
 mport com.tw ter.ho _m xer.product.scored_t ets.query_transfor r.T  l neRankerQueryTransfor r._
 mport com.tw ter.ho _m xer.ut l.Cac dScoredT ets lper
 mport com.tw ter.ho _m xer.ut l.earlyb rd.Earlyb rdRequestUt l
 mport com.tw ter.product_m xer.core.model.common. dent f er.Cand dateP pel ne dent f er
 mport com.tw ter.product_m xer.core.p pel ne.P pel neQuery
 mport com.tw ter.product_m xer.core.qual y_factor.HasQual yFactorStatus
 mport com.tw ter.t  l neranker.{model => tlr}
 mport com.tw ter.t  l nes.common.model.T etK ndOpt on
 mport com.tw ter.t  l nes.earlyb rd.common.opt ons.Earlyb rdOpt ons
 mport com.tw ter.t  l nes.earlyb rd.common.opt ons.Earlyb rdScor ngModelConf g
 mport com.tw ter.t  l nes.earlyb rd.common.ut ls.SearchOperator
 mport com.tw ter.t  l nes.model.User d
 mport com.tw ter.t  l nes.model.cand date.Cand dateT etS ce d
 mport com.tw ter.t  l nes.ut l.SnowflakeSort ndex lper
 mport com.tw ter.ut l.Durat on
 mport com.tw ter.ut l.T  

object T  l neRankerQueryTransfor r {

  /**
   * Spec f es t  max mum number of excluded t et  ds to  nclude  n t  search  ndex query.
   * Earlyb rd's na d mult  term d sjunct on map feature supports up to 1500 t et  ds.
   */
  pr vate val Earlyb rdMaxExcludedT ets = 1500

  /**
   * Max mum number of query h s each earlyb rd shard  s allo d to accumulate before
   * early-term nat ng t  query and reduc ng t  h s to MaxNumEarlyb rdResults.
   */
  pr vate val Earlyb rdMaxH s = 1000

  /**
   * Max mum number of results TLR should retr eve from each earlyb rd shard.
   */
  pr vate val Earlyb rdMaxResults = 300
}

tra  T  l neRankerQueryTransfor r[
  Query <: P pel neQuery w h HasQual yFactorStatus w h HasDev ceContext] {
  def maxT etsToFetch:  nt
  def opt ons: T etK ndOpt on.ValueSet = T etK ndOpt on.Default
  def cand dateT etS ce d: Cand dateT etS ce d.Value
  def utegL kedByT etsOpt ons(query: Query): Opt on[tlr.UtegL kedByT etsOpt ons] = None
  def seedAuthor ds(query: Query): Opt on[Seq[Long]] = None
  def cand dateP pel ne dent f er: Cand dateP pel ne dent f er
  def earlyb rdModels: Seq[Earlyb rdScor ngModelConf g] =
    Earlyb rdRequestUt l.Earlyb rdScor ngModels.Un f edEngage ntProd
  def getTensorflowModel(query: Query): Opt on[Str ng] = None

  def bu ldT  l neRankerQuery(query: Query, s nceDurat on: Durat on): tlr.RecapQuery = {
    val s nceT  : T   = s nceDurat on.ago
    val unt lT  : T   = T  .now

    val fromT et dExclus ve = SnowflakeSort ndex lper.t  stampToFake d(s nceT  )
    val toT et dExclus ve = SnowflakeSort ndex lper.t  stampToFake d(unt lT  )
    val range = tlr.T et dRange(So (fromT et dExclus ve), So (toT et dExclus ve))

    val excludedT et ds = query.features.map { featureMap =>
      Cac dScoredT ets lper.t et mpress onsAndCac dScoredT ets nRange(
        featureMap,
        cand dateP pel ne dent f er,
        Earlyb rdMaxExcludedT ets,
        s nceT  ,
        unt lT  )
    }

    val maxCount =
      (query.getQual yFactorCurrentValue(cand dateP pel ne dent f er) * maxT etsToFetch).to nt

    val authorScoreMap = query.features
      .map(_.getOrElse(RealGraph nNetworkScoresFeature, Map.empty[User d, Double]))
      .getOrElse(Map.empty)

    val dev ceContext =
      query.dev ceContext.map(_.toT  l neServ ceDev ceContext(query.cl entContext))

    val tensorflowModel = getTensorflowModel(query)

    val earlyB rdOpt ons = Earlyb rdOpt ons(
      maxNumH sPerShard = Earlyb rdMaxH s,
      maxNumResultsPerShard = Earlyb rdMaxResults,
      models = earlyb rdModels,
      authorScoreMap = authorScoreMap,
      sk pVeryRecentT ets = true,
      tensorflowModel = tensorflowModel
    )

    tlr.RecapQuery(
      user d = query.getRequ redUser d,
      maxCount = So (maxCount),
      range = So (range),
      opt ons = opt ons,
      searchOperator = SearchOperator.Exclude,
      earlyb rdOpt ons = So (earlyB rdOpt ons),
      dev ceContext = dev ceContext,
      author ds = seedAuthor ds(query),
      excludedT et ds = excludedT et ds,
      utegL kedByT etsOpt ons = utegL kedByT etsOpt ons(query),
      searchCl entSub d = None,
      cand dateT etS ce d = So (cand dateT etS ce d),
      hydratesContentFeatures = So (false)
    )
  }
}

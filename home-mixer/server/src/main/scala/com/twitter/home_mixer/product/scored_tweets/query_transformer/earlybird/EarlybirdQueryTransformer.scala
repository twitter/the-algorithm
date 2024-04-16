package com.tw ter.ho _m xer.product.scored_t ets.query_transfor r.earlyb rd

 mport com.tw ter.ho _m xer.model.Ho Features.RealGraph nNetworkScoresFeature
 mport com.tw ter.ho _m xer.model.request.HasDev ceContext
 mport com.tw ter.ho _m xer.ut l.Cac dScoredT ets lper
 mport com.tw ter.ho _m xer.ut l.earlyb rd.Earlyb rdRequestUt l
 mport com.tw ter.product_m xer.core.model.common. dent f er.Cand dateP pel ne dent f er
 mport com.tw ter.product_m xer.core.p pel ne.P pel neQuery
 mport com.tw ter.product_m xer.core.qual y_factor.HasQual yFactorStatus
 mport com.tw ter.search.earlyb rd.{thr ftscala => eb}
 mport com.tw ter.t  l nes.cl ents.relevance_search.SearchCl ent.T etTypes
 mport com.tw ter.t  l nes.common.model.T etK ndOpt on
 mport com.tw ter.t  l nes.ut l.SnowflakeSort ndex lper
 mport com.tw ter.ut l.Durat on
 mport com.tw ter.ut l.T  

tra  Earlyb rdQueryTransfor r[
  Query <: P pel neQuery w h HasQual yFactorStatus w h HasDev ceContext] {

  def cand dateP pel ne dent f er: Cand dateP pel ne dent f er
  def cl ent d: Opt on[Str ng] = None
  def maxT etsToFetch:  nt = 100
  def t etK ndOpt ons: T etK ndOpt on.ValueSet
  def tensorflowModel: Opt on[Str ng] = None

  pr vate val Earlyb rdMaxExcludedT ets = 1500

  def bu ldEarlyb rdQuery(
    query: Query,
    s nceDurat on: Durat on,
    follo dUser ds: Set[Long] = Set.empty
  ): eb.Earlyb rdRequest = {
    val s nceT  : T   = s nceDurat on.ago
    val unt lT  : T   = T  .now

    val fromT et dExclus ve = SnowflakeSort ndex lper.t  stampToFake d(s nceT  )
    val toT et dExclus ve = SnowflakeSort ndex lper.t  stampToFake d(unt lT  )

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
      .map(_.getOrElse(RealGraph nNetworkScoresFeature, Map.empty[Long, Double]))
      .getOrElse(Map.empty)

    Earlyb rdRequestUt l.getT etsRequest(
      user d = So (query.getRequ redUser d),
      cl ent d = cl ent d,
      sk pVeryRecentT ets = true,
      follo dUser ds = follo dUser ds,
      ret etsMutedUser ds = Set.empty,
      beforeT et dExclus ve = So (toT et dExclus ve),
      afterT et dExclus ve = So (fromT et dExclus ve),
      excludedT et ds = excludedT et ds.map(_.toSet),
      maxCount = maxCount,
      t etTypes = T etTypes.fromT etK ndOpt on(t etK ndOpt ons),
      authorScoreMap = So (authorScoreMap),
      tensorflowModel = tensorflowModel
    )
  }
}

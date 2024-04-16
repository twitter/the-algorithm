package com.tw ter.follow_recom ndat ons.common.feature_hydrat on.s ces

 mport com.google. nject. nject
 mport com.google. nject.Prov des
 mport com.google. nject.S ngleton
 mport com.tw ter.f nagle.stats.StatsRece ver
 mport com.tw ter.follow_recom ndat ons.common.feature_hydrat on.adapters.Cand dateAlgor hmAdapter
 mport com.tw ter.follow_recom ndat ons.common.feature_hydrat on.common.FeatureS ce
 mport com.tw ter.follow_recom ndat ons.common.feature_hydrat on.common.FeatureS ce d
 mport com.tw ter.follow_recom ndat ons.common.feature_hydrat on.common.HasPreFetc dFeature
 mport com.tw ter.follow_recom ndat ons.common.models.Cand dateUser
 mport com.tw ter.follow_recom ndat ons.common.models.HasD splayLocat on
 mport com.tw ter.follow_recom ndat ons.common.models.HasS m larToContext
 mport com.tw ter.ml.ap .DataRecord
 mport com.tw ter.ml.ap .FeatureContext
 mport com.tw ter.product_m xer.core.model.marshall ng.request.HasCl entContext
 mport com.tw ter.st ch.St ch
 mport com.tw ter.t  l nes.conf gap .HasParams

/**
 * T  s ce only takes features from t  cand date's s ce,
 * wh ch  s all t   nformat on   have about t  cand date pre-feature-hydrat on
 */

@Prov des
@S ngleton
class Cand dateAlgor hmS ce @ nject() (stats: StatsRece ver) extends FeatureS ce {

  overr de val  d: FeatureS ce d = FeatureS ce d.Cand dateAlgor hmS ce d

  overr de val featureContext: FeatureContext = Cand dateAlgor hmAdapter.getFeatureContext

  overr de def hydrateFeatures(
    t: HasCl entContext
      w h HasPreFetc dFeature
      w h HasParams
      w h HasS m larToContext
      w h HasD splayLocat on, //   don't use t  target  re
    cand dates: Seq[Cand dateUser]
  ): St ch[Map[Cand dateUser, DataRecord]] = {
    val featureHydrat onStats = stats.scope("cand date_alg_s ce")
    val hasS ceDeta lsStat = featureHydrat onStats.counter("has_s ce_deta ls")
    val noS ceDeta lsStat = featureHydrat onStats.counter("no_s ce_deta ls")
    val noS ceRankStat = featureHydrat onStats.counter("no_s ce_rank")
    val hasS ceRankStat = featureHydrat onStats.counter("has_s ce_rank")
    val noS ceScoreStat = featureHydrat onStats.counter("no_s ce_score")
    val hasS ceScoreStat = featureHydrat onStats.counter("has_s ce_score")

    val cand datesToAlgoMap = for {
      cand date <- cand dates
    } y eld {
       f (cand date.userCand dateS ceDeta ls.nonEmpty) {
        hasS ceDeta lsStat. ncr()
        cand date.userCand dateS ceDeta ls.foreach { deta ls =>
           f (deta ls.cand dateS ceRanks. sEmpty) {
            noS ceRankStat. ncr()
          } else {
            hasS ceRankStat. ncr()
          }
           f (deta ls.cand dateS ceScores. sEmpty) {
            noS ceScoreStat. ncr()
          } else {
            hasS ceScoreStat. ncr()
          }
        }
      } else {
        noS ceDeta lsStat. ncr()
      }
      cand date -> Cand dateAlgor hmAdapter.adaptToDataRecord(cand date.userCand dateS ceDeta ls)
    }
    St ch.value(cand datesToAlgoMap.toMap)
  }
}

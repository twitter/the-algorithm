package com.tw ter.follow_recom ndat ons.common.rankers.ml_ranker.rank ng

 mport com.google. nject. nject
 mport com.google. nject.S ngleton
 mport com.tw ter.f nagle.stats.StatsRece ver
 mport com.tw ter.follow_recom ndat ons.common.base.GatedTransform
 mport com.tw ter.follow_recom ndat ons.common.base.StatsUt l.prof leSt chMapResults
 mport com.tw ter.follow_recom ndat ons.common.feature_hydrat on.common.HasPreFetc dFeature
 mport com.tw ter.follow_recom ndat ons.common.feature_hydrat on.s ces.UserScor ngFeatureS ce
 mport com.tw ter.follow_recom ndat ons.common.models.Cand dateUser
 mport com.tw ter.follow_recom ndat ons.common.models.HasDebugOpt ons
 mport com.tw ter.follow_recom ndat ons.common.models.HasD splayLocat on
 mport com.tw ter.follow_recom ndat ons.common.models.HasS m larToContext
 mport com.tw ter.follow_recom ndat ons.common.models.R chDataRecord
 mport com.tw ter.ml.ap .DataRecord
 mport com.tw ter.product_m xer.core.model.marshall ng.request.HasCl entContext
 mport com.tw ter.st ch.St ch
 mport com.tw ter.t  l nes.conf gap .HasParams
 mport com.tw ter.ut l.logg ng.Logg ng

/**
 * Hydrate features g ven target and cand dates l sts.
 * T   s a requ red step before MlRanker.
 *  f a feature  s not hydrated before MlRanker  s tr ggered, a runt   except on w ll be thrown
 */
@S ngleton
class HydrateFeaturesTransform[
  Target <: HasCl entContext w h HasParams w h HasDebugOpt ons w h HasPreFetc dFeature w h HasS m larToContext w h HasD splayLocat on] @ nject() (
  userScor ngFeatureS ce: UserScor ngFeatureS ce,
  stats: StatsRece ver)
    extends GatedTransform[Target, Cand dateUser]
    w h Logg ng {

  pr vate val hydrateFeaturesStats = stats.scope("hydrate_features")

  def transform(target: Target, cand dates: Seq[Cand dateUser]): St ch[Seq[Cand dateUser]] = {
    // get features
    val featureMapSt ch: St ch[Map[Cand dateUser, DataRecord]] =
      prof leSt chMapResults(
        userScor ngFeatureS ce.hydrateFeatures(target, cand dates),
        hydrateFeaturesStats)

    featureMapSt ch.map { featureMap =>
      cand dates
        .map { cand date =>
          val dataRecord = featureMap(cand date)
          // add debugRecord only w n t  request para ter  s set
          val debugDataRecord =  f (target.debugOpt ons.ex sts(_.fetchDebug nfo)) {
            So (cand date.toDebugDataRecord(dataRecord, userScor ngFeatureS ce.featureContext))
          } else None
          cand date.copy(
            dataRecord = So (R chDataRecord(So (dataRecord), debugDataRecord))
          )
        }
    }
  }
}

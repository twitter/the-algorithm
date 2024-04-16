package com.tw ter.ho _m xer.product.scored_t ets.feature_hydrator

 mport com.tw ter.f nagle.stats.StatsRece ver
 mport com.tw ter.ho _m xer.model.Ho Features.Author dFeature
 mport com.tw ter.ho _m xer.model.Ho Features.Author sCreatorFeature
 mport com.tw ter.ho _m xer.ut l.M ss ngKeyExcept on
 mport com.tw ter.product_m xer.component_l brary.model.cand date.T etCand date
 mport com.tw ter.product_m xer.core.feature.Feature
 mport com.tw ter.product_m xer.core.feature.featuremap.FeatureMap
 mport com.tw ter.product_m xer.core.feature.featuremap.FeatureMapBu lder
 mport com.tw ter.product_m xer.core.funct onal_component.feature_hydrator.BulkCand dateFeatureHydrator
 mport com.tw ter.product_m xer.core.model.common.Cand dateW hFeatures
 mport com.tw ter.product_m xer.core.model.common. dent f er.FeatureHydrator dent f er
 mport com.tw ter.product_m xer.core.p pel ne.P pel neQuery
 mport com.tw ter.product_m xer.core.ut l.OffloadFuturePools
 mport com.tw ter.st ch.St ch
 mport com.tw ter.strato.generated.cl ent.aud encerewards.aud enceRewardsServ ce.GetSuperFollowEl g b l yOnUserCl entColumn
 mport com.tw ter.ut l.Throw
 mport javax. nject. nject
 mport javax. nject.S ngleton

@S ngleton
class Author sCreatorFeatureHydrator @ nject() (
  getSuperFollowEl g b l yOnUserCl entColumn: GetSuperFollowEl g b l yOnUserCl entColumn,
  statsRece ver: StatsRece ver)
    extends BulkCand dateFeatureHydrator[P pel neQuery, T etCand date] {

  overr de val  dent f er: FeatureHydrator dent f er =
    FeatureHydrator dent f er("Author sCreator")

  overr de val features: Set[Feature[_, _]] =
    Set(Author sCreatorFeature)

  pr vate val scopedStatsRece ver = statsRece ver.scope(getClass.getS mpleNa )
  pr vate val keyFoundCounter = scopedStatsRece ver.counter("key/found")
  pr vate val keyFa lureCounter = scopedStatsRece ver.counter("key/fa lure")

  pr vate val M ss ngKeyFeatureMap = FeatureMapBu lder()
    .add(Author sCreatorFeature, Throw(M ss ngKeyExcept on))
    .bu ld()

  pr vate val DefaultFeatureMap = FeatureMapBu lder()
    .add(Author sCreatorFeature, false)
    .bu ld()

  overr de def apply(
    query: P pel neQuery,
    cand dates: Seq[Cand dateW hFeatures[T etCand date]]
  ): St ch[Seq[FeatureMap]] = {
    OffloadFuturePools.offloadSt ch {
      val author ds = cand dates.flatMap(_.features.getOrElse(Author dFeature, None)).d st nct
      St ch
        .collect {
          author ds.map { author d =>
            getSuperFollowEl g b l yOnUserCl entColumn.fetc r
              .fetch(author d)
              .map { author d -> _.v }
          }
        }.map { author dsTo sCreator =>
          val author dsTo sCreatorMap = author dsTo sCreator.toMap
          cand dates.map { cand date =>
            cand date.features.getOrElse(Author dFeature, None) match {
              case So (author d) =>
                author dsTo sCreatorMap.get(author d) match {
                  case So (response) =>
                    keyFoundCounter. ncr()
                    FeatureMapBu lder()
                      .add(Author sCreatorFeature, response.getOrElse(false)).bu ld()
                  case _ =>
                    keyFa lureCounter. ncr()
                    DefaultFeatureMap
                }
              case _ => M ss ngKeyFeatureMap
            }
          }
        }
    }
  }
}

package com.tw ter.ho _m xer.product.scored_t ets.feature_hydrator

 mport com.tw ter.follow_recom ndat ons.{thr ftscala => frs}
 mport com.tw ter.ho _m xer.product.scored_t ets.model.ScoredT etsQuery
 mport com.tw ter.product_m xer.component_l brary.cand date_s ce.recom ndat ons.UserFollowRecom ndat onsCand dateS ce
 mport com.tw ter.product_m xer.component_l brary.model.cand date.T etCand date
 mport com.tw ter.product_m xer.core.feature.Feature
 mport com.tw ter.product_m xer.core.feature.featuremap.FeatureMap
 mport com.tw ter.product_m xer.core.feature.featuremap.FeatureMapBu lder
 mport com.tw ter.product_m xer.core.funct onal_component.cand date_s ce.strato.StratoKeyV ew
 mport com.tw ter.product_m xer.core.funct onal_component.feature_hydrator.QueryFeatureHydrator
 mport com.tw ter.product_m xer.core.model.common. dent f er.FeatureHydrator dent f er
 mport com.tw ter.st ch.St ch
 mport javax. nject. nject
 mport javax. nject.S ngleton

object FrsSeedUser dsFeature extends Feature[T etCand date, Opt on[Seq[Long]]]
object FrsUserToFollo dByUser dsFeature extends Feature[T etCand date, Map[Long, Seq[Long]]]

@S ngleton
case class FrsSeedUsersQueryFeatureHydrator @ nject() (
  userFollowRecom ndat onsCand dateS ce: UserFollowRecom ndat onsCand dateS ce)
    extends QueryFeatureHydrator[ScoredT etsQuery] {

  pr vate val maxUsersToFetch = 100

  overr de val  dent f er: FeatureHydrator dent f er = FeatureHydrator dent f er("FrsSeedUsers")

  overr de def features: Set[Feature[_, _]] = Set(
    FrsSeedUser dsFeature,
    FrsUserToFollo dByUser dsFeature
  )

  overr de def hydrate(query: ScoredT etsQuery): St ch[FeatureMap] = {
    val frsRequest = frs.Recom ndat onRequest(
      cl entContext = frs.Cl entContext(query.getOpt onalUser d),
      d splayLocat on = frs.D splayLocat on.Ho T  l neT etRecs,
      maxResults = So (maxUsersToFetch)
    )

    userFollowRecom ndat onsCand dateS ce(StratoKeyV ew(frsRequest, Un ))
      .map { userRecom ndat ons: Seq[frs.UserRecom ndat on] =>
        val seedUser ds = userRecom ndat ons.map(_.user d)
        val seedUser dsSet = seedUser ds.toSet

        val userToFollo dByUser ds: Map[Long, Seq[Long]] = userRecom ndat ons.flatMap {
          userRecom ndat on =>
             f (seedUser dsSet.conta ns(userRecom ndat on.user d)) {
              val followProof =
                userRecom ndat on.reason.flatMap(_.accountProof).flatMap(_.followProof)
              val follo dByUser ds = followProof.map(_.user ds).getOrElse(Seq.empty)
              So (userRecom ndat on.user d -> follo dByUser ds)
            } else {
              None
            }
        }.toMap

        FeatureMapBu lder()
          .add(FrsSeedUser dsFeature, So (seedUser ds))
          .add(FrsUserToFollo dByUser dsFeature, userToFollo dByUser ds)
          .bu ld()
      }
  }
}

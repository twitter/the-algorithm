package com.tw ter.ho _m xer.product.scored_t ets.feature_hydrator.offl ne_aggregates

 mport com.tw ter.product_m xer.core.feature.Feature
 mport com.tw ter.product_m xer.core.feature.featuremap.FeatureMap
 mport com.tw ter.product_m xer.core.feature.featuremap.FeatureMapBu lder
 mport com.tw ter.product_m xer.core.funct onal_component.feature_hydrator.QueryFeatureHydrator
 mport com.tw ter.product_m xer.core.p pel ne.P pel neQuery
 mport com.tw ter.servo.repos ory.Repos ory
 mport com.tw ter.st ch.St ch
 mport com.tw ter.t  l nes.aggregate_ nteract ons.thr ftjava.UserAggregate nteract ons
 mport com.tw ter.t  l nes.data_process ng.ml_ut l.aggregat on_fra work.AggregateType.AggregateType
 mport com.tw ter.t  l nes.data_process ng.ml_ut l.aggregat on_fra work.StoreConf g
 mport com.tw ter.t  l nes.suggests.common.dense_data_record.thr ftscala.DenseFeature tadata
 mport com.tw ter.user_sess on_store.thr ftjava.UserSess on
 mport com.tw ter.ut l.Future

abstract class BaseAggregateQueryFeatureHydrator(
  featureRepos ory: Repos ory[Long, Opt on[UserSess on]],
   tadataRepos ory: Repos ory[ nt, Opt on[DenseFeature tadata]],
  feature: Feature[P pel neQuery, Opt on[AggregateFeaturesToDecodeW h tadata]])
    extends QueryFeatureHydrator[P pel neQuery] {

  overr de def hydrate(query: P pel neQuery): St ch[FeatureMap] = {
    val v e r d = query.getRequ redUser d

    St ch.callFuture(
      featureRepos ory(v e r d)
        .flatMap { userSess on: Opt on[UserSess on] =>
          val featuresW h tadata: Opt on[Future[AggregateFeaturesToDecodeW h tadata]] =
            userSess on
              .flatMap(decodeUserSess on(_))

          featuresW h tadata
            .map { fu: Future[AggregateFeaturesToDecodeW h tadata] => fu.map(So (_)) }
            .getOrElse(Future.None)
            .map { value =>
              FeatureMapBu lder()
                .add(feature, value)
                .bu ld()
            }
        }
    )
  }

  pr vate def decodeUserSess on(
    sess on: UserSess on
  ): Opt on[Future[AggregateFeaturesToDecodeW h tadata]] = {
    Opt on(sess on.user_aggregate_ nteract ons).flatMap { aggregates =>
      aggregates.getSetF eld match {
        case UserAggregate nteract ons._F elds.V17 =>
          So (
            getAggregateFeaturesW h tadata(
              aggregates.getV17.user_aggregates.vers on d,
              UserAggregate nteract ons.v17(aggregates.getV17))
          )
        case _ =>
          None
      }
    }
  }

  pr vate def getAggregateFeaturesW h tadata(
    vers on d:  nt,
    userAggregate nteract ons: UserAggregate nteract ons,
  ): Future[AggregateFeaturesToDecodeW h tadata] = {
     tadataRepos ory(vers on d)
      .map(AggregateFeaturesToDecodeW h tadata(_, userAggregate nteract ons))
  }
}

tra  BaseAggregateRootFeature
    extends Feature[P pel neQuery, Opt on[AggregateFeaturesToDecodeW h tadata]] {
  def aggregateStores: Set[StoreConf g[_]]

  lazy val aggregateTypes: Set[AggregateType] = aggregateStores.map(_.aggregateType)
}

package com.tw ter.product_m xer.core.serv ce.feature_hydrator_observer

 mport com.tw ter.f nagle.stats.BroadcastStatsRece ver
 mport com.tw ter.f nagle.stats.Counter
 mport com.tw ter.f nagle.stats.RollupStatsRece ver
 mport com.tw ter.f nagle.stats.StatsRece ver
 mport com.tw ter.ml.featurestore.l b.data.Hydrat onError
 mport com.tw ter.product_m xer.core.feature.Feature
 mport com.tw ter.product_m xer.core.feature.featuremap.FeatureMap
 mport com.tw ter.product_m xer.core.feature.featurestorev1.featurevalue.FeatureStoreV1ResponseFeature
 mport com.tw ter.product_m xer.core.funct onal_component.feature_hydrator.FeatureHydrator
 mport com.tw ter.product_m xer.core.funct onal_component.feature_hydrator.featurestorev1.FeatureStoreV1Cand dateFeatureHydrator
 mport com.tw ter.product_m xer.core.funct onal_component.feature_hydrator.featurestorev1.FeatureStoreV1QueryFeatureHydrator
 mport com.tw ter.product_m xer.core.model.common. dent f er.Component dent f er
 mport com.tw ter.product_m xer.core.serv ce.Executor
 mport com.tw ter.product_m xer.shared_l brary.observer.Observer
 mport com.tw ter.servo.ut l.CancelledExcept onExtractor
 mport com.tw ter.ut l.Throw
 mport com.tw ter.ut l.Throwables

class FeatureHydratorObserver(
  statsRece ver: StatsRece ver,
  hydrators: Seq[FeatureHydrator[_]],
  context: Executor.Context) {

  pr vate val hydratorAndFeatureToStats: Map[
    Component dent f er,
    Map[Feature[_, _], FeatureCounters]
  ] =
    hydrators.map { hydrator =>
      val hydratorScope = Executor.bu ldScopes(context, hydrator. dent f er)
      val featureToCounterMap: Map[Feature[_, _], FeatureCounters] = hydrator.features
        .as nstanceOf[Set[Feature[_, _]]].map { feature =>
          val scopedStats = scopedBroadcastStats(hydratorScope, feature)
          //  n  al ze so   have t m reg stered
          val requestsCounter = scopedStats.counter(Observer.Requests)
          val successCounter = scopedStats.counter(Observer.Success)
          // T se are dynam c so   can't really cac  t m
          scopedStats.counter(Observer.Fa lures)
          scopedStats.counter(Observer.Cancelled)
          feature -> FeatureCounters(requestsCounter, successCounter, scopedStats)
        }.toMap
      hydrator. dent f er -> featureToCounterMap
    }.toMap

  def observeFeatureSuccessAndFa lures(
    hydrator: FeatureHydrator[_],
    featureMaps: Seq[FeatureMap]
  ): Un  = {

    val features = hydrator.features.as nstanceOf[Set[Feature[_, _]]]

    val fa ledFeaturesW hErrorNa s: Map[Feature[_, _], Seq[Seq[Str ng]]] = hydrator match {
      case _: FeatureStoreV1QueryFeatureHydrator[_] |
          _: FeatureStoreV1Cand dateFeatureHydrator[_, _] =>
        featureMaps.to erator
          .flatMap(_.getTry(FeatureStoreV1ResponseFeature).toOpt on.map(_.fa ledFeatures)).flatMap {
            fa lureMap: Map[_ <: Feature[_, _], Set[Hydrat onError]] =>
              fa lureMap.flatMap {
                case (feature, errors: Set[Hydrat onError]) =>
                  errors. adOpt on.map { error =>
                    feature -> Seq(Observer.Fa lures, error.errorType)
                  }
              }.to erator
          }.toSeq.groupBy { case (feature, _) => feature }.mapValues { seqOfTuples =>
            seqOfTuples.map { case (_, error) => error }
          }

      case _: FeatureHydrator[_] =>
        features.to erator
          .flatMap { feature =>
            featureMaps
              .flatMap(_.underly ngMap
                .get(feature).collect {
                  case Throw(CancelledExcept onExtractor(throwable)) =>
                    (feature, Observer.Cancelled +: Throwables.mkStr ng(throwable))
                  case Throw(throwable) =>
                    (feature, Observer.Fa lures +: Throwables.mkStr ng(throwable))
                })
          }.toSeq.groupBy { case (feature, _) => feature }.mapValues { seqOfTuples =>
            seqOfTuples.map { case (_, error) => error }
          }
    }

    val fa ledFeaturesW hErrorCountsMap: Map[Feature[_, _], Map[Seq[Str ng],  nt]] =
      fa ledFeaturesW hErrorNa s.mapValues(_.groupBy { statKey => statKey }.mapValues(_.s ze))

    val featuresToCounterMap = hydratorAndFeatureToStats.getOrElse(
      hydrator. dent f er,
      throw new M ss ngHydratorExcept on(hydrator. dent f er))
    features.foreach { feature =>
      val hydratorFeatureCounters: FeatureCounters = featuresToCounterMap.getOrElse(
        feature,
        throw new M ss ngFeatureExcept on(hydrator. dent f er, feature))
      val fa ledMapsCount = fa ledFeaturesW hErrorNa s.getOrElse(feature, Seq.empty).s ze
      val fa ledFeatureErrorCounts = fa ledFeaturesW hErrorCountsMap.getOrElse(feature, Map.empty)

      hydratorFeatureCounters.requestsCounter. ncr(featureMaps.s ze)
      hydratorFeatureCounters.successCounter. ncr(featureMaps.s ze - fa ledMapsCount)
      fa ledFeatureErrorCounts.foreach {
        case (fa lure, count) =>
          hydratorFeatureCounters.scopedStats.counter(fa lure: _*). ncr(count)
      }
    }
  }

  pr vate def scopedBroadcastStats(
    hydratorScope: Executor.Scopes,
    feature: Feature[_, _],
  ): StatsRece ver = {
    val suff x = Seq("Feature", feature.toStr ng)
    val localScope = hydratorScope.componentScopes ++ suff x
    val relat veScope = hydratorScope.relat veScope ++ suff x
    new RollupStatsRece ver(
      BroadcastStatsRece ver(
        Seq(
          statsRece ver.scope(localScope: _*),
          statsRece ver.scope(relat veScope: _*),
        )
      ))
  }
}

case class FeatureCounters(
  requestsCounter: Counter,
  successCounter: Counter,
  scopedStats: StatsRece ver)

class M ss ngHydratorExcept on(featureHydrator dent f er: Component dent f er)
    extends Except on(s"M ss ng Feature Hydrator  n Stats Map: ${featureHydrator dent f er.na }")

class M ss ngFeatureExcept on(
  featureHydrator dent f er: Component dent f er,
  feature: Feature[_, _])
    extends Except on(
      s"M ss ng Feature  n Stats Map: ${feature.toStr ng} for ${featureHydrator dent f er.na }")

package com.tw ter.ho _m xer.product.scored_t ets.feature_hydrator.offl ne_aggregates

 mport com.tw ter.ml.ap .FeatureContext
 mport com.tw ter.t  l nes.data_process ng.ml_ut l.aggregat on_fra work.AggregateGroup
 mport com.tw ter.t  l nes.data_process ng.ml_ut l.aggregat on_fra work.AggregateType.AggregateType
 mport com.tw ter.t  l nes.data_process ng.ml_ut l.aggregat on_fra work.TypedAggregateGroup
 mport scala.jdk.Collect onConverters.asJava erableConverter

// A  lper class der v ng aggregate feature  nfo from t  g ven conf gurat on para ters.
class AggregateFeature nfo(
  val aggregateGroups: Set[AggregateGroup],
  val aggregateType: AggregateType) {

  pr vate val typedAggregateGroups = aggregateGroups.flatMap(_.bu ldTypedAggregateGroups()).toL st

  val featureContext: FeatureContext =
    new FeatureContext(
      (typedAggregateGroups.flatMap(_.allOutputFeatures) ++
        typedAggregateGroups.flatMap(_.allOutputKeys) ++
        Seq(TypedAggregateGroup.t  stampFeature)).asJava)

  val feature: BaseAggregateRootFeature =
    AggregateFeature nfo.p ckFeature(aggregateType)
}

object AggregateFeature nfo {
  val features: Set[BaseAggregateRootFeature] =
    Set(PartAAggregateRootFeature, PartBAggregateRootFeature)

  def p ckFeature(aggregateType: AggregateType): BaseAggregateRootFeature = {
    val f ltered = features.f lter(_.aggregateTypes.conta ns(aggregateType))
    requ re(
      f ltered.s ze == 1,
      "requested AggregateType must be backed by exactly one phys cal store.")
    f ltered. ad
  }
}

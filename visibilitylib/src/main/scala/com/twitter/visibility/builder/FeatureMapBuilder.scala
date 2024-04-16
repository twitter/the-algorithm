package com.tw ter.v s b l y.bu lder

 mport com.tw ter.f nagle.stats.NullStatsRece ver
 mport com.tw ter.f nagle.stats.StatsRece ver
 mport com.tw ter.servo.ut l.Gate
 mport com.tw ter.st ch.St ch
 mport com.tw ter.v s b l y.features._
 mport com.tw ter.v s b l y.common.st ch.St ch lpers
 mport scala.collect on.mutable

object FeatureMapBu lder {
  type Bu ld = Seq[FeatureMapBu lder => FeatureMapBu lder] => FeatureMap

  def apply(
    statsRece ver: StatsRece ver = NullStatsRece ver,
    enableSt chProf l ng: Gate[Un ] = Gate.False
  ): Bu ld =
    fns =>
      Funct on
        .cha n(fns).apply(
          new FeatureMapBu lder(statsRece ver, enableSt chProf l ng)
        ).bu ld
}

class FeatureMapBu lder pr vate[bu lder] (
  statsRece ver: StatsRece ver,
  enableSt chProf l ng: Gate[Un ] = Gate.False) {

  pr vate[t ] val hydratedScope =
    statsRece ver.scope("v s b l y_result_bu lder").scope("hydrated")

  val mapBu lder: mutable.Bu lder[(Feature[_], St ch[_]), Map[Feature[_], St ch[_]]] =
    Map.newBu lder[Feature[_], St ch[_]]

  val constantMapBu lder: mutable.Bu lder[(Feature[_], Any), Map[Feature[_], Any]] =
    Map.newBu lder[Feature[_], Any]

  def bu ld: FeatureMap = new FeatureMap(mapBu lder.result(), constantMapBu lder.result())

  def w hConstantFeature[T](feature: Feature[T], value: T): FeatureMapBu lder = {
    val anyValue: Any = value.as nstanceOf[Any]
    constantMapBu lder += (feature -> anyValue)
    t 
  }

  def w hFeature[T](feature: Feature[T], st ch: St ch[T]): FeatureMapBu lder = {
    val prof ledSt ch =  f (enableSt chProf l ng()) {
      val featureScope = hydratedScope.scope(feature.na )
      St ch lpers.prof leSt ch(st ch, Seq(hydratedScope, featureScope))
    } else {
      st ch
    }

    val featureSt chRef = St ch.ref(prof ledSt ch)

    mapBu lder += FeatureMap.rescueFeatureTuple(feature -> featureSt chRef)

    t 
  }

  def w hConstantFeature[T](feature: Feature[T], opt on: Opt on[T]): FeatureMapBu lder = {
    opt on.map(w hConstantFeature(feature, _)).getOrElse(t )
  }
}

package com.tw ter.v s b l y.features

 mport com.tw ter.f nagle.mux.Cl entD scardedRequestExcept on
 mport com.tw ter.f nagle.stats.NullStatsRece ver
 mport com.tw ter.f nagle.stats.StatsRece ver
 mport com.tw ter.st ch.St ch
 mport scala.language.ex stent als

class M ss ngFeatureExcept on(feature: Feature[_]) extends Except on("M ss ng value for " + feature)

case class FeatureFa ledExcept on(feature: Feature[_], except on: Throwable) extends Except on

pr vate[v s b l y] case class FeatureFa ledPlaceholderObject(throwable: Throwable)

class FeatureMap(
  val map: Map[Feature[_], St ch[_]],
  val constantMap: Map[Feature[_], Any]) {

  def conta ns[T](feature: Feature[T]): Boolean =
    constantMap.conta ns(feature) || map.conta ns(feature)

  def conta nsConstant[T](feature: Feature[T]): Boolean = constantMap.conta ns(feature)

  lazy val s ze:  nt = keys.s ze

  lazy val keys: Set[Feature[_]] = constantMap.keySet ++ map.keySet

  def get[T](feature: Feature[T]): St ch[T] = {
    map.get(feature) match {
      case _  f constantMap.conta ns(feature) =>
        St ch.value(getConstant(feature))
      case So (x) =>
        x.as nstanceOf[St ch[T]]
      case _ =>
        St ch.except on(new M ss ngFeatureExcept on(feature))
    }
  }

  def getConstant[T](feature: Feature[T]): T = {
    constantMap.get(feature) match {
      case So (x) =>
        x.as nstanceOf[T]
      case _ =>
        throw new M ss ngFeatureExcept on(feature)
    }
  }

  def -[T](key: Feature[T]): FeatureMap = new FeatureMap(map - key, constantMap - key)

  overr de def toStr ng: Str ng = "FeatureMap(%s, %s)".format(map, constantMap)
}

object FeatureMap {

  def empty: FeatureMap = new FeatureMap(Map.empty, Map.empty)

  def resolve(
    featureMap: FeatureMap,
    statsRece ver: StatsRece ver = NullStatsRece ver
  ): St ch[ResolvedFeatureMap] = {
    val featureMapHydrat onStatsRece ver = statsRece ver.scope("feature_map_hydrat on")

    St ch
      .traverse(featureMap.map.toSeq) {
        case (feature, value: St ch[_]) =>
          val featureStatsRece ver = featureMapHydrat onStatsRece ver.scope(feature.na )
          lazy val featureFa lureStat = featureStatsRece ver.scope("fa lures")
          val featureSt ch: St ch[(Feature[_], Any)] = value
            .map { resolvedValue =>
              featureStatsRece ver.counter("success"). ncr()
              (feature, resolvedValue)
            }

          featureSt ch
            .handle {
              case ffe: FeatureFa ledExcept on =>
                featureFa lureStat.counter(). ncr()
                featureFa lureStat.counter(ffe.except on.getClass.getNa ). ncr()
                (feature, FeatureFa ledPlaceholderObject(ffe.except on))
            }
            .ensure {
              featureStatsRece ver.counter("requests"). ncr()
            }
      }
      .map { resolvedFeatures: Seq[(Feature[_], Any)] =>
        new ResolvedFeatureMap(resolvedFeatures.toMap ++ featureMap.constantMap)
      }
  }

  def rescueFeatureTuple(kv: (Feature[_], St ch[_])): (Feature[_], St ch[_]) = {
    val (k, v) = kv

    val rescueValue = v.rescue {
      case e =>
        e match {
          case cdre: Cl entD scardedRequestExcept on => St ch.except on(cdre)
          case _ => St ch.except on(FeatureFa ledExcept on(k, e))
        }
    }

    (k, rescueValue)
  }
}

class ResolvedFeatureMap(pr vate[v s b l y] val resolvedMap: Map[Feature[_], Any])
    extends FeatureMap(Map.empty, resolvedMap) {

  overr de def equals(ot r: Any): Boolean = ot r match {
    case ot rResolvedFeatureMap: ResolvedFeatureMap =>
      t .resolvedMap.equals(ot rResolvedFeatureMap.resolvedMap)
    case _ => false
  }

  overr de def toStr ng: Str ng = "ResolvedFeatureMap(%s)".format(resolvedMap)
}

object ResolvedFeatureMap {
  def apply(resolvedMap: Map[Feature[_], Any]): ResolvedFeatureMap = {
    new ResolvedFeatureMap(resolvedMap)
  }
}

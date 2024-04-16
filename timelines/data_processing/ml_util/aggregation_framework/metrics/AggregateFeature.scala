package com.tw ter.t  l nes.data_process ng.ml_ut l.aggregat on_fra work. tr cs

 mport com.tw ter.ut l.Durat on
 mport com.tw ter.ml.ap ._
 mport java.lang.{Boolean => JBoolean}

/**
 * Case class used as shared argu nt for
 * getAggregateValue() and setAggregateValue()  n Aggregat on tr c.
 *
 * @param aggregatePref x Pref x for aggregate feature na 
 * @param feature S mple (non-aggregate) feature be ng aggregated. T 
    s opt onal;  f None, t n t  label  s aggregated on  s own w hout
   be ng crossed w h any feature.
 * @param label Label be ng pa red w h. T   s opt onal;  f None, t n
   t  feature  s aggregated on  s own w hout be ng crossed w h any label.
 * @param halfL fe Half l fe be ng used for aggregat on
 */
case class AggregateFeature[T](
  aggregatePref x: Str ng,
  feature: Opt on[Feature[T]],
  label: Opt on[Feature[JBoolean]],
  halfL fe: Durat on) {
  val aggregateType = "pa r"
  val labelNa : Str ng = label.map(_.getDenseFeatureNa ()).getOrElse("any_label")
  val featureNa : Str ng = feature.map(_.getDenseFeatureNa ()).getOrElse("any_feature")

  /*
   * T  val precomputes a port on of t  feature na 
   * for faster process ng. Str ng bu ld ng turns
   * out to be a s gn f cant bottleneck.
   */
  val featurePref x: Str ng = L st(
    aggregatePref x,
    aggregateType,
    labelNa ,
    featureNa ,
    halfL fe.toStr ng
  ).mkStr ng(".")
}

/* Compan on object w h ut l  thods. */
object AggregateFeature {
  def parseHalfL fe(aggregateFeature: Feature[_]): Durat on = {
    val aggregateComponents = aggregateFeature.getDenseFeatureNa ().spl ("\\.")
    val numComponents = aggregateComponents.length
    val halfL feStr = aggregateComponents(numComponents - 3) + "." +
      aggregateComponents(numComponents - 2)
    Durat on.parse(halfL feStr)
  }
}

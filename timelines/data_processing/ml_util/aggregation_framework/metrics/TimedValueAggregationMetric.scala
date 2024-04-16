package com.tw ter.t  l nes.data_process ng.ml_ut l.aggregat on_fra work. tr cs

 mport com.tw ter.ml.ap ._
 mport com.tw ter.t  l nes.data_process ng.ml_ut l.aggregat on_fra work. tr cs.AggregateFeature
 mport com.tw ter.t  l nes.data_process ng.ml_ut l.aggregat on_fra work. tr cs.Aggregat on tr cCommon
 mport com.tw ter.t  l nes.data_process ng.ml_ut l.aggregat on_fra work. tr cs.T  dValue
 mport com.tw ter.t  l nes.data_process ng.ml_ut l.aggregat on_fra work. tr cs.Aggregat on tr c
 mport com.tw ter.ut l.Durat on
 mport com.tw ter.ut l.T  
 mport java.lang.{Double => JDouble}
 mport java.lang.{Long => JLong}
 mport java.ut l.{Map => JMap}

/*
 * Cont nuousAggregat on tr c overr des  thod Aggregat on tr c deal ng
 * w h read ng and wr  ng cont nuous values from a data record.
 *
 * operatorNa   s a str ng used for nam ng t  resultant aggregate feature
 * (e.g. "count"  f  s a count feature, or "sum"  f a sum feature).
 */
tra  T  dValueAggregat on tr c[T] extends Aggregat on tr c[T, Double] {
   mport Aggregat on tr cCommon._

  val operatorNa : Str ng

  overr de def getAggregateValue(
    record: DataRecord,
    query: AggregateFeature[T],
    aggregateOutputs: Opt on[L st[JLong]] = None
  ): T  dValue[Double] = {
    /*
     *   know aggregateOutputs(0) w ll have t  cont nuous feature,
     * s nce   put   t re  n getOutputFeature ds() - see code below.
     * T   lps us get a 4x speedup. Us ng any structure more complex
     * than a l st was also a performance bottleneck.
     */
    val featureHash: JLong = aggregateOutputs
      .getOrElse(getOutputFeature ds(query))
      . ad

    val cont nuousValueOpt on: Opt on[Double] = Opt on(record.cont nuousFeatures)
      .flatMap { case jmap: JMap[JLong, JDouble] => Opt on(jmap.get(featureHash)) }
      .map(_.toDouble)

    val t  Opt on = Opt on(record.d screteFeatures)
      .flatMap { case jmap: JMap[JLong, JLong] => Opt on(jmap.get(T  stampHash)) }
      .map(_.toLong)

    val resultOpt on: Opt on[T  dValue[Double]] = (cont nuousValueOpt on, t  Opt on) match {
      case (So (featureValue), So (t  samp)) =>
        So (T  dValue[Double](featureValue, T  .fromM ll seconds(t  samp)))
      case _ => None
    }

    resultOpt on.getOrElse(zero(t  Opt on))
  }

  overr de def setAggregateValue(
    record: DataRecord,
    query: AggregateFeature[T],
    aggregateOutputs: Opt on[L st[JLong]] = None,
    value: T  dValue[Double]
  ): Un  = {
    /*
     *   know aggregateOutputs(0) w ll have t  cont nuous feature,
     * s nce   put   t re  n getOutputFeature ds() - see code below.
     * T   lps us get a 4x speedup. Us ng any structure more complex
     * than a l st was also a performance bottleneck.
     */
    val featureHash: JLong = aggregateOutputs
      .getOrElse(getOutputFeature ds(query))
      . ad

    /* Only set value  f non-zero to save space */
     f (value.value != 0.0) {
      record.putToCont nuousFeatures(featureHash, value.value)
    }

    /*
     *   do not set t  stamp s nce that m ght affect correctness of
     * future aggregat ons due to t  decay semant cs.
     */
  }

  /* Only one feature stored  n t  aggregated datarecord: t  result cont nuous value */
  overr de def getOutputFeatures(query: AggregateFeature[T]): L st[Feature[_]] = {
    val feature = cac dFullFeature(query, operatorNa , FeatureType.CONT NUOUS)
    L st(feature)
  }
}

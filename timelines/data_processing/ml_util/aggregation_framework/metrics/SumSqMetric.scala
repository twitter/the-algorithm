package com.tw ter.t  l nes.data_process ng.ml_ut l.aggregat on_fra work. tr cs

 mport com.tw ter.ml.ap ._
 mport com.tw ter.ml.ap .ut l.SR chDataRecord
 mport com.tw ter.ut l.T  
 mport java.lang.{Double => JDouble}
 mport java.lang.{Long => JLong}

case class TypedSumSq tr c() extends TypedSumL ke tr c[JDouble] {
   mport Aggregat on tr cCommon._

  overr de val operatorNa  = "sumsq"

  /*
   * Transform feature ->  s squared value  n t  g ven record
   * or 0 w n feature = None (sumsq has no  an ng  n t  case)
   */
  overr de def get ncre ntValue(
    record: DataRecord,
    feature: Opt on[Feature[JDouble]],
    t  stampFeature: Feature[JLong]
  ): T  dValue[Double] = feature match {
    case So (f) => {
      val featureVal =
        Opt on(SR chDataRecord(record).getFeatureValue(f)).map(_.toDouble).getOrElse(0.0)
      T  dValue[Double](
        value = featureVal * featureVal,
        t  stamp = T  .fromM ll seconds(getT  stamp(record, t  stampFeature))
      )
    }

    case None =>
      T  dValue[Double](
        value = 0.0,
        t  stamp = T  .fromM ll seconds(getT  stamp(record, t  stampFeature))
      )
  }
}

/**
 * Syntact c sugar for t  sum of squares  tr c that works w h cont nuous features.
 * See Easy tr c.scala for more deta ls on why t   s useful.
 */
object SumSq tr c extends Easy tr c {
  overr de def forFeatureType[T](
    featureType: FeatureType
  ): Opt on[Aggregat on tr c[T, _]] =
    featureType match {
      case FeatureType.CONT NUOUS =>
        So (TypedSumSq tr c().as nstanceOf[Aggregat on tr c[T, Double]])
      case _ => None
    }
}

package com.tw ter.t  l nes.data_process ng.ml_ut l.aggregat on_fra work. tr cs

 mport com.tw ter.ml.ap ._
 mport com.tw ter.ml.ap .ut l.SR chDataRecord
 mport com.tw ter.ut l.T  
 mport java.lang.{Long => JLong}

case class TypedCount tr c[T](
) extends TypedSumL ke tr c[T] {
   mport Aggregat on tr cCommon._
   mport Convers onUt ls._
  overr de val operatorNa  = "count"

  overr de def get ncre ntValue(
    record: DataRecord,
    feature: Opt on[Feature[T]],
    t  stampFeature: Feature[JLong]
  ): T  dValue[Double] = {
    val featureEx sts: Boolean = feature match {
      case So (f) => SR chDataRecord(record).hasFeature(f)
      case None => true
    }

    T  dValue[Double](
      value = booleanToDouble(featureEx sts),
      t  stamp = T  .fromM ll seconds(getT  stamp(record, t  stampFeature))
    )
  }
}

/**
 * Syntact c sugar for t  count  tr c that works w h
 * any feature type as opposed to be ng t ed to a spec f c type.
 * See Easy tr c.scala for more deta ls on why t   s useful.
 */
object Count tr c extends Easy tr c {
  overr de def forFeatureType[T](
    featureType: FeatureType,
  ): Opt on[Aggregat on tr c[T, _]] =
    So (TypedCount tr c[T]())
}

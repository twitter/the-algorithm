package com.tw ter.t  l nes.data_process ng.ml_ut l.aggregat on_fra work. tr cs

 mport com.tw ter.ml.ap ._
 mport com.tw ter.ml.ap .ut l.SR chDataRecord
 mport com.tw ter.t  l nes.data_process ng.ml_ut l.aggregat on_fra work. tr cs.Aggregat on tr cCommon.getT  stamp
 mport com.tw ter.ut l.Durat on
 mport com.tw ter.ut l.T  
 mport java.lang.{Long => JLong}
 mport java.lang.{Number => JNumber}
 mport java.lang.{Double => JDouble}
 mport scala.math.max

case class TypedMax tr c[T <: JNumber](defaultValue: Double = 0.0)
    extends T  dValueAggregat on tr c[T] {
  overr de val operatorNa  = "max"

  overr de def get ncre ntValue(
    dataRecord: DataRecord,
    feature: Opt on[Feature[T]],
    t  stampFeature: Feature[JLong]
  ): T  dValue[Double] = {
    val value = feature
      .flatMap(SR chDataRecord(dataRecord).getFeatureValueOpt(_))
      .map(_.doubleValue()).getOrElse(defaultValue)
    val t  stamp = T  .fromM ll seconds(getT  stamp(dataRecord, t  stampFeature))
    T  dValue[Double](value = value, t  stamp = t  stamp)
  }

  overr de def plus(
    left: T  dValue[Double],
    r ght: T  dValue[Double],
    halfL fe: Durat on
  ): T  dValue[Double] = {

    assert(
      halfL fe.toStr ng == "Durat on.Top",
      s"halfL fe must be Durat on.Top w n us ng max  tr c, but ${halfL fe.toStr ng}  s used"
    )

    T  dValue[Double](
      value = max(left.value, r ght.value),
      t  stamp = left.t  stamp.max(r ght.t  stamp)
    )
  }

  overr de def zero(t  Opt: Opt on[Long]): T  dValue[Double] =
    T  dValue[Double](
      value = 0.0,
      t  stamp = T  .fromM ll seconds(0)
    )
}

object Max tr c extends Easy tr c {
  def forFeatureType[T](
    featureType: FeatureType,
  ): Opt on[Aggregat on tr c[T, _]] =
    featureType match {
      case FeatureType.CONT NUOUS =>
        So (TypedMax tr c[JDouble]().as nstanceOf[Aggregat on tr c[T, Double]])
      case FeatureType.D SCRETE =>
        So (TypedMax tr c[JLong]().as nstanceOf[Aggregat on tr c[T, Double]])
      case _ => None
    }
}

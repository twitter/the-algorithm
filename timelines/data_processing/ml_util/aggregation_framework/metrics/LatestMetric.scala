package com.tw ter.t  l nes.data_process ng.ml_ut l.aggregat on_fra work. tr cs

 mport com.tw ter.ml.ap ._
 mport com.tw ter.ml.ap .ut l.SR chDataRecord
 mport com.tw ter.ml.ap .DataRecord
 mport com.tw ter.ml.ap .Feature
 mport com.tw ter.ml.ap .FeatureType
 mport com.tw ter.t  l nes.data_process ng.ml_ut l.aggregat on_fra work. tr cs.Aggregat on tr cCommon.getT  stamp
 mport com.tw ter.t  l nes.data_process ng.ml_ut l.aggregat on_fra work. tr cs.Aggregat on tr c
 mport com.tw ter.t  l nes.data_process ng.ml_ut l.aggregat on_fra work. tr cs.Easy tr c
 mport com.tw ter.ut l.Durat on
 mport com.tw ter.ut l.T  
 mport java.lang.{Double => JDouble}
 mport java.lang.{Long => JLong}
 mport java.lang.{Number => JNumber}

case class TypedLatest tr c[T <: JNumber](defaultValue: Double = 0.0)
    extends T  dValueAggregat on tr c[T] {
  overr de val operatorNa  = "latest"

  overr de def plus(
    left: T  dValue[Double],
    r ght: T  dValue[Double],
    halfL fe: Durat on
  ): T  dValue[Double] = {
    assert(
      halfL fe.toStr ng == "Durat on.Top",
      s"halfL fe must be Durat on.Top w n us ng latest  tr c, but ${halfL fe.toStr ng}  s used"
    )

     f (left.t  stamp > r ght.t  stamp) {
      left
    } else {
      r ght
    }
  }

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

  overr de def zero(t  Opt: Opt on[Long]): T  dValue[Double] =
    T  dValue[Double](
      value = 0.0,
      t  stamp = T  .fromM ll seconds(0)
    )
}

object Latest tr c extends Easy tr c {
  overr de def forFeatureType[T](
    featureType: FeatureType
  ): Opt on[Aggregat on tr c[T, _]] = {
    featureType match {
      case FeatureType.CONT NUOUS =>
        So (TypedLatest tr c[JDouble]().as nstanceOf[Aggregat on tr c[T, Double]])
      case FeatureType.D SCRETE =>
        So (TypedLatest tr c[JLong]().as nstanceOf[Aggregat on tr c[T, Double]])
      case _ => None
    }
  }
}

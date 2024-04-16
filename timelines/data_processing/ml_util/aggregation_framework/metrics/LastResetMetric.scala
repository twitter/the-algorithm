package com.tw ter.t  l nes.data_process ng.ml_ut l.aggregat on_fra work. tr cs

 mport java.lang.{Long => JLong}
 mport com.tw ter.ml.ap ._
 mport com.tw ter.ml.ap .ut l.SR chDataRecord
 mport com.tw ter.t  l nes.data_process ng.ml_ut l.aggregat on_fra work. tr cs.Convers onUt ls._
 mport com.tw ter.ut l.Durat on
 mport com.tw ter.ut l.T  
 mport scala.math.max

/**
 * T   tr c  asures how recently an act on has taken place. A value of 1.0
 *  nd cates t  act on happened just now. T  value decays w h t    f t 
 * act on has not taken place and  s reset to 1 w n t  act on happens. So lo r
 * value  nd cates a stale or older act on.
 *
 * For example cons der an act on of "user l k ng a v deo". T  last reset  tr c
 * value changes as follows for a half l fe of 1 day.
 *
 * ----------------------------------------------------------------------------
 *  day  |         act on           |  feature value |      Descr pt on
 * ----------------------------------------------------------------------------
 *   1   | user l kes t  v deo     |      1.0       |    Set t  value to 1
 *   2   | user does not l ke v deo |      0.5       |    Decay t  value
 *   3   | user does not l ke v deo |      0.25      |    Decay t  value
 *   4   | user l kes t  v deo     |      1.0       |    Reset t  value to 1
 * -----------------------------------------------------------------------------
 *
 * @tparam T
 */
case class TypedLastReset tr c[T]() extends T  dValueAggregat on tr c[T] {
   mport Aggregat on tr cCommon._

  overr de val operatorNa  = "last_reset"

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
  pr vate def getDecayedValue(
    olderT  dValue: T  dValue[Double],
    ne rT  stamp: T  ,
    halfL fe: Durat on
  ): Double = {
     f (halfL fe. nM ll seconds == 0L) {
      0.0
    } else {
      val t  Delta = ne rT  stamp. nM ll seconds - olderT  dValue.t  stamp. nM ll seconds
      val resultValue = olderT  dValue.value / math.pow(2.0, t  Delta / halfL fe. nM ll s)
       f (resultValue > Aggregat on tr cCommon.Eps lon) resultValue else 0.0
    }
  }

  overr de def plus(
    left: T  dValue[Double],
    r ght: T  dValue[Double],
    halfL fe: Durat on
  ): T  dValue[Double] = {

    val (ne rT  dValue, olderT  dValue) =  f (left.t  stamp > r ght.t  stamp) {
      (left, r ght)
    } else {
      (r ght, left)
    }

    val opt onallyDecayedOlderValue =  f (halfL fe == Durat on.Top) {
      // S nce   don't want to decay, older value  s not changed
      olderT  dValue.value
    } else {
      // Decay older value
      getDecayedValue(olderT  dValue, ne rT  dValue.t  stamp, halfL fe)
    }

    T  dValue[Double](
      value = max(ne rT  dValue.value, opt onallyDecayedOlderValue),
      t  stamp = ne rT  dValue.t  stamp
    )
  }

  overr de def zero(t  Opt: Opt on[Long]): T  dValue[Double] = T  dValue[Double](
    value = 0.0,
    t  stamp = T  .fromM ll seconds(0)
  )
}

/**
 * Syntact c sugar for t  last reset  tr c that works w h
 * any feature type as opposed to be ng t ed to a spec f c type.
 * See Easy tr c.scala for more deta ls on why t   s useful.
 */
object LastReset tr c extends Easy tr c {
  overr de def forFeatureType[T](
    featureType: FeatureType
  ): Opt on[Aggregat on tr c[T, _]] =
    So (TypedLastReset tr c[T]())
}

package com.tw ter.s mclusters_v2.summ ngb rd.common

 mport com.tw ter.algeb rd.Mono d
 mport com.tw ter.summ ngb rd._

object Sum rW hSumValues {
  /*
  A common pattern  n  ron  s to use .sumByKeys to aggregate a value  n a store, and t n cont nue
  process ng w h t  aggregated value. Unfortunately, .sumByKeys returns t  ex st ng value from t 
  store and t  delta separately, leav ng   to manually comb ne t m.

  Example w hout sumValues:

   so KeyedProducer
    .sumByKeys(score)(mono d)
    .map {
      case (key, (ex st ngValueOpt, delta)) =>
        //  f   want t  value that was actually wr ten to t  store,   have to comb ne
        // ex st ngValueOpt and delta y self
    }

  Example w h sumValues:

   so KeyedProducer
    .sumByKeys(score)(mono d)
    .sumValues(mono d)
    .map {
      case (key, value) =>
        // `value`  s t  sa  as what was wr ten to t  store
    }
   */
   mpl c  class Sum rW hSumValues[P <: Platform[P], K, V](
    sum r: Sum r[P, K, V]) {
    def sumValues(mono d: Mono d[V]): KeyedProducer[P, K, V] =
      sum r.mapValues {
        case (So (oldV), deltaV) => mono d.plus(oldV, deltaV)
        case (None, deltaV) => deltaV
      }
  }
}

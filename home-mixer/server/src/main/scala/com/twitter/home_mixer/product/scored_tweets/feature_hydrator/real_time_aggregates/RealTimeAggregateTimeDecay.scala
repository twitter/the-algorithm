package com.tw ter.ho _m xer.product.scored_t ets.feature_hydrator.real_t  _aggregates

 mport com.tw ter.ml.ap .DataRecord
 mport com.tw ter.ml.ap .constant.SharedFeatures.T MESTAMP
 mport com.tw ter.ut l.Durat on

/**
 * T  default T  Decay  mple ntat on for real t   aggregates.
 *
 * @param feature dToHalfL fe A precomputed map from aggregate feature  ds to t  r half l ves.
 * @param t  stampFeature d A d screte t  stamp feature  d.
 */
case class RealT  AggregateT  Decay(
  feature dToHalfL fe: Map[Long, Durat on],
  t  stampFeature d: Long = T MESTAMP.getFeature d) {

  /**
   * Mutates t  data record wh ch  s just a reference to t   nput.
   *
   * @param record    Data record to apply decay to ( s mutated).
   * @param t  Now   T  current read t   ( n m ll seconds) to decay counts forward to.
   */
  def apply(record: DataRecord, t  Now: Long): Un  = {
     f (record. sSetD screteFeatures) {
      val d screteFeatures = record.getD screteFeatures
       f (d screteFeatures.conta nsKey(t  stampFeature d)) {
         f (record. sSetCont nuousFeatures) {
          val ctsFeatures = record.getCont nuousFeatures

          val storedT  stamp: Long = d screteFeatures.get(t  stampFeature d)
          val scaledDt =  f (t  Now > storedT  stamp) {
            (t  Now - storedT  stamp).toDouble * math.log(2)
          } else 0.0
          feature dToHalfL fe.foreach {
            case (feature d, halfL fe) =>
               f (ctsFeatures.conta nsKey(feature d)) {
                val storedValue = ctsFeatures.get(feature d)
                val alpha =
                   f (halfL fe. nM ll seconds != 0) math.exp(-scaledDt / halfL fe. nM ll seconds)
                  else 0
                val decayedValue: Double = alpha * storedValue
                record.putToCont nuousFeatures(feature d, decayedValue)
              }
          }
        }
        d screteFeatures.remove(t  stampFeature d)
      }
    }
  }
}

package com.tw ter.t  l nes.data_process ng.ml_ut l.aggregat on_fra work.job

 mport com.tw ter.ml.ap .DataRecord
 mport com.tw ter.summ ngb rd.Counter

/**
 * A summ ngb rd Counter wh ch  s assoc ated w h a pred cate wh ch operates on
 * [[com.tw ter.ml.ap .DataRecord]]  nstances.
 *
 * For example, for a data record wh ch represents a T et, one could def ne a pred cate
 * wh ch c cks w t r t  T et conta ns a b nary feature represent ng t  presence of
 * an  mage. T  counter can t n be used to represent t  t  count of T ets w h
 *  mages processed.
 *
 * @param pred cate a pred cate wh ch gates t  counter
 * @param counter a summ ngb rd Counter  nstance
 */
case class DataRecordFeatureCounter(pred cate: DataRecord => Boolean, counter: Counter)

object DataRecordFeatureCounter {

  /**
   *  ncre nts t  counter  f t  record sat sf es t  pred cate
   *
   * @param recordCounter a data record counter
   * @param record a data record
   */
  def apply(recordCounter: DataRecordFeatureCounter, record: DataRecord): Un  =
     f (recordCounter.pred cate(record)) recordCounter.counter. ncr()

  /**
   * Def nes a feature counter w h a pred cate that  s always true
   *
   * @param counter a summ ngb rd Counter  nstance
   * @return a data record counter
   */
  def any(counter: Counter): DataRecordFeatureCounter =
    DataRecordFeatureCounter({ _: DataRecord => true }, counter)
}

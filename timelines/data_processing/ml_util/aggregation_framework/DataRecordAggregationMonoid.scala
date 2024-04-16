package com.tw ter.t  l nes.data_process ng.ml_ut l.aggregat on_fra work

 mport com.tw ter.algeb rd.Mono d
 mport com.tw ter.ml.ap ._
 mport com.tw ter.ml.ap .constant.SharedFeatures
 mport com.tw ter.ml.ap .ut l.SR chDataRecord
 mport scala.collect on.mutable
 mport com.tw ter.t  l nes.data_process ng.ml_ut l.aggregat on_fra work. tr cs.Aggregat on tr cCommon._

/**
 * Mono d to aggregate over DataRecord objects.
 *
 * @param aggregates Set of ''TypedAggregateGroup'' case classes*
 *                   to compute us ng t  mono d (see TypedAggregateGroup.scala)
 */
tra  DataRecordMono d extends Mono d[DataRecord] {

  val aggregates: Set[TypedAggregateGroup[_]]

  def zero(): DataRecord = new DataRecord

  /*
   * Add two datarecords us ng t  mono d.
   *
   * @param left Left datarecord to add
   * @param r ght R ght datarecord to add
   * @return Sum of t  two datarecords as a DataRecord
   */
  def plus(left: DataRecord, r ght: DataRecord): DataRecord = {
    val result = zero()
    aggregates.foreach(_.mutatePlus(result, left, r ght))
    val leftT  stamp = getT  stamp(left)
    val r ghtT  stamp = getT  stamp(r ght)
    SR chDataRecord(result).setFeatureValue(
      SharedFeatures.T MESTAMP,
      leftT  stamp.max(r ghtT  stamp)
    )
    result
  }
}

case class DataRecordAggregat onMono d(aggregates: Set[TypedAggregateGroup[_]])
    extends DataRecordMono d {

  pr vate def sumBuffer(buffer: mutable.ArrayBuffer[DataRecord]): Un  = {
    val bufferSum = zero()
    buffer.to erator.foreach { value =>
      val leftT  stamp = getT  stamp(bufferSum)
      val r ghtT  stamp = getT  stamp(value)
      aggregates.foreach(_.mutatePlus(bufferSum, bufferSum, value))
      SR chDataRecord(bufferSum).setFeatureValue(
        SharedFeatures.T MESTAMP,
        leftT  stamp.max(r ghtT  stamp)
      )
    }

    buffer.clear()
    buffer += bufferSum
  }

  /*
   * Eff c ent batc d aggregat on of datarecords us ng
   * t  mono d + a buffer, for performance.
   *
   * @param dataRecord er An  erator of datarecords to sum
   * @return A datarecord opt on conta n ng t  sum
   */
  overr de def sumOpt on(dataRecord er: TraversableOnce[DataRecord]): Opt on[DataRecord] = {
     f (dataRecord er. sEmpty) {
      None
    } else {
      var buffer = mutable.ArrayBuffer[DataRecord]()
      val BatchS ze = 1000

      dataRecord er.foreach { u =>
         f (buffer.s ze > BatchS ze) sumBuffer(buffer)
        buffer += u
      }

       f (buffer.s ze > 1) sumBuffer(buffer)
      So (buffer(0))
    }
  }
}

/*
 * T  class  s used w n t re  s no need to use sumBuffer funct onal y, as  n t  case of
 * onl ne aggregat on of datarecords w re us ng a buffer on a small number of datarecords
 * would add so  performance over ad.
 */
case class DataRecordAggregat onMono dNoBuffer(aggregates: Set[TypedAggregateGroup[_]])
    extends DataRecordMono d {}

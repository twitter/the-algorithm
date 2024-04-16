package com.tw ter.t  l nes.data_process ng.ml_ut l.aggregat on_fra work.scald ng.s ces

 mport com.tw ter.ml.ap .Da lySuff xFeatureS ce
 mport com.tw ter.ml.ap .DataRecord
 mport com.tw ter.ml.ap .F xedPathFeatureS ce
 mport com.tw ter.ml.ap .H lySuff xFeatureS ce
 mport com.tw ter.ml.ap .ut l.SR chDataRecord
 mport com.tw ter.scald ng._
 mport com.tw ter.scald ng_ nternal.dalv2.DAL
 mport com.tw ter.scald ng_ nternal.dalv2.remote_access.AllowCrossClusterSa DC
 mport com.tw ter.stateb rd.v2.thr ftscala.Env ron nt
 mport com.tw ter.summ ngb rd._
 mport com.tw ter.summ ngb rd.scald ng.Scald ng.p peFactoryExact
 mport com.tw ter.summ ngb rd.scald ng._
 mport com.tw ter.summ ngb rd_ nternal.s ces.S ceFactory
 mport com.tw ter.t  l nes.data_process ng.ml_ut l.aggregat on_fra work.Offl neAggregateS ce
 mport java.lang.{Long => JLong}

/*
 * Summ ngb rd offl ne HDFS s ce that reads from data records on HDFS.
 *
 * @param offl neS ce Underly ng offl ne s ce that conta ns
 *   all t  conf g  nfo to bu ld t  platform-spec f c (scald ng) s ce.
 */
case class Scald ngAggregateS ce(offl neS ce: Offl neAggregateS ce)
    extends S ceFactory[Scald ng, DataRecord] {

  val hdfsPath: Str ng = offl neS ce.scald ngHdfsPath.getOrElse("")
  val suff xType: Str ng = offl neS ce.scald ngSuff xType.getOrElse("da ly")
  val w hVal dat on: Boolean = offl neS ce.w hVal dat on
  def na : Str ng = offl neS ce.na 
  def descr pt on: Str ng =
    "Summ ngb rd offl ne s ce that reads from data records at: " + hdfsPath

   mpl c  val t  Extractor: T  Extractor[DataRecord] = T  Extractor((record: DataRecord) =>
    SR chDataRecord(record).getFeatureValue[JLong, JLong](offl neS ce.t  stampFeature))

  def getS ceForDateRange(dateRange: DateRange) = {
    suff xType match {
      case "da ly" => Da lySuff xFeatureS ce(hdfsPath)(dateRange).s ce
      case "h ly" => H lySuff xFeatureS ce(hdfsPath)(dateRange).s ce
      case "f xed_path" => F xedPathFeatureS ce(hdfsPath).s ce
      case "dal" =>
        offl neS ce.dalDataSet match {
          case So (dataset) =>
            DAL
              .read(dataset, dateRange)
              .w hRemoteReadPol cy(AllowCrossClusterSa DC)
              .w hEnv ron nt(Env ron nt.Prod)
              .toTypedS ce
          case _ =>
            throw new  llegalArgu ntExcept on(
              "cannot prov de an empty dataset w n def n ng DAL as t  suff x type"
            )
        }
    }
  }

  /**
   * T   thod  s s m lar to [[Scald ng.s ceFromMappable]] except that t  uses [[p peFactoryExact]]
   *  nstead of [[p peFactory]]. [[p peFactoryExact]] also  nvokes [[F leS ce.val dateTaps]] on t  s ce.
   * T  val dat on ensures t  presence of _SUCCESS f le before process ng. For more deta ls, please refer to
   * https://j ra.tw ter.b z/browse/TQ-10618
   */
  def s ceFromMappableW hVal dat on[T: T  Extractor: Man fest](
    factory: (DateRange) => Mappable[T]
  ): Producer[Scald ng, T] = {
    Producer.s ce[Scald ng, T](p peFactoryExact(factory))
  }

  def s ce: Producer[Scald ng, DataRecord] = {
     f (w hVal dat on)
      s ceFromMappableW hVal dat on(getS ceForDateRange)
    else
      Scald ng.s ceFromMappable(getS ceForDateRange)
  }
}

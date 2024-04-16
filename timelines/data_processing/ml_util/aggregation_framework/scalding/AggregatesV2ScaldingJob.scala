package com.tw ter.t  l nes.data_process ng.ml_ut l.aggregat on_fra work.scald ng

 mport com.tw ter.b ject on.thr ft.CompactThr ftCodec
 mport com.tw ter.b ject on.Codec
 mport com.tw ter.b ject on. nject on
 mport com.tw ter.ml.ap ._
 mport com.tw ter.ml.ap .constant.SharedFeatures.T MESTAMP
 mport com.tw ter.ml.ap .ut l.CompactDataRecordConverter
 mport com.tw ter.ml.ap .ut l.SR chDataRecord
 mport com.tw ter.scald ng.Args
 mport com.tw ter.scald ng_ nternal.dalv2.DALWr e.D
 mport com.tw ter.storehaus_ nternal.manhattan.ManhattanROConf g
 mport com.tw ter.summ ngb rd.batch.opt on.Reducers
 mport com.tw ter.summ ngb rd.batch.Batch D
 mport com.tw ter.summ ngb rd.batch.Batc r
 mport com.tw ter.summ ngb rd.batch.T  stamp
 mport com.tw ter.summ ngb rd.opt on._
 mport com.tw ter.summ ngb rd.scald ng.Scald ng
 mport com.tw ter.summ ngb rd.scald ng.batch.{Batc dStore => Scald ngBatc dStore}
 mport com.tw ter.summ ngb rd.Opt ons
 mport com.tw ter.summ ngb rd.Producer
 mport com.tw ter.summ ngb rd_ nternal.b ject on.BatchPa r mpl c s._
 mport com.tw ter.summ ngb rd_ nternal.runner.common.JobNa 
 mport com.tw ter.summ ngb rd_ nternal.runner.scald ng.Gener cRunner
 mport com.tw ter.summ ngb rd_ nternal.runner.scald ng.Scald ngConf g
 mport com.tw ter.summ ngb rd_ nternal.runner.scald ng.Stateb rdState
 mport com.tw ter.summ ngb rd_ nternal.dalv2.DAL
 mport com.tw ter.summ ngb rd_ nternal.runner.store_conf g._
 mport com.tw ter.t  l nes.data_process ng.ml_ut l.aggregat on_fra work._
 mport com.tw ter.t  l nes.data_process ng.ml_ut l.aggregat on_fra work.scald ng.s ces._
 mport job.AggregatesV2Job
 mport org.apac .hadoop.conf.Conf gurat on
/*
 * Offl ne scald ng vers on of summ ngb rd job to compute aggregates v2.
 * T   s loosely based on t  template created by sb-gen.
 * Extend t  tra   n y  own scald ng job, and overr de t  val
 * "aggregatesToCompute" w h y  own des red set of aggregates.
 */
tra  AggregatesV2Scald ngJob {
  val aggregatesToCompute: Set[TypedAggregateGroup[_]]

   mpl c  val aggregat onKey nject on:  nject on[Aggregat onKey, Array[Byte]] =
    Aggregat onKey nject on

   mpl c  val aggregat onKeyOrder ng: Aggregat onKeyOrder ng.type = Aggregat onKeyOrder ng

   mpl c  val dataRecordCodec:  nject on[DataRecord, Array[Byte]] = CompactThr ftCodec[DataRecord]

  pr vate  mpl c  val compactDataRecordCodec:  nject on[CompactDataRecord, Array[Byte]] =
    CompactThr ftCodec[CompactDataRecord]

  pr vate val compactDataRecordConverter = new CompactDataRecordConverter()

  def numReducers:  nt = -1

  /**
   * Funct on that maps from a log cal ''AggregateS ce''
   * to an underly ng phys cal s ce. T  phys cal s ce
   * for t  scald ng platform  s a Scald ngAggregateS ce.
   */
  def dataRecordS ceToScald ng(
    s ce: AggregateS ce
  ): Opt on[Producer[Scald ng, DataRecord]] = {
    s ce match {
      case offl neS ce: Offl neAggregateS ce =>
        So (Scald ngAggregateS ce(offl neS ce).s ce)
      case _ => None
    }
  }

  /**
   * Creates and returns a vers oned store us ng t  conf g para ters
   * w h a spec f c number of vers ons to keep, and wh ch can read from
   * t  most recent ava lable vers on on HDFS rat r than a spec f c
   * vers on number. T  store appl es a t  stamp correct on based on t 
   * number of days of aggregate data sk pped over at read t   to ensure
   * that sk pp ng data plays n cely w h halfL fe decay.
   *
   * @param conf g         spec fy ng t  Manhattan store para ters
   * @param vers onsToKeep number of old vers ons to keep
   */
  def getMostRecentLagCorrect ngVers onedStoreW hRetent on[
    Key: Codec: Order ng,
    Val nStore: Codec,
    Val n mory
  ](
    conf g: Offl neStoreOnlyConf g[ManhattanROConf g],
    vers onsToKeep:  nt,
    lagCorrector: (Val n mory, Long) => Val n mory,
    packer: Val n mory => Val nStore,
    unpacker: Val nStore => Val n mory
  ): Scald ngBatc dStore[Key, Val n mory] = {
    MostRecentLagCorrect ngVers onedStore[Key, Val nStore, Val n mory](
      conf g.offl ne.hdfsPath.toStr ng,
      packer = packer,
      unpacker = unpacker,
      vers onsToKeep = vers onsToKeep)(
       nject on.connect[(Key, (Batch D, Val nStore)), (Array[Byte], Array[Byte])],
      conf g.batc r,
       mpl c ly[Order ng[Key]],
      lagCorrector
    ).w h n  alBatch(conf g.batc r.batchOf(conf g.startT  .value))
  }

  def mutablyCorrectDataRecordT  stamp(
    record: DataRecord,
    lagToCorrectM ll s: Long
  ): DataRecord = {
    val r chRecord = SR chDataRecord(record)
     f (r chRecord.hasFeature(T MESTAMP)) {
      val t  stamp = r chRecord.getFeatureValue(T MESTAMP).toLong
      r chRecord.setFeatureValue(T MESTAMP, t  stamp + lagToCorrectM ll s)
    }
    record
  }

  /**
   * Funct on that maps from a log cal ''AggregateStore''
   * to an underly ng phys cal store. T  phys cal store for
   * scald ng  s a HDFS Vers onedKeyValS ce dataset.
   */
  def aggregateStoreToScald ng(
    store: AggregateStore
  ): Opt on[Scald ng#Store[Aggregat onKey, DataRecord]] = {
    store match {
      case offl neStore: Offl neAggregateDataRecordStore =>
        So (
          getMostRecentLagCorrect ngVers onedStoreW hRetent on[
            Aggregat onKey,
            DataRecord,
            DataRecord](
            offl neStore,
            vers onsToKeep = offl neStore.batc sToKeep,
            lagCorrector = mutablyCorrectDataRecordT  stamp,
            packer =  nject on. dent y[DataRecord],
            unpacker =  nject on. dent y[DataRecord]
          )
        )
      case offl neStore: Offl neAggregateDataRecordStoreW hDAL =>
        So (
          DAL.vers onedKeyValStore[Aggregat onKey, DataRecord](
            dataset = offl neStore.dalDataset,
            pathLa t = D.Suff x(offl neStore.offl ne.hdfsPath.toStr ng),
            batc r = offl neStore.batc r,
            maybeStartT   = So (offl neStore.startT  ),
            maxErrors = offl neStore.maxKvS ceFa lures
          ))
      case _ => None
    }
  }

  def generate(args: Args): Scald ngConf g = new Scald ngConf g {
    val jobNa  = JobNa (args("job_na "))

    /*
     * Add reg strars for ch ll ser al zat on for user-def ned types.
     *   use t  default: an empty L st().
     */
    overr de def reg strars = L st()

    /* Use transformConf g to set Hadoop opt ons. */
    overr de def transformConf g(conf g: Map[Str ng, AnyRef]): Map[Str ng, AnyRef] =
      super.transformConf g(conf g) ++ Map(
        "mapreduce.output.f leoutputformat.compress" -> "true",
        "mapreduce.output.f leoutputformat.compress.codec" -> "com.hadoop.compress on.lzo.LzoCodec",
        "mapreduce.output.f leoutputformat.compress.type" -> "BLOCK"
      )

    /*
     * Use getNa dOpt ons to set Summ ngb rd runt   opt ons
     * T  opt ons   set are:
     * 1) Set mono d to non-commutat ve to d sable map-s de
     * aggregat on and force all aggregat on to reducers (prov des a 20% speedup)
     */
    overr de def getNa dOpt ons: Map[Str ng, Opt ons] = Map(
      "DEFAULT" -> Opt ons()
        .set(Mono d sCommutat ve(false))
        .set(Reducers(numReducers))
    )

     mpl c  val batc r: Batc r = Batc r.ofH s(24)

    /* State  mple ntat on that uses Stateb rd (go/stateb rd) to track t  batc s processed. */
    def getWa  ngState(hadoopConf g: Conf gurat on, startDate: Opt on[T  stamp], batc s:  nt) =
      Stateb rdState(
        jobNa ,
        startDate,
        batc s,
        args.opt onal("stateb rd_serv ce_dest nat on"),
        args.opt onal("stateb rd_cl ent_ d_na ")
      )(batc r)

    val s ceNa F lter: Opt on[Set[Str ng]] =
      args.opt onal(" nput_s ces").map(_.spl (",").toSet)
    val storeNa F lter: Opt on[Set[Str ng]] =
      args.opt onal("output_stores").map(_.spl (",").toSet)

    val f lteredAggregates =
      AggregatesV2Job.f lterAggregates(
        aggregates = aggregatesToCompute,
        s ceNa s = s ceNa F lter,
        storeNa s = storeNa F lter
      )

    overr de val graph =
      AggregatesV2Job.generateJobGraph[Scald ng](
        f lteredAggregates,
        dataRecordS ceToScald ng,
        aggregateStoreToScald ng
      )(DataRecordAggregat onMono d(f lteredAggregates))
  }
  def ma n(args: Array[Str ng]): Un  = {
    Gener cRunner(args, generate(_))

  }
}

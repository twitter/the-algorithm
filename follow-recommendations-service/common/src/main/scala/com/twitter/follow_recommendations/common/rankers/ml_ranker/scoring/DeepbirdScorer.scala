package com.tw ter.follow_recom ndat ons.common.rankers.ml_ranker.scor ng

 mport com.tw ter.cortex.deepb rd.thr ftjava.Deepb rdPred ct onServ ce
 mport com.tw ter.cortex.deepb rd.thr ftjava.ModelSelector
 mport com.tw ter.f nagle.stats.Stat
 mport com.tw ter.f nagle.stats.StatsRece ver
 mport com.tw ter.follow_recom ndat ons.common.models.Cand dateUser
 mport com.tw ter.follow_recom ndat ons.common.models.HasDebugOpt ons
 mport com.tw ter.follow_recom ndat ons.common.models.HasD splayLocat on
 mport com.tw ter.follow_recom ndat ons.common.models.Score
 mport com.tw ter.ml.ap .DataRecord
 mport com.tw ter.ml.ap .Feature
 mport com.tw ter.ml.ap .R chDataRecord
 mport com.tw ter.ml.pred ct on_serv ce.{BatchPred ct onRequest => JBatchPred ct onRequest}
 mport com.tw ter.product_m xer.core.model.marshall ng.request.HasCl entContext
 mport com.tw ter.st ch.St ch
 mport com.tw ter.t  l nes.conf gap .HasParams
 mport com.tw ter.ut l.Future
 mport com.tw ter.ut l.T  outExcept on
 mport scala.collect on.JavaConvers ons._
 mport scala.collect on.JavaConverters._

/**
 * Gener c tra  that  mple nts t  scor ng g ven a deepb rdCl ent
 * To test out a new model, create a scorer extend ng t  tra , overr de t  modelNa  and  nject t  scorer
 */
tra  Deepb rdScorer extends Scorer {
  def modelNa : Str ng
  def pred ct onFeature: Feature.Cont nuous
  // Set a default batchS ze of 100 w n mak ng model pred ct on calls to t  Deepb rd V2 pred ct on server
  def batchS ze:  nt = 100
  def deepb rdCl ent: Deepb rdPred ct onServ ce.Serv ceToCl ent
  def baseStats: StatsRece ver

  def modelSelector: ModelSelector = new ModelSelector().set d(modelNa )
  def stats: StatsRece ver = baseStats.scope(t .getClass.getS mpleNa ).scope(modelNa )

  pr vate def requestCount = stats.counter("requests")
  pr vate def emptyRequestCount = stats.counter("empty_requests")
  pr vate def successCount = stats.counter("success")
  pr vate def fa lureCount = stats.counter("fa lures")
  pr vate def  nputRecordsStat = stats.stat(" nput_records")
  pr vate def outputRecordsStat = stats.stat("output_records")

  // Counters for track ng batch-pred ct on stat st cs w n mak ng DBv2 pred ct on calls
  //
  // numBatchRequests tracks t  number of batch pred ct on requests made to DBv2 pred ct on servers
  pr vate def numBatchRequests = stats.counter("batc s")
  // numEmptyBatchRequests tracks t  number of batch pred ct on requests made to DBv2 pred ct on servers
  // that had an empty  nput DataRecord
  pr vate def numEmptyBatchRequests = stats.counter("empty_batc s")
  // numT  dOutBatchRequests tracks t  number of batch pred ct on requests made to DBv2 pred ct on servers
  // that had t  d-out
  pr vate def numT  dOutBatchRequests = stats.counter("t  out_batc s")

  pr vate def batchPred ct onLatency = stats.stat("batch_pred ct on_latency")
  pr vate def pred ct onLatency = stats.stat("pred ct on_latency")

  pr vate def numEmptyModelPred ct ons = stats.counter("empty_model_pred ct ons")
  pr vate def numNonEmptyModelPred ct ons = stats.counter("non_empty_model_pred ct ons")

  pr vate val DefaultPred ct onScore = 0.0

  /**
   * NOTE: For  nstances of [[Deepb rdScorer]] t  funct on SHOULD NOT be used.
   * Please use [[score(records: Seq[DataRecord])]]  nstead.
   */
  @Deprecated
  def score(
    target: HasCl entContext w h HasParams w h HasD splayLocat on w h HasDebugOpt ons,
    cand dates: Seq[Cand dateUser]
  ): Seq[Opt on[Score]] =
    throw new UnsupportedOperat onExcept on(
      "For  nstances of Deepb rdScorer t  operat on  s not def ned. Please use " +
        "`def score(records: Seq[DataRecord]): St ch[Seq[Score]]` " +
        " nstead.")

  overr de def score(records: Seq[DataRecord]): St ch[Seq[Score]] = {
    requestCount. ncr()
     f (records. sEmpty) {
      emptyRequestCount. ncr()
      St ch.N l
    } else {
       nputRecordsStat.add(records.s ze)
      St ch.callFuture(
        batchPred ct(records, batchS ze)
          .map { recordL st =>
            val scores = recordL st.map { record =>
              Score(
                value = record.getOrElse(DefaultPred ct onScore),
                ranker d = So ( d),
                scoreType = scoreType)
            }
            outputRecordsStat.add(scores.s ze)
            scores
          }.onSuccess(_ => successCount. ncr())
          .onFa lure(_ => fa lureCount. ncr()))
    }
  }

  def batchPred ct(
    dataRecords: Seq[DataRecord],
    batchS ze:  nt
  ): Future[Seq[Opt on[Double]]] = {
    Stat
      .t  Future(pred ct onLatency) {
        val batc dDataRecords = dataRecords.grouped(batchS ze).toSeq
        numBatchRequests. ncr(batc dDataRecords.s ze)
        Future
          .collect(batc dDataRecords.map(batch => pred ct(batch)))
          .map(res => res.reduce(_ ++ _))
      }
  }

  def pred ct(dataRecords: Seq[DataRecord]): Future[Seq[Opt on[Double]]] = {
    Stat
      .t  Future(batchPred ct onLatency) {
         f (dataRecords. sEmpty) {
          numEmptyBatchRequests. ncr()
          Future.N l
        } else {
          deepb rdCl ent
            .batchPred ctFromModel(new JBatchPred ct onRequest(dataRecords.asJava), modelSelector)
            .map { response =>
              response.pred ct ons.toSeq.map { pred ct on =>
                val pred ct onFeatureOpt on = Opt on(
                  new R chDataRecord(pred ct on).getFeatureValue(pred ct onFeature)
                )
                pred ct onFeatureOpt on match {
                  case So (pred ct onValue) =>
                    numNonEmptyModelPred ct ons. ncr()
                    Opt on(pred ct onValue.toDouble)
                  case None =>
                    numEmptyModelPred ct ons. ncr()
                    Opt on(DefaultPred ct onScore)
                }
              }
            }
            .rescue {
              case e: T  outExcept on => // DBv2 pred ct on calls that t  d out
                numT  dOutBatchRequests. ncr()
                stats.counter(e.getClass.getS mpleNa ). ncr()
                Future.value(dataRecords.map(_ => Opt on(DefaultPred ct onScore)))
              case e: Except on => // ot r gener c DBv2 pred ct on call fa lures
                stats.counter(e.getClass.getS mpleNa ). ncr()
                Future.value(dataRecords.map(_ => Opt on(DefaultPred ct onScore)))
            }
        }
      }
  }
}

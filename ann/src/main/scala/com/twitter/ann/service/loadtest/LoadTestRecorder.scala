package com.tw ter.ann.serv ce.loadtest

 mport com.google.common.ut l.concurrent.Atom cDouble
 mport com.tw ter.f nagle.stats.{ tr csBucketed togram, Snapshot, StatsRece ver}
 mport com.tw ter.ut l.{Durat on, Stopwatch}
 mport java.ut l.concurrent.atom c.{Atom c nteger, Atom cReference}

tra  LoadTestQueryRecorder[T] {
  def recordQueryResult(
    trueNe ghbors: Seq[T],
    foundNe ghbors: Seq[T],
    queryLatency: Durat on
  ): Un 
}

case class LoadTestQueryResults(
  numResults:  nt,
  top1Recall: Float,
  top10Recall: Opt on[Float],
  overallRecall: Float)

pr vate object LoadTestQueryRecorder {
  def recordQueryResult[T](
    trueNe ghbors: Seq[T],
    foundNe ghbors: Seq[T]
  ): LoadTestQueryResults = {
    // record number of results returned
    val numResults = foundNe ghbors.s ze
     f (trueNe ghbors. sEmpty) {
      LoadTestQueryResults(
        numResults,
        0f,
        Opt on.empty,
        0f
      )
    } else {
      // record top 1, top 10 and overall recall
      // recall  re  s computed as number of true ne ghbors w h n t  returned po nts set
      // d v des by t  number of requ red ne ghbors
      val top1Recall = foundNe ghbors. ntersect(Seq(trueNe ghbors. ad)).s ze
      val top10Recall =  f (numResults >= 10 && trueNe ghbors.s ze >= 10) {
        So (
          trueNe ghbors.take(10). ntersect(foundNe ghbors).s ze.toFloat / 10
        )
      } else {
        None
      }

      val overallRecall = trueNe ghbors
        .take(foundNe ghbors.s ze). ntersect(foundNe ghbors).s ze.toFloat /
        Math.m n(foundNe ghbors.s ze, trueNe ghbors.s ze)

      LoadTestQueryResults(
        numResults,
        top1Recall,
        top10Recall,
        overallRecall
      )
    }
  }
}

class StatsLoadTestQueryRecorder[T](
  statsRece ver: StatsRece ver)
    extends LoadTestQueryRecorder[T] {
  pr vate[t ] val numResultsStats = statsRece ver.stat("number_of_results")
  pr vate[t ] val recallStats = statsRece ver.stat("recall")
  pr vate[t ] val top1RecallStats = statsRece ver.stat("top_1_recall")
  pr vate[t ] val top10RecallStats = statsRece ver.stat("top_10_recall")
  pr vate[t ] val queryLatencyM crosStats = statsRece ver.stat("query_latency_m cros")

  overr de def recordQueryResult(
    trueNe ghbors: Seq[T],
    foundNe ghbors: Seq[T],
    queryLatency: Durat on
  ): Un  = {
    val results = LoadTestQueryRecorder.recordQueryResult(trueNe ghbors, foundNe ghbors)
    numResultsStats.add(results.numResults)
    recallStats.add(results.overallRecall * 100)
    results.top10Recall.foreach { top10Recall =>
      top10RecallStats.add(top10Recall * 100)
    }
    top1RecallStats.add(results.top1Recall * 100)
    queryLatencyM crosStats.add(queryLatency. nM croseconds)
  }
}

tra  LoadTestBu ldRecorder {
  def record ndexCreat on(
     ndexS ze:  nt,
     ndexLatency: Durat on,
    toQueryableLatency: Durat on
  ): Un 
}

class StatsLoadTestBu ldRecorder(
  statsRece ver: StatsRece ver)
    extends LoadTestBu ldRecorder {
  pr vate[t ] val  ndexLatencyGauge = statsRece ver.addGauge(" ndex_latency_ms")(_)
  pr vate[t ] val  ndexS zeGauge = statsRece ver.addGauge(" ndex_s ze")(_)
  pr vate[t ] val toQueryableGauge = statsRece ver.addGauge("to_queryable_latency_ms")(_)

  overr de def record ndexCreat on(
     ndexS ze:  nt,
     ndexLatency: Durat on,
    toQueryableLatency: Durat on
  ): Un  = {
     ndexLatencyGauge( ndexLatency. nM ll s)
     ndexS zeGauge( ndexS ze)
    toQueryableGauge(toQueryableLatency. nM ll s)
  }
}

class QueryRecorderSnapshot(snapshot: Snapshot) {
  def avgQueryLatencyM cros: Double = snapshot.average
  def p50QueryLatencyM cros: Double =
    snapshot.percent les.f nd(_.quant le == .5).get.value
  def p90QueryLatencyM cros: Double =
    snapshot.percent les.f nd(_.quant le == .9).get.value
  def p99QueryLatencyM cros: Double =
    snapshot.percent les.f nd(_.quant le == .99).get.value
}

class  n moryLoadTestQueryRecorder[T](
  //   have to spec fy a na  of t   togram even though    s not used
  // Use latch per od of bottom.   w ll compute a new snapshot every t     call computeSnapshot
  pr vate[t ] val latency togram:  tr csBucketed togram =
    new  tr csBucketed togram("latency togram", latchPer od = Durat on.Bottom))
    extends LoadTestQueryRecorder[T] {
  pr vate[t ] val counter = new Atom c nteger(0)
  pr vate[t ] val countMoreThan10Results = new Atom c nteger(0)
  pr vate[t ] val recallSum = new Atom cDouble(0.0)
  pr vate[t ] val top1RecallSum = new Atom cDouble(0.0)
  pr vate[t ] val top10RecallSum = new Atom cDouble(0.0)
  pr vate[t ] val elapsedT  Fun = new Atom cReference[(Stopwatch.Elapsed, Durat on)]()
  pr vate[t ] val elapsedT   = new Atom cReference[Durat on](Durat on.Zero)

  /**
   * Compute a snapshot of what happened bet en t  t   that t  was called and t  prev ous t  
   *   was called.
   * @return
   */
  def computeSnapshot(): QueryRecorderSnapshot = {
    new QueryRecorderSnapshot(latency togram.snapshot())
  }

  def recall: Double =
     f (counter.get() != 0) {
      recallSum.get * 100 / counter.get()
    } else { 0 }

  def top1Recall: Double =
     f (counter.get() != 0) {
      top1RecallSum.get * 100 / counter.get()
    } else { 0 }
  def top10Recall: Double =
     f (countMoreThan10Results.get() != 0) {
      top10RecallSum.get * 100 / countMoreThan10Results.get()
    } else { 0 }

  def avgRPS: Double =
     f (elapsedT  .get() != Durat on.Zero) {
      (counter.get().toDouble * 1e9) / elapsedT  .get(). nNanoseconds
    } else { 0 }

  overr de def recordQueryResult(
    trueNe ghbors: Seq[T],
    foundNe ghbors: Seq[T],
    queryLatency: Durat on
  ): Un  = {
    elapsedT  Fun.compareAndSet(null, (Stopwatch.start(), queryLatency))
    val results = LoadTestQueryRecorder.recordQueryResult(trueNe ghbors, foundNe ghbors)
    top1RecallSum.addAndGet(results.top1Recall)
    results.top10Recall.foreach { top10Recall =>
      top10RecallSum.addAndGet(top10Recall)
      countMoreThan10Results. ncre ntAndGet()
    }
    recallSum.addAndGet(results.overallRecall)
    latency togram.add(queryLatency. nM croseconds)
    counter. ncre ntAndGet()
    // Requests are assu d to have started around t  t   t   of t  f rst t   record was called
    // plus t  t     took for that query to hhave completed.
    val (elapsedS nceF rstCall, f rstQueryLatency) = elapsedT  Fun.get()
    val durat onSoFar = elapsedS nceF rstCall() + f rstQueryLatency
    elapsedT  .set(durat onSoFar)
  }
}

class  n moryLoadTestBu ldRecorder extends LoadTestBu ldRecorder {
  var  ndexLatency: Durat on = Durat on.Zero
  var  ndexS ze:  nt = 0
  var toQueryableLatency: Durat on = Durat on.Zero

  overr de def record ndexCreat on(
    s ze:  nt,
     ndexLatencyArg: Durat on,
    toQueryableLatencyArg: Durat on
  ): Un  = {
     ndexLatency =  ndexLatencyArg
     ndexS ze = s ze
    toQueryableLatency = toQueryableLatencyArg
  }
}

/**
 * A LoadTestRecorder that be composed by ot r recorders
 */
class ComposedLoadTestQueryRecorder[T](
  recorders: Seq[LoadTestQueryRecorder[T]])
    extends LoadTestQueryRecorder[T] {
  overr de def recordQueryResult(
    trueNe ghbors: Seq[T],
    foundNe ghbors: Seq[T],
    queryLatency: Durat on
  ): Un  = recorders.foreach {
    _.recordQueryResult(trueNe ghbors, foundNe ghbors, queryLatency)
  }
}

/**
 * A LoadTestRecorder that be composed by ot r recorders
 */
class ComposedLoadTestBu ldRecorder(
  recorders: Seq[LoadTestBu ldRecorder])
    extends LoadTestBu ldRecorder {
  overr de def record ndexCreat on(
     ndexS ze:  nt,
     ndexLatency: Durat on,
    toQueryableLatency: Durat on
  ): Un  = recorders.foreach { _.record ndexCreat on( ndexS ze,  ndexLatency, toQueryableLatency) }
}

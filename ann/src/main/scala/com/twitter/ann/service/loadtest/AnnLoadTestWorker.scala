package com.tw ter.ann.serv ce.loadtest

 mport com.google.common.annotat ons.V s bleForTest ng
 mport com.tw ter.ann.common.D stance
 mport com.tw ter.ann.common.Queryable
 mport com.tw ter.ann.common.Runt  Params
 mport com.tw ter.concurrent.Async ter
 mport com.tw ter.concurrent.AsyncStream
 mport com.tw ter.f nagle.ut l.DefaultT  r
 mport com.tw ter.logg ng.Logger
 mport com.tw ter.ut l.Durat on
 mport com.tw ter.ut l.Future
 mport com.tw ter.ut l.Stopwatch
 mport com.tw ter.ut l.T  r
 mport com.tw ter.ut l.Try
 mport java.ut l.concurrent.atom c.Atom c nteger

object QueryT  Conf gurat on {
  val Result ader =
    "params\tnumNe ghbors\trecall@1\trecall@10\trecall\tavgLatencyM cros\tp50LatencyM cros\tp90LatencyM cros\tp99LatencyM cros\tavgRPS"
}

case class QueryT  Conf gurat on[T, P <: Runt  Params](
  recorder: LoadTestQueryRecorder[T],
  param: P,
  numberOfNe ghbors:  nt,
  pr vate val results:  n moryLoadTestQueryRecorder[T]) {
  overr de def toStr ng: Str ng =
    s"QueryT  Conf gurat on(param = $param, numberOfNe ghbors = $numberOfNe ghbors)"

  def pr ntResults: Str ng = {
    val snapshot = results.computeSnapshot()
    s"$param\t$numberOfNe ghbors\t${results.top1Recall}\t${results.top10Recall}\t${results.recall}\t${snapshot.avgQueryLatencyM cros}\t${snapshot.p50QueryLatencyM cros}\t${snapshot.p90QueryLatencyM cros}\t${snapshot.p99QueryLatencyM cros}\t${results.avgRPS}"
  }
}

/**
 * Bas c worker for ANN benchmark, send query w h conf gured QPS and record results
 */
class AnnLoadTestWorker(
  t  r: T  r = DefaultT  r) {
  pr vate val logger = Logger()

  /**
   * @param quer es L st of tuple of query embedd ng and correspond ng l st of truth set of  ds assoc ated w h t  embedd ng
   * @param qps t  max mum number of request per second to send to t  queryable. Note that  f  
   *            do not set t  concurrency level h gh enough   may not be able to ach eve t .
   * @param durat on         how long to perform t  load test.
   * @param conf gurat on    Query conf gurat on encapsulat ng runt   params and recorder.
   * @param concurrencyLevel T  max mum number of concurrent requests to t  queryable at a t  .
   *                         Note that   may not be able to ach eve t  number of concurrent
   *                         requests  f   do not have t  QPS set h gh enough.
   *
   * @return a Future that completes w n t  load test  s over.   conta ns t  number of requests
   *         sent.
   */
  def runW hQps[T, P <: Runt  Params, D <: D stance[D]](
    queryable: Queryable[T, P, D],
    quer es: Seq[Query[T]],
    qps:  nt,
    durat on: Durat on,
    conf gurat on: QueryT  Conf gurat on[T, P],
    concurrencyLevel:  nt
  ): Future[ nt] = {
    val elapsed = Stopwatch.start()
    val atom c nteger = new Atom c nteger(0)
    val fullStream = Stream.cont nually {
       f (elapsed() <= durat on) {
        logger. fDebug(s"runn ng w h conf g: $conf gurat on")
        So (atom c nteger.getAnd ncre nt() % quer es.s ze)
      } else {
        logger. fDebug(s"stopp ng w h conf g: $conf gurat on")
        None
      }
    }
    val l m edStream = fullStream.takeWh le(_. sDef ned).flatten
    // at most   w ll have concurrencyLevel concurrent requests. So   should never have more than
    // concurrency level wa ers.
    val async ter = Async ter.perSecond(qps, concurrencyLevel)(t  r)

    Future.Un .before {
      AsyncStream
        .fromSeq(l m edStream).mapConcurrent(concurrencyLevel) {  ndex =>
          async ter.awa (1).flatMap { _ =>
            performQuery(conf gurat on, queryable, quer es( ndex))
          }
        }.s ze
    }
  }

  @V s bleForTest ng
  pr vate[loadtest] def performQuery[T, P <: Runt  Params, D <: D stance[D]](
    conf gurat on: QueryT  Conf gurat on[T, P],
    queryable: Queryable[T, P, D],
    query: Query[T]
  ): Future[Try[Un ]] = {
    val elapsed = Stopwatch.start()
    queryable
      .query(query.embedd ng, conf gurat on.numberOfNe ghbors, conf gurat on.param)
      .onSuccess { res: L st[T] =>
        // underneath LoadTestRecorder w ll record results for load test
        // knnMap should be truncated to be sa  s ze as query result
        conf gurat on.recorder.recordQueryResult(
          query.trueNe ghb s,
          res,
          elapsed.apply()
        )
        logger. fDebug(s"Successful query for $query")
      }
      .onFa lure { e =>
        logger.error(s"Fa led query for $query: " + e)
      }
      .un 
      .l ftToTry
  }
}

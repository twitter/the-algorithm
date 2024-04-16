package com.tw ter.cr_m xer.logg ng

 mport com.tw ter.cr_m xer.logg ng.Scr beLoggerUt ls._
 mport com.tw ter.cr_m xer.model.UtegT etCand dateGeneratorQuery
 mport com.tw ter.cr_m xer.model.ModuleNa s
 mport com.tw ter.cr_m xer.model.T etW hScoreAndSoc alProof
 mport com.tw ter.cr_m xer.param.dec der.CrM xerDec der
 mport com.tw ter.cr_m xer.param.dec der.Dec derConstants
 mport com.tw ter.cr_m xer.thr ftscala.UtegT etRequest
 mport com.tw ter.cr_m xer.thr ftscala.UtegT etResponse
 mport com.tw ter.cr_m xer.thr ftscala.FetchCand datesResult
 mport com.tw ter.cr_m xer.thr ftscala.GetUtegT etsScr be
 mport com.tw ter.cr_m xer.thr ftscala.Performance tr cs
 mport com.tw ter.cr_m xer.thr ftscala.UtegT etResult
 mport com.tw ter.cr_m xer.thr ftscala.UtegT etTopLevelAp Result
 mport com.tw ter.cr_m xer.thr ftscala.T etCand dateW h tadata
 mport com.tw ter.f nagle.stats.StatsRece ver
 mport com.tw ter.f nagle.trac ng.Trace
 mport com.tw ter.logg ng.Logger
 mport com.tw ter.s mclusters_v2.common.User d
 mport com.tw ter.ut l.Future
 mport com.tw ter.ut l.Stopwatch
 mport javax. nject. nject
 mport javax. nject.Na d
 mport javax. nject.S ngleton

@S ngleton
case class UtegT etScr beLogger @ nject() (
  dec der: CrM xerDec der,
  statsRece ver: StatsRece ver,
  @Na d(ModuleNa s.UtegT etsLogger) utegT etScr beLogger: Logger) {

  pr vate val scopedStats = statsRece ver.scope("UtegT etScr beLogger")
  pr vate val topLevelAp Stats = scopedStats.scope("TopLevelAp ")
  pr vate val upperFunnelsStats = scopedStats.scope("UpperFunnels")

  def scr be n  alCand dates(
    query: UtegT etCand dateGeneratorQuery,
    getResultFn: => Future[Seq[T etW hScoreAndSoc alProof]]
  ): Future[Seq[T etW hScoreAndSoc alProof]] = {
    scr beResultsAndPerformance tr cs(
      Scr be tadata.from(query),
      getResultFn,
      convertToResultFn = convertFetchCand datesResult
    )
  }

  /**
   * Scr be Top Level AP  Request / Response and performance  tr cs
   * for t  GetUtegT etRecom ndat ons() endpo nt.
   */
  def scr beGetUtegT etRecom ndat ons(
    request: UtegT etRequest,
    startT  : Long,
    scr be tadata: Scr be tadata,
    getResultFn: => Future[UtegT etResponse]
  ): Future[UtegT etResponse] = {
    val t  r = Stopwatch.start()
    getResultFn.onSuccess { response =>
       f (dec der. sAva lableFor d(
          scr be tadata.user d,
          Dec derConstants.upperFunnelPerStepScr beRate)) {
        topLevelAp Stats.counter(scr be tadata.product.or g nalNa ). ncr()
        val latencyMs = t  r(). nM ll seconds
        val result = convertTopLevelAP Result(request, response, startT  )
        val trace d = Trace. d.trace d.toLong
        val scr beMsg =
          bu ldScr be ssage(result, scr be tadata, latencyMs, trace d)

        scr beResult(scr beMsg)
      }
    }
  }

  pr vate def convertTopLevelAP Result(
    request: UtegT etRequest,
    response: UtegT etResponse,
    startT  : Long
  ): UtegT etResult = {
    UtegT etResult.UtegT etTopLevelAp Result(
      UtegT etTopLevelAp Result(
        t  stamp = startT  ,
        request = request,
        response = response
      ))
  }

  pr vate def bu ldScr be ssage(
    utegT etResult: UtegT etResult,
    scr be tadata: Scr be tadata,
    latencyMs: Long,
    trace d: Long
  ): GetUtegT etsScr be = {
    GetUtegT etsScr be(
      uu d = scr be tadata.requestUU D,
      user d = scr be tadata.user d,
      utegT etResult = utegT etResult,
      trace d = So (trace d),
      performance tr cs = So (Performance tr cs(So (latencyMs))),
       mpressedBuckets = get mpressedBuckets(scopedStats)
    )
  }

  pr vate def scr beResult(
    scr beMsg: GetUtegT etsScr be
  ): Un  = {
    publ sh(logger = utegT etScr beLogger, codec = GetUtegT etsScr be,  ssage = scr beMsg)
  }

  pr vate def convertFetchCand datesResult(
    cand dates: Seq[T etW hScoreAndSoc alProof],
    requestUser d: User d
  ): UtegT etResult = {
    val t etCand datesW h tadata = cand dates.map { cand date =>
      T etCand dateW h tadata(
        t et d = cand date.t et d,
        cand dateGenerat onKey = None
      ) // do not hydrate cand dateGenerat onKey to save cost
    }
    UtegT etResult.FetchCand datesResult(FetchCand datesResult(So (t etCand datesW h tadata)))
  }

  /**
   * Scr be Per-step  nter d ate results and performance  tr cs
   * for each step: fetch cand dates, f lters.
   */
  pr vate def scr beResultsAndPerformance tr cs[T](
    scr be tadata: Scr be tadata,
    getResultFn: => Future[T],
    convertToResultFn: (T, User d) => UtegT etResult
  ): Future[T] = {
    val t  r = Stopwatch.start()
    getResultFn.onSuccess {  nput =>
       f (dec der. sAva lableFor d(
          scr be tadata.user d,
          Dec derConstants.upperFunnelPerStepScr beRate)) {
        upperFunnelsStats.counter(scr be tadata.product.or g nalNa ). ncr()
        val latencyMs = t  r(). nM ll seconds
        val result = convertToResultFn( nput, scr be tadata.user d)
        val trace d = Trace. d.trace d.toLong
        val scr beMsg =
          bu ldScr be ssage(result, scr be tadata, latencyMs, trace d)
        scr beResult(scr beMsg)
      }
    }
  }
}

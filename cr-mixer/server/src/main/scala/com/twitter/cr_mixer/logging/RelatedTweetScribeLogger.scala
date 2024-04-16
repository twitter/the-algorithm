package com.tw ter.cr_m xer.logg ng

 mport com.tw ter.cr_m xer.model.RelatedT etCand dateGeneratorQuery
 mport com.tw ter.cr_m xer.model. n  alCand date
 mport com.tw ter.cr_m xer.model.ModuleNa s
 mport com.tw ter.cr_m xer.logg ng.Scr beLoggerUt ls._
 mport com.tw ter.cr_m xer.param.dec der.CrM xerDec der
 mport com.tw ter.cr_m xer.param.dec der.Dec derConstants
 mport com.tw ter.cr_m xer.thr ftscala.FetchCand datesResult
 mport com.tw ter.cr_m xer.thr ftscala.GetRelatedT etsScr be
 mport com.tw ter.cr_m xer.thr ftscala.Performance tr cs
 mport com.tw ter.cr_m xer.thr ftscala.PreRankF lterResult
 mport com.tw ter.cr_m xer.thr ftscala.RelatedT etRequest
 mport com.tw ter.cr_m xer.thr ftscala.RelatedT etResponse
 mport com.tw ter.cr_m xer.thr ftscala.RelatedT etResult
 mport com.tw ter.cr_m xer.thr ftscala.RelatedT etTopLevelAp Result
 mport com.tw ter.cr_m xer.thr ftscala.T etCand dateW h tadata
 mport com.tw ter.cr_m xer.ut l.Cand dateGenerat onKeyUt l
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
case class RelatedT etScr beLogger @ nject() (
  dec der: CrM xerDec der,
  statsRece ver: StatsRece ver,
  @Na d(ModuleNa s.RelatedT etsLogger) relatedT etsScr beLogger: Logger) {

  pr vate val scopedStats = statsRece ver.scope("RelatedT etsScr beLogger")
  pr vate val topLevelAp Stats = scopedStats.scope("TopLevelAp ")
  pr vate val topLevelAp NoUser dStats = scopedStats.scope("TopLevelAp NoUser d")
  pr vate val upperFunnelsStats = scopedStats.scope("UpperFunnels")
  pr vate val upperFunnelsNoUser dStats = scopedStats.scope("UpperFunnelsNoUser d")

  def scr be n  alCand dates(
    query: RelatedT etCand dateGeneratorQuery,
    getResultFn: => Future[Seq[Seq[ n  alCand date]]]
  ): Future[Seq[Seq[ n  alCand date]]] = {
    scr beResultsAndPerformance tr cs(
      RelatedT etScr be tadata.from(query),
      getResultFn,
      convertToResultFn = convertFetchCand datesResult
    )
  }

  def scr bePreRankF lterCand dates(
    query: RelatedT etCand dateGeneratorQuery,
    getResultFn: => Future[Seq[Seq[ n  alCand date]]]
  ): Future[Seq[Seq[ n  alCand date]]] = {
    scr beResultsAndPerformance tr cs(
      RelatedT etScr be tadata.from(query),
      getResultFn,
      convertToResultFn = convertPreRankF lterResult
    )
  }

  /**
   * Scr be Top Level AP  Request / Response and performance  tr cs
   * for t  getRelatedT ets endpo nt.
   */
  def scr beGetRelatedT ets(
    request: RelatedT etRequest,
    startT  : Long,
    relatedT etScr be tadata: RelatedT etScr be tadata,
    getResultFn: => Future[RelatedT etResponse]
  ): Future[RelatedT etResponse] = {
    val t  r = Stopwatch.start()
    getResultFn.onSuccess { response =>
      relatedT etScr be tadata.cl entContext.user d match {
        case So (user d) =>
           f (dec der. sAva lableFor d(user d, Dec derConstants.upperFunnelPerStepScr beRate)) {
            topLevelAp Stats.counter(relatedT etScr be tadata.product.or g nalNa ). ncr()
            val latencyMs = t  r(). nM ll seconds
            val result = convertTopLevelAP Result(request, response, startT  )
            val trace d = Trace. d.trace d.toLong
            val scr beMsg =
              bu ldScr be ssage(result, relatedT etScr be tadata, latencyMs, trace d)

            scr beResult(scr beMsg)
          }
        case _ =>
          topLevelAp NoUser dStats.counter(relatedT etScr be tadata.product.or g nalNa ). ncr()
      }
    }
  }

  /**
   * Scr be Per-step  nter d ate results and performance  tr cs
   * for each step: fetch cand dates, f lters.
   */
  pr vate def scr beResultsAndPerformance tr cs[T](
    relatedT etScr be tadata: RelatedT etScr be tadata,
    getResultFn: => Future[T],
    convertToResultFn: (T, User d) => RelatedT etResult
  ): Future[T] = {
    val t  r = Stopwatch.start()
    getResultFn.onSuccess {  nput =>
      relatedT etScr be tadata.cl entContext.user d match {
        case So (user d) =>
           f (dec der. sAva lableFor d(user d, Dec derConstants.upperFunnelPerStepScr beRate)) {
            upperFunnelsStats.counter(relatedT etScr be tadata.product.or g nalNa ). ncr()
            val latencyMs = t  r(). nM ll seconds
            val result = convertToResultFn( nput, user d)
            val trace d = Trace. d.trace d.toLong
            val scr beMsg =
              bu ldScr be ssage(result, relatedT etScr be tadata, latencyMs, trace d)
            scr beResult(scr beMsg)
          }
        case _ =>
          upperFunnelsNoUser dStats.counter(relatedT etScr be tadata.product.or g nalNa ). ncr()
      }
    }
  }

  pr vate def convertTopLevelAP Result(
    request: RelatedT etRequest,
    response: RelatedT etResponse,
    startT  : Long
  ): RelatedT etResult = {
    RelatedT etResult.RelatedT etTopLevelAp Result(
      RelatedT etTopLevelAp Result(
        t  stamp = startT  ,
        request = request,
        response = response
      ))
  }

  pr vate def convertFetchCand datesResult(
    cand datesSeq: Seq[Seq[ n  alCand date]],
    requestUser d: User d
  ): RelatedT etResult = {
    val t etCand datesW h tadata = cand datesSeq.flatMap { cand dates =>
      cand dates.map { cand date =>
        T etCand dateW h tadata(
          t et d = cand date.t et d,
          cand dateGenerat onKey = None
        ) // do not hydrate cand dateGenerat onKey to save cost
      }
    }
    RelatedT etResult.FetchCand datesResult(
      FetchCand datesResult(So (t etCand datesW h tadata)))
  }

  pr vate def convertPreRankF lterResult(
    cand datesSeq: Seq[Seq[ n  alCand date]],
    requestUser d: User d
  ): RelatedT etResult = {
    val t etCand datesW h tadata = cand datesSeq.flatMap { cand dates =>
      cand dates.map { cand date =>
        val cand dateGenerat onKey =
          Cand dateGenerat onKeyUt l.toThr ft(cand date.cand dateGenerat on nfo, requestUser d)
        T etCand dateW h tadata(
          t et d = cand date.t et d,
          cand dateGenerat onKey = So (cand dateGenerat onKey),
          author d = So (cand date.t et nfo.author d),
          score = So (cand date.getS m lar yScore),
          numCand dateGenerat onKeys = None
        )
      }
    }
    RelatedT etResult.PreRankF lterResult(PreRankF lterResult(So (t etCand datesW h tadata)))
  }

  pr vate def bu ldScr be ssage(
    relatedT etResult: RelatedT etResult,
    relatedT etScr be tadata: RelatedT etScr be tadata,
    latencyMs: Long,
    trace d: Long
  ): GetRelatedT etsScr be = {
    GetRelatedT etsScr be(
      uu d = relatedT etScr be tadata.requestUU D,
       nternal d = relatedT etScr be tadata. nternal d,
      relatedT etResult = relatedT etResult,
      requester d = relatedT etScr be tadata.cl entContext.user d,
      guest d = relatedT etScr be tadata.cl entContext.guest d,
      trace d = So (trace d),
      performance tr cs = So (Performance tr cs(So (latencyMs))),
       mpressedBuckets = get mpressedBuckets(scopedStats)
    )
  }

  pr vate def scr beResult(
    scr beMsg: GetRelatedT etsScr be
  ): Un  = {
    publ sh(logger = relatedT etsScr beLogger, codec = GetRelatedT etsScr be,  ssage = scr beMsg)
  }
}

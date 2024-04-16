package com.tw ter.cr_m xer.logg ng

 mport com.tw ter.cr_m xer.model.AdsCand dateGeneratorQuery
 mport com.tw ter.cr_m xer.model. n  alAdsCand date
 mport com.tw ter.cr_m xer.model.ModuleNa s
 mport com.tw ter.cr_m xer.logg ng.Scr beLoggerUt ls._
 mport com.tw ter.cr_m xer.param.dec der.CrM xerDec der
 mport com.tw ter.cr_m xer.param.dec der.Dec derConstants
 mport com.tw ter.cr_m xer.thr ftscala.AdsRecom ndat onTopLevelAp Result
 mport com.tw ter.cr_m xer.thr ftscala.AdsRecom ndat onsResult
 mport com.tw ter.cr_m xer.thr ftscala.AdsRequest
 mport com.tw ter.cr_m xer.thr ftscala.AdsResponse
 mport com.tw ter.cr_m xer.thr ftscala.FetchCand datesResult
 mport com.tw ter.cr_m xer.thr ftscala.GetAdsRecom ndat onsScr be
 mport com.tw ter.cr_m xer.thr ftscala.Performance tr cs
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
case class AdsRecom ndat onsScr beLogger @ nject() (
  @Na d(ModuleNa s.AdsRecom ndat onsLogger) adsRecom ndat onsScr beLogger: Logger,
  dec der: CrM xerDec der,
  statsRece ver: StatsRece ver) {

  pr vate val scopedStats = statsRece ver.scope(t .getClass.getCanon calNa )
  pr vate val upperFunnelsStats = scopedStats.scope("UpperFunnels")
  pr vate val topLevelAp Stats = scopedStats.scope("TopLevelAp ")

  /*
   * Scr be f rst step results after fetch ng  n  al ads cand date
   * */
  def scr be n  alAdsCand dates(
    query: AdsCand dateGeneratorQuery,
    getResultFn: => Future[Seq[Seq[ n  alAdsCand date]]],
    enableScr be: Boolean // controlled by feature sw ch so that   can scr be for certa n DDG
  ): Future[Seq[Seq[ n  alAdsCand date]]] = {
    val scr be tadata = Scr be tadata.from(query)
    val t  r = Stopwatch.start()
    getResultFn.onSuccess {  nput =>
      val latencyMs = t  r(). nM ll seconds
      val result = convertFetchCand datesResult( nput, scr be tadata.user d)
      val trace d = Trace. d.trace d.toLong
      val scr beMsg = bu ldScr be ssage(result, scr be tadata, latencyMs, trace d)

       f (enableScr be && dec der. sAva lableFor d(
          scr be tadata.user d,
          Dec derConstants.adsRecom ndat onsPerExper  ntScr beRate)) {
        upperFunnelsStats.counter(scr be tadata.product.or g nalNa ). ncr()
        scr beResult(scr beMsg)
      }
    }
  }

  /*
   * Scr be top level AP  results
   * */
  def scr beGetAdsRecom ndat ons(
    request: AdsRequest,
    startT  : Long,
    scr be tadata: Scr be tadata,
    getResultFn: => Future[AdsResponse],
    enableScr be: Boolean
  ): Future[AdsResponse] = {
    val t  r = Stopwatch.start()
    getResultFn.onSuccess { response =>
      val latencyMs = t  r(). nM ll seconds
      val result = AdsRecom ndat onsResult.AdsRecom ndat onTopLevelAp Result(
        AdsRecom ndat onTopLevelAp Result(
          t  stamp = startT  ,
          request = request,
          response = response
        ))
      val trace d = Trace. d.trace d.toLong
      val scr beMsg = bu ldScr be ssage(result, scr be tadata, latencyMs, trace d)

       f (enableScr be && dec der. sAva lableFor d(
          scr be tadata.user d,
          Dec derConstants.adsRecom ndat onsPerExper  ntScr beRate)) {
        topLevelAp Stats.counter(scr be tadata.product.or g nalNa ). ncr()
        scr beResult(scr beMsg)
      }
    }
  }

  pr vate def convertFetchCand datesResult(
    cand datesSeq: Seq[Seq[ n  alAdsCand date]],
    requestUser d: User d
  ): AdsRecom ndat onsResult = {
    val t etCand datesW h tadata = cand datesSeq.flatMap { cand dates =>
      cand dates.map { cand date =>
        T etCand dateW h tadata(
          t et d = cand date.t et d,
          cand dateGenerat onKey = So (
            Cand dateGenerat onKeyUt l.toThr ft(cand date.cand dateGenerat on nfo, requestUser d)),
          score = So (cand date.getS m lar yScore),
          numCand dateGenerat onKeys = None // not populated yet
        )
      }
    }
    AdsRecom ndat onsResult.FetchCand datesResult(
      FetchCand datesResult(So (t etCand datesW h tadata)))
  }

  pr vate def bu ldScr be ssage(
    result: AdsRecom ndat onsResult,
    scr be tadata: Scr be tadata,
    latencyMs: Long,
    trace d: Long
  ): GetAdsRecom ndat onsScr be = {
    GetAdsRecom ndat onsScr be(
      uu d = scr be tadata.requestUU D,
      user d = scr be tadata.user d,
      result = result,
      trace d = So (trace d),
      performance tr cs = So (Performance tr cs(So (latencyMs))),
       mpressedBuckets = get mpressedBuckets(scopedStats)
    )
  }

  pr vate def scr beResult(
    scr beMsg: GetAdsRecom ndat onsScr be
  ): Un  = {
    publ sh(
      logger = adsRecom ndat onsScr beLogger,
      codec = GetAdsRecom ndat onsScr be,
       ssage = scr beMsg)
  }

}

/** Copyr ght 2012 Tw ter,  nc. */
package com.tw ter.t etyp e.serv ce

 mport com.tw ter.coreserv ces.StratoPubl cAp RequestAttr but onCounter
 mport com.tw ter.f nagle.CancelledRequestExcept on
 mport com.tw ter.f nagle.context.Contexts
 mport com.tw ter.f nagle.context.Deadl ne
 mport com.tw ter.f nagle.mux.Cl entD scardedRequestExcept on
 mport com.tw ter.f nagle.stats.DefaultStatsRece ver
 mport com.tw ter.f nagle.stats.Stat
 mport com.tw ter.servo.except on.thr ftscala.Cl entError
 mport com.tw ter.servo.ut l.Except onCategor zer
 mport com.tw ter.servo.ut l. mo zedExcept onCounterFactory
 mport com.tw ter.t etyp e.Future
 mport com.tw ter.t etyp e.Gate
 mport com.tw ter.t etyp e.Logger
 mport com.tw ter.t etyp e.StatsRece ver
 mport com.tw ter.t etyp e.Thr ftT etServ ce
 mport com.tw ter.t etyp e.T et d
 mport com.tw ter.t etyp e.cl ent_ d.Cl ent d lper
 mport com.tw ter.t etyp e.context.T etyp eContext
 mport com.tw ter.t etyp e.core.OverCapac y
 mport com.tw ter.t etyp e.serverut l.Except onCounter
 mport com.tw ter.t etyp e.thr ftscala._
 mport com.tw ter.ut l.Prom se

/**
 * A T etServ ce that takes care of t  handl ng of requests from
 * external serv ces.  n part cular, t  wrapper doesn't have any
 * log c for handl ng requests  self.   just serves as a gateway for
 * requests and responses, mak ng sure that t  underly ng t et
 * serv ce only sees requests   should handle and that t  external
 * cl ents get clean responses.
 *
 * - Ensures that except ons are propagated cleanly
 * - S ds traff c  f necessary
 * - Aut nt cates cl ents
 * - Records stats about cl ents
 *
 * For each endpo nt,   record both cl ent-spec f c and total  tr cs for number of requests,
 * successes, except ons, and latency.  T  stats na s follow t  patterns:
 * - ./< thodNa >/requests
 * - ./< thodNa >/success
 * - ./< thodNa >/cl ent_errors
 * - ./< thodNa >/server_errors
 * - ./< thodNa >/except ons
 * - ./< thodNa >/except ons/<except onNa >
 * - ./< thodNa >/<cl ent d>/requests
 * - ./< thodNa >/<cl ent d>/success
 * - ./< thodNa >/<cl ent d>/except ons
 * - ./< thodNa >/<cl ent d>/except ons/<except onNa >
 */
class Cl entHandl ngT etServ ce(
  underly ng: Thr ftT etServ ce,
  stats: StatsRece ver,
  loadS dEl g ble: Gate[Str ng],
  s dReadTraff cVoluntar ly: Gate[Un ],
  requestAuthor zer: Cl entRequestAuthor zer,
  getT etsAuthor zer:  thodAuthor zer[GetT etsRequest],
  getT etF eldsAuthor zer:  thodAuthor zer[GetT etF eldsRequest],
  requestS zeAuthor zer:  thodAuthor zer[ nt],
  cl ent d lper: Cl ent d lper)
    extends Thr ftT etServ ce {
   mport RescueExcept ons._

  pr vate val log = Logger("com.tw ter.t etyp e.serv ce.T etServ ce")

  pr vate[t ] val Requests = "requests"
  pr vate[t ] val Success = "success"
  pr vate[t ] val Latency = "latency_ms"

  pr vate[t ] val StratoStatsCounter = new StratoPubl cAp RequestAttr but onCounter(
    DefaultStatsRece ver
  )
  pr vate[t ] val cl entServerCategor zer =
    Except onCategor zer.s mple {
      _ match {
        case _: Cl entError | _: AccessDen ed => "cl ent_errors"
        case _ => "server_errors"
      }
    }

  pr vate[t ] val preServoExcept onCountersW hCl ent d =
    new  mo zedExcept onCounterFactory(stats)
  pr vate[t ] val preServoExcept onCounters =
    new  mo zedExcept onCounterFactory(stats, categor zer = Except onCounter.defaultCategor zer)
  pr vate[t ] val postServoExcept onCounters =
    new  mo zedExcept onCounterFactory(stats, categor zer = cl entServerCategor zer)

  pr vate def cl ent d: Str ng =
    cl ent d lper.effect veCl ent d.getOrElse(Cl ent d lper.UnknownCl ent d)
  pr vate def cl ent dRoot: Str ng =
    cl ent d lper.effect veCl ent dRoot.getOrElse(Cl ent d lper.UnknownCl ent d)

  pr vate[t ] val futureOverCapac yExcept on =
    Future.except on(OverCapac y("Request rejected due to load s dd ng."))

  pr vate[t ] def  fNotOverCapac yRead[T](
     thodStats: StatsRece ver,
    requestS ze: Long
  )(
    f: => Future[T]
  ): Future[T] = {
    val couldS d = loadS dEl g ble(cl ent d)
    val doS d = couldS d && s dReadTraff cVoluntar ly()

     thodStats.stat("loads d_ ncom ng_requests").add(requestS ze)
     f (couldS d) {
       thodStats.stat("loads d_el g ble_requests").add(requestS ze)
    } else {
       thodStats.stat("loads d_ nel g ble_requests").add(requestS ze)
    }

     f (doS d) {
       thodStats.stat("loads d_rejected_requests").add(requestS ze)
      futureOverCapac yExcept on
    } else {
      f
    }
  }

  pr vate def maybeT  Future[A](maybeStat: Opt on[Stat])(f: => Future[A]) =
    maybeStat match {
      case So (stat) => Stat.t  Future(stat)(f)
      case None => f
    }

  /**
   * Perform t  act on,  ncre nt t  appropr ate counters, and clean up t  except ons to servo except ons
   *
   * T   thod also masks all  nterrupts to prevent request cancellat on on hangup.
   */
  pr vate[t ] def trackS[T](
    na : Str ng,
    request nfo: Any,
    extraStatPref x: Opt on[Str ng] = None,
    requestS ze: Opt on[Long] = None
  )(
    act on: StatsRece ver => Future[T]
  ): Future[T] = {
    val  thodStats = stats.scope(na )
    val cl entStats =  thodStats.scope(cl ent dRoot)
    val cancelledCounter =  thodStats.counter("cancelled")

    /**
     * Returns an  dent cal future except that    gnores  nterrupts and  ncre nts a counter
     * w n a request  s cancelled. T   s [[Future]].masked but w h a counter.
     */
    def maskedW hStats[A](f: Future[A]): Future[A] = {
      val p = Prom se[A]()
      p.set nterruptHandler {
        case _: Cl entD scardedRequestExcept on | _: CancelledRequestExcept on =>
          cancelledCounter. ncr()
      }
      f.proxyTo(p)
      p
    }

    maskedW hStats(
      requestAuthor zer(na , cl ent d lper.effect veCl ent d)
        .flatMap { _ =>
           thodStats.counter(Requests). ncr()
          extraStatPref x.foreach(p =>  thodStats.counter(p, Requests). ncr())
          cl entStats.counter(Requests). ncr()
          StratoStatsCounter.recordStats(na , "t ets", requestS ze.getOrElse(1L))

          Stat.t  Future( thodStats.stat(Latency)) {
            Stat.t  Future(cl entStats.stat(Latency)) {
              maybeT  Future(extraStatPref x.map(p =>  thodStats.stat(p, Latency))) {
                T etyp eContext.Local.trackStats(stats,  thodStats, cl entStats)

                // Remove t  deadl ne for backend requests w n   mask cl ent cancellat ons so
                // that s de-effects are appl ed to all backend serv ces even after cl ent t  outs.
                // Wrap and t n flatten an extra layer of Future to capture any thrown except ons.
                Future(Contexts.broadcast.letClear(Deadl ne)(act on( thodStats))).flatten
              }
            }
          }
        }
    ).onSuccess { _ =>
         thodStats.counter(Success). ncr()
        extraStatPref x.foreach(p =>  thodStats.counter(p, Success). ncr())
        cl entStats.counter(Success). ncr()
      }
      .onFa lure { e =>
        preServoExcept onCounters(na )(e)
        preServoExcept onCountersW hCl ent d(na , cl ent dRoot)(e)
      }
      .rescue(rescueToServoFa lure(na , cl ent d))
      .onFa lure { e =>
        postServoExcept onCounters(na )(e)
        logFa lure(e, request nfo)
      }
  }

  def track[T](
    na : Str ng,
    request nfo: Any,
    extraStatPref x: Opt on[Str ng] = None,
    requestS ze: Opt on[Long] = None
  )(
    act on: => Future[T]
  ): Future[T] = {
    trackS(na , request nfo, extraStatPref x, requestS ze) { _: StatsRece ver => act on }
  }

  pr vate def logFa lure(ex: Throwable, request nfo: Any): Un  =
    log.warn(s"Return ng fa lure response: $ex\n fa led request  nfo: $request nfo")

  object RequestW dthPref x {
    pr vate def pref x(w dth:  nt) = {
      val bucketM n =
        w dth match {
          case c  f c < 10 => "0_9"
          case c  f c < 100 => "10_99"
          case _ => "100_plus"
        }
      s"w dth_$bucketM n"
    }

    def forGetT etsRequest(r: GetT etsRequest): Str ng = pref x(r.t et ds.s ze)
    def forGetT etF eldsRequest(r: GetT etF eldsRequest): Str ng = pref x(r.t et ds.s ze)
  }

  object W h d aPref x {
    def forPostT etRequest(r: PostT etRequest): Str ng =
       f (r. d aUpload ds.ex sts(_.nonEmpty))
        "w h_ d a"
      else
        "w hout_ d a"
  }

  overr de def getT ets(request: GetT etsRequest): Future[Seq[GetT etResult]] =
    trackS(
      "get_t ets",
      request,
      So (RequestW dthPref x.forGetT etsRequest(request)),
      So (request.t et ds.s ze)
    ) { stats =>
      getT etsAuthor zer(request, cl ent d).flatMap { _ =>
         fNotOverCapac yRead(stats, request.t et ds.length) {
          underly ng.getT ets(request)
        }
      }
    }

  overr de def getT etF elds(request: GetT etF eldsRequest): Future[Seq[GetT etF eldsResult]] =
    trackS(
      "get_t et_f elds",
      request,
      So (RequestW dthPref x.forGetT etF eldsRequest(request)),
      So (request.t et ds.s ze)
    ) { stats =>
      getT etF eldsAuthor zer(request, cl ent d).flatMap { _ =>
         fNotOverCapac yRead(stats, request.t et ds.length) {
          underly ng.getT etF elds(request)
        }
      }
    }

  overr de def repl catedGetT ets(request: GetT etsRequest): Future[Un ] =
    track("repl cated_get_t ets", request, requestS ze = So (request.t et ds.s ze)) {
      underly ng.repl catedGetT ets(request).rescue {
        case e: Throwable => Future.Un  // do not need deferredrpc to retry on except ons
      }
    }

  overr de def repl catedGetT etF elds(request: GetT etF eldsRequest): Future[Un ] =
    track("repl cated_get_t et_f elds", request, requestS ze = So (request.t et ds.s ze)) {
      underly ng.repl catedGetT etF elds(request).rescue {
        case e: Throwable => Future.Un  // do not need deferredrpc to retry on except ons
      }
    }

  overr de def getT etCounts(request: GetT etCountsRequest): Future[Seq[GetT etCountsResult]] =
    trackS("get_t et_counts", request, requestS ze = So (request.t et ds.s ze)) { stats =>
       fNotOverCapac yRead(stats, request.t et ds.length) {
        requestS zeAuthor zer(request.t et ds.s ze, cl ent d).flatMap { _ =>
          underly ng.getT etCounts(request)
        }
      }
    }

  overr de def repl catedGetT etCounts(request: GetT etCountsRequest): Future[Un ] =
    track("repl cated_get_t et_counts", request, requestS ze = So (request.t et ds.s ze)) {
      underly ng.repl catedGetT etCounts(request).rescue {
        case e: Throwable => Future.Un  // do not need deferredrpc to retry on except ons
      }
    }

  overr de def postT et(request: PostT etRequest): Future[PostT etResult] =
    track("post_t et", request, So (W h d aPref x.forPostT etRequest(request))) {
      underly ng.postT et(request)
    }

  overr de def postRet et(request: Ret etRequest): Future[PostT etResult] =
    track("post_ret et", request) {
      underly ng.postRet et(request)
    }

  overr de def setAdd  onalF elds(request: SetAdd  onalF eldsRequest): Future[Un ] =
    track("set_add  onal_f elds", request) {
      underly ng.setAdd  onalF elds(request)
    }

  overr de def deleteAdd  onalF elds(request: DeleteAdd  onalF eldsRequest): Future[Un ] =
    track("delete_add  onal_f elds", request, requestS ze = So (request.t et ds.s ze)) {
      requestS zeAuthor zer(request.t et ds.s ze, cl ent d).flatMap { _ =>
        underly ng.deleteAdd  onalF elds(request)
      }
    }

  overr de def asyncSetAdd  onalF elds(request: AsyncSetAdd  onalF eldsRequest): Future[Un ] =
    track("async_set_add  onal_f elds", request) {
      underly ng.asyncSetAdd  onalF elds(request)
    }

  overr de def asyncDeleteAdd  onalF elds(
    request: AsyncDeleteAdd  onalF eldsRequest
  ): Future[Un ] =
    track("async_delete_add  onal_f elds", request) {
      underly ng.asyncDeleteAdd  onalF elds(request)
    }

  overr de def repl catedUndeleteT et2(request: Repl catedUndeleteT et2Request): Future[Un ] =
    track("repl cated_undelete_t et2", request) { underly ng.repl catedUndeleteT et2(request) }

  overr de def repl cated nsertT et2(request: Repl cated nsertT et2Request): Future[Un ] =
    track("repl cated_ nsert_t et2", request) { underly ng.repl cated nsertT et2(request) }

  overr de def async nsert(request: Async nsertRequest): Future[Un ] =
    track("async_ nsert", request) { underly ng.async nsert(request) }

  overr de def updatePoss blySens  veT et(
    request: UpdatePoss blySens  veT etRequest
  ): Future[Un ] =
    track("update_poss bly_sens  ve_t et", request) {
      underly ng.updatePoss blySens  veT et(request)
    }

  overr de def asyncUpdatePoss blySens  veT et(
    request: AsyncUpdatePoss blySens  veT etRequest
  ): Future[Un ] =
    track("async_update_poss bly_sens  ve_t et", request) {
      underly ng.asyncUpdatePoss blySens  veT et(request)
    }

  overr de def repl catedUpdatePoss blySens  veT et(t et: T et): Future[Un ] =
    track("repl cated_update_poss bly_sens  ve_t et", t et) {
      underly ng.repl catedUpdatePoss blySens  veT et(t et)
    }

  overr de def undeleteT et(request: UndeleteT etRequest): Future[UndeleteT etResponse] =
    track("undelete_t et", request) {
      underly ng.undeleteT et(request)
    }

  overr de def asyncUndeleteT et(request: AsyncUndeleteT etRequest): Future[Un ] =
    track("async_undelete_t et", request) {
      underly ng.asyncUndeleteT et(request)
    }

  overr de def unret et(request: Unret etRequest): Future[Unret etResult] =
    track("unret et", request) {
      underly ng.unret et(request)
    }

  overr de def eraseUserT ets(request: EraseUserT etsRequest): Future[Un ] =
    track("erase_user_t ets", request) {
      underly ng.eraseUserT ets(request)
    }

  overr de def asyncEraseUserT ets(request: AsyncEraseUserT etsRequest): Future[Un ] =
    track("async_erase_user_t ets", request) {
      underly ng.asyncEraseUserT ets(request)
    }

  overr de def asyncDelete(request: AsyncDeleteRequest): Future[Un ] =
    track("async_delete", request) { underly ng.asyncDelete(request) }

  overr de def deleteT ets(request: DeleteT etsRequest): Future[Seq[DeleteT etResult]] =
    track("delete_t ets", request, requestS ze = So (request.t et ds.s ze)) {
      requestS zeAuthor zer(request.t et ds.s ze, cl ent d).flatMap { _ =>
        underly ng.deleteT ets(request)
      }
    }

  overr de def cascadedDeleteT et(request: CascadedDeleteT etRequest): Future[Un ] =
    track("cascaded_delete_t et", request) { underly ng.cascadedDeleteT et(request) }

  overr de def repl catedDeleteT et2(request: Repl catedDeleteT et2Request): Future[Un ] =
    track("repl cated_delete_t et2", request) { underly ng.repl catedDeleteT et2(request) }

  overr de def  ncrT etFavCount(request:  ncrT etFavCountRequest): Future[Un ] =
    track(" ncr_t et_fav_count", request) { underly ng. ncrT etFavCount(request) }

  overr de def async ncrFavCount(request: Async ncrFavCountRequest): Future[Un ] =
    track("async_ ncr_fav_count", request) { underly ng.async ncrFavCount(request) }

  overr de def repl cated ncrFavCount(t et d: T et d, delta:  nt): Future[Un ] =
    track("repl cated_ ncr_fav_count", t et d) {
      underly ng.repl cated ncrFavCount(t et d, delta)
    }

  overr de def  ncrT etBookmarkCount(request:  ncrT etBookmarkCountRequest): Future[Un ] =
    track(" ncr_t et_bookmark_count", request) { underly ng. ncrT etBookmarkCount(request) }

  overr de def async ncrBookmarkCount(request: Async ncrBookmarkCountRequest): Future[Un ] =
    track("async_ ncr_bookmark_count", request) { underly ng.async ncrBookmarkCount(request) }

  overr de def repl cated ncrBookmarkCount(t et d: T et d, delta:  nt): Future[Un ] =
    track("repl cated_ ncr_bookmark_count", t et d) {
      underly ng.repl cated ncrBookmarkCount(t et d, delta)
    }

  overr de def repl catedSetAdd  onalF elds(request: SetAdd  onalF eldsRequest): Future[Un ] =
    track("repl cated_set_add  onal_f elds", request) {
      underly ng.repl catedSetAdd  onalF elds(request)
    }

  def setRet etV s b l y(request: SetRet etV s b l yRequest): Future[Un ] = {
    track("set_ret et_v s b l y", request) {
      underly ng.setRet etV s b l y(request)
    }
  }

  def asyncSetRet etV s b l y(request: AsyncSetRet etV s b l yRequest): Future[Un ] = {
    track("async_set_ret et_v s b l y", request) {
      underly ng.asyncSetRet etV s b l y(request)
    }
  }

  overr de def repl catedSetRet etV s b l y(
    request: Repl catedSetRet etV s b l yRequest
  ): Future[Un ] =
    track("repl cated_set_ret et_v s b l y", request) {
      underly ng.repl catedSetRet etV s b l y(request)
    }

  overr de def repl catedDeleteAdd  onalF elds(
    request: Repl catedDeleteAdd  onalF eldsRequest
  ): Future[Un ] =
    track("repl cated_delete_add  onal_f elds", request) {
      underly ng.repl catedDeleteAdd  onalF elds(request)
    }

  overr de def repl catedTakedown(t et: T et): Future[Un ] =
    track("repl cated_takedown", t et) { underly ng.repl catedTakedown(t et) }

  overr de def scrubGeoUpdateUserT  stamp(request: DeleteLocat onData): Future[Un ] =
    track("scrub_geo_update_user_t  stamp", request) {
      underly ng.scrubGeoUpdateUserT  stamp(request)
    }

  overr de def scrubGeo(request: GeoScrub): Future[Un ] =
    track("scrub_geo", request, requestS ze = So (request.status ds.s ze)) {
      requestS zeAuthor zer(request.status ds.s ze, cl ent d).flatMap { _ =>
        underly ng.scrubGeo(request)
      }
    }

  overr de def repl catedScrubGeo(t et ds: Seq[T et d]): Future[Un ] =
    track("repl cated_scrub_geo", t et ds) { underly ng.repl catedScrubGeo(t et ds) }

  overr de def deleteLocat onData(request: DeleteLocat onDataRequest): Future[Un ] =
    track("delete_locat on_data", request) {
      underly ng.deleteLocat onData(request)
    }

  overr de def flush(request: FlushRequest): Future[Un ] =
    track("flush", request, requestS ze = So (request.t et ds.s ze)) {
      requestS zeAuthor zer(request.t et ds.s ze, cl ent d).flatMap { _ =>
        underly ng.flush(request)
      }
    }

  overr de def takedown(request: TakedownRequest): Future[Un ] =
    track("takedown", request) { underly ng.takedown(request) }

  overr de def asyncTakedown(request: AsyncTakedownRequest): Future[Un ] =
    track("async_takedown", request) {
      underly ng.asyncTakedown(request)
    }

  overr de def setT etUserTakedown(request: SetT etUserTakedownRequest): Future[Un ] =
    track("set_t et_user_takedown", request) { underly ng.setT etUserTakedown(request) }

  overr de def quotedT etDelete(request: QuotedT etDeleteRequest): Future[Un ] =
    track("quoted_t et_delete", request) {
      underly ng.quotedT etDelete(request)
    }

  overr de def quotedT etTakedown(request: QuotedT etTakedownRequest): Future[Un ] =
    track("quoted_t et_takedown", request) {
      underly ng.quotedT etTakedown(request)
    }

  overr de def getDeletedT ets(
    request: GetDeletedT etsRequest
  ): Future[Seq[GetDeletedT etResult]] =
    track("get_deleted_t ets", request, requestS ze = So (request.t et ds.s ze)) {
      requestS zeAuthor zer(request.t et ds.s ze, cl ent d).flatMap { _ =>
        underly ng.getDeletedT ets(request)
      }
    }

  overr de def getStoredT ets(
    request: GetStoredT etsRequest
  ): Future[Seq[GetStoredT etsResult]] = {
    track("get_stored_t ets", request, requestS ze = So (request.t et ds.s ze)) {
      requestS zeAuthor zer(request.t et ds.s ze, cl ent d).flatMap { _ =>
        underly ng.getStoredT ets(request)
      }
    }
  }

  overr de def getStoredT etsByUser(
    request: GetStoredT etsByUserRequest
  ): Future[GetStoredT etsByUserResult] = {
    track("get_stored_t ets_by_user", request) {
      underly ng.getStoredT etsByUser(request)
    }
  }
}

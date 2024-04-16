package com.tw ter.t etyp e
package serv ce

 mport com.tw ter.servo.except on.thr ftscala.Cl entError
 mport com.tw ter.servo.ut l.Synchron zedHashMap
 mport com.tw ter.t etyp e.cl ent_ d.Cl ent d lper
 mport com.tw ter.t etyp e.serv ce.observer._
 mport com.tw ter.t etyp e.thr ftscala._
 mport com.tw ter.f nagle.trac ng.Trace

/**
 * Wraps an underly ng T etServ ce, observ ng requests and results.
 */
class ObservedT etServ ce(
  protected val underly ng: Thr ftT etServ ce,
  stats: StatsRece ver,
  cl ent d lper: Cl ent d lper)
    extends T etServ ceProxy {

  pr vate[t ] val asyncEventOrRetryScope = stats.scope("async_event_or_retry")
  pr vate[t ] val deleteF eldsScope = stats.scope("delete_add  onal_f elds")
  pr vate[t ] val deleteT etsScope = stats.scope("delete_t ets")
  pr vate[t ] val getDeletedT etsScope = stats.scope("get_deleted_t ets")
  pr vate[t ] val getT etCountsScope = stats.scope("get_t et_counts")
  pr vate[t ] val getT etsScope = stats.scope("get_t ets")
  pr vate[t ] val getT etF eldsScope = stats.scope("get_t et_f elds")
  pr vate[t ] val postT etScope = stats.scope("post_t et")
  pr vate[t ] val repl cated nsertT et2Scope = stats.scope("repl cated_ nsert_t et2")
  pr vate[t ] val ret etScope = stats.scope("post_ret et")
  pr vate[t ] val scrubGeoScope = stats.scope("scrub_geo")
  pr vate[t ] val setF eldsScope = stats.scope("set_add  onal_f elds")
  pr vate[t ] val setRet etV s b l yScope = stats.scope("set_ret et_v s b l y")
  pr vate[t ] val getStoredT etsScope = stats.scope("get_stored_t ets")
  pr vate[t ] val getStoredT etsByUserScope = stats.scope("get_stored_t ets_by_user")

  pr vate[t ] val defaultGetT etsRequestOpt ons = GetT etOpt ons()

  /**  ncre nts t  appropr ate wr e success/fa lure counter */
  pr vate[t ] val observeWr eResult: Effect[Try[_]] = {
    w hAndW houtCl ent d(stats) { (stats, _) =>
      val successCounter = stats.counter("wr e_successes")
      val fa lureCounter = stats.counter("wr e_fa lures")
      val cl entErrorCounter = stats.counter("wr e_cl ent_errors")
      Effect[Try[_]] {
        case Return(_) => successCounter. ncr()
        case Throw(Cl entError(_, _)) | Throw(AccessDen ed(_, _)) => cl entErrorCounter. ncr()
        case Throw(_) => fa lureCounter. ncr()
      }
    }
  }

  /**  ncre nts t  t et_creates counter on future success. */
  pr vate[t ] val observeT etWr eSuccess: Effect[Any] = {
    w hAndW houtCl ent d(stats) { (stats, _) =>
      val counter = stats.counter("t et_wr es")
      Effect[Any] { _ => counter. ncr() }
    }
  }

  pr vate[t ] val observeGetT etsRequest =
    w hAndW houtCl ent d(getT etsScope) {
      GetT etsObserver.observeRequest
    }

  pr vate[t ] val observeGetT etF eldsRequest =
    w hAndW houtCl ent d(getT etF eldsScope) {
      GetT etF eldsObserver.observeRequest
    }

  pr vate[t ] val observeGetT etCountsRequest =
    w hAndW houtCl ent d(getT etCountsScope) { (s, _) =>
      GetT etCountsObserver.observeRequest(s)
    }

  pr vate[t ] val observeRet etRequest: Effect[Ret etRequest] =
    w hAndW houtCl ent d(ret etScope) { (s, _) => Observer.observeRet etRequest(s) }

  pr vate[t ] val observeDeleteT etsRequest =
    w hAndW houtCl ent d(deleteT etsScope) { (s, _) => Observer.observeDeleteT etsRequest(s) }

  pr vate[t ] val observeSetF eldsRequest: Effect[SetAdd  onalF eldsRequest] =
    w hAndW houtCl ent d(setF eldsScope) { (s, _) => Observer.observeSetF eldsRequest(s) }

  pr vate[t ] val observeSetRet etV s b l yRequest: Effect[SetRet etV s b l yRequest] =
    w hAndW houtCl ent d(setRet etV s b l yScope) { (s, _) =>
      Observer.observeSetRet etV s b l yRequest(s)
    }

  pr vate[t ] val observeDeleteF eldsRequest: Effect[DeleteAdd  onalF eldsRequest] =
    w hAndW houtCl ent d(deleteF eldsScope) { (s, _) => Observer.observeDeleteF eldsRequest(s) }

  pr vate[t ] val observePostT etAdd  onals: Effect[T et] =
    w hAndW houtCl ent d(postT etScope) { (s, _) => Observer.observeAdd  onalF elds(s) }

  pr vate[t ] val observePostT etRequest: Effect[PostT etRequest] =
    w hAndW houtCl ent d(postT etScope) { (s, _) => PostT etObserver.observerRequest(s) }

  pr vate[t ] val observeGetT etResults =
    w hAndW houtCl ent d(getT etsScope) {
      GetT etsObserver.observeResults
    }

  pr vate[t ] val observeGetT etF eldsResults: Effect[Seq[GetT etF eldsResult]] =
    GetT etF eldsObserver.observeResults(getT etF eldsScope)

  pr vate[t ] val observeT etCountsResults =
    GetT etCountsObserver.observeResults(getT etCountsScope)

  pr vate[t ] val observeScrubGeoRequest =
    Observer.observeScrubGeo(scrubGeoScope)

  pr vate[t ] val observeRet etResponse =
    PostT etObserver.observeResults(ret etScope, byCl ent = false)

  pr vate[t ] val observePostT etResponse =
    PostT etObserver.observeResults(postT etScope, byCl ent = false)

  pr vate[t ] val observeAsync nsertRequest =
    Observer.observeAsync nsertRequest(asyncEventOrRetryScope)

  pr vate[t ] val observeAsyncSetAdd  onalF eldsRequest =
    Observer.observeAsyncSetAdd  onalF eldsRequest(asyncEventOrRetryScope)

  pr vate[t ] val observeAsyncSetRet etV s b l yRequest =
    Observer.observeAsyncSetRet etV s b l yRequest(asyncEventOrRetryScope)

  pr vate[t ] val observeAsyncUndeleteT etRequest =
    Observer.observeAsyncUndeleteT etRequest(asyncEventOrRetryScope)

  pr vate[t ] val observeAsyncDeleteT etRequest =
    Observer.observeAsyncDeleteT etRequest(asyncEventOrRetryScope)

  pr vate[t ] val observeAsyncDeleteAdd  onalF eldsRequest =
    Observer.observeAsyncDeleteAdd  onalF eldsRequest(asyncEventOrRetryScope)

  pr vate[t ] val observeAsyncTakedownRequest =
    Observer.observeAsyncTakedownRequest(asyncEventOrRetryScope)

  pr vate[t ] val observeAsyncUpdatePoss blySens  veT etRequest =
    Observer.observeAsyncUpdatePoss blySens  veT etRequest(asyncEventOrRetryScope)

  pr vate[t ] val observedRepl cated nsertT et2Request =
    Observer.observeRepl cated nsertT etRequest(repl cated nsertT et2Scope)

  pr vate[t ] val observeGetT etF eldsResultState: Effect[GetT etF eldsObserver.Type] =
    w hAndW houtCl ent d(getT etF eldsScope) { (statsRece ver, _) =>
      GetT etF eldsObserver.observeExchange(statsRece ver)
    }

  pr vate[t ] val observeGetT etsResultState: Effect[GetT etsObserver.Type] =
    w hAndW houtCl ent d(getT etsScope) { (statsRece ver, _) =>
      GetT etsObserver.observeExchange(statsRece ver)
    }

  pr vate[t ] val observeGetT etCountsResultState: Effect[GetT etCountsObserver.Type] =
    w hAndW houtCl ent d(getT etCountsScope) { (statsRece ver, _) =>
      GetT etCountsObserver.observeExchange(statsRece ver)
    }

  pr vate[t ] val observeGetDeletedT etsResultState: Effect[GetDeletedT etsObserver.Type] =
    w hAndW houtCl ent d(getDeletedT etsScope) { (statsRece ver, _) =>
      GetDeletedT etsObserver.observeExchange(statsRece ver)
    }

  pr vate[t ] val observeGetStoredT etsRequest: Effect[GetStoredT etsRequest] =
    GetStoredT etsObserver.observeRequest(getStoredT etsScope)

  pr vate[t ] val observeGetStoredT etsResult: Effect[Seq[GetStoredT etsResult]] =
    GetStoredT etsObserver.observeResult(getStoredT etsScope)

  pr vate[t ] val observeGetStoredT etsResultState: Effect[GetStoredT etsObserver.Type] =
    GetStoredT etsObserver.observeExchange(getStoredT etsScope)

  pr vate[t ] val observeGetStoredT etsByUserRequest: Effect[GetStoredT etsByUserRequest] =
    GetStoredT etsByUserObserver.observeRequest(getStoredT etsByUserScope)

  pr vate[t ] val observeGetStoredT etsByUserResult: Effect[GetStoredT etsByUserResult] =
    GetStoredT etsByUserObserver.observeResult(getStoredT etsByUserScope)

  pr vate[t ] val observeGetStoredT etsByUserResultState: Effect[
    GetStoredT etsByUserObserver.Type
  ] =
    GetStoredT etsByUserObserver.observeExchange(getStoredT etsByUserScope)

  overr de def getT ets(request: GetT etsRequest): Future[Seq[GetT etResult]] = {
    val actualRequest =
       f (request.opt ons.nonEmpty) request
      else request.copy(opt ons = So (defaultGetT etsRequestOpt ons))
    observeGetT etsRequest(actualRequest)
    Trace.recordB nary("query_w dth", request.t et ds.length)
    super
      .getT ets(request)
      .onSuccess(observeGetT etResults)
      .respond(response => observeGetT etsResultState((request, response)))
  }

  overr de def getT etF elds(request: GetT etF eldsRequest): Future[Seq[GetT etF eldsResult]] = {
    observeGetT etF eldsRequest(request)
    Trace.recordB nary("query_w dth", request.t et ds.length)
    super
      .getT etF elds(request)
      .onSuccess(observeGetT etF eldsResults)
      .respond(response => observeGetT etF eldsResultState((request, response)))
  }

  overr de def getT etCounts(request: GetT etCountsRequest): Future[Seq[GetT etCountsResult]] = {
    observeGetT etCountsRequest(request)
    Trace.recordB nary("query_w dth", request.t et ds.length)
    super
      .getT etCounts(request)
      .onSuccess(observeT etCountsResults)
      .respond(response => observeGetT etCountsResultState((request, response)))
  }

  overr de def getDeletedT ets(
    request: GetDeletedT etsRequest
  ): Future[Seq[GetDeletedT etResult]] = {
    Trace.recordB nary("query_w dth", request.t et ds.length)
    super
      .getDeletedT ets(request)
      .respond(response => observeGetDeletedT etsResultState((request, response)))
  }

  overr de def postT et(request: PostT etRequest): Future[PostT etResult] = {
    observePostT etRequest(request)
    request.add  onalF elds.foreach(observePostT etAdd  onals)
    super
      .postT et(request)
      .onSuccess(observePostT etResponse)
      .onSuccess(observeT etWr eSuccess)
      .respond(observeWr eResult)
  }

  overr de def postRet et(request: Ret etRequest): Future[PostT etResult] = {
    observeRet etRequest(request)
    super
      .postRet et(request)
      .onSuccess(observeRet etResponse)
      .onSuccess(observeT etWr eSuccess)
      .respond(observeWr eResult)
  }

  overr de def setAdd  onalF elds(request: SetAdd  onalF eldsRequest): Future[Un ] = {
    observeSetF eldsRequest(request)
    super
      .setAdd  onalF elds(request)
      .respond(observeWr eResult)
  }

  overr de def setRet etV s b l y(request: SetRet etV s b l yRequest): Future[Un ] = {
    observeSetRet etV s b l yRequest(request)
    super
      .setRet etV s b l y(request)
      .respond(observeWr eResult)
  }

  overr de def deleteAdd  onalF elds(request: DeleteAdd  onalF eldsRequest): Future[Un ] = {
    observeDeleteF eldsRequest(request)
    super
      .deleteAdd  onalF elds(request)
      .respond(observeWr eResult)
  }

  overr de def updatePoss blySens  veT et(
    request: UpdatePoss blySens  veT etRequest
  ): Future[Un ] =
    super
      .updatePoss blySens  veT et(request)
      .respond(observeWr eResult)

  overr de def deleteLocat onData(request: DeleteLocat onDataRequest): Future[Un ] =
    super
      .deleteLocat onData(request)
      .respond(observeWr eResult)

  overr de def scrubGeo(geoScrub: GeoScrub): Future[Un ] = {
    observeScrubGeoRequest(geoScrub)
    super
      .scrubGeo(geoScrub)
      .respond(observeWr eResult)
  }

  overr de def scrubGeoUpdateUserT  stamp(request: DeleteLocat onData): Future[Un ] =
    super.scrubGeoUpdateUserT  stamp(request).respond(observeWr eResult)

  overr de def takedown(request: TakedownRequest): Future[Un ] =
    super
      .takedown(request)
      .respond(observeWr eResult)

  overr de def setT etUserTakedown(request: SetT etUserTakedownRequest): Future[Un ] =
    super
      .setT etUserTakedown(request)
      .respond(observeWr eResult)

  overr de def  ncrT etFavCount(request:  ncrT etFavCountRequest): Future[Un ] =
    super
      . ncrT etFavCount(request)
      .respond(observeWr eResult)

  overr de def  ncrT etBookmarkCount(request:  ncrT etBookmarkCountRequest): Future[Un ] =
    super
      . ncrT etBookmarkCount(request)
      .respond(observeWr eResult)

  overr de def deleteT ets(request: DeleteT etsRequest): Future[Seq[DeleteT etResult]] = {
    observeDeleteT etsRequest(request)
    super
      .deleteT ets(request)
      .respond(observeWr eResult)
  }

  overr de def cascadedDeleteT et(request: CascadedDeleteT etRequest): Future[Un ] =
    super
      .cascadedDeleteT et(request)
      .respond(observeWr eResult)

  overr de def async nsert(request: Async nsertRequest): Future[Un ] = {
    observeAsync nsertRequest(request)
    super
      .async nsert(request)
      .respond(observeWr eResult)
  }

  overr de def asyncSetAdd  onalF elds(request: AsyncSetAdd  onalF eldsRequest): Future[Un ] = {
    observeAsyncSetAdd  onalF eldsRequest(request)
    super
      .asyncSetAdd  onalF elds(request)
      .respond(observeWr eResult)
  }

  overr de def asyncSetRet etV s b l y(
    request: AsyncSetRet etV s b l yRequest
  ): Future[Un ] = {
    observeAsyncSetRet etV s b l yRequest(request)
    super
      .asyncSetRet etV s b l y(request)
      .respond(observeWr eResult)
  }

  overr de def asyncUndeleteT et(request: AsyncUndeleteT etRequest): Future[Un ] = {
    observeAsyncUndeleteT etRequest(request)
    super
      .asyncUndeleteT et(request)
      .respond(observeWr eResult)
  }

  overr de def asyncDelete(request: AsyncDeleteRequest): Future[Un ] = {
    observeAsyncDeleteT etRequest(request)
    super
      .asyncDelete(request)
      .respond(observeWr eResult)
  }

  overr de def asyncDeleteAdd  onalF elds(
    request: AsyncDeleteAdd  onalF eldsRequest
  ): Future[Un ] = {
    observeAsyncDeleteAdd  onalF eldsRequest(request)
    super
      .asyncDeleteAdd  onalF elds(request)
      .respond(observeWr eResult)
  }

  overr de def asyncTakedown(request: AsyncTakedownRequest): Future[Un ] = {
    observeAsyncTakedownRequest(request)
    super
      .asyncTakedown(request)
      .respond(observeWr eResult)
  }

  overr de def asyncUpdatePoss blySens  veT et(
    request: AsyncUpdatePoss blySens  veT etRequest
  ): Future[Un ] = {
    observeAsyncUpdatePoss blySens  veT etRequest(request)
    super
      .asyncUpdatePoss blySens  veT et(request)
      .respond(observeWr eResult)
  }

  overr de def repl cated nsertT et2(request: Repl cated nsertT et2Request): Future[Un ] = {
    observedRepl cated nsertT et2Request(request.cac dT et.t et)
    super.repl cated nsertT et2(request)
  }

  overr de def getStoredT ets(
    request: GetStoredT etsRequest
  ): Future[Seq[GetStoredT etsResult]] = {
    observeGetStoredT etsRequest(request)
    super
      .getStoredT ets(request)
      .onSuccess(observeGetStoredT etsResult)
      .respond(response => observeGetStoredT etsResultState((request, response)))
  }

  overr de def getStoredT etsByUser(
    request: GetStoredT etsByUserRequest
  ): Future[GetStoredT etsByUserResult] = {
    observeGetStoredT etsByUserRequest(request)
    super
      .getStoredT etsByUser(request)
      .onSuccess(observeGetStoredT etsByUserResult)
      .respond(response => observeGetStoredT etsByUserResultState((request, response)))
  }

  pr vate def w hAndW houtCl ent d[A](
    stats: StatsRece ver
  )(
    f: (StatsRece ver, Boolean) => Effect[A]
  ) =
    f(stats, false).also(w hCl ent d(stats)(f))

  pr vate def w hCl ent d[A](stats: StatsRece ver)(f: (StatsRece ver, Boolean) => Effect[A]) = {
    val map = new Synchron zedHashMap[Str ng, Effect[A]]

    Effect[A] { value =>
      cl ent d lper.effect veCl ent dRoot.foreach { cl ent d =>
        val cl entObserver = map.getOrElseUpdate(cl ent d, f(stats.scope(cl ent d), true))
        cl entObserver(value)
      }
    }
  }
}
